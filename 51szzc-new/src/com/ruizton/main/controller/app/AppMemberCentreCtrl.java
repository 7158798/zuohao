package com.ruizton.main.controller.app;

import com.ruizton.main.Enum.IntegralTypeEnum;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.controller.front.response.FintegralActivityResp;
import com.ruizton.main.controller.front.response.UserGradeResp;
import com.ruizton.main.log.LOG;
import com.ruizton.main.model.Fuser;
import com.ruizton.main.model.integral.Fintegralactivity;
import com.ruizton.main.model.integral.Fusergrade;
import com.ruizton.util.OSSPostObject;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zygong on 17-3-28.
 */
@Controller
public class AppMemberCentreCtrl extends BaseController implements ApiConstants {

    private static final String pictureUrl = OSSPostObject.URL + "//static/front/images/user/point_%s_icon@2X.png";

    /**
     * 获取会员中心数据
     * @param loginToken
     * @return
     */
    @RequestMapping(value = APP_MEMBERCENTRE_DATA, method = RequestMethod.GET, produces = JsonEncode)
    @ResponseBody
    public String getData(String loginToken){
        JSONObject jsonObject = new JSONObject();
        JSONObject data = new JSONObject();
        try {
            if (!this.realTimeData.isAppLogin(loginToken, true)) {
                return ApiConstant.getNoLoginError();
            }

            Fuser user = this.realTimeData.getAppFuser(loginToken);
            if (user == null) {
                return ApiConstant.getRequestError("用户不存在");
            }

            //等级特权说明
            List<Fusergrade> fusergrades = this.userGradeService.findAll();
            List<UserGradeResp> usergradeList = new ArrayList<>();
            int difference = 0;
            for (Fusergrade fusergrade : fusergrades) {
                UserGradeResp grade = new UserGradeResp();
                grade.setLevel(fusergrade.getFtitle());
                grade.setIntegral(fusergrade.getFneedintegral().toString());

                //
                if (fusergrade.getFoutfee().doubleValue() == 0.0) {
                    grade.setOutFee("免费");
                } else {
                    grade.setOutFee(fusergrade.getFoutfee().multiply(new BigDecimal(100)).setScale(2).toString());
                }
                //人民币充值
                if (fusergrade.getFcapitalinfee().doubleValue() == 0.0) {
                    grade.setInCNY("免费");
                } else {
                    grade.setInCNY(fusergrade.getFcapitalinfee().multiply(new BigDecimal(100)).setScale(2).toString());
                }

                //比特币充值
                if (fusergrade.getVirtualinfee().doubleValue() == 0.0) {
                    grade.setInBtc("免费");
                } else {
                    grade.setInBtc(fusergrade.getVirtualinfee().multiply(new BigDecimal(100)).setScale(2).toString());
                }

                //交易手续费
                if (fusergrade.getTradefee().doubleValue() == 0.0) {
                    grade.setTrade("免费");
                } else {
                    grade.setTrade(fusergrade.getTradefee().multiply(new BigDecimal(100)).setScale(2).toString());
                }
                usergradeList.add(grade);

                if (difference == 0 && user.getFscore().getFlevel() + 1 == fusergrade.getFid()) {
                    difference = fusergrade.getFneedintegral() - user.getIntegral();
                }
            }
            data.accumulate("gradeList", usergradeList);

            //积分规则
            List<Fintegralactivity> activityList = this.fintegralactivityService.findOpenActivity();

            List<FintegralActivityResp> respList = new ArrayList<>();
            for (Fintegralactivity fintegral : activityList) {
                FintegralActivityResp resp = new FintegralActivityResp();
                //String s = IntegralTypeEnum.getMap().get(1);
                resp.setIntegralType(IntegralTypeEnum.getMap().get(fintegral.getType()));
                resp.setIntegral(fintegral.getIntegral());
                resp.setRemark(fintegral.getContent());
                resp.setUrl(String.format(pictureUrl, IntegralTypeEnum.getUrlmap().get(fintegral.getType())));
                respList.add(resp);
            }
            data.accumulate("activityList", respList);

            // 用户当前积分
            data.accumulate("currentIntegral", user.getIntegral());
            jsonObject.accumulate("data", data);
        }catch (Exception e){
            LOG.e(this, "获取会员中心数据失败", e);
            return ApiConstant.getRequestError("获取会员中心数据失败");
        }

        return ApiConstant.getSuccessResp(jsonObject);
    }
}
