package com.ruizton.main.controller.front;

import com.ruizton.main.Enum.CoinTypeEnum;
import com.ruizton.main.Enum.EntrustStatusEnum;
import com.ruizton.main.Enum.EntrustTypeEnum;
import com.ruizton.main.Enum.TrademappingStatusEnum;
import com.ruizton.main.auto.RealTimeData;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.controller.front.response.WebResponse;
import com.ruizton.main.model.*;
import com.ruizton.main.service.front.FrontTradeService;
import com.ruizton.main.service.front.FrontUserService;
import com.ruizton.main.service.front.FrontVirtualCoinService;
import com.ruizton.main.service.front.FtradeMappingService;
import com.ruizton.util.Utils;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by fenggq on 17-1-16.
 */
@Controller
public class FrontTradeAutoController extends BaseController{

    /**
     * 买入虚拟币
     * @param symbol
     * @param tradeAmount
     * @param tradeCnyPrice
     * @param fid
     * @param inside        是否内部
     * @return
     * @throws Exception
     */
    public WebResponse<Fentrust> insideBuySubmit(int symbol,double tradeAmount,double tradeCnyPrice,int fid,Boolean inside) throws Exception {
        int limited=0;//禁用市价单
        Ftrademapping ftrademapping = this.ftradeMappingService.findFtrademapping2(symbol) ;
        if(ftrademapping==null  ||ftrademapping.getFstatus()==TrademappingStatusEnum.FOBBID){
            return new WebResponse<Fentrust>(-100,"该币暂时不能交易");
        }

        //限制法币为人民币和比特币
        if(ftrademapping.getFvirtualcointypeByFvirtualcointype1().getFtype() != CoinTypeEnum.FB_CNY_VALUE
                && ftrademapping.getFvirtualcointypeByFvirtualcointype1().getFtype() != CoinTypeEnum.FB_COIN_VALUE){
            return new WebResponse<Fentrust>(-1,"网络错误");

        }

        Fvirtualcointype coin1 = ftrademapping.getFvirtualcointypeByFvirtualcointype1() ;
        Fvirtualcointype coin2 = ftrademapping.getFvirtualcointypeByFvirtualcointype2() ;
        double minBuyCount = ftrademapping.getFminBuyCount() ;
        double minBuyPrice = ftrademapping.getFminBuyPrice() ;
        double minBuyAmount = ftrademapping.getFminBuyAmount() ;

        //是否开放交易
        if(Utils.openTrade(ftrademapping,Utils.getTimestamp())==false){
            return new WebResponse<Fentrust>(-400,"现在不是交易时间");
        }

        tradeAmount = Utils.getDouble(tradeAmount, ftrademapping.getFcount2()) ;
        tradeCnyPrice = Utils.getDouble(tradeCnyPrice, ftrademapping.getFcount1()) ;

        //定价单
        if(limited == 0 ){

            if(tradeAmount<minBuyCount){
                return new WebResponse<Fentrust>(-1,"最小交易数量："+coin1.getfSymbol()+minBuyCount);
            }

            if(tradeCnyPrice < minBuyPrice){
                return new WebResponse<Fentrust>(-3,"最小挂单价格："+coin1.getfSymbol()+minBuyPrice);
            }

            double total = Utils.getDouble(tradeAmount*tradeCnyPrice,ftrademapping.getFcount1());
            if(total < minBuyAmount){
                return new WebResponse<Fentrust>(-3,"最小挂单金额："+coin1.getfSymbol()+minBuyAmount);
            }


        }else{
            if(tradeCnyPrice <minBuyAmount){
                return new WebResponse<Fentrust>(-33,"最小交易金额："+minBuyAmount+coin1.getFname());
            }
        }


        Fuser fuser = this.frontUserService.findById(fid) ;
        double totalTradePrice = 0F ;
        if(limited==0){
            totalTradePrice = tradeAmount*tradeCnyPrice ;
        }else{
            totalTradePrice = tradeAmount ;
        }
        Fvirtualwallet fwallet = this.frontUserService.findVirtualWalletByUser(fuser.getFid(),coin1.getFid());
        if(fwallet.getFtotal()<totalTradePrice){
            return new WebResponse<Fentrust>(-4,coin1.getFname()+"余额不足");
        }

        boolean flag = false ;
        Fentrust fentrust = null ;
        try {
            fentrust = this.frontTradeService.updateEntrustBuy(symbol, tradeAmount, tradeCnyPrice, fuser, limited==1,null,Boolean.FALSE) ;
            flag = true ;
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(flag && fentrust != null){
            fentrust = this.frontTradeService.findFentrustById(fentrust.getFid()) ;
            if (limited==1) {
                this.realTimeData.addEntrustLimitBuyMap(symbol, fentrust);
            } else {
                if (inside){
                    // 内部订单
                    this.realTimeData.addInsideEntrustBuyMap(symbol,fentrust);
                } else {
                    // 市场交易
                    this.realTimeData.addEntrustBuyMap(symbol, fentrust);
                }
            }

            return new WebResponse<Fentrust>(0,"下单成功",fentrust);
        }else{
            return new WebResponse<Fentrust>(-200,"网络错误，请稍后再试");
        }
    }
    /**
     *
     * @param symbol 币种
     * @param tradeAmount //数量
     * @param tradeCnyPrice //单价
     * @param fid 用户id
     * @return
     * @throws Exception
     */

    public WebResponse<Fentrust> buyBtcSubmit(int symbol,double tradeAmount,double tradeCnyPrice,int fid) throws Exception{
        return insideBuySubmit(symbol,tradeAmount,tradeCnyPrice,fid,Boolean.FALSE);
    }


    public WebResponse<Fentrust> insideSellSubmit(int symbol,double tradeAmount, double tradeCnyPrice,int fid,Boolean inside) throws Exception{
        int limited = 0;
        Ftrademapping ftrademapping = this.ftradeMappingService.findFtrademapping2(symbol) ;
        if(ftrademapping==null  || ftrademapping.getFstatus()!= TrademappingStatusEnum.ACTIVE){
            return new WebResponse<Fentrust>(-100,"该币暂时不能交易");
        }

        //限制法币为人民币和比特币
        if(ftrademapping.getFvirtualcointypeByFvirtualcointype1().getFtype() != CoinTypeEnum.FB_CNY_VALUE
                && ftrademapping.getFvirtualcointypeByFvirtualcointype1().getFtype() != CoinTypeEnum.FB_COIN_VALUE){
            return new WebResponse<Fentrust>(-1,"网络错误");
        }

        tradeAmount = Utils.getDouble(tradeAmount, ftrademapping.getFcount2()) ;
        tradeCnyPrice = Utils.getDouble(tradeCnyPrice, ftrademapping.getFcount1()) ;
        Fvirtualcointype coin1 = ftrademapping.getFvirtualcointypeByFvirtualcointype1() ;
        Fvirtualcointype coin2 = ftrademapping.getFvirtualcointypeByFvirtualcointype2() ;
        double minBuyCount = ftrademapping.getFminBuyCount() ;
        double minBuyPrice = ftrademapping.getFminBuyPrice() ;
        double minBuyAmount = ftrademapping.getFminBuyAmount() ;

        //是否开放交易
        if(Utils.openTrade(ftrademapping,Utils.getTimestamp())==false){
            return new WebResponse<Fentrust>(-400,"现在不是交易时间");
        }

        if(limited == 0 ){

            if(tradeAmount<minBuyCount){
                return new WebResponse<Fentrust>(-1,"最小交易数量："+coin1.getfSymbol()+minBuyCount);
            }

            if(tradeCnyPrice < minBuyPrice){
                return new WebResponse<Fentrust>(-3,"最小挂单价格："+coin1.getfSymbol()+minBuyPrice);
            }
            double total = Utils.getDouble(tradeAmount*tradeCnyPrice,ftrademapping.getFcount1());
            if(total < minBuyAmount){
                return new WebResponse<Fentrust>(-3,"最小挂单金额："+coin1.getfSymbol()+minBuyAmount);
            }

        }else{
            if(tradeAmount <minBuyCount){
                return new WebResponse<Fentrust>(-33,"最小交易数量："+minBuyCount+coin2.getFname());
            }
        }

        Fuser fuser = this.frontUserService.findById(fid) ;
        Fvirtualwallet fvirtualwallet = this.frontUserService.findVirtualWalletByUser(fuser.getFid(), coin2.getFid()) ;
        if(fvirtualwallet==null){
            return new WebResponse<Fentrust>(-200,"系统错误，请联系管理员");
        }
        if(fvirtualwallet.getFtotal()<tradeAmount){
            return  new WebResponse<Fentrust>(-4,coin2.getFname()+"余额不足");
        }

        boolean flag = false ;
        Fentrust fentrust = null ;
        try {
            fentrust = this.frontTradeService.updateEntrustSell(symbol, tradeAmount, tradeCnyPrice, fuser, limited==1,null,Boolean.FALSE) ;
            flag = true ;
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(flag && fentrust != null){
            fentrust = this.frontTradeService.findFentrustById(fentrust.getFid()) ;
            if (limited==1) {
                this.realTimeData.addEntrustLimitSellMap(symbol, fentrust);
            } else {
                if (inside){
                    // 内部卖单
                    this.realTimeData.addInsideEntrustSellMap(symbol,fentrust);
                } else {
                    this.realTimeData.addEntrustSellMap(symbol, fentrust);
                }
            }
            return new WebResponse<Fentrust>(0,"下单成功",fentrust);
        }else{
            return new WebResponse<Fentrust>(-200,"网络错误，请稍后再试");
        }
    }


    /**
     * 挂单卖出
     * @param symbol   币种
     * @param tradeAmount  交易数量
     * @param tradeCnyPrice   交易单价
     * @param fid   用户id
     * @return
     * @throws Exception
     */
    public WebResponse<Fentrust> sellBtcSubmitTest(int symbol,double tradeAmount, double tradeCnyPrice,int fid) throws Exception{
        return insideSellSubmit(symbol,tradeAmount,tradeCnyPrice,fid,Boolean.FALSE);
    }


    public String insideCancelEntrust(int uid,int id,Boolean inside){

        JSONObject jsonObject = new JSONObject() ;

        Fuser fuser = this.frontUserService.findById(uid);
        Fentrust fentrust = this.frontTradeService.findFentrustById(id) ;
        if(fentrust!=null
                &&(fentrust.getFstatus()== EntrustStatusEnum.Going || fentrust.getFstatus()==EntrustStatusEnum.PartDeal )
                &&fentrust.getFuser().getFid() == fuser.getFid() ){
            boolean flag = false ;
            try {
                this.frontTradeService.updateCancelFentrust(fentrust, fuser) ;
                flag = true ;
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(flag==true){
                if(fentrust.getFentrustType()== EntrustTypeEnum.BUY){

                    //买
                    if(fentrust.isFisLimit()){
                        this.realTimeData.removeEntrustLimitBuyMap(fentrust.getFtrademapping().getFid(), fentrust) ;
                    }else{
                        if (inside){
                            // 取消内部买单
                            this.realTimeData.removeInsideEntrustBuyMap(fentrust.getFtrademapping().getFid(),fentrust);
                        } else {
                            this.realTimeData.removeEntrustBuyMap(fentrust.getFtrademapping().getFid(), fentrust) ;
                        }
                    }
                }else{

                    //卖
                    if(fentrust.isFisLimit()){
                        this.realTimeData.removeEntrustLimitSellMap(fentrust.getFtrademapping().getFid(), fentrust) ;
                    }else{
                        if (inside){
                            // 取消内部卖单
                            this.realTimeData.removeInsideEntrustSellMap(fentrust.getFtrademapping().getFid(),fentrust);
                        } else {
                            this.realTimeData.removeEntrustSellMap(fentrust.getFtrademapping().getFid(), fentrust) ;
                        }
                    }

                }
            }
        }

        jsonObject.accumulate("code", 0) ;
        jsonObject.accumulate("msg", "取消成功") ;
        return jsonObject.toString() ;
    }

    /**
     * 取消委托订单
     * @param uid   用户id
     * @param id    订单id
     * @return
     * @throws Exception
     */
    public String cancelEntrust(int uid,int id) throws Exception{
        return insideCancelEntrust(uid,id,Boolean.FALSE);
    }

}
