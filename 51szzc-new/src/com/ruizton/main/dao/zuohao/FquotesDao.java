package com.ruizton.main.dao.zuohao;

import com.ruizton.main.dao.comm.HibernateDaoSupport;
import com.ruizton.main.model.OtherPlatform.Fquotes;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zygong on 17-3-7.
 */
@Repository
public class FquotesDao extends HibernateDaoSupport {

    private static final Logger log = LoggerFactory
            .getLogger(FquotesDao.class);

    public void saveList(List<Fquotes> list){
        log.debug("saving Fquotes list");
        try {
            Fquotes model = null;

            if(null != list && list.size() > 0){
                for(int i = 0; i < list.size(); i++){
                    model = list.get(i);
                    getSession().save(model);
                }
            }
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public Fquotes findById(Integer id) {
        log.debug("getting Fquotes instance with id: " + id);
        try {
            Fquotes instance = (Fquotes) getSession().get(
                    "com.ruizton.main.model.OtherPlatform.Fquotes", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List<Fquotes> list(int firstResult, int maxResults,
                                   String filter, boolean isFY) {
        List<Fquotes> list = null;
        log.debug("finding Fquotes instance with filter");
        try {
            String queryString = "from Fquotes "+filter;
            Query queryObject = getSession().createQuery(queryString);
            if(isFY){
                queryObject.setFirstResult(firstResult);
                queryObject.setMaxResults(maxResults);
            }
            list = queryObject.list();
        } catch (RuntimeException re) {
            log.error("find Fquotes by filter name failed", re);
            throw re;
        }
        return list;
    }

    public List findAll() {
        log.debug("finding all Fquotes instances");
        try {
            String queryString = "from Fquotes order by fid asc";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setCacheable(true) ;
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public void deleteAll(){
        log.debug("delete all Fquotes instances");
        try {
            String hql = " delete from Fquotes ";
            Query query = getSession().createQuery(hql);
            query.executeUpdate();
        } catch (RuntimeException re) {
            log.error("delete all failed", re);
            throw re;
        }
    }

    public List<Fquotes> findLast(int typeId){
        log.debug("findLast all Fquotes instances");
        List<Fquotes> list = null;
        try {
            String sql = " select * from fquotes where fvirtualcointypeid = " + typeId + " and fcreatetime = (select max(fcreatetime) from fquotes where fvirtualcointypeid = " + typeId + ") order by fplatform desc ";
            Query query = getSession().createSQLQuery(sql).addEntity(Fquotes.class);
            list = query.list();
            return list;
        } catch (RuntimeException re) {
            log.error("delete all failed", re);
            throw re;
        }
    };
}
