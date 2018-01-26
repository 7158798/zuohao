package com.ruizton.main.auto;

import com.ruizton.main.Enum.ValidateMessageStatusEnum;
import com.ruizton.main.auto.order.OrderResponse;
import com.ruizton.main.controller.front.FrontTradeAutoController;
import com.ruizton.main.model.*;
import com.ruizton.main.service.front.FrontValidateService;
import com.ruizton.main.service.front.FvirtualWalletService;
import com.ruizton.util.DateHelper;
import com.ruizton.util.HttpUtils;
import com.ruizton.util.JsonHelper;
import com.ruizton.util.Utils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lx on 17-1-17.
 */
@Component
public class TestAutoTask2 {

    public static final Logger logger = LoggerFactory.getLogger(TestAutoTask2.class);

    private Map<Integer,OrderThread> threadMap = new HashMap();
    @Autowired
    private FrontTradeAutoController frontTradeAutoController;
    @Autowired
    private FvirtualWalletService fvirtualWalletService;
    // 当前交易数据
    @Autowired
    private RealTimeData realTimeData;
    // 发送短信验证码
    @Autowired
    private FrontValidateService frontValidateService;
    // 每日交易数据
    @Autowired
    private OneDayData oneDayData;

    // 获取当日交易量
    //js.accumulate("total", Utils.getDouble(this.oneDayData.getTotal(ftrademapping.getFid()), 2));

    /**
     * 启动测试线程
     * @param ftrademapping
     */
    public void start(Ftrademapping ftrademapping,int[] ids){
        logger.info("启动自动刷单线程开始");
        OrderThread order = threadMap.get(ftrademapping.getFid());
        if (order == null){
            logger.info("刷单线程为空,启动刷单线程");
            order = this.new OrderThread(ftrademapping,ids);
            // 启动线程
            order.start();
            threadMap.put(ftrademapping.getFid(),order);
        }
        logger.info("启动自动刷单线程结束");
    }

    /**
     * 终止测试线程
     * @param ftrademapping
     */
    public void stop(Ftrademapping ftrademapping){

        OrderThread order = threadMap.get(ftrademapping.getFid());
        if (order != null){
            order.stopThread();
            threadMap.remove(ftrademapping.getFid());
            logger.info("终止线程成功： coinType=" + ftrademapping.getFid());
        }
    }


    protected class OrderThread extends Thread {

        private Long flag = null;

        private Ftrademapping ftrademapping;

        private Fvirtualcointype coinType;
        // 线程终止标记
        private boolean exit = false;

        private int[] uids = null;

        private int[] tUids = null;

        private TestCoinInfo testCoinInfo;
        // 人民币类型
        private final static int RMB_TYPE = 2;

        private Map<Integer,Map<Integer,Double>> walletMap;
        // 是否开启短信通知
        private boolean messageFlag = false;
        // 用于控制发送短信的次数（发送过一次之后，设置未false）
        private boolean sendMessage = true;

        public OrderThread(Ftrademapping ftrademapping,int[] ids){
            this.ftrademapping = ftrademapping;
            this.uids = ids;
        }

        public void run(){
            coinType = ftrademapping.getFvirtualcointypeByFvirtualcointype2();
            testCoinInfo = TestCoinInfo.getCoinInfo(coinType.getFid());
            if (uids == null){
                logger.error("挂单用户为空，将不能进行挂单");
                return;
            }
            // 加载用户钱包总金额
            walletMap = loadWallet();
            logger.info("线程启动开始" + coinType.getFname());
            while (!exit){
                try {
                    // 聚币网
                    // HttpResponse response = HttpUtils.doGet("https://www.jubi.com/api/v1/orders?coin=" + coinType.getfShortName().toLowerCase(), null);
                    // okcoin
                    HttpResponse response = HttpUtils.doGet("https://www.okcoin.cn/api/v1/trades.do?symbol=" + coinType.getfShortName().toLowerCase() + "_cny", null);
                    String reps_str = EntityUtils.toString(response.getEntity(), "UTF-8");
                    List<OrderResponse> rList = JsonHelper.jsonArrayStr2List(reps_str, OrderResponse.class);
                    if (rList != null){
                        OrderResponse temp = null;
                        for (OrderResponse order : rList){
                            if (exit){
                                break;
                            }
                            if (flag != null){
                                if (order.getTid().intValue() <= flag.intValue()){
                                    // 已经处理过的订单,不再处理
                                    continue;
                                }
                            }
                            int coin = ftrademapping.getFid();
                            //加载下单用户
                            int flag = loadUser();
                            switch (flag){
                                case 1:
                                    // 卖单
                                    //String result = frontTradeAutoController.sellBtcSubmitTest(coin,getRandomAmount(),order.getPrice(),"ABC123",tUids[0]);
                                    //logger.info(coinType.getFname() + " 下卖单的结果:" + result);
                                    Thread.sleep(1000);

                                    // 买单
                                    //result = frontTradeAutoController.buyBtcSubmit(coin,getRandomAmount(),order.getPrice(),"ABC123",tUids[1]);
                                    //logger.info(coinType.getFname() + "下买单的结果:" + result);
                                    Thread.sleep(1000);

                                    temp = order;
                                    break;
                                case 2:
                                    for (int uid : uids){
                                        // 买单
                                        /*result = frontTradeAutoController.buyBtcSubmit(coin,getRandomAmount(),order.getPrice(),"ABC123",uid);
                                        logger.info(coinType.getFname() + "下买单的结果:" + result);*/
                                        Thread.sleep(1000);
                                    }
                                    temp = order;
                                    break;
                                case 3:
                                    for (int uid : uids){
                                        // 只剩下虚拟币 卖单
                                        //result = frontTradeAutoController.sellBtcSubmitTest(coin,getRandomAmount(),order.getPrice(),"ABC123",uid);
                                        //logger.info(coinType.getFname() + "下卖单的结果:" + result);
                                        Thread.sleep(1000);
                                    }
                                    temp = order;
                                    break;
                                default :
                                    // 休眠五秒中
                                    Thread.sleep(1000);
                                    break;
                            }
                            // 撤销委托订单
                            cancelEntrust();
                            // 加载钱包
                            walletMap = loadWallet();
                        }

                        if (temp != null){
                            flag = temp.getTid();
                            System.out.println("tid = " + flag);
                        }
                    }

                } catch (Exception e) {
                    logger.error("自动下单发生异常",e.getMessage(),e);
                }
            }
            logger.info("线程启动结束" + coinType.getFname());

        }


        /**
         * 获取下单用户的数据
         * @return
         */
        private Map<Integer,Map<Integer,Double>> loadWallet(){
            logger.debug("加载用户虚拟帐号金额开始");
            Map<Integer,Map<Integer,Double>> walletMap = new HashMap<Integer, Map<Integer, Double>>();
            // 人民币和虚拟币
            int[] coinArr = {2,coinType.getFid()};
            for (int uid : uids){
                Map<Integer,Double> tMap = new HashMap<Integer, Double>();
                for (int str : coinArr){
                    // 加载钱包的金额
                    Fvirtualwallet wallet = fvirtualWalletService.findFvirtualwallet(uid, str);
                    tMap.put(str,wallet.getFtotal());
                }
                walletMap.put(uid,tMap);
            }
            logger.debug("加载用户虚拟帐号金额结束");
            return walletMap;
        }

        /**
         * 获取下单用户
         * @return
         */
        private int loadUser(){
            int[] temp = tUids == null?uids:tUids;
            Map<Integer,Double> well0 = walletMap.get(temp[0]);
            Map<Integer,Double> well1 = walletMap.get(temp[1]);
            int xUid = temp[0];
            int rUid = temp[1];
            // 校验帐号是否没有币或钱
            if (!checkwallet(well0,xUid)){
                // 钱包0中没有币也没有钱了
                return 4;
            }
            if (!checkwallet(well1,rUid)){
                // 钱包1中没有币也没有钱了
                return 4;
            }

            boolean flag = false;
            // 持有币
            if (well0.get(coinType.getFid()) < testCoinInfo.getXnb()){
                if (well1.get(coinType.getFid()) > testCoinInfo.getXnb()){
                    xUid = temp[1];
                    flag = true;
                } else {
                    if (well0.get(RMB_TYPE) > testCoinInfo.getRmb() && well1.get(RMB_TYPE) > testCoinInfo.getRmb()){
                        // 两个帐号中人民币金额大于界限
                        String text = "两个帐号中只剩下人民币,两个帐号都买单";
                        logger.info(text);
                        sendMessage(text);
                        return 2;
                    } else {
                        // 两个帐号人民币和虚拟币都不足
                        logger.info("帐号中虚拟币和人民币不足");
                        return 4;
                    }
                }
            }
            // 持有钱
            if (well1.get(RMB_TYPE) < testCoinInfo.getRmb() || flag){
                // 虚拟帐号1的人民币小于人民币界限　或　需要虚拟帐号1卖虚拟币
                if (well0.get(2) > testCoinInfo.getRmb()){
                    // 虚拟帐号0的人民币金额大于人民币界限
                    rUid = temp[0];
                    if (!flag){
                        // 虚拟帐号1的虚拟币大于虚拟币的界限
                        if (well1.get(coinType.getFid()) > testCoinInfo.getXnb()){
                            // 有币的场合
                            xUid = temp[1];
                        } else {
                            logger.info("帐号中既也没有虚拟币了");
                            //　well1 没有钱也没有币
                            return 4;
                        }
                    }
                } else {
                    String text = "两个帐号中只剩下人民币,两个帐号都买单";
                    logger.info(text);
                    sendMessage(text);
                    return 3;
                }
            }
            if (tUids == null){
                tUids = new int[2];
            }
            tUids[0] = xUid;
            tUids[1] = rUid;
            // 可以下单的场合，则开启端提醒
            sendMessage = true;
            return 1;
        }

        /**
         * 取消过期委托订单
         */
        private void cancelEntrust(){
            logger.debug("取消订单方法开始");
            Fentrust[] buys = realTimeData.getEntrustBuyMap(coinType.getFid(), Integer.MAX_VALUE);
            cancelCommon(buys);
            Fentrust[] sells = realTimeData.getEntrustSellMap(coinType.getFid(), Integer.MAX_VALUE);
            cancelCommon(sells);
            logger.debug("取消订单方法结束");
        }
        /**
         * 取消订单共同方法
         * @param objs
         */
        private void cancelCommon(Fentrust[] objs){

            if (objs != null && objs.length  > 15){
                logger.debug("订单总数量为:" + objs.length);
                // 保持15个挂单
                Date sysdate = new Date();
                for (Fentrust fentrust : objs){
                    int uid = fentrust.getFuser().getFid();
                    if (uid == uids[0] || uid == uids[1]){
                        // 自动挂单的订单
                        Long time = DateHelper.getDifferSecond(sysdate, fentrust.getFcreateTime());
                        // 分钟
                        time = time / 1000 / 60;
                        if (time > 2){
                            try {
                                logger.debug("取消订单的id = " + fentrust.getFid());
                                // 挂单时间超过10分钟就取消挂单
                                String result = frontTradeAutoController.cancelEntrust(uid, fentrust.getFid());
                                logger.debug(coinType.getFname() + "取消订单结果为" + result);
                                Thread.sleep(100);
                            } catch (Exception e) {
                                logger.error("取消订单失败"+fentrust.getFid(),e.getMessage(),e);
                            }
                        }
                    }
                }

            } else {
                logger.info("挂单总数量不足不需要取消订单");
            }
        }

        /**
         * 校验虚拟帐号的币和钱
         * @param well
         * @param uid
         * @return
         */
        private boolean checkwallet(Map<Integer,Double> well,int uid){

            boolean flag = true;

            if (well.get(RMB_TYPE) <=  testCoinInfo.getRmb() && well.get(coinType.getFid()) <= testCoinInfo.getXnb() ){
                String content = "帐号不存在虚拟币和人民币,用户id=" + uid;
                // 不存在虚拟币和人民币
                logger.info(content);
                sendMessage(content);
                flag = false;
            }
            return flag;
        }

        /**
         * 发送短信
         * @param content
         */
        private void sendMessage(String content){

            if (sendMessage && messageFlag){
                Fvalidatemessage fvalidatemessage = new Fvalidatemessage() ;
                fvalidatemessage.setFcontent(content) ;
                fvalidatemessage.setFcreateTime(Utils.getTimestamp()) ;
                fvalidatemessage.setFphone("15921911246") ;
                fvalidatemessage.setFstatus(ValidateMessageStatusEnum.Not_send) ;
                // 发送短信通知
                frontValidateService.addFvalidateMessage(fvalidatemessage);
                // 发送完成之后,不在发送
                sendMessage = Boolean.FALSE;
            }

        }

        /**
         * 下单数量上增加随机数量
         * @return
         */
        /*private double getRandomAmount(double data){
            int start = (int)(Math.random()*10);
            double s = start * 0.0001;
            data = data + s;
            return data;
        }*/

        public double getRandomAmount(){
            int start = (int)(Math.random() * 3) + 2;
            double result = start * testCoinInfo.getBl();
            return result;
        }

        public void stopThread(){
            exit = Boolean.TRUE;
        }

        /**
         * 开启发送短信
         */
        public void openSendMessage(){
            sendMessage = true;
        }

        public void stopSendMessage(){
            sendMessage = false;
        }

    }

    public double getRandomAmount(){
        int start = (int)(Math.random() * 5) + 2;
        double result = start * 0.0001;
        return result;
    }

    public static void main(String[] args) {
       /* TestAutoTask task = new TestAutoTask();
        while (true){
            System.out.println(String.valueOf(task.getRandomAmount()));
        }*/
        int i = 2;
        switch (i){
            case 1:
                System.out.println("test");
                break;
        }
    }

}
