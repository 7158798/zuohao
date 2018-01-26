package com.ruizton.main.dao;

import static org.hibernate.criterion.Example.create;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.ruizton.main.model.VirtualOperationLogVo;
import org.apache.commons.collections.map.HashedMap;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.ruizton.main.Enum.VirtualCapitalOperationOutStatusEnum;
import com.ruizton.main.Enum.VirtualCapitalOperationTypeEnum;
import com.ruizton.main.dao.comm.HibernateDaoSupport;
import com.ruizton.main.model.Fuser;
import com.ruizton.main.model.Fvirtualcaptualoperation;
import com.ruizton.main.model.Fvirtualcointype;
import com.ruizton.util.Utils;

/**
 * A data access object (DAO) providing persistence and search support for
 * Fvirtualcaptualoperation entities. Transaction control of the save(),
 * update() and delete() operations can directly support Spring
 * container-managed transactions or they can be augmented to handle
 * user-managed Spring transactions. Each of these methods provides additional
 * information for how to configure it for the desired type of transaction
 * control.
 * 
 * @see ztmp.Fvirtualcaptualoperation
 * @author MyEclipse Persistence Tools
 */
@Repository
public class FvirtualcaptualoperationDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory
			.getLogger(FvirtualcaptualoperationDAO.class);
	// property constants
	public static final String FVI_FID = "fviFId";
	public static final String FUS_FID = "fusFId";
	public static final String FVI_FID2 = "fviFId2";
	public static final String FAMOUNT = "famount";
	public static final String FTYPE = "ftype";
	public static final String FSTATUS = "fstatus";

	public void save(Fvirtualcaptualoperation transientInstance) {
		log.debug("saving Fvirtualcaptualoperation instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Fvirtualcaptualoperation persistentInstance) {
		log.debug("deleting Fvirtualcaptualoperation instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Fvirtualcaptualoperation findById(java.lang.Integer id) {
		log.debug("getting Fvirtualcaptualoperation instance with id: " + id);
		try {
			Fvirtualcaptualoperation instance = (Fvirtualcaptualoperation) getSession()
					.get("com.ruizton.main.model.Fvirtualcaptualoperation", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<Fvirtualcaptualoperation> findByExample(
			Fvirtualcaptualoperation instance) {
		log.debug("finding Fvirtualcaptualoperation instance by example");
		try {
			List<Fvirtualcaptualoperation> results = (List<Fvirtualcaptualoperation>) getSession()
					.createCriteria(
							"com.ruizton.main.model.Fvirtualcaptualoperation")
					.add(create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Fvirtualcaptualoperation instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from Fvirtualcaptualoperation as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<Fvirtualcaptualoperation> findByFviFId(Object fviFId) {
		return findByProperty(FVI_FID, fviFId);
	}

	public List<Fvirtualcaptualoperation> findByFusFId(Object fusFId) {
		return findByProperty(FUS_FID, fusFId);
	}

	public List<Fvirtualcaptualoperation> findByFviFId2(Object fviFId2) {
		return findByProperty(FVI_FID2, fviFId2);
	}

	public List<Fvirtualcaptualoperation> findByFamount(Object famount) {
		return findByProperty(FAMOUNT, famount);
	}

	public List<Fvirtualcaptualoperation> findByFtype(Object ftype) {
		return findByProperty(FTYPE, ftype);
	}

	public List<Fvirtualcaptualoperation> findByFstatus(Object fstatus) {
		return findByProperty(FSTATUS, fstatus);
	}

	public List findAll() {
		log.debug("finding all Fvirtualcaptualoperation instances");
		try {
			String queryString = "from Fvirtualcaptualoperation";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Fvirtualcaptualoperation merge(
			Fvirtualcaptualoperation detachedInstance) {
		log.debug("merging Fvirtualcaptualoperation instance");
		try {
			Fvirtualcaptualoperation result = (Fvirtualcaptualoperation) getSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Fvirtualcaptualoperation instance) {
		log.debug("attaching dirty Fvirtualcaptualoperation instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Fvirtualcaptualoperation instance) {
		log.debug("attaching clean Fvirtualcaptualoperation instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public List<Fvirtualcaptualoperation> list(int firstResult, int maxResults,
			String filter, boolean isFY) {
		List<Fvirtualcaptualoperation> list = null;
		log.debug("finding Fvirtualcaptualoperation instance with filter");
		try {
			String queryString = "from Fvirtualcaptualoperation " + filter;
			//log.error(queryString);
			Query queryObject = getSession().createQuery(queryString);
			if (isFY) {
				queryObject.setFirstResult(firstResult);
				queryObject.setMaxResults(maxResults);
			}
			list = queryObject.list();
		} catch (RuntimeException re) {
			log.error("find Fvirtualcaptualoperation by filter name failed", re);
			throw re;
		}
		return list;
	}
	
	public int findFvirtualcaptualoperationCount(
			Fuser fuser,int type[],int status[],Fvirtualcointype[] fvirtualcointypes,String order){
		log.debug("findFvirtualcaptualoperation all Fvirtualcaptualoperation instances");
		try {
			StringBuffer queryString = new StringBuffer("select count(fid) from Fvirtualcaptualoperation where ");
			
			if(fuser!=null){
				queryString.append(" fuser.fid=? and ") ;
			}
			
			if(status!=null){
				queryString.append(" ( ") ;
				for (int i = 0; i < status.length; i++) {
					if(i==0){
						queryString.append(" status=? ") ;
					}else{
						queryString.append(" or status=? ") ;
					}
				}
				queryString.append(" ) and ") ;
			}
			
			if(type!=null){
				queryString.append(" ( ") ;
				for (int i = 0; i < type.length; i++) {
					if(i==0){
						queryString.append(" ftype=? ") ;
					}else{
						queryString.append(" or ftype=? ") ;
					}
				}
				queryString.append(" ) and ") ;
			}
			
			if(fvirtualcointypes!=null){
				queryString.append(" ( ") ;
				for (int i = 0; i < fvirtualcointypes.length; i++) {
					if(i==0){
						queryString.append(" fvirtualcointype.fid=? ") ;
					}else{
						queryString.append(" or fvirtualcointype.fid=? ") ;
					}
				}
				queryString.append(" ) and ") ;
			}
			
			queryString.append(" 1=1 ") ;
			if(order!=null){
				queryString.append(" order by "+order) ;
			}
			
			Query queryObject = getSession().createQuery(queryString.toString());
			int index = 0 ;
			if(fuser!=null){
				queryObject.setParameter(0, fuser.getFid()) ;
				index = 1 ;
			}
			if(status!=null){
				for(int i=0;i<status.length;i++){
					queryObject.setParameter(index+i, status[i]) ;
				}
				index+=status.length ;
			}
			
			if(type!=null){
				for (int i = 0; i < type.length; i++) {
					queryObject.setParameter(index+i, type[i]) ;
				}
				index+=type.length ;
			}
			
			if(fvirtualcointypes!=null){
				for(int i=0;i<fvirtualcointypes.length;i++){
					queryObject.setParameter(index+i, fvirtualcointypes[i].getFid()) ;
				}
			}
			long l = (Long)queryObject.list().get(0);
			return (int) l ;
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
	public List<Map> getTotalQty(int type,String fstatus,boolean isToday) {
		List<Map> all = new ArrayList();
		StringBuffer sql = new StringBuffer();
		sql.append("select sum(famount) amount,sum(a.ffees) as fees,b.fName from Fvirtualcaptualoperation a left outer join \n");
		sql.append("fvirtualcointype b on a.fVi_fId2 = b.fId  where \n");
		sql.append("a.ftype="+type+" and a.fstatus IN"+fstatus+"  \n");
		if(isToday){
			sql.append("and  DATE_FORMAT(a.flastUpdateTime,'%Y-%m-%d') = DATE_FORMAT(now(),'%Y-%m-%d') \n");
		}
		sql.append("group by b.fName \n");
		SQLQuery queryObject = getSession().createSQLQuery(sql.toString());
		List allList = queryObject.list();
		Iterator it = allList.iterator();
		while(it.hasNext()) {
			Map map = new HashMap();
			Object[] o = (Object[]) it.next();
			map.put("amount", o[0]);
			map.put("fees", o[1]);
			map.put("fName", o[2]);
			all.add(map);
		}
		return all;
	}
	
	public List getTotalGroup(String filter) {
		List all = new ArrayList();
		StringBuffer sql = new StringBuffer();
		sql.append("select sum(a.famount) amount,DATE_FORMAT(a.fcreateTime,'%Y-%m-%d') fcreatetime from Fvirtualcaptualoperation a \n");
		sql.append(filter+"\n");
		sql.append("group by DATE_FORMAT(a.fcreateTime,'%Y-%m-%d') \n");
		SQLQuery queryObject = getSession().createSQLQuery(sql.toString());
		return queryObject.list();
	}

	public int getTodayVirtualCoinWithdrawTimes(Fuser fuser){
		log.debug("finding all getTodayVirtualCoinWithdrawTimes instances");
		try {
			String queryString = 
					"select count(fid) from Fvirtualcaptualoperation where fuser.fid=? " +
					"and fcreateTime>? " +
					"and  ftype=? " +
					"and fstatus!=? ";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, fuser.getFid()) ;
			queryObject.setParameter(1, new Timestamp(Utils.getTimesmorning())) ;
			queryObject.setParameter(2, VirtualCapitalOperationTypeEnum.COIN_OUT) ;
			queryObject.setParameter(3, VirtualCapitalOperationOutStatusEnum.Cancel) ;
			
			return Integer.parseInt(queryObject.list().get(0).toString());
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}


	/**
	 * 汇总充值或提现虚拟币流水，统计出订单笔数，总金额，总手续费
	 * @param type  类型 1充值  2提现
	 * @param vifid  虚拟币id
	 * @param date 对账时间  yyyy-mm-dd格式
	 * @param isRecharge  是否是充值
	 * @return
	 */
	public Map<String, String> virtualOperationLogStats(Integer type, Integer vifid, String date, boolean isRecharge) {
		Map<String, String> map = new HashMap<String, String>();
		StringBuffer sql = new StringBuffer();
		sql.append("  select * from (");
		sql.append(" select  count(1) as orderNum,  sum(a.fAmount) as amount,");
		sql.append(" sum(ifnull(a.ffees,0)) as fees, sum(a.fAmount+ifnull(a.ffees,0)) as total_amtFees ,'"+date+"' as flastUpdateTime");
		sql.append(" from fvirtualcaptualoperation a");
		sql.append(" inner join fuser b on a.FUs_fId2 = b.fId");
		sql.append(" where a.fStatus = 3");
		sql.append(" and a.fType = " + type);
		sql.append(" and a.fVi_fId2 = " + vifid);
		sql.append(" and date_format(a.flastUpdateTime , '%Y-%m-%d') = '" + date + "'");
		sql.append("  ) tab where orderNum >0");
		SQLQuery queryObject = getSession().createSQLQuery(sql.toString());
		List allList = queryObject.list();
		if (allList != null && allList.size() > 0) {
			Iterator it = allList.iterator();
			while (it.hasNext()) {
				Object[] o = (Object[]) it.next();
				map.put("orderNum", o[0].toString());
				map.put("amount", o[1].toString());
				map.put("fees", o[2].toString());
				map.put("total_amtFees", o[3].toString());
				map.put("flastUpdateTime", o[4].toString());
			}
		}else{
			map.put("orderNum", "0");
			map.put("amount", "0");
			map.put("fees", "0");
			map.put("total_amtFees", "0");
			map.put("flastUpdateTime", date);
		}
		return map;
	}


	/**
	 * 查询充值或提现虚拟币流水
	 * @param type  类型 1充值  2提现
	 * @param vifid  虚拟币id
	 * @param date 对账时间  yyyy-mm-dd格式
	 * @param isRecharge  是否是充值
	 * @return
	 */
	public List<VirtualOperationLogVo> virtualOperationLog(Integer type, Integer vifid, String date, boolean isRecharge){
		List<VirtualOperationLogVo> list = new ArrayList<VirtualOperationLogVo>();
		StringBuffer sql = new StringBuffer();
		if(isRecharge){
			sql.append(" select  b.floginName as floginName, b.fRealName as frealName, b.fTelephone as ftelephone,");
			sql.append(" a.fAmount as amount,a.recharge_virtual_address as rechargeAddress,");
		}else{
			sql.append(" select  b.floginName as floginName, b.fRealName as frealName, b.fTelephone as ftelephone,");
			sql.append(" a.fAmount as withdrawNum,a.ffees as ffees,sum(ifnull(a.fAmount,0)+ifnull(a.ffees,0)) as amount,");
			sql.append(" a.withdraw_virtual_address as withdrawAddress,");

		}
		sql.append(" date_format(a.flastUpdateTime , '%Y-%m-%d %T') as date");
		sql.append(" from fvirtualcaptualoperation a");
		sql.append(" inner join fuser b on a.FUs_fId2 = b.fId");
		sql.append(" where a.fStatus = 3");
		sql.append(" and a.fType = " + type);
		sql.append(" and a.fVi_fId2 = " + vifid);
		sql.append(" and date_format(a.flastUpdateTime , '%Y-%m-%d') = '" + date + "'");
		if(!isRecharge){
			sql.append(" group by a.fId");
			sql.append(" having sum(ifnull(a.fAmount,0)+ifnull(a.ffees,0)) > 0");
		}
		SQLQuery queryObject = getSession().createSQLQuery(sql.toString());
		List allList = queryObject.list();
		if (allList == null || allList.size() == 0) {
			return null;
		}

		Iterator it = allList.iterator();
		while (it.hasNext()) {
			VirtualOperationLogVo vo = new VirtualOperationLogVo();
			Object[] o = (Object[]) it.next();
			vo.setFloginName(o[0].toString());
			vo.setFrealName(o[1].toString());
			vo.setFtelephone(o[2].toString());
			if(isRecharge){
				vo.setRechargeNum(o[3].toString());
				vo.setRechargeAddress(o[4].toString());
				vo.setRechargeDate(o[5].toString());
			}else{
				vo.setWithdrawNum(o[5].toString());
				vo.setWithdrawFees(o[4].toString());
				vo.setWithdrawUserGetNum(o[3].toString());
				vo.setWithdrawAddress(o[6].toString());
				vo.setWithdrawDate(o[7].toString());
			}

			list.add(vo);
		}

		return list;
	}


	/**
	 * 查询日期范围内，该用户提币的次数、总额度信息(不含取消)
	 * @param userId
	 * @param startDate
	 * @param endDate
	 * @param virCoinFid
	 * @return
	 * 如果查询一天的，则startDate和endDate传递一样的值
	 */
	public Map<String, String> queryUserWithNum(Integer userId, String startDate, String endDate, int virCoinFid){
		Map<String, String> map = new HashedMap();
		map.put("amount", "0");
		map.put("num", "0");
		StringBuffer sql = new StringBuffer();
		sql.append(" select ifnull(sum(fAmount),0) as amount, ifnull(count(fid),0) as num from fvirtualcaptualoperation a  ");
		sql.append(" where a.FUs_fId2 = "+userId+" and a.ftype = 2 and fStatus <> " + VirtualCapitalOperationOutStatusEnum.Cancel);
		sql.append(" and date_format(fCreateTime, '%Y-%m-%d') >= '" + startDate + "' ");
		sql.append(" and date_format(fCreateTime, '%Y-%m-%d') <= '" + endDate + "' ");
		sql.append(" and a.fVi_fId2 = " + virCoinFid);
		SQLQuery queryObject = getSession().createSQLQuery(sql.toString());
		List allList = queryObject.list();
		//查询不到数据，则表示没有提币，直接返回map即可
		if (allList == null || allList.size() == 0) {
			return map;
		}

		Iterator it = allList.iterator();
		//查询出来，只有一条数据
		while (it.hasNext()) {
			Object[] o = (Object[]) it.next();
			map.put("amount", o[0].toString());
			map.put("num", o[1].toString());
		}
		return map;
	}

	/**
	 * 是否是第一次使用该地址进行提币
	 * @param userId
	 * @param withdraw_virtual_address
	 * @return true是，false不是
	 * 判断依据：未成功提现过的地址
	 */
	public boolean isNewWithAddr(Integer userId, String withdraw_virtual_address) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select count(fid) from fvirtualcaptualoperation  ");
		sql.append(" where FUs_fId2 = "+userId+" and fType =  " + VirtualCapitalOperationTypeEnum.COIN_OUT);
		sql.append(" and withdraw_virtual_address ='"+withdraw_virtual_address+"' ");
		sql.append(" and fStatus =   " + VirtualCapitalOperationOutStatusEnum.OperationSuccess);
		SQLQuery queryObject = getSession().createSQLQuery(sql.toString());
		int result = Integer.valueOf(queryObject.uniqueResult().toString());
		if(result > 0) {
			return false;
		}else {
			return true;
		}
	}

	/**
	 * 查询用户某日，自动提币的次数(不含取消)
	 * @param userId
	 * @param date
	 * @param virCoinFid
	 * @return
	 */
	public Map<String, String> queryUserAutoWithNum(Integer userId, String date, int virCoinFid) {
		Map<String, String> map = new HashedMap();
		map.put("amount", "0");
		map.put("num", "0");
		StringBuffer sql = new StringBuffer();
		sql.append("  select ifnull(sum(fAmount),0) as amount, ifnull(count(fid),0) as num  ");
		sql.append("  from fvirtualcaptualoperation ");
		sql.append("  where FUs_fId2 = " + userId + " and ifnull(mailLinkUUID, '')<>'' ");
		sql.append("  and date_format(fCreateTime, '%Y-%m-%d') = '" + date + "' ");
		sql.append("  and fStatus <> " + VirtualCapitalOperationOutStatusEnum.Cancel);
		sql.append(" and fVi_fId2 = " + virCoinFid);
		SQLQuery queryObject = getSession().createSQLQuery(sql.toString());
		List allList = queryObject.list();
		//查询不到数据，则表示没有提币，直接返回map即可
		if (allList == null || allList.size() == 0) {
			return map;
		}

		Iterator it = allList.iterator();
		//查询出来，只有一条数据
		while (it.hasNext()) {
			Object[] o = (Object[]) it.next();
			map.put("amount", o[0].toString());
			map.put("num", o[1].toString());
		}
		return map;
	}


	//修改虚拟币操作流水的状态
	public int updateVirtualCapitalOperationStatus(int fstatus, int fid, boolean isUpdateMailVerDate) {
		String sql = " update fvirtualcaptualoperation set fStatus = ?  ";
		if(isUpdateMailVerDate) {
			sql += " ,mailLinkVerDate=now() ";
		}
		sql += " where fId = ?";
		SQLQuery queryObject = getSession().createSQLQuery(sql.toString());
		queryObject.setParameter(0, fstatus);
		queryObject.setParameter(1, fid);
		int result = queryObject.executeUpdate();
		return result;
	}
}