package com.ruizton.main.dao.zuohao;

import com.ruizton.main.dao.comm.HibernateDaoSupport;
import com.ruizton.main.model.Farticletype;
import com.ruizton.main.model.FhostRecommended;
import com.ruizton.main.model.Fvideotype;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.hibernate.criterion.Example.create;


@Repository
public class FhostRecommendedDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory
			.getLogger(FhostRecommendedDAO.class);

	public void save(FhostRecommended transientInstance) {
		log.debug("saving FhostRecommended instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public FhostRecommended findById(Integer id) {
		log.debug("getting FhostRecommended instance with id: " + id);
		try {
			FhostRecommended instance = (FhostRecommended) getSession().get(
					"com.ruizton.main.model.FhostRecommended", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<FhostRecommended> findByExample(FhostRecommended instance) {
		log.debug("finding FhostRecommended instance by example");
		try {
			List<FhostRecommended> results = (List<FhostRecommended>) getSession()
					.createCriteria("com.ruizton.main.model.FhostRecommended").add(create(instance))
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
		log.debug("finding FhostRecommended instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from FhostRecommended as model where model."
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
		log.debug("finding all FhostRecommended instances");
		try {
			String queryString = "from FhostRecommended order by fid asc";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setCacheable(true) ;
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public void attachDirty(FhostRecommended instance) {
		log.debug("attaching dirty FhostRecommended instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}