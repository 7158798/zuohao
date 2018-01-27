package com.base.facade.info.service;

import com.base.facade.info.pojo.vo.InfoBankProductVo;

import java.util.Date;

/**
 * Created by zh on 16-9-21.
 */
public interface InfoBankProductFacade {

    /**
     * 分页查询银行产品列表
     * @param vo
     * @return
     */
    InfoBankProductVo getProductByConditionPage(InfoBankProductVo vo);

    /**
     * 获取最大的发布时间
     * @return
     */
    Date getProductMaxPushDate();
}
