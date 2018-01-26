package com.ruizton.main.service.admin;

import com.ruizton.main.dao.FtrademappingDAO;
import com.ruizton.main.dao.zuohao.TradeSetDao;
import com.ruizton.main.model.AutoOrder;
import com.ruizton.main.model.Ftrademapping;
import com.ruizton.main.model.TradeSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by luwei on 17-2-14.
 */
@Service
public class TradeSetService {

    @Autowired
    private TradeSetDao tradeSetDao;

    @Autowired
    private FtrademappingDAO trademappingDAO;

    public List<Ftrademapping> findAllTrademapping() {
        List<Ftrademapping> list = this.trademappingDAO.findAll();
        for(Ftrademapping ftrademapping : list) {
            ftrademapping.getFvirtualcointypeByFvirtualcointype2().getFname();
        }
        return list;
    }

    public List<TradeSet> list(int firstResult, int maxResults,
                               String filter, boolean isFY) {
        List<TradeSet> list = this.tradeSetDao.list(firstResult, maxResults, filter,isFY);
        for (TradeSet tradeSet : list){
            tradeSet.getFtrademapping().getFvirtualcointypeByFvirtualcointype2().getFname();
            tradeSet.getFuser().getFloginName();
        }
        return list;
    }

    public TradeSet findById(int fid) {
        TradeSet tradeSet = this.tradeSetDao.findById(fid);
        tradeSet.getFtrademapping().getFvirtualcointypeByFvirtualcointype2().getFname();
        tradeSet.getFuser().getFloginName();
        tradeSet.getFuser().getFid();
        return tradeSet;
    }

    public void save(TradeSet transientInstance) {
        this.tradeSetDao.save(transientInstance);
    }

    public void update(TradeSet instance) {
        this.tradeSetDao.attachDirty(instance);
    }


    public boolean isCoinTypeExists(int coinTypeId) throws Exception{
        boolean flag = false ;
        List<AutoOrder> list = this.tradeSetDao.findByProperty("ftrademapping.fid", coinTypeId) ;
        flag = list.size()>0 ;
        return flag ;
    }

    public List<TradeSet> findByProperty(String name, Object value) {
        return this.tradeSetDao.findByProperty(name, value);
    }

}
