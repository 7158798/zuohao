package com.ruizton.main.controller.front;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.ruizton.main.Enum.AlipayConfig;
import com.ruizton.main.log.LOG;
import com.ruizton.util.JsonHelper;
import com.ruizton.util.alipayUtil.AlipayNotify;
import net.chrone.util.MyRSAUtils;
import net.chrone.util.SdkUtil;
import net.chrone.util.SignatureUtil;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ruizton.main.Enum.CapitalOperationInStatus;
import com.ruizton.main.model.Fcapitaloperation;
import com.ruizton.main.model.Fvirtualwallet;
import com.ruizton.main.service.admin.CapitaloperationService;
import com.ruizton.main.service.front.FrontUserService;
import com.ruizton.util.Utils;
import org.springframework.web.servlet.ModelAndView;

/**
 * 三方快捷支付
 * @author luwei
 * @Date 1/9/17 10:24 AM
 */
@Controller
public class QuickPayController {

    @Autowired
    private CapitaloperationService capitaloperationService ;
    @Autowired
    private FrontUserService frontUserService ;

    public static final Logger LOGGER = LoggerFactory.getLogger(QuickPayController.class);

    //支付结果回调通知
    @RequestMapping(value = "/quickpay/qrpay_result")
    @ResponseBody
    public String qrpay_result(HttpServletRequest request) throws Exception {
        String result_str = "{\"success\":\"false\"}";
        LOGGER.info("支付结果回调通知开始");
        //此处可考虑用@RequestBody接收，接收对象是一个json串
        //注：此回调比较特殊，contentType
       /* String key = "";
        String value = "";
        Iterator<String> it = request.getParameterMap().keySet().iterator();
        while (it.hasNext()) {
            key = it.next();
            value = ((Object[]) (request.getParameterMap().get(key)))[0].toString();
            LOGGER.info("key=" + key + "&value=" + value);
        }

        if (StringUtils.isBlank(key) && StringUtils.isBlank(value)) {
            LOGGER.info("QuickPayController qrpay_result响应参数为空");
            return result_str;
        }

        //注意=号
        String jsonStr = key + "=" + value;
        //空格替换成+号
        jsonStr = jsonStr.replaceAll(" ", "\\+");
        LOGGER.info("支付平台响应的报文：jsonStr:{}", jsonStr);
        //此处需要将参数json字符串转换成map，然后根据signature进行平台公钥验签 MyRSAUtils.verifySignature
        JSONObject jsonObject = JSON.parseObject(jsonStr);
        Map<String, String> paramMap = (Map) jsonObject;
        //取出签名
        String signature = paramMap.get("signature").toString();
        signature = signature.replaceAll(" ", "\\+");  //防止还有空格存在
        //从map中移除signature
        paramMap.remove("signature");
        //将剩余的参数进行验签
        String bigStr = SignatureUtil.hex(paramMap);
        boolean check = MyRSAUtils.verifySignature(SdkUtil.getStringValue("public_key"), signature, bigStr, MyRSAUtils.MD5_SIGN_ALGORITHM);
        LOGGER.info("回调函数，平台公钥验签，是否平台返回：check:{}", check);

        //具体业务代码

        //应该从请求参数中获取值，此处省略...
        if (check == true && paramMap != null && paramMap.size() > 0 && paramMap.get("paySt").toString().equals("2")) {
            int orderNo = Integer.parseInt(paramMap.get("orgOrderNo"));
            LOGGER.info("回调响应订单号orgOrderNo:{}", orderNo);
            Fcapitaloperation capitalOperation = this.capitaloperationService.findById(orderNo);

            if (capitalOperation == null) {
                LOGGER.info("根据订单号orderno:{}查询充值流水为空", orderNo);
                return result_str;
            }

            int status = capitalOperation.getFstatus();

            if (status != CapitalOperationInStatus.WaitForComing) {
                LOGGER.info("状态错误，只有等待到帐的订单才能修改状态，请检查是否重复操作，订单号：" + orderNo);
                return result_str;
            }

            if (status == CapitalOperationInStatus.WaitForComing) {
                Fvirtualwallet walletInfo = this.frontUserService.findWalletByUser(capitalOperation.getFuser().getFid());
                // 充值操作
                capitalOperation.setFstatus(CapitalOperationInStatus.Come);
                capitalOperation.setfLastUpdateTime(Utils.getTimestamp());
                // 钱包操作
                walletInfo.setFlastUpdateTime(Utils.getTimestamp());
                LOGGER.info("充值金额amount:{}", capitalOperation.getFamount());
                walletInfo.setFtotal(walletInfo.getFtotal() + capitalOperation.getFamount());
                try {
                    this.capitaloperationService.updateCapital(capitalOperation, walletInfo, true);
                    result_str = "{\"success\":\"true\"}";
                    return result_str;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
*/
        return result_str;
    }


    //电脑端集成，异步通知路径
    @RequestMapping(value = "/quickpay/alipay_notify")
    @ResponseBody
    public String alipay_notify (HttpServletRequest request) throws Exception{
        LOG.i(this, "支付宝支付，电脑端集成，异步通知接口开始");
        String result_str = "fail";
        Map<String, String> params = getParamByReq(request);
        if(params == null || params.size() == 0) {
            return result_str;
        }
        //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
        //商户订单号
        String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
        //支付宝交易号
        String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
        //交易状态
        String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
        LOG.i(this, "交易状态: " + trade_status + "，订单号：" + out_trade_no+"，支付宝订单号："+trade_no);

        //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//

        if(AlipayNotify.verify(params, AlipayConfig.partner, AlipayConfig.alipay_public_key)){//验证成功
            result_str = this.common_notify(params, trade_status);
            //////////////////////////////////////////////////////////////////////////////////////////
        }else{//验证失败
            result_str = "fail";
            LOG.i(this, "支付宝验证失败");
        }

        LOG.i(this, "支付宝支付，电脑端集成，异步通知接口结束");
        return result_str;
    }



    //App端集成，异步通知路径
    @RequestMapping(value = "/quickpay/app_alipay_notify")
    @ResponseBody
    public String app_alipay_notify (HttpServletRequest request) throws Exception{
        String result_str = "fail";
        LOG.i(this, "支付宝支付，App端集成，异步通知接口开始");
        Map<String, String> params = getParamByReq(request);
        if(params == null || params.size() == 0) {
            return result_str;
        }

        //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
        //商户订单号
        String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
        //支付宝交易号
        String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
        //交易状态
        String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
        LOG.i(this, "交易状态: " + trade_status + "，订单号：" + out_trade_no+"，支付宝订单号："+trade_no);

        //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//

        if(AlipayNotify.verify(params, AlipayConfig.partner, AlipayConfig.app_alipay_public_key)){//验证成功
            result_str = this.common_notify(params, trade_status);
            //////////////////////////////////////////////////////////////////////////////////////////
        }else{//验证失败
            result_str = "fail";
            LOG.i(this, "支付宝验证失败");
        }

        LOG.i(this, "支付宝支付，App端集成，异步通知接口结束");
        return result_str;
    }


    //异步回调通知，抽取出来的公共部分
    private String common_notify(Map<String,String> params, String trade_status) {
        String result_str = "fail";
        //////////////////////////////////////////////////////////////////////////////////////////
        //请在这里加上商户的业务逻辑程序代码
        int orderNo = Integer.parseInt(params.get("out_trade_no"));
        LOGGER.info("回调响应订单号orgOrderNo:{}", orderNo);
        Fcapitaloperation capitalOperation = this.capitaloperationService.findById(orderNo);

        if (capitalOperation == null) {
            LOGGER.info("根据订单号orderno:{}查询充值流水为空", orderNo);
            return result_str;
        }

        int status = capitalOperation.getFstatus();
        //——请根据您的业务逻辑来编写程序（以下代码仅作参考）——

        if(trade_status.equals("TRADE_FINISHED")){
            //判断该笔订单是否在商户网站中已经做过处理
            //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
            //请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
            //如果有做过处理，不执行商户的业务程序
            if (status != CapitalOperationInStatus.WaitForComing && StringUtils.isNotBlank(capitalOperation.getfPayee())) {
                LOGGER.info("交易结束，业务平台订单信息已修改，订单号：" + orderNo);
                return result_str;
            }
            //注意：
            //退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
        } else if (trade_status.equals("TRADE_SUCCESS")){

            if (status != CapitalOperationInStatus.WaitForComing) {
                LOGGER.info("状态错误，只有等待到帐的订单才能修改状态，请检查是否重复操作，订单号：" + orderNo);
                return result_str;
            }
            //获取充值流水的
            //判断该笔订单是否在商户网站中已经做过处理
            //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
            //请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
            //如果有做过处理，不执行商户的业务程序

            //注意：
            //付款完成后，支付宝系统发送该交易状态通知
        }

        if (status == CapitalOperationInStatus.WaitForComing) {
            //修改为人工审核，所以不直接操作钱包，而是将支付宝返回的信息写到备注字段当中，将将汇款人帐号写入到fPayee字段
            String remark = JsonHelper.obj2JsonStr(params);
            capitalOperation.setFremark(remark);
            capitalOperation.setfPayee(params.get("buyer_email"));
            this.capitaloperationService.updateObj(capitalOperation);
        }

        //——请根据您的业务逻辑来编写程序（以上代码仅作参考）——

        result_str = "success";	//请不要修改或删除
        return result_str;
    }


    //从请求中获取参数信息， 抽取公共
    private Map<String, String> getParamByReq(HttpServletRequest request) {

        Map<String,String> params = new HashMap<String,String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        return params;
    }


    //电脑端集成，页面跳转同步通知页面路径
    @RequestMapping(value = "/quickpay/alipay_result")
    public ModelAndView alipay_result (HttpServletRequest request) throws Exception{
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("front/account/rechargecny_return");
        LOG.i(this, "支付宝支付，电脑端集成，同步通知接口开始");

        Map<String, String> params = getParamByReq(request);
        //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
        //商户订单号
        String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
        //支付宝交易号
        String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
        //交易状态
        String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
        LOG.i(this, "交易状态: " + trade_status + "，订单号：" + out_trade_no+"，支付宝订单号："+trade_no);
        modelAndView.addObject("trade_status", trade_status);
        //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//

        //计算得出通知验证结果
        boolean verify_result = AlipayNotify.verify(params, AlipayConfig.partner, AlipayConfig.alipay_public_key);

        if(verify_result){//验证成功
            //////////////////////////////////////////////////////////////////////////////////////////
            //请在这里加上商户的业务逻辑程序代码
            int orderNo = Integer.parseInt(params.get("out_trade_no"));
            LOGGER.info("回调响应订单号orgOrderNo:{}", orderNo);
            Fcapitaloperation capitalOperation = this.capitaloperationService.findById(orderNo);

            if (capitalOperation == null) {
                LOGGER.info("根据订单号orderno:{}查询充值流水为空", orderNo);
                modelAndView.addObject("msg", "查询订单信息为空");
                modelAndView.addObject("code", 300);
                return modelAndView;
            }
            int status = capitalOperation.getFstatus();

            //——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
            if(trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")){
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //如果有做过处理，不执行商户的业务程序
                if (status != CapitalOperationInStatus.WaitForComing && StringUtils.isNotBlank(capitalOperation.getfPayee())) {
                    LOGGER.info("交易结束，业务平台订单信息已修改，订单号：" + orderNo);
                    modelAndView.addObject("msg", "交易成功");
                    modelAndView.addObject("code", 0);
                    return modelAndView;
                }

                if (status == CapitalOperationInStatus.WaitForComing) {
                    //修改为人工审核，所以不直接操作钱包，而是将支付宝返回的信息写到备注字段当中，将将汇款人帐号写入到fPayee字段
                    String remark = JsonHelper.obj2JsonStr(params);
                    capitalOperation.setFremark(remark);
                    String fpayee = params.get("buyer_email");  //电脑端集成有数据，app没有
                    if (StringUtils.isBlank(fpayee)) {
                        fpayee = params.get("buyer_logon_id");  //app有，电脑端没有
                    }
                    if (StringUtils.isBlank(fpayee)) {
                        fpayee = "未知";
                    }
                    capitalOperation.setfPayee(fpayee);
                    this.capitaloperationService.updateObj(capitalOperation);
                }

            }

            //该页面可做页面美工编辑
           LOG.i(this, "验证成功");
            //——请根据您的业务逻辑来编写程序（以上代码仅作参考）——

            //////////////////////////////////////////////////////////////////////////////////////////
        }else{
            //该页面可做页面美工编辑
            LOG.i(this, "验证失败");
        }


        LOG.i(this, "支付宝支付，电脑端集成，同步通知接口结束");
        return modelAndView;

    }

}