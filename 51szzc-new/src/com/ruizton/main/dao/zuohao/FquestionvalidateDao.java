package com.ruizton.main.dao.zuohao;

import com.ruizton.main.dao.comm.HibernateDaoSupport;
import com.ruizton.main.log.LOG;
import com.ruizton.main.model.Fquestionvalidate;
import com.ruizton.main.model.Fvideo;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 安全问题验证
 * Created by luwei on 17-3-28.
 */
@Repository
public class FquestionvalidateDao extends HibernateDaoSupport {

    /**
     * 多条件查询
     * @param firstResult 起始行
     * @param maxResults  最大返回行数
     * @param filter  sql条件
     * @param isFY  是否分页
     * @return
     */
    public List<Fquestionvalidate> list(int firstResult, int maxResults,
                              String filter, boolean isFY) {
        List<Fquestionvalidate> list = null;
        LOG.d(this, "finding Fquestionvalidate instance with filter");
        try {
            String queryString = "from Fquestionvalidate "+filter;
            Query queryObject = getSession().createQuery(queryString);
            if(isFY){
                queryObject.setFirstResult(firstResult);
                queryObject.setMaxResults(maxResults);
            }
            list = queryObject.list();
        } catch (RuntimeException re) {
            LOG.e(this, "find Fquestionvalidate by filter name failed", re);
            throw re;
        }
        return list;
    }


    /**
     * 根据主键id查询
     * @param id
     * @return
     */
    public Fquestionvalidate findById(int id) {
        LOG.d(this, "getting Fquestionvalidate instance with id: " + id);
        try {
            Fquestionvalidate instance = (Fquestionvalidate) getSession()
                    .get("com.ruizton.main.model.Fquestionvalidate", id);
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
    public void save(Fquestionvalidate transientInstance) {
        LOG.d(this, "saving Fquestionvalidate instance");
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
    public void attachDirty(Fquestionvalidate instance) {
        LOG.d(this, "attaching dirty Fquestionvalidate instance");
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
        LOG.d(this, "finding Fquestionvalidate instance with property: " + propertyName
                + ", value: " + value);
        try {
            String queryString = "from Fquestionvalidate as model where model."
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
     * 删除
     * @param transientInstance
     */
    public void delete(Fquestionvalidate transientInstance) {
        LOG.d(this, "deleting Fquestionvalidate instance");
        try {
            getSession().delete(transientInstance);
            LOG.d(this, "delete successful");
        } catch (RuntimeException re) {
            LOG.e(this, "delete failed", re);
            throw re;
        }
    }
    
}
