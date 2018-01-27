package com.szzc.api.app.ctrl;

import com.jucaifu.common.log.LOG;
import com.jucaifu.common.network.HttpClientHelper;
import com.jucaifu.common.util.JsonHelper;
import com.szzc.api.app.ctrl.request.BindAlipayReq;
import com.szzc.api.app.ctrl.request.BindBankReq;
import org.apache.http.HttpRequest;
import org.apache.poi.util.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Created by fenggq on 17-3-21.
 */
@Controller
public class AppAssetsCtrl extends BaseCtrl{

    /**
     * 添加支付宝帐号
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = APP_ALIPAY_ADD_ACCOUNT, method = RequestMethod.POST, produces=JsonEncode_Text)
    public String getAppInfoBankList(@RequestBody BindAlipayReq req) throws Exception {
        LOG.dStart(this, "添加支付宝帐号");
        String result = HttpClientHelper.postJsonParams(buildPostUrl(APP_ALIPAY_ADD_ACCOUNT), JsonHelper.obj2JsonStr(req));
        LOG.dEnd(this,"添加支付宝帐号");
        return result;
    }

    /**
     * 人民币银行卡充值页面初始
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = APP_RECHARGE_CNY_INIT, method = RequestMethod.GET,produces=JsonEncode_Text)
    public String getRechargeInit(HttpServletRequest request) throws Exception {
        LOG.dStart(this, "人民币银行卡充值页面初始");

        int type = Integer.parseInt(request.getParameter("type"));
        String token = request.getParameter("loginToken");

        String result = HttpClientHelper.sendGetRequest(buildGetUrl(APP_RECHARGE_CNY_INIT+"?type="+type,token), Boolean.FALSE);
        LOG.d(this,result);
        LOG.dEnd(this,"人民币银行卡充值页面初始");
        return result;
    }


    /**
     * 绑定银行卡
     */
    @ResponseBody
    @RequestMapping(value = APP_BANK_ADD_ACCOUNT, method = RequestMethod.POST, produces=JsonEncode_Text)
    public String updateOutAddress(@RequestBody BindBankReq req) throws Exception {
        LOG.dStart(this, "绑定银行卡");
        String result = HttpClientHelper.postJsonParams(buildPostUrl(APP_BANK_ADD_ACCOUNT), JsonHelper.obj2JsonStr(req));
        LOG.dEnd(this,"绑定银行卡");
        return result;
    }


    //充值提现记录,type:1人民币充值，2人民币提现，3虚拟币充值，4虚拟币提现
    @ResponseBody
    @RequestMapping(value = APP_RECHARGE_RECORD, method = RequestMethod.GET,produces=JsonEncode_Text)
    public String GetAllRecords(
            @RequestParam Integer type,
            @RequestParam Integer currentPage,
            @RequestParam String symbol,
            @RequestParam String loginToken
    ) throws Exception {

        LOG.dStart(this, "人民币银行卡充值页面初始");

        String url = APP_RECHARGE_RECORD+"?type="+type+"&symbol="+symbol+"&currentPage="+currentPage;

        String result = HttpClientHelper.sendGetRequest(buildGetUrl(url,loginToken), Boolean.FALSE);
        LOG.d(this,result);
        LOG.dEnd(this,"人民币银行卡充值页面初始");
        return result;

    }


    //获取充值、提现相关文案   1.支付宝充值  2.银行卡  3.人民币提现  4.虚拟币提现
    @ResponseBody
    @RequestMapping(value = APP_RECHARGE_REMIND, method = RequestMethod.GET,produces=JsonEncode_Text)
    public String getRechargeRemind(@RequestParam int type){
        LOG.dStart(this, "人民币银行卡充值页面初始");
        String result = HttpClientHelper.sendGetRequest(buildGetUrl(APP_RECHARGE_REMIND+"?type="+type,null), Boolean.FALSE);
        LOG.d(this,result);
        LOG.dEnd(this,"人民币银行卡充值页面初始");
        return result;
    }

    @ResponseBody
    @RequestMapping(value = APP_RECHARGE_DETAIL, method = RequestMethod.GET,produces=JsonEncode_Text)
    public String getRechargeDetail(@RequestParam String loginToken,
                                    @RequestParam int operationId){

        LOG.dStart(this, "充值明细");
        String result = HttpClientHelper.sendGetRequest(buildGetUrl(APP_RECHARGE_DETAIL+"?operationId="+operationId,loginToken), Boolean.FALSE);
        LOG.d(this,result);
        LOG.dEnd(this,"充值明细");
        return result;

    }


}
