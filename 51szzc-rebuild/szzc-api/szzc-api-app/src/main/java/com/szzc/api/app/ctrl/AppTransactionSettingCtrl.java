package com.szzc.api.app.ctrl;

import com.jucaifu.common.annotation.token.TokenValidateAnno;
import com.jucaifu.common.log.LOG;
import com.jucaifu.common.network.HttpClientHelper;
import com.jucaifu.common.util.JsonHelper;
import com.szzc.api.app.constants.ApiConstants;
import com.szzc.api.app.ctrl.request.TransactionSettingReq;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

/**
 * 交易设置
 * Created by zygong on 17-3-29.
 */
@Controller
public class AppTransactionSettingCtrl implements ApiConstants {

    private static final String logoUrl = "https://www.51szzc.com/";

    /**
     * 删除银行卡（删除银行卡类型15）
     * @param req
     * @return
     */
    @TokenValidateAnno(skip = true)
    @RequestMapping(value = APP_TRANSACTIONSETTING_DELETEBANK, method = RequestMethod.POST, produces=JsonEncode_Text)
    @ResponseBody
    public String deleteBank(@RequestBody TransactionSettingReq req) throws IOException {
        LOG.dStart(this, "删除银行卡");
        String url = BASE_URL + APP_TRANSACTIONSETTING_DELETEBANK + ".html";
        String result = HttpClientHelper.postJsonParams(url, JsonHelper.obj2JsonStr(req));
        LOG.d(this,result);
        LOG.dEnd(this,"删除银行卡");
        return result;
    }

    /**
     * 获取银行卡信息
     * @return
     */
    @TokenValidateAnno(skip = true)
    @RequestMapping(value = APP_TRANSACTIONSETTING_BANKINFO, method = RequestMethod.GET ,produces=JsonEncode_Text)
    @ResponseBody
    public String getBankInfo(String loginToken, Integer bankId){
        LOG.dStart(this, "获取银行卡信息");
        String url = BASE_URL + APP_TRANSACTIONSETTING_BANKINFO + ".html?loginToken=" + loginToken + "&bankId=" + bankId;
        String result = HttpClientHelper.sendGetRequest(url, Boolean.FALSE);
        LOG.d(this,result);
        LOG.dEnd(this,"获取银行卡信息");
        return result;
    }

    /**
     * 获取银行列表
     * @return
     */
    @TokenValidateAnno(skip = true)
    @RequestMapping(value = APP_TRANSACTIONSETTING_BANKLIST, method = RequestMethod.GET ,produces=JsonEncode_Text)
    @ResponseBody
    public String getBankList(){
        LOG.dStart(this, "获取银行列表");
        String url = BASE_URL + APP_TRANSACTIONSETTING_BANKLIST + ".html";
        String result = HttpClientHelper.sendGetRequest(url, Boolean.FALSE);
        LOG.d(this,result);
        LOG.dEnd(this,"获取银行列表");
        return result;
    }

    /**
     * 获取用户银行卡列表
     * @param loginToken
     * @return
     */
    @TokenValidateAnno(skip = true)
    @RequestMapping(value = APP_TRANSACTIONSETTING_USERBANKLIST, method = RequestMethod.GET ,produces=JsonEncode_Text)
    @ResponseBody
    public String getUserBankList(String loginToken){
        LOG.dStart(this, "获取用户银行卡列表");
        String url = BASE_URL + APP_TRANSACTIONSETTING_USERBANKLIST + ".html?loginToken=" + loginToken;
        String result = HttpClientHelper.sendGetRequest(url, Boolean.FALSE);
        LOG.d(this,result);
        LOG.dEnd(this,"获取用户银行卡列表");
        return result;
    }

    /**
     * 获取用户支付宝列表
     * @param loginToken
     * @return
     */
    @TokenValidateAnno(skip = true)
    @RequestMapping(value = APP_TRANSACTIONSETTING_USERALIPAYLIST, method = RequestMethod.GET ,produces=JsonEncode_Text)
    @ResponseBody
    public String getUserAlipayList(String loginToken){
        LOG.dStart(this, "获取用户支付宝列表");
        String url = BASE_URL + APP_TRANSACTIONSETTING_USERALIPAYLIST + ".html?loginToken=" + loginToken;
        String result = HttpClientHelper.sendGetRequest(url, Boolean.FALSE);
        LOG.d(this,result);
        LOG.dEnd(this,"获取用户支付宝列表");
        return result;
    }

    /**
     * 获取支付宝信息
     * @param loginToken    token
     * @param AlipayId      支付宝id
     * @return
     */
    @TokenValidateAnno(skip = true)
    @RequestMapping(value = APP_TRANSACTIONSETTING_ALIPAYINFO, method = RequestMethod.GET ,produces=JsonEncode_Text)
    @ResponseBody
    public String getAlipayInfo(String loginToken, Integer AlipayId){
        LOG.dStart(this, "获取支付宝信息");
        String url = BASE_URL + APP_TRANSACTIONSETTING_ALIPAYINFO + ".html?loginToken=" + loginToken + "&AlipayId=" + AlipayId;
        String result = HttpClientHelper.sendGetRequest(url, Boolean.FALSE);
        LOG.d(this,result);
        LOG.dEnd(this,"获取支付宝信息");
        return result;
    }

    /**
     * 删除支付宝（删除银行卡类型18）
     * @param req
     * @return
     */
    @TokenValidateAnno(skip = true)
    @RequestMapping(value = APP_TRANSACTIONSETTING_DELETEALIPAY, method = RequestMethod.POST, produces=JsonEncode_Text)
    @ResponseBody
    public String deleteAlipay(@RequestBody TransactionSettingReq req) throws IOException {
        LOG.dStart(this, "删除支付宝");
        String url = BASE_URL + APP_TRANSACTIONSETTING_DELETEALIPAY + ".html";
        String result = HttpClientHelper.postJsonParams(url, JsonHelper.obj2JsonStr(req));
        LOG.d(this,result);
        LOG.dEnd(this,"删除支付宝");
        return result;
    }

    /**
     * 获取虚拟币列表
     * @return
     */
    @TokenValidateAnno(skip = true)
    @RequestMapping(value = APP_TRANSACTIONSETTING_GETVIRTUALLIST, method = RequestMethod.GET ,produces=JsonEncode_Text)
    @ResponseBody
    public String getVirtualList(){
        LOG.dStart(this, "获取虚拟币列表");
        String url = BASE_URL + APP_TRANSACTIONSETTING_GETVIRTUALLIST + ".html";
        String result = HttpClientHelper.sendGetRequest(url, Boolean.FALSE);
        LOG.d(this,result);
        LOG.dEnd(this,"获取虚拟币列表");
        return result;
    }

    /**
     * 添加虚拟币地址 （MessageTypeEnum 8）
     * @param req
     * @return
     */
    @TokenValidateAnno(skip = true)
    @RequestMapping(value = APP_TRANSACTIONSETTING_SAVEVIRTUALADDRESS, method = RequestMethod.POST, produces=JsonEncode_Text)
    @ResponseBody
    public String saveVirtualAddress(@RequestBody TransactionSettingReq req) throws IOException {
        LOG.dStart(this, "添加虚拟币地址");
        String url = BASE_URL + APP_TRANSACTIONSETTING_SAVEVIRTUALADDRESS + ".html";
        String result = HttpClientHelper.postJsonParams(url, JsonHelper.obj2JsonStr(req));
        LOG.d(this,result);
        LOG.dEnd(this,"添加虚拟币地址");
        return result;
    }

    /**
     * 获取虚拟币地址列表
     * @param loginToken
     * @param symbol
     * @return
     */
    @TokenValidateAnno(skip = true)
    @RequestMapping(value = APP_TRANSACTIONSETTING_GETVIRTUALADDRESSLIST, method = RequestMethod.GET, produces=JsonEncode_Text)
    @ResponseBody
    public String getVirtualAddressList(String loginToken, Integer symbol){
        LOG.dStart(this, "获取虚拟币地址列表");
        String url = BASE_URL + APP_TRANSACTIONSETTING_GETVIRTUALADDRESSLIST + ".html?loginToken=" + loginToken + "&symbol=" + symbol;
        String result = HttpClientHelper.sendGetRequest(url, Boolean.FALSE);
        LOG.d(this,result);
        LOG.dEnd(this,"获取虚拟币地址列表");
        return result;
    }

    /**
     * 获取虚拟币地址详情
     * @param loginToken
     * @param virtualAddressId
     * @return
     */
    @TokenValidateAnno(skip = true)
    @RequestMapping(value = APP_TRANSACTIONSETTING_VIRTUALADDRESSINFO, method = RequestMethod.GET ,produces=JsonEncode_Text)
    @ResponseBody
    public String getVirtualAddressInfo(String loginToken, Integer virtualAddressId){
        LOG.dStart(this, "获取虚拟币地址详情");
        String url = BASE_URL + APP_TRANSACTIONSETTING_VIRTUALADDRESSINFO + ".html?loginToken=" + loginToken + "&virtualAddressId=" + virtualAddressId;
        String result = HttpClientHelper.sendGetRequest(url, Boolean.FALSE);
        LOG.d(this,result);
        LOG.dEnd(this,"获取虚拟币地址详情");
        return result;
    }

    /**
     * 删除虚拟币地址（删除虚拟币地址类型 8）
     * @param req
     * @return
     */
    @TokenValidateAnno(skip = true)
    @RequestMapping(value = APP_TRANSACTIONSETTING_DELETEVIRTUALADDRESS, method = RequestMethod.POST, produces=JsonEncode_Text)
    @ResponseBody
    public String deleteVirtualAddress(@RequestBody TransactionSettingReq req) throws IOException {
        LOG.dStart(this, "删除虚拟币地址");
        String url = BASE_URL + APP_TRANSACTIONSETTING_DELETEVIRTUALADDRESS + ".html";
        String result = HttpClientHelper.postJsonParams(url, JsonHelper.obj2JsonStr(req));
        LOG.d(this,result);
        LOG.dEnd(this,"删除虚拟币地址");
        return result;
    }

}
