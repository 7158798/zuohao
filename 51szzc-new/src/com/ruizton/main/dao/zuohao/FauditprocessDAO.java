package com.ruizton.main.dao.zuohao;

import com.ruizton.main.dao.comm.HibernateDaoSupport;
import com.ruizton.main.model.Fauditprocess;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.hibernate.criterion.Example.create;

/**
 * A data access object (DAO) providing persistence and search support for
 * Fauditprocess entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 *
 * @author MyEclipse Persistence Tools
 */

@Repository
public class FauditprocessDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory.getLogger(FauditprocessDAO.class);
	// property constants


	public void save(Fauditprocess transientInstance) {
		log.debug("saving Fauditprocess instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Fauditprocess persistentInstance) {
		log.debug("deleting Fauditprocess instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Fauditprocess findById(Integer id) {
		log.debug("getting Fauditprocess instance with id: " + id);
		try {
			Fauditprocess instance = (Fauditprocess) getSession().get("com.ruizton.main.model.Fauditprocess", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<Fauditprocess> findByExample(Fauditprocess instance) {
		log.debug("finding Fauditprocess instance by example");
		try {
			List<Fauditprocess> results = (List<Fauditprocess>) getSession()
					.createCriteria("com.ruizton.main.model.Fauditprocess").add(create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Fauditprocess instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Fauditprocess as model where model."
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
		log.debug("finding all Fauditprocess instances");
		try {
			String queryString = "from Fauditprocess";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Fauditprocess merge(Fauditprocess detachedInstance) {
		log.debug("merging Fauditprocess instance");
		try {
			Fauditprocess result = (Fauditprocess) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Fauditprocess instance) {
		log.debug("attaching dirty Fauditprocess instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Fauditprocess instance) {
		log.debug("attaching clean Fauditprocess instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	
	public List<Fauditprocess> list(int firstResult, int maxResults, String filter,boolean isFY) {
		List<Fauditprocess> list = null;
		log.debug("finding Fauditprocess instance with filter");
		try {
			String queryString = "from Fauditprocess "+filter;
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setCacheable(true);
			if(isFY){
				queryObject.setFirstResult(firstResult);
				queryObject.setMaxResults(maxResults);
			}
			list = queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by Fauditprocess name failed", re);
			throw re;
		}
		return list;
	}



	
}