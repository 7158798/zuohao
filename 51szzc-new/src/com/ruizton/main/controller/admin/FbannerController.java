package com.ruizton.main.controller.admin;

import com.ruizton.main.Enum.BannerSeatEnum;
import com.ruizton.main.Enum.BannerStatusEnum;
import com.ruizton.main.Enum.ModuleConstont;
import com.ruizton.main.aspect.SysLog;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.model.Fadmin;
import com.ruizton.main.model.Fbanner;
import com.ruizton.main.model.Fvideo;
import com.ruizton.util.DateHelper;
import com.ruizton.util.Utils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * banner横幅广告管理
 * Created by luwei on 17-3-7.
 */
@Controller
public class FbannerController extends BaseController {

    //每页显示多少条数据
    private int numPerPage = Utils.getNumPerPage();

    //列表页
    @RequestMapping("/ssadmin/bannerList")
    public ModelAndView init() throws Exception{
        ModelAndView modelAndView =  new ModelAndView();
        modelAndView.setViewName("ssadmin/bannerList");

        modelAndView.addObject("bannerSeatEnumList", BannerSeatEnum.bannerSeatEnumList);
        modelAndView.addObject("bannerStatusEnumList", BannerStatusEnum.bannerStatusEnumList);

        //当前页
        int currentPage = 1;
        if(request.getParameter("pageNum") != null) {
            currentPage = Integer.parseInt(request.getParameter("pageNum"));
        }

        //查询条件
        StringBuffer filter = new StringBuffer();
        filter.append("where 1=1 \n");

        String startDate = request.getParameter("startDate");
        if(StringUtils.isNotBlank(startDate)) {
            modelAndView.addObject("startDate", startDate);
            filter.append(" and fcreateTime >=  '"  +startDate + " 00:00:00'");
        }

        String endDate = request.getParameter("endDate");
        if(StringUtils.isNotBlank(endDate)) {
            modelAndView.addObject("endDate", endDate);
            filter.append(" and fcreateTime <=  '"  +startDate + " 23:59:59'");
        }

        String fseat = request.getParameter("fseat");
        if(StringUtils.isNotBlank(fseat)) {
            modelAndView.addObject("fseat", Integer.valueOf(fseat));
            filter.append(" and fseat = " + fseat);
        }

        String current_date = DateHelper.date2String(new Date(), DateHelper.DateFormatType.YearMonthDay);
        Date currentDate = DateHelper.string2Date(current_date, DateHelper.DateFormatType.YearMonthDay);
        String fstatus = request.getParameter("fstatus");
        if(StringUtils.isNotBlank(fstatus)) {
            modelAndView.addObject("fstatus", Integer.valueOf(fstatus));
            //查询已删除的
            if(fstatus.equals(BannerStatusEnum.DELETE.getCode().toString())) {
                filter.append(" and fstatus = " + fstatus);
            }else if(fstatus.equals(BannerStatusEnum.DISABLED.getCode().toString())) {  //查询停用的
                filter.append(" and (fstatus = "+fstatus+"  or fendDate < '"+current_date+"') ");
            }else if(fstatus.equals(BannerStatusEnum.USE.getCode().toString())) {  //使用中
                filter.append(" and (fstatus = "+fstatus+"  or fendDate >= '"+current_date+"') ");
            }
        }else {
            //查询所有，则不含已删除的
            filter.append(" and fstatus in (0,1,2) ");
        }

        //排序
        filter.append(" order by (case when '"+current_date+"' > fendDate then 2 else fstatus end) asc,fcreateTime desc ");


        int firstResult = (currentPage -1) * numPerPage;
        List<Fbanner> list = this.fbannerService.list(firstResult, numPerPage, filter+"", true);
        if(list != null) {
            for(Fbanner fbanner : list) {
                fbanner.setFseatName(BannerSeatEnum.getNameByCode(fbanner.getFseat()));

                //有效期
                String startDate_str = DateHelper.date2String(fbanner.getFcreateTime(), DateHelper.DateFormatType.YearMonthDay);
                String endDate_str = DateHelper.date2String(fbanner.getFendDate(), DateHelper.DateFormatType.YearMonthDay);
                fbanner.setFvalidate(startDate_str + "—" + endDate_str);
                //状态
                if(fbanner.getFstatus().intValue() != BannerStatusEnum.DELETE.getCode().intValue()) {
                    if(fbanner.getFstatus().intValue() ==  BannerStatusEnum.USE.getCode().intValue()) {  //使用中
                        if(currentDate.after(fbanner.getFendDate())) {
                            fbanner.setFstatus(BannerStatusEnum.DISABLED.getCode());
                        }
                    }
                }
                fbanner.setFstatusName(BannerStatusEnum.getNameByCode(fbanner.getFstatus()));

            }
        }
        modelAndView.addObject("fbannerList", list);
        modelAndView.addObject("numPerPage", numPerPage);
        modelAndView.addObject("currentPage", currentPage);

        //总数量
        modelAndView.addObject("totalCount", this.adminService.getAllCount("Fbanner", filter+""));

        modelAndView.addObject("rel", "bannerList");
        return modelAndView;
    }


    @RequestMapping("ssadmin/goBannerJsp")
    public ModelAndView goBannerJsp() {
        String url =  request.getParameter("url");
        ModelAndView modelAndView = new ModelAndView(url);
        if(request.getParameter("fid") != null) {  //修改
            int fid = Integer.parseInt(request.getParameter("fid"));
            Fbanner fbanner =  this.fbannerService.findById(fid);
            modelAndView.addObject("fbanner", fbanner);
        }


        modelAndView.addObject("bannerSeatEnumList", BannerSeatEnum.bannerSeatEnumList);

        return modelAndView;
    }


    @RequestMapping("ssadmin/saveBanner")
    public ModelAndView saveBanner(Fbanner req) throws Exception {
        ModelAndView modelAndView = new ModelAndView() ;
        modelAndView.setViewName("ssadmin/comm/ajaxDone");

        Timestamp tm = Timestamp.valueOf(DateHelper.date2String(new Date(), DateHelper.DateFormatType.YearMonthDay_HourMinuteSecond));
        req.setFcreateTime(tm);

        String startDate_str = request.getParameter("startDate_str");
        req.setFstartDate(Timestamp.valueOf(startDate_str + " 00:00:00"));

        String endDate_str =  request.getParameter("endDate_str");
        req.setFendDate(Timestamp.valueOf(endDate_str + " 00:00:00"));

        Fadmin admin = (Fadmin) request.getSession().getAttribute("login_admin");
        req.setFcreateUser(admin);

        //查询最新的banner
        Fbanner lastBanne = this.fbannerService.getLastBanner(req.getFseat());
        req.setFpriority( lastBanne == null ? 1 :  lastBanne.getFpriority() + 1);
        this.fbannerService.save(req);
        modelAndView.addObject("statusCode",200);
        modelAndView.addObject("message","更新成功");
        modelAndView.addObject("callbackType","closeCurrent");

        return modelAndView;
    }



    @RequestMapping("ssadmin/updateBanner")
    public ModelAndView updateBanner(Fbanner req) throws Exception {
        ModelAndView modelAndView = new ModelAndView() ;
        modelAndView.setViewName("ssadmin/comm/ajaxDone");

        if(req.getFid() == null) {
            modelAndView.addObject("message","fid为空");
            modelAndView.addObject("statusCode",300);
            return modelAndView;
        }

        Fbanner vo = this.fbannerService.findById(req.getFid());
        if(vo  == null) {
            modelAndView.addObject("message","查询数据为空，请核对后再操作");
            modelAndView.addObject("statusCode",300);
            return modelAndView;
        }

        String current_date = DateHelper.date2String(new Date(), DateHelper.DateFormatType.YearMonthDay);
        Date currentDate = DateHelper.string2Date(current_date, DateHelper.DateFormatType.YearMonthDay);
        if(vo != null && vo.getFstatus() != null && vo.getFstatus().intValue() == BannerStatusEnum.USE.getCode().intValue()) {

            //状态是使用中，则需要判断投放时间
            if(currentDate.after(vo.getFendDate())) {  //状态是使用中，但超过投放期，处于停用状态
                //停用是可以直接修改的
            }else{
                modelAndView.addObject("message","使用中的banner无法进行修改，请先停用");
                modelAndView.addObject("statusCode",300);
                return modelAndView;
            }
        }

        Timestamp tm = Timestamp.valueOf(DateHelper.date2String(new Date(), DateHelper.DateFormatType.YearMonthDay_HourMinuteSecond));
        vo.setFcreateTime(tm);
        vo.setFseat(req.getFseat());

        String startDate_str = request.getParameter("startDate_str");
        vo.setFstartDate(Timestamp.valueOf(startDate_str + " 00:00:00"));

        String endDate_str = request.getParameter("endDate_str");
        vo.setFendDate(Timestamp.valueOf(endDate_str + " 00:00:00"));

        Fadmin admin = (Fadmin) request.getSession().getAttribute("login_admin");
        vo.setFcreateUser(admin);
        vo.setFlinkurl(req.getFlinkurl());
        vo.setFimgurl(req.getFimgurl());
        this.fbannerService.update(vo);
        modelAndView.addObject("statusCode", 200);
        modelAndView.addObject("message", "更新成功");
        modelAndView.addObject("callbackType", "closeCurrent");

        return modelAndView;
    }

    @RequestMapping("ssadmin/updateBannerStatus")
    @SysLog(code = ModuleConstont.SYSTEM_OPERATION, method = "修改banner状态")
    public ModelAndView updateStatus(Fbanner req) {
        ModelAndView modelAndView = new ModelAndView() ;
        modelAndView.setViewName("ssadmin/comm/ajaxDone");

        if(req.getFid() == null) {
            modelAndView.addObject("message","fid为空");
            modelAndView.addObject("statusCode",300);
            return modelAndView;
        }

        Fbanner vo = this.fbannerService.findById(req.getFid());
        if(vo  == null) {
            modelAndView.addObject("message","查询数据为空，请核对后再操作");
            modelAndView.addObject("statusCode",300);
            return modelAndView;
        }

        String current_date = DateHelper.date2String(new Date(), DateHelper.DateFormatType.YearMonthDay);
        Date currentDate = DateHelper.string2Date(current_date, DateHelper.DateFormatType.YearMonthDay);
        //将状态改为已删除
        if(req.getFstatus() != null && req.getFstatus().intValue() == BannerStatusEnum.DELETE.getCode().intValue()) {
            if(vo != null && vo.getFstatus() != null && vo.getFstatus().intValue() == BannerStatusEnum.USE.getCode().intValue()) {
                //状态是使用中，则需要判断投放时间
                if(currentDate.after(vo.getFendDate())) {  //状态是使用中，但超过投放期，处于停用状态
                    //停用是可以直接删除的
                }else{
                    modelAndView.addObject("message","使用中的banner无法删除，请先停用");
                    modelAndView.addObject("statusCode",300);
                    return modelAndView;
                }
            }
        }else if(req.getFstatus() != null && req.getFstatus().intValue() == BannerStatusEnum.DISABLED.getCode().intValue()) {
            if(vo != null && vo.getFstatus() != null && vo.getFstatus().intValue() == BannerStatusEnum.DRAFT.getCode().intValue()) {
                modelAndView.addObject("message","草稿状态的无法停用");
                modelAndView.addObject("statusCode",300);
                return modelAndView;
            }
        }

        vo.setFstatus(req.getFstatus());
        this.fbannerService.update(vo);
        modelAndView.addObject("statusCode", 200);
        modelAndView.addObject("message", "更新成功");
        return modelAndView;
    }



    /**
     * 列表上移
     * @return
     * @throws Exception
     */
    @RequestMapping("ssadmin/upBanner")
    public ModelAndView upVideo(Fbanner req) throws Exception {
        return this.upOrDownVideo(req);
    }

    /**
     * 列表下移
     * @return
     * @throws Exception
     */
    @RequestMapping("ssadmin/downBanner")
    public ModelAndView downVideo(Fbanner req) throws Exception {
        return this.upOrDownVideo(req);
    }

    public ModelAndView upOrDownVideo(Fbanner req) throws Exception {
        ModelAndView modelAndView = new ModelAndView() ;

        if(req == null || req.getFid() == null || StringUtils.isBlank(req.getOrderType())) {
            modelAndView.addObject("message","参数错误");
            modelAndView.addObject("statusCode",300);
            return modelAndView;
        }

        //根据id获取类别信息
        Fadmin admin = (Fadmin) request.getSession()
                .getAttribute("login_admin");
        int temp;
        Fbanner propertyBanner = null;
        Fbanner fbanner = this.fbannerService.findById(req.getFid());
        if("down".equals(req.getOrderType())){
            propertyBanner = this.fbannerService.getPreviousBanner(fbanner.getFpriority(), fbanner.getFseat());
        }else{
            propertyBanner = this.fbannerService.getNextBanner(fbanner.getFpriority(), fbanner.getFseat());
        }
        if(null == propertyBanner){
            modelAndView.addObject("statusCode",300);
            modelAndView.addObject("message","已无法移动");
        }else{
            temp = fbanner.getFpriority();
            fbanner.setFpriority(propertyBanner.getFpriority());
            propertyBanner.setFpriority(temp);

            this.fbannerService.update(propertyBanner);
            this.fbannerService.update(fbanner);

            modelAndView.addObject("statusCode",200);
            modelAndView.addObject("message","移动成功");
        }
        modelAndView.setViewName("ssadmin/comm/ajaxDone");

        return modelAndView;
    }


}
