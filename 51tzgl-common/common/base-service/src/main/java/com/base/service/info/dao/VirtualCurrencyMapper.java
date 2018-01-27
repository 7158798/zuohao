package com.base.service.info.dao;

import com.base.facade.info.pojo.po.VirtualCurrency;
import com.base.facade.info.pojo.vo.VirtualCurrencyVo;
import com.jucaifu.core.dao.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author luwei
 * @Date 11/8/16 3:04 PM
 */
public interface VirtualCurrencyMapper extends BaseMapper<VirtualCurrency, VirtualCurrencyVo> {


    /**
     * 分页查询虚拟货币信息
     * @param vo
     * @return
     */
    List<VirtualCurrency> queryCncyPageListByConditionPage(VirtualCurrencyVo vo);

    /**
     * 查询所有的币种
     * @return
     */
    List<VirtualCurrency> queryAllCncy();

    /**
     * 添加时，判断市场代码是否存在
     * @param marketCode 市场代码
     * @return
     */
    Integer queryExistsCodeByAdd(String marketCode);

    /**
     * 修改时，判断市场代码是否存在
     * @param marketCode 市场代码
     * @param id 主键id流水号
     * @return
     */
    Integer queryExistsCodeByUpdate(@Param("marketCode") String marketCode, @Param("id") Long id);

    /**
     * 根据市场代码查询虚拟货币信息
     * @param marketCode
     * @return
     */
    VirtualCurrency selectByMarketCode(String marketCode);
}
