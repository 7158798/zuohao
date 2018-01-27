package com.otc.api.web.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.code.kaptcha.Producer;
import com.jucaifu.common.context.ApplicationContextHelper;
import com.otc.facade.advertisement.service.TransactionDesFacade;
import com.otc.facade.advertisement.service.web.AdvertisementTimeWebFacade;
import com.otc.facade.advertisement.service.web.AdvertisementWebFacade;
import com.otc.facade.advertisement.service.web.PriceFormulaWebFacade;
import com.otc.facade.advertisement.service.web.TransactionDesWebFacade;
import com.otc.facade.announcement.service.web.AnnouncementWebFacade;
import com.otc.facade.message.service.web.MessageWebFacade;
import com.otc.facade.trade.pojo.po.TradeJudge;
import com.otc.facade.trade.service.TradeFacade;
import com.otc.facade.trade.service.web.TradeJudgeWebFacade;
import com.otc.facade.trade.service.web.TradeWebFacade;
import com.otc.facade.user.service.web.UserAuWebFacade;
import com.otc.facade.user.service.web.UserWebFacade;
import com.otc.facade.virtual.service.web.UserAddressWebFacade;
import com.otc.facade.virtual.service.web.VirtualCoinWebFacade;
import com.otc.facade.virtual.service.web.VirtualRecordWebFacade;
import com.otc.facade.virtual.service.web.VirtualWalletWebFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by lx on 17-4-14.
 */
@Component
public class WebService {

    @Reference
    public VirtualCoinWebFacade virtualCoinWebFacade;
    @Reference
    public MessageWebFacade messageWebFacade;
    @Reference
    public VirtualRecordWebFacade virtualRecordWebFacade;

    @Autowired
    public Producer captchaProducer ;

    @Reference
    public UserWebFacade userWebFacade;
    @Reference
    public VirtualWalletWebFacade virtualWalletWebFacade;
    @Reference
    public UserAddressWebFacade userAddressWebFacade;

    @Reference
    public UserAuWebFacade userAuWebFacade;

    @Reference
    public AdvertisementWebFacade advertisementWebFacade;
    @Reference
    public AnnouncementWebFacade announcementWebFacade;
    @Reference
    public PriceFormulaWebFacade priceFormulaWebFacade;

    @Reference
    public TradeWebFacade tradeWebFacade;

    @Reference
    public TradeJudgeWebFacade tradeJudgeWebFacade;

    @Reference
    public AdvertisementTimeWebFacade advertisementTimeWebFacade;

    @Reference
    public TransactionDesWebFacade transactionDesWebFacade;





    public static WebService getInstance() {
        return ApplicationContextHelper.getInstance().getBean(WebService.class);
    }
}
