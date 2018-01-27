package com.otc.service.advertisement.dao;

import com.jucaifu.core.dao.BaseMapper;
import com.otc.facade.advertisement.pojo.po.Advertisement;
import com.otc.facade.advertisement.pojo.po.TransactionDes;
import com.otc.facade.advertisement.pojo.vo.AdvertisementVo;
import com.otc.facade.advertisement.pojo.vo.TransactionDesVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TransactionDesMapper extends BaseMapper<TransactionDes, TransactionDesVo> {
    /**
     * 通过类别获取
     * @param type
     * @return
     */
    TransactionDes getByTypeAndId(@Param("id") Long id, @Param("type") Integer type);

    List<TransactionDes> queryCountByConditionPage(TransactionDesVo vo);
}