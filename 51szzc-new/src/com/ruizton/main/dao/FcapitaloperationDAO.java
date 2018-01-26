package com.ruizton.main.dao;

import static org.hibernate.criterion.Example.create;

import java.sql.Date;
import java.util.*;

import com.opensymphony.oscache.util.StringUtil;
import com.ruizton.main.Enum.RemittanceTypeEnum;
import com.ruizton.main.model.CapitalOperationLogVo;
import com.ruizton.main.model.VirtualOperationLogVo;
import org.apache.commons.lang.StringUtils;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.ruizton.main.Enum.CapitalOperationOutStatus;
import com.ruizton.main.Enum.CapitalOperationTypeEnum;
import com.ruizton.main.dao.comm.HibernateDaoSupport;
import com.ruizton.main.model.Fcapitaloperation;
import com.ruizton.main.model.Fuser;
import com.ruizton.util.Utils;

/**
 	* A data access object (DAO) providing persistence and search support for Fcapitaloperation entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	

  * @author MyEclipse Persistence Tools 
 */

@Repository
public class FcapitaloperationDAO extends HibernateDaoSupport  {
	     private static final Logger log = LoggerFactory.getLogger(FcapitaloperationDAO.class);
		//property constants
	public static final String FAMOUNT = "famount";
	public static final String FTYPE = "ftype";
	public static final String FSTATUS = "fstatus";
	public static final String FREMITTANCE_TYPE = "fremittanceType";
	public static final String FREMARK = "fremark";



    
    public void save(Fcapitaloperation transientInstance) {
        log.debug("saving Fcapitaloperation instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(Fcapitaloperation persistentInstance) {
        log.debug("deleting Fcapitaloperation instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public Fcapitaloperation findById( java.lang.Integer id) {
        log.debug("getting Fcapitaloperation instance with id: " + id);
        try {
            Fcapitaloperation instance = (Fcapitaloperation) getSession()
                    .get("com.ruizton.main.model.Fcapitaloperation", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List<Fcapitaloperation> findByExample(Fcapitaloperation instance) {
        log.debug("finding Fcapitaloperation instance by example");
        try {
            List<Fcapitaloperation> results = (List<Fcapitaloperation>) getSession()
                    .createCriteria("com.ruizton.main.model.Fcapitaloperation")
                    .add( create(instance) )
            .list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }    
    
    public List findByProperty(String propertyName, Object value) {
      log.debug("finding Fcapitaloperation instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from Fcapitaloperation as model where model." 
         						+ propertyName + "= ?";
         Query queryObject = getSession().createQuery(queryString);
		 queryObject.setParameter(0, value);
		 return queryObject.list();
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List<Fcapitaloperation> findByFamount(Object famount
	) {
		return findByProperty(FAMOUNT, famount
		);
	}
	
	public List<Fcapitaloperation> findByFtype(Object ftype
	) {
		return findByProperty(FTYPE, ftype
		);
	}
	
	public List<Fcapitaloperation> findByFstatus(Object fstatus
	) {
		return findByProperty(FSTATUS, fstatus
		);
	}
	
	public List<Fcapitaloperation> findByFremittanceType(Object fremittanceType
	) {
		return findByProperty(FREMITTANCE_TYPE, fremittanceType
		);
	}
	
	public List<Fcapitaloperation> findByFremark(Object fremark
	) {
		return findByProperty(FREMARK, fremark
		);
	}
	

	public List findAll() {
		log.debug("finding all Fcapitaloperation instances");
		try {
			String queryString = "from Fcapitaloperation";
	         Query queryObject = getSession().createQuery(queryString);
			 return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public Fcapitaloperation merge(Fcapitaloperation detachedInstance) {
        log.debug("merging Fcapitaloperation instance");
        try {
            Fcapitaloperation result = (Fcapitaloperation) getSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(Fcapitaloperation instance) {
        log.debug("attaching dirty Fcapitaloperation instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(Fcapitaloperation instance) {
        log.debug("attaching clean Fcapitaloperation instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public List findByParam(int firstResult, int maxResults,String filter,boolean isFY) {
		List<Fcapitaloperation> list = null;
		log.debug("finding Fcapitaloperation instance with filter");
		try {
			String queryString = "from Fcapitaloperation "+filter;
			Query queryObject = getSession().createQuery(queryString);
			if(isFY){
				queryObject.setFirstResult(firstResult);
				queryObject.setMaxResults(maxResults);
			}
			list = queryObject.list();
		} catch (RuntimeException re) {
			log.error("find Fcapitaloperation by filter name failed", re);
			throw re;
		}
		return list;
	}
    
    public int findByParamCount(Map<String, Object> param) {
		log.debug("finding Fcapitaloperation findByParam");
		try {
			StringBuffer queryString = new StringBuffer("select count(f.fid) from Fcapitaloperation f where ");
			
			Object[] keys = null ;
			if(param!=null){
				keys = param.keySet().toArray() ;
				for(int i=0;i<keys.length;i++){
					queryString.append(keys[i]+"=? and ") ;
				}
			}
			
			queryString.append(" 1=1 ") ;
			
			Query queryObject = getSession().createQuery(queryString.toString());
			if(keys!=null){
				for(int i=0;i<keys.length;i++){
					queryObject.setParameter(i, param.get(keys[i])) ;
				}
			}
			
			List listResult =queryObject.list();
			if(listResult==null||listResult.size()==0){
				return 0 ;
			}else{
				return (int)((Long)listResult.get(0)).longValue() ;
			}
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
    
	public List<Fcapitaloperation> list(int firstResult, int maxResults,
			String filter,boolean isFY) {
		List<Fcapitaloperation> list = null;
		log.debug("finding Fcapitaloperation instance with filter");
		try {
			String queryString = "from Fcapitaloperation "+filter;
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setCacheable(true);
			if(isFY){
				queryObject.setFirstResult(firstResult);
				queryObject.setMaxResults(maxResults);
			}
			list = queryObject.list();
		} catch (RuntimeException re) {
			log.error("find Fcapitaloperation by filter name failed", re);
			throw re;
		}
		return list;
	}
	
	public boolean updateCapital(String capitalSelectSQL,String capitalUpdateSQL) throws Exception {
		Query queryObject = getSession().createSQLQuery(capitalSelectSQL);
		if(queryObject.list().size() != 1){
			throw new Exception("数据被修改");
		}
		SQLQuery sQLQuery=getSession().createSQLQuery(capitalUpdateSQL);
		sQLQuery.executeUpdate();
		return true;
	}
	
	public boolean updateWallet(String walletSelectSQL,String walletUpdateSQL) throws Exception {
		Query queryObject = getSession().createSQLQuery(walletSelectSQL);
		if(queryObject.list().size() != 1){
			throw new Exception("数据被修改");
		}
		SQLQuery sQLQuery=getSession().createSQLQuery(walletUpdateSQL);
		sQLQuery.executeUpdate();
		return true;
	}
	
	
	public Map getTotalAmountIn(int type,String fstatus,boolean isToday) {
		Map map = new HashMap();
		StringBuffer sql = new StringBuffer();
		sql.append("select '人民币',sum(famount) totalAmount from fcapitaloperation where fType="+type+" and fstatus in"+fstatus+" \n");
		if(isToday){
			sql.append("and DATE_FORMAT(fLastUpdateTime,'%Y-%m-%d') = DATE_FORMAT(now(),'%Y-%m-%d') \n");
		}
	//	sql.append("group by fRemittanceType \n");
		SQLQuery queryObject = getSession().createSQLQuery(sql.toString());
		List allList = queryObject.list();
		if(allList != null && allList.size() >0 && allList.get(0) != null){
			for(int i=0;i<allList.size();i++){
				Object[] o = (Object[])allList.get(i);
				map.put(o[0],o[1]);
			}
		}
		return map;
	}
	
	public Map getTotalAmount(int type,String fstatus,boolean isToday) {
		Map map = new HashMap();
		StringBuffer sql = new StringBuffer();
		sql.append("select sum(famount) totalAmount from Fcapitaloperation where fType="+type+" and fstatus in"+fstatus+" \n");
		if(isToday){
			sql.append("and DATE_FORMAT(fcreateTime,'%Y-%m-%d') = DATE_FORMAT(now(),'%Y-%m-%d') \n");
		}
		SQLQuery queryObject = getSession().createSQLQuery(sql.toString());
		List allList = queryObject.list();
		if(allList != null && allList.size() >0){
			map.put("totalAmount",allList.get(0));
		}
		return map;
	}
	
	public List getTotalGroup(String filter) {
		Map map = new HashMap();
		StringBuffer sql = new StringBuffer();
		sql.append("select sum(famount) totalAmount,DATE_FORMAT(fcreateTime,'%Y-%m-%d') fcreateTime from Fcapitaloperation \n");
		sql.append(filter);
		sql.append("group by DATE_FORMAT(fcreateTime,'%Y-%m-%d') \n");
		SQLQuery queryObject = getSession().createSQLQuery(sql.toString());
		return queryObject.list();
	}
			
	public int getTodayCnyWithdrawTimes(Fuser fuser){
		log.debug("getTodayCnyWithdrawTimes all Fcapitaloperation instances");
		try {
			String queryString = 
					"select count(*) from Fcapitaloperation where fuser.fid=? " +
					"and fcreateTime>? " +
					"and ftype=? " +
					"and fstatus!=?";
	         Query queryObject = getSession().createQuery(queryString);
	         queryObject.setParameter(0, fuser.getFid()) ;
	         queryObject.setParameter(1, new Date(Utils.getTimesmorning())) ;
	         queryObject.setParameter(2, CapitalOperationTypeEnum.RMB_OUT) ;
	         queryObject.setParameter(3, CapitalOperationOutStatus.Cancel) ;
			 return Integer.parseInt(queryObject.list().get(0).toString());
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
	public List getTotalAmountForReport(String filter) {
		StringBuffer sql = new StringBuffer();
		sql.append("select sum(famount) totalAmount from Fcapitaloperation \n");
		sql.append(filter +" \n");
		SQLQuery queryObject = getSession().createSQLQuery(sql.toString());
		return queryObject.list();
	}
	
	public List getTotalOperationlog(String filter) {
		StringBuffer sql = new StringBuffer();
		sql.append("select sum(famount) totalAmount from Foperationlog \n");
		sql.append(filter +" \n");
		SQLQuery queryObject = getSession().createSQLQuery(sql.toString());
		return queryObject.list();
	}
	
	public Map getTotalAmount(int type,String fstatus,boolean isToday,boolean isFee) {
		Map map = new HashMap();
		StringBuffer sql = new StringBuffer();
		if(isFee){
			sql.append("select sum(ffees) totalAmount from Fcapitaloperation where fType="+type+" and fstatus in"+fstatus+" \n");
		}else{
			sql.append("select sum(famount) totalAmount from Fcapitaloperation where fType="+type+" and fstatus in"+fstatus+" \n");
		}
		
		if(isToday){
			sql.append("and DATE_FORMAT(fLastUpdateTime,'%Y-%m-%d') = DATE_FORMAT(now(),'%Y-%m-%d') \n");
		}
		SQLQuery queryObject = getSession().createSQLQuery(sql.toString());
		List allList = queryObject.list();
		if(allList != null && allList.size() >0){
			map.put("totalAmount",allList.get(0));
		}
		return map;
	}



	public Double getTotalPayAmountForUser(Fuser fuser){
		log.info("getTodayCnyWithdrawTimes all Fcapitaloperation instances");
		try {
			String queryString =
					"select sum(famount) from Fcapitaloperation where fuser.fid=? " +
							"and fLastUpdateTime>? " +
							"and ftype=? "+
							"and (fStatus = 3 or fStatus=2) "+
							"and (fremittanceType = ? "+
							"or fremittanceType = ?)";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, fuser.getFid()) ;
			queryObject.setParameter(1, new Date(Utils.getTimesmorning())) ;
			queryObject.setParameter(2, CapitalOperationTypeEnum.RMB_IN) ;
			queryObject.setParameter(3,"支付宝转账");
			queryObject.setParameter(4,"微信转账");
			List list = queryObject.list();
			if(list != null && list.size() > 0 && list.get(0) != null && StringUtils.isNotBlank(list.get(0).toString())){
				return Double.parseDouble(list.get(0).toString());
			}else{
				return 0.0;
			}

		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	/**
	 * 统计人民币充值，提现流水
	 * @param type
	 * @param date
	 * @param isRecharge
	 * @return
	 */
	public Map<String, String>  capitalOperationLogStats(Integer type, String date, boolean isRecharge) {
		Map<String, String> map = new HashMap<String, String>();
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from ( ");
		sql.append(" select count(1) as orderNum,sum(a.fAmount+ifnull(a.ffees,0))  as total_amtFees, sum(a.fAmount) as amount,");
		sql.append(" sum(ifnull(a.ffees,0))  as fees,'"+date+"' as flastUpdateTime");
		sql.append(" from fcapitaloperation a");
		sql.append(" inner join fuser b on a.FUs_fId = b.fId");
		sql.append(" where  a.fStatus = 3");
		sql.append(" and a.fType = " + type);
		sql.append(" and date_format(a.flastUpdateTime , '%Y-%m-%d') = '"+date+"'");
		sql.append(" ) tab where orderNum >0");

		SQLQuery queryObject = getSession().createSQLQuery(sql.toString());
		List allList = queryObject.list();
		if (allList != null && allList.size() > 0) {
			Iterator it = allList.iterator();
			while (it.hasNext()) {
				Object[] o = (Object[]) it.next();
				map.put("orderNum", o[0].toString());
				map.put("total_amtFees", o[1].toString());
				map.put("amount", o[2].toString());
				map.put("fees", o[3].toString());
				map.put("flastUpdateTime", o[4].toString());
			}
		}else{
			map.put("orderNum", "0");
			map.put("total_amtFees", "0");
			map.put("amount", "0");
			map.put("fees", "0");
			map.put("flastUpdateTime", date);
		}

		return map;
	}

	/**
	 * 各支付方式充值金额分类
	 * @param date
	 * @return
	 */
	public List<Map<String, String>>  rechargeRmbStatsByType(String date) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		StringBuffer sql = new StringBuffer();
		sql.append(" select  (case when a.fRemittanceType = '微信转账' then '微信转账'");
		sql.append(" when a.fRemittanceType = '支付宝转账' then  '支付宝转账'");
		sql.append(" else '银行转账' end) as rechargeType,count(1) as type_orderNum,");
		sql.append(" sum(a.fAmount) as type_amount");
		sql.append(" from fcapitaloperation a");
		sql.append(" inner join fuser b on a.FUs_fId = b.fId");
		sql.append(" where  a.fStatus = 3");
		sql.append(" and a.fType = 1");
		sql.append(" and date_format(a.flastUpdateTime , '%Y-%m-%d') = '"+date+"'  ");
		sql.append(" group by a.fRemittanceType");
		SQLQuery queryObject = getSession().createSQLQuery(sql.toString());
		List allList = queryObject.list();
		if (allList != null && allList.size() > 0) {
			Iterator it = allList.iterator();
			while (it.hasNext()) {
				Map<String, String> map = new HashMap<String, String>();
				Object[] o = (Object[]) it.next();
				map.put("rechargeType", o[0].toString());
				map.put("type_orderNum", o[1].toString());
				map.put("type_amount", o[2].toString());
				list.add(map);
			}
		}
		return list;
	}

	/**
	 * 查询人民币充值，提现流水
	 * @param type
	 * @param date
	 * @param isRecharge
	 * @return
	 */
	public List<CapitalOperationLogVo> capitalOperationLog(Integer type, String date, boolean isRecharge) {
		List<CapitalOperationLogVo>  list = new ArrayList<CapitalOperationLogVo>();
		StringBuffer sql = new StringBuffer();
		if(!isRecharge){
			sql.append(" select  a.fId as orderId,b.floginName as floginName, b.fRealName frealName,");
			sql.append(" (a.fAmount+ifnull(a.ffees,0)) as withdrawAmount , a.ffees as withdrawFees, a.fAmount as withdrawUserGetAmount,");
			sql.append(" b.fTelephone as ftelephone,a.fBank as bankName, a.fAccount as bankCardNo,a.faddress as branchName,");
			sql.append("  date_format(a.flastUpdateTime , '%Y-%m-%d %T')  as withdrawDate");
			sql.append(" from fcapitaloperation a");
		}else{
			sql.append(" select a.fId as orderId,b.floginName as floginName,b.fRealName as frealName,");
			sql.append(" b.fTelephone as ftelephone,");
			sql.append(" (case a.fRemittanceType when '微信转账' then a.fRemittanceType ");
			sql.append(" when '支付宝转账'then a.fRemittanceType else '银行转账' end) as rechargeType,");
			sql.append(" a.fAmount as rechargeAmount,date_format(a.flastUpdateTime , '%Y-%m-%d %T')  as rechargeDate");
			sql.append(" from fcapitaloperation a");
		}
		sql.append(" inner join fuser b on a.FUs_fId = b.fId");
		sql.append(" where  a.fStatus = 3");
		sql.append(" and a.fType = " + type);
		sql.append(" and date_format(a.flastUpdateTime , '%Y-%m-%d') = '"+date+"'");
		SQLQuery queryObject = getSession().createSQLQuery(sql.toString());
		List allList = queryObject.list();
		if (allList == null || allList.size() == 0) {
			return null;
		}

		Iterator it = allList.iterator();
		while (it.hasNext()) {
			CapitalOperationLogVo log = new CapitalOperationLogVo();
			Object[] o = (Object[]) it.next();
			log.setOrderId(o[0].toString());
			log.setFloginName(o[1].toString());
			log.setFrealName(o[2].toString());
			if(isRecharge){
				log.setFtelephone(o[3].toString());
				log.setRechargeType(o[4].toString());
				log.setRechargeAmount(o[5].toString());
				log.setRechargeDate(o[6].toString());
			}else{
				log.setWithdrawAmount(o[3].toString());
				log.setWithdrawFees(o[4].toString());
				log.setWithdrawUserGetAmount(o[5].toString());
				log.setFtelephone(o[6].toString());
				log.setBankName(o[7].toString());
				log.setBankCardNo(o[8].toString());
				log.setBranchName(o[9].toString());
				log.setWithdrawDate(o[10].toString());
			}
			list.add(log);

		}
		return list;
	}


	/**
	 * 三方支付的次数
	 * @param remittanceType  充值类型
	 * @param userId  用户id
	 * @return 充值次数
	 */
	public int queryCountByRemittanceType(String remittanceType, Integer userId) {

		if(StringUtils.isBlank(remittanceType) || userId == null) {
			return 0;
		}

		log.info("queryCountByRemittanceType,remittanceType=" + remittanceType + ",userid=" + userId);

		try {

			StringBuffer sql = new StringBuffer();
			sql.append(" select count(fId) from fcapitaloperation  ");
			sql.append(" where fRemittanceType = ?");
			sql.append(" and fType = 1");
			sql.append(" and fStatus in(2,3)");
			sql.append(" and FUs_fId = ?");
			SQLQuery queryObject = getSession().createSQLQuery(sql.toString());
			queryObject.setParameter(0, remittanceType);
			queryObject.setParameter(1, userId);

			List list = queryObject.list();
			if(list != null && list.size() > 0 && list.get(0) != null && StringUtils.isNotBlank(list.get(0).toString())){
				return Integer.parseInt(list.get(0).toString());
			}else{
				return 0;
			}

		}catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}



	public long getDayCount(int fid, int fType) {

		StringBuffer hql = new StringBuffer();
		hql.append(" select count(fid) from Fcapitaloperation  ");
		hql.append(" where  ftype = ?");
		hql.append(" and fuser.fid = ?");
		hql.append(" and fstatus != "+CapitalOperationOutStatus.Cancel);
		hql.append("and DATE_FORMAT(fcreateTime,'%Y-%m-%d')=DATE_FORMAT(now(),'%Y-%m-%d')");
		Query query = getSession().createQuery( hql.toString());
		query.setParameter(0, fType);
		query.setParameter(1, fid);
		return (Long) query.uniqueResult();

	}

	public double getDayLimit(int fid, int fType) {

		StringBuffer hql = new StringBuffer();
		hql.append(" select sum(famount) from Fcapitaloperation  ");
		hql.append(" where  ftype = ?");
		hql.append(" and fuser.fid = ?");
		hql.append(" and fstatus != "+CapitalOperationOutStatus.Cancel);
		hql.append("and DATE_FORMAT(fcreateTime,'%Y-%m-%d')=DATE_FORMAT(now(),'%Y-%m-%d')");
		Query query = getSession().createQuery( hql.toString());
		query.setParameter(0, fType);
		query.setParameter(1, fid);
		Object obj=query.uniqueResult();
		if(obj!=null){
			return (double) obj;
		}else{
			return 0;
		}

	}

	public double getMonthLimit(int fid, int fType) {
		StringBuffer hql = new StringBuffer();
		hql.append(" select sum(famount) from Fcapitaloperation  ");
		hql.append(" where  ftype = ?");
		hql.append(" and fuser.fid = ?");
		hql.append(" and fstatus != "+CapitalOperationOutStatus.Cancel);
		hql.append("and DATE_FORMAT(fcreateTime,'%Y-%m')=DATE_FORMAT(now(),'%Y-%m')");
		Query query = getSession().createQuery( hql.toString());
		query.setParameter(0, fType);
		query.setParameter(1, fid);

		Object obj=query.uniqueResult();
		if(obj!=null){
			return (double) obj;
		}else{
			return 0;
		}

	}
}