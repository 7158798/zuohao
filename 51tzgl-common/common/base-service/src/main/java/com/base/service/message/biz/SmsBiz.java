package com.base.service.message.biz;

import com.base.common.fileupload.client.AliyunFileUploadUtils;
import com.base.common.fileupload.facade.request.AliyunConfigReq;
import com.jucaifu.common.log.LOG;
import com.jucaifu.core.biz.AbsBaseBiz;
import com.base.facade.exception.BaseCommonBizException;
import com.base.facade.message.enums.SmsStatus;
import com.base.facade.message.pojo.po.Sms;
import com.base.facade.message.pojo.po.SmsSendObj;
import com.base.facade.message.pojo.vo.SmsVo;
import com.base.service.message.dao.SmsMapper;
import com.base.service.message.dao.SmsSendObjMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
/**
 * @author luwei
 * @Date 16-10-26 下午4:22
 */
@Service
@Transactional
public class SmsBiz extends AbsBaseBiz<Sms, SmsVo, SmsMapper> {

    private final AliyunConfigReq smsConfigReq =  new AliyunConfigReq("console/sms", AliyunFileUploadUtils.PRIVATE_BUCKET, 0, AliyunFileUploadUtils.PRIVATE_FILE_LOAD_PATH);

    @Autowired
    private SmsMapper smsMapper;

    @Autowired
    private SmsSendObjMapper smsSendObjMapper;

    @Override
    public SmsMapper getDefaultMapper() {
        return smsMapper;
    }

    public Long addSms(Sms sms) {
        LOG.dStart(this, "新增短信通知开始");
        //数据校验
        validate(sms, "1");
        Date currentDate = new Date();
        sms.setCreateDate(currentDate);
        sms.setModifiedDate(currentDate);
        sms.setDeleted(Boolean.FALSE);
        smsMapper.insert(sms);
        for(SmsSendObj obj : sms.getSendObjList()) {
            obj.setSmsId(sms.getId());
            obj.setDeleted(Boolean.FALSE);
            obj.setCreateUserId(sms.getCreateUserId());
            obj.setModifiedUserId(sms.getModifiedUserId());
            obj.setCreateDate(currentDate);
            obj.setModifiedDate(currentDate);
            smsSendObjMapper.insert(obj);
        }
        LOG.dEnd(this, "新增短信通知结束");
        return sms.getId();
    }


    public void updateSendStatus(Sms sms) {
        Sms vo = smsMapper.select(sms.getId());
        if(vo == null){
            throw new BaseCommonBizException("根据id查询短息通知信息时，查询为空，产生脏读，请核对数据后再查询");
        }
        vo.setStatus(sms.getStatus());
        vo.setActualSendingTime(sms.getActualSendingTime());
        vo.setSendResultJson(sms.getSendResultJson());
        smsMapper.update(sms);
    }

    public Long updateSms(Sms sms, Boolean isReloadFile) {
        validate(sms, "2");
        Sms vo = smsMapper.select(sms.getId());
        if(vo == null){
            throw new BaseCommonBizException("根据id查询短息通知信息时，查询为空，产生脏读，请核对数据后再查询");
        }
        vo.setStatus(sms.getStatus());
        vo.setUrl(sms.getUrl());
        vo.setTitle(sms.getTitle());
        vo.setContent(sms.getContent());
        vo.setSendNow(sms.getSendNow());
        vo.setSendingTime(sms.getSendingTime());
        Date currentDate = new Date();
        vo.setModifiedDate(currentDate);
        vo.setModifiedUserId(sms.getModifiedUserId());
        smsMapper.update(vo);
        //是重新上传的文件，并且是草稿状态    注：只有草稿状态可编辑
        if(isReloadFile && vo.getStatus().intValue() == SmsStatus.DRAFT.getStatus().intValue()){
            //修改时，没有数据变化，则沿用旧的
            if(sms.getSendObjList() == null || sms.getSendObjList().size() == 0){
                return sms.getId();
            }
            //物理删除外键表数据
            smsSendObjMapper.physicalDeleteBySmsId(sms.getId());
            //重新插入外键表数据
            for(SmsSendObj obj : sms.getSendObjList()) {
                obj.setSmsId(sms.getId());
                obj.setDeleted(Boolean.FALSE);
                obj.setCreateUserId(sms.getModifiedUserId());
                obj.setModifiedUserId(sms.getModifiedUserId());
                obj.setCreateDate(currentDate);
                obj.setModifiedDate(currentDate);
                smsSendObjMapper.insert(obj);
            }
        }
        return sms.getId();
    }

    public void deleteById(Long id){
        LOG.d(this, "删除短信通知开始");
        Sms sms = smsMapper.select(id);
        if(sms ==  null){
            throw new BaseCommonBizException("删除短信通知时，查询为空，请核对数据后再删除!");
        }
        //删除阿里云上的文件
        if(StringUtils.isNoneBlank(sms.getUrl())) {
            AliyunFileUploadUtils.getFileUploadObject().delFile(sms.getUrl(), smsConfigReq);
        }
        //草稿状态，删除做物理，非草稿做逻辑
        if(sms.getStatus().intValue() == SmsStatus.DRAFT.getStatus().intValue()){
            smsSendObjMapper.physicalDeleteBySmsId(id);
            smsMapper.physicalDelete(id);
        }else{
            smsSendObjMapper.deleteBySmsId(id);
            smsMapper.delete(id);
        }

        LOG.d(this, "删除短信通知结束");
    }

    public Sms queryById(Long id){
        if(id == null){
            throw new BaseCommonBizException("参数错误");
        }
        Sms sms = smsMapper.select(id);
        List<SmsSendObj> list = smsSendObjMapper.queryList(sms.getId());
        String filePhoneNumber = "";
        if(list != null && list.size() >0 ){
            sms.setSendObjList(list);
            int i = 0;
            for(SmsSendObj obj : list){
                i++;
                filePhoneNumber += obj.getSendPhone()+",";
                //只显示前50个手机号
                if(i>50){
                    break;
                }
            }
            filePhoneNumber = filePhoneNumber.substring(0, filePhoneNumber.length()-1);
        }
        sms.setFilePhoneNumber(filePhoneNumber);
        return sms;
    }

    /**
     * 根据短信id查询短信发送对象
     * @param smsId
     * @return
     */
    public List<SmsSendObj> querySendObjListBySmsId(Long smsId) {
        if(smsId == null){
            throw new BaseCommonBizException("参数错误");
        }
        List<SmsSendObj> list = smsSendObjMapper.queryList(smsId);
        return list;
    }

    public SmsVo querySmsPageListByConditionPage(SmsVo smsVo){
        List<Sms> list =smsMapper.querySmsPageListByConditionPage(smsVo);
        smsVo.setResultList(list);
        return smsVo;
    }

    /**
     * 查询待发送的短信
     * @param status 待发送状态
     * @return
     */
    public List<Sms> queryWaitSendSms(int status) {
        List<Sms> list =smsMapper.queryWaitSendSms(status);
        if(list != null && list.size() > 0){
            for(Sms sms : list){
                List<SmsSendObj> sendObjs = smsSendObjMapper.queryList(sms.getId());
                sms.setSendObjList(sendObjs);
            }
        }
        return list;
    }


    public String queryFilePath(Long id) {
        String filePath = "";
        Sms sms = smsMapper.select(id);
        if(sms != null && StringUtils.isNoneBlank(sms.getUrl())){
            filePath = sms.getUrl();
        }
        return filePath;
    }


    public void validate(Sms sms, String type){
        if(sms == null){
            throw new BaseCommonBizException("短信通知对象不能为空");
        }

        if(StringUtils.isNoneBlank(type) && type.equals("2")){
            if(sms.getId() == null){
                throw new BaseCommonBizException("短信通知主键id不能为空");
            }
        }

        if(StringUtils.isBlank(sms.getTitle())){
            throw new BaseCommonBizException("短信标题不能为空");
        }

        if(StringUtils.isBlank(sms.getContent())){
            throw new BaseCommonBizException("短信内容不能为空");
        }

        if(sms.getSmsType() == null){
            throw new BaseCommonBizException("发送类型不能为空");
        }

        if(sms.getPersonCount() == null){
            throw new BaseCommonBizException("发送人数不能为空");
        }

        if(sms.getStatus() == null){
            throw new BaseCommonBizException("状态不能为空");
        }
    }
}
