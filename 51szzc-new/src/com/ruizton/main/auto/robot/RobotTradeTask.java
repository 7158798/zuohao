package com.ruizton.main.auto.robot;

import com.ruizton.main.Enum.robot.RobotStatusEnum;
import com.ruizton.main.Enum.robot.RobotTypeEnum;
import com.ruizton.main.auto.RealTimeData;
import com.ruizton.main.auto.order.OrderType;
import com.ruizton.main.controller.front.FrontTradeAutoController;
import com.ruizton.main.controller.front.response.WebResponse;
import com.ruizton.main.log.LOG;
import com.ruizton.main.model.*;
import com.ruizton.main.model.robot.RobotTrade;
import com.ruizton.main.service.admin.AutoOrderService;
import com.ruizton.main.service.zuohao.robot.RobotTradeService;
import com.ruizton.util.GroupHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by lx on 17-2-16.
 */
@Component
public class RobotTradeTask {

    public static final Logger logger = LoggerFactory.getLogger(RobotTradeTask.class);
    private Map<String,TradeThread> threadMap = new HashMap();
    @Autowired
    private RealTimeData realTimeData;
    @Autowired
    private FrontTradeAutoController frontTradeAutoController;
    @Autowired
    private AutoOrderService autoOrderService;
    @Autowired
    private RobotTradeService robotTradeService;
    /**
     * 启动测试线程
     * @param ftrademapping
     */
    public void start(Ftrademapping ftrademapping,String type) throws Exception{
        LOG.i(this,"机器人自动卖出线程启动开始");
        String key = ftrademapping.getFid() + "_" + type;
        TradeThread tradeThread = threadMap.get(key);
        if (tradeThread == null){
            tradeThread = this.new TradeThread(ftrademapping,type);
            tradeThread.start();
            threadMap.put(key,tradeThread);
        } else {
            throw new RuntimeException("请不要重复启动");
        }
        LOG.i(this,"机器人自动卖出线程启动结束");
    }

    /**
     * 终止测试线程
     * @param ftrademapping
     */
    public void stop(Ftrademapping ftrademapping,String type) throws Exception {
        String key = ftrademapping.getFid() + "_" + type;
        TradeThread tradeThread = threadMap.get(key);
        if (tradeThread != null){
            tradeThread.stopThread();
            threadMap.remove(key);
        } else {
            throw new RuntimeException("请不要重复停止");
        }
    }

    /**
     * 交易线程是否已经启动
     * @param key
     * @return
     */
    public Boolean isStart(int key){
        TradeThread tradeThread = threadMap.get(key);
        if (tradeThread == null){
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public class TradeThread extends Thread {

        private String robotType;
        private AutoOrder autoOrder;
        private Ftrademapping ftrademapping;
        private Fvirtualcointype coinType;
        private Map<Integer,BigDecimal> checkMap = new HashMap<>();
        // 线程终止标记
        private boolean exit = false;
        // 交易数据
        private Map<Object,List<RobotTrade>> treeMap = new TreeMap<>();

        public TradeThread(Ftrademapping ftrademapping,String robotType){
            this.robotType = robotType;
            this.ftrademapping = ftrademapping;
            coinType = ftrademapping.getFvirtualcointypeByFvirtualcointype2();
            List<AutoOrder> list = autoOrderService.findByProperty("ftrademapping.fid", ftrademapping.getFid());
            if (list != null && list.size() > 0){
                this.autoOrder = list.get(0);
            }
            loadData();
        }

        private void loadData(){
            StringBuffer filter = new StringBuffer();
            filter.append("where 1=1 \n");
            filter.append("and type = " + robotType + " ");
            filter.append("and status = " + RobotStatusEnum.INIT.getCode() + " ");
            filter.append("and ftrademapping.fid=" + ftrademapping.getFid() + " ");
            List<RobotTrade> list = robotTradeService.list(filter.toString());
            if (list.size() == 0){
                logger.info("没有查询到["  +  coinType.getFname()+  "]待处理的机器交易数据");
                throw new RuntimeException("没有查询到["  +  coinType.getFname()+  "]待处理的机器交易数据");
            }
            try {
                treeMap = GroupHelper.groupListByFieldName(RobotTrade.class,"cost",list);
            } catch (Exception e) {
                logger.error("数据分组发生异常" + e.getMessage(), e);
                throw new RuntimeException(e);
            }
        }

        public void run(){
            logger.info(coinType.getFname() + " 机器交易线程已经启动");
            while (!exit){
                try{
                    OrderType orderType = OrderType.buy;
                    if (RobotTypeEnum.BUY.getCode().equals(robotType)){
                        // 卖出
                        orderType = OrderType.sell;
                    }
                    trade(orderType);
                    remove();
                    Thread.sleep(10000);
                } catch (Exception e){
                    LOG.e(this,e.getMessage(),e);
                }
            }
            logger.info(coinType.getFname() + " 机器交易线程已经停止");
        }

        private void trade(OrderType orderType){
            if (check(orderType)){
                // 查找订单的
                List<Fentrust> rList = findFentrust(orderType);
                order(rList,orderType);
            }
        }

        private void remove(){
            Map<Object,List<RobotTrade>> tMap = new TreeMap<>();
            for (Map.Entry<Object,List<RobotTrade>> entry : treeMap.entrySet()){
                List<RobotTrade> tList = new ArrayList<>();
                for (RobotTrade robotTrade : entry.getValue()){
                    if (!RobotStatusEnum.CARRY_OUT.getCode().equals(robotTrade.getStatus())){
                        tList.add(robotTrade);
                    }
                }
                if (tList.size() != 0)tMap.put(entry.getKey(),tList);
            }
            treeMap = tMap;
        }

        /**
         *
         * @param orderType
         * @return
         */
        private Boolean check(OrderType orderType){
            Boolean flag = Boolean.FALSE;
            if (treeMap.size() == 0){
                return flag;
            }
            return Boolean.TRUE;
        }

        private void order(List<Fentrust> list,OrderType orderType){
            if (list.size() == 0){
                return;
            }
            try{
                for (Fentrust fentrust : list){
                    // 未成交的数量
                    BigDecimal count = new BigDecimal(String.valueOf(fentrust.getFleftCount()));
                    for (Map.Entry<Object,List<RobotTrade>> entry : treeMap.entrySet()){
                        BigDecimal price = new BigDecimal(String.valueOf(fentrust.getFprize()));
                        BigDecimal key = (BigDecimal) entry.getKey();
                        // 买入的场合,委托单金额大于成本
                        if ((OrderType.buy.getCode() == orderType.getCode() && price.compareTo(key) == 1)
                                // 卖出的场合，委托单金额小于成本
                                ||(OrderType.sell.getCode() == orderType.getCode() && price.compareTo(key) == -1)){
                            for (RobotTrade robotTrade : entry.getValue()){
                                if (count.compareTo(BigDecimal.ZERO) == 0){
                                    // 处理结束
                                    break;
                                }
                                BigDecimal temp = robotTrade.getAmount().subtract(robotTrade.getDealAmount());
                                temp = count.compareTo(temp) == 1?temp:count;
                                // 下单价格大于成本金额
                                WebResponse<Fentrust> response;
                                if (OrderType.buy.getCode() == orderType.getCode()){
                                    // 卖出
                                    response = frontTradeAutoController.sellBtcSubmitTest(ftrademapping.getFid(), temp.doubleValue(), fentrust.getFprize(), robotTrade.getFuser().getFid());
                                } else {
                                    // 买入
                                    response = frontTradeAutoController.buyBtcSubmit(ftrademapping.getFid(), temp.doubleValue(), fentrust.getFprize(), robotTrade.getFuser().getFid());
                                }
                                if (response.getResultCode() == 0){
                                    count = count.subtract(temp);
                                    // 更新成交数量
                                    temp = temp.add(robotTrade.getDealAmount());
                                    robotTrade.setDealAmount(temp);
                                    if (robotTrade.getAmount().compareTo(temp) == 0){
                                        robotTrade.setStatus(RobotStatusEnum.CARRY_OUT.getCode());
                                    }
                                    robotTradeService.update(robotTrade);
                                }
                            }
                        } else {
                            if (OrderType.buy.getCode() == orderType.getCode())
                                break;
                        }
                    }
                }
            } catch (Exception e){
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
                    if (fentrust.getFuser().getFid() == autoOrder.getUser().getFid()){
                        // 刷单用户的委托单
                        continue;
                    }
                    double tPrice = fentrust.getFprize();
                    if (tPrice != price)break;
                    rList.add(fentrust);
                }
            }
            return rList;
        }

        public void sendMessage(){
            /*if (StringUtils.isNotEmpty(tradeSet.getMobileNumber())){
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
            }*/
        }


        /**
         * 停止线程
         */
        public void stopThread(){
            exit = Boolean.TRUE;
        }

    }
}
