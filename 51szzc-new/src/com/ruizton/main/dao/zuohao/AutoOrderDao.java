package com.ruizton.main.dao.zuohao;

import com.ruizton.main.dao.comm.HibernateDaoSupport;
import com.ruizton.main.model.AutoOrder;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by lx on 17-1-24.
 */
@Repository
public class AutoOrderDao extends HibernateDaoSupport{

    private static final Logger log = LoggerFactory.getLogger(AutoOrderDao.class);

    public AutoOrder findById(Integer id) {
        log.debug("getting AutoOrder instance with id: " + id);
        try {
            AutoOrder instance = (AutoOrder) getSession()
                    .get("com.ruizton.main.model.AutoOrder", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findAll() {
        log.debug("finding all AutoOrder instances");
        try {
            String queryString = "from AutoOrder ";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public List<AutoOrder> list(int firstResult, int maxResults,
                                       String filter,boolean isFY) {
        List<AutoOrder> list = null;
        log.debug("finding AutoOrder instance with filter");
        try {
            String queryString = "from AutoOrder "+filter;
            Query queryObject = getSession().createQuery(queryString);
            if(isFY){
                queryObject.setFirstResult(firstResult);
                queryObject.setMaxResults(maxResults);
            }
            list = queryObject.list();
        } catch (RuntimeException re) {
            log.error("find AutoOrder by filter name failed", re);
            throw re;
        }
        return list;
    }

    public void save(AutoOrder transientInstance) {
        log.debug("saving AutoOrder instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void attachDirty(AutoOrder instance) {
        log.debug("attaching dirty AutoOrder instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding AutoOrder instance with property: " + propertyName
                + ", value: " + value);
        try {
            String queryString = "from AutoOrder as model where model."
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
