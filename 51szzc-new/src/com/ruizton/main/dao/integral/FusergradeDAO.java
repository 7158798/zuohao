package com.ruizton.main.dao.integral;

import com.ruizton.main.dao.comm.HibernateDaoSupport;
import com.ruizton.main.model.integral.Fusergrade;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.hibernate.criterion.Example.create;

/**
 * A data access object (DAO) providing persistence and search support for
 * Fusergrade entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 *
 * @author MyEclipse Persistence Tools
 */

@Repository
public class FusergradeDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory.getLogger(FusergradeDAO.class);
	// property constants


	public void save(Fusergrade transientInstance) {
		log.debug("saving Fusergrade instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Fusergrade persistentInstance) {
		log.debug("deleting Fusergrade instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Fusergrade findById(Integer id) {
		log.debug("getting Fusergrade instance with id: " + id);
		try {
			Fusergrade instance = (Fusergrade) getSession().get("com.ruizton.main.model.integral.Fusergrade", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<Fusergrade> findByExample(Fusergrade instance) {
		log.debug("finding Fusergrade instance by example");
		try {
			List<Fusergrade> results = (List<Fusergrade>) getSession()
					.createCriteria("com.ruizton.main.model.integral.Fusergrade").add(create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Fusergrade instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Fusergrade as model where model."
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
		log.debug("finding all Fusergrade instances");
		try {
			String queryString = "from Fusergrade";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Fusergrade merge(Fusergrade detachedInstance) {
		log.debug("merging Fusergrade instance");
		try {
			Fusergrade result = (Fusergrade) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Fusergrade instance) {
		log.debug("attaching dirty Fusergrade instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Fusergrade instance) {
		log.debug("attaching clean Fusergrade instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	
	public List<Fusergrade> list(int firstResult, int maxResults, String filter,boolean isFY) {
		List<Fusergrade> list = null;
		log.debug("finding Fusergrade instance with filter");
		try {
			String queryString = "from Fusergrade "+filter;
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setCacheable(true);
			if(isFY){
				queryObject.setFirstResult(firstResult);
				queryObject.setMaxResults(maxResults);
			}
			list = queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by Fusergrade name failed", re);
			throw re;
		}
		return list;
	}


	public double getTradeFee(int id){
		Fusergrade fusergrade = this.findById(id);
		if(fusergrade == null || fusergrade.getTradefee() == null){
			return 0.0;
		}
		return fusergrade.getTradefee().doubleValue();
	}
	
}