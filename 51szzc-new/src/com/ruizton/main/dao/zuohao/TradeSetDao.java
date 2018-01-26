package com.ruizton.main.dao.zuohao;

import com.ruizton.main.dao.comm.HibernateDaoSupport;
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
public class TradeSetDao extends HibernateDaoSupport {

    private static final Logger log = LoggerFactory.getLogger(TradeSetDao.class);



    public List<TradeSet> list(int firstResult, int maxResults,
                               String filter, boolean isFY) {
        List<TradeSet> list = null;
        log.debug("finding TradeSet instance with filter");
        try {
            String queryString = "from TradeSet "+filter;
            Query queryObject = getSession().createQuery(queryString);
            if(isFY){
                queryObject.setFirstResult(firstResult);
                queryObject.setMaxResults(maxResults);
            }
            list = queryObject.list();
        } catch (RuntimeException re) {
            log.error("find TradeSet by filter name failed", re);
            throw re;
        }
        return list;
    }


    public TradeSet findById(int id) {
        log.debug("getting TradeSet instance with id: " + id);
        try {
            TradeSet instance = (TradeSet) getSession()
                    .get("com.ruizton.main.model.TradeSet", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }


    public void save(TradeSet transientInstance) {
        log.debug("saving TradeSet instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void attachDirty(TradeSet instance) {
        log.debug("attaching dirty TradeSet instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding TradeSet instance with property: " + propertyName
                + ", value: " + value);
        try {
            String queryString = "from TradeSet as model where model."
                    + propertyName + "= ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }


}
