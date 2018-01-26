package com.ruizton.main.dao.zuohao;

import com.ruizton.main.dao.comm.HibernateDaoSupport;
import com.ruizton.main.log.LOG;
import com.ruizton.main.model.robot.EntrustWarn;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by lx on 17-5-4.
 */
@Repository
public class EntrustWarnDao extends HibernateDaoSupport {

    public List<EntrustWarn> list(int firstResult, int maxResults,
                                 String filter, boolean isFY) {
        List<EntrustWarn> list = null;
        LOG.d(this, "finding EntrustWarn instance with filter");
        try {
            String queryString = "from EntrustWarn "+filter;
            Query queryObject = getSession().createQuery(queryString);
            if(isFY){
                queryObject.setFirstResult(firstResult);
                queryObject.setMaxResults(maxResults);
            }
            list = queryObject.list();
        } catch (RuntimeException re) {
            LOG.e(this, "find EntrustWarn by filter name failed", re);
            throw re;
        }
        return list;
    }


    public EntrustWarn findById(int id) {
        LOG.d(this,"getting EntrustWarn instance with id: " + id);
        try {
            EntrustWarn instance = (EntrustWarn) getSession()
                    .get(EntrustWarn.class, id);
            return instance;
        } catch (RuntimeException re) {
            LOG.e(this,"get failed", re);
            throw re;
        }
    }


    public void save(EntrustWarn transientInstance) {
        LOG.d(this,"saving EntrustWarn instance");
        try {
            getSession().save(transientInstance);
            LOG.d(this, "save successful");
        } catch (RuntimeException re) {
            LOG.e(this,"save failed", re);
            throw re;
        }
    }

    public void attachDirty(EntrustWarn instance) {
        LOG.d(this,"attaching dirty EntrustWarn instance");
        try {
            getSession().saveOrUpdate(instance);
            LOG.d(this,"attach successful");
        } catch (RuntimeException re) {
            LOG.e(this,"attach failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        LOG.d(this,"finding EntrustWarn instance with property: " + propertyName
                + ", value: " + value);
        try {
            String queryString = "from EntrustWarn as model where model."
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
