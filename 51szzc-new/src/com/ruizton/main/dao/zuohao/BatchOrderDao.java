package com.ruizton.main.dao.zuohao;

import com.ruizton.main.dao.comm.HibernateDaoSupport;
import com.ruizton.main.log.LOG;
import com.ruizton.main.model.BatchOrder;
import com.ruizton.main.model.FastTrade;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by luwei on 17-2-14.
 */
@Repository
public class BatchOrderDao extends HibernateDaoSupport {

    public List<BatchOrder> list(int firstResult, int maxResults,
                               String filter, boolean isFY) {
        List<BatchOrder> list = null;
        LOG.d(this,"finding BatchOrder instance with filter");
        try {
            String queryString = "from BatchOrder "+filter;
            Query queryObject = getSession().createQuery(queryString);
            if(isFY){
                queryObject.setFirstResult(firstResult);
                queryObject.setMaxResults(maxResults);
            }
            list = queryObject.list();
        } catch (RuntimeException re) {
            LOG.e(this, "find BatchOrder by filter name failed", re);
            throw re;
        }
        return list;
    }


    public BatchOrder findById(int id) {
        LOG.d(this,"getting BatchOrder instance with id: " + id);
        try {
            BatchOrder instance = (BatchOrder) getSession()
                    .get(BatchOrder.class, id);
            return instance;
        } catch (RuntimeException re) {
            LOG.e(this,"get failed", re);
            throw re;
        }
    }


    public void save(BatchOrder transientInstance) {
        LOG.d(this,"saving BatchOrder instance");
        try {
            getSession().save(transientInstance);
            LOG.d(this, "save successful");
        } catch (RuntimeException re) {
            LOG.e(this,"save failed", re);
            throw re;
        }
    }

    public void attachDirty(BatchOrder instance) {
        LOG.d(this,"attaching dirty BatchOrder instance");
        try {
            getSession().saveOrUpdate(instance);
            LOG.d(this,"attach successful");
        } catch (RuntimeException re) {
            LOG.e(this,"attach failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        LOG.d(this,"finding BatchOrder instance with property: " + propertyName
                + ", value: " + value);
        try {
            String queryString = "from BatchOrder as model where model."
                    + propertyName + "= ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            return queryObject.list();
        } catch (RuntimeException re) {
            LOG.e(this,"find by property name failed", re);
            throw re;
        }
    }


    public void updateStatus(int userId,String status) {
        try {
            Query query = getSession().createQuery("update BatchOrder set status = ? where user.fid = ? ");
            query.setString(0,status);
            //设定条件参数
            query.setInteger(1, userId);
            query.executeUpdate();
        } catch (RuntimeException re) {
            throw re;
        }
    }


}
