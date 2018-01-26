package com.ruizton.main.controller.front;

import com.ruizton.main.Enum.BankInfoStatusEnum;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.model.FalipayBind;
import com.ruizton.main.model.Fuser;
import com.ruizton.util.DateHelper;
import com.ruizton.util.Utils;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * 支付宝绑定
 * Created by luwei on 17-3-15.
 */
@Controller
public class FalipayBindController extends BaseController {

    //每页显示多少条数据
    private int numPerPage = Utils.getNumPerPage();

    /**
     * 用户绑定支付宝信息
     * @param name  支付宝帐号名称
     * @param account  支付宝帐号
     * @return
     */
    @RequestMapping(value = "/user/addAlipayBind", produces = {JsonEncode})
    @ResponseBody
    public String addAlipayBind(String name, String account) {
        JSONObject jsonObject = new JSONObject();
        if(StringUtils.isBlank(name)) {
            jsonObject.accumulate("code", -1);
            jsonObject.accumulate("msg", i18nMsg("s_alipay_name_not_empty"));
            return jsonObject.toString();
        }

        if(StringUtils.isBlank(account)) {
            jsonObject.accumulate("code", -1);
            jsonObject.accumulate("msg", i18nMsg("s_alipay_account_not_empty"));
            return jsonObject.toString();
        }

        //判断数据库中是否存在该帐号
        FalipayBind vo =  falipayBindService.findByAccount(account);
        if(vo != null) {
            jsonObject.accumulate("code", -1);
            jsonObject.accumulate("msg", i18nMsg("s_account_exist"));
            return jsonObject.toString();
        }

        Fuser user = this.GetSession(request);
        if(user == null ) {
            jsonObject.accumulate("code", -1);
            jsonObject.accumulate("msg", i18nMsg("s_user_not_login"));
            return jsonObject.toString();
        }

        //判断支付宝绑定数量是否超过5个
        List<FalipayBind> list = falipayBindService.findListByUserId(user.getFid());
        if(list != null && list.size() > 5 ) {
            jsonObject.accumulate("code", -1);
            jsonObject.accumulate("msg", i18nMsg("s_bind_alipay_limit") + "5");
            return jsonObject.toString();
        }

        Timestamp tm = Timestamp.valueOf(DateHelper.date2String(new Date(), DateHelper.DateFormatType.YearMonthDay_HourMinuteSecond));

        FalipayBind install = new FalipayBind();
        install.setFcreateTime(tm);
        install.setFstatus(BankInfoStatusEnum.NORMAL_VALUE);
        install.setVersion(0);
        install.setFname(name);
        install.setFaccount(account);
        install.setFuser(user);
        this.falipayBindService.save(install);


        jsonObject.accumulate("code", 0);

        return jsonObject.toString();
    }


    /**
     * 删除绑定
     * @param account
     * @return
     */
    @RequestMapping(value = "/user/delAlipayBind", produces = {JsonEncode})
    @ResponseBody
    public String delAlipayBind(String account){
        JSONObject jsonObject = new JSONObject();
        //判断数据库中是否存在该帐号
        FalipayBind vo =  falipayBindService.findByAccount(account);
        if(vo == null) {
            jsonObject.accumulate("code", -1);
            jsonObject.accumulate("msg", i18nMsg("s_account_not_exist"));
            return jsonObject.toString();
        }

        falipayBindService.delete(vo);
        jsonObject.accumulate("code", 0);

        return jsonObject.toString();
    }


    @RequestMapping(value = "/ssadmin/alipayBindList")
    public ModelAndView list() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("ssadmin/alipayBindList") ;
        //当前页
        int currentPage = 1;
        //搜索关键字
        String account = request.getParameter("account");
        String keyWord = request.getParameter("keywords");
        String orderField = request.getParameter("orderField");
        String orderDirection = request.getParameter("orderDirection");
        if(request.getParameter("pageNum") != null){
            currentPage = Integer.parseInt(request.getParameter("pageNum"));
        }
        StringBuffer filter = new StringBuffer();
        filter.append("where 1=1 \n");
        if(keyWord != null && keyWord.trim().length() >0){
            try {
                int fid = Integer.parseInt(keyWord);
                filter.append("and fuser.fid =" + fid + " \n");
            } catch (Exception e) {
                filter.append("and (fuser.floginName like '%" + keyWord
                        + "%' OR \n");
                filter.append("fuser.fnickName like '%" + keyWord + "%' OR \n");
                filter.append("fuser.frealName like '%" + keyWord + "%' ) \n");
            }
            modelAndView.addObject("keywords", keyWord);
        }

        if(account != null && account.trim().length() >0){
            filter.append(" and faccount like '%"+account+"%' \n");
            modelAndView.addObject("account", account);
        }

        if(orderField != null && orderField.trim().length() >0){
            filter.append("order by "+orderField+"\n");
        }else{
            filter.append("order by fid \n");
        }
        if(orderDirection != null && orderDirection.trim().length() >0){
            filter.append(orderDirection+"\n");
        }else{
            filter.append("desc \n");
        }

        List<FalipayBind> list = this.falipayBindService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);
        modelAndView.addObject("alipaybindList", list);
        modelAndView.addObject("numPerPage", numPerPage);
        modelAndView.addObject("currentPage", currentPage);
        modelAndView.addObject("rel", "alipayBindList");
        //总数量
        modelAndView.addObject("totalCount",this.adminService.getAllCount("FalipayBind", filter+""));
        return modelAndView;
    }
}
