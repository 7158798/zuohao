package com.ruizton.main.service.front;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.ruizton.main.Enum.IntegralTypeEnum;
import com.ruizton.main.dao.integral.FintegralactivityDAO;
import com.ruizton.main.log.LOG;
import com.ruizton.main.model.integral.Fintegralactivity;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruizton.main.Enum.CoinTypeEnum;
import com.ruizton.main.auto.RealTimeData;
import com.ruizton.main.comm.ConstantMap;
import com.ruizton.main.dao.FassetDAO;
import com.ruizton.main.dao.FvirtualcointypeDAO;
import com.ruizton.main.dao.FvirtualwalletDAO;
import com.ruizton.main.model.Fasset;
import com.ruizton.main.model.Ftrademapping;
import com.ruizton.main.model.Fvirtualcointype;
import com.ruizton.main.model.Fvirtualwallet;
import com.ruizton.main.service.admin.TradeMappingService;
import com.ruizton.util.Utils;

@Service
public class AssetService {

	@Autowired
	private FassetDAO fassetDAO ;
	@Autowired
	private FvirtualcointypeDAO fvirtualcointypeDAO ;
	@Autowired
	private FvirtualwalletDAO fvirtualwalletDAO ;
	@Autowired
	private RealTimeData realTimeData ;
	@Autowired
	private FrontUserService frontUserService ;
	@Autowired
	private ConstantMap constantMap;

	@Autowired
	private FintegralactivityDAO fintegralactivityDAO;

	@Autowired
	private IntegralService integralService;
	
	//记录所有用户的资产明细
	public boolean updateAllAssets(){
		String sql = " insert into fasset( fuser, ftotal, fcreatetime, flastupdatetime, version, status ) select fid,0,now(),now(),1,0 from fuser " ;
		int count = this.fassetDAO.executeSQL(sql) ;
		return count>0 ;
	}
	

	//更新明细
	public boolean updateAllAssetsDetail() {
		List<Fasset> fassets = this.fassetDAO.findByParam(0, 300, " where status=0 ", true, Fasset.class) ;
		Map<Integer,Integer> tradeMappings = (Map)this.constantMap.get("tradeMappings");
		List<Integer> userIdList = new LinkedList<>();
		Fintegralactivity fintegralactivity = fintegralactivityDAO.findOpenActivity(IntegralTypeEnum.EVERDAY_ASSES.getCode());
		Boolean integralFlag = fintegralactivity != null;
		for (Fasset fasset : fassets) {
			if(fasset.getFid()<=0||fasset.getStatus()==true) continue ;
			
			JSONObject jsonObject = new JSONObject() ;
			Double total = 0D ;//预估总资产
			List<Fvirtualwallet> fvirtualwallets = this.fvirtualwalletDAO.findByParam(0, 0, " where fuser.fid="+fasset.getFuser().getFid(), false, Fvirtualwallet.class) ;

			for (Fvirtualwallet fvirtualwallet : fvirtualwallets) {
				if(fvirtualwallet.getFvirtualcointype().getFtype() ==CoinTypeEnum.FB_CNY_VALUE){
					JSONObject cny = new JSONObject() ;
					cny.accumulate("total", fvirtualwallet.getFtotal()) ;
					cny.accumulate("frozen", fvirtualwallet.getFfrozen()) ;
					jsonObject.accumulate("0", cny) ;
					total+=Utils.add(fvirtualwallet.getFtotal(), fvirtualwallet.getFfrozen());
				}else{
					JSONObject item = new JSONObject() ;
					item.accumulate("total", fvirtualwallet.getFtotal()) ;
					item.accumulate("frozen", fvirtualwallet.getFfrozen()) ;
					jsonObject.accumulate(String.valueOf(fvirtualwallet.getFvirtualcointype().getFid()), item) ;
					//2017-04-07 转换计算方式
					double t_total = fvirtualwallet.getFtotal();
					double t_frozen = fvirtualwallet.getFfrozen();
					double t_lates_price = this.realTimeData.getLatestDealPrize(tradeMappings.get(fvirtualwallet.getFvirtualcointype().getFid()));
					double sum = Utils.add(t_total, t_frozen);
					total += Utils.mul(sum, t_lates_price);
				}
			}

			if(integralFlag && total>=fintegralactivity.getNeedAmount()){
				userIdList.add(fasset.getFuser().getFid());
			}
			fasset.setDetail(jsonObject.toString()) ;
			fasset.setFtotal(Utils.getDouble(total, 2)) ;
			fasset.setStatus(true) ;
			this.fassetDAO.attachDirty(fasset) ;
		}

		try{
           Iterator<Integer> iterator = userIdList.iterator();
		   while(iterator.hasNext()){
			   integralService.addUserIntegral(IntegralTypeEnum.EVERDAY_ASSES,iterator.next(),0,0);
		   }
		}catch (Exception e){
			LOG.e(this.getClass(),"增加积分出错");
		}
		return fassets.size()>0 ;
	}
}
