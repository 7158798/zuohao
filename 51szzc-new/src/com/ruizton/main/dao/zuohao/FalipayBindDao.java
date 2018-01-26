package com.ruizton.main.dao.zuohao;

import com.ruizton.main.dao.comm.HibernateDaoSupport;
import com.ruizton.main.log.LOG;
import com.ruizton.main.model.FalipayBind;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 支付宝绑定
 * Created by luwei on 17-3-15.
 */
@Repository
public class FalipayBindDao extends HibernateDaoSupport {



    public List<FalipayBind> list(int firstResult, int maxResults,
                               String filter, boolean isFY) {
        List<FalipayBind> list = null;
        LOG.d(this, "finding FalipayBind instance with filter");
        try {
            String queryString = "from FalipayBind "+filter;
            Query queryObject = getSession().createQuery(queryString);
            if(isFY){
                queryObject.setFirstResult(firstResult);
                queryObject.setMaxResults(maxResults);
            }
            list = queryObject.list();
        } catch (RuntimeException re) {
            LOG.e(this, "find FalipayBind by filter name failed", re);
            throw re;
        }
        return list;
    }


    public FalipayBind findById(int id) {
        LOG.d(this, "getting FalipayBind instance with id: " + id);
        try {
            FalipayBind instance = (FalipayBind) getSession()
                    .get("com.ruizton.main.model.FalipayBind", id);
            return instance;
        } catch (RuntimeException re) {
            LOG.e(this, "get failed", re);
            throw re;
        }
    }


    public void save(FalipayBind transientInstance) {
        LOG.d(this, "saving FalipayBind instance");
        try {
            getSession().save(transientInstance);
            LOG.d(this, "save successful");
        } catch (RuntimeException re) {
            LOG.e(this, "save failed", re);
            throw re;
        }
    }

    public void attachDirty(FalipayBind instance) {
        LOG.d(this, "attaching dirty FalipayBind instance");
        try {
            getSession().saveOrUpdate(instance);
            LOG.d(this, "attach successful");
        } catch (RuntimeException re) {
            LOG.e(this, "attach failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        LOG.d(this, "finding FalipayBind instance with property: " + propertyName
                + ", value: " + value);
        try {
            String queryString = "from FalipayBind as model where model."
                    + propertyName + "= ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            return queryObject.list();
        } catch (RuntimeException re) {
            LOG.e(this, "find by property name failed", re);
            throw re;
        }
    }


    public void delete(FalipayBind instance) {
        LOG.d(this, "deleting FalipayBind instance");
        try {
            getSession().delete(instance);
            LOG.d(this, "delete successful");
        } catch (RuntimeException re) {
            LOG.e(this, "delete failed", re);
            throw re;
        }
    }
}
