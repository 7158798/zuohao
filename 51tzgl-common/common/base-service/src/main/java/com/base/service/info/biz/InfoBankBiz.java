package com.base.service.info.biz;

import com.base.facade.exception.BaseCommonBizException;
import com.base.service.info.dao.InfoBankMapper;
import com.base.service.pool.BaseServiceBizPool;
import com.jucaifu.core.biz.AbsBaseBiz;
import com.base.facade.info.pojo.po.InfoBank;
import com.base.facade.info.pojo.po.InfoBankProduct;
import com.base.facade.info.pojo.po.InfoRate;
import com.base.facade.info.pojo.vo.InfoBankVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by zh on 16-8-22.
 */
@Service
public class InfoBankBiz extends AbsBaseBiz<InfoBank, InfoBankVo, InfoBankMapper> {

    @Autowired
    private InfoBankMapper infoBankMapper;

    @Override
    public InfoBankMapper getDefaultMapper() {
        return infoBankMapper;
    }

    /**
     * 增加银行
     *
     * @param bankName
     * @param log
     * @return
     */
    public int addInfoBank(String bankName, String log, String appLog) {
        if (StringUtils.isBlank(bankName)) {
            throw new BaseCommonBizException("银行名称不能为空");
        }
        if (StringUtils.isBlank(log)) {
            throw new BaseCommonBizException("银行log不能为空");
        }
        if (StringUtils.isBlank(appLog)) {
            throw new BaseCommonBizException("App银行log不能为空");
        }
        InfoBank bankQuery = this.getInfoBankByName(bankName);
        if (bankQuery != null) {
            throw new BaseCommonBizException("银行名称重复，不能添加");
        }
        Date now = new Date();
        InfoBank bank = new InfoBank();
        bank.setBankName(bankName);
        bank.setBankLogo(log);
        bank.setCreateDatetime(now);
        bank.setModifedDatetime(now);
        bank.setShowStatus("1");
        bank.setBankLogoApp(appLog);

        return this.insert(bank);
    }

    /**
     * 编辑银行
     *
     * @param uuid
     * @param bankName
     * @param log
     * @return
     */
    public int editInfoBank(String uuid, String bankName, String log, String appLog) {
        if (StringUtils.isBlank(bankName)) {
            throw new BaseCommonBizException("银行名称不能为空");
        }
        if (StringUtils.isBlank(log)) {
            throw new BaseCommonBizException("银行log不能为空");
        }
        if (StringUtils.isBlank(uuid)) {
            throw new BaseCommonBizException("银行id不能为空");
        }
        if (StringUtils.isBlank(appLog)) {
            throw new BaseCommonBizException("app银行id不能为空");
        }
        InfoBank bank = this.select(uuid);
        if (bank == null) {
            throw new BaseCommonBizException("银行信息不存在");
        }
        if (!bankName.equals(bank.getBankName())) {
            InfoBank bankQuery = this.getInfoBankByName(bankName);
            if (bankQuery != null) {
                throw new BaseCommonBizException("银行名称重复，不能修改");
            }
        }
        Date now = new Date();
        bank.setBankName(bankName);
        bank.setBankLogo(log);
        bank.setModifedDatetime(now);
        bank.setBankLogoApp(appLog);
        return this.update(bank);
    }

    /**
     * 删除银行信息
     *
     * @param uuid
     */
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public void deleteBank(String uuid) {
        if (StringUtils.isBlank(uuid)) {
            throw new BaseCommonBizException("银行id不能为空");
        }
        InfoBank bank = this.select(uuid);
        if (bank == null) {
            throw new BaseCommonBizException("银行信息不存在");
        }
        List<InfoRate> list = BaseServiceBizPool.getInstance().infoRateBiz.selectByBankId(uuid);
        if (list.size() > 0) {
            throw new BaseCommonBizException("该银行信息已被利率表使用，不能删除");
        }
        List<InfoBankProduct> productList = BaseServiceBizPool.getInstance().infoBankProductBiz.getByBankId(uuid);
        if (productList.size() > 0) {
            throw new BaseCommonBizException("该银行信息已被理财产品使用，不能删除");
        }
        //删除银行
        this.delete(uuid);
    }

    /**
     * 分页查询银行信息
     *
     * @param vo
     * @return
     */
    public InfoBankVo selectInfoBankByConditionPage(InfoBankVo vo) {
        List<InfoBank> list = infoBankMapper.getByConditionPage(vo);
        vo.setResultList(list);
        return vo;
    }

    /**
     * 设置银行状态
     *
     * @param bankId
     * @param status
     */
    public void setInfoBankShowStatus(String bankId, String status) {
        if (StringUtils.isBlank(bankId)) {
            throw new BaseCommonBizException("银行id不存在");
        }
        if (StringUtils.isBlank(status)) {
            throw new BaseCommonBizException("状态不能为空");
        }
        InfoBank bank = this.select(bankId);
        if (bank == null) {
            throw new BaseCommonBizException("银行信息不存在");
        }
        bank.setShowStatus(status);
        infoBankMapper.update(bank);
    }

    /**
     * 通过银行名称获取银行信息
     *
     * @param bankName
     * @return
     */
    public InfoBank getInfoBankByName(String bankName) {
        if (StringUtils.isBlank(bankName)) {
            throw new BaseCommonBizException("银行名称不能为空");
        }
        return infoBankMapper.selectByName(bankName);
    }

    /**
     * 查询所有存在银行利率的银行列表
     *
     * @param type
     * @return
     * @throws BaseCommonBizException
     */
    public List<InfoBank> getInfoBankForRelease(String type) {
        return infoBankMapper.getInfoBankForRelease(type);
    }
}
