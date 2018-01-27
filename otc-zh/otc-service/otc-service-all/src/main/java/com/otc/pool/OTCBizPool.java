package com.otc.pool;

import com.jucaifu.common.context.ApplicationContextHelper;
import com.otc.facade.user.pojo.po.UserAsset;
import com.otc.facade.user.pojo.po.UserCreditRecord;
import com.otc.service.advertisement.biz.AdvertisementTimeBiz;
import com.otc.service.advertisement.biz.PriceFormulaBiz;
import com.otc.service.announcement.biz.AnnouncementBiz;
import com.otc.service.advertisement.biz.TransactionDesBiz;
import com.otc.service.holiday.biz.HolidayBiz;
import com.otc.service.quotes.biz.QuotesBiz;
import com.otc.service.message.biz.MessageBiz;
import com.otc.service.statistic.biz.ComprehensiveStatisticsBiz;
import com.otc.service.sys.biz.*;
import com.otc.service.advertisement.biz.AdvertisementBiz;
import com.otc.service.trade.biz.TradeBiz;
import com.otc.service.trade.biz.TradeJudgeBiz;
import com.otc.service.trade.biz.TradeLogBiz;
import com.otc.service.user.biz.*;
import com.otc.service.virtual.biz.VirtualRecordBiz;
import com.otc.service.virtual.biz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by lx on 17-4-17.
 */
@Component
public class OTCBizPool {

    @Autowired
    public MessageBiz messageBiz;
    @Autowired
    public ResourceBiz resourceBiz;
    @Autowired
    public EmployeeRoleBiz employeeRoleBiz;
    @Autowired
    public EmployeeBiz employeeBiz;
    @Autowired
    public RoleBiz roleBiz;
    @Autowired
    public VirtualRecordBiz virtualRecordBiz;
    @Autowired
    public UserAddressBiz userAddressBiz;
    @Autowired
    public VirtualWalletBiz virtualWalletBiz;
    @Autowired
    public VirtualCoinBiz virtualCoinBiz;
    @Autowired
    public PoolBiz poolBiz;
    @Autowired
    public UserBiz userBiz;
    @Autowired
    public UserAuBiz userAuBiz;
    @Autowired
    public AdvertisementBiz advertisementBiz;
    @Autowired
    public TransactionDesBiz transactionDesBiz;
    @Autowired
    public AnnouncementBiz announcementBiz;
    @Autowired
    public PriceFormulaBiz priceFormulaBiz;
    @Autowired
    public UserOperationBiz userOperationBiz;
    @Autowired
    public UserAssetBiz userAssetBiz;
    @Autowired
    public AuditProcessBiz auditProcessBiz;
    @Autowired
    public SystemArgsBiz systemArgsBiz;
    @Autowired
    public QuotesBiz quotesBiz;
    @Autowired
    public TradeJudgeBiz tradeJudgeBiz;
    @Autowired
    public TradeLogBiz tradeLogBiz;
    @Autowired
    public TradeBiz tradeBiz;
    @Autowired
    public UserCreditBiz userCreditBiz;
    @Autowired
    public ComprehensiveStatisticsBiz comprehensiveStatisticsBiz;
    @Autowired
    public AdvertisementTimeBiz advertisementTimeBiz;
    @Autowired
    public HolidayBiz holidayBiz;


    public static OTCBizPool getInstance() {
        return ApplicationContextHelper.getInstance().getBean(OTCBizPool.class);
    }

}
