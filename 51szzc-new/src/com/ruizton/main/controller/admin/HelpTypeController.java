package com.ruizton.main.controller.admin;

import com.ruizton.main.Enum.ModuleConstont;
import com.ruizton.main.aspect.SysLog;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.model.*;
import com.ruizton.util.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by zygong on 17-3-16.
 */
@Controller
public class HelpTypeController extends BaseController{

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:sss");

    //每页显示多少条数据
    private int numPerPage = Utils.getNumPerPage();

    @RequestMapping("/ssadmin/helpTypeList")
    @SysLog(code = ModuleConstont.HELP_OPERATION, method = "帮助类型列表")
    public ModelAndView helpTypeList() throws Exception{
        ModelAndView modelAndView = new ModelAndView() ;
        modelAndView.setViewName("ssadmin/helpTypeList") ;
        int currentPage = 1;
        String keyWord = request.getParameter("keywords");
        String orderField = request.getParameter("orderField");
        String orderDirection = request.getParameter("orderDirection");
        if(request.getParameter("pageNum") != null){
            currentPage = Integer.parseInt(request.getParameter("pageNum"));
        }
        StringBuffer filter = new StringBuffer();
        filter.append("where 1=1 \n");
        if(keyWord != null && keyWord.trim().length() >0){
            filter.append("and fname like '%"+keyWord+"%' \n");
            modelAndView.addObject("keywords", keyWord);
        }
        if(orderField != null && orderField.trim().length() >0){
            filter.append("order by "+orderField+"\n");
        }else{
            filter.append("order by forder \n");
        }
        if(orderDirection != null && orderDirection.trim().length() >0){
            filter.append(orderDirection+"\n");
        }else{
            filter.append(" asc \n");
        }
        List<Fhelptype> list = this.fhelptypeService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);
        modelAndView.addObject("helpTypeList", list);
        modelAndView.addObject("numPerPage", numPerPage);
        modelAndView.addObject("currentPage", currentPage);
        modelAndView.addObject("rel", "aboutList");
        modelAndView.addObject("totalCount",this.adminService.getAllCount("Fhelptype", filter+""));
        return modelAndView ;
    }

    @RequestMapping("ssadmin/goHelpTypeJSP")
    public ModelAndView goHelpJSP() throws Exception{
        String url = request.getParameter("url");
        ModelAndView modelAndView = new ModelAndView() ;
        modelAndView.setViewName(url) ;
        if(request.getParameter("uid") != null){
            int fid = Integer.parseInt(request.getParameter("uid"));
            Fhelptype fhelptype = this.fhelptypeService.findById(fid);
            modelAndView.addObject("fhelptype", fhelptype);
        }

        return modelAndView;
    }

    @RequestMapping("ssadmin/saveHelpType")
    public ModelAndView saveHelpType(Fhelptype fhelptype) throws Exception{
        ModelAndView modelAndView = new ModelAndView() ;

        String dateString = sdf.format(new java.util.Date());
        Timestamp tm = Timestamp.valueOf(dateString);
        // 设在上传用户
        Fadmin admin = (Fadmin) request.getSession()
                .getAttribute("login_admin");
        fhelptype.setFcreateuser(admin);
        fhelptype.setFupdateuser(admin);
        //设置修改时间
        fhelptype.setFcreatetime(tm);
        fhelptype.setFupdatetime(tm);

        Fhelptype lastType = fhelptypeService.getLastType();

        // 判断数据库中是否已有数据
        if(null != lastType){
            fhelptype.setForder(lastType.getForder() + 1);
        }else{
            fhelptype.setForder(1);
        }

        this.fhelptypeService.saveObj(fhelptype);

        modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
        modelAndView.addObject("statusCode",200);
        modelAndView.addObject("message","保存成功");
        modelAndView.addObject("callbackType","closeCurrent");
        return modelAndView;
    }

    @RequestMapping("ssadmin/updateHelpType")
    @SysLog(code = ModuleConstont.HELP_OPERATION, method = "修改帮助类型")
    public ModelAndView updateHelpType(@RequestParam(required=true)Integer fid, @RequestParam(required=true)String fname, String fdescription) throws Exception{
        ModelAndView modelAndView = new ModelAndView() ;
        modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
        if(null == fid){
            modelAndView.addObject("statusCode",300);
            modelAndView.addObject("message","修改失败");
            modelAndView.addObject("callbackType","closeCurrent");
            return modelAndView;
        }
        Fhelptype fhelptype = fhelptypeService.findById(fid);

        fhelptype.setFname(fname);
        fhelptype.setFdescription(fdescription);

        String dateString = sdf.format(new java.util.Date());
        Timestamp tm = Timestamp.valueOf(dateString);
        // 设在上传用户
        Fadmin admin = (Fadmin) request.getSession()
                .getAttribute("login_admin");
        fhelptype.setFupdateuser(admin);
        //设置修改时间
        fhelptype.setFupdatetime(tm);

        this.fhelptypeService.updateObj(fhelptype);


        modelAndView.addObject("statusCode",200);
        modelAndView.addObject("message","修改成功");
        modelAndView.addObject("callbackType","closeCurrent");
        return modelAndView;
    }

    @RequestMapping("ssadmin/deleteHelpType")
    @SysLog(code = ModuleConstont.HELP_OPERATION, method = "删除帮助类型")
    public ModelAndView deleteHelpType() throws Exception{
        int fid = Integer.parseInt(request.getParameter("uid"));
        ModelAndView modelAndView = new ModelAndView() ;
        List<Fvideo> all = this.fvideoService.findByProperty("fvideotype.fid", fid);
        if(all != null && all.size() >0){
            modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
            modelAndView.addObject("statusCode",300);
            modelAndView.addObject("message","删除失败，该资讯类型已被引用");
            return modelAndView;
        }
        this.videoTypeService.deleteObj(fid);
        modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
        modelAndView.addObject("statusCode",200);
        modelAndView.addObject("message","删除成功");
        return modelAndView;
    }

    /**
     * 列表上移
     * @return
     * @throws Exception
     */
    @RequestMapping("ssadmin/upHelpType")
    @SysLog(code = ModuleConstont.HELP_OPERATION, method = "移动帮助类型")
    public ModelAndView upHelpType() throws Exception {
        return this.upOrDownHelpType("up");
    }

    /**
     * 列表下移
     * @return
     * @throws Exception
     */
    @RequestMapping("ssadmin/downHelpType")
    @SysLog(code = ModuleConstont.HELP_OPERATION, method = "移动帮助类型")
    public ModelAndView downHelpType() throws Exception {
        return this.upOrDownHelpType("down");
    }

    public ModelAndView upOrDownHelpType(String upOrDown) throws Exception {
        ModelAndView modelAndView = new ModelAndView() ;

        //根据id获取类别信息
        String fid = request.getParameter("fid");
        Fadmin admin = (Fadmin) request.getSession()
                .getAttribute("login_admin");
        int temp;
        Fhelptype propertyHelpType = null;
        Fhelptype fhelptype = this.fhelptypeService.findById(Integer.parseInt(fid));
        String dateString = sdf.format(new java.util.Date());
        Timestamp tm = Timestamp.valueOf(dateString);
        fhelptype.setFupdateuser(admin);
        fhelptype.setFupdatetime(tm);
        if("down".equals(upOrDown)){
            propertyHelpType = this.fhelptypeService.getPreviousHelpType(fhelptype.getForder());
        }else{
            propertyHelpType = this.fhelptypeService.getNextHelpType(fhelptype.getForder());
        }
        if(null == propertyHelpType){
            modelAndView.addObject("statusCode",300);
            modelAndView.addObject("message","已无法移动");
        }else{
            temp = fhelptype.getForder();
            fhelptype.setForder(propertyHelpType.getForder());
            propertyHelpType.setForder(temp);

            this.fhelptypeService.updateObj(fhelptype);
            this.fhelptypeService.updateObj(propertyHelpType);

            modelAndView.addObject("statusCode",200);
            modelAndView.addObject("message","移动成功");
        }
        modelAndView.setViewName("ssadmin/comm/ajaxDone");

        return modelAndView;
    }

    @RequestMapping("ssadmin/dingHelptype")
    @SysLog(code = ModuleConstont.HELP_OPERATION, method = "推荐帮助类型")
    public ModelAndView dingArticle() throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("ssadmin/comm/ajaxDone");
        int fid = Integer.parseInt(request.getParameter("uid"));
        Fhelptype fhelptype = this.fhelptypeService.findById(fid);

        if (fhelptype.isFisding()) {
            fhelptype.setFisding(false);
            modelAndView.addObject("message", "取消推荐操作成功");
        } else {

            fhelptype.setFisding(true);
            modelAndView.addObject("message", "设置推荐操作成功");
        }

        this.fhelptypeService.updateObj(fhelptype);
        modelAndView.addObject("statusCode",200);
        return modelAndView;
    }

    @RequestMapping("ssadmin/deleteHelptype")
    @SysLog(code = ModuleConstont.HELP_OPERATION, method = "删除帮助类型")
    public ModelAndView deleteHelptype() throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("ssadmin/comm/ajaxDone");
        int fid = Integer.parseInt(request.getParameter("uid"));

        List<Fhelp> all = this.fhelpService.findByProperty("fhelptype.fid", fid);
        if(all != null && all.size() >0){
            modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
            modelAndView.addObject("statusCode",300);
            modelAndView.addObject("message","删除失败，该类型已被引用");
            return modelAndView;
        }
        this.fhelptypeService.deleteObj(fid);
        modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
        modelAndView.addObject("statusCode",200);
        modelAndView.addObject("message","删除成功");

        return modelAndView;
    }
}
