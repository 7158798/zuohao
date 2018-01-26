package com.ruizton.main.service.app;

import com.ruizton.main.Enum.PushStatusEnum;
import com.ruizton.main.comm.ConstantMap;
import com.ruizton.main.controller.app.request.AppPushReq;
import com.ruizton.main.controller.app.request.PriceRange;
import com.ruizton.main.dao.zuohao.FapppushDAO;
import com.ruizton.main.model.Fapppush;
import com.ruizton.main.model.Ftrademapping;
import com.ruizton.main.model.Fuser;
import com.ruizton.main.model.vo.FapppushPhone;
import com.ruizton.main.model.vo.FapppushVo;
import com.ruizton.main.model.vo.MessagePushDetailVo;
import com.ruizton.main.service.front.FtradeMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

/**
 * 价格提示
 */
@Service
public class FapppushService {
	@Autowired
	private FapppushDAO fapppushDAO;
	@Autowired
	private FtradeMappingService fapppushService;

	public Fapppush findById(int id) {
		return this.fapppushDAO.findById(id);
	}

	public void saveObj(Fapppush obj) {
		this.fapppushDAO.save(obj);
	}

	public void saveList(List<Fapppush> fapppushList) {
		this.fapppushDAO.saveList(fapppushList);
	}

	public void deleteObj(int id) {
		Fapppush obj = this.fapppushDAO.findById(id);
		this.fapppushDAO.delete(obj);
	}

	public void deleteParam(Fapppush obj){
		this.fapppushDAO.deleteParam(obj);
	}

	public void updateObj(Fapppush obj) {
		this.fapppushDAO.attachDirty(obj);
	}

	public List<Fapppush> findByProperty(String name, Object value) {
		return this.fapppushDAO.findByProperty(name, value);
	}

	public List<Fapppush> findByUserAndCoinType(int user, Integer coinType) {
		return this.fapppushDAO.findByUserAndCoinType(user, coinType);
	}

	public List<Fapppush> findAll() {
		return this.fapppushDAO.findAll();
	}

	public List<Fapppush> list(int firstResult, int maxResults,
			String filter,boolean isFY) {
		return this.fapppushDAO.list(firstResult, maxResults, filter,isFY);
	}
	public void update(Fapppush obj) {
		this.fapppushDAO.update(obj);
	}

	public void updatePhoneInfo(Fapppush obj) {
		this.fapppushDAO.updatePhoneInfo(obj);
	}

	public List<FapppushPhone> findPushList(int coinType, double price, Timestamp time){
		return this.fapppushDAO.findPushList(coinType, price, time);
	}

	public void updateSendTime(int coinType, double price, Timestamp time){
		this.fapppushDAO.updateSendTime(coinType, price, time);
	}

	/**
	 * 推送价格区间设置
	 * @param fuser
	 * @param appPush
	 */
	public void updatePushMessage(Fuser fuser, AppPushReq appPush){
		Fapppush fapppush = null;
		Integer sendType = appPush.getSendType();
		Integer smsSendType = PushStatusEnum.NO_SEND.getKey();
		PriceRange priceRange = appPush.getPriceRange();
		// 通过user和fcointype获取数据
		List<Fapppush> fapppushList = this.findByUserAndCoinType(fuser.getFid(), priceRange.getId());
		List<Fapppush> fapppushListByUser = this.findByUserAndCoinType(fuser.getFid(), null);
		// 如果已经存在则进行更新，没有就进行保存
		if(null != fapppushList && fapppushList.size() > 0){
			fapppush = fapppushList.get(0);
			// 如果推送和短信都不需要发送则删除对应的数据
			if(sendType == PushStatusEnum.NO_SEND.getKey()){
				this.deleteParam(fapppush);
			}else {
				fapppush.setFispush(sendType);
				fapppush.setFlowprice(priceRange.getLowPrice());
				fapppush.setFhighprice(priceRange.getHighPrice());
				this.update(fapppush);
			}
		}else {
			if(fapppushListByUser != null && fapppushListByUser.size() > 0){
				for(Fapppush f : fapppushListByUser){
					if(f.getFissms() == PushStatusEnum.SEND.getKey()){
						smsSendType = PushStatusEnum.SEND.getKey();
						break;
					}
				}
			}

			fapppush = new Fapppush();
			fapppush.setFuser(fuser.getFid());
			fapppush.setFissms(smsSendType);
			fapppush.setFispush(PushStatusEnum.getByKey(sendType).getKey());
			fapppush.setFcointype(priceRange.getId());
			fapppush.setFhighprice(priceRange.getHighPrice());
			fapppush.setFlowprice(priceRange.getLowPrice());
			fapppush.setFphonetype(appPush.getPhoneType());
			fapppush.setFphonecode(appPush.getPhoneCode());
			fapppush.setFlastsendtime(Timestamp.valueOf(LocalDateTime.now()));
			this.saveObj(fapppush);
		}
	}

	/**
	 * 获取设置详情
	 * @param fuserId
	 * @return
	 */
	public MessagePushDetailVo findDetail(Integer fuserId){
		FapppushVo vo = null;
		List<FapppushVo> voList = null;
		Map<Integer, Fapppush> map = null;
		boolean isSms = false;
		MessagePushDetailVo detailVo = new MessagePushDetailVo();

		if(null == fuserId || 0 == fuserId.intValue()){
			return null;
		}
		// 获取币种
		List<Ftrademapping> tradeMappingList = (List<Ftrademapping>) ConstantMap.get("activetradeMapping");

		// 获取推送设置
		List<Fapppush> fapppushList = this.findByProperty("fuser", fuserId);
		if(null != fapppushList && fapppushList.size() > 0){
			map = new HashMap<Integer, Fapppush>();
			for(Fapppush fapppush : fapppushList){
				map.put(fapppush.getFcointype(), fapppush);
			}
		}

		if(null != tradeMappingList && tradeMappingList.size() > 0){
			voList = new ArrayList<FapppushVo>();
			for(Ftrademapping ftrademapping : tradeMappingList){
				vo = new FapppushVo();
				vo.setCoinName(ftrademapping.getFvirtualcointypeByFvirtualcointype2().getFname() + " (CNY)");
				vo.setShortName(ftrademapping.getFvirtualcointypeByFvirtualcointype2().getfShortName());
				vo.setUrl(ftrademapping.getFvirtualcointypeByFvirtualcointype2().getFurl());
				vo.setFid(ftrademapping.getFid());
				if(null != map && null != map.get(ftrademapping.getFid())){
					Fapppush fapppush = map.get(ftrademapping.getFid());
					vo.setFphonetype(fapppush.getFphonetype());
					vo.setFuser(fapppush.getFuser());
					vo.setFispush(fapppush.getFispush());
					vo.setFhighprice(fapppush.getFhighprice());
					vo.setFcointype(fapppush.getFcointype());
					vo.setFissms(fapppush.getFissms());
					vo.setFlowprice(fapppush.getFlowprice());
					vo.setFphonecode(fapppush.getFphonecode());
					if(PushStatusEnum.SEND.getKey() == vo.getFissms()){
						isSms = true;
					}
				}
				voList.add(vo);
			}
		}
		detailVo.setSms(isSms);
		detailVo.setFapppushVoList(voList);
		return detailVo;
	}

	/**
	 * 短信发送价格区间设置
	 * @param fuser
	 * @param appPush
	 */
	public void updateSendSMS(Fuser fuser, AppPushReq appPush){
		List<Fapppush> saveList = new ArrayList<Fapppush>();
		this.fapppushDAO.updateSendSms(fuser.getFid(), PushStatusEnum.getByKey(appPush.getSendType()).getKey());
	}
}