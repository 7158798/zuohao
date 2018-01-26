package com.ruizton.main.service.admin;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.ruizton.main.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruizton.main.dao.FcapitaloperationDAO;
import com.ruizton.main.dao.FintrolinfoDAO;
import com.ruizton.main.dao.FscoreDAO;
import com.ruizton.main.dao.FuserDAO;
import com.ruizton.main.dao.FvirtualwalletDAO;
import com.ruizton.util.Utils;

@Service
public class CapitaloperationService {
	@Autowired
	private FcapitaloperationDAO fcapitaloperationDAO;
	@Autowired
	private FintrolinfoDAO fintrolinfoDAO;
	@Autowired
	private FuserDAO fuserDao;
	@Autowired
	private FscoreDAO fscoreDAO;
	@Autowired
	private SystemArgsService systemArgsService;
	@Autowired
	private FvirtualwalletDAO fvirtualwalletDAO ;

	public Fcapitaloperation findById(int id) {
		Fcapitaloperation fcapitaloperation = this.fcapitaloperationDAO.findById(id);
		return fcapitaloperation;
	}

	public void saveObj(Fcapitaloperation obj) {
		this.fcapitaloperationDAO.save(obj);
	}

	public void deleteObj(int id) {
		Fcapitaloperation obj = this.fcapitaloperationDAO.findById(id);
		this.fcapitaloperationDAO.delete(obj);
	}

	public void updateObj(Fcapitaloperation obj) {
		this.fcapitaloperationDAO.attachDirty(obj);
	}

	public List<Fcapitaloperation> findByProperty(String name, Object value) {
		return this.fcapitaloperationDAO.findByProperty(name, value);
	}

	public List<Fcapitaloperation> findAll() {
		return this.fcapitaloperationDAO.findAll();
	}

	public List<Fcapitaloperation> list(int firstResult, int maxResults,
			String filter,boolean isFY) {
		List<Fcapitaloperation> all = this.fcapitaloperationDAO.list(firstResult, maxResults, filter,isFY);
		for (Fcapitaloperation fcapitaloperation : all) {
			fcapitaloperation.getFuser().getFemail() ;
			if(fcapitaloperation.getfAuditee_id() != null){
				fcapitaloperation.getfAuditee_id().getFname();
			}
			if(fcapitaloperation.getSystembankinfo() != null) fcapitaloperation.getSystembankinfo().getFbankAddress();
		}
		return all;
	}

	public void updateCapital(Fcapitaloperation capitaloperation,Fvirtualwallet fvirtualwallet,boolean isRecharge) throws RuntimeException {
		try {
			this.fcapitaloperationDAO.attachDirty(capitaloperation);
			this.fvirtualwalletDAO.attachDirty(fvirtualwallet);
//			if(isRecharge){
//				int userid = capitaloperation.getFuser().getFid();
//				Fuser fuser = this.fuserDao.findById(userid);
//				Fscore fscore = fuser.getFscore();
//				double firstTimeRechargeRate = Double.valueOf(this.systemArgsService.getValue("firstTimeRechargeRate"));
//				if(!fscore.isFissign() && firstTimeRechargeRate >0){
//					fscore.setFissign(true);
//					this.fscoreDAO.attachDirty(fscore);
//					double amt = Utils.getDouble(capitaloperation.getFamount()*firstTimeRechargeRate, 4);
//					fvirtualwallet.setFtotal(fvirtualwallet.getFtotal()+amt);
//					Fintrolinfo info = new Fintrolinfo();
//					info.setFcreatetime(Utils.getTimestamp());
//					info.setFiscny(true);
//					info.setFuser(fuser);
//					info.setFname("人民币");
//					info.setFtitle("首次充值,奖励￥"+new BigDecimal(amt).setScale(4, BigDecimal.ROUND_HALF_UP));
//					info.setFqty(amt);
//					this.fintrolinfoDAO.save(info);
//				}
//			}
//			this.fvirtualwalletDAO.attachDirty(fvirtualwallet);
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
	
	public void updateCapital(Fcapitaloperation fcapitaloperation,Fvirtualwallet fvirtualwallet,Fscore fscore,Fintrolinfo info){
		try {
			this.fcapitaloperationDAO.attachDirty(fcapitaloperation);
			this.fvirtualwalletDAO.attachDirty(fvirtualwallet);
			this.fscoreDAO.attachDirty(fscore);
			this.fintrolinfoDAO.save(info);
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
	
	public Map getTotalAmount(int type,String fstatus,boolean isToday) {
		return this.fcapitaloperationDAO.getTotalAmount(type, fstatus,isToday);
	}
	
	public Map getTotalAmountIn(int type,String fstatus,boolean isToday) {
		return this.fcapitaloperationDAO.getTotalAmountIn(type, fstatus,isToday);
	}
	
	public List getTotalGroup(String filter) {
		return this.fcapitaloperationDAO.getTotalGroup(filter);
	}
	
	public List getTotalAmountForReport(String filter) {
		return this.fcapitaloperationDAO.getTotalAmountForReport(filter);
	}
	
	public List getTotalOperationlog(String filter) {
		return this.fcapitaloperationDAO.getTotalOperationlog(filter);
	}
	
	public Map getTotalAmount(int type,String fstatus,boolean isToday,boolean isFee) {
		return this.fcapitaloperationDAO.getTotalAmount(type, fstatus,isToday,isFee);
	}

	public Double getTotalAmountForUser(Fuser fuser){
		return this.fcapitaloperationDAO.getTotalPayAmountForUser(fuser);

	}

	/**
	 * 各支付方式充值金额分类
	 * @param date
	 * @return
	 */
	public List<Map<String, String>>  rechargeRmbStatsByType(String date) {
		return this.fcapitaloperationDAO.rechargeRmbStatsByType(date);
	}

	/**
	 * 统计人民币充值，提现流水
	 * @param type
	 * @param date
	 * @param isRecharge
	 * @return
	 */
	public Map<String, String>  capitalOperationLogStats(Integer type, String date, boolean isRecharge) {
		return this.fcapitaloperationDAO.capitalOperationLogStats(type, date, isRecharge);
	}

	/**
	 * 查询人民币充值，提现流水
	 * @param type
	 * @param date
	 * @param isRecharge
	 * @return
	 */
	public List<CapitalOperationLogVo> capitalOperationLog(Integer type, String date, boolean isRecharge) {
		return this.fcapitaloperationDAO.capitalOperationLog(type, date, isRecharge);
	}

	/**
	 * 三方支付的次数
	 * @param remittanceType  充值类型
	 * @param userId  用户id
	 * @return 充值次数
	 */
	public int queryCountByRemittanceType(String remittanceType, Integer userId) {
		return this.fcapitaloperationDAO.queryCountByRemittanceType(remittanceType, userId);
	}

	//获取用户当天提现总次数
	public long getDayCount(int fid, int fType) {

		return this.fcapitaloperationDAO.getDayCount(fid,fType);

	}

	//获取用户当天提现金额
	public double getDayLimit(int fid, int fType) {
		return this.fcapitaloperationDAO.getDayLimit(fid,fType);
	}

	public double getMonthLimit(int fid, int fType) {
		return this.fcapitaloperationDAO.getMonthLimit(fid,fType);

	}
}