package com.ruizton.main.auto.order;

import com.ruizton.main.Enum.EntrustTypeEnum;
import com.ruizton.main.auto.RealTimeData;
import com.ruizton.main.controller.front.FrontTradeAutoController;
import com.ruizton.main.controller.front.response.WebResponse;
import com.ruizton.main.log.LOG;
import com.ruizton.main.model.*;
import org.apache.commons.lang.StringUtils;
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
public class FastTradeTask {

    public static final Logger logger = LoggerFactory.getLogger(FastTradeTask.class);
    private Map<Integer,FastTradeThread> threadMap = new HashMap();
    @Autowired
    private RealTimeData realTimeData;
    @Autowired
    private FrontTradeAutoController frontTradeAutoController;

    public void start(FastTrade fastTrade){
        LOG.i(this,"快速交易线程启动开始");
        FastTradeThread tradeThread = threadMap.get(fastTrade.getFtrademapping().getFid());
        if (tradeThread == null){
            tradeThread = this.new FastTradeThread(fastTrade);
            tradeThread.start();
            threadMap.put(fastTrade.getFtrademapping().getFid(),tradeThread);
        }
        LOG.i(this,"快速交易线程启动结束");
    }

    public void stop(FastTrade fastTrade){
        LOG.i(this,"快速交易线程停止开始");
        FastTradeThread tradeThread = threadMap.get(fastTrade.getFtrademapping().getFid());
        if (tradeThread != null){
            tradeThread.stopThread();
            threadMap.remove(fastTrade.getFtrademapping().getFid());
        }
        LOG.i(this,"快速交易线程停止结束");
    }

    /**
     * 取消批量下单
     * @param batchOrder
     */
    public void cancelBatchOrder(BatchOrder batchOrder){

        if (StringUtils.isNotEmpty(batchOrder.getEntrusts())){
            // id
            String[] ids = batchOrder.getEntrusts().split(",");
            for (String id : ids){
                try {
                    Integer i = Integer.parseInt(id);
                    frontTradeAutoController.cancelEntrust(batchOrder.getUser().getFid(),i);
                } catch (Exception e){
                    LOG.e(this,e.getMessage(),e);
                }

            }
        }
    }

    /**
     * 取消用户订单
     * @param list
     * @param fuser
     */
    public void cancelUserOrder(List<Fentrust> list,Fuser fuser){
        // 取消用户的订单
        for (Fentrust fentrust : list) {
            if (fentrust.getFuser().getFid() == fuser.getFid()){
                try {
                    frontTradeAutoController.cancelEntrust(fuser.getFid(), fentrust.getFid());
                } catch (Exception e) {
                    LOG.e(this, e.getMessage(), e);
                }
            }
        }
    }

    public WebResponse<Fentrust> batchOrder(BatchOrder batchOrder){
        StringBuffer entrusts = new StringBuffer();
        WebResponse<Fentrust> response = null;
        try {
            int index = 0;
            String[] prices = batchOrder.getPriceScope().split(",");
            double s_price = Double.parseDouble(prices[0]);
            double e_price = Double.parseDouble(prices[1]);
            String[] amounts = batchOrder.getAmountScope().split(",");
            double s_amount = Double.parseDouble(amounts[0]);
            double e_amount = Double.parseDouble(amounts[1]);
            Ftrademapping ftrademapping = batchOrder.getFtrademapping();
            Fuser fuser = batchOrder.getUser();
            while (index < batchOrder.getOrderNum()){
                // 生成订单的开始
                BigDecimal ran_p  = getRandom(s_price,e_price);
                BigDecimal ran_a = getRandom(s_amount,e_amount,batchOrder.getRatio().intValue());
                BigDecimal price = ran_p;
                BigDecimal amount = ran_a;
                if (EntrustTypeEnum.BUY == batchOrder.getType()){
                    // 买入
                    response = frontTradeAutoController.buyBtcSubmit(ftrademapping.getFid(),amount.doubleValue(),price.doubleValue(),fuser.getFid());
                } else {
                    // 卖出
                    response = frontTradeAutoController.sellBtcSubmitTest(ftrademapping.getFid(),amount.doubleValue(),price.doubleValue(),fuser.getFid());
                }
                if (response.getResultCode() != 0){
                    // 下单异常
                    break;
                } else {
                    LOG.i(this,response.getMsg());
                }
                // 委托单id
                entrusts.append(response.getBody().getFid() + ",");
                index++;
            }
        } catch (Exception ex){
            LOG.e(this,ex.getMessage(),ex);
        }
        if (StringUtils.isNotEmpty(entrusts.toString())){
            String entrustIds = entrusts.toString();
            batchOrder.setEntrusts(entrustIds.substring(0, entrustIds.length() - 1));
        }
        return response;
    }


    public BigDecimal getRandom(double min,double max){
        return getRandom(min, max,2);
    }
    /**
     * 获取随机数
     * @param min
     * @param max
     * @return
     */
    public BigDecimal getRandom(double min,double max,Integer bit){
        BigDecimal result = new BigDecimal(Math.random() * (max - min) + min);
        return result.setScale(bit, BigDecimal.ROUND_HALF_UP);
    }

    public class FastTradeThread extends Thread {

        private Integer minTime;
        private Integer maxTime;
        private Integer minNum;
        private Integer maxNum;
        private Fuser fuser;
        private Ftrademapping ftrademapping;
        private BigDecimal temp = new BigDecimal("6");
        private Boolean exit = false;

        public FastTradeThread(FastTrade fastTrade){
            String[] times = fastTrade.getRandomTime().split(",");
            minTime = Integer.valueOf(times[0])  * 60 * 1000;
            maxTime = Integer.valueOf(times[1])  * 60 * 1000;
            String[] nums = fastTrade.getRandomNum().split(",");
            minNum = Integer.valueOf(nums[0]) * 10000;
            maxNum = Integer.valueOf(nums[1]) * 10000;
            this.ftrademapping = fastTrade.getFtrademapping();
            this.fuser = fastTrade.getUser();
        }

        /**
         * 获取随机值,不包含0
         * @param min
         * @param max
         * @return
         */
        public int getRandom(Integer min,Integer max){
            Random random = new Random();
            int result = 0;
            while (result == 0){
                result = random.nextInt(max - min) + min;
            }
            return result;
        }

        public void run(){
            logger.info("自动快速交易线程启动");
            while (!exit){
                try{
                    Double price = findPrice();
                    //LOG.i(this,"下单的价格是" + price);
                    if (price != null){
                        int rand  = getRandom(minNum, maxNum);
                        Double amount = Double.valueOf(rand * 1.0 / 10000);
                        amount(ftrademapping, fuser.getFid(), amount, price, Boolean.TRUE);
                        Long time = Long.valueOf(getRandom(minTime, maxTime));
                        // 休眠
                        Thread.sleep(time);
                    } else {
                        Thread.sleep(5000);
                    }
                } catch (Exception e){
                    LOG.e(this,e.getMessage(),e);
                }
            }
            logger.info("自动快速交易线程停止");
        }

        private BigDecimal getPrice(Fentrust[] obj){

            if (obj.length > 0){
                return BigDecimal.valueOf(obj[0].getFprize());
            }
            return null;
        }

        /**
         * 计算下单的价格
         */
        private Double findPrice(){
            Fentrust[] buys = realTimeData.getBuyDepthMap(ftrademapping.getFid(), 1);
            BigDecimal buy = getPrice(buys);
            Fentrust[] sells = realTimeData.getSellDepthMap(ftrademapping.getFid(), 1);
            BigDecimal sell = getPrice(sells);
            BigDecimal lastPrice = new BigDecimal(String.valueOf(realTimeData.getLatestDealPrize(ftrademapping.getFid())));

            if (buy == null && sell != null){
                int random = getRandom(0,temp.intValue());
                BigDecimal rand = new BigDecimal(random * 1.0 / 100);
                if (sell.subtract(lastPrice).compareTo(rand) == 1){
                    return lastPrice.add(rand).doubleValue();
                } else {
                    return lastPrice.subtract(rand).doubleValue();
                }
            } else if (buy != null && sell == null){
                int random = getRandom(0,temp.intValue());
                BigDecimal rand = new BigDecimal(random * 1.0 / 100);
                if (lastPrice.subtract(buy).compareTo(rand) == 1){
                    return lastPrice.subtract(rand).doubleValue();
                } else {
                    return lastPrice.add(rand).doubleValue();
                }
            } else if (buy != null &&  sell != null){
                BigDecimal value = sell.subtract(buy);
                if (value.compareTo(new BigDecimal("0.01")) == 1){
                    value = value.divide(new BigDecimal(2),2,BigDecimal.ROUND_DOWN);
                    value = value.multiply(new BigDecimal(100));
                    value = value.compareTo(temp) == 1?temp:value;
                    int random = 1;
                    if (value.intValue() != 1){
                        random = getRandom(1,value.intValue() == 1?2:value.intValue());
                    }
                    if (lastPrice.compareTo(sell) == 1){
                        lastPrice = sell;
                    } else if (buy.compareTo(lastPrice) == 1){
                        lastPrice = buy;
                    }
                    BigDecimal rand = new BigDecimal(random * 1.0 / 100);
                    if (sell.subtract(lastPrice).compareTo(lastPrice.subtract(buy)) == 1){
                        // sell 空间比较大
                        return lastPrice.add(rand).doubleValue();
                    } else if (sell.subtract(lastPrice).compareTo(lastPrice.subtract(buy)) == 0){
                        // 都没有空间的场合
                        return lastPrice.doubleValue();
                    } else {
                        // buy 空间比较大
                        return lastPrice.subtract(rand).doubleValue();
                    }
                }
            }
            return null;
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

        /**
         * 停止线程
         */
        public void stopThread(){
            exit = Boolean.TRUE;
        }

    }

    public static void main(String[] args) {
        while (true){
            //System.out.println(getRandom(2,6));
        }
    }
}
