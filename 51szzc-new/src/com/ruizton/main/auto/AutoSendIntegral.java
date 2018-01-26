package com.ruizton.main.auto;

import com.ruizton.main.Enum.IntegralTypeEnum;
import com.ruizton.main.log.LOG;
import com.ruizton.main.model.Fentrustlog;
import com.ruizton.main.model.Fuser;
import com.ruizton.main.model.integral.Fintegralactivity;
import com.ruizton.main.service.front.IntegralService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.TimerTask;

/**
 * Created by fenggq on 17-3-7.
 */
public class AutoSendIntegral extends TimerTask {
    @Autowired
    private RealTimeData realTimeData ;

    @Autowired
    private IntegralService integralService;

    @Override
    public void run() {
        int a = 1;
        Fentrustlog log = this.realTimeData.getOneIntegralLog();
        if (log != null) {
            try{
                Fuser fuser =  log.getFentrust().getFuser();
                if(!fuser.getFscore().isIntegralFirstTrade()){
                    this.integralService.addUserIntegralFirst(IntegralTypeEnum.TRADE_FIRST,fuser.getFid(),log.getFid());
                }
                //判断是否
                Fintegralactivity fintegralactivity = integralService.getIntegralActivy(IntegralTypeEnum.TRADE);

                if(fintegralactivity != null){
                    //交易送积分活动
                    this.integralService.addUserIntegral(IntegralTypeEnum.TRADE,fuser.getFid(),log.getFamount(),log.getFid());
                }
            }catch (Exception e){
                e.printStackTrace();
                LOG.e(this.getClass(),"交易送积分发生异常");
            }

        }
    }
}
