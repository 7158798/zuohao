package com.ruizton.main.controller.app;

import com.ruizton.main.Enum.*;
import com.ruizton.main.comm.ConstantMap;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.controller.app.request.TransactionSettingReq;
import com.ruizton.main.log.LOG;
import com.ruizton.main.model.*;
import com.ruizton.main.model.vo.LogoExtendsIdAndName;
import com.ruizton.util.BTCUtils;
import com.ruizton.util.ETHUtils;
import com.ruizton.util.IdAndName;
import com.ruizton.util.Utils;
import com.ruizton.util.antshare.resp.TransactionResp;
import net.sf.json.JSONObject;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * 交易设置
 * Created by zygong on 17-3-29.
 */
@Controller
public class AppTransactionSettingCtrl extends BaseController implements ApiConstants {

    private static final String logoUrl = "https://www.51szzc.com/";

    /**
     * 删除银行卡（删除银行卡类型15）
     * @param loginToken        token
     * @param bankId            银行卡id
     * @param verificationCode  验证码
     * @return
     */
    @RequestMapping(value = APP_TRANSACTIONSETTING_DELETEBANK, method = RequestMethod.POST, produces = JsonEncode)
    @ResponseBody
    public String deleteBank(@RequestBody TransactionSettingReq req) {
        JSONObject jsonObject = new JSONObject();
        try {
            String loginToken = req.getLoginToken();
            Integer bankId = req.getBankId();
            String verificationCode = req.getVerificationCode();
            if (!checkParam(loginToken, bankId, verificationCode)) {
                LOG.w(this, "参数错误");
                return ApiConstant.getRequestError("操作失败");
            }
            //验证是否登录
            if (!this.realTimeData.isAppLogin(loginToken, true)) {
                return ApiConstant.getNoLoginError();
            }
            Fuser fuser = this.realTimeData.getAppFuser(loginToken);
            if (fuser == null) {
                return ApiConstant.getNoLoginError();
            }
            if (!fuser.isFisTelephoneBind()) {
                return ApiConstant.getRequestError("没有绑定手机号码");
            }

            String ip = getIpAddr(request);
//		int google_limit = this.frontValidateService.getLimitCount(ip, CountLimitTypeEnum.GOOGLE) ;
            int tel_limit = this.frontValidateService.getLimitCount(ip, CountLimitTypeEnum.TELEPHONE);

            if (tel_limit <= 0) {
                return ApiConstant.getRequestError("此ip操作频繁，请2小时后再试!");
            } else {

                if (!validateMessageCode(fuser, fuser.getFareaCode(), fuser.getFtelephone(), MessageTypeEnum.BANK_DEL, verificationCode)) {
                    //手機驗證錯誤
                    this.frontValidateService.updateLimitCount(ip, CountLimitTypeEnum.TELEPHONE);
                    return ApiConstant.getRequestError("手机验证码错误，您还有" + tel_limit + "次机会");
                } else {
                    this.frontValidateService.deleteCountLimite(ip, CountLimitTypeEnum.TELEPHONE);
                    this.validateMap.removeMessageMap(fuser.getFid()+"_"+ MessageTypeEnum.BANK_DEL);
                }
            }

            //成功
            this.bankinfoWithdrawService.deleteObj(bankId);
        } catch (Exception e) {
            return ApiConstant.getUnknownError(e);
        }
        return ApiConstant.getSuccessResp();
    }

    /**
     * 获取银行卡信息
     * @return
     */
    @RequestMapping(value = APP_TRANSACTIONSETTING_BANKINFO, method = RequestMethod.GET, produces = JsonEncode)
    @ResponseBody
    public String getBankInfo(String loginToken, Integer bankId){
        JSONObject jsonObject = new JSONObject();
        try {
            if (!checkParam(loginToken, bankId)) {
                LOG.w(this, "参数错误");
                return ApiConstant.getRequestError("操作失败");
            }
            if (!this.realTimeData.isAppLogin(loginToken, true)) {
                return ApiConstant.getNoLoginError();
            }

            Fuser user = this.realTimeData.getAppFuser(loginToken);
            if (user == null) {
                return ApiConstant.getRequestError("用户不存在");
            }
            if(null == bankId){
                LOG.w(this, "银行id参数为空");
                return ApiConstant.getRequestError();
            }


            FbankinfoWithdraw bank = this.bankinfoWithdrawService.findById(bankId);
            int l = bank.getFbankNumber().length();
            bank.setFbankNumber(bank.getFbankNumber().substring(l - 4, l));
            bank.setUrl(logoUrl + BankTypeEnum.getBankUrl(bank.getFbankType()));
            bank.setBgColor(BankTypeEnum.getBgColor(bank.getFbankType()));
            ObjectMapper mapper = new ObjectMapper();
            jsonObject.accumulate("bankInfo", mapper.writeValueAsString(bank));

        }catch (Exception e){
            LOG.e(this, "获取银行信息失败", e);
            return ApiConstant.getRequestError();
        }
        return ApiConstant.getSuccessResp(jsonObject);
    }

    /**
     * 获取银行列表
     * @return
     */
    @RequestMapping(value = APP_TRANSACTIONSETTING_BANKLIST, method = RequestMethod.GET, produces = JsonEncode)
    @ResponseBody
    public String getBankList(){
        JSONObject jsonObject = new JSONObject();

        //添加银行卡列表
        List<IdAndName> bankList = new ArrayList<IdAndName>();
        IdAndName idAndName = null;
        for (int i = 1; i <= BankTypeEnum.QT; i++) {
            if (BankTypeEnum.getEnumString(i) != null && BankTypeEnum.getEnumString(i).trim().length() > 0) {
                idAndName = new IdAndName();
                idAndName.setId(i);
                idAndName.setName(BankTypeEnum.getEnumString(i));
                bankList.add(idAndName);
            }
        }
        jsonObject.accumulate(LIST, bankList);

        return ApiConstant.getSuccessResp(jsonObject);
    }

    /**
     * 获取用户银行卡列表
     * @param loginToken
     * @return
     */
    @RequestMapping(value = APP_TRANSACTIONSETTING_USERBANKLIST, method = RequestMethod.GET, produces = JsonEncode)
    @ResponseBody
    public String getUserBankList(String loginToken){
        JSONObject jsonObject = new JSONObject();
        try {
            if (!this.realTimeData.isAppLogin(loginToken, true)) {
                return ApiConstant.getNoLoginError();
            }

            Fuser user = this.realTimeData.getAppFuser(loginToken);
            if (user == null) {
                return ApiConstant.getRequestError("用户不存在");
            }

            //获取银行卡列表
            String filter = "where fuser.fid=" + user.getFid() + " and fbankType >0 order by fcreateTime desc ";
            List<FbankinfoWithdraw> fbankinfoWithdraws = this.frontUserService.findFbankinfoWithdrawByFuser(0, 0, filter, false);
            if (null != fbankinfoWithdraws && fbankinfoWithdraws.size() > 0) {
                for (FbankinfoWithdraw fbankinfoWithdraw : fbankinfoWithdraws) {
                    int l = fbankinfoWithdraw.getFbankNumber().length();
                    fbankinfoWithdraw.setFbankNumber(fbankinfoWithdraw.getFbankNumber().substring(l - 4, l));
                    fbankinfoWithdraw.setUrl(logoUrl + BankTypeEnum.getBankUrl(fbankinfoWithdraw.getFbankType()));
                    fbankinfoWithdraw.setBgColor(BankTypeEnum.getBgColor(fbankinfoWithdraw.getFbankType()));
                }
                ObjectMapper mapper = new ObjectMapper();
                jsonObject.accumulate(LIST, mapper.writeValueAsString(fbankinfoWithdraws));
            } else {
                jsonObject.accumulate(LIST, null);
            }
        }catch (Exception e){
            LOG.e(this, "获取银行卡列表失败", e);
            return ApiConstant.getRequestError();
        }
        return ApiConstant.getSuccessResp(jsonObject);
    }

    /**
     * 获取用户支付宝列表
     * @param loginToken
     * @return
     */
    @RequestMapping(value = APP_TRANSACTIONSETTING_USERALIPAYLIST, method = RequestMethod.GET, produces = JsonEncode)
    @ResponseBody
    public String getUserAlipayList(String loginToken){
        JSONObject jsonObject = new JSONObject();
        try {
            if (!this.realTimeData.isAppLogin(loginToken, true)) {
                return ApiConstant.getNoLoginError();
            }

            Fuser user = this.realTimeData.getAppFuser(loginToken);
            if (user == null) {
                return ApiConstant.getRequestError("用户不存在");
            }

            //获支付宝列表
            String filter = "where fuser.fid=" + user.getFid() + " and fstatus = 1 order by fcreateTime desc ";
            List<FalipayBind> list = this.falipayBindService.list(0, 0, filter, false);
            ObjectMapper mapper = new ObjectMapper();
            jsonObject.accumulate(LIST, mapper.writeValueAsString(list));
        }catch (Exception e){
            LOG.e(this, "获取支付宝列表失败", e);
            return ApiConstant.getRequestError();
        }
        return ApiConstant.getSuccessResp(jsonObject);
    }

    /**
     * 获取支付宝信息
     * @param loginToken    token
     * @param AlipayId      支付宝id
     * @return
     */
    @RequestMapping(value = APP_TRANSACTIONSETTING_ALIPAYINFO, method = RequestMethod.GET, produces = JsonEncode)
    @ResponseBody
    public String getAlipayInfo(String loginToken, Integer AlipayId){
        JSONObject jsonObject = new JSONObject();
        try {
            if (!checkParam(loginToken, AlipayId)) {
                LOG.w(this, "参数错误");
                return ApiConstant.getRequestError("操作失败");
            }
            if (!this.realTimeData.isAppLogin(loginToken, true)) {
                return ApiConstant.getNoLoginError();
            }

            Fuser user = this.realTimeData.getAppFuser(loginToken);
            if (user == null) {
                return ApiConstant.getRequestError("用户不存在");
            }
            if(null == AlipayId){
                LOG.w(this, "支付宝id参数为空");
                return ApiConstant.getRequestError();
            }

            FalipayBind byId = falipayBindService.findById(AlipayId);
            ObjectMapper mapper = new ObjectMapper();
            jsonObject.accumulate("AlipayInfo", mapper.writeValueAsString(byId));

        }catch (Exception e){
            LOG.e(this, "获取支付宝信息失败", e);
            return ApiConstant.getRequestError();
        }
        return ApiConstant.getSuccessResp(jsonObject);
    }

    /**
     * 删除支付宝（删除银行卡类型18）
     * @param req
     * @return
     */
    @RequestMapping(value = APP_TRANSACTIONSETTING_DELETEALIPAY, method = RequestMethod.POST, produces = JsonEncode)
    @ResponseBody
    public String deleteAlipay(@RequestBody TransactionSettingReq req) {
        JSONObject jsonObject = new JSONObject();
        try {
            String loginToken = req.getLoginToken();
            Integer alipayId = req.getAlipayId();
            String verificationCode = req.getVerificationCode();
            if (!checkParam(loginToken, alipayId, verificationCode)) {
                LOG.w(this, "参数错误");
                return ApiConstant.getRequestError("操作失败");
            }
            //验证是否登录
            if (!this.realTimeData.isAppLogin(loginToken, true)) {
                return ApiConstant.getNoLoginError();
            }
            Fuser fuser = this.realTimeData.getAppFuser(loginToken);
            if (fuser == null) {
                return ApiConstant.getNoLoginError();
            }
            if (!fuser.isFisTelephoneBind()) {
                return ApiConstant.getRequestError("没有绑定手机号码");
            }

            String ip = getIpAddr(request);
//		int google_limit = this.frontValidateService.getLimitCount(ip, CountLimitTypeEnum.GOOGLE) ;
            int tel_limit = this.frontValidateService.getLimitCount(ip, CountLimitTypeEnum.TELEPHONE);

            if (tel_limit <= 0) {
                return ApiConstant.getRequestError("此ip操作频繁，请2小时后再试!");
            } else {

                if (!validateMessageCode(fuser, fuser.getFareaCode(), fuser.getFtelephone(), MessageTypeEnum.BANGDING_DELALIPAY, verificationCode)) {
                    //手機驗證錯誤
                    this.frontValidateService.updateLimitCount(ip, CountLimitTypeEnum.TELEPHONE);
                    return ApiConstant.getRequestError("手机验证码错误，您还有" + tel_limit + "次机会");
                } else {
                    this.frontValidateService.deleteCountLimite(ip, CountLimitTypeEnum.TELEPHONE);
                    this.validateMap.removeMessageMap(fuser.getFid()+"_"+ MessageTypeEnum.BANGDING_DELALIPAY);
                }
            }

            //成功
            FalipayBind byId = falipayBindService.findById(alipayId);
            this.falipayBindService.delete(byId);
        } catch (Exception e) {
            LOG.e(this, "删除支付宝", e);
            return ApiConstant.getUnknownError(e);
        }
        return ApiConstant.getSuccessResp();
    }

    /**
     * 获取虚拟币列表
     * @return
     */
    @RequestMapping(value = APP_TRANSACTIONSETTING_GETVIRTUALLIST, method = RequestMethod.GET, produces = JsonEncode)
    @ResponseBody
    public String getVirtualList(){
        JSONObject jsonObject = new JSONObject();
        try {
            List<Ftrademapping> activetradeMappingList = (List<Ftrademapping>) ConstantMap.get("activetradeMapping");
            List<LogoExtendsIdAndName> list = null;
            LogoExtendsIdAndName idAndName = null;
            if(null != activetradeMappingList && activetradeMappingList.size() > 0){
                list = new ArrayList<LogoExtendsIdAndName>();
                for(Ftrademapping ftrademapping : activetradeMappingList){
                    idAndName = new LogoExtendsIdAndName();
                    idAndName.setId(ftrademapping.getFvirtualcointypeByFvirtualcointype2().getFid());
                    idAndName.setName(ftrademapping.getFvirtualcointypeByFvirtualcointype2().getfShortName());
                    idAndName.setLogo(ftrademapping.getFvirtualcointypeByFvirtualcointype2().getFurl());
                    list.add(idAndName);
                }
            }
            jsonObject.accumulate(LIST, list);
        }catch (Exception e){
            LOG.e(this, "获取虚拟币地址列表失败", e);
            return ApiConstant.getRequestError();
        }
        return ApiConstant.getSuccessResp(jsonObject);
    }

    /**
     * 添加虚拟币地址 （MessageTypeEnum 8）
     * @param req
     * @return
     */
    @RequestMapping(value = APP_TRANSACTIONSETTING_SAVEVIRTUALADDRESS, method = RequestMethod.POST, produces = JsonEncode)
    @ResponseBody
    public String saveVirtualAddress(@RequestBody TransactionSettingReq req) {
        JSONObject jsonObject = new JSONObject();
        Fuser fuser = null;
        try {
            String loginToken = req.getLoginToken();
            Integer symbol = req.getSymbol();
            String verificationCode = req.getVerificationCode();
            String remark = req.getRemark();
            String virtualAddress = req.getVirtualAddress();
            if (!checkParam(loginToken, symbol, verificationCode, virtualAddress)) {
                LOG.w(this, "参数错误");
                return ApiConstant.getRequestError("操作失败");
            }
            if (!this.realTimeData.isAppLogin(loginToken, true)) {
                return ApiConstant.getNoLoginError();
            }

            fuser = this.realTimeData.getAppFuser(loginToken);
            if (fuser == null) {
                return ApiConstant.getRequestError("用户不存在");
            }
            if (!fuser.getFgoogleBind() && !fuser.isFisTelephoneBind()) {
                return ApiConstant.getRequestError("请先绑定GOOGLE验证或手机号码");
            }

            if (fuser.getFtradePassword() == null || fuser.getFtradePassword().trim().length() == 0) {
                return ApiConstant.getRequestError("请先设置交易密码");
            }

            if (remark.length() > 100) {
                return ApiConstant.getRequestError("交易密码不正确");
            }

            Fvirtualcointype fvirtualcointype = this.frontVirtualCoinService.findFvirtualCoinById(symbol);
            if (fvirtualcointype == null
                    || fvirtualcointype.getFstatus() == VirtualCoinTypeStatusEnum.Abnormal
                    || !fvirtualcointype.isFIsWithDraw()) {
                return ApiConstant.getRequestError("该币种不存在");
            }

            List<Fvirtualaddress> list = this.frontVirtualCoinService.findFvirtualaddress(fvirtualcointype, virtualAddress);
            if (list != null && list.size() > 0) {
                return ApiConstant.getRequestError("提现地址不能是51平台的地址");
            }

            String ip = getIpAddr(request);
            if (fuser.isFisTelephoneBind()) {
                int tel_limit = this.frontValidateService.getLimitCount(ip, CountLimitTypeEnum.TELEPHONE);
                if (tel_limit <= 0) {
                    return ApiConstant.getRequestError("此ip操作频繁，请2小时后再试!");
                } else {

                    if (!validateMessageCode(fuser, fuser.getFareaCode(), fuser.getFtelephone(), MessageTypeEnum.VIRTUAL_WITHDRAW_ACCOUNT, verificationCode)) {
                        //手機驗證錯誤
                        this.frontValidateService.updateLimitCount(ip, CountLimitTypeEnum.TELEPHONE);
                        return ApiConstant.getRequestError("手机验证码错误，您还有" + tel_limit + "次机会");
                    } else {
                        this.frontValidateService.deleteCountLimite(ip, CountLimitTypeEnum.TELEPHONE);
                        this.validateMap.removeMessageMap(fuser.getFid()+"_"+ MessageTypeEnum.VIRTUAL_WITHDRAW_ACCOUNT);
                    }
                }
            }

            //判断同一同用户下提现地址是否存在，如果存在，不让添加
            if(frontVirtualCoinService.isExistsFaddress(virtualAddress, fuser.getFid() )){
                return ApiConstant.getRequestError("不能重复添加相同的提现地址");
            }


            if (!CoinType.ANS.equals(fvirtualcointype.getfShortName().toUpperCase())){
                // 钱包校验地址是否合法
                BTCMessage btcMsg = new BTCMessage();
                btcMsg.setACCESS_KEY(fvirtualcointype.getFaccess_key());
                btcMsg.setSECRET_KEY(fvirtualcointype.getFsecrt_key());
                btcMsg.setIP(fvirtualcointype.getFip());
                btcMsg.setPORT(fvirtualcointype.getFport());
                btcMsg.setCOIN_TYPE(fvirtualcointype.getfShortName().toUpperCase());
                try{
                    Boolean flag;
                    if (fvirtualcointype.isFisEth()){
                        ETHUtils ethUtils = new ETHUtils(btcMsg);
                        flag = ethUtils.validateaddress(virtualAddress);
                        // eth
                    } else {
                        // btc ltc zec
                        BTCUtils btcUtils = new BTCUtils(btcMsg);
                        flag = btcUtils.validateaddress(virtualAddress);
                    }
                    if (!flag){
                        return ApiConstant.getRequestError("提现地址非法");
                    }
                } catch (Exception ex){
                    LOG.e(this,ex.getMessage(),ex);
                    return ApiConstant.getRequestError("交易提现地址失败,请联系客服");
                }
            }

            FvirtualaddressWithdraw fvirtualaddressWithdraw = new FvirtualaddressWithdraw();
            fvirtualaddressWithdraw.setFadderess(virtualAddress);
            fvirtualaddressWithdraw.setFcreateTime(Utils.getTimestamp());
            fvirtualaddressWithdraw.setFremark(remark);
            fvirtualaddressWithdraw.setFuser(fuser);
            fvirtualaddressWithdraw.setFvirtualcointype(fvirtualcointype);
            this.frontVirtualCoinService.updateFvirtualaddressWithdraw(fvirtualaddressWithdraw);

        } catch (Exception e) {
            LOG.e(this, "添加虚拟币地址失败", e);
            return ApiConstant.getRequestError("添加失败");
        } finally {
            this.validateMap.removeMessageMap(fuser.getFid() + "_" + MessageTypeEnum.VIRTUAL_WITHDRAW_ACCOUNT);
        }
        return ApiConstant.getSuccessResp();
    }

    /**
     * 获取虚拟币地址列表
     * @param loginToken
     * @param symbol
     * @return
     */
    @RequestMapping(value = APP_TRANSACTIONSETTING_GETVIRTUALADDRESSLIST, method = RequestMethod.GET, produces = JsonEncode)
    @ResponseBody
    public String getVirtualAddressList(String loginToken, Integer symbol){
        JSONObject jsonObject = new JSONObject();
        try {
            if (!checkParam(loginToken, symbol)) {
                LOG.w(this, "参数错误");
                return ApiConstant.getRequestError("操作失败");
            }
            if (!this.realTimeData.isAppLogin(loginToken, true)) {
                return ApiConstant.getNoLoginError();
            }

            Fuser user = this.realTimeData.getAppFuser(loginToken);
            if (user == null) {
                return ApiConstant.getRequestError("用户不存在");
            }

            //获取虚拟币地址列表
            String filter = "where fuser.fid=" + user.getFid() + " and fvirtualcointype.fid = " + symbol.intValue() + " order by fcreateTime desc ";
            List<FvirtualaddressWithdraw> list = this.virtualaddressWithdrawService.list(0, 0, filter.toString(), false);
            ObjectMapper mapper = new ObjectMapper();
            jsonObject.accumulate(LIST, mapper.writeValueAsString(list));
        }catch (Exception e){
            LOG.e(this, "获取虚拟币地址列表失败", e);
            return ApiConstant.getRequestError();
        }
        return ApiConstant.getSuccessResp(jsonObject);
    }

    /**
     * 获取虚拟币地址详情
     * @param loginToken
     * @param virtualAddressId
     * @return
     */
    @RequestMapping(value = APP_TRANSACTIONSETTING_VIRTUALADDRESSINFO, method = RequestMethod.GET, produces = JsonEncode)
    @ResponseBody
    public String getVirtualAddressInfo(String loginToken, Integer virtualAddressId){
        JSONObject jsonObject = new JSONObject();
        try {
            if (!checkParam(loginToken, virtualAddressId)) {
                LOG.w(this, "参数错误");
                return ApiConstant.getRequestError("操作失败");
            }
            if (!this.realTimeData.isAppLogin(loginToken, true)) {
                return ApiConstant.getNoLoginError();
            }

            Fuser user = this.realTimeData.getAppFuser(loginToken);
            if (user == null) {
                return ApiConstant.getRequestError("用户不存在");
            }
            FvirtualaddressWithdraw byId = this.virtualaddressWithdrawService.findById(virtualAddressId);
            ObjectMapper mapper = new ObjectMapper();
            jsonObject.accumulate("virtualAddressInfo", mapper.writeValueAsString(byId));
        }catch (Exception e){
            LOG.e(this, "获取虚拟币地址详情失败", e);
            return ApiConstant.getRequestError();
        }
        return ApiConstant.getSuccessResp(jsonObject);
    }

    /**
     * 删除虚拟币地址（删除虚拟币地址类型 8）
     * @param req
     * @return
     */
    @RequestMapping(value = APP_TRANSACTIONSETTING_DELETEVIRTUALADDRESS, method = RequestMethod.POST, produces = JsonEncode)
    @ResponseBody
    public String deleteVirtualAddress(@RequestBody TransactionSettingReq req) {
        JSONObject jsonObject = new JSONObject();
        try {
            String loginToken = req.getLoginToken();
            String verificationCode = req.getVerificationCode();
            Integer virtualAddressId = req.getVirtualAddressId();
            if (!checkParam(loginToken, virtualAddressId, verificationCode)) {
                LOG.w(this, "参数错误");
                return ApiConstant.getRequestError("操作失败");
            }
            //验证是否登录
            if (!this.realTimeData.isAppLogin(loginToken, true)) {
                return ApiConstant.getNoLoginError();
            }
            Fuser fuser = this.realTimeData.getAppFuser(loginToken);
            if (fuser == null) {
                return ApiConstant.getNoLoginError();
            }
            if (!fuser.isFisTelephoneBind()) {
                return ApiConstant.getRequestError("没有绑定手机号码");
            }

            String ip = getIpAddr(request);
//		int google_limit = this.frontValidateService.getLimitCount(ip, CountLimitTypeEnum.GOOGLE) ;
            int tel_limit = this.frontValidateService.getLimitCount(ip, CountLimitTypeEnum.TELEPHONE);

            if (tel_limit <= 0) {
                return ApiConstant.getRequestError("此ip操作频繁，请2小时后再试!");
            } else {

                if (!validateMessageCode(fuser, fuser.getFareaCode(), fuser.getFtelephone(), MessageTypeEnum.VIRTUAL_WITHDRAW_ACCOUNT, verificationCode)) {
                    //手機驗證錯誤
                    this.frontValidateService.updateLimitCount(ip, CountLimitTypeEnum.TELEPHONE);
                    return ApiConstant.getRequestError("手机验证码错误，您还有" + tel_limit + "次机会");
                } else {
                    this.frontValidateService.deleteCountLimite(ip, CountLimitTypeEnum.TELEPHONE);
                    this.validateMap.removeMessageMap(fuser.getFid()+"_"+ MessageTypeEnum.VIRTUAL_WITHDRAW_ACCOUNT);
                }
            }
            this.virtualaddressWithdrawService.deleteObj(virtualAddressId);
        } catch (Exception e) {
            LOG.e(this, "删除支付宝", e);
            return ApiConstant.getUnknownError(e);
        }
        return ApiConstant.getSuccessResp();
    }

}
