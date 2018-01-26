package com.ruizton.main.dao.zuohao;

import com.ruizton.main.dao.comm.HibernateDaoSupport;
import com.ruizton.main.model.Farticletype;
import com.ruizton.main.model.Fvideotype;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 视频管理Dao
 * Created by zygong on 17-2-28.
 */
@Repository
public class FvideotypeDao extends HibernateDaoSupport {

    private static final Logger log = LoggerFactory.getLogger(FvideotypeDao.class);

    public void save(Fvideotype transientInstance) {
        log.debug("saving Fvideotype instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(Fvideotype persistentInstance) {
        log.debug("deleting Fvideotype instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public Fvideotype findById( int id) {
        log.debug("getting Fvideotype instance with id: " + id);
        try {
            Fvideotype instance = (Fvideotype) getSession()
                    .get("com.ruizton.main.model.Fvideotype", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public void attachDirty(Fvideotype instance) {
        log.debug("attaching dirty Fvideotype instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public List<Fvideotype> list(int firstResult, int maxResults,
                                   String filter, boolean isFY) {
        List<Fvideotype> list = null;
        log.debug("finding Fvideotype instance with filter");
        try {
            String queryString = "from Fvideotype "+filter;
            Query queryObject = getSession().createQuery(queryString);
            if(isFY){
                queryObject.setFirstResult(firstResult);
                queryObject.setMaxResults(maxResults);
            }
            list = queryObject.list();
        } catch (RuntimeException re) {
            log.error("find Farticletype by filter name failed", re);
            throw re;
        }
        return list;
    }


    public List<Fvideotype> findAll() {
        List<Fvideotype> list = null;
        log.debug("findAll Fvideotype instance");
        try {
            String queryString = "from Fvideotype ";
            Query queryObject = getSession().createQuery(queryString);
            list = queryObject.list();
        } catch (RuntimeException re) {
            log.error("findAll Farticletype by filter name failed", re);
            throw re;
        }
        return list;
    }

    }
