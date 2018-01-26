package com.ruizton.main.dao.zuohao;

import com.ruizton.main.dao.comm.HibernateDaoSupport;
import com.ruizton.main.log.LOG;
import com.ruizton.main.model.FastTrade;
import com.ruizton.main.model.TradeSet;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by luwei on 17-2-14.
 */
@Repository
public class FastTradeDao extends HibernateDaoSupport {

    public List<FastTrade> list(int firstResult, int maxResults,
                               String filter, boolean isFY) {
        List<FastTrade> list = null;
        LOG.d(this,"finding FastTrade instance with filter");
        try {
            String queryString = "from FastTrade "+filter;
            Query queryObject = getSession().createQuery(queryString);
            if(isFY){
                queryObject.setFirstResult(firstResult);
                queryObject.setMaxResults(maxResults);
            }
            list = queryObject.list();
        } catch (RuntimeException re) {
            LOG.e(this, "find FastTrade by filter name failed", re);
            throw re;
        }
        return list;
    }


    public FastTrade findById(int id) {
        LOG.d(this,"getting TradeSet instance with id: " + id);
        try {
            FastTrade instance = (FastTrade) getSession()
                    .get(FastTrade.class, id);
            return instance;
        } catch (RuntimeException re) {
            LOG.e(this,"get failed", re);
            throw re;
        }
    }


    public void save(FastTrade transientInstance) {
        LOG.d(this,"saving FastTrade instance");
        try {
            getSession().save(transientInstance);
            LOG.d(this, "save successful");
        } catch (RuntimeException re) {
            LOG.e(this,"save failed", re);
            throw re;
        }
    }

    public void attachDirty(FastTrade instance) {
        LOG.d(this,"attaching dirty FastTrade instance");
        try {
            getSession().saveOrUpdate(instance);
            LOG.d(this,"attach successful");
        } catch (RuntimeException re) {
            LOG.e(this,"attach failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        LOG.d(this,"finding FastTrade instance with property: " + propertyName
                + ", value: " + value);
        try {
            String queryString = "from FastTrade as model where model."
                    + propertyName + "= ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            return queryObject.list();
        } catch (RuntimeException re) {
            LOG.e(this,"find by property name failed", re);
            throw re;
        }
    }


}
