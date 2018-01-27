package com.base.service.info.biz;

import com.base.facade.exception.BaseCommonBizException;
import com.base.facade.info.pojo.po.VirtualCurrency;
import com.base.facade.info.pojo.vo.VirtualCurrencyVo;
import com.base.service.info.dao.VirtualCurrencyMapper;
import com.base.service.pool.BaseServiceBizPool;
import com.jucaifu.common.log.LOG;
import com.jucaifu.core.biz.AbsBaseBiz;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author luwei
 * @Date 11/8/16 3:05 PM
 */
@Service
@Transactional
public class VirtualCurrencyBiz extends AbsBaseBiz<VirtualCurrency, VirtualCurrencyVo, VirtualCurrencyMapper>{

    @Autowired
    private VirtualCurrencyMapper virtualCurrencyMapper;

    public VirtualCurrencyMapper getDefaultMapper() {
        return virtualCurrencyMapper;
    }


    /**
     * 分页查询虚拟货币信息
     * @param vo
     * @return
     */
    public VirtualCurrencyVo queryCncyPageListByConditionPage(VirtualCurrencyVo vo) {
        LOG.dStart(this, "分页查询虚拟货币信息开始");
        List<VirtualCurrency> list = virtualCurrencyMapper.queryCncyPageListByConditionPage(vo);
        vo.setResultList(list);
        LOG.dEnd(this, "分页查询虚拟货币信息结束");
        return vo;
    }

    /**
     * 查询所有的币种
     * @return
     */
    public List<VirtualCurrency> queryAllCncy() {
        return virtualCurrencyMapper.queryAllCncy();
    }

    /**
     * 新增虚拟货币
     * @param virtualCurrency
     */
    public void addCncy(VirtualCurrency virtualCurrency) {
        LOG.dStart(this, "新增虚拟货币开始");
        validate(virtualCurrency, "1");
        virtualCurrency.setStatus(1);
        virtualCurrency.setDeleted(Boolean.FALSE);
        Date currentDate = new Date();
        virtualCurrency.setCreateDate(currentDate);
        virtualCurrency.setModifiedDate(currentDate);
        virtualCurrencyMapper.insert(virtualCurrency);
        LOG.dEnd(this, "新增虚拟货币结束");
    }

    /**
     * 修改
     * @param virtualCurrency
     */
    public void updateCncy(VirtualCurrency virtualCurrency) {
        LOG.dStart(this, "修改虚拟货币开始");
        validate(virtualCurrency, "2");
        //根据id查询
        VirtualCurrency vo = virtualCurrencyMapper.select(virtualCurrency.getId());
        if(vo == null) {
            throw new BaseCommonBizException("修改虚拟货币时，查询为空，产生脏读，请核对数据后再查询");
        }

        vo.setCncyCnName(virtualCurrency.getCncyCnName());
        vo.setCncyEnName(virtualCurrency.getCncyEnName());
        vo.setCncyEnShortName(virtualCurrency.getCncyEnShortName());
        vo.setCreator(virtualCurrency.getCreator());
        vo.setCoreAlgorithm(virtualCurrency.getCoreAlgorithm());
        vo.setReleaseDate(virtualCurrency.getReleaseDate());
        vo.setReleaseNum(virtualCurrency.getReleaseNum());
        vo.setStock(virtualCurrency.getStock());
        vo.setProofMethod(virtualCurrency.getProofMethod());
        vo.setDifficultyAdjust(virtualCurrency.getDifficultyAdjust());
        vo.setBlockSpeed(virtualCurrency.getBlockSpeed());
        vo.setBlockReward(virtualCurrency.getBlockReward());
        vo.setMarketCode(virtualCurrency.getMarketCode());
        vo.setCncyDesc(virtualCurrency.getCncyDesc());
        vo.setFeatures(virtualCurrency.getFeatures());
        vo.setShortcomming(virtualCurrency.getShortcomming());
        vo.setCncyIconUrl(virtualCurrency.getCncyIconUrl());
        vo.setModifiedUserId(virtualCurrency.getModifiedUserId());
        vo.setModifiedDate(new Date());
        virtualCurrencyMapper.update(vo);
        LOG.dEnd(this, "修改虚拟货币结束");
    }

    /**
     * 修改状态
     * @param id
     * @param status
     */
    public void updateStatus(Long id, Integer status) {
        LOG.dStart(this, "修改虚拟货币状态开始");
        if(id == null){
            throw new BaseCommonBizException("修改状态时，参数错误，id不能为空");
        }
        if(status == null){
            throw new BaseCommonBizException("修改状态时，参数错误，id不能为空");
        }
        //根据id查询对象
        VirtualCurrency vo = virtualCurrencyMapper.select(id);
        if(vo == null){
            throw new BaseCommonBizException("根据id查询虚拟货币时，查询为空，产生脏读，请核对数据后再查询");
        }

        vo.setStatus(status);
        virtualCurrencyMapper.update(vo);
        LOG.dEnd(this, "修改虚拟货币状态结束");
    }

    /**
     * 添加时，判断市场代码是否存在
     * @param marketCode 市场代码
     * @return
     */
    public Integer queryExistsCodeByAdd(String marketCode) {
        return virtualCurrencyMapper.queryExistsCodeByAdd(marketCode);
    }

    /**
     * 修改时，判断市场代码是否存在
     * @param marketCode 市场代码
     * @param id 主键id流水号
     * @return
     */
    public Integer queryExistsCodeByUpdate(String marketCode, Long id) {
        return virtualCurrencyMapper.queryExistsCodeByUpdate(marketCode, id);
    }


    /**
     * 根据市场代码查询虚拟货币信息
     * @param marketCode
     * @return
     */
    public VirtualCurrency selectByMarketCode(String marketCode) {
        return virtualCurrencyMapper.selectByMarketCode(marketCode);
    }

    public void validate(VirtualCurrency virtualCurrency, String type){
        if(virtualCurrency == null){
            throw new BaseCommonBizException("参数错误，虚拟货币对象不能为空");
        }

        if(StringUtils.isNoneBlank(type) && type.equals("2")) {
            if(virtualCurrency.getId() == null) {
                throw new BaseCommonBizException("id不能为空");
            }
        }

        if(StringUtils.isBlank(virtualCurrency.getCncyEnName())) {
            throw new BaseCommonBizException("英文名不能为空");
        }

        if(StringUtils.isBlank(virtualCurrency.getCncyEnShortName())) {
            throw new BaseCommonBizException("英文简称不能为空");
        }

        if(StringUtils.isBlank(virtualCurrency.getMarketCode())) {
            throw new BaseCommonBizException("市场代码不能为空");
        }

        if(StringUtils.isBlank(virtualCurrency.getCncyIconUrl())) {
            throw new BaseCommonBizException("货币图标不能为空");
        }
    }

}
