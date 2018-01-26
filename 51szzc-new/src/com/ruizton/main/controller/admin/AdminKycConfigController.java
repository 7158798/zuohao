package com.ruizton.main.controller.admin;

import com.ruizton.main.controller.BaseController;
import com.ruizton.main.log.LOG;
import com.ruizton.main.model.Fvirtualcointype;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mibook3 on 17-5-5.
 */
@Controller
public class AdminKycConfigController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(AdminKycConfigController.class);



    @RequestMapping(value ="/ssadmin/kycconfig"   ,produces = {JsonEncode})
    public ModelAndView kycconfig(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("ssadmin/kycconfig");
        List<Fvirtualcointype> allcoins=frontVirtualCoinService.findAllFvirtualCoinType();
        modelAndView.addObject("allcoins",allcoins);
        return modelAndView;
    }


//ftype作用临时为coin的id
    @RequestMapping(value ="/ssadmin/kycconfig/save",produces = {JsonEncode})
    @ResponseBody
    public String  kycconfigsave(int type, String jsonStr ){
        JSONObject jsonObject = new JSONObject();
        if(StringUtils.isBlank(jsonStr)) {
            jsonObject.accumulate("message", "参数不能为空");
            jsonObject.accumulate("statusCode", 300);
              return jsonObject.toString();
        }


        try {
            JSONArray jsonArray = JSONArray.fromObject(jsonStr);

            String num1Reg = "^\\d*|\\d.{1}\\d{1,6}$";  //整数和小数
            String num2Reg = "^\\d*$";  //整数

            List<Fvirtualcointype> list = new ArrayList<>();

            for (int i = 0; i < jsonArray.size(); i++) {
                Fvirtualcointype fvirtualcointype = (Fvirtualcointype) JSONObject.toBean(jsonArray.getJSONObject(i), Fvirtualcointype.class);

                Fvirtualcointype old=  frontVirtualCoinService.findFvirtualCoinById(fvirtualcointype.getFtype());

                if(type==1) {
                    old.setKyc1_day_count(fvirtualcointype.getKyc1_day_count());
                    old.setKyc1_single_limit(fvirtualcointype.getKyc1_single_limit());
                    old.setKyc1_day_limit(fvirtualcointype.getKyc1_day_limit());
                    old.setKyc1_month_limit(fvirtualcointype.getKyc1_month_limit());
                }
                 if(type==2){
                  old.setKyc2_day_count(fvirtualcointype.getKyc2_day_count());
                  old.setKyc2_single_limit(fvirtualcointype.getKyc2_single_limit());
                  old.setKyc2_day_limit(fvirtualcointype.getKyc2_day_limit());
                  old.setKyc2_month_limit(fvirtualcointype.getKyc2_month_limit());
              }

       /*         //给默认值
                if(StringUtils.isBlank(vo.getAuto_day_count())) {
                    vo.setAuto_day_count("0");
                }
                if(StringUtils.isBlank(vo.getAuto_day_limit())) {
                    vo.setAuto_day_limit("0");
                }
                if(StringUtils.isBlank(vo.getAuto_single_limit())) {
                    vo.setAuto_single_limit("0");
                }
                if(!vo.getAuto_day_count().matches(num2Reg) || !vo.getAuto_day_limit().matches(num1Reg) || !vo.getAuto_single_limit().matches(num1Reg)) {
                    jsonObject.accumulate("message", "数据错误，请检查");
                    jsonObject.accumulate("statusCode", 300);
                }
                */
                list.add(old);
            }
          frontVirtualCoinService.saveOrUpdate(list);
                jsonObject.accumulate("message", "更新成功");
                jsonObject.accumulate("statusCode", 200);
                return jsonObject.toString();


        }catch (Exception e) {

            e.printStackTrace();
            jsonObject.accumulate("message", "系统异常");
            jsonObject.accumulate("statusCode", 300);
        }
        return jsonObject.toString();
    }


}
