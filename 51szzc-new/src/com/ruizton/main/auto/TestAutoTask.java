package com.ruizton.main.auto;

import com.ruizton.main.Enum.ValidateMessageStatusEnum;
import com.ruizton.main.auto.order.*;
import com.ruizton.main.controller.front.FrontTradeAutoController;
import com.ruizton.main.controller.front.response.WebResponse;
import com.ruizton.main.log.LOG;
import com.ruizton.main.model.*;
import com.ruizton.main.service.admin.AutoOrderService;
import com.ruizton.main.service.front.FrontValidateService;
import com.ruizton.main.service.front.FvirtualWalletService;
import com.ruizton.util.*;
import com.ruizton.util.zuohao.RandomUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by lx on 17-1-17.
 */
@Component
public class TestAutoTask {

    public static final Logger logger = LoggerFactory.getLogger(TestAutoTask.class);

    private Map<Integer,OrderThread> threadMap = new HashMap();
    @Autowired
    private FrontTradeAutoController frontTradeAutoController;
    @Autowired
    private FvirtualWalletService fvirtualWalletService;
    // 当前交易数据
    @Autowired
    private RealTimeData realTimeData;
    @Autowired
    private AutoOrderService autoOrderService;
    // 发送短信验证码
    @Autowired
    private FrontValidateService frontValidateService;
    @Autowired
    private TaskList taskList;
    // 每日交易数据
    @Autowired
    private OneDayData oneDayData;

    /**
     * 启动测试线程
     * @param autoOrder
     */
    public void start(AutoOrder autoOrder){
        logger.info("启动自动刷单线程开始");
        OrderThread order = threadMap.get(autoOrder.getId());
        if (order == null){
            logger.info("刷单线程为空,启动刷单线程");
            order = this.new OrderThread(autoOrder);
            // 启动线程
            order.start();
            threadMap.put(autoOrder.getId(),order);
        }
        logger.info("启动自动刷单线程结束");
    }

    /**
     * 终止测试线程
     * @param autoOrder
     */
    public void stop(AutoOrder autoOrder){

        OrderThread order = threadMap.get(autoOrder.getId());
        if (order != null){
            order.stopThread();
            threadMap.remove(autoOrder.getId());
            logger.info("终止线程成功： coinType=" + autoOrder.getId());
        }
    }

    /**
     * 开启短信提醒
     * @param ftrademapping
     * @param mobilePhone
     */
    public void openMessage(Ftrademapping ftrademapping,String mobilePhone){
        OrderThread order = threadMap.get(ftrademapping.getFid());
        if (order != null){
            order.openSendMessage(mobilePhone);
            logger.info("开启短信提现成功： coinType=" + ftrademapping.getFid());
        }

    }

    /**
     * 关闭短信提醒
     * @param ftrademapping
     */
    public void stopMessage(Ftrademapping ftrademapping){
        OrderThread order = threadMap.get(ftrademapping.getFid());
        if (order != null){
            order.stopSendMessage();
            logger.info("关闭短信提现成功： coinType=" + ftrademapping.getFid());
        }
    }

    /**
     * 增加交易量
     * @param ftrademapping
     * @param userId
     * @param amount
     * @param price
     * @param inside
     */
    public WebResponse<Fentrust> amount(Ftrademapping ftrademapping,int userId,Double amount,Double price,Boolean inside){
        WebResponse<Fentrust> response;
        try {
            int coin = ftrademapping.getFid();
            response = frontTradeAutoController.insideBuySubmit(coin, amount, price, userId, inside);
            if (response.getResultCode() == 0){
                int entrustId = response.getBody().getFid();
                // 下单成功
                response = frontTradeAutoController.insideSellSubmit(coin, amount, price, userId, inside);
                if (response.getResultCode() != 0){
                    // 卖出发生异常了，撤销订单
                    String result = frontTradeAutoController.insideCancelEntrust(userId, entrustId, inside);
                }
            }

        } catch (Exception e) {
            LOG.e("下单发生异常",e.getMessage(),e);
            response = new WebResponse<Fentrust>(-2,"下委托订单时发生异常");
        }

        return response;
    }

    public Map<Integer,OrderThread> getThreadMap(){
        return this.threadMap;
    }


    public class OrderThread extends Thread {
        // 上一次交易id
        private Long lastTid = null;

        private Ftrademapping ftrademapping;

        private Fvirtualcointype coinType;
        // 线程终止标记
        private boolean exit = false;
        // 挂单用户用户集合
        private List<User> users = new ArrayList<User>();
        // 挂单配置信息
        private AutoOrder tAutoOrder;
        // 人民币类型
        private final static int RMB_TYPE = 2;
        // 用于控制发送短信的次数（发送过一次之后，设置未false）
        private boolean sendMessage = true;
        // 手机号码集合
        private String[] mobiles = null;
        // 最新的价格
        private BigDecimal newPrice;
        // 计算虚拟币和人民币的下限
        private AutoConfig autoConfig = new AutoConfig();
        // 请求数据源的地址结合
        private List<String> urls = new ArrayList<String>();
        // 请求的url
        private Date requestDate = null;
        private int url_index = 0;
        // 停止时间
        private Date stopDate = null;
        // 异常时间
        private Date errorDate = null;
        // 上次发送短信的时间
        private Date lastSendDate;
        private Integer[] priceScope;


        public OrderThread(AutoOrder autoOrder){
            this.tAutoOrder = autoOrder;
            this.ftrademapping = autoOrder.getFtrademapping();
            coinType = ftrademapping.getFvirtualcointypeByFvirtualcointype2();
            init();
        }

        /**
         * 初始化线程
         */
        public void init(){
            // 组装挂单用户集合
            int[] ids = {tAutoOrder.getUser().getFid(),tAutoOrder.getUser().getFid()};
            for (int id : ids){
                User user = new User();
                user.setUid(id);
                user.setXnbFrozen(tAutoOrder.getXnbFrozen());
                user.setRmbFrozen(tAutoOrder.getRmbFrozen());
                users.add(user);
            }
            if (StringUtils.isNotEmpty(tAutoOrder.getMobilePhone())){
                mobiles = tAutoOrder.getMobilePhone().split(",");
            }
            String[] urlArray = tAutoOrder.getUrls().split(",");
            // 设置请求的地址
            this.urls = Arrays.asList(urlArray);
            // 系统日期增加一天
            stopDate = DateUtils.addDays(new Date(),1);
            String stopDateStr = DateHelper.date2String(stopDate, DateHelper.DateFormatType.YearMonthDay);
            stopDateStr += " 00:00:00";
            // 计算停止日期
            stopDate = DateHelper.string2Date(stopDateStr, DateHelper.DateFormatType.YearMonthDay_HourMinuteSecond);
            // 加载用户的金额
            loadWallet();
            if (tAutoOrder.getXnbFrozen() == null && tAutoOrder.getRmbFrozen() == null){
                User user = users.get(0);
                tAutoOrder.setRmbFrozen(user.getRmbFrozen());
                tAutoOrder.setXnbFrozen(user.getXnbFrozen());
                // 更新冻结数量
                autoOrderService.updateObj(tAutoOrder);
            }
            if(StringUtils.isNotEmpty(tAutoOrder.getPriceScope())){
                // 价格区间不为空
                String arr[] = tAutoOrder.getPriceScope().split(",");
                priceScope = new Integer[arr.length];
                for (int i=0;i<arr.length;i++){
                    priceScope[i]=Integer.parseInt(arr[i]);
                }
            }
        }

        public void run(){
            if (users.size() == 0){
                logger.error("挂单用户为空，将不能进行挂单");
                return;
            }
            logger.info("线程启动开始" + coinType.getFname());
            while (!exit){
                try {
                    toggleApi();
                    String reqUrl = urls.get(url_index);
                    logger.info("请求的url:" + reqUrl);
                    requestDate = new Date();
                    String reps_str = HttpClientHelper.sendGetRequest(reqUrl, Boolean.FALSE);
                    List<OrderResponse> rList = DefaultParse.parse(reqUrl, reps_str);
                    if (rList != null){
                        OrderResponse temp = null;
                        for (OrderResponse order : rList){
                            if (exit){
                                break;
                            }
                            if (lastTid != null){
                                if (order.getTid().intValue() <= lastTid.intValue()){
                                    // 已经处理过的订单,不再处理
                                    continue;
                                }
                            }
                            int coin = ftrademapping.getFid();
                            if (tAutoOrder.getAdjustPrice() != null){
                                // 加上调整价格
                                order.setPrice(order.getPrice().add(tAutoOrder.getAdjustPrice()));
                            }
                            if (tAutoOrder.getCostPrice() != null){
                                if (tAutoOrder.getCostPrice().compareTo(order.getPrice()) == 1){
                                    BigDecimal costPrice = tAutoOrder.getCostPrice();
                                    if (priceScope != null && priceScope.length == 2){
                                        // 为成本价格加上随机的数据
                                        Integer random = RandomUtils.getRandom(priceScope[0], priceScope[1]);
                                        BigDecimal _random = new BigDecimal(random).multiply(tAutoOrder.getRatio());
                                        costPrice = costPrice.add(_random);
                                    }
                                    // 下单金额小于成本金额，将使用成本金额进行下单哦
                                    order.setPrice(costPrice);
                                    // 发送短信提示
                                    sendMessage(coinType.getFname() + "自动挂单单价小于成本金额,请及时处理！");
                                }
                            }
                            // 计算下单金额的下限
                            autoConfig.calcLimit(tAutoOrder,order.getPrice());
                            //加载下单用户
                            boolean uFlag = loadUser();
                            // 获取最新的价格
                            newPrice = order.getPrice();
                            if (uFlag){
                                for (User user : users){
                                    if (exit){
                                        break;
                                    }
                                    double price = order.getPrice().doubleValue();
                                    WebResponse<Fentrust> result = null;
                                    if (user.getOrderTye().intValue() == OrderType.buy.getCode()){
                                        // 买单
                                        result = frontTradeAutoController.buyBtcSubmit(coin,getRandomAmount(),price,user.getUid());
                                    } else if (user.getOrderTye().intValue() == OrderType.sell.getCode()){
                                        // 卖单
                                        result = frontTradeAutoController.sellBtcSubmitTest(coin,getRandomAmount(),price,user.getUid());
                                    }
                                    if (result.getResultCode() != 0){
                                        // 下单失败
                                        logger.info(coinType.getFname() + " " + user.getOrderTye() + " 下单的结果:" + result.getMsg());
                                    }
                                    Thread.sleep(tAutoOrder.getSleepTime());
                                }
                                temp = order;
                            } else {
                                Thread.sleep(tAutoOrder.getSleepTime());
                            }
                            // 撤销委托订单
                            cancelEntrust(Boolean.TRUE);
                            // 加载钱包
                            loadWallet();
                        }

                        if (temp != null){
                            lastTid = temp.getTid();
                            logger.info(coinType.getFname() + " 上次接口的订单id = " + lastTid);
                        }
                    } else {
                        // 撤销委托订单
                        cancelEntrust(Boolean.TRUE);
                        // 加载钱包
                        loadWallet();
                    }

                    Long time = DateHelper.getDifferSecond(new Date(), requestDate);
                    // 获得休眠的秒钟数
                    time = tAutoOrder.getReqSleepTime() - time;
                    logger.info("休眠的时间(毫秒)= " + time);
                    if (time > 0){
                        // 每次请求之后休眠20s
                        Thread.sleep(time);
                    }

                } catch (Exception e) {
                    // 发生异常，切换请求地址
                    url_index++;
                    if (url_index == urls.size()){
                        url_index = 0;
                    }
                    lastTid = null;
                    errorDate = new Date();
                    logger.error("自动下单发生异常",e.getMessage(),e);
                }
            }
            logger.info("线程启动结束" + coinType.getFname());

        }

        /**
         * 切换api
         */
        private void toggleApi(){
            if (errorDate != null) {
                Long time = DateHelper.getDifferSecond(new Date(), errorDate);
                if (url_index != 0 && time > tAutoOrder.getToggleTime() * 60 * 1000){
                    // 重置api,比较毫秒
                    url_index = 0;
                    lastTid = null;
                }
            }

        }

        /**
         * 获取下单用户的数据
         * @return
         */
        private void loadWallet(){
            logger.debug("加载用户虚拟帐号金额开始");
            Map<Integer,BigDecimal> map = loadReleaseAmount();
            logger.debug("冻结的金额如下：" + JsonHelper.obj2JsonStr(map));
            // 人民币和虚拟币
            int[] coins = {RMB_TYPE,coinType.getFid()};
            for (User user : users){
                for (int coin : coins){
                    // 加载钱包的金额
                    Fvirtualwallet wallet = fvirtualWalletService.findFvirtualwallet(user.getUid(), coin);
                    // 账户总金额
                    BigDecimal ftotal = new BigDecimal(String.valueOf(wallet.getFtotal()));
                    if (coin == RMB_TYPE){
                        if (user.getRmbFrozen() == null){
                            BigDecimal rmbFrozen = BigDecimal.ZERO;
                            if (ftotal.compareTo(tAutoOrder.getAllCny()) == 1){
                                // 需要冻结的金额
                                rmbFrozen = ftotal.subtract(tAutoOrder.getAllCny());
                            }
                            user.setRmbFrozen(rmbFrozen);
                        }
                        // 得到释放的cny
                        user.setRmb(ftotal.subtract(user.getRmbFrozen()).subtract(map.get(RMB_TYPE)));
                    } else {
                        if (user.getXnbFrozen() == null){
                            BigDecimal xnbFrozen = BigDecimal.ZERO;
                            if (ftotal.compareTo(tAutoOrder.getAllXnb()) == 1){
                                // 需要冻结的金额
                                xnbFrozen = ftotal.subtract(tAutoOrder.getAllXnb());
                            }
                            user.setXnbFrozen(xnbFrozen);
                        }
                        // 得到释放的xnb
                        user.setXnb(ftotal.subtract(user.getXnbFrozen()).subtract(map.get(coinType.getFid())));
                    }
                }
            }
            logger.debug("用户剩余的金额：" + JsonHelper.obj2JsonStr(users));
            logger.debug("加载用户虚拟帐号金额结束");
        }

        /**
         * 加载释放金额
         * @return
         */
        private Map<Integer,BigDecimal> loadReleaseAmount(){
            Map<Integer,BigDecimal> map = new HashMap<Integer, BigDecimal>();
            Integer oneDay = 24 * 60;
            BigDecimal time = new BigDecimal(oneDay / tAutoOrder.getReleaseTime());
            // 时间片释放的金额
            BigDecimal cnyAmount = tAutoOrder.getAllCny().divide(time, 2, BigDecimal.ROUND_DOWN);
            // 时间片释放的虚拟币
            BigDecimal xnb = tAutoOrder.getAllXnb().divide(time, 4, BigDecimal.ROUND_DOWN);
            // 剩余的分钟数
            BigDecimal surplus = new BigDecimal(DateHelper.getDifferSecond(stopDate, new Date()) / 1000 / 60 / tAutoOrder.getReleaseTime());
            // 获取系统的当前时间
            map.put(RMB_TYPE,cnyAmount.multiply(surplus));
            map.put(coinType.getFid(),xnb.multiply(surplus));
            return map;
        }


        /**
         * 获取下单用户
         * @return
         */
        private Boolean loadUser(){
            User last = null;
            for (User user: users){
                // 初始化状态
                user.initStatusMap(autoConfig);
                user.loadStatus(last);
                last = user;
            }
            User one = users.get(0);
            if (one.getOrderTye().intValue() == OrderType.error.getCode()
                    || last.getOrderTye().intValue() == OrderType.error.getCode()){
                // 判断连个用户的冻结金额或数量，紧急取消所有订单
                logger.info("进入紧急取消订单模式,存在帐号钱和币的不够.");
                int cancelNum = cancelEntrust(Boolean.FALSE);
                if (cancelNum == 0){
                    // 存在钱包没有金额的数据
                    String content = coinType.getFname() + "刷单:存在帐号钱和币的不够, 用户id:" + one.getUid() + "," + last.getUid();
                    logger.info(content);
                    // 发送短信
                    //sendMessage(content);
                }
                return false;
            } else if (one.getOrderTye().intValue() == last.getOrderTye().intValue()){
                String content = coinType.getFname() + "刷单:两个帐号都在"  + OrderType.typeMap.get(last.getOrderTye()).getDesc()
                        +  ", 用户id:" + one.getUid() + "," + last.getUid();
                logger.info(content);
                //sendMessage(content);
            } else {
                // 设置短信可以发送
                sendMessage = Boolean.TRUE;
            }
            //return safeguard(Boolean.TRUE);
            return Boolean.TRUE;
        }

        /**
         * 取消委托订单
         * @param filter 是否需要过滤条件(false:紧急取消所有的订单,true:取消过期的)
         * @return
         */
        private int cancelEntrust(boolean filter){
            logger.debug("取消订单方法开始");
            Fentrust[] buys = realTimeData.getEntrustBuyMap(ftrademapping.getFid(), Integer.MAX_VALUE);
            int buyNum = cancelCommon(buys,filter);
            Fentrust[] sells = realTimeData.getEntrustSellMap(ftrademapping.getFid(), Integer.MAX_VALUE);
            int sellNum = cancelCommon(sells,filter);
            logger.debug("取消订单方法结束");
            return buyNum + sellNum;
        }
        /**
         * 取消订单共同方法
         * @param objs     订单金额
         * @param filter  是否需要过滤条件(false:紧急取消所有的订单,true:取消过期的)
         */
        private int cancelCommon(Fentrust[] objs,boolean filter){
            int num = 0;
            //if (objs != null && objs.length > 0 && (!filter || objs.length  > 15)){
            if (objs != null && objs.length > 0){
                logger.debug("订单总数量为:" + objs.length);
                // 保持15个挂单
                Date sysdate = new Date();
                for (Fentrust fentrust : objs){
                    int uid = fentrust.getFuser().getFid();
                    for (User user : users){
                        if (user.getUid() == uid){
                            // 自动挂单的订单
                            Long time = DateHelper.getDifferSecond(sysdate, fentrust.getFcreateTime());
                            // 分钟
                            time = time / 1000 / 60;
                            if (time > tAutoOrder.getCancelTime() || !filter){
                                try {
                                    logger.info("取消订单的id = " + fentrust.getFid());
                                    // 挂单时间超过10分钟就取消挂单
                                    String result = frontTradeAutoController.cancelEntrust(uid, fentrust.getFid());
                                    logger.debug(coinType.getFname() + "取消订单结果为" + result);
                                    num++;
                                    //Thread.sleep(200);
                                } catch (Exception e) {
                                    logger.error("取消订单失败"+fentrust.getFid(),e.getMessage(),e);
                                }
                            }
                        }
                    }
                }

            } else {
                logger.debug("挂单总数量不足不需要取消订单");
            }
            return num;
        }


        /*private boolean safeguard(boolean flag){
            // 查找大单
            if (flag){
                Fentrust[] sells = realTimeData.getEntrustSellMap(ftrademapping.getFid(), Integer.MAX_VALUE);
                flag = getMaxFentrust(sells, OrderType.sell);
            }
            if (flag){
                Fentrust[] buys = realTimeData.getEntrustBuyMap(ftrademapping.getFid(), Integer.MAX_VALUE);
                flag = getMaxFentrust(buys,OrderType.buy);
            }
            return flag;
        }*/

        /**
         * 查找大单
         * @param fentrusts 委托单集合
         * @param orderType 类型
         * @return
         */
        /*private boolean getMaxFentrust(Fentrust[] fentrusts,OrderType orderType){
            boolean flag = Boolean.TRUE;
            if (fentrusts != null && fentrusts.length > 0){
                // 计算下单数量
                double quantity = 10 * testCoinInfo.getXnb();
                for (Fentrust fentrust : fentrusts){
                    // 大单的标准（下单最大数量的10倍）
                    if (fentrust.getFleftCount() >= quantity){
                        double tPrice = fentrust.getFprize();
                        if ((OrderType.buy.getCode() == orderType.getCode() && tPrice >= newPrice)
                                || (OrderType.sell.getCode() == orderType.getCode() && tPrice <= newPrice)){
                            // 出现大单了
                            flag = Boolean.FALSE;
                            String typeName = OrderType.typeMap.get(orderType.getCode()).getDesc();
                            logger.info(coinType.getFname() + typeName + "的场合出现大单价格：" + tPrice + ",系统下单的价格：" + newPrice);
                            break;
                        }
                    }
                }
            }
            return flag;
        }*/

        public void sendMessage(String content){
            if (mobiles != null && mobiles.length > 0){
                Long time = null;
                if (lastSendDate != null){
                    time = DateHelper.getDifferSecond(new Date(), lastSendDate);
                }
                // 获得休眠的秒钟数
                if (time == null || time >= tAutoOrder.getPauseMsgTime() * 60 * 1000){
                    for (String mobilePhone : mobiles){
                        // 间隔60分钟发送错误短信
                        Fvalidatemessage fvalidatemessage = new Fvalidatemessage() ;
                        fvalidatemessage.setFcontent(content) ;
                        fvalidatemessage.setFcreateTime(Utils.getTimestamp()) ;
                        fvalidatemessage.setFphone(mobilePhone) ;
                        fvalidatemessage.setFstatus(ValidateMessageStatusEnum.Not_send) ;
                        // 发送短信通知
                        frontValidateService.addFvalidateMessage(fvalidatemessage);
                        taskList.returnMessageList(fvalidatemessage.getFid());
                    }
                    // 更新发送短信的时间
                    lastSendDate = new Date();
                }
            }
        }
        /**
         * 发送短信
         * @param content
         */
        private void sendMessage(){
            // 暂时关闭短信发送代码
            /*if (sendMessage && tAutoOrder.getMessageFlag()){
                if (mobiles != null && mobiles.length > 0){
                    // 校验发生短信的间隔时间
                    for (String mobilePhone : mobiles){
                        Fvalidatemessage fvalidatemessage = new Fvalidatemessage() ;
                        fvalidatemessage.setFcontent(content) ;
                        fvalidatemessage.setFcreateTime(Utils.getTimestamp()) ;
                        fvalidatemessage.setFphone(mobilePhone) ;
                        fvalidatemessage.setFstatus(ValidateMessageStatusEnum.Not_send) ;
                        // 发送短信通知
                        frontValidateService.addFvalidateMessage(fvalidatemessage);
                        taskList.returnMessageList(fvalidatemessage.getFid());
                    }
                    // 发送完成之后,不在发送
                    sendMessage = Boolean.FALSE;
                } else {
                    logger.error("手机号码为空,发生错误消息失败．");
                }
            }*/

        }

        /**
         * 下单数量上增加随机数量
         * @return
         */
        public double getRandomAmount(){
            int start = (int)(Math.random() * tAutoOrder.getRandom()) + 2;
            double result = start * tAutoOrder.getRatio().doubleValue();
            return result;
        }

        public void stopThread(){
            exit = Boolean.TRUE;
            // 清除所有的挂单
            cancelEntrust(Boolean.FALSE);
        }

        /**
         * 开启发送短信
         */
        public void openSendMessage(String mobilePhone){
            //this.mobilePhone = mobilePhone;
            // messageFlag = true;
        }

        /**
         * 关闭短信发送
         */
        public void stopSendMessage(){
            //messageFlag = false;
        }

    }

    public BigDecimal getRandomAmount(){
        int start = (int)(Math.random() * 3) + 2;
        BigDecimal result  = new BigDecimal(start).multiply(new BigDecimal("0.0001"));
        return result;
    }

    public static void main(String[] args) {
        /*User user = new User();
        user.setXnb(0.1);
        user.setRmb(100);
        user.setOrderTye(OrderType.buy.getCode());
        user.initStatusMap(TestCoinInfo.BTC);
        user.loadStatus(null);
        User user1 = new User();
        user1.setRmb(100);
        user1.setXnb(0.1);
        user1.setOrderTye(OrderType.buy.getCode());
        user1.initStatusMap(TestCoinInfo.BTC);
        user1.loadStatus(user);
        System.out.println(JsonHelper.obj2JsonStr(user));
        System.out.println(JsonHelper.obj2JsonStr(user1));*/
       /**/
        TestAutoTask task = new TestAutoTask();
        while (true){
            System.out.println(task.getRandomAmount().toString());
        }

        /*double d = 0.111;
        System.out.println(new BigDecimal(String.valueOf(d)));*/

        /*double rmb = 25 * 0.0001 * 6000;
        System.out.print(rmb);
        int i = 2;
        switch (i){
            case 1:
                System.out.println("test");
                break;
        }*/
    }

}
