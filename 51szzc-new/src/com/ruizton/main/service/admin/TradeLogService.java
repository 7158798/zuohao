package com.ruizton.main.service.admin;

import com.ruizton.main.dao.zuohao.TradeLogDao;
import com.ruizton.main.model.AutoOrder;
import com.ruizton.main.model.TradeLog;
import com.ruizton.main.model.TradeSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lx on 17-2-13.
 */
@Service
public class TradeLogService {

    @Autowired
    private TradeLogDao tradeLogDao;

    public void saveObj(TradeLog obj) {
        this.tradeLogDao.save(obj);
    }

    public List<TradeLog> list(int firstResult, int maxResults,
                                String filter,boolean isFY) {
        List<TradeLog> all = this.tradeLogDao.list(firstResult, maxResults, filter,isFY);
        for (TradeLog tradeLog : all){
            tradeLog.getFtrademapping().getFvirtualcointypeByFvirtualcointype2().getFname();
            tradeLog.getUser().getFloginName();
            tradeLog.getFentrust().getFid();
            tradeLog.getTradeAccount().getId();
        }
        return all;
    }

    public List<TradeLog> list(String filter){
        return this.list(0,0,filter,Boolean.FALSE);
    }

    public List<TradeSet> findByProperty(String name, Object value) {
        return this.tradeLogDao.findByProperty(name, value);
    }

    public void updateObj(TradeLog obj) {
        this.tradeLogDao.attachDirty(obj);
    }
}
