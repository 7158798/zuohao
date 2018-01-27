package com.otc.job;

import com.base.wallet.utils.IWalletUtil;
import com.base.wallet.utils.Utils;
import com.jucaifu.common.log.LOG;
import com.otc.core.cache.CacheHelper;
import com.otc.facade.cache.CacheKey;
import com.otc.facade.user.enums.UserMessageConstantEnum;
import com.otc.facade.virtual.enums.VirtualRecordOutStatus;
import com.otc.facade.virtual.enums.VirtualRecordType;
import com.otc.facade.virtual.pojo.cond.VirtualRecordCond;
import com.otc.facade.virtual.pojo.po.VirtualRecord;
import com.otc.pool.OTCBizPool;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by lx on 17-6-11.
 */
@Component
public class WithdrawJob {

    @Scheduled(cron = "0 0/2 * * * ?")
    public void withdraw(){
        VirtualRecordCond cond = new VirtualRecordCond();
        // 系统等待
        cond.setStatus(VirtualRecordOutStatus.WAIT_SYSTEM_PROCESS.getCode());
        // 提币
        cond.setType(VirtualRecordType.WITHDRAW.getCode());
        List<VirtualRecord> rList = OTCBizPool.getInstance().virtualRecordBiz.queryListVirtualRecord(cond);
        if (rList != null && rList.size() > 0){
            for (VirtualRecord temp : rList){
                if (!VirtualRecordOutStatus.WAIT_SYSTEM_PROCESS.getCode().equals(temp.getStatus())){
                    // 状态不正确,不需要处理
                    continue;
                }
                // 锁定系统数据
                temp.setStatus(VirtualRecordOutStatus.SYSTEM_LOCK.getCode());
                OTCBizPool.getInstance().virtualRecordBiz.updateCore(temp);

                VirtualRecord tRecord = OTCBizPool.getInstance().virtualRecordBiz.select(temp.getId());
                if (tRecord != null){
                    if (VirtualRecordOutStatus.SYSTEM_LOCK.getCode().equals(tRecord.getStatus())){
                        // 系统锁定的场合
                        boolean flag = Boolean.FALSE;
                        String errorMsg = "";
                        // 审核订单订单开始
                        try {
                            IWalletUtil iWalletUtil = CacheHelper.getObj(CacheKey.buildWithdrawKey(tRecord.getId()));
                            if (iWalletUtil != null){
                                tRecord.setStatus(VirtualRecordOutStatus.SUCCESS.getCode());
                                OTCBizPool.getInstance().virtualRecordBiz.sendCoin(tRecord,iWalletUtil);
                                flag = Boolean.TRUE;
                            } else {
                                errorMsg = "提现id=" + tRecord.getId() + ",提现工具类钱包未找到";
                            }
                        } catch (Exception e) {
                            LOG.e(this,e.getMessage(),e);
                            // 发生异常
                            errorMsg = e.getMessage();
                        }
                        if (!flag){
                            // 操作失败的场景
                            tRecord.setStatus(VirtualRecordOutStatus.SYSTEM_PROCESS_FAIL.getCode());
                            // 错误信息
                            //tRecord.setErrorMsg(errorMsg);
                            // 发生异常修改提现的状态
                            OTCBizPool.getInstance().virtualRecordBiz.updateCore(tRecord);
                        } else {
                            // 通知
                            OTCBizPool.getInstance().messageBiz.sendMessage(tRecord.getUserId(), UserMessageConstantEnum.COIN_WITHOUT_SUCESS,null,tRecord.getId());
                        }
                    } else {
                        LOG.i(this,"提币记录ID:" + temp.getId() + ",该记录不能提币,状态不是系统锁定");
                    }
                } else {
                    LOG.i(this,"提币记录ID:" + temp.getId() + ",提币记录未找到");
                }
            }
        }
    }
}
