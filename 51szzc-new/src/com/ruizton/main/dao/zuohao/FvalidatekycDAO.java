package com.ruizton.main.dao.zuohao;

import com.ruizton.main.dao.comm.HibernateDaoSupport;
import com.ruizton.main.model.Fvalidatekyc;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.hibernate.criterion.Example.create;

/**
 * Created by fenggq on 17-3-28.
 */
@Repository
public class FvalidatekycDAO extends HibernateDaoSupport{

       private static final Logger log = LoggerFactory.getLogger(com.ruizton.main.dao.zuohao.FvalidatekycDAO.class);
        // property constants


        public void save(Fvalidatekyc transientInstance) {
            log.debug("saving Fvalidatekyc instance");
            try {
                getSession().save(transientInstance);
                log.debug("save successful");
            } catch (RuntimeException re) {
                log.error("save failed", re);
                throw re;
            }
        }

        public void delete(Fvalidatekyc persistentInstance) {
            log.debug("deleting Fvalidatekyc instance");
            try {
                getSession().delete(persistentInstance);
                log.debug("delete successful");
            } catch (RuntimeException re) {
                log.error("delete failed", re);
                throw re;
            }
        }

        public Fvalidatekyc findById(Integer id) {
            log.debug("getting Fvalidatekyc instance with id: " + id);
            try {
                Fvalidatekyc instance = (Fvalidatekyc) getSession().get("com.ruizton.main.model.Fvalidatekyc", id);
                return instance;
            } catch (RuntimeException re) {
                log.error("get failed", re);
                throw re;
            }
        }

        public List<Fvalidatekyc> findByExample(Fvalidatekyc instance) {
            log.debug("finding Fvalidatekyc instance by example");
            try {
                List<Fvalidatekyc> results = (List<Fvalidatekyc>) getSession()
                        .createCriteria("com.ruizton.main.model.Fvalidatekyc").add(create(instance)).list();
                log.debug("find by example successful, result size: "
                        + results.size());
                return results;
            } catch (RuntimeException re) {
                log.error("find by example failed", re);
                throw re;
            }
        }

        public List findByProperty(String propertyName, Object value) {
            log.debug("finding Fvalidatekyc instance with property: " + propertyName
                    + ", value: " + value);
            try {
                String queryString = "from Fvalidatekyc as model where model."
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
            log.debug("finding all Fvalidatekyc instances");
            try {
                String queryString = "from Fvalidatekyc";
                Query queryObject = getSession().createQuery(queryString);
                return queryObject.list();
            } catch (RuntimeException re) {
                log.error("find all failed", re);
                throw re;
            }
        }

        public Fvalidatekyc merge(Fvalidatekyc detachedInstance) {
            log.debug("merging Fvalidatekyc instance");
            try {
                Fvalidatekyc result = (Fvalidatekyc) getSession().merge(detachedInstance);
                log.debug("merge successful");
                return result;
            } catch (RuntimeException re) {
                log.error("merge failed", re);
                throw re;
            }
        }

        public void attachDirty(Fvalidatekyc instance) {
            log.debug("attaching dirty Fvalidatekyc instance");
            try {
                getSession().saveOrUpdate(instance);
                log.debug("attach successful");
            } catch (RuntimeException re) {
                log.error("attach failed", re);
                throw re;
            }
        }

        public void attachClean(Fvalidatekyc instance) {
            log.debug("attaching clean Fvalidatekyc instance");
            try {
                getSession().lock(instance, LockMode.NONE);
                log.debug("attach successful");
            } catch (RuntimeException re) {
                log.error("attach failed", re);
                throw re;
            }
        }

        public List<Fvalidatekyc> list(int firstResult, int maxResults, String filter,boolean isFY) {
            List<Fvalidatekyc> list = null;
            log.debug("finding Fvalidatekyc instance with filter");
            try {
                String queryString = "from Fvalidatekyc "+filter;
                Query queryObject = getSession().createQuery(queryString);
                queryObject.setCacheable(true);
                if(isFY){
                    queryObject.setFirstResult(firstResult);
                    queryObject.setMaxResults(maxResults);
                }
                list = queryObject.list();
            } catch (RuntimeException re) {
                log.error("find by Fvalidatekyc name failed", re);
                throw re;
            }
            return list;
        }

    /**
     * 查找用户对应认证
     * @param userId
     * @return
     */
    public Fvalidatekyc findFvalidatekycByUserId(int userId){
            log.debug("findFvirtualaddress all FvirtualaddressWithdraw instances");
            try {
                String queryString = "from Fvalidatekyc where fuser.fid=? order by Id desc ";
                Query queryObject = getSession().createQuery(queryString);
                queryObject.setParameter(0, userId) ;
                List<Fvalidatekyc> fvalidatekycs = queryObject.list();
                if(fvalidatekycs.size()>0){
                    return fvalidatekycs.get(0) ;
                }else{
                    return null ;
                }
            } catch (RuntimeException re) {
                log.error("find all failed", re);
                throw re;
            }
        }


    

    }