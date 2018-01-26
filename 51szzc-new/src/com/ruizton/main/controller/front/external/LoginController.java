package com.ruizton.main.controller.front.external;

import com.ruizton.main.comm.ConstantMap;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.log.LOG;
import com.ruizton.main.model.Fuser;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 外部用户登录
 * Created by luwei on 17-4-11.
 */
@Controller
public class LoginController extends BaseController {

    /*@Autowired
    protected ConstantMap map;*/

    //初始化登录页面
    @RequestMapping(value = "/external/init")
    public ModelAndView init() {
        ModelAndView modelAndView = new ModelAndView("/front/external/login");

        return modelAndView;
    }

    @RequestMapping(value = "/external/login", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String login(@RequestParam(required=true) String loginName,
                        @RequestParam(required=true) String password) {
        JSONObject jsonObject = new JSONObject();
        LOG.i(this.getClass(),"资讯用户登录开始");
        if(StringUtils.isBlank(loginName)) {
            jsonObject.accumulate("code", -1);
            jsonObject.accumulate("msg", "登录名不能为空！");
            return jsonObject.toString();
        }

        if(StringUtils.isBlank(password)) {
            jsonObject.accumulate("code", -2);
            jsonObject.accumulate("msg", "密码不能为空！");
            return jsonObject.toString();
        }

        String sys_login_name = map.get("article_loginName").toString();
        String sys_login_pwd = map.get("article_loginPwd").toString();
        if(StringUtils.isBlank(sys_login_name) || StringUtils.isBlank(sys_login_pwd)) {
            jsonObject.accumulate("code", -1);
            jsonObject.accumulate("msg", "系统异常！");
            return jsonObject.toString();
        }

        if(!sys_login_name.equals(loginName)) {
            jsonObject.accumulate("code", -1);
            jsonObject.accumulate("msg", "登录名错误！");
            return jsonObject.toString();
        }

        if(!sys_login_pwd.equals(password)) {
            jsonObject.accumulate("code", -2);
            jsonObject.accumulate("msg", "密码错误！");
            return jsonObject.toString();
        }

        Fuser fuser = new Fuser();
        fuser.setFloginName(loginName);
        fuser.setFloginPassword(password);
        //保存三方登录信息进session
        SetSecondLoginSession(request, fuser);

        //资讯用户登录，不用存储任何信息
        jsonObject.accumulate("code", 0);
        jsonObject.accumulate("msg", "登录成功");

        return jsonObject.toString();
    }

}
