package com.ruizton.main.dao.zuohao;

import com.ruizton.main.dao.comm.HibernateDaoSupport;
import com.ruizton.main.model.Farticletype;
import com.ruizton.main.model.Ftag;
import com.ruizton.main.model.Ftagmanage;
import org.hibernate.Query;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.hibernate.criterion.Example.create;

/**
 * Created by zygong on 17-3-7.
 */
@Repository
public class FtagDao extends HibernateDaoSupport {

    private static final Logger log = LoggerFactory
            .getLogger(FtagDao.class);

    public void saveList(List<Ftag> list){
        log.debug("saving Ftag list");
        try {
            // 开启事务
            Ftag model = null;

            if(null != list && list.size() > 0){
                for(int i = 0; i < list.size(); i++){
                    model = list.get(i);
                    getSession().save(model);

                    if(i % 10 ==0){
                        getSession().flush();
                        getSession().clear();
                    }
                }
                log.debug("save successful");
            }
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public Ftag findById(Integer id) {
        log.debug("getting Ftag instance with id: " + id);
        try {
            Ftag instance = (Ftag) getSession().get(
                    "com.ruizton.main.model.Ftag", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List<Ftag> list(int firstResult, int maxResults,
                                   String filter, boolean isFY) {
        List<Ftag> list = null;
        log.debug("finding Ftag instance with filter");
        try {
            String queryString = "from Ftag "+filter;
            Query queryObject = getSession().createQuery(queryString);
            if(isFY){
                queryObject.setFirstResult(firstResult);
                queryObject.setMaxResults(maxResults);
            }
            list = queryObject.list();
        } catch (RuntimeException re) {
            log.error("find Ftag by filter name failed", re);
            throw re;
        }
        return list;
    }

    public List findAll() {
        log.debug("finding all Ftag instances");
        try {
            String queryString = "from Ftag order by fid asc";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setCacheable(true) ;
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public void deleteAll(){
        log.debug("delete all Ftag instances");
        try {
            String hql = " delete from Ftag ";
            Query query = getSession().createQuery(hql);
            query.executeUpdate();
        } catch (RuntimeException re) {
            log.error("delete all failed", re);
            throw re;
        }
    }
}
