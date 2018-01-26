package com.ruizton.main.dao.zuohao;

import com.ruizton.main.dao.comm.HibernateDaoSupport;
import com.ruizton.main.log.LOG;
import com.ruizton.main.model.Ftrademapping;
import com.ruizton.main.model.TradeAccount;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by lx on 17-2-14.
 */
@Repository
public class TradeAccountDao extends HibernateDaoSupport {

    public List findAll() {
        LOG.d(this, "finding all AutoOrder instances");
        try {
            String queryString = "from TradeAccount ";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        } catch (RuntimeException re) {
            LOG.e(this,"find all failed", re);
            throw re;
        }
    }

    public List<TradeAccount> list(int firstResult, int maxResults,
                               String filter,boolean isFY) {
        List<TradeAccount> list = null;
        LOG.d(this, "finding TradeAccount instance with filter");
        try {
            String queryString = "from TradeAccount "+filter;
            Query queryObject = getSession().createQuery(queryString);
            if(isFY){
                queryObject.setFirstResult(firstResult);
                queryObject.setMaxResults(maxResults);
            }
            list = queryObject.list();
        } catch (RuntimeException re) {
            LOG.e(this, "find TradeAccount by filter name failed", re);
            throw re;
        }
        return list;
    }

}
