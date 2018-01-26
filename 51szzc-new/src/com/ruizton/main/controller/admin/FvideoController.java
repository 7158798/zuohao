package com.ruizton.main.controller.admin;

import com.ruizton.main.controller.BaseController;
import com.ruizton.main.model.Fadmin;
import com.ruizton.main.model.Fvideo;
import com.ruizton.main.model.Fvideotype;
import com.ruizton.util.Utils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by luwei on 17-2-28.
 */
@Controller
public class FvideoController  extends BaseController {


    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:sss");

    //每页显示多少条数据
    private int numPerPage = Utils.getNumPerPage();

    //列表页
    @RequestMapping("/ssadmin/videoList")
    public ModelAndView init() throws Exception{
        ModelAndView modelAndView =  new ModelAndView();
        modelAndView.setViewName("ssadmin/videoList");
        //当前页
        int currentPage = 1;
        if(request.getParameter("pageNum") != null) {
            currentPage = Integer.parseInt(request.getParameter("pageNum"));
        }

        //查询条件
        StringBuffer filter = new StringBuffer();
        filter.append("where 1=1 \n");

        //根据标题、上传人模糊查询
        String title = request.getParameter("title");
        if(StringUtils.isNotBlank(title)) {
            filter.append(" and (fTitle like '%" + title + "%' or  fUpdateUser.floginName like '%" + title + "%') ");
        }

        filter.append("order by fPriority \n");
        filter.append("asc \n");

        int firstResult = (currentPage -1) * numPerPage;
        List<Fvideo> list = this.fvideoService.list(firstResult, numPerPage, filter+"", true);
        modelAndView.addObject("fvideoList", list);
        modelAndView.addObject("numPerPage", numPerPage);
        modelAndView.addObject("currentPage", currentPage);

        //总数量
        modelAndView.addObject("totalCount", this.adminService.getAllCount("Fvideo", filter+""));

        modelAndView.addObject("rel", "autoOrderList");
        return modelAndView;
    }


    //新增，修改弹出框页面
    @RequestMapping("ssadmin/goVideoJsp")
    public ModelAndView goFvideoJsp() throws Exception {
        String url =  request.getParameter("url");
        ModelAndView modelAndView = new ModelAndView(url);
        if(request.getParameter("fid") != null) {  //修改
            int fid = Integer.parseInt(request.getParameter("fid"));
            Fvideo Fvideo =  this.fvideoService.findById(fid);
            modelAndView.addObject("fvideo", Fvideo);
        }

        List<Fvideotype> fvideotypes =  fvideoService.findAllVideoType();
        modelAndView.addObject("fvideotypes", fvideotypes);

        return modelAndView;
    }


    /**
     * 新增
     * @param req
     * @return
     * @throws Exception
     */
    @RequestMapping("ssadmin/saveVideo")
    public ModelAndView saveVideo(Fvideo req) throws Exception {
        ModelAndView modelAndView = new ModelAndView() ;
        modelAndView.setViewName("ssadmin/comm/ajaxDone");

        //根据id获取类别信息
        String fvideoType_id = request.getParameter("fvideoType_id");
        if(StringUtils.isBlank(fvideoType_id)) {
            modelAndView.addObject("message","视频类别为空");
            modelAndView.addObject("statusCode",300);
        }

        Fvideotype fvideotype = fvideoService.findVideoTypeById(Integer.valueOf(fvideoType_id));
        if(fvideotype == null) {
            modelAndView.addObject("message","视频类别错误");
            modelAndView.addObject("statusCode",300);
        }

        req.setFvideotype(fvideotype);

        // 获取最新的一条视频
        Fvideo fvideo = this.fvideoService.getLastVideo();

        String dateString = sdf.format(new java.util.Date());
        Timestamp tm = Timestamp.valueOf(dateString);
        // 设置上传用户
        Fadmin admin = (Fadmin) request.getSession()
                .getAttribute("login_admin");
        req.setfCreateUser(admin);
        req.setfUpdateUser(admin);
        //设置修改时间
        req.setfCreateTime(tm);
        req.setfUpdateTime(tm);
        req.setfPriority( fvideo == null ? 1 :  fvideo.getfPriority() + 1);
        //保存
        this.fvideoService.save(req);

        modelAndView.addObject("statusCode",200);
        modelAndView.addObject("message","更新成功");
        modelAndView.addObject("callbackType","closeCurrent");
        return modelAndView;
    }


    /**
     * 修改
     * @param req
     * @return
     * @throws Exception
     */
    @RequestMapping("ssadmin/updateVideo")
    public ModelAndView updateVideo(Fvideo req) throws Exception {
        ModelAndView modelAndView = new ModelAndView() ;
        modelAndView.setViewName("ssadmin/comm/ajaxDone");

        if(req == null || req.getFid() == 0) {
            modelAndView.addObject("message","参数错误");
            modelAndView.addObject("statusCode",300);
        }

        //根据主键查询
        Fvideo vo = fvideoService.findById(req.getFid());
        if(vo == null) {
            modelAndView.addObject("message","查询视频信息为空");
            modelAndView.addObject("statusCode",300);
        }
        //根据id获取类别信息
        String fvideoType_id = request.getParameter("fvideoType_id");
        if(StringUtils.isBlank(fvideoType_id)) {
            modelAndView.addObject("message","视频类别为空");
            modelAndView.addObject("statusCode",300);
        }

        Fvideotype fvideotype = fvideoService.findVideoTypeById(Integer.valueOf(fvideoType_id));
        if(fvideotype == null) {
            modelAndView.addObject("message","视频类别错误");
            modelAndView.addObject("statusCode",300);
        }

        vo.setFvideotype(fvideotype);
        vo.setfPictureUrl(req.getfPictureUrl());
        vo.setfVideoUrl(req.getfVideoUrl());

        String dateString = sdf.format(new java.util.Date());
        Timestamp tm = Timestamp.valueOf(dateString);
        // 设在修改用户
        Fadmin admin = (Fadmin) request.getSession()
                .getAttribute("login_admin");
        vo.setfUpdateUser(admin);
        //设置修改时间
        vo.setfUpdateTime(tm);
        vo.setfTitle(req.getfTitle());
        vo.setfDescription(req.getfDescription());
        //保存
        this.fvideoService.update(vo);

        modelAndView.addObject("statusCode",200);
        modelAndView.addObject("message","更新成功");
        modelAndView.addObject("callbackType","closeCurrent");
        return modelAndView;
    }

    /**
     * 删除
     * @param req
     * @return
     */
    @RequestMapping("ssadmin/deleteVideo")
    public ModelAndView deleteVideo(Fvideo req) {
        ModelAndView modelAndView = new ModelAndView() ;
        modelAndView.setViewName("ssadmin/comm/ajaxDone");

        if(req == null || req.getFid() == 0) {
            modelAndView.addObject("message","参数错误");
            modelAndView.addObject("statusCode",300);
        }

        //根据主键查询
        Fvideo vo = fvideoService.findById(req.getFid());
        if(vo == null) {
            modelAndView.addObject("message","查询视频信息为空");
            modelAndView.addObject("statusCode",300);
        }

        this.fvideoService.delete(vo);

        modelAndView.addObject("statusCode",200);
        modelAndView.addObject("message","删除成功");
        return modelAndView;
    }

    /**
     * 列表上移
     * @return
     * @throws Exception
     */
    @RequestMapping("ssadmin/upVideo")
    public ModelAndView upVideo() throws Exception {
        return this.upOrDownVideo("up");
    }

    /**
     * 列表下移
     * @return
     * @throws Exception
     */
    @RequestMapping("ssadmin/downVideo")
    public ModelAndView downVideo() throws Exception {
        return this.upOrDownVideo("down");
    }

    public ModelAndView upOrDownVideo(String upOrDown) throws Exception {
        ModelAndView modelAndView = new ModelAndView() ;

        //根据id获取类别信息
        String fid = request.getParameter("fid");
        Fadmin admin = (Fadmin) request.getSession()
                .getAttribute("login_admin");
        int temp;
        Fvideo propertyVideo = null;
        Fvideo fvideo = this.fvideoService.findById(Integer.parseInt(fid));
        String dateString = sdf.format(new java.util.Date());
        Timestamp tm = Timestamp.valueOf(dateString);
        fvideo.setfUpdateUser(admin);
        fvideo.setfUpdateTime(tm);
        if("down".equals(upOrDown)){
            propertyVideo = this.fvideoService.getPreviousVideo(fvideo.getfPriority());
        }else{
            propertyVideo = this.fvideoService.getNextVideo(fvideo.getfPriority());
        }
        if(null == propertyVideo){
            modelAndView.addObject("statusCode",300);
            modelAndView.addObject("message","已无法移动");
        }else{
            temp = fvideo.getfPriority();
            fvideo.setfPriority(propertyVideo.getfPriority());
            propertyVideo.setfPriority(temp);

            this.fvideoService.update(propertyVideo);
            this.fvideoService.update(fvideo);

            modelAndView.addObject("statusCode",200);
            modelAndView.addObject("message","移动成功");
        }
        modelAndView.setViewName("ssadmin/comm/ajaxDone");

        return modelAndView;
    }
}
