package com.otc.service.advertisement.biz;

import com.jucaifu.core.biz.AbsBaseBiz;
import com.otc.facade.advertisement.exceptions.AdvertisementBizException;
import com.otc.facade.advertisement.exceptions.TransactionDesBizException;
import com.otc.facade.advertisement.pojo.po.Advertisement;
import com.otc.facade.advertisement.pojo.po.TransactionDes;
import com.otc.facade.advertisement.pojo.vo.AdvertisementVo;
import com.otc.facade.advertisement.pojo.vo.TransactionDesVo;
import com.otc.service.advertisement.dao.TransactionDesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by zygong on 17-4-27.
 */
@Service
@Transactional
public class TransactionDesBiz extends AbsBaseBiz<TransactionDes,TransactionDesVo,TransactionDesMapper> {
    @Autowired
    private TransactionDesMapper transactionDesMapper;

    @Override
    public TransactionDesMapper getDefaultMapper() {
        return transactionDesMapper;
    }

    public boolean save(TransactionDes transactionDes) {
        boolean flag = false;
        if (transactionDes == null) {
            throw new TransactionDesBizException("保存数据为空");
        }
        TransactionDes byType = transactionDesMapper.getByTypeAndId(null, transactionDes.getType());
        if(byType != null){
            throw new TransactionDesBizException("已存在该类型数据");
        }
        int insert = transactionDesMapper.insert(transactionDes);
        if (insert != 0) {
            flag = true;
        }
        return flag;
    }

    public int update(TransactionDes transactionDes){
        int insert = 0;
        if (transactionDes == null) {
            throw new TransactionDesBizException("保存数据为空");
        }
        TransactionDes byType = transactionDesMapper.getByTypeAndId(null, transactionDes.getType());
        if(byType != null && byType.getId() != transactionDes.getId()){
            throw new TransactionDesBizException("已存在该类型数据");
        }
        insert = transactionDesMapper.update(transactionDes);

        return insert;
    }

    public TransactionDes getByTypeAndId(Long id, Integer type){
        return transactionDesMapper.getByTypeAndId(id, type);
    }

    public TransactionDesVo queryCountByConditionPage(TransactionDesVo vo){
        List<TransactionDes> transactionDes = transactionDesMapper.queryCountByConditionPage(vo);
        if(transactionDes != null){
            vo.setResultList(transactionDes);
        }
        return vo;
    }
}
