package com.ruizton.main.controller.admin;

import com.ruizton.main.Enum.BankTypeEnum;
import com.ruizton.main.Enum.IdentityTypeEnum;
import com.ruizton.main.Enum.IntegralTypeEnum;
import com.ruizton.main.Enum.ModuleConstont;
import com.ruizton.main.aspect.SysLog;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.model.Fabout;
import com.ruizton.main.model.Fuser;
import com.ruizton.main.model.Fusersetting;
import com.ruizton.main.model.integral.Fusergrade;
import com.ruizton.main.model.integral.Fuserintegraldetail;
import com.ruizton.main.service.front.FrontTradeService;
import com.ruizton.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fenggq on 17-2-22.
 */
@Controller
public class GradeController extends BaseController{

    private int numPerPage = Utils.getNumPerPage();

    @RequestMapping("ssadmin/goGradeJSP")
    public ModelAndView goUserJSP() throws Exception {
        String url = request.getParameter("url");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(url);
        if (request.getParameter("uid") != null) {
            int fid = Integer.parseInt(request.getParameter("uid"));
            Fusergrade fusergrade = this.userGradeService.findById(fid);
            fusergrade.setFoutfee(fusergrade.getFoutfee().multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP));
            fusergrade.setFcapitalinfee(fusergrade.getFcapitalinfee().multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP));
            fusergrade.setTradefee(fusergrade.getTradefee().multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP));
            fusergrade.setVirtualinfee(fusergrade.getVirtualinfee().multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP));
            modelAndView.addObject("fusergrade",fusergrade);
        }
        return modelAndView;
    }

    /**
     * 修改用户积分等级配置
     * @param req
     * @return
     * @throws Exception
     */
    @RequestMapping("ssadmin/updateGrade")
    public ModelAndView updateGradeSet(Fusergrade req) throws Exception {
        String url = request.getParameter("url");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("ssadmin/comm/ajaxDone");

        BigDecimal maxFee = new BigDecimal(100);
        if(req.getTradefee().compareTo(maxFee) >= 0 ||
            req.getVirtualinfee().compareTo(maxFee) >= 0 ||
            req.getFoutfee().compareTo(maxFee) >= 0 ||
            req.getFcapitalinfee().compareTo(maxFee) >= 0 ){

            modelAndView.addObject("statusCode", -1);
            modelAndView.addObject("message", "手续费必须小于100%");
            return null;
        }

        if(req.getTradefee().compareTo(BigDecimal.ZERO) < 0 ||
                req.getVirtualinfee().compareTo(BigDecimal.ZERO) <0 ||
                req.getFoutfee().compareTo(BigDecimal.ZERO) < 0 ||
                req.getFcapitalinfee().compareTo(BigDecimal.ZERO)< 0 ){

            modelAndView.addObject("statusCode", -1);
            modelAndView.addObject("message", "手续费必须大于0");
            return null;
        }

        if(req.getFneedintegral() < 0 ){
            modelAndView.addObject("statusCode", -1);
            modelAndView.addObject("message", "所需积分不能小于0");
            return null;
        }



        Fusergrade fusergrade = this.userGradeService.findById(req.getFid());
        fusergrade.setFneedintegral(req.getFneedintegral());
        fusergrade.setFoutfee(req.getFoutfee().divide(new BigDecimal(100)));
        fusergrade.setFcapitalinfee(req.getFcapitalinfee().divide(new BigDecimal(100)));
        fusergrade.setTradefee(req.getTradefee().divide(new BigDecimal(100)));
        fusergrade.setVirtualinfee(req.getVirtualinfee().divide(new BigDecimal(100)));
        this.userGradeService.updateObj(fusergrade);

        //修改交易手续费
        this.frontTradeService.putRates(fusergrade.getFid(), fusergrade.getTradefee().doubleValue()) ;


        modelAndView.addObject("statusCode", 200);
        modelAndView.addObject("callbackType", "closeCurrent");
        modelAndView.addObject("message", "修改成功");
        return modelAndView;
    }


    /**
     * 用户等级设置列表
     * @return
     * @throws Exception
     */
    @RequestMapping("/ssadmin/gradeSetList")
    public ModelAndView Index() throws Exception{
        ModelAndView modelAndView = new ModelAndView() ;
        modelAndView.setViewName("ssadmin/gradeSetList") ;
        int currentPage = 1;
        String orderField = request.getParameter("orderField");
        String orderDirection = request.getParameter("orderDirection");
        if(request.getParameter("pageNum") != null){
            currentPage = Integer.parseInt(request.getParameter("pageNum"));
        }
        StringBuffer filter = new StringBuffer();
        filter.append("where 1=1 \n");

        if(orderField != null && orderField.trim().length() >0){
            filter.append("order by "+orderField+"\n");
        }else{
            filter.append("order by fid \n");
        }
        if(orderDirection != null && orderDirection.trim().length() >0){
            filter.append(orderDirection+"\n");
        }else{
            filter.append("asc \n");
        }
        List<Fusergrade> list = this.userGradeService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);

        for(Fusergrade fusergrade:list){
            fusergrade.setFoutfee(fusergrade.getFoutfee().multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP));
            fusergrade.setFcapitalinfee(fusergrade.getFcapitalinfee().multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP));
            fusergrade.setTradefee(fusergrade.getTradefee().multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP));
            fusergrade.setVirtualinfee(fusergrade.getVirtualinfee().multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP));
        }
        modelAndView.addObject("gradelist", list);
        modelAndView.addObject("numPerPage", numPerPage);
        modelAndView.addObject("currentPage", currentPage);
        modelAndView.addObject("rel", "aboutList");
        modelAndView.addObject("totalCount",this.adminService.getAllCount("Fusergrade", filter+""));
        return modelAndView ;
    }

    /**
     * 用户积分列表
     * @return
     * @throws Exception
     */
    @RequestMapping("/ssadmin/userIntegralList")
    public ModelAndView userIntegralList(@RequestParam(required = false) Integer integral1,
                                         @RequestParam(required = false) Integer integral2,
                                         @RequestParam(required = false) Integer flevel) throws Exception{
        ModelAndView modelAndView = new ModelAndView() ;
        modelAndView.setViewName("ssadmin/userIntegralList") ;
        int currentPage = 1;
        String orderField = request.getParameter("orderField");
        String orderDirection = request.getParameter("orderDirection");
        String keyWord = request.getParameter("keywords");
        if(request.getParameter("pageNum") != null){
            currentPage = Integer.parseInt(request.getParameter("pageNum"));
        }
        StringBuffer filter = new StringBuffer();
        filter.append("where 1=1 \n");

        if (keyWord != null && keyWord.trim().length() > 0) {
            try {
                int fid = Integer.parseInt(keyWord);
                filter.append("and fid =" + fid + " \n");
            } catch (Exception e) {
                filter.append("and (floginName like '%" + keyWord + "%' or \n");
                filter.append("fnickName like '%" + keyWord + "%'  or \n");
                filter.append("frealName like '%" + keyWord + "%'  or \n");
                filter.append("ftelephone like '%" + keyWord + "%'  or \n");
                filter.append("femail like '%" + keyWord + "%'  or \n");
                filter.append("fidentityNo like '%" + keyWord + "%' )\n");
            }
            modelAndView.addObject("keywords", keyWord);
        }

        if(flevel != null && flevel > 0){
            filter.append(" and fscore.flevel = "+flevel);
        }

        if(integral1 != null){
            filter.append(" and integral >= "+integral1);
        }

        if(integral2 != null){
            filter.append(" and integral <= "+integral2);
        }

        if(orderField != null && orderField.trim().length() >0){
            filter.append(" order by "+orderField+"\n");
        }else{
            filter.append(" order by fid \n");
        }

        if(orderDirection != null && orderDirection.trim().length() >0){
            filter.append(orderDirection+"\n");
        }else{
            filter.append("asc \n");
        }
        List<Fuser> list = this.userService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);

        int[] a ;
        for(Fuser fuser:list){
            a = this.integralService.getUserIntegralDetails(fuser.getFid());
            fuser.setIntegralArray(a);
        }
        modelAndView.addObject("userlist", list);
        modelAndView.addObject("numPerPage", numPerPage);
        modelAndView.addObject("currentPage", currentPage);
        modelAndView.addObject("rel", "aboutList");
        modelAndView.addObject("totalCount",this.adminService.getAllCount("Fuser", filter+""));

        return modelAndView ;
    }


    @RequestMapping("ssadmin/goIntegralJSP")
    public ModelAndView goIntegralJSP() throws Exception {
        String url = request.getParameter("url");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(url);
        if (request.getParameter("uid") != null) {
            int fid = Integer.parseInt(request.getParameter("uid"));
            Fuser fuser = this.userService.findById(fid);
            modelAndView.addObject("fuser",fuser);

            Map map = IntegralTypeEnum.getMap();
            modelAndView.addObject("map",map);
        }
        return modelAndView;
    }


    @SysLog(code = ModuleConstont.USER_OPERATION, method = "修改积分")
    @RequestMapping("ssadmin/updateIntegeral")
    public ModelAndView updateIntegeral(@RequestParam Integer uid,
                                        @RequestParam Integer integral,
                                        @RequestParam Integer type,
                                        @RequestParam(required = false) String  remark) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("ssadmin/comm/ajaxDone");

        Fuser fuser = this.userService.findById(uid);
        if(integral == null || integral == 0){
            modelAndView.addObject("statusCode", -1);
            modelAndView.addObject("message", "修改积分不能为空");
            return modelAndView;
        }

        if(fuser.getIntegral()+integral < 0){
            modelAndView.addObject("statusCode", -1);
            modelAndView.addObject("message", "用户积分不足");
            return modelAndView;
        }

        if(org.apache.commons.lang.StringUtils.isEmpty(remark)){
            modelAndView.addObject("message", "改动缘由不能为空");
            return modelAndView;
        }

        this.integralService.updateIntegralBySystem(fuser,type,integral,remark);

        modelAndView.setViewName("ssadmin/comm/ajaxDone");
        modelAndView.addObject("statusCode", 200);
        modelAndView.addObject("callbackType", "closeCurrent");
        modelAndView.addObject("message", "修改成功");
        modelAndView.addObject("uid",uid);
        return modelAndView;
    }



    /**
     * 用户积分明细列表
     * @return
     * @throws Exception
     */
    @RequestMapping("/ssadmin/integralDetail")
    public ModelAndView userIntegralDetail(
                                         @RequestParam Integer userId,
                                         @RequestParam(required = false)  Integer type) throws Exception{
     //   Integer userId = 11;
        ModelAndView modelAndView = new ModelAndView() ;
        modelAndView.setViewName("ssadmin/userIntegralDetailList") ;
        int currentPage = 1;
        String orderField = request.getParameter("orderField");
        String orderDirection = request.getParameter("orderDirection");
        if(request.getParameter("pageNum") != null){
            currentPage = Integer.parseInt(request.getParameter("pageNum"));
        }
        StringBuffer filter = new StringBuffer();
        filter.append("where fuser.fid= "+userId);


        String start = request.getParameter("start");
        if(start != null && start.trim().length() >0){
            filter.append("and  DATE_FORMAT(createDate,'%Y-%m-%d') >= '"+start+"' \n");
            modelAndView.addObject("logDate", start);
        }

        String end = request.getParameter("end");
        if(end != null && end.trim().length() >0){
            filter.append("and  DATE_FORMAT(createDate,'%Y-%m-%d') <= '"+end+"' \n");
            modelAndView.addObject("end", end);
        }

        if(type != null && type > 0){
            filter.append(" and type = "+type);
        }

        if(orderField != null && orderField.trim().length() >0){
            filter.append(" order by "+orderField+"\n");
        }else{
            filter.append(" order by id \n");
        }

        if(orderDirection != null && orderDirection.trim().length() >0){
            filter.append(orderDirection+"\n");
        }else{
            filter.append("asc \n");
        }
        List<Fuserintegraldetail> list = this.userintegraldetailService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);

        Map map = IntegralTypeEnum.getMap();
        modelAndView.addObject("typeMap",map);
        modelAndView.addObject("userId",userId);

        modelAndView.addObject("detaillist", list);
        modelAndView.addObject("numPerPage", numPerPage);
        modelAndView.addObject("currentPage", currentPage);
        modelAndView.addObject("rel", "aboutList");
        modelAndView.addObject("totalCount",this.adminService.getAllCount("Fuserintegraldetail", filter+""));

        return modelAndView ;
    }


    public static void main(String[] args) {
        BigDecimal b = new BigDecimal(1.23233);
        BigDecimal s = b.multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP);
        s = s;
    }
}
