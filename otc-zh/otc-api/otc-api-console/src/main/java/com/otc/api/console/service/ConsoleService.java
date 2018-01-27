package com.otc.api.console.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jucaifu.common.context.ApplicationContextHelper;
import com.otc.facade.advertisement.service.console.PriceFormulaConsoleFacade;
import com.otc.facade.announcement.service.console.AnnouncementConsoleFacade;
import com.otc.facade.message.service.console.MessageConsoleFacade;
import com.otc.facade.statistic.service.console.ComprehensiveStatisticsConsoleFacade;
import com.otc.facade.sys.service.console.EmployeeConsoleFacade;
import com.otc.facade.sys.service.console.EmployeeRoleConsoleFacade;
import com.otc.facade.sys.service.console.ResourceConsoleFacade;
import com.otc.facade.advertisement.service.console.AdvertisementConsoleFacade;
import com.otc.facade.advertisement.service.console.TransactionDesConsoleFacade;
import com.otc.facade.trade.service.console.TradeConsoleFacade;
import com.otc.facade.trade.service.console.TradeJudgeConsoleFacade;
import com.otc.facade.user.service.console.UserAuConsoleFacade;
import com.otc.facade.user.service.console.UserConsoleFacade;
import com.otc.facade.sys.service.console.RoleConsoleFacade;
import com.otc.facade.virtual.service.console.*;
import org.springframework.stereotype.Component;

/**
 * Created by lx on 17-4-21.
 */
@Component
public class ConsoleService {

    @Reference
    public AuditProcessConsoleFacade auditProcessConsoleFacade;
    @Reference
    public VirtualWalletConsoleFacade virtualWalletConsoleFacade;
    @Reference
    public VirtualRecordConsoleFacade virtualRecordConsoleFacade;
    @Reference
    public ResourceConsoleFacade resourceConsoleFacade;
    @Reference
    public EmployeeConsoleFacade employeeConsoleFacade;
    @Reference
    public EmployeeRoleConsoleFacade employeeRoleConsoleFacade;
    @Reference
    public PoolConsoleFacade poolConsoleFacade;
    @Reference
    public VirtualCoinConsoleFacade virtualCoinConsoleFacade;
    @Reference
    public UserAuConsoleFacade userAuConsoleFacade;
    @Reference
    public UserConsoleFacade userConsoleFacade;
    @Reference
    public AdvertisementConsoleFacade advertisementConsoleFacade;
    @Reference
    public TransactionDesConsoleFacade transactionDesConsoleFacade;
    @Reference
    public RoleConsoleFacade roleConsoleFacade;
    @Reference
    public AnnouncementConsoleFacade announcementConsoleFacade;
    @Reference
    public PriceFormulaConsoleFacade priceFormulaConsoleFacade;
    @Reference
    public ComprehensiveStatisticsConsoleFacade comprehensiveStatisticsConsoleFacade;

    @Reference
    public TradeConsoleFacade tradeConsoleFacade;

    @Reference
    public TradeJudgeConsoleFacade judgeConsoleFacade;

    @Reference
    public MessageConsoleFacade messageConsoleFacade;


    public static ConsoleService getInstance() {
        return ApplicationContextHelper.getInstance().getBean(ConsoleService.class);
    }

}
