package com.ruizton.main.service.front;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ruizton.main.Enum.*;
import com.ruizton.main.auto.TaskList;
import com.ruizton.main.comm.ValidateMap;
import com.ruizton.main.dao.*;
import com.ruizton.main.log.LOG;
import com.ruizton.main.model.*;
import com.ruizton.main.model.vo.AutoWithConfigVo;
import com.ruizton.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FrontVirtualCoinService {
	@Autowired
	private FvirtualcointypeDAO fvirtualcointypeDAO ;
	@Autowired
	private FwithdrawfeesDAO withdrawfeesDAO;
	@Autowired
	private FvirtualaddressDAO fvirtualaddressDAO ;
	@Autowired
	private FvirtualaddressWithdrawDAO fvirtualaddressWithdrawDAO ;
	@Autowired
	private FvirtualcaptualoperationDAO fvirtualcaptualoperationDAO ;
	@Autowired
	private FvirtualwalletDAO fvirtualwalletDAO ;
	@Autowired
	private FsystemargsDAO systemargsDAO;

	@Autowired
	protected FvalidateemailDAO validateemailsDAO ;
	@Autowired
	protected EmailvalidateDAO emailvalidateDAO ;
	@Autowired
	private TaskList taskList ;
	@Autowired
	private ValidateMap validateMap ;
	
	public List<Fvirtualcointype> findFvirtualCoinType(int status,int coinType){
		List<Fvirtualcointype> list = this.fvirtualcointypeDAO.findByParam(0, 0, " where fstatus="+status+" and ftype ="+coinType+" order by fid asc ", false, Fvirtualcointype.class) ;
		return list ;
	}
	
	public Fvirtualcointype findFvirtualCoinById(int id){
		Fvirtualcointype fvirtualcointype = this.fvirtualcointypeDAO.findById(id) ;
		return fvirtualcointype ;
	}
	
	public Fvirtualcointype findFirstFirtualCoin(){
		Fvirtualcointype fvirtualcointype = null ;
		String filter ="where fstatus="+VirtualCoinTypeStatusEnum.Normal+" and ftype="+CoinTypeEnum.COIN_VALUE;
		List<Fvirtualcointype> list = this.fvirtualcointypeDAO.list(0, 0, filter, false);
		if(list.size()>0){
			fvirtualcointype = list.get(0) ;
		}else{
			fvirtualcointype = (Fvirtualcointype)this.fvirtualcointypeDAO.findAll(CoinTypeEnum.COIN_VALUE,0).get(0);
		}
		return fvirtualcointype ;
	}
	
	public Fvirtualcointype findFirstFirtualCoin_Wallet(){
		Fvirtualcointype fvirtualcointype = null ;
		String filter = "where fstatus="+VirtualCoinTypeStatusEnum.Normal+" and FIsWithDraw=1";
		List<Fvirtualcointype> list = this.fvirtualcointypeDAO.list(0, 0, filter, false);
		if(list.size()>0){
			fvirtualcointype = list.get(0) ;
		}
		return fvirtualcointype ;
	}
	
	public Fvirtualaddress findFvirtualaddress(Fuser fuser,Fvirtualcointype fvirtualcointype){
		return this.fvirtualaddressDAO.findFvirtualaddress(fuser, fvirtualcointype) ;
	}
	
	public List<Fvirtualaddress> findFvirtualaddress(Fvirtualcointype fvirtualcointype,String address){
		return this.fvirtualaddressDAO.findFvirtualaddress(fvirtualcointype, address) ;
	}
	
	public FvirtualaddressWithdraw findFvirtualaddressWithdraw(int fid){
		return this.fvirtualaddressWithdrawDAO.findById(fid);
	}
	
	public List<FvirtualaddressWithdraw> findFvirtualaddressWithdraws(int firstResult, int maxResults,
			String filter,boolean isFY) {
		return this.fvirtualaddressWithdrawDAO.list(firstResult, maxResults, filter,isFY);
	}
	
	public int findFvirtualcaptualoperationCount(
			Fuser fuser,int type[],int status[],Fvirtualcointype[] fvirtualcointypes,String order){
		return this.fvirtualcaptualoperationDAO.findFvirtualcaptualoperationCount(fuser, type, status, fvirtualcointypes, order) ;
	}
	
	public List<Fvirtualcaptualoperation> findFvirtualcaptualoperations(int firstResult, int maxResults,String filter, boolean isFY){
		List<Fvirtualcaptualoperation> list =  this.fvirtualcaptualoperationDAO.findByParam(firstResult, maxResults, filter, isFY, Fvirtualcaptualoperation.class) ;
		for (Fvirtualcaptualoperation fvirtualcaptualoperation : list) {
			fvirtualcaptualoperation.getFvirtualcointype().getFid();
			fvirtualcaptualoperation.getFvirtualcointype().getFname() ;
		}
		return list ;
	}
	public int findFvirtualcaptualoperationsCount(String filter){
		return this.fvirtualcaptualoperationDAO.findByParamCount(filter, Fvirtualcaptualoperation.class) ;
	}
	
	public void updateFvirtualaddressWithdraw(FvirtualaddressWithdraw fvirtualaddressWithdraw){
		if (fvirtualaddressWithdraw.getFvirtualcointype().isFisEth()){
			fvirtualaddressWithdraw.setFadderess(fvirtualaddressWithdraw.getFadderess().toLowerCase());
		}
		this.fvirtualaddressWithdrawDAO.save(fvirtualaddressWithdraw) ;
	}
	
	public void updateDelFvirtualaddressWithdraw(FvirtualaddressWithdraw fvirtualaddressWithdraw){
		this.fvirtualaddressWithdrawDAO.delete(fvirtualaddressWithdraw) ;
	}
	
	public Fwithdrawfees findFfees(int virtualCoinTypeId,int level){
		return this.withdrawfeesDAO.findFfee(virtualCoinTypeId, level) ;
	}

	public String getSystemArgs(String key){
		String value = null ;
		List<Fsystemargs> list = this.systemargsDAO.findByFkey(key) ;
		if(list.size()>0){
			value = list.get(0).getFvalue() ;
		}
		return value ;
	}
	
	public void updateWithdrawBtc(FvirtualaddressWithdraw fvirtualaddressWithdraw,Fvirtualcointype fvirtualcointype,Fvirtualwallet fvirtualwallet ,double withdrawAmount,double ffees,Fuser fuser, boolean isAuto, String ip){
		try {
			synchronized (this) {

				String UUID = null;
				//插入邮件信息，发送给用户
				if(isAuto) {
					UUID  = sendWithdrawEmail(fuser, ip);
				}

				//2017-04-07 转换计算方式
				//修改用户钱包数据
				fvirtualwallet.setFtotal(Utils.sub(fvirtualwallet.getFtotal(), withdrawAmount)) ;
				fvirtualwallet.setFfrozen(Utils.add(fvirtualwallet.getFfrozen(), withdrawAmount)) ;
				fvirtualwallet.setFlastUpdateTime(Utils.getTimestamp()) ;
				this.fvirtualwalletDAO.attachDirty(fvirtualwallet) ;

				//插入提现流水
				Fvirtualcaptualoperation fvirtualcaptualoperation = new Fvirtualcaptualoperation() ;
				fvirtualcaptualoperation.setFamount(Utils.sub(withdrawAmount, ffees)) ;
				fvirtualcaptualoperation.setFcreateTime(Utils.getTimestamp()) ;
				fvirtualcaptualoperation.setFfees(ffees) ;
				fvirtualcaptualoperation.setFlastUpdateTime(Utils.getTimestamp()) ;
				if(isAuto) {  //是邮件确认自动
					fvirtualcaptualoperation.setFstatus(VirtualCapitalOperationOutStatusEnum.WaitMailVer);
					fvirtualcaptualoperation.setMailLinkUUID(UUID);
				}else{
					fvirtualcaptualoperation.setFstatus(VirtualCapitalOperationOutStatusEnum.WaitForOperation) ;
				}

				fvirtualcaptualoperation.setFtype(VirtualCapitalOperationTypeEnum.COIN_OUT) ;
				fvirtualcaptualoperation.setFuser(fuser) ;
				fvirtualcaptualoperation.setFvirtualcointype(fvirtualcointype) ;
				fvirtualcaptualoperation.setWithdraw_virtual_address(fvirtualaddressWithdraw.getFadderess()) ;
				this.fvirtualcaptualoperationDAO.save(fvirtualcaptualoperation) ;
			}

		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
	
	public void addFvirtualcaptualoperation(Fvirtualcaptualoperation fvirtualcaptualoperation){
		this.fvirtualcaptualoperationDAO.save(fvirtualcaptualoperation) ;
	}
	
	public List<Fvirtualcaptualoperation> findFvirtualcaptualoperationByProperty(String key,Object value){
		return this.fvirtualcaptualoperationDAO.findByProperty(key, value) ;
	}
	
	public Fvirtualcaptualoperation findFvirtualcaptualoperationById(int id){
		return this.fvirtualcaptualoperationDAO.findById(id) ;
	}
	
	//比特币自动充值并加币
	public void updateFvirtualcaptualoperationCoinIn(Fvirtualcaptualoperation fvirtualcaptualoperation) throws Exception{
		try {
			Fvirtualcaptualoperation real = this.fvirtualcaptualoperationDAO.findById(fvirtualcaptualoperation.getFid()) ;
			if(real!=null && real.getFstatus()!=VirtualCapitalOperationInStatusEnum.SUCCESS){
				real.setFstatus(fvirtualcaptualoperation.getFstatus()) ;
				real.setFconfirmations(fvirtualcaptualoperation.getFconfirmations()) ;
				real.setFlastUpdateTime(Utils.getTimestamp()) ;
				real.setFamount(fvirtualcaptualoperation.getFamount());
				this.fvirtualcaptualoperationDAO.attachDirty(real) ;
				
				if(real.getFstatus()==VirtualCapitalOperationInStatusEnum.SUCCESS && real.isFhasOwner()){
					Fvirtualcointype fvirtualcointype = this.fvirtualcointypeDAO.findById(real.getFvirtualcointype().getFid()) ;
					Fvirtualwallet fvirtualwallet = this.fvirtualwalletDAO.findVirtualWallet(real.getFuser().getFid(), fvirtualcointype.getFid()) ;
					fvirtualwallet.setFtotal(Utils.add(fvirtualwallet.getFtotal(), real.getFamount())) ;
					fvirtualwallet.setFlastUpdateTime(Utils.getTimestamp()) ;
					this.fvirtualwalletDAO.attachDirty(fvirtualwallet) ;
				}
			}
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
	
	public List<Fvirtualaddress> findFvirtualaddressByProperty(String key,Object value){
		List<Fvirtualaddress> fvirtualaddresses = this.fvirtualaddressDAO.findByProperty(key, value) ;
		for (Fvirtualaddress fvirtualaddress : fvirtualaddresses) {
			fvirtualaddress.getFuser().getFnickName() ;
		}
		return fvirtualaddresses ;
	}
	
	public boolean isExistsCanWithdrawCoinType(){
		List<Fvirtualcointype> fvirtualcointypes = this.fvirtualcointypeDAO.findByParam(0, 0, " where FIsWithDraw=1 and fstatus=1 ", false, Fvirtualcointype.class) ;
		return fvirtualcointypes.size()>0 ;
	}


	public List<Fvirtualcointype> findAllFvirtualCoinType() {
		return this.fvirtualcointypeDAO.findAll();
	}


	/**
	 * 修改自动提现配置
	 * @param list
	 * @return
	 */
	public int updateAutoWithConfig( List<AutoWithConfigVo> list) {
		if(list == null || list.size() == 0) {
			return 0;
		}
		int result = 0;
		for(AutoWithConfigVo vo : list) {
			result = this.fvirtualcointypeDAO.updateAutoWithConfig(vo);
			if(result == 0) {
				LOG.i(this, "修改系统自动提现配置错误，详细数据信息：" + JsonHelper.obj2JsonStr(vo));
				break;
			}
		}
		return result;
	}

	public void saveOrUpdate(List<Fvirtualcointype> list) {
		for(Fvirtualcointype fvirtualcointype:list){
			fvirtualcointypeDAO.saveOrUpdate(fvirtualcointype);
		}
	}

	//查询日期范围内，该用户提币的次数、总额度信息(不含取消)
	public Map<String, String> queryUserWithNum(Integer userId, String startDate, String endDate, int virCoinFid) {
		return this.fvirtualcaptualoperationDAO.queryUserWithNum(userId, startDate, endDate, virCoinFid);
	}

	//查询一天的，该用户提币的次数、总额度信息(不含取消)
	public Map<String, String> queryUserWithNum(Integer userId, String date, int virCoinFid) {
		return this.fvirtualcaptualoperationDAO.queryUserWithNum(userId, date, date, virCoinFid);
	}

	//是否是第一次使用该地址进行提币
	public boolean isNewWithAddr(Integer userId, String withdraw_virtual_address) {
		return this.fvirtualcaptualoperationDAO.isNewWithAddr(userId, withdraw_virtual_address);
	}

	//查询用户某日，自动提币的次数(不含取消)
	public Map<String, String> queryUserAutoWithNum(Integer userId, String date,int virCoinFid) {
		return this.fvirtualcaptualoperationDAO.queryUserAutoWithNum(userId, date, virCoinFid);
	}

	//根据用户id、提现地址查询提现地址详细信息
	public FvirtualaddressWithdraw findWithdrawAddress(int userId, String address) {
		return this.fvirtualaddressWithdrawDAO.findWithdrawAddress(userId, address);
	}

	/**
	 * 虚拟币提现发送邮件
	 * @param fuser
	 * @param ip
	 * @return
	 */
	public String sendWithdrawEmail(Fuser fuser, String ip) {
		String UUID = Utils.UUID() ;

		Fvalidateemail validateemails = new Fvalidateemail() ;
		String webName  = this.getSystemArgs(ConstantKeys.WEB_NAME);
		validateemails.setFtitle(webName+"提现虚拟币") ;
		validateemails.setFcontent(
				this.getSystemArgs(ConstantKeys.withdraw_virtual)
						.replace("#date#", DateHelper.date2String(new Date(), DateHelper.DateFormatType.YearMonthDay_HourMinuteSecond))
						.replace("#url#", new Constant().Domain+"account/email/withdrawvirtual.html?uid="+fuser.getFid()+"&&uuid="+UUID)
						.replace("#fulldomain#", this.getSystemArgs(ConstantKeys.fulldomain))
						.replace("#englishName#", webName)
						.replace("#webName#",webName)) ;
		validateemails.setFcreateTime(Utils.getTimestamp()) ;
		validateemails.setFstatus(ValidateMailStatusEnum.Not_send) ;
		validateemails.setFuser(fuser) ;
		validateemails.setEmail(fuser.getFemail());
		validateemailsDAO.save(validateemails);

		//验证并对应到用户的UUID
		Emailvalidate emailvalidate = new Emailvalidate() ;
		emailvalidate.setFcreateTime(Utils.getTimestamp()) ;
		emailvalidate.setFmail(fuser.getFemail()) ;
		emailvalidate.setFuser(fuser) ;
		emailvalidate.setFuuid(UUID) ;
		emailvalidate.setType(SendMailTypeEnum.FindPassword) ;
		this.emailvalidateDAO.save(emailvalidate) ;

		//加入邮件队列
		this.taskList.returnMailList(validateemails.getFid()) ;

		this.validateMap.putMailMap(ip+"_"+SendMailTypeEnum.WithdrawCncy, emailvalidate) ;
		return UUID;
	}

	/**
	 * 重新发送提现邮件
	 * @param fuser
	 * @param ip
	 * @return
	 */
	public boolean resendWithdrawEmail(Fuser fuser, String ip, Fvirtualcaptualoperation fvirtualcaptualoperation) {
		boolean flag = false;
		try {
			//重发邮件
			String uuid = this.sendWithdrawEmail(fuser, ip);
			//修改uuid值
			fvirtualcaptualoperation.setMailLinkUUID(uuid);
			fvirtualcaptualoperationDAO.attachDirty(fvirtualcaptualoperation);
			flag = true;
		}catch (Exception e) {
			e.printStackTrace();
			LOG.i(this, "重发提币邮件异常");
		}

		return flag;
	}

	public boolean isExistsFaddress(String withdrawAddr, int userId) {
		LOG.i(this, "新增地址地址withdrawAddr=" + withdrawAddr + ",用户id=" + userId + ",进行校验");
		List<FvirtualaddressWithdraw> list	=fvirtualcointypeDAO.findByParam(0,0," where fadderess = ? and fuser.fid = ? ",false, FvirtualaddressWithdraw.class,withdrawAddr,userId);
		return list.size()>0;
	}
}
