package com.ruizton.main.service.admin;

import java.util.List;
import java.util.Map;

import com.ruizton.main.Enum.CoinType;
import com.ruizton.main.log.LOG;
import com.ruizton.main.model.VirtualOperationLogVo;
import com.ruizton.util.antshare.ANSUtils;
import com.ruizton.util.antshare.resp.TransactionResp;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruizton.main.dao.FvirtualcaptualoperationDAO;
import com.ruizton.main.dao.FvirtualcointypeDAO;
import com.ruizton.main.dao.FvirtualwalletDAO;
import com.ruizton.main.model.Fvirtualcaptualoperation;
import com.ruizton.main.model.Fvirtualcointype;
import com.ruizton.main.model.Fvirtualwallet;
import com.ruizton.util.BTCUtils;
import com.ruizton.util.ETHUtils;
import com.ruizton.util.IWalletUtil;

@Service
public class VirtualCapitaloperationService {
	@Autowired
	private FvirtualcaptualoperationDAO virtualcaptualoperationDAO;
	@Autowired
	private FvirtualwalletDAO virtualwalletDAO;
	@Autowired
	private SystemArgsService systemArgsService;
	@Autowired
	private FvirtualcointypeDAO fvirtualcointypeDAO ;

	public Fvirtualcaptualoperation findById(int id) {
		Fvirtualcaptualoperation info = this.virtualcaptualoperationDAO.findById(id);
		info.getFuser().getFnickName();
		info.getFvirtualcointype().getFname();
		return info;
	}

	public void saveObj(Fvirtualcaptualoperation obj) {
		this.virtualcaptualoperationDAO.save(obj);
	}

	public void deleteObj(int id) {
		Fvirtualcaptualoperation obj = this.virtualcaptualoperationDAO
				.findById(id);
		this.virtualcaptualoperationDAO.delete(obj);
	}

	public void updateObj(Fvirtualcaptualoperation obj) {
		this.virtualcaptualoperationDAO.attachDirty(obj);
	}

	public List<Fvirtualcaptualoperation> findByProperty(String name,
			Object value) {
		return this.virtualcaptualoperationDAO.findByProperty(name, value);
	}

	public List<Fvirtualcaptualoperation> findAll() {
		return this.virtualcaptualoperationDAO.findAll();
	}

	public List<Fvirtualcaptualoperation> list(int firstResult, int maxResults,
			String filter, boolean isFY) {
		List<Fvirtualcaptualoperation> all = this.virtualcaptualoperationDAO
				.list(firstResult, maxResults, filter, isFY);
		for (Fvirtualcaptualoperation virtualcaptualoperation : all) {
			if(virtualcaptualoperation.getFuser() != null){
				virtualcaptualoperation.getFuser().getFemail();
			}
			if(virtualcaptualoperation.getFvirtualcointype() != null){
				virtualcaptualoperation.getFvirtualcointype().getfShortName();
			}
			
		}
		return all;
	}

	public void updateCapital(Fvirtualcaptualoperation virtualcaptualoperation,
			Fvirtualwallet virtualwallet,IWalletUtil iWalletUtil) throws RuntimeException {
		if(virtualcaptualoperation.getFtradeUniqueNumber() != null
				&& virtualcaptualoperation.getFtradeUniqueNumber().trim().length()>0){
			return;
		}
		
		Fvirtualcointype fvirtualcointype = this.fvirtualcointypeDAO.findById(virtualcaptualoperation.getFvirtualcointype().getFid()) ;
		if(iWalletUtil instanceof ETHUtils) {
			ETHUtils ethUtils = (ETHUtils) iWalletUtil;

			try {
				boolean isPasswordTrue = ethUtils.walletpassphrase(fvirtualcointype.getMainAddr());
				if (!isPasswordTrue) {
					throw new RuntimeException("钱包验证失败");
				}
				ethUtils.lockAccount(fvirtualcointype.getMainAddr());
			} catch (Exception e2) {
				LOG.e(this,e2.getMessage(),e2);
				throw new RuntimeException("钱包验证失败");
			}

			String address = virtualcaptualoperation.getWithdraw_virtual_address();
			double amount = virtualcaptualoperation.getFamount();
			try {
				this.virtualcaptualoperationDAO.attachDirty(virtualcaptualoperation);
				this.virtualwalletDAO.attachDirty(virtualwallet);
			} catch (Exception e1) {
				LOG.e(this,e1.getMessage(),e1);
				throw new RuntimeException("发送失败");
			}

			try {
				String isOpenAutoWidthCoin = this.systemArgsService.getValue("isOpenAutoWidthCoin").trim();
				if (isOpenAutoWidthCoin.equals("1")) {
					String flag = ethUtils.sendtoaddressValue(fvirtualcointype.getMainAddr(), address, amount, 333333);
					if (flag != null && !"".equals(flag)) {
						virtualcaptualoperation.setFtradeUniqueNumber(flag);
						this.virtualcaptualoperationDAO.attachDirty(virtualcaptualoperation);
					}
				}
			} catch (Exception e) {
				LOG.e(this,e.getMessage(),e);
			}
		} else if (StringUtils.equals(fvirtualcointype.getfShortName(), CoinType.ANS)){
			// 小蚁股
			String address = virtualcaptualoperation.getWithdraw_virtual_address();
			Long amount = new Double(virtualcaptualoperation.getFamount()).longValue();
			ANSUtils ansUtils = (ANSUtils) iWalletUtil;
			try {
				this.virtualcaptualoperationDAO.attachDirty(virtualcaptualoperation);
				this.virtualwalletDAO.attachDirty(virtualwallet);
			} catch (Exception e1) {
				LOG.e(this,e1.getMessage(),e1);
				throw new RuntimeException("发送失败");
			}

			try {
				String isOpenAutoWidthCoin = this.systemArgsService.getValue("isOpenAutoWidthCoin").trim();
				if (isOpenAutoWidthCoin.equals("1")) {
					TransactionResp resp = ansUtils.ans_sendtoaddress(ANSUtils.ANS_ASSET_ID, address, amount);
					if (resp.getResult() != null && !"".equals(resp.getResult().getTxid())) {
						// 交易id
						virtualcaptualoperation.setFtradeUniqueNumber(resp.getResult().getTxid());
						this.virtualcaptualoperationDAO.attachDirty(virtualcaptualoperation);
					}
				}
			} catch (Exception e) {
				LOG.e(this,e.getMessage(),e);
			}

		}else{
			BTCUtils btcUtils = (BTCUtils)iWalletUtil ;
			try {
				boolean isPasswordTrue = btcUtils.walletpassphrase(10);
				if(!isPasswordTrue){
					throw new RuntimeException("钱包验证失败");
				}
			} catch (Exception e2) {
				LOG.e(this,e2.getMessage(),e2);
				throw new RuntimeException("钱包验证失败");
			}
			
			String address = virtualcaptualoperation.getWithdraw_virtual_address();
			double amount = virtualcaptualoperation.getFamount();
			try {
				this.virtualcaptualoperationDAO.attachDirty(virtualcaptualoperation);
				this.virtualwalletDAO.attachDirty(virtualwallet);
			} catch (Exception e1) {
				LOG.e(this,e1.getMessage(),e1);
				throw new RuntimeException("发送失败");
			}
			
			try {
				String isOpenAutoWidthCoin = this.systemArgsService.getValue("isOpenAutoWidthCoin").trim();
				if(isOpenAutoWidthCoin.equals("1")){
					String flag = btcUtils.sendtoaddressValue(address,amount,virtualcaptualoperation.getFfees(),virtualcaptualoperation.getFid().toString());
					if(flag != null && !"".equals(flag)){
						virtualcaptualoperation.setFtradeUniqueNumber(flag);
						this.virtualcaptualoperationDAO.attachDirty(virtualcaptualoperation);
					}
				}
			} catch (Exception e) {
				LOG.e(this,e.getMessage(),e);
			}
		}
		
		
		
	}
	
	public void updateCapital(Fvirtualcaptualoperation virtualcaptualoperation,
			Fvirtualwallet virtualwallet) throws RuntimeException {
		try {
			this.virtualcaptualoperationDAO.attachDirty(virtualcaptualoperation);
			this.virtualwalletDAO.attachDirty(virtualwallet);
		} catch (Exception e1) {
			throw new RuntimeException();
		}
	}
	
	public List<Map> getTotalAmount(int type,String fstatus,boolean isToday) {
		return this.virtualcaptualoperationDAO.getTotalQty(type, fstatus,isToday);
	}
	
	public List getTotalGroup(String filter) {
		return this.virtualcaptualoperationDAO.getTotalGroup(filter);
	}
	
	public void updateCharge(Fvirtualcaptualoperation v,Fvirtualwallet w) {
		try {
			this.virtualcaptualoperationDAO.attachDirty(v);
			this.virtualwalletDAO.attachDirty(w);
		} catch (Exception e) {
			throw new RuntimeException();
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
		return virtualcaptualoperationDAO.virtualOperationLogStats(type, vifid, date, isRecharge);
	}

	/**
	 * 查询充值或提现虚拟币流水
	 * @param type  类型 1充值  2提现
	 * @param vifid  虚拟币id
	 * @param date 对账时间  yyyy-mm-dd格式
	 * @param isRecharge  是否是充值
	 * @return
	 */
	public List<VirtualOperationLogVo> virtualOperationLog(Integer type, Integer vifid, String date, boolean isRecharge) {
		return virtualcaptualoperationDAO.virtualOperationLog(type,vifid,date, isRecharge);
	}

	//修改虚拟币操作流水的状态
	public int updateVirtualCapitalOperationStatus(int fstatus, int fid, boolean isUpdateMailVerDate) {
		return virtualcaptualoperationDAO.updateVirtualCapitalOperationStatus(fstatus, fid, isUpdateMailVerDate);
	}


}