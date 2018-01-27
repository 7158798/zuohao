package com.szzc.job;

import com.jucaifu.common.log.LOG;
import com.jucaifu.common.util.DateHelper;
import com.szzc.core.wallet.pool.WalletBizPool;
import com.szzc.facade.wallet.pojo.po.PlatformBalance;
import com.szzc.facade.wallet.pojo.vo.VirtualWalletStaticVo;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * 每天晚上统计平台账户总余额,1天1次
 * Created by luwei on 17-6-13.
 */

public class PlatformBalanceJob {


    public void syncInit() {
        synchronized (this) {
            syncBalance();
        }
    }


    public void syncBalance() {
        LOG.dStart(this, "定时同步平台账户余额开始");
        Date current_date = new Date();
        LOG.i(this, "平台余额列表定时统计，执行时间：" + DateHelper.date2String(current_date, DateHelper.DateFormatType.YearMonthDay_HourMinuteSecond));
        String date = DateHelper.date2String(current_date, DateHelper.DateFormatType.YearMonthDay);

        //查询此时平台账户余额总和
        List<VirtualWalletStaticVo>  walletStaticVoList = WalletBizPool.getInstance().virtualWalletBiz.queryStatisWallet();
        if(walletStaticVoList == null || walletStaticVoList.size() == 0) {
            LOG.d(this, "查询平台账户余额汇总信息为空");
            return;
        }

        //根据日期判断统计数据是否存在，存在则删除
        Integer result = WalletBizPool.getInstance().platformBalanceBiz.queryExistsByDate(date);
        if(result != null && result > 0) {
            LOG.d(this, "检查平台数据已经存在，请检查是否调整过系统时间，影响定时任务重复执行");
            WalletBizPool.getInstance().platformBalanceBiz.deleteByDate(date);
        }

        //将统计数据保存进数据库
        PlatformBalance platformBalance =  null;
        for(VirtualWalletStaticVo staticVo : walletStaticVoList) {
            platformBalance = new PlatformBalance();
            platformBalance.setFable(staticVo.getFable());
            platformBalance.setFfrozen(staticVo.getFfrozen());
            platformBalance.setFtotal(staticVo.getBalance());
            platformBalance.setFcreatetime(current_date);
            platformBalance.setFviId(staticVo.getfVi_fId());
            WalletBizPool.getInstance().platformBalanceBiz.insert(platformBalance);
        }

        LOG.dEnd(this, "定时同步平台账户余额结束");
    }
}
