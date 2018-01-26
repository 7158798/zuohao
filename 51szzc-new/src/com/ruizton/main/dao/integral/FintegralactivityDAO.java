package com.ruizton.main.dao.integral;

import com.ruizton.main.dao.comm.HibernateDaoSupport;
import com.ruizton.main.model.integral.Fintegralactivity;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.hibernate.criterion.Example.create;

/**
 * A data access object (DAO) providing persistence and search support for
 * Fintegralactivity entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 *
 * @author MyEclipse Persistence Tools
 */

@Repository
public class FintegralactivityDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory.getLogger(FintegralactivityDAO.class);
	// property constants


	public void save(Fintegralactivity transientInstance) {
		log.debug("saving Fintegralactivity instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Fintegralactivity persistentInstance) {
		log.debug("deleting Fintegralactivity instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Fintegralactivity findById(Integer id) {
		log.debug("getting Fintegralactivity instance with id: " + id);
		try {
			Fintegralactivity instance = (Fintegralactivity) getSession().get("com.ruizton.main.model.integral.Fintegralactivity", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<Fintegralactivity> findByExample(Fintegralactivity instance) {
		log.debug("finding Fintegralactivity instance by example");
		try {
			List<Fintegralactivity> results = (List<Fintegralactivity>) getSession()
					.createCriteria("com.ruizton.main.model.integral.Fintegralactivity").add(create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Fintegralactivity instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Fintegralactivity as model where model."
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
		log.debug("finding all Fintegralactivity instances");
		try {
			String queryString = "from Fintegralactivity";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Fintegralactivity merge(Fintegralactivity detachedInstance) {
		log.debug("merging Fintegralactivity instance");
		try {
			Fintegralactivity result = (Fintegralactivity) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Fintegralactivity instance) {
		log.debug("attaching dirty Fintegralactivity instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Fintegralactivity instance) {
		log.debug("attaching clean Fintegralactivity instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public Fintegralactivity findOpenActivity(int type){
		List<Fintegralactivity> list = null;
		log.debug("finding Fintegralactivity instance with filter");
		try {
			StringBuffer filter = new StringBuffer();
			filter.append(" where type = "+type);
			filter.append(" and status = 2 ");
			filter.append(" and now() > begin_time and now()< end_time ");
			String queryString = "from Fintegralactivity "+filter;
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setCacheable(false);

			list = queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by Fintegralactivity name failed", re);
			throw re;
		}
		if(list != null && list.size() == 1){
			return list.get(0);
		}else{
			return null;
		}
	}


	public List<Fintegralactivity> findOpenActivityList(){
		List<Fintegralactivity> list = null;
		log.debug("finding Fintegralactivity instance with filter");
		try {
			StringBuffer filter = new StringBuffer();
			filter.append(" where status = 2 ");
			filter.append(" and now() > begin_time and now()< end_time ");
			String queryString = "from Fintegralactivity "+filter;
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setCacheable(false);

			list = queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by Fintegralactivity name failed", re);
			throw re;
		}
		return list;
	}
	
	public List<Fintegralactivity> list(int firstResult, int maxResults, String filter,boolean isFY) {
		List<Fintegralactivity> list = null;
		log.debug("finding Fintegralactivity instance with filter");
		try {
			String queryString = "from Fintegralactivity "+filter;
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setCacheable(true);
			if(isFY){
				queryObject.setFirstResult(firstResult);
				queryObject.setMaxResults(maxResults);
			}
			list = queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by Fintegralactivity name failed", re);
			throw re;
		}
		return list;
	}
	
}