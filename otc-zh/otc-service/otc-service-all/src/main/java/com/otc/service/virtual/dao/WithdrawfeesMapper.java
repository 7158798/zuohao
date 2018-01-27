package com.otc.service.virtual.dao;

import com.jucaifu.core.dao.BaseMapper;
import com.otc.facade.virtual.pojo.po.Withdrawfees;
import com.otc.facade.virtual.pojo.vo.WithdrawfeesVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WithdrawfeesMapper extends BaseMapper<Withdrawfees, WithdrawfeesVo> {

    /**
     *  增加提现手续费配置
     * @param list
     * @return
     */
    int insertList(List<Withdrawfees> list);

    /**
     * 删除
     * @param coinId
     * @return
     */
    int deleteByCoinId(@Param("coinId") Long coinId);

    /**
     * 通过币种id获取
     * @param coinId
     * @return
     */
    List<Withdrawfees> selectByConId(@Param("coinId") Long coinId);
}