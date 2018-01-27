package com.otc.job;

import com.jucaifu.common.log.LOG;
import com.otc.pool.OTCBizPool;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 *
 * 获取用户资产记录定时任务
 * Created by fenggq on 17-5-23.
 */
@Component
public class UserAssetJob {

    /**
     * 每天零点执行，记录用户资产记录
     */
    @Scheduled(cron = "0 0 0 * * ? ")
    private void userAssetRunning(){
        LOG.dStart(this,"用户资产记录定时任务开始---------------------");
        try{
            OTCBizPool.getInstance().userAssetBiz.addAll();
        }catch (Exception e){
            LOG.e(this.getClass(),"记录用户资产记录出错");
            e.printStackTrace();
        }
        LOG.dEnd(this,"用户资产记录定时任务结束-----------------------");
    }
}
