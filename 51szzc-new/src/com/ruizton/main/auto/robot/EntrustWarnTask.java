package com.ruizton.main.auto.robot;

import cn.jpush.api.utils.StringUtils;
import com.ruizton.main.Enum.ValidateMessageStatusEnum;
import com.ruizton.main.Enum.robot.RobotStatusEnum;
import com.ruizton.main.Enum.robot.RobotTypeEnum;
import com.ruizton.main.auto.RealTimeData;
import com.ruizton.main.auto.TaskList;
import com.ruizton.main.auto.order.OrderType;
import com.ruizton.main.controller.front.response.WebResponse;
import com.ruizton.main.log.LOG;
import com.ruizton.main.model.Fentrust;
import com.ruizton.main.model.Ftrademapping;
import com.ruizton.main.model.Fvalidatemessage;
import com.ruizton.main.model.Fvirtualcointype;
import com.ruizton.main.model.robot.EntrustWarn;
import com.ruizton.main.model.robot.RobotTrade;
import com.ruizton.main.service.admin.AutoOrderService;
import com.ruizton.main.service.front.FrontValidateService;
import com.ruizton.main.service.zuohao.robot.RobotTradeService;
import com.ruizton.util.DateHelper;
import com.ruizton.util.Utils;
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
public class EntrustWarnTask {

    public static final Logger logger = LoggerFactory.getLogger(EntrustWarnTask.class);
    private Map<String,EntrustWarnThread> threadMap = new HashMap();
    @Autowired
    private RealTimeData realTimeData;
    // 发送短信验证码
    @Autowired
    private FrontValidateService frontValidateService;
    @Autowired
    private TaskList taskList;
    /**
     * 启动线程
     * @param ftrademapping
     */
    public void start(Ftrademapping ftrademapping,EntrustWarn entrustWarn) throws Exception{
        LOG.i(this,"自动预警线程启动开始" + ftrademapping.getFvirtualcointypeByFvirtualcointype2().getFname());
        String key = ftrademapping.getFid() + "_" + entrustWarn.getType();
        EntrustWarnThread thread = threadMap.get(key);
        if (thread == null){
            thread = this.new EntrustWarnThread(ftrademapping,entrustWarn);
            thread.start();
            threadMap.put(key,thread);
        } else {
            throw new RuntimeException("请不要重复启动");
        }
        LOG.i(this,"自动预警线程结束"+ ftrademapping.getFvirtualcointypeByFvirtualcointype2().getFname());
    }

    /**
     * 终止线程
     * @param ftrademapping
     */
    public void stop(Ftrademapping ftrademapping,String type) throws Exception {
        LOG.i(this,"自动预警线程停止开始" + ftrademapping.getFvirtualcointypeByFvirtualcointype2().getFname());
        String key = ftrademapping.getFid() + "_" + type;
        EntrustWarnThread tradeThread = threadMap.get(key);
        if (tradeThread != null){
            tradeThread.stopThread();
            threadMap.remove(key);
        } else {
            throw new RuntimeException("请不要重复停止");
        }
        LOG.i(this,"自动预警线程停止开始" + ftrademapping.getFvirtualcointypeByFvirtualcointype2().getFname());
    }

    /**
     * 预警线程是否已经启动
     * @param key
     * @return
     */
    public Boolean isStart(int key){
        EntrustWarnThread tradeThread = threadMap.get(key);
        if (tradeThread == null){
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public class EntrustWarnThread extends Thread {

        private Ftrademapping ftrademapping;
        private Fvirtualcointype coinType;
        private EntrustWarn entrustWarn;
        // 线程终止标记
        private boolean exit = false;
        private Date lastSendDate;

        public EntrustWarnThread(Ftrademapping ftrademapping,EntrustWarn entrustWarn){
            this.ftrademapping = ftrademapping;
            this.entrustWarn = entrustWarn;
            coinType = ftrademapping.getFvirtualcointypeByFvirtualcointype2();
        }

        public void run(){
            logger.info(coinType.getFname() + " 预警线程已经启动" + entrustWarn.getType());
            while (!exit){
                try{
                    OrderType orderType = OrderType.buy;
                    if (RobotTypeEnum.SELL.getCode().equals(entrustWarn.getType())){
                        // 卖出
                        orderType = OrderType.sell;
                    }
                    // 查找合并深度的订单
                    findMerge(orderType);
                    Thread.sleep(10000);
                } catch (Exception e){
                    LOG.e(this,e.getMessage(),e);
                }
            }
            logger.info(coinType.getFname() + " 预警交易线程已经停止" + entrustWarn.getType());
        }


        private void findMerge(OrderType orderType){
            Fentrust[] depth;
            if (OrderType.buy.getCode() == orderType.getCode()){
                depth = realTimeData.getBuyDepthMap(ftrademapping.getFid(), 1);
            } else {
                depth = realTimeData.getSellDepthMap(ftrademapping.getFid(), 1);
            }
            if (depth != null && depth.length != 0){
                // 未成交数量
                double amount = depth[0].getFleftCount();
                BigDecimal _amount = new BigDecimal(String.valueOf(amount));
                if (_amount.compareTo(entrustWarn.getMergeAmount()) == 1){
                    // 合并深度量大于设定大于设定量
                    // 发送短信
                    String context = coinType.getFname() + ":" +RobotTypeEnum.getDescByCode(entrustWarn.getType())
                            + "的合并深度数量大于预警数量。";
                    sendMessage(context);
                }
            }
        }

        private void findSingle(OrderType orderType){
            // 查找订单的
            List<Fentrust> rList = findFentrust(orderType);
        }



        /**
         * 查找订单
         */
        private List<Fentrust> findFentrust(OrderType orderType){

            /*Fentrust[] one;
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
            return rList;*/
            return null;
        }

        public void sendMessage(String context){
            if (StringUtils.isNotEmpty(entrustWarn.getMobileNumber())){
                //LOG.i(this,"预警短信发送开始 " + entrustWarn.getMobileNumber());
                Long time = null;
                if (lastSendDate != null)
                    time = DateHelper.getDifferSecond(new Date(), lastSendDate);
                // 获得休眠的秒钟数
                if (time == null || time >= entrustWarn.getPauseMsgTime() * 60 * 1000){
                    String[] phones = entrustWarn.getMobileNumber().split(",");
                    for (String phone:phones){
                        // 间隔60分钟发送错误短信
                        Fvalidatemessage fvalidatemessage = new Fvalidatemessage() ;
                        fvalidatemessage.setFcontent(context) ;
                        fvalidatemessage.setFcreateTime(Utils.getTimestamp()) ;
                        fvalidatemessage.setFphone(phone) ;
                        fvalidatemessage.setFstatus(ValidateMessageStatusEnum.Not_send) ;
                        // 发送短信通知
                        frontValidateService.addFvalidateMessage(fvalidatemessage);
                        taskList.returnMessageList(fvalidatemessage.getFid());
                    }
                    // 更新发送短信的时间
                    lastSendDate = new Date();
                }
                //LOG.i(this,"预警短信发送开始 " + entrustWarn.getMobileNumber());
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
