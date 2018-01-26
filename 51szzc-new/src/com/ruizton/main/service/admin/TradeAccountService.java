package com.ruizton.main.service.admin;

import com.ruizton.main.dao.zuohao.TradeAccountDao;
import com.ruizton.main.model.TradeAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lx on 17-2-14.
 */
@Service
public class TradeAccountService {

    @Autowired
    private TradeAccountDao tradeAccountDao;

    /**
     * 第三方平台交易帐号
     * @param firstResult
     * @param maxResults
     * @param filter
     * @param isFY
     * @return
     */
    public List<TradeAccount> list(int firstResult, int maxResults,
                               String filter,boolean isFY) {
        List<TradeAccount> all = this.tradeAccountDao.list(firstResult, maxResults, filter,isFY);
        return all;
    }

    /**
     * 第三方交易帐号
     * @return
     */
    public List<TradeAccount> findAll() {
        return this.tradeAccountDao.findAll();
    }

}
