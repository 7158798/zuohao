package com.base.facade.info.service;

import com.base.facade.exception.BaseCommonBizException;
import com.base.facade.info.pojo.po.InfoBank;
import com.base.facade.info.pojo.po.InfoRate;
import com.base.facade.info.pojo.poex.InfoRateDetailEx;

import java.util.List;

/**
 * Created by zh on 16-8-22.
 */
public interface InfoRateFacade {
    /**
     * 查询所有银行列表
     * @return
     */
    List<InfoBank> selectInfoBankForAll() throws BaseCommonBizException;


    /**
     * 查看银行利率
     * @param rateId
     * @return
     * @throws BaseCommonBizException
     */
    List<InfoRateDetailEx> viewInfoRate(String rateId) throws BaseCommonBizException;

    /**
     * 根据银行id查询已发布的利率列表
     * @param bankId
     * @return
     * @throws BaseCommonBizException
     */
    List<InfoRate> selectInfoRateByBankId(String bankId) throws BaseCommonBizException;

    /**
     * 根据uuid获取银行信息
     * @param uuid
     * @return
     * @throws BaseCommonBizException
     */

    InfoBank getInfoBankDetail(String uuid)  throws BaseCommonBizException;

    /**
     * 查询所有存在银行利率的银行列表
     * @param type
     * @return
     * @throws BaseCommonBizException
     */
    List<InfoBank> getInfoBankForRelease(String type) throws  BaseCommonBizException;
}
