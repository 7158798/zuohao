package com.ruizton.main.service.front;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;

import com.ruizton.main.dao.integral.FusergradeDAO;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruizton.main.Enum.EntrustStatusEnum;
import com.ruizton.main.Enum.EntrustTypeEnum;
import com.ruizton.main.dao.FentrustDAO;
import com.ruizton.main.dao.FentrustlogDAO;
import com.ruizton.main.dao.FentrustplanDAO;
import com.ruizton.main.dao.FfeesDAO;
import com.ruizton.main.dao.FintrolinfoDAO;
import com.ruizton.main.dao.FtrademappingDAO;
import com.ruizton.main.dao.FuserDAO;
import com.ruizton.main.dao.FvirtualwalletDAO;
import com.ruizton.main.dao.UtilsDAO;
import com.ruizton.main.model.Fentrust;
import com.ruizton.main.model.Fentrustlog;
import com.ruizton.main.model.Fentrustplan;
import com.ruizton.main.model.Fintrolinfo;
import com.ruizton.main.model.Ftrademapping;
import com.ruizton.main.model.Fuser;
import com.ruizton.main.model.Fvirtualwallet;
import com.ruizton.util.Utils;

@Service
public class FrontTradeService {
    private static final Logger log = LoggerFactory
            .getLogger(FrontTradeService.class);

    @Autowired
    private FentrustDAO fentrustDAO;
    @Autowired
    private FentrustlogDAO fentrustlogDAO;
    @Autowired
    private FvirtualwalletDAO fvirtualwalletDAO;
    @Autowired
    private FentrustplanDAO fentrustplanDAO;
    @Autowired
    private FfeesDAO ffeesDAO;
    @Autowired
    private FintrolinfoDAO fintrolinfoDAO;
    @Autowired
    private FtrademappingDAO ftrademappingDAO;
    @Autowired
    private UtilsDAO utilsDAO;
    @Autowired
    private FuserDAO fuserDAO;
    @Autowired
    private FusergradeDAO fusergradeDAO;

    //手续费率
    private Map<Integer, Double> rates = new HashMap<Integer, Double>();

    public void putRates(Integer key, double value) {
        synchronized (this.rates) {
            this.rates.put(key, value);
        }
    }

    public double getRates(Integer level) {
        synchronized (this.rates) {
            return this.rates.get(level);
        }
    }

    //撮合
    public void updateDealMaking(Ftrademapping ftrademapping, Fentrust buy, Fentrust sell,
                                 Fentrustlog buyLog, Fentrustlog sellLog, int id) {


        double buyFee = 0D;
        if (buy.isFisLimit() == false) {//limit=0
            // 限价单
            buyFee = (buyLog.getFcount() / buy.getFcount()) * buy.getFfees();
        } else {//limit==1
            double ffeeRate = this.getRates(buy.getFuser().getFscore().getFlevel());
            buyFee = buyLog.getFcount() * ffeeRate;
        }

        if (buy.isFisLimit()) {
            buy.setFcount(Utils.add(buy.getFcount(), buyLog.getFcount()));
            buy.setFsuccessAmount(Utils.add(buy.getFsuccessAmount(), buyLog.getFamount()));
            buy.setFfees(Utils.add(buy.getFfees(), buyFee));

            buy.setFlastUpdatTime(Utils.getTimestamp());
            if ((buy.getFamount() - buy.getFsuccessAmount()) / buyLog.getFprize() < Utils.getDoubleForAccount(ftrademapping.getFcount2())) {
                buy.setFstatus(EntrustStatusEnum.AllDeal);
            } else {
                buy.setFstatus(EntrustStatusEnum.PartDeal);
            }
        } else {
            buy.setFleftCount(Utils.sub(buy.getFleftCount(), buyLog.getFcount()));
            buy.setFsuccessAmount(Utils.add(buy.getFsuccessAmount(), buyLog.getFamount()));
            buy.setFleftfees(Utils.sub(buy.getFleftfees(), buyFee));

            buy.setFlastUpdatTime(Utils.getTimestamp());
            if (buy.getFleftCount() < Utils.getDoubleForAccount(ftrademapping.getFcount2())) {
                buy.setFstatus(EntrustStatusEnum.AllDeal);
            } else {
                buy.setFstatus(EntrustStatusEnum.PartDeal);
            }
        }

        double sellFee = 0D;
        if (sell.isFisLimit() == false) {
            //limit==0 限价单
            sellFee = (buyLog.getFamount() / sell.getFamount()) * sell.getFfees();
        } else {
            //limit==1 市价单
            double sellRate = this.getRates(sell.getFuser().getFscore().getFlevel());
            sellFee = sellRate * sellLog.getFamount();
        }

        if (sell.isFisLimit()) {
            sell.setFsuccessAmount(Utils.add(sell.getFsuccessAmount(), buyLog.getFamount()));
            sell.setFamount(Utils.add(sell.getFamount(), buyLog.getFamount()));
            sell.setFleftCount(Utils.sub(sell.getFleftCount(), buyLog.getFcount()));
            sell.setFfees(Utils.add(sell.getFfees(), sellFee));

            sell.setFlastUpdatTime(Utils.getTimestamp());
            if (sell.getFleftCount() < Utils.getDoubleForAccount(ftrademapping.getFcount2())) {
                sell.setFstatus(EntrustStatusEnum.AllDeal);
            } else {
                sell.setFstatus(EntrustStatusEnum.PartDeal);
            }

        } else {
            sell.setFleftCount(Utils.sub(sell.getFleftCount(), buyLog.getFcount()));
            sell.setFsuccessAmount(Utils.add(sell.getFsuccessAmount(), sellLog.getFamount()));
            sell.setFleftfees(Utils.sub(sell.getFleftfees(), sellFee));

            sell.setFlastUpdatTime(Utils.getTimestamp());
            if (sell.getFleftCount() < Utils.getDoubleForAccount(ftrademapping.getFcount2())) {
                sell.setFstatus(EntrustStatusEnum.AllDeal);
            } else {
                sell.setFstatus(EntrustStatusEnum.PartDeal);
            }
        }

        // 货币钱包
        Fvirtualwallet fbuyWallet = this.fackFvirtualwallet(buy.getFuser().getFid(), ftrademapping.getFvirtualcointypeByFvirtualcointype1().getFid());
        // 虚拟币钱包
        Fvirtualwallet fbuyVirtualwallet = this.fackFvirtualwallet(buy.getFuser().getFid(), ftrademapping.getFvirtualcointypeByFvirtualcointype2().getFid());
        // 货币钱包
        Fvirtualwallet fsellWallet = this.fackFvirtualwallet(sell.getFuser().getFid(), ftrademapping.getFvirtualcointypeByFvirtualcointype1().getFid());
        // 虚拟币钱包
        Fvirtualwallet fsellVirtualwallet = this.fackFvirtualwallet(sell.getFuser().getFid(), ftrademapping.getFvirtualcointypeByFvirtualcointype2().getFid());

        fsellWallet = theSame(fsellWallet, fbuyWallet, fbuyVirtualwallet, fsellVirtualwallet);
        fbuyVirtualwallet = theSame(fbuyVirtualwallet, fbuyWallet, fsellWallet, fsellVirtualwallet);
        fsellVirtualwallet = theSame(fsellVirtualwallet, fbuyWallet, fbuyVirtualwallet, fsellWallet);

        //买法币
        fbuyWallet.setFfrozen(Utils.sub(fbuyWallet.getFfrozen(), buyLog.getFamount()));
        //卖法币
        fsellWallet.setFtotal(Utils.add(fsellWallet.getFtotal(), Utils.sub(buyLog.getFamount(), sellFee)));
        //买虚拟
        fbuyVirtualwallet.setFtotal(Utils.add(fbuyVirtualwallet.getFtotal(), Utils.sub(buyLog.getFcount(), buyFee)));
        //卖虚拟
        fsellVirtualwallet.setFfrozen(Utils.sub(fsellVirtualwallet.getFfrozen(), buyLog.getFcount()));


        if (buy.getFstatus() == EntrustStatusEnum.AllDeal) {
            // 因为有人低价卖出，冻结剩余部分返回钱包
            double left_amount = Utils.sub (buy.getFamount() , buy.getFsuccessAmount());
            fbuyWallet.setFfrozen(Utils.sub( fbuyWallet.getFfrozen(), left_amount));
            fbuyWallet.setFtotal(Utils.add( fbuyWallet.getFtotal() , left_amount));
        }

        fentrustlogDAO.save(buyLog);
        fentrustlogDAO.save(sellLog);

        fentrustDAO.attachDirty(buy);
        fentrustDAO.attachDirty(sell);

        this.updateFackFvirtualwallet(fsellVirtualwallet, fbuyVirtualwallet, fbuyWallet, fsellWallet);

    }


    private void updateFackFvirtualwallet(Fvirtualwallet w1, Fvirtualwallet w2, Fvirtualwallet w3, Fvirtualwallet w4) {
        String hql = "update Fvirtualwallet set ftotal=ftotal+? , ffrozen=ffrozen+? , version=version+1 where fuser.fid=? and fvirtualcointype.fid=?";
        int count = this.utilsDAO.executeHQL(hql, w1.getFtotal(), w1.getFfrozen(), w1.getFack_uid(), w1.getFack_vid());

        if (w2.getFack_id().equals(w1.getFack_id()) == false) {
            count = this.utilsDAO.executeHQL(hql, w2.getFtotal(), w2.getFfrozen(), w2.getFack_uid(), w2.getFack_vid());
        }

        if (w3.getFack_id().equals(w1.getFack_id()) == false && w3.getFack_id().equals(w2.getFack_id()) == false) {
            count = this.utilsDAO.executeHQL(hql, w3.getFtotal(), w3.getFfrozen(), w3.getFack_uid(), w3.getFack_vid());
        }

        if (w4.getFack_id().equals(w1.getFack_id()) == false && w4.getFack_id().equals(w2.getFack_id()) == false && w4.getFack_id().equals(w3.getFack_id()) == false) {
            count = this.utilsDAO.executeHQL(hql, w4.getFtotal(), w4.getFfrozen(), w4.getFack_uid(), w4.getFack_vid());
        }

    }

    private Fvirtualwallet fackFvirtualwallet(int fuserid, int vid) {
        Fvirtualwallet fvirtualwallet = new Fvirtualwallet();
        fvirtualwallet.setFack_uid(fuserid);
        fvirtualwallet.setFack_vid(vid);

        fvirtualwallet.setFack_id(fuserid + "_" + vid);
        return fvirtualwallet;
    }

    private Fvirtualwallet theSame(Fvirtualwallet v1, Fvirtualwallet v2, Fvirtualwallet v3, Fvirtualwallet v4) {
        if (v1.getFack_id().equals(v2.getFack_id())) {
            return v2;
        }
        if (v1.getFack_id().equals(v3.getFack_id())) {
            return v3;
        }
        if (v1.getFack_id().equals(v4.getFack_id())) {
            return v4;
        }
        return v1;
    }

    public Fentrust findFentrustById(int id) {
        return this.fentrustDAO.findById(id);
    }

    public List<Fentrustlog> findFentrustLogByFentrust(Fentrust fentrust) {
        return this.fentrustlogDAO.findByProperty("fentrust.fid",
                fentrust.getFid());
    }

    // 最新成交记录
    public List<Fentrust> findLatestSuccessDeal(int coinTypeId,
                                                int fentrustType, int count) {
        return this.fentrustDAO.findLatestSuccessDeal(coinTypeId, fentrustType,
                count);
    }

    public List<Fentrust> findAllGoingFentrust(int coinTypeId,
                                               int fentrustType, boolean isLimit) {
        return this.fentrustDAO.findAllGoingFentrust(coinTypeId, fentrustType,
                isLimit);
    }

    // 获得24小时内的成交记录
    public List<Fentrustlog> findLatestSuccessDeal24(int coinTypeId, int hour) {
        List<Fentrustlog> list = this.fentrustlogDAO.findLatestSuccessDeal24(coinTypeId, 24);
        if (list == null || list.size() == 0) {
            return null;
        }
        return list;
    }

    /**
     * 取最新成交价
     *
     * @param coinTypeId
     * @return
     */
    public Fentrustlog findLatestDeal(int coinTypeId) {
        Fentrustlog fentrust = this.fentrustDAO.findLatestDeal(coinTypeId);
        if (fentrust == null) return null;
        return fentrust;
    }

    /**
     * 取某个币种价格成交信息
     *
     * @param coinTypeId
     * @return
     */
    public List<Fentrustlog> findNewLatestDeal(int coinTypeId, int max_result) {
        return this.fentrustDAO.findNewLatestDeal(coinTypeId, max_result);
    }

    // 委托记录
    public List<Fentrust> findFentrustHistory(int fuid, int fvirtualCoinTypeId,
                                              int[] entrust_type, int first_result, int max_result, String order,
                                              int entrust_status[], Date beginDate, Date endDate)
            throws Exception {
        List<Fentrust> list = this.fentrustDAO.getFentrustHistory(fuid,
                fvirtualCoinTypeId, entrust_type, first_result, max_result,
                order, entrust_status, beginDate, endDate);
        return list;
    }

    // 计划委托
    public List<Fentrustplan> findEntrustPlan(int type, int status[]) {
        List<Fentrustplan> list = this.fentrustplanDAO.findEntrustPlan(type,
                status);

        return list;
    }

    public Fentrust updateEntrustBuy(int tradeMappingID, double tradeAmount,
                                     double tradeCnyPrice, Fuser fuser, boolean fisLimit,
                                     HttpServletRequest req, boolean ffeeFlag) throws Exception {
        try {
            Ftrademapping mapping = this.ftrademappingDAO.findById(tradeMappingID);

//			double ffeeRate = this.ffeesDAO.findFfee(tradeMappingID,
//					fuser.getFscore().getFlevel()).getFbuyfee();
            //修改交易手续费
            double ffeeRate = 0F;
            if (ffeeFlag) {
                ffeeRate = this.fusergradeDAO.getTradeFee(fuser.getFscore().getFlevel());
            }
            double ffee = 0F;

            // 买入总价格
            double totalTradePrice = 0F;
            if (fisLimit) { //市价买入
                totalTradePrice = tradeCnyPrice;
                ffee = 0;
            } else {
                // 总交易人民币
                totalTradePrice = tradeAmount * tradeCnyPrice;
                // 手续费
                ffee = tradeAmount * ffeeRate;
            }
            Fvirtualwallet fwallet = this.fvirtualwalletDAO.findVirtualWallet(fuser.getFid(), mapping.getFvirtualcointypeByFvirtualcointype1().getFid());
            if (fwallet.getFtotal() < totalTradePrice) return null;

			/*fwallet.setFtotal(fwallet.getFtotal() - totalTradePrice);
            fwallet.setFfrozen(fwallet.getFfrozen()
					+ totalTradePrice);
			fwallet.setFlastUpdateTime(Utils.getTimestamp());
			this.fvirtualwalletDAO.attachDirty(fwallet);*/


            Fentrust fentrust = new Fentrust();

            if (fisLimit) {
                fentrust.setFcount(0F);
                fentrust.setFleftCount(0F);
                fentrust.setFprize(0);
            } else {
                fentrust.setFcount(tradeAmount);
                fentrust.setFleftCount(tradeAmount);
                fentrust.setFprize(tradeCnyPrice);
            }

            fentrust.setFamount(totalTradePrice);
            fentrust.setFfees(ffee);
            fentrust.setFleftfees(ffee);
            fentrust.setFcreateTime(Utils.getTimestamp());
            fentrust.setFentrustType(EntrustTypeEnum.BUY);
            fentrust.setFisLimit(fisLimit);
            fentrust.setFlastUpdatTime(Utils.getTimestamp());
            fentrust.setFstatus(EntrustStatusEnum.Going);
            fentrust.setFsuccessAmount(0F);
            fentrust.setFhasSubscription(false);
            fentrust.setFuser(fuser);
            fentrust.setFtrademapping(mapping);
            this.fentrustDAO.save(fentrust);

            String hql = "update Fvirtualwallet set ftotal=ftotal+? , ffrozen=ffrozen+? , version=version+1 where fuser.fid=? and fvirtualcointype.fid=? AND ftotal>=?";
            int count = this.utilsDAO.executeHQL(hql, -totalTradePrice, +totalTradePrice, fuser.getFid(), mapping.getFvirtualcointypeByFvirtualcointype1().getFid(), totalTradePrice);
            if (count <= 0) {
                throw new RuntimeException();
            }

            return fentrust;
        } catch (Exception e) {
            throw new RuntimeException();
        }

    }

    // 委托买入
    public Fentrust updateEntrustBuy(int tradeMappingID, double tradeAmount,
                                     double tradeCnyPrice, Fuser fuser, boolean fisLimit,
                                     HttpServletRequest req) throws Exception {
        return this.updateEntrustBuy(tradeMappingID, tradeAmount, tradeCnyPrice, fuser, fisLimit, req, Boolean.TRUE);
    }


    public Fentrust updateEntrustSell(int tradeMappingID, double tradeAmount,
                                      double tradeCnyPrice, Fuser fuser, boolean fisLimit,
                                      HttpServletRequest req, boolean ffeeFlag) throws Exception {
        try {
            Ftrademapping mapping = this.ftrademappingDAO.findById(tradeMappingID);
            Fvirtualwallet fvirtualwallet = this.fvirtualwalletDAO.findVirtualWallet(fuser.getFid(), mapping.getFvirtualcointypeByFvirtualcointype2().getFid());
            if (fvirtualwallet.getFtotal() < tradeAmount) {
                return null;
            }

			/*fvirtualwallet.setFtotal(fvirtualwallet.getFtotal() - tradeAmount);
            fvirtualwallet.setFfrozen(fvirtualwallet.getFfrozen() + tradeAmount);
			fvirtualwallet.setFlastUpdateTime(Utils.getTimestamp());
			this.fvirtualwalletDAO.attachDirty(fvirtualwallet);*/


//			double ffeeRate = this.ffeesDAO.findFfee(tradeMappingID,
//					fuser.getFscore().getFlevel()).getFfee();
            //修改交易手续费
            double ffeeRate = 0F;
            if (ffeeFlag) {
                ffeeRate = this.fusergradeDAO.getTradeFee(fuser.getFscore().getFlevel());
            }
            Fentrust fentrust = new Fentrust();

            //总手续费人民币
            double ffee = 0;
            if (fisLimit) {
                fentrust.setFamount(0F);
                fentrust.setFfees(ffee);
                fentrust.setFleftfees(ffee);
            } else {
                ffee = tradeAmount * tradeCnyPrice * ffeeRate;
                fentrust.setFamount(tradeAmount * tradeCnyPrice);
                fentrust.setFfees(ffee);
                fentrust.setFleftfees(ffee);
            }

            fentrust.setFcount(tradeAmount);
            fentrust.setFleftCount(tradeAmount);
            fentrust.setFcreateTime(Utils.getTimestamp());
            fentrust.setFentrustType(EntrustTypeEnum.SELL);
            fentrust.setFisLimit(fisLimit);
            fentrust.setFlastUpdatTime(Utils.getTimestamp());
            fentrust.setFprize(tradeCnyPrice);
            fentrust.setFstatus(EntrustStatusEnum.Going);
            fentrust.setFsuccessAmount(0F);
            fentrust.setFuser(fuser);
            fentrust.setFhasSubscription(false);
            fentrust.setFtrademapping(mapping);
            this.fentrustDAO.save(fentrust);

            String hql = "update Fvirtualwallet set ftotal=ftotal+? , ffrozen=ffrozen+? , version=version+1 where fuser.fid=? and fvirtualcointype.fid=? AND ftotal>=?";
            int count = this.utilsDAO.executeHQL(hql, -tradeAmount, +tradeAmount, fuser.getFid(), mapping.getFvirtualcointypeByFvirtualcointype2().getFid(), tradeAmount);
            if (count <= 0) {
                throw new RuntimeException();
            }

            return fentrust;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    // 委托卖出
    public Fentrust updateEntrustSell(int tradeMappingID, double tradeAmount,
                                      double tradeCnyPrice, Fuser fuser, boolean fisLimit,
                                      HttpServletRequest req) throws Exception {
        return updateEntrustSell(tradeMappingID, tradeAmount, tradeCnyPrice, fuser, fisLimit, req, Boolean.TRUE);
    }

    // 委托记录
    public List<Fentrust> findFentrustHistory(int firstResult, int maxResults,
                                              String filter, boolean isFY) throws Exception {
        List<Fentrust> list = this.fentrustDAO.list(firstResult, maxResults, filter, isFY);
        return list;
    }

    // 委托记录
    public List<Fentrust> findFentrustHistory(int fuid, int fvirtualCoinTypeId,
                                              int[] entrust_type, int first_result, int max_result, String order,
                                              int entrust_status[]) throws Exception {
        List<Fentrust> list = this.fentrustDAO.getFentrustHistory(fuid,
                fvirtualCoinTypeId, entrust_type, first_result, max_result,
                order, entrust_status);
        for (Fentrust fentrust : list) {
            Ftrademapping ftrademapping = fentrust.getFtrademapping();
            ftrademapping.getFvirtualcointypeByFvirtualcointype1().getFname();
            ftrademapping.getFvirtualcointypeByFvirtualcointype2().getFname();
        }
        return list;
    }

    public int findFentrustHistoryCount(int fuid, int fvirtualCoinTypeId,
                                        int[] entrust_type, int entrust_status[]) throws Exception {
        return this.fentrustDAO.getFentrustHistoryCount(fuid,
                fvirtualCoinTypeId, entrust_type, entrust_status);
    }

    public List<Fentrustplan> findFentrustplan(int fuser, int fvirtualcointype,
                                               int[] fstatus, int firtResult, int maxResult, String order) {
        return this.fentrustplanDAO.findFentrustplan(fuser, fvirtualcointype,
                fstatus, firtResult, maxResult, order);
    }

    public Fentrustplan findFentrustplanById(int id) {
        return this.fentrustplanDAO.findById(id);
    }

    public long findFentrustplanCount(int fuser, int fvirtualcointype,
                                      int[] fstatus) {
        return this.fentrustplanDAO.findFentrustplanCount(fuser,
                fvirtualcointype, fstatus);
    }


    public void updateCancelFentrust(Fentrust fentrust, Fuser fuser) {

        try {
            fentrust.setFlastUpdatTime(Utils.getTimestamp());
            fentrust.setFstatus(EntrustStatusEnum.Cancel);
            this.fentrustDAO.attachDirty(fentrust);

            if (fentrust.getFentrustType() == EntrustTypeEnum.BUY) {
                // 买
                Fvirtualwallet fwallet = this.fvirtualwalletDAO
                        .findVirtualWallet(fuser.getFid(), fentrust.getFtrademapping().getFvirtualcointypeByFvirtualcointype1().getFid());
                double leftAmount = fentrust.getFamount()
                        - fentrust.getFsuccessAmount();

                fwallet.setFtotal(fwallet.getFtotal() + leftAmount);
                fwallet.setFfrozen(fwallet.getFfrozen() - leftAmount);
                fwallet.setFlastUpdateTime(Utils.getTimestamp());
                this.fvirtualwalletDAO.attachDirty(fwallet);

            } else {
                // 卖
                Fvirtualwallet fvirtualwallet = this.fvirtualwalletDAO
                        .findVirtualWallet(fuser.getFid(), fentrust.getFtrademapping().getFvirtualcointypeByFvirtualcointype2().getFid());
                double leftCount = fentrust.getFleftCount();
                fvirtualwallet.setFtotal(fvirtualwallet.getFtotal() + leftCount);
                fvirtualwallet.setFfrozen(fvirtualwallet.getFfrozen() - leftCount);
                fvirtualwallet.setFlastUpdateTime(Utils.getTimestamp());
                this.fvirtualwalletDAO.attachDirty(fvirtualwallet);

            }
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public List<Fentrust> findFentrustByParam(int firstResult, int maxResults,
                                              String filter, boolean isFY) {
        return this.fentrustDAO.findByParam(firstResult, maxResults, filter,
                isFY, Fentrust.class);
    }

    public int findFentrustByParamCount(String filter) {
        return this.fentrustDAO.findByParamCount(filter, Fentrust.class);
    }

    public void updateFeeLog(Fentrust entrust, Fvirtualwallet fvirtualwallet) {
        try {
            this.fentrustDAO.attachDirty(entrust);
            this.fvirtualwalletDAO.attachDirty(fvirtualwallet);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }


    public List<Fentrust> findFentrustsByParam(int firstResult, int maxResults, String filter, boolean isFY) {
        return this.fentrustDAO.findByParam(firstResult, maxResults, filter, isFY, Fentrust.class);
    }


    public void updateFentrust(Fentrust fentrust) {
        try {
            this.fentrustDAO.attachDirty(fentrust);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public void updateCoinFentrust(Fentrust fentrust, List<Fvirtualwallet> fvirtualwallets, List<Fintrolinfo> fintrolinfos) {
        try {
            this.fentrustDAO.attachDirty(fentrust);
            for (Fintrolinfo fintrolinfo : fintrolinfos) {
                this.fintrolinfoDAO.save(fintrolinfo);
            }
            for (Fvirtualwallet fvirtualwallet : fvirtualwallets) {
                this.fvirtualwalletDAO.attachDirty(fvirtualwallet);
            }
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

}
