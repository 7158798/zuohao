package com.ruizton.main.controller.admin;

import com.ruizton.main.Enum.ModuleConstont;
import com.ruizton.main.aspect.SysLog;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.model.Fadmin;
import com.ruizton.main.model.Farticletype;
import com.ruizton.main.model.Fvideo;
import com.ruizton.main.model.Fvideotype;
import com.ruizton.util.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by zygong on 17-2-28.
 */
@Controller
public class VideoTypeController  extends BaseController {

    //每页显示多少条数据

    private int numPerPage = Utils.getNumPerPage();

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:sss");

    @RequestMapping("/ssadmin/videoTypeList")
    public ModelAndView Index() throws Exception{
        ModelAndView modelAndView = new ModelAndView() ;
        modelAndView.setViewName("ssadmin/videoTypeList") ;
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
            filter.append("and (fname like '%"+keyWord+"%' or \n");
            filter.append("fdescription like '%"+keyWord+"%' )\n");
            modelAndView.addObject("keywords", keyWord);
        }

        if(orderField != null && orderField.trim().length() >0){
            filter.append("order by "+orderField+"\n");
        }else{
            filter.append("order by fupdatetime \n");
        }

        if(orderDirection != null && orderDirection.trim().length() >0){
            filter.append(orderDirection+"\n");
        }else{
            filter.append("desc \n");
        }
        List<Fvideotype> list = this.videoTypeService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);
        modelAndView.addObject("videoTypeList", list);
        modelAndView.addObject("numPerPage", numPerPage);
        modelAndView.addObject("rel", "videoTypeList");
        modelAndView.addObject("currentPage", currentPage);
        //总数量
        modelAndView.addObject("totalCount",this.adminService.getAllCount("Fvideotype", filter+""));
        return modelAndView ;
    }

    //新增，修改弹出框页面
    @RequestMapping("ssadmin/goVideoTypeJsp")
    public ModelAndView goFvideoTypeJsp() throws Exception {
        String url =  request.getParameter("url");
        ModelAndView modelAndView = new ModelAndView(url);
        if(request.getParameter("fid") != null) {  //修改
            int fid = Integer.parseInt(request.getParameter("fid"));
            Fvideotype fvideotype =  this.videoTypeService.findById(fid);
            modelAndView.addObject("videoType", fvideotype);
        }

       return modelAndView;
    }

    @RequestMapping("ssadmin/saveVideoType")
    public ModelAndView saveVideoType(Fvideotype req){
        ModelAndView modelAndView = new ModelAndView() ;
        modelAndView.setViewName("ssadmin/comm/ajaxDone");

        String dateString = sdf.format(new java.util.Date());
        Timestamp tm = Timestamp.valueOf(dateString);
        // 设在上传用户
        Fadmin admin = (Fadmin) request.getSession()
                .getAttribute("login_admin");
        req.setfCreateUser(admin);
        req.setfUpdateUser(admin);
        //设置修改时间
        req.setfCreateTime(tm);
        req.setfUpdateTime(tm);
        //保存
        this.videoTypeService.save(req);

        modelAndView.addObject("statusCode",200);
        modelAndView.addObject("message","更新成功");
        modelAndView.addObject("callbackType","closeCurrent");
        return modelAndView;
    }

    @RequestMapping("ssadmin/deleteVideoType")
    @SysLog(code = ModuleConstont.ARTICLE_OPERATION, method = "删除视频类型")
    public ModelAndView deleteVideoType() throws Exception{
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
     * 修改咨询类型
     * @return
     * @throws Exception
     */
    @RequestMapping("ssadmin/updateVideoType")
    @SysLog(code = ModuleConstont.ARTICLE_OPERATION, method = "修改视频类型")
    public ModelAndView updateVideoType() throws Exception{
        int fid = Integer.parseInt(request.getParameter("fid"));
        String name = request.getParameter("fName");
        // 设在用户
        Fadmin admin = (Fadmin) request.getSession()
                .getAttribute("login_admin");
        Fvideotype fvideotype = this.videoTypeService.findById(fid);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:sss");
        String dateString = sdf.format(new java.util.Date());
        Timestamp tm = Timestamp.valueOf(dateString);
        fvideotype.setfUpdateTime(tm);
        fvideotype.setfUpdateUser(admin);
        fvideotype.setfName(name);
        fvideotype.setfDescription(request.getParameter("fDescription"));
        this.videoTypeService.updateObj(fvideotype);

        ModelAndView modelAndView = new ModelAndView() ;
        modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
        modelAndView.addObject("statusCode",200);
        modelAndView.addObject("message","更新成功");
        modelAndView.addObject("callbackType","closeCurrent");
        return modelAndView;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }
}
