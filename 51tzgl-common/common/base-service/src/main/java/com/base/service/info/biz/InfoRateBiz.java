package com.base.service.info.biz;

import com.base.facade.exception.BaseCommonBizException;
import com.base.service.info.dao.InfoRateMapper;
import com.base.service.info.utils.InfoImportExcelUtil;
import com.base.service.pool.BaseServiceBizPool;
import com.jucaifu.common.dfs.FastDFSHelper;
import com.jucaifu.common.log.LOG;
import com.jucaifu.common.poi.WDWUtil;
import com.jucaifu.core.biz.AbsBaseBiz;
import com.base.facade.info.enums.InfoRateGainWay;
import com.base.facade.info.enums.InfoRateStatus;
import com.base.facade.info.pojo.bean.InfoRateBean;
import com.base.facade.info.pojo.po.InfoBank;
import com.base.facade.info.pojo.po.InfoRate;
import com.base.facade.info.pojo.po.InfoRateDetail;
import com.base.facade.info.pojo.poex.InfoRateExcelEx;
import com.base.facade.info.pojo.vo.InfoRateVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zh on 16-8-22.
 */
@Service
public class InfoRateBiz extends AbsBaseBiz<InfoRate, InfoRateVo, InfoRateMapper> {
    @Autowired
    private InfoRateMapper infoRateMapper;

    @Override
    public InfoRateMapper getDefaultMapper() {
        return infoRateMapper;
    }

    /**
     * 新增银行利率
     *
     * @param info
     * @param details
     */
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public void addInfoRate(InfoRate info, List<InfoRateDetail> details) {
        LOG.dStart(this, "新增银行利率开始");
        //验证主表参数
        validItem(info);
        if (details == null || details.size() == 0) {
            throw new BaseCommonBizException("利率明细不能为空");
        }
        String status = info.getStatus();
        if(StringUtils.isBlank(status)){
            throw new BaseCommonBizException("保存状态不能为空");
        }
        //detail 重复验证
        if (!validDetailsRepeat(details)) {
            throw new BaseCommonBizException("利率存在重复，请检查");
        }


        Date now = new Date();
        info.setCreateDatetime(now);
        info.setModifedDatetime(now);
        info.setDeleted(false);
        if(StringUtils.equals(status,InfoRateStatus.FINISH_RELEASE.getCode())){
            info.setStatus(InfoRateStatus.INITIAL_RELEASE.getCode());
        }
        this.insert(info);

        String uuid = info.getUuid();
        String type = info.getType();

        for (InfoRateDetail detail : details) {
            String itemFisrt = detail.getItemFisrt();
            String itemSecond = detail.getItemSecond();
            String period = detail.getPeriod();
            String rate = detail.getRate();

            BaseServiceBizPool.getInstance().infoRateDetailBiz.addInfoRateDetail(uuid, type, itemFisrt, itemSecond,
                    period, rate);
        }
        if(StringUtils.equals(status,InfoRateStatus.FINISH_RELEASE.getCode())){
            this.updateInfoRateStatus(uuid,status);
        }
        LOG.dEnd(this, "新增银行利率结束");
    }

    public List<InfoRate> selectInfoRateByConditionPage(InfoRateVo vo) {
        return infoRateMapper.selectInfoRateByConditionPage(vo);

    }


    /**
     * 修改银行利率
     *
     * @param bankRateId
     * @param detailLis
     */
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public void editInfoRate(String bankRateId, List<InfoRateDetail> detailLis) {
        LOG.dStart(this, "修改银行利率开始");
        if (StringUtils.isBlank(bankRateId)) {
            throw new BaseCommonBizException("银行利率id不能为空");
        }
        if (detailLis == null || detailLis.size() == 0) {
            throw new BaseCommonBizException("银行利率不能为空");
        }
        //detail 重复验证
        if (!validDetailsRepeat(detailLis)) {
            throw new BaseCommonBizException("银行利率存在重复，请检查");
        }


        InfoRate bankRate = infoRateMapper.select(bankRateId);
        if (bankRate == null) {
            throw new BaseCommonBizException("银行利率记录不存在");
        }
        if (!InfoRateStatus.INITIAL_RELEASE.getCode().equals(bankRate.getStatus()) &&
                !InfoRateStatus.READY_RELEASE.getCode().equals(bankRate.getStatus())) {
            throw new BaseCommonBizException("只有初始和待发布状态才可以修改");
        }

        Date now = new Date();
        String type = bankRate.getType();
        //删除已有记录，重新insert
        BaseServiceBizPool.getInstance().infoRateDetailBiz.delete(bankRateId);

        for (InfoRateDetail detail : detailLis) {
            String itemFisrt = detail.getItemFisrt();
            String itemSecond = detail.getItemSecond();
            String period = detail.getPeriod();
            String rate = detail.getRate();

            BaseServiceBizPool.getInstance().infoRateDetailBiz.addInfoRateDetail(bankRateId, type, itemFisrt, itemSecond,
                    period, rate);
        }
        LOG.dEnd(this, "修改银行利率结束");
    }

    /**
     * 删除银行利率
     *
     * @param rateId
     */
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public void deleteInfoRate(String rateId) {
        LOG.dStart(this, "删除银行利率开始");
        if (StringUtils.isBlank(rateId)) {
            throw new BaseCommonBizException("银行利率id不能为空");
        }
        InfoRate infoRate = infoRateMapper.select(rateId);
        if(infoRate == null){
            throw new BaseCommonBizException("银行利率不存在");
        }

        BaseServiceBizPool.getInstance().infoRateBiz.delete(rateId);
        BaseServiceBizPool.getInstance().infoRateDetailBiz.delete(rateId);
        LOG.dEnd(this, "删除银行利率结束");
    }

    /**
     * 修改状态
     *
     * @param rateId
     * @param status
     */
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public void updateInfoRateStatus(String rateId, String status) {
        LOG.dStart(this, "开始修改银行利率状态");
        if (StringUtils.isBlank(rateId)) {
            throw new BaseCommonBizException("银行利率ID不能为空");
        }
        if (StringUtils.isBlank(status)) {
            throw new BaseCommonBizException("状态值不能为空");
        }
        InfoRate infoRate = infoRateMapper.select(rateId);
        if (infoRate == null) {
            throw new BaseCommonBizException("银行利率信息不存在");
        }
        String oldStatus = infoRate.getStatus();
        Date now = new Date();

        if (status.equals(InfoRateStatus.FINISH_RELEASE.getCode())) {
            if (!oldStatus.equals(InfoRateStatus.INITIAL_RELEASE.getCode()) &&
                    !oldStatus.equals(InfoRateStatus.READY_RELEASE.getCode())) {
                throw new BaseCommonBizException("只有初始或待发布状态才可以发布");
            }
        } else if (status.equals(InfoRateStatus.READY_RELEASE.getCode())) {
            if (!oldStatus.equals(InfoRateStatus.FINISH_RELEASE.getCode())) {
                throw new BaseCommonBizException("只有发布状态才可以取消发布");
            }
        } else {
            throw new BaseCommonBizException("银行利率状态不正确");
        }

        //发布同时需要修改其他状态为失效
        if (status.equals(InfoRateStatus.FINISH_RELEASE.getCode())) {
            String bankId = infoRate.getBankId();
            String type = infoRate.getType();
            List<InfoRate> list = infoRateMapper.selectByReleased(rateId, bankId, type, InfoRateStatus.FINISH_RELEASE.getCode());
            for (InfoRate rate : list) {
                rate.setStatus(InfoRateStatus.LOSE_VALID.getCode());
                infoRateMapper.update(rate);
            }
            infoRate.setPushTime(now);
        }
        //修改状态
        infoRate.setStatus(status);
        infoRate.setModifedDatetime(now);

        infoRateMapper.update(infoRate);
        LOG.dEnd(this, "结束修改银行利率状态");
    }

    /**
     * 批量发布
     *
     * @param list
     */
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public void pushBatchInfoRate(List<String> list) {
        LOG.dStart(this, "批量发布银行利率开始");
        if (list == null || list.size() == 0) {
            throw new BaseCommonBizException("发布批次不能为空");
        }
        for (String uuid : list) {
            updateInfoRateStatus(uuid, InfoRateStatus.FINISH_RELEASE.getCode());
        }
        LOG.dEnd(this, "批量发布银行利率结束");
    }

    /**
     * 根据银行id查询利率
     *
     * @param bankId
     * @return
     */
    public List<InfoRate> selectByBankId(String bankId) {
        if (StringUtils.isBlank(bankId)) {
            throw new BaseCommonBizException("银行id不能为空");
        }
        InfoRateBean bean = new InfoRateBean();
        bean.setBankId(bankId);
        return infoRateMapper.selectByCondition(bean);
    }

    /**
     * 根据银行id查询已发布的利率
     *
     * @param bankId
     * @return
     */
    public List<InfoRate> selectByBankIdAndFinish(String bankId) {
        if (StringUtils.isBlank(bankId)) {
            throw new BaseCommonBizException("银行id不能为空");
        }
        InfoRateBean bean = new InfoRateBean();
        bean.setBankId(bankId);
        bean.setStatus(InfoRateStatus.FINISH_RELEASE.getCode());
        return infoRateMapper.selectByCondition(bean);
    }

    /**
     * 清理银行利率
     * @param clearDate
     */
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public void clearInfoRate(Date clearDate){
       if(clearDate == null){
           throw new BaseCommonBizException("清理截至日期不能为空");
       }
        InfoRateBean bean = new InfoRateBean();
        bean.setCreateDate(clearDate);
        bean.setStatus(InfoRateStatus.READY_RELEASE.getCode());
        List<InfoRate> lists = infoRateMapper.selectByCondition(bean);
        for(InfoRate info:lists){
            this.deleteInfoRate(info.getUuid());
        }
    }


    /**
     * 解析上传的银行利率
     *
     * @param bytes
     */
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public void parseUploadFile(byte[] bytes, String url) {
        InputStream inputStream = new ByteArrayInputStream(bytes);
        List<InfoRateExcelEx> listExcel = InfoImportExcelUtil.importStreamExcelForInfoRate(inputStream, WDWUtil.isExcel2003(url));
        if (listExcel == null || listExcel.size() == 0) {
            throw new BaseCommonBizException("解析列表为空");
        }
        InfoRate infoRate;
        String batchNo = listExcel.get(0).getBatchNo();
        if (StringUtils.isBlank(batchNo)) {
            throw new BaseCommonBizException("解析到的批次号不存在");
        }
        InfoRateBean bean = new InfoRateBean();
        bean.setBatchNo(batchNo);
        List<InfoRate> list = infoRateMapper.selectByCondition(bean);
        if (list.size() != 0) {
            throw new BaseCommonBizException("该批次号重复导入");
        }
        for (InfoRateExcelEx excel : listExcel) {
            String bankName = excel.getBankName();
            InfoBank bank = BaseServiceBizPool.getInstance().infoBankBiz.getInfoBankByName(bankName);
            if (bank == null) {
                throw new BaseCommonBizException("未找到对应的银行：" + bankName);
            }
            infoRate = new InfoRate();
            infoRate.setBankId(bank.getUuid());
            infoRate.setStatus(InfoRateStatus.INITIAL_RELEASE.getCode());
            infoRate.setType(excel.getType());
            infoRate.setBatchNo(batchNo);
            infoRate.setGainWay(InfoRateGainWay.IINSERT_BY_FILE.getCode());

            List<InfoRateDetail> details = excel.getDetailList();
            if (details == null || details.size() == 0) {
                throw new BaseCommonBizException(bankName + "对应的利率明细不存在");
            }
            this.addInfoRate(infoRate, details);
        }
    }

    /**
     * 查询所有未发布的批次
     *
     * @return
     */
    public List<InfoRate> selectAllBatch() {
        return infoRateMapper.selectInfoRateAllBatch();
    }


    /**
     * 主表请求参数验证
     *
     * @param info
     */
    private void validItem(InfoRate info) {
        if (info == null) {
            throw new BaseCommonBizException("请求参数不能为空");
        }
        if (StringUtils.isBlank(info.getBankId())) {
            throw new BaseCommonBizException("银行id不能为空");
        }
        if (StringUtils.isBlank(info.getType())) {
            throw new BaseCommonBizException("类型不能为空");
        }
        if (StringUtils.isBlank(info.getGainWay())) {
            throw new BaseCommonBizException("获取方式不能为空");
        } else if (info.getGainWay().equals(InfoRateGainWay.IINSERT_BY_FILE.getCode())) {
            if (StringUtils.isBlank(info.getBatchNo())) {
                throw new BaseCommonBizException("批次号不能为空");
            }
        }
    }

    /**
     * 明细表重复性验证
     *
     * @param lists
     */
    private Boolean validDetailsRepeat(List<InfoRateDetail> lists) {
        List<String> tempList = new ArrayList<>();
        StringBuffer buffer;
        for (InfoRateDetail detail : lists) {
            buffer = new StringBuffer();
            String item1 = detail.getItemFisrt();
            String item2 = detail.getItemSecond();
            String period = detail.getPeriod();

            if (StringUtils.isNotBlank(item1)) {
                buffer.append(item1.trim());
            }
            if (StringUtils.isNotBlank(item2)) {
                buffer.append(item2.trim());
            }
            if (StringUtils.isNotBlank(period)) {
                buffer.append(period.trim());
            }
            String item = buffer.toString();
            if (tempList.indexOf(item) >= 0) {
                return false;
            } else {
                tempList.add(item);
            }
        }
        return true;
    }
}
