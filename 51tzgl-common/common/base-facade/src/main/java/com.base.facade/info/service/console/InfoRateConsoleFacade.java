package com.base.facade.info.service.console;

import com.base.facade.exception.BaseCommonBizException;
import com.base.facade.info.pojo.po.InfoBank;
import com.base.facade.info.pojo.po.InfoRate;
import com.base.facade.info.pojo.po.InfoRateDetail;
import com.base.facade.info.pojo.vo.InfoBankVo;
import com.base.facade.info.pojo.vo.InfoRateVo;
import com.base.facade.info.service.InfoRateFacade;

import java.util.Date;
import java.util.List;

/**
 * Created by zh on 16-8-22.
 */
public interface InfoRateConsoleFacade extends InfoRateFacade {
    /**
     * 增加银行
     * @param bankName
     * @param log
     * @return
     * @throws BaseCommonBizException
     */
    int addInfoBank(String bankName, String log, String appLog) throws BaseCommonBizException;

    /**
     * 修改银行信息
     * @param uuid
     * @param bankName
     * @param log
     * @return
     * @throws BaseCommonBizException
     */
    int editInfoBank(String uuid, String bankName, String log, String appLog) throws BaseCommonBizException;

    /**
     * 删除银行信息
     * @param uuid
     * @return
     * @throws BaseCommonBizException
     */
    void dropInfoBank(String uuid) throws BaseCommonBizException;

    /**
     * 分页查询银行信息
     * @param vo
     * @return
     * @throws BaseCommonBizException
     */
    InfoBankVo selectInfoBankByConditionPage(InfoBankVo vo) throws BaseCommonBizException;


    /**
     * 查询所有银行
     * @return
     * @throws BaseCommonBizException
     */
    List<InfoBank> selectAllInfoBankList() throws BaseCommonBizException;


    void setInfoBankShowStatus(String uuid, String status) throws BaseCommonBizException;



    /**
     * 增加银行利率
     * @param rate
     * @param details
     * @return
     * @throws BaseCommonBizException
     */
    void addInfoRate(InfoRate rate, List<InfoRateDetail> details) throws BaseCommonBizException;

    /**
     * 修改银行利率
     * @param rateId
     * @param details
     * @return
     * @throws BaseCommonBizException
     */

    void editInfoRate(String rateId, List<InfoRateDetail> details) throws BaseCommonBizException;



    /**
     * 删除银行利率
     * @param rateId
     * @return
     * @throws BaseCommonBizException
     */
    void deleteInfoRate(String rateId) throws BaseCommonBizException;

    /**
     * 修改银行利率发布状态
     * @param rateId
     * @param status
     * @throws BaseCommonBizException
     */
    void updateInfoRateStatus(String rateId, String status) throws BaseCommonBizException;

    /**
     * 批量发布银行利率
     * @param list
     * @throws BaseCommonBizException
     */
    void pushBatchInfoRate(List<String> list) throws BaseCommonBizException;

    /**
     * 通过id查询银行利率
     * @param uuid
     * @return
     * @throws BaseCommonBizException
     */
    InfoRate selectInfoRateByUuid(String uuid) throws BaseCommonBizException;


    /**
     * 分页查询银行利率
     * @param vo
     * @return
     * @throws BaseCommonBizException
     */
    InfoRateVo selectInfoRateByConditionPage(InfoRateVo vo) throws BaseCommonBizException;


    /**
     * 解析上传的银行利率文件
     * @param bytes
     */
    void parseUploadFile(byte[] bytes, String url);


    /**
     * 查询所有未发布的批次
     * @return
     * @throws BaseCommonBizException
     */
    List<InfoRate> selectInfoRateAllBatch() throws BaseCommonBizException;


    void clearInfoRateRecord(Date clearDate) throws BaseCommonBizException;
}
