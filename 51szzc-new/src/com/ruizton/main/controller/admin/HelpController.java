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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zygong on 17-3-16.
 */
@Controller
public class HelpController extends BaseController{

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:sss");

    //每页显示多少条数据
    private int numPerPage = Utils.getNumPerPage();

    @RequestMapping("/ssadmin/helpList")
    @SysLog(code = ModuleConstont.HELP_OPERATION, method = "帮助列表")
    public ModelAndView helpList() throws Exception{
        ModelAndView modelAndView = new ModelAndView() ;
        modelAndView.setViewName("ssadmin/helpList") ;
        //当前页
        int currentPage = 1;
        //搜索关键字
        String keyWord = request.getParameter("keywords");
        String orderField = request.getParameter("orderField");
        String orderDirection = request.getParameter("orderDirection");

        if(request.getParameter("pageNum") != null){
            currentPage = Integer.parseInt(request.getParameter("pageNum"));
        }
        StringBuffer filter = new StringBuffer();
        filter.append("where 1=1 \n");
        if(keyWord != null && keyWord.trim().length() >0){
            filter.append("and ftitle like '%"+keyWord+"%' \n");
            modelAndView.addObject("keywords", keyWord);
        }
        if(request.getParameter("fhelptype") != null){
            int type = Integer.parseInt(request.getParameter("fhelptype"));
            if(type != 0){
                filter.append(" and fhelptype.fid="+ type +"\n");
            }
            modelAndView.addObject("fhelptype", request.getParameter("fhelptype"));
        }
        String beginTimeStr = request.getParameter("beginTimeStr");
        String endTimeStr = request.getParameter("endTimeStr");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = sdf.format(new java.util.Date());
        Timestamp tm = Timestamp.valueOf(dateString);
        if(null != beginTimeStr && !"".equals(beginTimeStr)){
            Timestamp beginTime = Timestamp.valueOf(beginTimeStr + " 00:00:00 ");
            filter.append(" and DATE_FORMAT(fupdatetime,'%Y-%m-%d %H:%i:%S')  > '" + (beginTimeStr + " 00:00:00 ") + "' \n");
            modelAndView.addObject("beginTimeStr", beginTimeStr);
        }
        if(null != endTimeStr && !"".equals(endTimeStr)){
            Timestamp endTime = Timestamp.valueOf(endTimeStr + " 23:59:59 ");
            filter.append(" and DATE_FORMAT(fupdatetime,'%Y-%m-%d %H:%i:%S') < '" + (endTimeStr + " 23:59:59 ") + "' \n");
            modelAndView.addObject("endTimeStr", endTimeStr);
        }

        if(orderField != null && orderField.trim().length() >0){
            filter.append(" order by fhelptype.forder asc,forder asc,"+orderField+"\n");
        }else{
            filter.append(" order by fhelptype.forder asc ,forder asc,fupdatetime \n");
        }

        if(orderDirection != null && orderDirection.trim().length() >0){
            filter.append(orderDirection+"\n");
        }else{
            filter.append(" desc \n");
        }

        // 帮助类型
        Map typeMap = new HashMap();
        typeMap.put(0, "全部");
        List<Fhelptype> all = this.fhelptypeService.findAll();
        for (Fhelptype fhelptype : all) {
            typeMap.put(fhelptype.getFid(), fhelptype.getFname());
        }
        modelAndView.addObject("typeMap", typeMap);
        List<Fhelp> list = this.fhelpService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);
        modelAndView.addObject("helpList", list);
        modelAndView.addObject("numPerPage", numPerPage);
        modelAndView.addObject("currentPage", currentPage);
        modelAndView.addObject("rel", "helpList");
        modelAndView.addObject("totalCount",this.adminService.getAllCount("Fhelp", filter+""));
        return modelAndView ;
    }

    /**
     * 上移
     * @return
     * @throws Exception
     */
    @RequestMapping("ssadmin/upHelp")

    public ModelAndView upHelpType() throws Exception {
        return this.upOrDownHelp("up");
    }

    /**
     * 下移
     * @return
     * @throws Exception
     */
    @RequestMapping("ssadmin/downHelp")

    public ModelAndView downHelpType() throws Exception {
        return this.upOrDownHelp("down");
    }

    public ModelAndView upOrDownHelp(String upOrDown) throws Exception {
        ModelAndView modelAndView = new ModelAndView() ;
        //根据id获取类别信息
        String fid = request.getParameter("fid");
        Fadmin admin = (Fadmin) request.getSession()
                .getAttribute("login_admin");
        int temp;
        Fhelp propertyHelp = null;
        Fhelp fhelp = this.fhelpService.findById(Integer.parseInt(fid));
        String dateString = sdf.format(new java.util.Date());
        Timestamp tm = Timestamp.valueOf(dateString);
        fhelp.setFupdateuser(admin);
        fhelp.setFupdatetime(tm);
        if("down".equals(upOrDown)){
            propertyHelp = this.fhelpService.getNextHelp(fhelp);
        }else{
            propertyHelp = this.fhelpService.getPreviousHelp(fhelp);
        }
        if(null == propertyHelp){
            modelAndView.addObject("statusCode",300);
            modelAndView.addObject("message","已无法移动");
        }else{
            temp = fhelp.getForder();
            fhelp.setForder(propertyHelp.getForder());
            propertyHelp.setForder(temp);
            this.fhelpService.updateObj(fhelp);
            this.fhelpService.updateObj(propertyHelp);
            modelAndView.addObject("statusCode",200);
            modelAndView.addObject("message","移动成功");
        }
        modelAndView.setViewName("ssadmin/comm/ajaxDone");

        return modelAndView;
    }



    @RequestMapping("ssadmin/goHelpJSP")
    public ModelAndView goHelpJSP() throws Exception{
        String url = request.getParameter("url");
        ModelAndView modelAndView = new ModelAndView() ;
        modelAndView.setViewName(url) ;
        if(request.getParameter("uid") != null){
            int fid = Integer.parseInt(request.getParameter("uid"));
            Fhelp fhelp = this.fhelpService.findById(fid);
            modelAndView.addObject("fhelp", fhelp);
        }

        // 帮助类型
        Map typeMap = new HashMap();
        List<Fhelptype> all = this.fhelptypeService.findAll();
        for (Fhelptype fhelptype : all) {
            typeMap.put(fhelptype.getFid(), fhelptype.getFname());
        }
        modelAndView.addObject("typeMap", typeMap);

        return modelAndView;
    }

    @RequestMapping("ssadmin/saveHelp")
    public ModelAndView saveHelp(Fhelp fhelp, @RequestParam(defaultValue = "") String fhelptypeId) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("ssadmin/comm/ajaxDone");;
        modelAndView.addObject("callbackType", "closeCurrent");

        if(!"".equals(fhelptypeId)){
            Fhelptype fhelptype = this.fhelptypeService.findById(Integer.parseInt(fhelptypeId));
            fhelp.setFhelptype(fhelptype);
      //设置forder
          Fhelp last=      fhelpService.getLastHelp();
            if(null != last){
                fhelp.setForder(last.getForder() + 1);
            }else{
                fhelp.setForder(1);
            }

        }else{
            modelAndView.addObject("statusCode", 300);
            modelAndView.addObject("message", "保存失败");
            return modelAndView;
        }

        String dateString = sdf.format(new java.util.Date());
        Timestamp tm = Timestamp.valueOf(dateString);
        // 设在上传用户
        Fadmin admin = (Fadmin) request.getSession().getAttribute("login_admin");
        fhelp.setFcreateuser(admin);
        fhelp.setFupdateuser(admin);
        //设置修改时间
        fhelp.setFcreatetime(tm);
        fhelp.setFupdatetime(tm);

        this.fhelpService.saveObj(fhelp);

        modelAndView.addObject("statusCode", 200);
        modelAndView.addObject("message", "保存成功");
        return modelAndView;
    }

    @RequestMapping("ssadmin/updateHelp")
    @SysLog(code = ModuleConstont.HELP_OPERATION, method = "updateHelp",description = "修改帮助内容")
    public ModelAndView updateHelp(@RequestParam(required = true)String ftitle, @RequestParam(defaultValue = "", required = true) String fhelptypeId, @RequestParam(required = true)String fcontent, @RequestParam(defaultValue = "", required = true)String fid) throws Exception {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("ssadmin/comm/ajaxDone");
        modelAndView.addObject("callbackType", "closeCurrent");

        Fhelp fhelp = null;
        if(!"".equals(fid)){
            fhelp = this.fhelpService.findById(Integer.parseInt(fid));
            fhelp.setFtitle(ftitle);
            fhelp.setFcontent(fcontent);
        }
        if(null == fhelp){
            modelAndView.addObject("statusCode", 300);
            modelAndView.addObject("message", "修改失败");
            return modelAndView;
        }

        if(!"".equals(fhelptypeId)){
            Fhelptype fhelptype = this.fhelptypeService.findById(Integer.parseInt(fhelptypeId));
            fhelp.setFhelptype(fhelptype);
        }

        String dateString = sdf.format(new java.util.Date());
        Timestamp tm = Timestamp.valueOf(dateString);
        // 设在上传用户
        Fadmin admin = (Fadmin) request.getSession().getAttribute("login_admin");
        fhelp.setFupdateuser(admin);
        //设置修改时间
        fhelp.setFupdatetime(tm);

        this.fhelpService.updateObj(fhelp);

        modelAndView.addObject("statusCode", 200);
        modelAndView.addObject("message", "修改成功");
        return modelAndView;
    }

    @RequestMapping("ssadmin/deleteHelp")
    @SysLog(code = ModuleConstont.HELP_OPERATION, method = "deleteHelp",description = "删除帮助内容")
    public ModelAndView deleteHelp() throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("ssadmin/comm/ajaxDone");
        int fid = Integer.parseInt(request.getParameter("uid"));

        this.fhelpService.deleteObj(fid);
        modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
        modelAndView.addObject("statusCode",200);
        modelAndView.addObject("message","删除成功");

        return modelAndView;
    }

    @RequestMapping("ssadmin/dingHelp")
    @SysLog(code = ModuleConstont.HELP_OPERATION, method = "推荐帮助内容")
    public ModelAndView dingHelp() throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("ssadmin/comm/ajaxDone");
        int fid = Integer.parseInt(request.getParameter("uid"));
        Fhelp fhelp = this.fhelpService.findById(fid);

        if (fhelp.isFisding()) {
            fhelp.setFisding(false);
            modelAndView.addObject("message", "取消推荐操作成功");
        } else {

            fhelp.setFisding(true);
            modelAndView.addObject("message", "设置推荐操作成功");
        }

        this.fhelpService.updateObj(fhelp);
        modelAndView.addObject("statusCode",200);
        return modelAndView;
    }
}
