package com.ruizton.main.dao.zuohao;

import com.ruizton.main.dao.comm.HibernateDaoSupport;
import com.ruizton.main.log.LOG;
import com.ruizton.main.model.Fhelptype;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.hibernate.criterion.Example.create;

/**
 * A data access object (DAO) providing persistence and search support for
 * Fhelptype entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see Fhelptype
 * @author MyEclipse Persistence Tools
 */
@Repository
public class FhelptypeDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory
			.getLogger(FhelptypeDAO.class);
	// property constants
	public static final String FNAME = "fname";
	public static final String FKEYWORDS = "fkeywords";
	public static final String FDESCRIPTION = "fdescription";

	public void save(Fhelptype transientInstance) {
		log.debug("saving Fhelptype instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Fhelptype persistentInstance) {
		log.debug("deleting Fhelptype instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Fhelptype findById(Integer id) {
		log.debug("getting Fhelptype instance with id: " + id);
		try {
			Fhelptype instance = (Fhelptype) getSession().get(
					"com.ruizton.main.model.Fhelptype", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<Fhelptype> findByExample(Fhelptype instance) {
		log.debug("finding Fhelptype instance by example");
		try {
			List<Fhelptype> results = (List<Fhelptype>) getSession()
					.createCriteria("com.ruizton.main.model.Fhelptype").add(create(instance))
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
		log.debug("finding Fhelptype instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from Fhelptype as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<Fhelptype> findByFname(Object fname) {
		return findByProperty(FNAME, fname);
	}

	public List<Fhelptype> findByFkeywords(Object fkeywords) {
		return findByProperty(FKEYWORDS, fkeywords);
	}

	public List<Fhelptype> findByFdescription(Object fdescription) {
		return findByProperty(FDESCRIPTION, fdescription);
	}

	public List findAll() {
		log.debug("finding all Fhelptype instances");
		try {
			String queryString = "from Fhelptype as model order by model.forder asc ";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setCacheable(true) ;
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Fhelptype merge(Fhelptype detachedInstance) {
		log.debug("merging Fhelptype instance");
		try {
			Fhelptype result = (Fhelptype) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Fhelptype instance) {
		log.debug("attaching dirty Fhelptype instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Fhelptype instance) {
		log.debug("attaching clean Fhelptype instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	
	public List<Fhelptype> list(int firstResult, int maxResults,
			String filter,boolean isFY) {
		List<Fhelptype> list = null;
		log.debug("finding Fhelptype instance with filter");
		try {
			String queryString = "from Fhelptype "+filter;
			Query queryObject = getSession().createQuery(queryString);
			if(isFY){
				queryObject.setFirstResult(firstResult);
				queryObject.setMaxResults(maxResults);
			}
			list = queryObject.list();
		} catch (RuntimeException re) {
			log.error("find Fhelptype by filter name failed", re);
			throw re;
		}
		return list;
	}

	/**
	 * 获取最新的一个帮助类型
	 * @return
	 */
	public Fhelptype getLastType(){
		LOG.d(this, "finding Fhelptype last HelpType");
		try {
			String queryString = "from Fhelptype as model order by model.forder desc";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setMaxResults(1);
			return (Fhelptype)queryObject.uniqueResult();
		} catch (RuntimeException re) {
			LOG.e(this, "find by property name failed", re);
			throw re;
		}
	}

	/**
	 *
	 * @param forder
	 * @return
	 */
	public Fhelptype getPreviousHelpType(int forder) {
		LOG.d(this, "finding Fhelptype getPreviousHelpType");
		try {
			String queryString = "from Fhelptype as model where model.forder > ? order by model.forder asc";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, forder);
			queryObject.setMaxResults(1);
			return (Fhelptype)queryObject.uniqueResult();
		} catch (RuntimeException re) {
			LOG.e(this, "find getPreviousHelpType failed", re);
			throw re;
		}
	}

	/**
	 *
	 * @param forder
	 * @return
	 */
	public Fhelptype getNextHelpType(int forder) {
		LOG.d(this, "finding Fhelptype getPreviousHelpType");
		try {
			String queryString = "from Fhelptype as model where model.forder < ? order by model.forder desc";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, forder);
			queryObject.setMaxResults(1);
			return (Fhelptype)queryObject.uniqueResult();
		} catch (RuntimeException re) {
			LOG.e(this, "find getPreviousHelpType failed", re);
			throw re;
		}
	}
}