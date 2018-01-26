package com.ruizton.main.dao.zuohao;

import com.ruizton.main.dao.comm.HibernateDaoSupport;
import com.ruizton.main.log.LOG;
import com.ruizton.main.model.Fhelp;
import com.ruizton.main.model.Fhelptype;
import com.ruizton.main.model.vo.HotHelpVo;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.hibernate.criterion.Example.create;

/**
 * A data access object (DAO) providing persistence and search support for
 * Fhelp entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see Fhelp
 * @author MyEclipse Persistence Tools
 */
@Repository
public class FhelpDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory
			.getLogger(FhelpDAO.class);
	// property constants
	public static final String FNAME = "fname";
	public static final String FKEYWORDS = "fkeywords";
	public static final String FDESCRIPTION = "fdescription";

	public void save(Fhelp transientInstance) {
		log.debug("saving Fhelp instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Fhelp persistentInstance) {
		log.debug("deleting Fhelp instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Fhelp findById(Integer id) {
		log.debug("getting Fhelp instance with id: " + id);
		try {
			Fhelp instance = (Fhelp) getSession().get(
					"com.ruizton.main.model.Fhelp", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<Fhelp> findByExample(Fhelp instance) {
		log.debug("finding Fhelp instance by example");
		try {
			List<Fhelp> results = (List<Fhelp>) getSession()
					.createCriteria("com.ruizton.main.model.Fhelp").add(create(instance))
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
		log.debug("finding Fhelp instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from Fhelp as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<Fhelp> findByFname(Object fname) {
		return findByProperty(FNAME, fname);
	}

	public List<Fhelp> findByFkeywords(Object fkeywords) {
		return findByProperty(FKEYWORDS, fkeywords);
	}

	public List<Fhelp> findByFdescription(Object fdescription) {
		return findByProperty(FDESCRIPTION, fdescription);
	}

	public List findAll() {
		log.debug("finding all Fhelp instances");
		try {
			String queryString = "from Fhelp";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setCacheable(true) ;
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Fhelp merge(Fhelp detachedInstance) {
		log.debug("merging Fhelp instance");
		try {
			Fhelp result = (Fhelp) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Fhelp instance) {
		log.debug("attaching dirty Fhelp instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Fhelp instance) {
		log.debug("attaching clean Fhelp instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	
	public List<Fhelp> list(int firstResult, int maxResults,
			String filter,boolean isFY) {
		List<Fhelp> list = null;
		log.debug("finding Fhelp instance with filter");
		try {
			String queryString = "from Fhelp "+filter;
			Query queryObject = getSession().createQuery(queryString);
			if(isFY){
				queryObject.setFirstResult(firstResult);
				queryObject.setMaxResults(maxResults);
			}
			list = queryObject.list();
		} catch (RuntimeException re) {
			log.error("find Fhelp by filter name failed", re);
			throw re;
		}
		return list;
	}

	public List<HotHelpVo> hotHelp(){
		List<HotHelpVo> list = null;
		log.debug("finding hotHelp instance with filter");
		try {
			String hql = "select new com.ruizton.main.model.vo.HotHelpVo(ftype.fname, f.ftitle, f.fid, ftype.fid) from " +
					" Fhelp f, Fhelptype ftype where ftype.fisding = 1 and f.fhelptype.fid = ftype.fid " +
					" and f.fisding = 1 order by ftype.forder asc, f.forder asc, f.fupdatetime desc";
			Query queryObject = getSession().createQuery(hql);

			list = queryObject.list();
		} catch (RuntimeException re) {
			log.error("find Fhelp by filter name failed", re);
			throw re;
		}
		return list;
	}

    public Fhelp getLastHelp() {
		String queryString = "from Fhelp  order by forder desc";
		Query query = getSession().createQuery(queryString);
		query.setMaxResults(1);
		return (Fhelp)query.uniqueResult();
    }

	public Fhelp getNextHelp(Fhelp fhelp) {
		String queryString = "from Fhelp  where forder > ? and fhelptype.fid=?  order by forder asc";
		Query queryObject = getSession().createQuery(queryString);
		queryObject.setParameter(0, fhelp.getForder());
		queryObject.setParameter(1, fhelp.getFhelptype().getFid());
		queryObject.setMaxResults(1);
		return (Fhelp)queryObject.uniqueResult();
	}

	public Fhelp getPreviousHelp(Fhelp fhelp) {
		String queryString = "from Fhelp where forder < ?  and fhelptype.fid=? order by forder desc";
		Query queryObject = getSession().createQuery(queryString);
		queryObject.setParameter(0, fhelp.getForder());
		queryObject.setParameter(1, fhelp.getFhelptype().getFid());
		queryObject.setMaxResults(1);
		return (Fhelp)queryObject.uniqueResult();
	}
}