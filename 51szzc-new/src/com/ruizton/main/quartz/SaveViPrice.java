package com.ruizton.main.quartz;

import com.ruizton.main.Enum.CoinTypeEnum;
import com.ruizton.main.Enum.VirtualCoinTypeStatusEnum;
import com.ruizton.main.auto.RealTimeData;
import com.ruizton.main.log.LOG;
import com.ruizton.main.model.Ftimewallet;
import com.ruizton.main.model.Ftrademapping;
import com.ruizton.main.model.Fvirtualcointype;
import com.ruizton.main.model.Fvirtualwallet;
import com.ruizton.main.service.front.FtimewalletService;
import com.ruizton.main.service.front.FrontVirtualCoinService;
import com.ruizton.main.service.front.FtradeMappingService;
import com.ruizton.main.service.front.FvirtualWalletService;
import com.ruizton.util.DateHelper;
import com.ruizton.util.JsonHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 每天15点保存虚拟币和价格
 * Created by luwei on 17-3-6.
 */
public class SaveViPrice {

    //用户虚拟币存储信息
    @Autowired
    private FtimewalletService ftimewalletService;

    //法币匹配信息
    @Autowired
    protected FtradeMappingService ftradeMappingService ;

    //用户钱包信息
    @Autowired
    private FvirtualWalletService fvirtualWalletService;

    //实时更新的虚拟币价格信息
    @Autowired
    protected RealTimeData realTimeData;

    //币种信息
    @Autowired
    private FrontVirtualCoinService frontVirtualCoinService;

    public void save() {
        LOG.i(this, "每天15点保存用户虚拟币、价格信息开始");
        //判断今天是否有数据，有则删除
        boolean flag = this.ftimewalletService.queryExistsData();
        if(flag) {
            LOG.i(this, "当天已有数据，请检查时间是否有调整，影响job重复执行");
            this.ftimewalletService.deleteTodayData();
        }
        //获取各个币种的市价(当前最新价)
        List<Ftrademapping> ftrademappingList = ftradeMappingService.findActiveTradeMapping();
        if(ftrademappingList == null || ftrademappingList.size() == 0) {
            LOG.i(this, "获取法币匹配信息为空");
            return;
        }
        Map<Integer, BigDecimal> price_map = new HashMap<>();
        String fvi_fid_str = "";
        for(Ftrademapping ftrademapping : ftrademappingList) {
            BigDecimal price = new BigDecimal(realTimeData.getLatestDealPrize(ftrademapping.getFid())).setScale(ftrademapping.getFcount1(), BigDecimal.ROUND_DOWN) ;
            Integer fvi_fid = ftrademapping.getFvirtualcointypeByFvirtualcointype2().getFid();
            price_map.put(fvi_fid, price);
            fvi_fid_str += fvi_fid+",";
        }

        if(StringUtils.isNotBlank(fvi_fid_str)) {
            fvi_fid_str = fvi_fid_str.substring(0, fvi_fid_str.length() - 1);
        }

        LOG.i(this, "虚拟币价格："+ JsonHelper.obj2JsonStr(price_map));

        //查询法定货币信息
        int cny_fid = 0;
        List<Fvirtualcointype>   vcoinList = frontVirtualCoinService.findFvirtualCoinType(VirtualCoinTypeStatusEnum.Normal, CoinTypeEnum.FB_CNY_VALUE);
        if(vcoinList ==  null || vcoinList.size() == 0) {
            LOG.i(this, "查询虚拟币信息为空");
            return;
        }

        cny_fid = vcoinList.get(0).getFid();

        //获取用户的虚拟币信息(可用、冻结)
        StringBuffer filter = new StringBuffer();
        filter.append(" where 1=1 \n");
        filter.append(" and fvirtualcointype.fid in(" + fvi_fid_str + ")");
        List<Fvirtualwallet> userWalletList = fvirtualWalletService.list(0,0, filter.toString(), false);
        if(userWalletList == null || userWalletList.size() == 0) {
            LOG.i(this, "查询虚拟币钱包信息为空");
            return;
        }

        String dateString = DateHelper.date2String(new Date(), DateHelper.DateFormatType.YearMonthDay_HourMinuteSecond);
        Timestamp tm = Timestamp.valueOf(dateString);

        //保存数据进库
        for(Fvirtualwallet wallet : userWalletList) {
            Ftimewallet ftimewallet = new Ftimewallet();
            ftimewallet.setFcreateTime(tm);
            ftimewallet.setFprice(price_map.get(wallet.getFvirtualcointype().getFid()));
            BigDecimal able = new BigDecimal(wallet.getFtotal());
            BigDecimal freeze = new BigDecimal(wallet.getFfrozen());
            ftimewallet.setFtotal(able.add(freeze));
            ftimewallet.setFvirtualcointype(wallet.getFvirtualcointype());
            ftimewallet.setFuser(wallet.getFuser());
            this.ftimewalletService.save(ftimewallet);
        }


        LOG.i(this, "每天15点保存用户虚拟币、价格信息结束");
    }

}
