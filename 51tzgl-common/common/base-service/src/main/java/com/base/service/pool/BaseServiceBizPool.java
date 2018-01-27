package com.base.service.pool;

import com.base.service.banner.biz.MobileActivityBiz;
import com.base.service.comment.biz.CommentBiz;
import com.base.service.info.biz.*;
import com.base.service.integral.biz.IntegralTaskBiz;
import com.base.service.integral.biz.UserIntegralAccountBiz;
import com.base.service.integral.biz.UserIntegralDetailBiz;
import com.base.service.integral.biz.UserIntegralOrdersBiz;
import com.base.service.laud.biz.LaudBiz;
import com.base.service.message.biz.SmsBiz;
import com.base.service.user.biz.UserLoginBiz;
import com.base.service.version.biz.MobileVersionBiz;
import com.base.service.weixin.biz.WeixinBiz;
import com.base.service.welcomepage.biz.WelcomePageBiz;
import com.jucaifu.common.context.ApplicationContextHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author luwei
 * @Date 16-9-30 下午1:44
 */
@Component
public class BaseServiceBizPool {

    public static BaseServiceBizPool getInstance(){
        return ApplicationContextHelper.getInstance().getBean(BaseServiceBizPool.class);
    }

    @Autowired
    public WelcomePageBiz welcomePageBiz;
    @Autowired
    public MobileActivityBiz mobileActivityBiz;
    @Autowired
    public MobileVersionBiz mobileVersionBiz;

    @Autowired
    public VirtualCurrencyBiz virtualCurrencyBiz;
    @Autowired
    public InfoExchangeBiz infoExchangeBiz;
    @Autowired
    public InfoExchangeDetailBiz infoExchangeDetailBiz;
    @Autowired
    public InfoCurrencyBiz infoCurrencyBiz;

    @Autowired
    public InfoBankBiz infoBankBiz;
    @Autowired
    public InfoRateBiz infoRateBiz;
    @Autowired
    public InfoRateDetailBiz infoRateDetailBiz;
    @Autowired
    public InfoBankProductBiz infoBankProductBiz;

    @Autowired
    public SysLogBiz sysLogBiz;

    @Autowired
    public SmsBiz smsBiz;

    @Autowired
    public IntegralTaskBiz integralTaskBiz;
    @Autowired
    public UserIntegralAccountBiz userIntegralAccountBiz;
    @Autowired
    public UserIntegralDetailBiz userIntegralDetailBiz;
    @Autowired
    public UserIntegralOrdersBiz userIntegralOrdersBiz;
    @Autowired
    public CommentBiz commentBiz;
    @Autowired
    public LaudBiz laudBiz;
    @Autowired
    public WeixinBiz weixinBiz;

    @Autowired
    public UserLoginBiz userLoginBiz;

}


