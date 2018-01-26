package com.ruizton.main.dao.integral;

import com.ruizton.main.dao.comm.HibernateDaoSupport;
import com.ruizton.main.model.integral.Fuserintegraldetail;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.hibernate.criterion.Example.create;

/**
 * A data access object (DAO) providing persistence and search support for
 * Fuserintegraldetail entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 *
 * @author MyEclipse Persistence Tools
 */

@Repository
public class FuserintegraldetailDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory.getLogger(FuserintegraldetailDAO.class);
	// property constants


	public void save(Fuserintegraldetail transientInstance) {
		log.debug("saving Fuserintegraldetail instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Fuserintegraldetail persistentInstance) {
		log.debug("deleting Fuserintegraldetail instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Fuserintegraldetail findById(Integer id) {
		log.debug("getting Fuserintegraldetail instance with id: " + id);
		try {
			Fuserintegraldetail instance = (Fuserintegraldetail) getSession().get("com.ruizton.main.model.integral.Fuserintegraldetail", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<Fuserintegraldetail> findByExample(Fuserintegraldetail instance) {
		log.debug("finding Fuserintegraldetail instance by example");
		try {
			List<Fuserintegraldetail> results = (List<Fuserintegraldetail>) getSession()
					.createCriteria("com.ruizton.main.model.integral.Fuserintegraldetail").add(create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Fuserintegraldetail instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Fuserintegraldetail as model where model."
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
		log.debug("finding all Fuserintegraldetail instances");
		try {
			String queryString = "from Fuserintegraldetail";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Fuserintegraldetail merge(Fuserintegraldetail detachedInstance) {
		log.debug("merging Fuserintegraldetail instance");
		try {
			Fuserintegraldetail result = (Fuserintegraldetail) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Fuserintegraldetail instance) {
		log.debug("attaching dirty Fuserintegraldetail instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Fuserintegraldetail instance) {
		log.debug("attaching clean Fuserintegraldetail instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	
	public List<Fuserintegraldetail> list(int firstResult, int maxResults, String filter,boolean isFY) {
		List<Fuserintegraldetail> list = null;
		log.debug("finding Fuserintegraldetail instance with filter");
		try {
			String queryString = "from Fuserintegraldetail "+filter;
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setCacheable(true);
			if(isFY){
				queryObject.setFirstResult(firstResult);
				queryObject.setMaxResults(maxResults);
			}
			list = queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by Fuserintegraldetail name failed", re);
			throw re;
		}
		return list;
	}

	/**
	 *  获取当天已获取积分数
	 * @param type
	 * @param userId
	 * @return
     */
	public int getTodayIntegral(int type,int userId){
		int total = 0;
		StringBuilder sqlbf = new StringBuilder("select sum(operate_amount) from fuserintegraldetail ");
		sqlbf.append("where type = "+type);
		sqlbf.append(" and user_id = "+userId);
		sqlbf.append(" and  date_format(create_date,'%y-%m-%d') =  date_format(now(),'%y-%m-%d') and remark is null ");

		Query queryObject = getSession().createSQLQuery(sqlbf.toString());
		List list = queryObject.list();
		if(list != null && list.size() >0 && list.get(0) != null){
			total = Integer.valueOf(list.get(0).toString());
		}
		return total;
	}

	/**
	 *  获取类型、关联id获取对应积分
	 * @param type
	 * @param ralationId
	 * @return
	 */
	public int getIntegralByRalationId(int userid,int type,int ralationId){
		int total = 0;
		StringBuilder sqlbf = new StringBuilder("select sum(operate_amount) from fuserintegraldetail ");
		sqlbf.append("where 1 = "+1);
		if(ralationId != 0 ){
			sqlbf.append(" and relation_id = "+ralationId);
		}
		if(userid != 0 ){
			sqlbf.append(" and user_id = "+userid);
		}
		if(type != 0 ){
			sqlbf.append(" and `type` = "+type);
		}

		Query queryObject = getSession().createSQLQuery(sqlbf.toString());
		List list = queryObject.list();
		if(list != null && list.size() >0 && list.get(0) != null){
			total = Integer.valueOf(list.get(0).toString());
		}
		return total;
	}

    /**
	 *  获取用户积分汇总
	 * @param userId
	 * @return
     */
	public int[] getDetailAccount(int userId){
		int[] details = new int[6];
		StringBuilder sqlbf = new StringBuilder("select sum(case `type` when 1 then operate_amount else 0 end),\n" +
				"sum(case `type` when 2 then operate_amount else 0 end),\n" +
				"sum(case when `type`=3 or `type`=4  then operate_amount else 0 end),\n" +
				"sum(case `type` when 5 then operate_amount else 0 end),\n" +
				"sum(case `type` when 6 then operate_amount else 0 end),\n" +
				"sum(case `type` when 7 then operate_amount else 0 end)\n" +
				"from fuserintegraldetail ");
		sqlbf.append(" where user_id = "+userId);

		Query queryObject = getSession().createSQLQuery(sqlbf.toString());
		List list = queryObject.list();
		if(list != null && list.size() >0 && list.get(0) != null){
			Object[] o = (Object[])list.get(0);
			for(int i =0;i<6;i++){
				if(o[i] == null){
					details[i] = 0;
				}else{
					details[i] = Integer.parseInt(o[i].toString());
				}
			}
		}
		return details;
	}
	
}