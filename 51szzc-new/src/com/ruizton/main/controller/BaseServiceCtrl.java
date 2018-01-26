package com.ruizton.main.controller;

import com.ruizton.main.auto.*;
import com.ruizton.main.auto.order.FastTradeTask;
import com.ruizton.main.auto.robot.EntrustWarnTask;
import com.ruizton.main.auto.robot.RobotTradeTask;
import com.ruizton.main.comm.ConstantMap;
import com.ruizton.main.comm.ValidateMap;
import com.ruizton.main.model.robot.EntrustWarn;
import com.ruizton.main.service.app.FapppushService;
import com.ruizton.main.service.front.FquotesService;
import com.ruizton.main.service.admin.*;
import com.ruizton.main.service.front.*;
import com.ruizton.main.service.zuohao.robot.EntrustWarnService;
import com.ruizton.main.service.front.FrontValidateKycService;
import com.ruizton.main.service.zuohao.robot.RobotTradeService;
import com.ruizton.util.MyResourceBundleMessageSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by a123 on 17-2-20.
 */
public class BaseServiceCtrl {
    @Autowired
    protected RealTimeData realTimeData ;
    @Autowired
    protected OneDayData oneDayData ;
    @Autowired
    protected ConstantMap constantMap ;
    @Autowired
    protected FrontValidateService frontValidateService ;
    @Autowired
    protected TaskList taskList ;
    @Autowired
    protected LimittradeService limittradeService;
    @Autowired
    protected SystemArgsService systemArgsService;
    @Autowired
    protected FtradeMappingService ftradeMappingService ;
    @Autowired
    protected AboutService aboutService;

    @Autowired
    protected ArticleService articleService ;
    @Autowired
    protected ArticleTypeService articleTypeService ;
    @Autowired
    protected FrontOthersService frontOtherService;
    @Autowired
    protected ConstantMap map;
    @Autowired
    protected UtilsService utilsService;
    @Autowired
    protected TestAutoTask testAutoTask;
    @Autowired
    protected HttpServletRequest request;
    @Autowired
    protected AutoOrderService autoOrderService;
    @Autowired
    protected UserService userService;

    @Autowired
    protected BankinfoWithdrawService bankinfoWithdrawService;
    @Autowired
    protected AdminService adminService ;
    @Autowired
    protected VirtualCoinService virtualCoinService ;
    @Autowired
    protected VirtualaddressWithdrawService virtualaddressWithdrawService;

    @Autowired
    protected VirtualCapitaloperationService virtualCapitaloperationService;

    @Autowired
    protected CapitaloperationService capitaloperationService;

    @Autowired
    protected CountLimitService countLimitService;

    @Autowired
    protected EntrustService entrustService;


    @Autowired
    protected FriendLinkService friendLinkService ;

    @Autowired
    protected IntrolinfoService introlinfoService;

    @Autowired
    protected LogService logService;

    @Autowired
    protected OperationLogService operationLogService ;
    @Autowired
    protected QuestionService questionService ;

    @Autowired
    protected VirtualWalletService virtualWalletService;

    @Autowired
    protected RoleService roleService;
    @Autowired
    protected SecurityService securityService;
    @Autowired
    protected RoleSecurityService roleSecurityService;

    @Autowired
    protected SystembankService systembankService;

    @Autowired
    protected SysLogService sysLogService;

    @Autowired
    protected TradehistoryService tradehistoryService;


    @Autowired
    protected TradeMappingService tradeMappingService;

    @Autowired
    protected FfeesService feesService;

    @Autowired
    protected TradeSetService tradeSetService;

    @Autowired
    protected TradeTask tradeTask;
    @Autowired
    protected UsersettingService usersettingService;

    @Autowired
    protected ScoreService scoreService;
    @Autowired
    protected VirtualaddressService virtualaddressService;
    @Autowired
    protected PoolService poolService;
    @Autowired
    protected WithdrawFeesService withdrawFeesService;
    @Autowired
    protected VirtualOperationLogService virtualOperationLogService;

    @Autowired
    protected WebBaseInfoService webBaseInfoService ;

    //前端
    @Autowired
    protected FrontOthersService frontOthersService ;

    @Autowired
    protected FrontBankInfoService frontBankInfoService ;
    @Autowired
    protected FrontAccountService frontAccountService ;
    @Autowired
    protected FrontUserService frontUserService ;
    @Autowired
    protected FrontTradeService frontTradeService ;
    @Autowired
    protected FrontVirtualCoinService frontVirtualCoinService ;




    @Autowired
    protected ValidateMap messageValidateMap ;


    @Autowired
    protected ValidateMap validateMap ;

    @Autowired
    protected LatestKlinePeroid latestKlinePeroid ;

    @Autowired
    protected KlinePeriodData klinePeriodData ;

    @Autowired
    protected FrontQuestionService frontQuestionService ;

    @Autowired
    protected VideoTypeService videoTypeService;

    @Autowired
    protected FvideoService fvideoService;

    @Autowired
    protected FastTradeTask fastTradeTask;
    //积分模块
    @Autowired
    protected UserGradeService userGradeService;

    @Autowired
    protected  IntegralService integralService;

    @Autowired
    protected FintegralactivityService fintegralactivityService;

    @Autowired
    protected UserintegraldetailService userintegraldetailService;

    @Autowired
    protected FtimewalletService ftimewalletService;
    @Autowired
    protected HostRecommendedService hostRecommendedService;

    @Autowired
    protected FbannerService fbannerService;

    @Autowired
    protected FastTradeService fastTradeService;
    @Autowired
    protected FtagManageService ftagManageService;

    @Autowired
    protected FtagService ftagService;

    @Autowired
    protected BatchOrderService batchOrderService;

    @Autowired
    protected AuditProcessService auditProcessService;

    //支付宝绑定
    @Autowired
    protected FalipayBindService falipayBindService;
    @Autowired
    protected RobotTradeService robotTradeService;
    @Autowired
    protected RobotTradeTask robotTradeTask;


    //国际化资源文件
    @Autowired
    protected MyResourceBundleMessageSource messageSource;

    //cookie用户语言
    @Autowired
    protected CookieLocaleResolver resolver;

    // 帮助类型
    @Autowired
    protected FhelptypeService fhelptypeService;

    // 帮助列表
    @Autowired
    protected FhelpService fhelpService;

    // 其他平台行情数据
    @Autowired
    protected FquotesService fquotesService;


    @Autowired
    protected FquestionvalidateService fquestionvalidateService;
    @Autowired
    protected EntrustWarnService entrustWarnService;
    @Autowired
    protected EntrustWarnTask entrustWarnTask;

    @Autowired
    protected FrontValidateKycService frontValidateKycService;

    @Autowired
    protected FvalidateKycService fvalidateKycService;

    // 价格提示
    @Autowired
    protected FapppushService fapppushService;
}
