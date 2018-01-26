package com.ruizton.main.controller.app;

import com.ruizton.main.controller.BaseController;
import com.ruizton.main.controller.app.request.AppPushReq;
import com.ruizton.main.log.LOG;
import com.ruizton.main.model.Fapppush;
import com.ruizton.main.model.Fuser;
import com.ruizton.main.model.vo.FapppushVo;
import com.ruizton.main.model.vo.MessagePushDetailVo;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 价格提示
 * Created by zygong on 17-4-12.
 */
@Controller
public class AppMessageSendCtrl extends BaseController implements ApiConstants{

    // 推送集合
    /*public static Map<Integer, TreeSet<Fapppush>> pushMap1 = new HashMap<Integer, TreeSet<Fapppush>>();

    // 短信集合
    private Map<Integer, Fapppush> smsMap = new HashMap<Integer, Fapppush>();

    public static Comparator<Fapppush> comparator = new Comparator<Fapppush>() {

        @Override
        public int compare(Fapppush o1, Fapppush o2) {
            if(o1.getFlowprice() > o2.getFlowprice()){
                return 1;
            }else {
                return -1;
            }
        }
    };*/

    /**
     * 获取设置详情
     * @param loginToken    token
     * @return
     */
    @RequestMapping(value = APP_SENDMESSAGE_GETDETAIL, method = RequestMethod.GET, produces = JsonEncode)
    @ResponseBody
    public String getDetail(String loginToken) {
        JSONObject jsonObject = new JSONObject();
        FapppushVo vo = null;
        List<FapppushVo> voList = null;
        Map<Integer, Fapppush> map = null;
        boolean isSms = false;
        try {
            if (StringUtils.isBlank(loginToken)) {
                LOG.w(this, "参数错误");
                return ApiConstant.getRequestError();
            }
            if (!this.realTimeData.isAppLogin(loginToken, true)) {
                return ApiConstant.getNoLoginError();
            }
            Fuser fuser = this.realTimeData.getAppFuser(loginToken);
            if (fuser == null) {
                return ApiConstant.getNoLoginError();
            }

            // 获取推送设置
            MessagePushDetailVo detail = this.fapppushService.findDetail(fuser.getFid());

            jsonObject.accumulate(LIST, detail.getFapppushVoList());
            jsonObject.accumulate("isSms", detail.isSms());

        } catch (Exception e) {
            LOG.e(this, "价格提示发送短信", e);
            return ApiConstant.getUnknownError(e);
        }
        return ApiConstant.getSuccessResp(jsonObject);
    }

    /**
     * 价格提示发送短信
     * @param appPush   价格区间推送
     * @return
     */
    @RequestMapping(value = APP_SENDMESSAGE_SENDSMS, method = RequestMethod.POST, produces = JsonEncode)
    @ResponseBody
    public String sendSMS(@RequestBody AppPushReq appPush) {
        try {
            if(!checkParam(appPush.getLoginToken(), appPush.getSendType())){
                LOG.w(this, "参数错误");
                return ApiConstant.getRequestError();
            }
            String loginToken = appPush.getLoginToken();
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
            this.fapppushService.updateSendSMS(fuser, appPush);

        } catch (Exception e) {
            LOG.e(this, "价格提示发送短信", e);
            return ApiConstant.getUnknownError(e);
        }
        return ApiConstant.getSuccessResp();
    }

    /**
     * 价格提示推送
     * @param appPush
     * @return
     */
    @RequestMapping(value = APP_SENDMESSAGE_PUSHMESSAGE, method = RequestMethod.POST, produces = JsonEncode)
    @ResponseBody
    public String pushMessage(@RequestBody AppPushReq appPush) {
        try {
            if(!checkParam(appPush.getLoginToken(), appPush.getPriceRange(), appPush.getSendType(), appPush.getPhoneCode(), appPush.getPhoneType())){
                LOG.w(this, "参数错误");
                return ApiConstant.getRequestError();
            }
            String loginToken = appPush.getLoginToken();
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

            this.fapppushService.updatePushMessage(fuser, appPush);

        } catch (Exception e) {
            LOG.e(this, "价格提示推送", e);
            return ApiConstant.getUnknownError(e);
        }
        return ApiConstant.getSuccessResp();
    }


    /**
     * 价格提示发送短信
     * @param appPush   价格区间推送
     * @return
     */
    /*@RequestMapping(value = APP_SENDMESSAGE_SENDSMS, method = RequestMethod.POST, produces = JsonEncode)
    @ResponseBody
    public String sendSMS(@RequestBody AppPushReq appPush) {
        Fapppush fapppush = null;
        TreeSet<Fapppush> value = null;
        boolean isFind = false;
        try {
            if(!checkParam(appPush.getLoginToken(), appPush.getPriceRangeList(), appPush.getSendType())){
                LOG.w(this, "参数错误");
                return ApiConstant.getRequestError();
            }
            String loginToken = appPush.getLoginToken();
            Integer sendType = appPush.getSendType();
            List<PriceRange> priceRangeList = appPush.getPriceRangeList();
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
            Map<Integer, TreeSet<Fapppush>> pushMap = ConstantMap.pushMap;
            for(Map.Entry<Integer, TreeSet<Fapppush>> it : pushMap.entrySet()){
                value = new TreeSet<Fapppush>(this.comparator);
                value = it.getValue();
                for(Iterator iter = value.descendingIterator(); iter.hasNext();){
                    fapppush = (Fapppush)iter.next();
                    if(fuser.getFid() == fapppush.getFuser()){
                            // 如果推送和短信都不需要发送则删除对应的数据
                            if(fapppush.getFispush() == PushStatusEnum.NO_SEND.getKey() && sendType == PushStatusEnum.NO_SEND.getKey()){
                                iter.remove();
                                this.fapppushService.deleteParam(fapppush);
                            }else {
                                fapppush.setFissms(sendType);
                                for (PriceRange priceRange : priceRangeList) {
                                    if (it.getKey() == priceRange.getId()) {
                                        fapppush.setFhighprice(priceRange.getHighPrice());
                                        fapppush.setFlowprice(priceRange.getLowPrice());
                                    }
                                }
                                this.fapppushService.update(fapppush);
                                iter.remove();
                            }
                            isFind = true;
                            break;
                    }

                }
                if(!isFind){
                    fapppush = new Fapppush();
                    fapppush.setFissms(sendType);
                    fapppush.setFuser(fuser.getFid());
                    fapppush.setFcointype(it.getKey());
                    for(PriceRange priceRange : priceRangeList){
                        if(it.getKey() == priceRange.getId()){
                            fapppush.setFhighprice(priceRange.getHighPrice());
                            fapppush.setFlowprice(priceRange.getLowPrice());
                        }
                    }
                    this.fapppushService.saveObj(fapppush);
                }
                value.add(fapppush);

                pushMap.put(it.getKey(), value);
            }

        } catch (Exception e) {
            LOG.e(this, "价格提示发送短信", e);
            return ApiConstant.getUnknownError(e);
        }
        return ApiConstant.getSuccessResp();
    }

    *//**
     * 价格提示推送
     * @param appPush   价格区间推送
     * @return
     *//*
    @RequestMapping(value = APP_SENDMESSAGE_PUSHMESSAGE, method = RequestMethod.POST, produces = JsonEncode)
    @ResponseBody
    public String pushMessage(@RequestBody AppPushReq appPush) {
        Fapppush fapppush = null;
        TreeSet<Fapppush> value = null;
        boolean isFind = false;
        try {
            if(!checkParam(appPush.getLoginToken(), appPush.getPriceRange(), appPush.getSendType(), appPush.getPhoneCode(), appPush.getPhoneType())){
                LOG.w(this, "参数错误");
                return ApiConstant.getRequestError();
            }
            String loginToken = appPush.getLoginToken();
            Integer sendType = appPush.getSendType();
            PriceRange priceRange = appPush.getPriceRange();
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
            Map<Integer, TreeSet<Fapppush>> pushMap = ConstantMap.pushMap;
            value = new TreeSet<Fapppush>(this.comparator);
            value = pushMap.get(priceRange.getId());
            for(Iterator iter = value.descendingIterator(); iter.hasNext();){
                fapppush = (Fapppush)iter.next();
                if(fuser.getFid() == fapppush.getFuser()){
                    // 如果推送和短信都不需要发送则删除对应的数据
                    if(fapppush.getFissms() == PushStatusEnum.NO_SEND.getKey() && sendType == PushStatusEnum.NO_SEND.getKey()){
                        iter.remove();
                        this.fapppushService.deleteParam(fapppush);
                    }else {
                        fapppush.setFispush(sendType);
                        fapppush.setFlowprice(priceRange.getLowPrice());
                        fapppush.setFhighprice(priceRange.getHighPrice());
                        this.fapppushService.update(fapppush);
                        iter.remove();
                    }
                    isFind = true;
                    break;
                }

            }
            if(!isFind){
                fapppush = new Fapppush();
                fapppush.setFuser(fuser.getFid());
                fapppush.setFissms(sendType);
                fapppush.setFispush(fuser.getFid());
                fapppush.setFcointype(priceRange.getId());
                fapppush.setFhighprice(priceRange.getHighPrice());
                fapppush.setFlowprice(priceRange.getLowPrice());
                fapppush.setFphonetype(appPush.getPhoneType());
                fapppush.setFphonecode(appPush.getPhoneCode());
                this.fapppushService.saveObj(fapppush);
            }
            value.add(fapppush);
            pushMap.put(priceRange.getId(), value);

        } catch (Exception e) {
            LOG.e(this, "价格提示推送", e);
            return ApiConstant.getUnknownError(e);
        }
        return ApiConstant.getSuccessResp();
    }*/
}
