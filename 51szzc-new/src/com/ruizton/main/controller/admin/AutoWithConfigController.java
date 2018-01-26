package com.ruizton.main.controller.admin;

import com.ruizton.main.controller.BaseController;
import com.ruizton.main.log.LOG;
import com.ruizton.main.model.Fvirtualcointype;
import com.ruizton.main.model.vo.AutoWithConfigVo;
import com.ruizton.util.Constant;
import com.ruizton.util.Utils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统自动提现(币)配置
 * Created by luwei on 17-5-8.
 */
@Controller
public class AutoWithConfigController extends BaseController {

    @RequestMapping("/ssadmin/autowithconfig")
    public ModelAndView autowithconfig(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("ssadmin/autoWithConfig");
        List<Fvirtualcointype> allcoins=frontVirtualCoinService.findAllFvirtualCoinType();
        List<AutoWithConfigVo> list = new ArrayList<>();
        if(allcoins != null && allcoins.size() > 0 ) {
            for(Fvirtualcointype vo : allcoins) {
                if(vo.getFtype() == 0) {
                    continue;
                }
                AutoWithConfigVo resp = new AutoWithConfigVo(vo.getFid(), vo.getfShortName());
                resp.setAuto_day_count(vo.getAuto_day_count().toString());
                resp.setAuto_day_limit(Utils.decimal2Str(vo.getAuto_day_limit()));
                resp.setAuto_single_limit(Utils.decimal2Str(vo.getAuto_single_limit()));
                list.add(resp);
            }
        }
        modelAndView.addObject("allcoins",list);

        return modelAndView;
    }

    //修改系统自动提现(币)配置
    @RequestMapping(value = "/ssadmin/updateautowithconfig", produces = {JsonEncode})
    @ResponseBody
    public String updateAutoWithConfig(String jsonStr ){
        JSONObject jsonObject = new JSONObject();
        if(StringUtils.isBlank(jsonStr)) {
            jsonObject.accumulate("message", "参数不能为空");
            jsonObject.accumulate("statusCode", 300);
            return jsonObject.toString();
        }

        try {
            JSONArray jsonArray = JSONArray.fromObject(jsonStr);
            if(StringUtils.isBlank(jsonStr)) {
                jsonObject.accumulate("message", "参数不能为空");
                jsonObject.accumulate("statusCode", 300);
                return jsonObject.toString();
            }


            List<AutoWithConfigVo> list = new ArrayList<>();

            for (int i = 0; i < jsonArray.size(); i++) {
                AutoWithConfigVo vo = (AutoWithConfigVo) JSONObject.toBean(jsonArray.getJSONObject(i), AutoWithConfigVo.class);
                //给默认值
                if(StringUtils.isBlank(vo.getAuto_day_count())) {
                    vo.setAuto_day_count("0");
                }
                if(StringUtils.isBlank(vo.getAuto_day_limit())) {
                    vo.setAuto_day_limit("0");
                }
                if(StringUtils.isBlank(vo.getAuto_single_limit())) {
                    vo.setAuto_single_limit("0");
                }

                boolean day_count_compare = vo.getAuto_day_count().matches(Constant.num2Reg);
                boolean single_limit_compare = vo.getAuto_single_limit().matches(Constant.num1Reg);
                boolean day_limit_compare = vo.getAuto_day_limit().matches(Constant.num1Reg);
                if(!day_count_compare || !single_limit_compare || !day_limit_compare) {
                    LOG.i(this, "数据格式错误，day_count_compare=" + day_count_compare + ",single_limit_compare=" + single_limit_compare + ",day_limit_compare=" + day_limit_compare);
                    jsonObject.accumulate("message", "数据错误，请检查");
                    jsonObject.accumulate("statusCode", 300);
                    return jsonObject.toString();
                }
                list.add(vo);
            }
            int result = this.frontVirtualCoinService.updateAutoWithConfig(list);
            if(result == 1) {
                jsonObject.accumulate("message", "更新成功");
                jsonObject.accumulate("statusCode", 200);
            }

        }catch (Exception e) {
            LOG.i(this, "异常："+e.getMessage());
            jsonObject.accumulate("message", "系统异常");
            jsonObject.accumulate("statusCode", 300);
        }

        return jsonObject.toString();

    }

}
