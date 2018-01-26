package com.ruizton.main.dao;

import static org.hibernate.criterion.Example.create;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import com.ruizton.main.model.FentrustlogVo;
import com.ruizton.main.model.vo.TradeVo;
import com.ruizton.util.DateHelper;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.poi.hssf.record.formula.functions.T;
import org.hibernate.Hibernate;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.ruizton.main.dao.comm.HibernateDaoSupport;
import com.ruizton.main.model.Fentrustlog;
import com.ruizton.util.Utils;

/**
 * A data access object (DAO) providing persistence and search support for
 * Fentrustlog entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 *
 * @see com.ruizton.main.com.ruizton.main.model.Fentrustlog
 * @author MyEclipse Persistence Tools
 */
@Repository
public class FentrustlogDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory
			.getLogger(FentrustlogDAO.class);
	// property constants
	public static final String FAMOUNT = "famount";
	public static final String FPRIZE = "fprize";
	public static final String FCOUNT = "fcount";

	public void save(Fentrustlog transientInstance) {
		log.debug("saving Fentrustlog instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Fentrustlog persistentInstance) {
		log.debug("deleting Fentrustlog instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Fentrustlog findById(java.lang.Integer id) {
		log.debug("getting Fentrustlog instance with id: " + id);
		try {
			Fentrustlog instance = (Fentrustlog) getSession().get(
					"com.ruizton.main.model.Fentrustlog", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<Fentrustlog> findByExample(Fentrustlog instance) {
		log.debug("finding Fentrustlog instance by example");
		try {
			List<Fentrustlog> results = (List<Fentrustlog>) getSession()
					.createCriteria("com.ruizton.main.model.Fentrustlog").add(create(instance))
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
		log.debug("finding Fentrustlog instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Fentrustlog as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<Fentrustlog> findByFamount(Object famount) {
		return findByProperty(FAMOUNT, famount);
	}

	public List<Fentrustlog> findByFprize(Object fprize) {
		return findByProperty(FPRIZE, fprize);
	}

	public List<Fentrustlog> findByFcount(Object fcount) {
		return findByProperty(FCOUNT, fcount);
	}

	public List findAll() {
		log.debug("finding all Fentrustlog instances");
		try {
			String queryString = "from Fentrustlog";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Fentrustlog merge(Fentrustlog detachedInstance) {
		log.debug("merging Fentrustlog instance");
		try {
			Fentrustlog result = (Fentrustlog) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Fentrustlog instance) {
		log.debug("attaching dirty Fentrustlog instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Fentrustlog instance) {
		log.debug("attaching clean Fentrustlog instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	
	public List<Fentrustlog> findLatestSuccessDeal24(int coinTypeId,int hour){
		log.debug("findLatestSuccessDeal all Fentrustlog instances");
		try {
			String queryString = "from Fentrustlog where "+
					"ftrademapping.fid=? and " +
					"isactive=? and " +
					"fentrust!=null and " +
					"fcreateTime>?  " +
					"order by fid desc";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, coinTypeId) ;
			queryObject.setParameter(1, true) ;
			queryObject.setParameter(2, new Date(Utils.getTimestamp().getTime()-hour*60L*60*1000)) ;
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
	public Fentrustlog getLastFpeFentrustlog(int fvirtualcointype){
		log.debug("getLastFpeFentrustlog all Fentrustlog instances");
		try {
			String queryString = "from Fentrustlog where "+
					"fvirtualcointype.fid=? " +
					"order by fid desc";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, fvirtualcointype) ;
			queryObject.setFirstResult(0) ;
			queryObject.setMaxResults(1) ;
			List<Fentrustlog> fentrustlogs = queryObject.list();
			if(fentrustlogs.size()==0){
				return null ;
			}else{
				return fentrustlogs.get(0) ;
			}
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
	public List list(int firstResult, int maxResults, String filter,
			boolean isFY) {
		List list = null;
		log.debug("finding Fentrustlog instance with filter");
		try {
			StringBuffer sf = new StringBuffer();
			sf.append("select * from ( \n");
			sf.append("select \n");
			sf.append("c.fNickName,c.fRealName,c.floginName \n");
			sf.append(",sum(case WHEN a.fEntrustType=0 then a.fCount ELSE 0 END) totalBuy \n");
			sf.append(",sum(case WHEN a.fEntrustType=1 then a.fCount ELSE 0 END) totalSell \n");
			sf.append(",(sum(case WHEN a.fEntrustType=0 then a.fCount ELSE 0 END)+sum(case WHEN a.fEntrustType=1 then a.fCount ELSE 0 END)) total \n");
			sf.append(",date_format(a.fCreateTime,'%Y-%m-%d') createDate \n");
			sf.append("from fentrustlog a LEFT OUTER JOIN fentrust b on a.FEn_fId = b.fId \n");
			sf.append("left outer join fuser c on b.FUs_fId=c.fId \n");
			sf.append(filter +"\n");
			sf.append("GROUP BY date_format(a.fCreateTime,'%Y-%m-%d'),c.fNickName,c.fRealName,c.floginName \n");
			sf.append(")as t order by createDate,total desc");
			Query queryObject = getSession().createSQLQuery(sf.toString());
			if (isFY) {
				queryObject.setFirstResult(firstResult);
				queryObject.setMaxResults(maxResults);
			}
			list = queryObject.list();
		} catch (RuntimeException re) {
			log.error("find Fentrustlog by filter name failed", re);
			throw re;
		}
		return list;
	}
	
	public Double getTotalTradeAmt() {
		double total = 0d;
		String sql = "select sum(famount) from fentrustlog";
		Query queryObject = getSession().createSQLQuery(sql);
		List list = queryObject.list();
		if(list != null && list.size() >0 && list.get(0) != null){
			total = Double.valueOf(list.get(0).toString());
		}
		return total;
	}


	/**
	 * 查询用户的
	 * @param startTime  开始时间
	 * @param endTime  结束时间
	 * @param enType  委托类型
	 * @param userId  用户id
	 * @param fees 手续费百分比(买卖手续费可能不同，不同会员有不同等级，有不同手续费)
	 * @param fvi_fid 虚拟币id
	 * @return
	 */
	public Map<Integer, FentrustlogVo> queryLogByTime(String startTime, String endTime, Integer enType, Integer userId, BigDecimal fees, String fvi_fid) {
		Map<Integer, FentrustlogVo> map = new HashedMap();
		log.debug("finding Fentrustlog instance with filter");
		try {
			StringBuffer sf = new StringBuffer();
			sf.append(" select d.fId,b.fEntrustType,sum(a.fPrize*a.fCount) as amount,sum(a.fAmount*"+fees+") as fees,sum(a.fCount) as count ");
			sf.append(" from fentrustlog a");
			sf.append(" inner join fentrust b on b.fid = a.FEn_fId and a.fEntrustType = b.fEntrustType");
			sf.append(" inner join ftrademapping c on c.fid = a.ftrademapping ");
			sf.append(" inner join fvirtualcointype d on d.fid = c.fvirtualcointype2");
			sf.append(" where b.FUs_fId = "+userId+"   ");
			if (enType != null) {
				sf.append(" and b.fEntrustType =  " + enType);
			}
			if (fvi_fid != null) {
				sf.append(" and d.fid in ( " + fvi_fid + " )");
			}
			sf.append(" and (date_format(a.fCreateTime, '%Y-%m-%d %T') between '"+startTime+"' and '"+endTime+"')");
			sf.append(" group by d.fId,b.fEntrustType");
			Query queryObject = getSession().createSQLQuery(sf.toString());
			List allList = queryObject.list();
			if(allList.isEmpty()) {
				return map;
			}

			for (int i = 0; i < allList.size(); i++) {
				Object[] o = (Object[]) allList.get(i);
				FentrustlogVo v = new FentrustlogVo();
				v.setFviFid(Integer.valueOf(o[0].toString()));
				v.setfEntrustType(Integer.valueOf(o[1].toString()));
				v.setAmount(new BigDecimal(o[2].toString()));
				v.setFees(new BigDecimal(o[3].toString()));
				v.setCount(new BigDecimal(o[4].toString()));
				map.put(Integer.valueOf(o[0].toString()), v);
			}
		} catch (RuntimeException re) {
			log.error("find Fentrustlog by filter name failed", re);
			throw re;
		}
		return map;
	}

    /**
	 * 查询最低价最高价
	 * @param mappingId
     * @return
     */
	public FentrustlogVo queryLowAndHighByTime(String start,String end,Integer mappingId){
		StringBuffer sf = new StringBuffer();
		sf.append("select ifnull(max(fPrize),0),ifnull(min(fPrize),0) ");
		sf.append(" from fentrustlog a ");
		sf.append(" inner join ftrademapping c on c.fid = a.ftrademapping ");
		sf.append(" where c.fid="+mappingId);
		sf.append(" and (a.fCreateTime between '"+start+"' and '"+end+"')");
		Query queryObject = getSession().createSQLQuery(sf.toString());
		FentrustlogVo v = null;
		List allList = queryObject.list();
		if(allList != null && allList.size() >0) {
				Object[] o = (Object[])allList.get(0);
			    v = new FentrustlogVo();
				v.setAmount(new BigDecimal(o[0].toString()));
				v.setFees(new BigDecimal(o[1].toString()));
		}
		return v;
	}

	/**
	 * 获取交易托管最近记录
	 *
	 * @param symbol
	 * @param since
	 * @return
	 */
	public List<TradeVo> findTrade(Integer symbol, Integer since) {
		List<TradeVo> list = null;
		try {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, -1);
			java.util.Date time = cal.getTime();
			String timeStr = DateHelper.date2String(time, DateHelper.DateFormatType.YearMonthDay_HourMinuteSecond);

			StringBuffer sf = new StringBuffer();
			sf.append("select fCount as amount, fPrize as price, fid, fEntrustType as type, fCreateTime as date from fentrustlog ");
			sf.append(" where fcreateTime > '" + timeStr + "'");
			if (null != symbol) {
				sf.append(" and ftrademapping = ? ");
			}
			sf.append(" order by fcreateTime desc");
			SQLQuery sqlQuery = getSession().createSQLQuery(sf.toString());
			if (null != symbol) {
				sqlQuery.setInteger(0, symbol);
			}
			sqlQuery.setFirstResult(0);
			sqlQuery.addScalar("amount", Hibernate.STRING);
			sqlQuery.addScalar("price", Hibernate.STRING);
			sqlQuery.addScalar("fid", Hibernate.INTEGER);
			sqlQuery.addScalar("type", Hibernate.INTEGER);
			sqlQuery.addScalar("date", Hibernate.TIMESTAMP);
			sqlQuery.setMaxResults(since);
			sqlQuery.setResultTransformer(Transformers.aliasToBean(TradeVo.class));
			list = sqlQuery.list();
		} catch (RuntimeException re) {
			log.error("find Fentrustlog by filter name failed", re);
			throw re;
		}
		return list;
	}
}