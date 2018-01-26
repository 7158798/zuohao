package com.ruizton.main.controller.admin;

import com.ruizton.main.Enum.AuditProcessEnum;
import com.ruizton.main.Enum.KYCAuditingStatusEnum;
import com.ruizton.main.Enum.ModuleConstont;
import com.ruizton.main.Enum.ValidateKycLevelEnum;
import com.ruizton.main.aspect.SysLog;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.model.*;
import com.ruizton.util.BTCUtils;
import com.ruizton.util.ETHUtils;
import com.ruizton.util.Utils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by fenggq on 17-3-30.
 */
@Controller
public class UserKycController  extends BaseController {

    // 每页显示多少条数据
    private int numPerPage = Utils.getNumPerPage();


    //KYC认证列表
    @RequestMapping("/ssadmin/userKycList")
    public ModelAndView goUpList() throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("ssadmin/userKycList");
        // 当前页
        int currentPage = 1;
        // 搜索关键字
        String keyWord = request.getParameter("keywords");
//		String uid = request.getParameter("uid");
//		String startDate = request.getParameter("startDate");
        String orderField = request.getParameter("orderField");
        String orderDirection = request.getParameter("orderDirection");
        if (request.getParameter("pageNum") != null) {
            currentPage = Integer.parseInt(request.getParameter("pageNum"));
        }

        StringBuffer filter = new StringBuffer();


        filter.append("where status = 1\n");


        if (keyWord != null && keyWord.trim().length() > 0) {
            try {
                int fid = Integer.parseInt(keyWord);
                filter.append("and fuser.fid =" + fid + " \n");
            } catch (Exception e) {
                filter.append("and (fuser.floginName like '%" + keyWord + "%' or \n");
                filter.append("fuser.fnickName like '%" + keyWord + "%'  or \n");
                filter.append("fuser.frealName like '%" + keyWord + "%'  or \n");
                filter.append("fuser.ftelephone like '%" + keyWord + "%'  or \n");
                filter.append("fuser.femail like '%" + keyWord + "%'  or \n");
                filter.append("fuser.fidentityNo like '%" + keyWord + "%' )\n");
            }
            modelAndView.addObject("keywords", keyWord);
        }


        if (orderField != null && orderField.trim().length() > 0) {
            filter.append("order by " + orderField + "\n");
        } else {
            filter.append("order by id \n");
        }

        if (orderDirection != null && orderDirection.trim().length() > 0) {
            filter.append(orderDirection + "\n");
        } else {
            filter.append("desc \n");
        }

        java.util.List<Fvalidatekyc> list = this.fvalidateKycService.list(
                (currentPage - 1) * numPerPage, numPerPage, filter + "", true);


        modelAndView.addObject("fvalidateKyclist", list);
        modelAndView.addObject("numPerPage", numPerPage);
        modelAndView.addObject("currentPage", currentPage);
        modelAndView.addObject("rel", "fvalidateKyclist");
        modelAndView.addObject("totalCount",
                this.adminService.getAllCount("Fvalidatekyc", filter + ""));
        return modelAndView;
    }


    //kyc认证跳转
    @RequestMapping("ssadmin/goKycJSP")
    public ModelAndView goKycJSP(@RequestParam int uid) throws Exception {

        String url = request.getParameter("url");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(url);
        Fvalidatekyc fvalidatekyc =  this.fvalidateKycService.findById(uid) ;
        modelAndView.addObject("fvalidatekyc", fvalidatekyc);
        return modelAndView;
    }


    /**
     *  审核
     * @param uid
     * @param status
     * @return
     */
    @SysLog(code = ModuleConstont.USER_OPERATION, method = "用户KYC认证审核")
    @RequestMapping(value = "/ssadmin/auditingKyc",method = RequestMethod.POST)
    public ModelAndView auditingKyc(@RequestParam Integer uid,
                                    @RequestParam Integer status){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("ssadmin/comm/ajaxDone");

        Fvalidatekyc fvalidatekyc = this.fvalidateKycService.findById(uid);
        Fuser fuser = this.frontUserService.findById(fvalidatekyc.getFuser().getFid());
        if(fvalidatekyc == null){
            modelAndView.addObject("statusCode", 300);
            modelAndView.addObject("message", "记录不存在");
            return modelAndView;
        }
        if(fvalidatekyc.getStatus() != KYCAuditingStatusEnum.AUDITING.getValue()){
            modelAndView.addObject("statusCode", 300);
            modelAndView.addObject("message", "状态不正确");
            return modelAndView;
        }
        if(fuser == null){
            modelAndView.addObject("statusCode", 300);
            modelAndView.addObject("message", "用户不存在");
            return modelAndView;
        }
        if(fuser.getKyclevel() != ValidateKycLevelEnum.AUDITING.getKey()
                && fuser.getKyclevel() != ValidateKycLevelEnum.NOT_PASS.getKey() ){
            modelAndView.addObject("statusCode", 300);
            modelAndView.addObject("message", "用户状态错误");
            return modelAndView;
        }

        String reason = request.getParameter("reason");
        String message = "";
        if(status == KYCAuditingStatusEnum.PASS.getValue()){
            fvalidatekyc.setCancelReason("");
            fuser.setKyclevel(ValidateKycLevelEnum.COMPLETE.getKey());
            message = "恭喜您的KYC认证已通过审核";
        }else if(status == KYCAuditingStatusEnum.NO_PASS.getValue()){
            if(StringUtils.isBlank(reason)){
                modelAndView.addObject("statusCode", 300);
                modelAndView.addObject("message", "拒绝原因不能为空");
                return modelAndView;
            }
            fvalidatekyc.setCancelReason(reason);
            fuser.setKyclevel(ValidateKycLevelEnum.NOT_PASS.getKey());
            modelAndView.addObject("callbackType","closeCurrent");
            message = "您的KYC认证未通过审核，未通过原因："+reason+",若有疑问请致电400-900-6615";
        }
        fvalidatekyc.setStatus(status);
        fvalidatekyc.setAuditTime(Utils.getTimestamp());
        fvalidatekyc.setFadmin(getAdminSession(request));
        this.frontUserService.updateFuser(fuser,fvalidatekyc);
        sendMessage(fuser,message);
        modelAndView.addObject("statusCode", 200);
        modelAndView.addObject("message", "保存成功");
        return modelAndView;
    }

}
