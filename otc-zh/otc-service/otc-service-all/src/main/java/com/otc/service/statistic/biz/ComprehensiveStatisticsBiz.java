package com.otc.service.statistic.biz;

import com.otc.facade.base.CountVo;
import com.otc.facade.base.CountVoEx;
import com.otc.facade.trade.enums.TradeStatusEnum;
import com.otc.facade.virtual.enums.VirtualRecordOutStatus;
import com.otc.facade.virtual.enums.VirtualRecordType;
import com.otc.pool.OTCBizPool;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zygong on 17-5-15.
 */
@Service
public class ComprehensiveStatisticsBiz {
    /**
     * 综合统计
     * @return
     */
    public Map<String, Object> totalCount(){
        Map<String, Object> map = new HashMap<String, Object>();
        Date date = new Date();
        // 会员统计-全部
        List<CountVo> userCountVoAll = OTCBizPool.getInstance().userBiz.countUser();
        map.put("hytj", userCountVoAll);
        // 会员统计-今天
        List<CountVo> userCountVoToday = OTCBizPool.getInstance().userBiz.countUser(date);
        map.put("hytjjr", userCountVoToday);

        // 广告统计-全部
        List<CountVo> adCountVoAll = OTCBizPool.getInstance().advertisementBiz.countAdvertisement();
        map.put("ggtj", adCountVoAll);
        //广告统计-今天
        List<CountVo> adCountVoToday = OTCBizPool.getInstance().advertisementBiz.countAdvertisement(date);
        map.put("ggtjjr", adCountVoToday);

        //交易统计-全部
        List<CountVoEx> tradeCountVoAll = OTCBizPool.getInstance().tradeBiz.countTrade();
        map.put("jytj", tradeCountVoAll);
        // 交易统计-今日
        List<CountVoEx> tradeCountVoToday = OTCBizPool.getInstance().tradeBiz.countTrade(date, String.valueOf(TradeStatusEnum.COMPLETE.getCode()));
        map.put("jytjjr", tradeCountVoToday);

        // 充值统计-全部
        List<CountVoEx> rechargeCountVoAll = OTCBizPool.getInstance().virtualRecordBiz.countVitralRecord(VirtualRecordType.RECHARGE.getCode());
        map.put("cztj", rechargeCountVoAll);
        // 充值统计-今日
        List<CountVoEx> rechanrgeCountVoToday = OTCBizPool.getInstance().virtualRecordBiz.countVitralRecord(date, VirtualRecordOutStatus.SUCCESS.getCode(), VirtualRecordType.RECHARGE.getCode());
        map.put("cztjjr", rechanrgeCountVoToday);

        // 提现统计-全部
        List<CountVoEx> withdrawCountVoAll = OTCBizPool.getInstance().virtualRecordBiz.countVitralRecord(VirtualRecordType.WITHDRAW.getCode());
        map.put("txtj", withdrawCountVoAll);
        // 提现统计-今日
        List<CountVoEx> withdrawCountVoToday = OTCBizPool.getInstance().virtualRecordBiz.countVitralRecord(date, VirtualRecordOutStatus.SUCCESS.getCode(), VirtualRecordType.WITHDRAW.getCode());
        map.put("txtjjr", withdrawCountVoToday);

        // 平台钱包信息
        List<CountVoEx> virtualWallertVo = OTCBizPool.getInstance().virtualWalletBiz.countVitralWallet();
        map.put("ptqbxx", virtualWallertVo);

        return map;
    }

}
