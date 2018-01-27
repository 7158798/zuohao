package com.otc.service.virtual.dao;

import com.jucaifu.core.dao.BaseMapper;
import com.otc.facade.virtual.pojo.bean.PoolBean;
import com.otc.facade.virtual.pojo.po.Pool;
import com.otc.facade.virtual.pojo.vo.PoolVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PoolMapper extends BaseMapper<Pool,PoolVo> {


    int saveList(@Param("list")List<Pool> list);

    Pool queryOnePool(@Param("coinId") Long coinId);

    List<PoolBean> queryCountByConditionPage(PoolVo vo);
}