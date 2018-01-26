package com.ruizton.main.dao.zuohao;

import com.ruizton.main.dao.comm.HibernateDaoSupport;
import com.ruizton.main.log.LOG;
import com.ruizton.main.model.robot.RobotTrade;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by lx on 17-3-20.
 */
@Repository
public class RobotTradeDao extends HibernateDaoSupport {

    public List<RobotTrade> list(int firstResult, int maxResults,
                                 String filter, boolean isFY) {
        List<RobotTrade> list = null;
        LOG.d(this, "finding RobotTrade instance with filter");
        try {
            String queryString = "from RobotTrade "+filter;
            Query queryObject = getSession().createQuery(queryString);
            if(isFY){
                queryObject.setFirstResult(firstResult);
                queryObject.setMaxResults(maxResults);
            }
            list = queryObject.list();
        } catch (RuntimeException re) {
            LOG.e(this, "find RobotTrade by filter name failed", re);
            throw re;
        }
        return list;
    }


    public RobotTrade findById(int id) {
        LOG.d(this,"getting RobotTrade instance with id: " + id);
        try {
            RobotTrade instance = (RobotTrade) getSession()
                    .get(RobotTrade.class, id);
            return instance;
        } catch (RuntimeException re) {
            LOG.e(this,"get failed", re);
            throw re;
        }
    }


    public void save(RobotTrade transientInstance) {
        LOG.d(this,"saving RobotTrade instance");
        try {
            getSession().save(transientInstance);
            LOG.d(this, "save successful");
        } catch (RuntimeException re) {
            LOG.e(this,"save failed", re);
            throw re;
        }
    }

    public void attachDirty(RobotTrade instance) {
        LOG.d(this,"attaching dirty RobotTrade instance");
        try {
            getSession().saveOrUpdate(instance);
            LOG.d(this,"attach successful");
        } catch (RuntimeException re) {
            LOG.e(this,"attach failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        LOG.d(this,"finding RobotTrade instance with property: " + propertyName
                + ", value: " + value);
        try {
            String queryString = "from RobotTrade as model where model."
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
