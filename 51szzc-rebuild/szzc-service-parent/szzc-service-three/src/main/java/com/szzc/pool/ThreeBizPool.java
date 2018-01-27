package com.szzc.pool;

import com.jucaifu.common.context.ApplicationContextHelper;
import com.szzc.api.biz.UserApiBiz;
import com.szzc.fentrustLog.biz.FentrustBiz;
import com.szzc.fentrustLog.biz.FentrustLogBiz;
import com.szzc.fentrustLog.biz.VirtualCoinTypeBiz;
import com.szzc.fquotes.biz.FquotesBiz;
import com.szzc.ftrademapping.biz.FtrademappingBiz;
import com.szzc.wallet.biz.VirtualWalletBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by lx on 17-5-25.
 */
@Component
public class ThreeBizPool {

    @Autowired
    public FentrustLogBiz fentrustLogBiz;

    @Autowired
    public FquotesBiz fquotesBiz;

    @Autowired
    public VirtualWalletBiz virtualWalletBiz;

    @Autowired
    public VirtualCoinTypeBiz virtualCoinTypeBiz;

    @Autowired
    public FtrademappingBiz ftrademappingBiz;

    @Autowired
    public FentrustBiz fentrustBiz;
    @Autowired
    public UserApiBiz userApiBiz;


    public static ThreeBizPool getInstance() {
        return ApplicationContextHelper.getInstance().getBean(ThreeBizPool.class);
    }

}
