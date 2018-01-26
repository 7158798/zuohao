package com.ruizton.main.dao.zuohao;

import com.ruizton.main.dao.comm.HibernateDaoSupport;
import com.ruizton.main.log.LOG;
import com.ruizton.main.model.Fbanner;
import com.ruizton.main.model.Fvideo;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * banner横幅广告管理
 * Created by luwei on 17-3-7.
 */
@Repository
public class FbannerDao extends HibernateDaoSupport {

    /**
     * 多条件查询
     * @param firstResult 起始行
     * @param maxResults  最大返回行数
     * @param filter  sql条件
     * @param isFY  是否分页
     * @return
     */
    public List<Fbanner> list(int firstResult, int maxResults,
                             String filter, boolean isFY) {
        List<Fbanner> list = null;
        LOG.d(this, "finding Fbanner instance with filter");
        try {
            String queryString = "from Fbanner "+filter;
            Query queryObject = getSession().createQuery(queryString);
            if(isFY){
                queryObject.setFirstResult(firstResult);
                queryObject.setMaxResults(maxResults);
            }
            list = queryObject.list();
        } catch (RuntimeException re) {
            LOG.e(this, "find Fbanner by filter name failed", re);
            throw re;
        }
        return list;
    }


    /**
     * 根据主键id查询
     * @param id
     * @return
     */
    public Fbanner findById(int id) {
        LOG.d(this, "getting Fbanner instance with id: " + id);
        try {
            Fbanner instance = (Fbanner) getSession()
                    .get("com.ruizton.main.model.Fbanner", id);
            return instance;
        } catch (RuntimeException re) {
            LOG.e(this, "get failed", re);
            throw re;
        }
    }


    /**
     * 保存
     * @param transientInstance
     */
    public void save(Fbanner transientInstance) {
        LOG.d(this, "saving Fbanner instance");
        try {
            getSession().save(transientInstance);
            LOG.d(this, "save successful");
        } catch (RuntimeException re) {
            LOG.e(this, "save failed", re);
            throw re;
        }
    }

    /**
     * 保存或修改
     * @param instance
     */
    public void attachDirty(Fbanner instance) {
        LOG.d(this, "attaching dirty Fbanner instance");
        try {
            getSession().saveOrUpdate(instance);
            LOG.d(this, "attach successful");
        } catch (RuntimeException re) {
            LOG.e(this, "attach failed", re);
            throw re;
        }
    }

    /**
     * 根据属性查询
     * @param propertyName 属性名
     * @param value 属性值
     * @return
     */
    public List findByProperty(String propertyName, Object value) {
        LOG.d(this, "finding Fbanner instance with property: " + propertyName
                + ", value: " + value);
        try {
            String queryString = "from Fbanner as model where model."
                    + propertyName + "= ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            return queryObject.list();
        } catch (RuntimeException re) {
            LOG.e(this, "find by property name failed", re);
            throw re;
        }
    }

    /**
     * 获取最新的一个
     * @return
     */
    public Fbanner getLastBanner(int fseat){
        LOG.d(this, "finding last Fbanner");
        try {
            String queryString = "from Fbanner as model order by model.fpriority desc";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setMaxResults(1);
            return (Fbanner)queryObject.uniqueResult();
        } catch (RuntimeException re) {
            LOG.e(this, "find by property name failed", re);
            throw re;
        }
    }

    /**
     * 查询下一个
     * @param priority
     * @return
     */
    public Fbanner getPreviousBanner(int priority, int fseat) {
        LOG.d(this, "finding Fbanner getPreviousBanner");
        try {
            String queryString = "from Fbanner as model where model.fseat=? and  model.fpriority > ? order by model.fpriority asc";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, fseat);
            queryObject.setParameter(1, priority);
            queryObject.setMaxResults(1);
            return (Fbanner)queryObject.uniqueResult();
        } catch (RuntimeException re) {
            LOG.e(this, "find getPreviousBanner failed", re);
            throw re;
        }
    }

    /**
     * 查询上一个
     * @param priority
     * @return
     */
    public Fbanner getNextBanner(int priority, int fseat) {
        LOG.d(this, "finding Fbanner getNextBanner");
        try {
            String queryString = "from Fbanner as model where model.fseat=? and model.fpriority < ? order by model.fpriority desc";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, fseat);
            queryObject.setParameter(1, priority);
            queryObject.setMaxResults(1);
            return (Fbanner)queryObject.uniqueResult();
        } catch (RuntimeException re) {
            LOG.e(this, "find getNextBanner failed", re);
            throw re;
        }
    }


}
