package com.szzc.core.wallet.dao;

import com.jucaifu.core.dao.BaseMapper;
import com.szzc.facade.wallet.pojo.po.PlatformBalance;
import com.szzc.facade.wallet.pojo.vo.PlatformBalanceVo;
import org.apache.ibatis.annotations.Param;

public interface PlatformBalanceMapper extends BaseMapper<PlatformBalance, PlatformBalanceVo> {

    /**
     * 根据日期判断当天是否有数据存在
     * @param date  yyyy-mm-dd格式
     * @return
     */
    Integer queryExistsByDate(@Param("date") String date);

    /**
     * 根据日期删除指定时间的数据
     * @param date yyyy-mm-dd格式
     */
    void deleteByDate(@Param("date") String date);

}