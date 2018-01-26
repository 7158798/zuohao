package com.ruizton.main.auto;

import com.ruizton.main.Enum.EntrustStatusEnum;
import com.ruizton.main.Enum.ValidateMessageStatusEnum;
import com.ruizton.main.auto.okcoin.DepthResponse;
import com.ruizton.main.auto.okcoin.OkCoinUtils;
import com.ruizton.main.auto.order.OrderType;
import com.ruizton.main.auto.okcoin.OrderResponse;
import com.ruizton.main.controller.front.FrontTradeAutoController;
import com.ruizton.main.controller.front.response.WebResponse;
import com.ruizton.main.log.LOG;
import com.ruizton.main.model.*;
import com.ruizton.main.service.admin.AutoOrderService;
import com.ruizton.main.service.admin.EntrustService;
import com.ruizton.main.service.admin.TradeAccountService;
import com.ruizton.main.service.admin.TradeLogService;
import com.ruizton.main.service.front.FrontValidateService;
import com.ruizton.util.DateHelper;
import com.ruizton.util.JsonHelper;
import com.ruizton.util.Utils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by lx on 17-2-16.
 */
@Component
public class TradeTask {

    public static final Logger logger = LoggerFactory.getLogger(TradeTask.class);
    private Map<Integer,TradeThread> threadMap = new HashMap();
    @Autowired
    private TradeAccountService tradeAccountService;
    @Autowired
    private EntrustService entrustService;
    @Autowired
    private TradeLogService tradeLogService;
    @Autowired
    private RealTimeData realTimeData;
    @Autowired
    private FrontTradeAutoController frontTradeAutoController;
    @Autowired
    private FrontValidateService frontValidateService;
    @Autowired
    private TaskList taskList;
    @Autowired
    private AutoOrderService autoOrderService;
    /**
     * 启动测试线程
     * @param tradeSet
     */
    public void start(TradeSet tradeSet){
        LOG.i(this,"自动买卖线程开始");
        TradeThread tradeThread = threadMap.get(tradeSet.getId());
        if (tradeThread == null){
            tradeThread = this.new TradeThread(tradeSet);
            tradeThread.start();
            threadMap.put(tradeSet.getId(),tradeThread);
        }
        LOG.i(this,"启动自动刷单线程结束");
    }

    /**
     * 终止测试线程
     * @param tradeSet
     */
    public void stop(TradeSet tradeSet){
        TradeThread tradeThread = threadMap.get(tradeSet.getId());
        if (tradeThread != null){
            tradeThread.stopThread();
            threadMap.remove(tradeSet.getId());
        }
    }

    public class TradeThread extends Thread{

        private AutoOrder autoOrder;
        private Ftrademapping ftrademapping;
        private Fvirtualcointype coinType;
        // 交易配置数据
        private TradeSet tradeSet;
        private List<Fentrust> sells = new ArrayList<Fentrust>();
        private List<Fentrust> buys = new ArrayList<Fentrust>();
        // 线程终止标记
        private boolean exit = false;

        private List<TradeAccount> accounts = null;
        // 当前可交易帐号
        private TradeAccount account;
        // 删除发送短信时间
        private Date lastSendDate = new Date();

        public TradeThread(TradeSet tradeSet){
            this.tradeSet = tradeSet;
            ftrademapping = tradeSet.getFtrademapping();
            coinType = ftrademapping.getFvirtualcointypeByFvirtualcointype2();
            accounts = tradeAccountService.findAll();
            account = accounts.get(0);
            List<AutoOrder> list = autoOrderService.findByProperty("ftrademapping.fid", tradeSet.getFtrademapping().getFid());
            if (list != null && list.size() > 0){
                this.autoOrder = list.get(0);
            }
        }

        public void run(){
            logger.info("自动买卖线程已经启动");
            while (!exit){
                try{
                    Date startDate = new Date();
                    cancel();
                    trade(OrderType.sell);
                    trade(OrderType.buy);
                    Long time = DateHelper.getDifferSecond(new Date(), startDate);
                    // 获得休眠的秒钟数
                    time = tradeSet.getPauseTime() * 60 * 1000 - time;
                    logger.info("休眠的时间(毫秒)= " + time);
                    if (time > 0){
                        // 休眠时间
                        Thread.sleep(time);
                    }
                } catch (Exception e){
                    LOG.e(this,e.getMessage(),e);
                }
            }
            logger.info("自动买卖线程已经停止");
        }

        private void trade(OrderType orderType){
            if (check(orderType)){
                // 查找订单的
                List<Fentrust> rList = findFentrust(orderType);
                order(rList,orderType);
            }
        }

        private List<TradeLog> getGoingEntrust(Integer entrustType){
            StringBuffer buffer = new StringBuffer();
            buffer.append("where 1=1 ");
            // -1:已撤销  0:未成交  1:部分成交  2:完全成交 4:撤单处理中
            buffer.append(" and user.fid = " + tradeSet.getFuser().getFid());
            // 未成交的数据
            buffer.append(" and fentrust.fstatus = " + EntrustStatusEnum.Going);
            if (entrustType != null){
                buffer.append(" and fentrustType = " + entrustType);
            }
            return  tradeLogService.list(buffer.toString());
        }

        private void cancel(){
            List<TradeLog> tList = getGoingEntrust(null);
            if (tList != null && tList.size() > 0){
                String symbol = coinType.getfShortName().toLowerCase() + "_cny";
                for (TradeLog tradeLog : tList){
                    try{
                        // 长时间没有成交，取消挂单
                        frontTradeAutoController.cancelEntrust(tradeLog.getUser().getFid(),tradeLog.getFentrust().getFid());
                        // 取消第三方的订单
                        if (StringUtils.isNotEmpty(tradeLog.getOrderId())){
                            OrderResponse response = OkCoinUtils.cancelOrder(account.getAppKey(),account.getSecretKey(),symbol,tradeLog.getOrderId());
                            if (!response.getResult()){
                                logger.info("第三方平台撤单失败,订单id = " + tradeLog.getOrderId());
                            }
                        }
                    } catch (Exception e){
                        logger.error("撤销委托订单发生异常", e);
                    }
                }
            }
        }

        /**
         *
         * @param orderType
         * @return
         */
        private Boolean check(OrderType orderType){
            Boolean flag = Boolean.TRUE;
            // 校验交易数量 每日交易数量,客户买（算今日卖出数量）,客户卖（算今日买入数量）
            OrderType type = orderType.getCode() == OrderType.buy.getCode()?OrderType.sell:OrderType.buy;
            BigDecimal today = entrustService.getTotalQtyByTrade(tradeSet.getFuser().getFid(), type.getCode());
            if (tradeSet != null && today != null && today.compareTo(tradeSet.getUpperLimit()) != -1){
                logger.info("已达交易的上限");
                return Boolean.FALSE;
            }

            List<TradeLog> rList = getGoingEntrust(type.getCode());
            if (rList != null && rList.size() > tradeSet.getUnTradeOrderNum()){
                flag = Boolean.FALSE;
            }
            return flag;
        }

       /* private Boolean syncOrderInfo(){

            StringBuffer buffer = new StringBuffer();
            buffer.append("where 1=1 ");
            // -1:已撤销  0:未成交  1:部分成交  2:完全成交 4:撤单处理中
            buffer.append(" and status = '0'");
            List<TradeLog> tList = tradeLogService.list(buffer.toString());
            String symbol = coinType.getfShortName().toLowerCase() + "_cny";
            for (TradeLog tradeLog : tList){
                OrderInfoResponse response = OkCoinUtils.orderInfo(account.getAppKey(),account.getSecretKey(),symbol,tradeLog.getOrderId());
                if (!response.getResult() || response.getOrders().size() != 1){
                    // 同步订单发生错误
                }
                OrderInfo info = response.getOrders().get(0);
                // 成交数量
                tradeLog.setDealCount(info.getDeal_amount());
                tradeLog.setStatus(Integer.valueOf(info.getStatus()));

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:sss");
                String dateString = sdf.format(new java.util.Date());
                Timestamp tm = Timestamp.valueOf(dateString);
                tradeLog.setModifiedDate(tm);
                // 第三方平台订单信息
                tradeLogService.updateObj(tradeLog);
            }

            // 下单数量
            return null;
        }*/

        /**
         * 计算成本是否可以下单
         * @param quantity  数量
         * @param myPrice   51平台价格
         * @param price     第三方价格
         * @return
         */
        private boolean calcCost(BigDecimal quantity,Double myPrice,BigDecimal price,OrderType orderType){
            boolean flag = false;
            // 51平台下单的总金额
            BigDecimal myCost = quantity.multiply(new BigDecimal(String.valueOf(myPrice)));
            // 第三方平台的总金额
            BigDecimal cost = quantity.multiply(new BigDecimal(String.valueOf(price)));
            if (OrderType.buy.getCode() == orderType.getCode()){
                // 增加千分之二的手续非,直接进入保留2位小数
                cost = cost.add(cost.multiply(new BigDecimal(0.002)).setScale(2, BigDecimal.ROUND_UP));
                flag = cost.compareTo(myCost) == -1?Boolean.TRUE:flag;
            } else {
                // 去掉千分之二的手续费用,直接进入保留2位小数
                cost = cost.subtract(cost.multiply(new BigDecimal(0.002)).setScale(2,BigDecimal.ROUND_UP));
                flag = myCost.compareTo(cost) == -1?Boolean.TRUE:flag;
            }
            return flag;
        }

        /**
         * 获取数量
         * @param fcount    51平台数量
         * @param tquan     第三方数量
         * @return
         */
        private BigDecimal getQuantity(Double fcount,BigDecimal tquan){
            BigDecimal bcount = new BigDecimal(String.valueOf(fcount));
            BigDecimal result = bcount.compareTo(tquan) == -1?bcount:tquan;
            result = result.compareTo(tradeSet.getSingleNum()) == -1?result:tradeSet.getSingleNum();
            if (result.compareTo(bcount) == 1){
                return bcount;
            }
            return result;
        }

        private BigDecimal[] getDepth(String sysmbol,OrderType orderType){

            DepthResponse response = OkCoinUtils.depth(sysmbol, 1);
            List<BigDecimal[]> list = response.getBids();
            if (OrderType.buy.getCode() == orderType.getCode()){
                list = response.getAsks();
            }
            if (list != null && list.size() > 0){
                return list.get(0);
            }
            return null;
        }

        private void order(List<Fentrust> list,OrderType orderType){
            if (list.size() == 0){
                return;
            }
            try{
                String symbol = coinType.getfShortName().toLowerCase() + "_cny";
                for (Fentrust fentrust : list){
                    // 交易记录
                    TradeLog tradeLog = new TradeLog();
                    // 响应状态
                    OrderResponse orderResponse;
                    WebResponse<Fentrust> result = null;
                    BigDecimal[] depth = getDepth(symbol,orderType);
                    if (depth == null){
                        logger.info("第三方深度没有数据");
                        continue;
                    }
                    // 计算下单的数量
                    BigDecimal fcount = getQuantity(fentrust.getFleftCount(),depth[1]);
                    if (fcount.compareTo(tradeSet.getMinSingleNum()) == -1){
                        // 下单数量小于0.001
                        logger.info("数量小于" + tradeSet.getMinSingleNum().toString() + "不处理下单");
                        continue;
                    }
                    BigDecimal lastPrice = depth[0];
                    if (calcCost(fcount,fentrust.getFprize(),lastPrice,orderType)) {
                        // 计算是否可以下单
                        String quantity = fcount.toString();
                        String type = null;
                        int userId = tradeSet.getFuser().getFid();
                        if (OrderType.buy.getCode() == orderType.getCode()){
                            // 订单的状态为买入，比较okcoin中的价格
                            result = frontTradeAutoController.sellBtcSubmitTest(ftrademapping.getFid(), new Double(quantity), fentrust.getFprize(), userId);
                            type = "buy";
                        } else if (OrderType.sell.getCode() == orderType.getCode()){
                            // 卖出订单
                            result = frontTradeAutoController.buyBtcSubmit(ftrademapping.getFid(), new Double(quantity), fentrust.getFprize(), userId);
                            type = "sell";
                        }
                        // 第三方订单
                        if (result.getResultCode() == 0){
                            // 保存下单记录
                            Fentrust temp = result.getBody();
                            // 51平台下单成功
                            orderResponse = OkCoinUtils.trade(account.getAppKey(), account.getSecretKey(), symbol, type, String.valueOf(lastPrice), quantity);
                            if (orderResponse != null && orderResponse.getResult()){
                                // 第三方自动下的订单
                                tradeLog.setOrderId(orderResponse.getOrder_id());
                            } else {
                                // 第三方下单失败,取消51的订单
                                frontTradeAutoController.cancelEntrust(userId,temp.getFid());
                                logger.info("第三方接口下单失败： " + JsonHelper.obj2JsonStr(orderResponse));
                                this.sendMessage();
                            }
                            // 第三方下单成功
                            tradeLog.setFtrademapping(ftrademapping);
                            // 平台自动下的订单
                            tradeLog.setFentrust(temp);
                            // 关联的订单
                            tradeLog.setRelFentrust(fentrust);
                            tradeLog.setUser(tradeSet.getFuser());
                            tradeLog.setTradeAccount(account);
                            // 交易类型
                            tradeLog.setType(orderType.getCode());
                            tradeLog.setStatus(EntrustStatusEnum.Going);

                            tradeLog.setCount(fcount);
                            tradeLog.setPrice(lastPrice);

                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:sss");
                            String dateString = sdf.format(new java.util.Date());
                            Timestamp tm = Timestamp.valueOf(dateString);
                            tradeLog.setCreateDate(tm);
                            tradeLogService.saveObj(tradeLog);
                        } else {
                            sendMessage();
                            // 51平台下单失败
                            logger.info("51下单失败：" + result.getMsg());
                        }
                    }
                }
            } catch (Exception e){
                sendMessage();
                // 下单的时候发生异常
                LOG.e(this,e.getMessage(),e);
            }
        }

        /**
         * 查找订单
         */
        private List<Fentrust> findFentrust(OrderType orderType){

            Fentrust[] one;
            Fentrust[] data;
            List<Fentrust> rList = new ArrayList<Fentrust>();
            if (OrderType.buy.getCode() == orderType.getCode()){
                one = realTimeData.getBuyDepthMap(ftrademapping.getFid(), 1);
                data = realTimeData.getEntrustBuyMap(ftrademapping.getFid(),Integer.MAX_VALUE);
            } else {
                one = realTimeData.getSellDepthMap(ftrademapping.getFid(), 1);
                data = realTimeData.getEntrustSellMap(ftrademapping.getFid(),Integer.MAX_VALUE);
            }
            if (one != null && one.length ==1){
                rList = findFentrust(one[0].getFprize(), data);
            }
            return rList;
        }

        /**
         * 查找订单
         * @param fentrusts 委托单集合
         * @return
         */
        private List<Fentrust> findFentrust(Double price , Fentrust[] fentrusts){
            List<Fentrust> rList = new ArrayList<Fentrust>();
            if (fentrusts != null && fentrusts.length > 0){
                // 计算下单数量
                for (Fentrust fentrust : fentrusts){
                    if (fentrust.getFuser().getFid() == autoOrder.getUser().getFid()
                            || fentrust.getFuser().getFid() == tradeSet.getFuser().getFid()){
                        // 刷单用户的委托单
                        continue;
                    }
                    double tPrice = fentrust.getFprize();
                    if (tPrice != price)break;
                    /*StringBuffer buffer = new StringBuffer();
                    buffer.append("where 1=1 ");
                    // -1:已撤销  0:未成交  1:部分成交  2:完全成交 4:撤单处理中
                    buffer.append(" and status = '0'");
                    buffer.append(" and fentrust.fid = " + fentrust.getFid());
                    List<TradeLog> tList = tradeLogService.list(buffer.toString());
                    if (tList.size() > 0){
                        // 有订单还没有处理完成,不再继续下单。
                        continue;
                    }*/
                    rList.add(fentrust);
                }
            }
            return rList;
        }

        public void sendMessage(){
            if (StringUtils.isNotEmpty(tradeSet.getMobileNumber())){
                Long time = DateHelper.getDifferSecond(new Date(), lastSendDate);
                // 获得休眠的秒钟数
                if (time >= tradeSet.getPauseMsgTime() * 60 * 1000){
                    // 间隔60分钟发送错误短信
                    Fvalidatemessage fvalidatemessage = new Fvalidatemessage() ;
                    fvalidatemessage.setFcontent("自动买卖线程运行出现异常，请检查原因") ;
                    fvalidatemessage.setFcreateTime(Utils.getTimestamp()) ;
                    fvalidatemessage.setFphone(tradeSet.getMobileNumber()) ;
                    fvalidatemessage.setFstatus(ValidateMessageStatusEnum.Not_send) ;
                    // 发送短信通知
                    frontValidateService.addFvalidateMessage(fvalidatemessage);
                    taskList.returnMessageList(fvalidatemessage.getFid());
                    // 更新发送短信的时间
                    lastSendDate = new Date();
                }
            }
        }


        /**
         * 停止线程
         */
        public void stopThread(){
            exit = Boolean.TRUE;
        }

    }
}
