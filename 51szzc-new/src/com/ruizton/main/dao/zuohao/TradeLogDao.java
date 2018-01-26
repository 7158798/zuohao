package com.ruizton.main.dao.zuohao;

import com.ruizton.main.dao.comm.HibernateDaoSupport;
import com.ruizton.main.log.LOG;
import com.ruizton.main.model.AutoOrder;
import com.ruizton.main.model.TradeLog;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by lx on 17-2-13.
 */
@Repository
public class TradeLogDao extends HibernateDaoSupport {

    public List<TradeLog> list(int firstResult, int maxResults,
                                String filter,boolean isFY) {
        List<TradeLog> list = null;
        LOG.d(this,"finding TradeLog instance with filter");
        try {
            String queryString = "from TradeLog "+filter;
            Query queryObject = getSession().createQuery(queryString);
            if(isFY){
                queryObject.setFirstResult(firstResult);
                queryObject.setMaxResults(maxResults);
            }
            list = queryObject.list();
        } catch (RuntimeException re) {
            LOG.e(this, "find TradeLog by filter name failed", re);
            throw re;
        }
        return list;
    }

    public void save(TradeLog transientInstance) {
        LOG.d(this,"saving TradeLog instance");
        try {
            getSession().save(transientInstance);
            LOG.d(this, "save successful");
        } catch (RuntimeException re) {
            LOG.e(this, "save failed", re);
            throw re;
        }
    }

    public void attachDirty(TradeLog instance) {
        LOG.d(this,"attaching dirty TradeLog instance");
        try {
            getSession().saveOrUpdate(instance);
            LOG.d(this,"attach successful");
        } catch (RuntimeException re) {
            LOG.e(this,"attach failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        LOG.d(this,"finding TradeLog instance with property: "
                + propertyName + ", value: " + value);
        try {
            String queryString = "from TradeLog as model where model."
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
