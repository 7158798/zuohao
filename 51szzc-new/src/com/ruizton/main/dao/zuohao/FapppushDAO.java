package com.ruizton.main.dao.zuohao;

import com.ruizton.main.dao.comm.HibernateDaoSupport;
import com.ruizton.main.model.Fapppush;
import com.ruizton.main.model.Fapppush;
import com.ruizton.main.model.vo.FapppushPhone;
import com.ruizton.main.model.vo.HotHelpVo;
import org.apache.commons.lang.StringUtils;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static org.hibernate.criterion.Example.create;

/**
 * A data access object (DAO) providing persistence and search support for
 * Fapppush entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see Fapppush
 * @author MyEclipse Persistence Tools
 */
@Repository
public class FapppushDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory
			.getLogger(FapppushDAO.class);

	public void save(Fapppush transientInstance) {
		log.debug("saving Fapppush instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void saveList(List<Fapppush> fapppushList) {
		log.debug("saving Fapppush instance");
		try {
			if(null != fapppushList && fapppushList.size() > 0){
				for(Fapppush fapppush : fapppushList){
					getSession().save(fapppush);
				}
			}
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Fapppush persistentInstance) {
		log.debug("deleting Fapppush instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public void deleteByUser(int userId) {
		log.debug("deleteByUser Fapppush instance");
		try {
			String hql = "delete Fapppush where fuser= ?";
			Query query = getSession().createQuery(hql);
			query.setInteger(0, userId);
			query.executeUpdate();
			log.debug("deleteByUser successful");
		} catch (RuntimeException re) {
			log.error("deleteByUser failed", re);
			throw re;
		}
	}

	public Fapppush findById(Integer id) {
		log.debug("getting Fapppush instance with id: " + id);
		try {
			Fapppush instance = (Fapppush) getSession().get(
					"com.ruizton.main.model.Fapppush", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<Fapppush> findByExample(Fapppush instance) {
		log.debug("finding Fapppush instance by example");
		try {
			List<Fapppush> results = (List<Fapppush>) getSession()
					.createCriteria("com.ruizton.main.model.Fapppush").add(create(instance))
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
		log.debug("finding Fapppush instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from Fapppush as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByUserAndCoinType(int user, Integer coinType) {
		log.debug("finding Fapppush instance with property: user and coinType ");
		try {
			if(user == 0){
				return null;
			}
			StringBuffer hql = new StringBuffer();
			hql.append(" from Fapppush as model where 1=1 ");
			if(user != 0){
				hql.append(" and model.fuser = " + user);
			}
			if(coinType != null && coinType != 0){
				hql.append(" and model.fcointype = " + coinType);
			}
			Query queryObject = getSession().createQuery(hql.toString());
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("findByUserAndCoinType failed", re);
			throw re;
		}
	}

	public List findAll() {
		log.debug("finding all Fapppush instances");
		try {
			String queryString = "from Fapppush";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setCacheable(true) ;
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Fapppush merge(Fapppush detachedInstance) {
		log.debug("merging Fapppush instance");
		try {
			Fapppush result = (Fapppush) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Fapppush instance) {
		log.debug("attaching dirty Fapppush instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Fapppush instance) {
		log.debug("attaching clean Fapppush instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	
	public List<Fapppush> list(int firstResult, int maxResults,
			String filter,boolean isFY) {
		List<Fapppush> list = null;
		log.debug("finding Fapppush instance with filter");
		try {
			String queryString = "from Fapppush "+filter;
			Query queryObject = getSession().createQuery(queryString);
			if(isFY){
				queryObject.setFirstResult(firstResult);
				queryObject.setMaxResults(maxResults);
			}
			list = queryObject.list();
		} catch (RuntimeException re) {
			log.error("find Fapppush by filter name failed", re);
			throw re;
		}
		return list;
	}

	public void update(Fapppush instance){
		log.debug("update Fapppush instance with filter");
		try {
			if(instance.getFuser() < 1 && instance.getFphonetype() < 1){
				return;
			}
			StringBuffer hql = new StringBuffer();
			hql.append("update fapppush ");
			hql.append(" set flowprice = " + instance.getFlowprice());
			hql.append(", fhighprice = " + instance.getFhighprice());
			hql.append(", fphonetype = " + instance.getFphonetype());
			if(instance.getFispush() != 0){
				hql.append(", fispush = " + instance.getFispush());
			}
			if(instance.getFissms() != 0 ){
				hql.append(", fissms = " + instance.getFissms());
			}

			if(StringUtils.isNotBlank(instance.getFphonecode())){
				hql.append(", fphonecode = " + instance.getFphonecode());
			}
			hql.append(" where fuser = " + instance.getFuser());
			hql.append(" and fcointype = " + instance.getFcointype());

			Query query = getSession().createSQLQuery(hql.toString());
			query.executeUpdate();
		} catch (RuntimeException re) {
			log.error("update Fapppush by filter name failed", re);
			throw re;
		}
	}

	public void updatePhoneInfo(Fapppush instance){
		log.debug("updatePhoneInfo Fapppush instance with filter");
		try {
			if(instance.getFuser() < 1 || instance.getFphonetype() < 1){
				return;
			}
			StringBuffer hql = new StringBuffer();
			hql.append("update Fapppush ");
			hql.append(" set fphonetype = " + instance.getFphonetype());

			if(StringUtils.isNotBlank(instance.getFphonecode())){
				hql.append(", fphonecode = '" + instance.getFphonecode() + "'");
			}
			hql.append(" where fuser = " + instance.getFuser());

			Query query = getSession().createQuery(hql.toString());
			query.executeUpdate();
		} catch (RuntimeException re) {
			log.error("updatePhoneInfo Fapppush by filter name failed", re);
			throw re;
		}
	}

	public void deleteParam(Fapppush persistentInstance) {
		log.debug("deleteParam Fapppush instance");
		try {
			if(persistentInstance.getFuser() == 0 || persistentInstance.getFcointype() == 0){
				return;
			}
			String hql = "delete Fapppush where fuser = ? and fcointype = ?";
			Query query = getSession().createQuery(hql);
			query.setInteger(0, persistentInstance.getFuser());
			query.setInteger(1, persistentInstance.getFcointype());
			query.executeUpdate();
			log.debug("deleteParam successful");
		} catch (RuntimeException re) {
			log.error("deleteParam failed", re);
			throw re;
		}
	}

	public List<FapppushPhone> findPushList(int coinType, double price, Timestamp time){
		List<FapppushPhone> list = null;
		log.debug("findPushList Fapppush instance");
		try {
			if(coinType == 0 || price == 0){
				return null;
			}
			//new com.ruizton.main.model.vo.FapppushPhone(
			String hql = "select new com.ruizton.main.model.vo.FapppushPhone(model.fid, model.fuser, model.fispush, model.fissms, model.fcointype, model.fphonecode, model.fhighprice, model.flowprice, model.fphonetype, u.ftelephone)" +
					" from Fapppush model, Fuser u where u.fid = model.fuser" +
					" and (model.flowprice >= " + price + " or model.fhighprice <= " + price + ") " +
					" and (model.fispush = 2 or model.fissms = 2) " +
					" and model.fcointype =" + coinType;
			if(null != time){
				hql += " and DATE_FORMAT(model.flastsendtime,'%Y-%m-%d %H:%i:%S')  <= '" + time + "'";
			}
			Query query = getSession().createQuery(hql);
			list = query.list();
			log.debug("findPushList successful");
		} catch (RuntimeException re) {
			log.error("findPushList failed", re);
			throw re;
		}
		return list;
	}

	public void updateSendTime(int coinType, double price, Timestamp time) {
		log.debug("updateSendTime Fapppush instance");
		try {
			// LocalDateTime jdk1.8+ 才支持
			Timestamp nowTime = Timestamp.valueOf(LocalDateTime.now());
			String sql = "update fapppush set flastsendtime =? where (flowprice >=? or fhighprice <=?) and (fispush = 2 or fissms = 2) and fcointype =? and DATE_FORMAT(flastsendtime,'%Y-%m-%d %H:%i:%S')  <=?";
			SQLQuery sqlQuery = getSession().createSQLQuery(sql);
			sqlQuery.setTimestamp(0, new Date());
			sqlQuery.setDouble(1, price);
			sqlQuery.setDouble(2, price);
			sqlQuery.setInteger(3, coinType);
			sqlQuery.setTimestamp(4, time);
			sqlQuery.executeUpdate();
		} catch (RuntimeException re) {
			log.error("updateSendTime failed", re);
			throw re;
		}
	}

	public void updateSendSms(int userId, int sendType){
		log.debug("updateSendSms Fapppush");
		try {
			if(userId == 0 || sendType == 0){
				return;
			}
			StringBuffer hql = new StringBuffer();
			hql.append("update Fapppush ");
			hql.append(" set fissms = ?");
			hql.append(" where fuser = ?");

			Query query = getSession().createQuery(hql.toString());
			query.setInteger(0, sendType);
			query.setInteger(1, userId);
			query.executeUpdate();
		} catch (RuntimeException re) {
			log.error("updateSendSms Fapppush failed", re);
			throw re;
		}
	}
}