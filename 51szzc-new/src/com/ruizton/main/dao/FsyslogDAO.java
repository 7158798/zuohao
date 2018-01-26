package com.ruizton.main.dao;

import com.ruizton.main.dao.comm.HibernateDaoSupport;
import com.ruizton.main.model.Fabout;
import com.ruizton.main.model.Fsystemoperatorlog;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.hibernate.criterion.Example.create;

/**
 * Created by a123 on 17-1-23.
 */
@Repository
public class FsyslogDAO extends HibernateDaoSupport{
    private static final Logger log = LoggerFactory.getLogger(FsyslogDAO.class);
    // property constants
    public static final String FTITLE = "ftitle";
    public static final String FCONTENT = "fcontent";

    public void save(Fsystemoperatorlog transientInstance) {
        log.debug("saving Fsystemoperatorlog instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(Fsystemoperatorlog persistentInstance) {
        log.debug("deleting Fsystemoperatorlog instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public Fsystemoperatorlog findById(java.lang.Integer id) {
        log.debug("getting Fsystemoperatorlog instance with id: " + id);
        try {
            Fsystemoperatorlog instance = (Fsystemoperatorlog) getSession().get("com.ruizton.main.model.Fsystemoperatorlog", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List<Fsystemoperatorlog> findByExample(Fsystemoperatorlog instance) {
        log.debug("finding Fsystemoperatorlog instance by example");
        try {
            List<Fsystemoperatorlog> results = (List<Fsystemoperatorlog>) getSession()
                    .createCriteria("com.ruizton.main.model.Fsystemoperatorlog").add(create(instance)).list();
            log.debug("find by example successful, result size: "
                    + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding Fsystemoperatorlog instance with property: " + propertyName
                + ", value: " + value);
        try {
            String queryString = "from Fsystemoperatorlog as model where model."
                    + propertyName + "= ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List<Fsystemoperatorlog> findByFtitle(Object ftitle) {
        return findByProperty(FTITLE, ftitle);
    }

    public List<Fsystemoperatorlog> findByFcontent(Object fcontent) {
        return findByProperty(FCONTENT, fcontent);
    }

    public List findAll() {
        log.debug("finding all Fsystemoperatorlog instances");
        try {
            String queryString = "from Fsystemoperatorlog";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public Fsystemoperatorlog merge(Fsystemoperatorlog detachedInstance) {
        log.debug("merging Fsystemoperatorlog instance");
        try {
            Fsystemoperatorlog result = (Fsystemoperatorlog) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(Fsystemoperatorlog instance) {
        log.debug("attaching dirty Fsystemoperatorlog instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(Fsystemoperatorlog instance) {
        log.debug("attaching clean Fsystemoperatorlog instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public List<Fsystemoperatorlog> list(int firstResult, int maxResults, String filter,boolean isFY) {
        List<Fsystemoperatorlog> list = null;
        log.debug("finding Fsystemoperatorlog instance with filter");
        try {
            String queryString = "from Fsystemoperatorlog "+filter;
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setCacheable(true);
            if(isFY){
                queryObject.setFirstResult(firstResult);
                queryObject.setMaxResults(maxResults);
            }
            list = queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by Fsystemoperatorlog name failed", re);
            throw re;
        }
        return list;
    }
}
