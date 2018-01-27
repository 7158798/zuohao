package com.otc.service.user.dao;

import com.jucaifu.core.dao.BaseMapper;
import com.otc.facade.user.pojo.po.UserCreditRecord;
import com.otc.facade.user.pojo.vo.UserCreditRecordVo;

public interface UserCreditRecordMapper extends BaseMapper<UserCreditRecord,UserCreditRecordVo> {
    /**
     * 通过用户id查询
     * @param userId
     * @return
     */
    UserCreditRecord getByUserId(Long userId);
}