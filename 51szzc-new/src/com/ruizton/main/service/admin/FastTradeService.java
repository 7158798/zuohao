package com.ruizton.main.service.admin;

import com.ruizton.main.dao.zuohao.FastTradeDao;
import com.ruizton.main.model.AutoOrder;
import com.ruizton.main.model.FastTrade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lx on 17-3-7.
 */
@Service
public class FastTradeService {

    @Autowired
    private FastTradeDao fastTradeDao;

    public List<FastTrade> list(int firstResult, int maxResults,
                               String filter, boolean isFY) {
        List<FastTrade> list = this.fastTradeDao.list(firstResult, maxResults, filter,isFY);
        for (FastTrade fastTrade : list){
            fastTrade.getFtrademapping().getFvirtualcointypeByFvirtualcointype2().getFname();
            fastTrade.getUser().getFloginName();
        }
        return list;
    }

    public FastTrade findById(int fid) {
        FastTrade fastTrade = this.fastTradeDao.findById(fid);
        fastTrade.getFtrademapping().getFvirtualcointypeByFvirtualcointype2().getFname();
        fastTrade.getUser().getFloginName();
        fastTrade.getUser().getFid();
        return fastTrade;
    }

    public void save(FastTrade transientInstance) {
        this.fastTradeDao.save(transientInstance);
    }

    public void update(FastTrade instance) {
        this.fastTradeDao.attachDirty(instance);
    }


    public boolean isCoinTypeExists(int coinTypeId) throws Exception{
        boolean flag = false ;
        List<FastTrade> list = this.fastTradeDao.findByProperty("ftrademapping.fid", coinTypeId) ;
        flag = list.size()>0 ;
        return flag ;
    }

    public List<FastTrade> findByProperty(String name, Object value) {
        return this.fastTradeDao.findByProperty(name, value);
    }

}
