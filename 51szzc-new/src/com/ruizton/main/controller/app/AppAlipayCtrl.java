package com.ruizton.main.controller.app;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.opensymphony.oscache.util.StringUtil;
import com.ruizton.main.Enum.AlipayConfig;
import com.ruizton.main.Enum.SystemBankTypeEnum;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.controller.app.request.AlipayReq;
import com.ruizton.main.log.LOG;
import com.ruizton.main.model.Fcapitaloperation;
import com.ruizton.main.model.Systembankinfo;
import com.ruizton.util.Constant;
import com.ruizton.util.ConstantKeys;
import com.ruizton.util.JsonHelper;
import com.ruizton.util.Utils;
import com.ruizton.util.alipayUtil.AlipaySubmit;
import com.ruizton.util.alipayUtil.appUtil.OrderInfoUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * app 支付宝支付
 * Created by luwei on 17-4-1.
 */
@Controller
public class AppAlipayCtrl extends BaseController implements ApiConstants {

    /**
     * 支付宝充值
     * @return
     */
    @ResponseBody
    @RequestMapping(value = APP_ALIPAY_ORDER, method = RequestMethod.POST, produces = JsonEncode)
    public String alipayorder(@RequestBody AlipayReq req) {
        JSONObject jsonObject = new JSONObject();

        try {

            if(req == null || StringUtils.isBlank(req.getTradeid()) || StringUtils.isBlank(req.getLoginToken())) {
                jsonObject.accumulate(CODE, -1);
				jsonObject.accumulate(MSG, "参数错误");
                return jsonObject.toString();
            }

            Fcapitaloperation fcapitaloperation = frontAccountService.findCapitalOperationById(Integer.valueOf(req.getTradeid()));
            if(fcapitaloperation == null) {
                jsonObject.accumulate(CODE, -1);
                jsonObject.accumulate(MSG, "系统异常");
                return jsonObject.toString();
            }

            //查询系统支付宝帐号
            List<Systembankinfo> alipayList = frontBankInfoService.findAllSystemBankInfo(SystemBankTypeEnum.ALIPAY.getCode());
            if (alipayList == null || alipayList.size() == 0) {
                //系统没有支付宝帐号
                jsonObject.accumulate(CODE, -1);
				jsonObject.accumulate(MSG, "网络异常");
                return jsonObject.toString();
            }
            //调用系统支付宝帐号的appid
            Systembankinfo sysAlipay = alipayList.get(0);
            AlipayConfig.app_id = sysAlipay.getFappId();

            String amount = Utils.decimalFormat(fcapitaloperation.getFamount(), 2);

            Map<String, String> params = OrderInfoUtil.buildOrderParamMap(AlipayConfig.app_id, req.getTradeid(), amount);
            String orderParam = OrderInfoUtil.buildOrderParam(params);  //将参数用&=拼接成字符串
            //对参数字符串进行签名
            String sign = OrderInfoUtil.getSign(params, AlipayConfig.private_key);
            //将参数字符串、签名拼接
            final String orderInfo = orderParam + "&" + sign;
            jsonObject.accumulate("orderInfo", orderInfo);
            jsonObject.accumulate(CODE, 0);
        } catch (Exception e) {
            jsonObject.accumulate(CODE, -1);
            jsonObject.accumulate(MSG, "系统异常");
            LOG.i(this, "支付宝支付异常" + e.getMessage());
        }

        return jsonObject.toString();
    }

    /**
     * 支付宝充值，返回h5网页
     * @return
     */
    @RequestMapping(value = APP_ALIPAY_H5_ORDER)
    public ModelAndView alipayh5page(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/app/alipay/alipay");

        //充值流水id
        String tradeId = request.getParameter("tradeId");
        if(StringUtils.isBlank(tradeId)) {
            modelAndView.addObject(CODE, -1);
            modelAndView.addObject("msg", "参数异常");
            return modelAndView;
        }

        Fcapitaloperation fcapitaloperation = frontAccountService.findCapitalOperationById(Integer.valueOf(tradeId));
        if(fcapitaloperation == null) {
            modelAndView.addObject(CODE, -1);
            modelAndView.addObject(MSG, "系统异常");
            return modelAndView;
        }

        try {
            //查询系统支付宝帐号
            List<Systembankinfo> alipayList = frontBankInfoService.findAllSystemBankInfo(SystemBankTypeEnum.ALIPAY.getCode());
            if (alipayList == null || alipayList.size() == 0) {
                //系统没有支付宝帐号
                modelAndView.addObject("code", -1);
                modelAndView.addObject("msg", "网络异常");
                return modelAndView;
            }
            Systembankinfo sysAlipay = alipayList.get(0);
            AlipayConfig.app_id = sysAlipay.getFappId();

            //调SDK信息封装
            AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.mapi_url, AlipayConfig.app_id, AlipayConfig.private_key,
                    AlipayConfig.format, AlipayConfig.input_charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);

            AlipayTradeWapPayRequest alipay_request = new AlipayTradeWapPayRequest();
            //封装请求支付信息
            AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
            model.setOutTradeNo(new Date().getTime()+"");
            model.setSubject("人民币充值(昊涌科技)");
            model.setTotalAmount("0.01");
            model.setBody(constantMap.getString(ConstantKeys.WEB_NAME));
            model.setTimeoutExpress(AlipayConfig.it_b_pay);
            model.setProductCode(AlipayConfig.product_code);
            alipay_request.setBizModel(model);
            // 设置异步通知地址
            alipay_request.setNotifyUrl(AlipayConfig.notify_url);
            // 设置同步地址
            alipay_request.setReturnUrl(AlipayConfig.return_url);
            // form表单生产
            String form = "";
            try {
                // 调用SDK生成表单
                form = alipayClient.pageExecute(alipay_request).getBody();
                modelAndView.addObject("form", form);
                modelAndView.addObject(CODE, 0);
            } catch (AlipayApiException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        } catch (Exception e) {
            LOG.e(this, "支付宝充值异常", e);
        }

        return modelAndView;
    }

}
