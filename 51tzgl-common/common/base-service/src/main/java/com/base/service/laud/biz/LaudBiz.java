package com.base.service.laud.biz;

import com.base.facade.exception.BaseCommonBizException;
import com.base.facade.laud.pojo.cond.LaudCond;
import com.base.facade.laud.pojo.po.Laud;
import com.base.facade.laud.pojo.vo.LaudVo;
import com.base.service.laud.dao.LaudMapper;
import com.base.spi.UserLockService;
import com.base.spi.response.CallbackResp;
import com.jucaifu.core.biz.AbsBaseBiz;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by liuxun on 16-10-12.
 */
@Service
public class LaudBiz extends AbsBaseBiz<Laud,LaudVo,LaudMapper> {

    @Autowired
    private LaudMapper laudMapper;

    @Override
    public LaudMapper getDefaultMapper() {
        return laudMapper;
    }

    private void laudCommonFilter(Laud laud){

        if (laud == null){
            throw new BaseCommonBizException("点赞对象不能空");
        }
        if (laud.getRelationId() == null){
            throw new BaseCommonBizException("点赞关联ID不能为空");
        }
        if (StringUtils.isBlank(laud.getType())){
            throw new BaseCommonBizException("点赞类型不能为空");
        }
        if (laud.getLaudUserId() == null && StringUtils.isBlank(laud.getIpAddress()) && StringUtils.isBlank(laud.getMacAddress())){
            throw new BaseCommonBizException("点赞用户不能为空");
        }
    }
    /**
     * 校验点赞
     * @param laud
     * @return
     */
    private boolean laudFilter(Laud laud){
        boolean flag = Boolean.FALSE;
        laudCommonFilter(laud);
        LaudCond cond = new LaudCond();
        if (laud.getLaudUserId() != null){
            cond.setLaudUserId(laud.getLaudUserId());
        } else if (StringUtils.isNotEmpty(laud.getMacAddress())){
            cond.setMacAddress(laud.getMacAddress());
        } else {
            cond.setIpAddress(laud.getIpAddress());
        }
        cond.setType(laud.getType());
        cond.setRelationId(laud.getRelationId());
        List<Laud> list = laudMapper.getLaudByCondition(cond);
        if (list == null || list.size() == 0){
            flag = Boolean.TRUE;
        }
        return flag;
    }

    /**
     * Save laud boolean.
     *
     * @param laud the laud
     * @return the boolean
     */
    @Transactional(rollbackFor = Exception.class,readOnly = false)
    public boolean saveLaud(Laud laud){
        boolean flag = false;
        if (laudFilter(laud)){
            laud.setCreateDate(new Date());
            int rtn = laudMapper.insert(laud);
            if (rtn > 0){
                flag = true;
            }
        }
        return flag;
    }

    /**
     * Merge laud.
     *
     * @param sourcesUserId the sources user id
     * @param targetUserId  the target user id
     */
    public void mergeLaud(Long sourcesUserId, Long targetUserId){
        laudMapper.mergeLaud(sourcesUserId,targetUserId);
    }

    public Laud getLaudByCondition(LaudCond cond){
        List<Laud> lList = laudMapper.getLaudByCondition(cond);
        if (lList != null && lList.size() > 0){
            return lList.get(0);
        }
        return null;
    }

    public Laud getLaudByCondition(Long relationId, String type, Long userId, String ipAddress,String macAddress) {
        LaudCond cond = new LaudCond();
        cond.setRelationId(relationId);
        cond.setType(type);
        if (userId != null){
            // 点赞用户ID查询
            cond.setLaudUserId(userId);
        } else if (StringUtils.isNotEmpty(macAddress)){
            cond.setMacAddress(macAddress);
        } else if (StringUtils.isNotEmpty(ipAddress)){
            cond.setIpAddress(ipAddress);
            /*String mac = MacAddressHelper.getMacAddress(ipAddress);
            if (StringUtils.isNotEmpty(mac)){
                cond.setMacAddress(mac);
            } else {

            }*/
        }
        return getLaudByCondition(cond);
    }

    public boolean cancelLaud(Laud laud){
        boolean flag = false;
        laudCommonFilter(laud);
        Laud temp = getLaudByCondition(laud.getRelationId(), laud.getType(), laud.getLaudUserId(), null, null);
        if (temp != null){
            temp.setDeleted(Boolean.TRUE);
            // 设置修改人
            temp.setModifiedUserId(laud.getLaudUserId());
            updateLaud(temp);
            flag = true;
        }
        return flag;
    }


    private int updateLaud(Laud laud){
        laud.setModifiedDate(new Date());
        return laudMapper.update(laud);
    }
}
