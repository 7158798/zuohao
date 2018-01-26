package com.ruizton.main.dao.zuohao;

import com.ruizton.main.dao.comm.HibernateDaoSupport;
import com.ruizton.main.model.Ftagmanage;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.hibernate.criterion.Example.create;

/**
 * Created by zygong on 17-3-7.
 */
@Repository
public class FtagmanageDao extends HibernateDaoSupport {

    private static final Logger log = LoggerFactory
            .getLogger(FtagmanageDao.class);

    public void save(Ftagmanage transientInstance) {
        log.debug("saving Ftagmanage instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public Ftagmanage findById(Integer id) {
        log.debug("getting Ftagmanage instance with id: " + id);
        try {
            Ftagmanage instance = (Ftagmanage) getSession().get(
                    "com.ruizton.main.model.Ftagmanage", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List<Ftagmanage> findByExample(Ftagmanage instance) {
        log.debug("finding Ftagmanage instance by example");
        try {
            List<Ftagmanage> results = (List<Ftagmanage>) getSession()
                    .createCriteria("com.ruizton.main.model.Ftagmanage").add(create(instance))
                    .list();
            log.debug("find by example successful, result size: "
                    + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding Ftagmanage instance with property: "
                + propertyName + ", value: " + value);
        try {
            String queryString = "from Ftagmanage as model where model."
                    + propertyName + "= ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findAll() {
        log.debug("finding all Ftagmanage instances");
        try {
            String queryString = "from Ftagmanage order by fid asc";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setCacheable(true) ;
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public void attachDirty(Ftagmanage instance) {
        log.debug("attaching dirty Ftagmanage instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void deleteAll(){
        log.debug("delete all Ftagmanage instances");
        try {
            String hql = " delete from Ftagmanage ";
            Query query = getSession().createQuery(hql);
            query.executeUpdate();
        } catch (RuntimeException re) {
            log.error("delete all failed", re);
            throw re;
        }
    }
}
