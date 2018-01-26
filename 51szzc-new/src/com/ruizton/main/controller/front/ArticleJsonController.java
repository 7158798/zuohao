package com.ruizton.main.controller.front;

import com.ruizton.main.Enum.FarticleStatusEnum;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.log.LOG;
import com.ruizton.main.model.*;
import com.ruizton.util.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.record.formula.functions.Mode;
import org.aspectj.apache.bcel.classfile.Code;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 从web网站录入的资讯
 * Created by luwei on 17-3-30.
 */
@Controller
public class ArticleJsonController extends BaseController {

    //每页显示多少条数据
    private int numPerPage = Utils.getNumPerPage();

    /**
     * 添加、修改资讯
     * @param furl  小封面
     * @param ftitle  标题
     * @param articleLookupid  资讯类型
     * @param tagName   标签
     * @param fcontent  内容
     * @param forigin   来源
     * @return
     * 新增完成之后，跳转到列表页，显示已添加的资讯信息
     */
    @RequestMapping(value = "/external/user/addArticleByWeb", produces = {JsonEncode})
    @ResponseBody
    public String addArticleByWeb(@RequestParam(required = false) String furl,
                                  @RequestParam(required = true) String ftitle,
                                  @RequestParam(required = true) int articleLookupid,
                                  @RequestParam(required = false) String tagName,
                                  @RequestParam(required = true) String fcontent,
                                  @RequestParam(required = true) int fstatus,
                                  @RequestParam(required = false) String forigin,
                                  @RequestParam(required = false) Integer fid) {
        JSONObject jsonObject = new JSONObject();

        Fuser fuser = GetSecondLoginSession(request);
        if(fuser == null) {
            jsonObject.accumulate(CODE, -1);
            jsonObject.accumulate(MSG, i18nMsg("s_user_not_login"));
            return jsonObject.toString();
        }

        Farticle article = new Farticle();
        if(fid != null) {
            article = this.articleService.findById(fid);
        }

        Farticletype articletype = this.articleTypeService.findById(articleLookupid);
        article.setFarticletype(articletype);
        article.setFtitle(ftitle);
        article.setFstatus(fstatus);  //有待审核、草稿2种状态
        if(null != tagName && !"".equals(tagName)){
            String[] arrTagNames = tagName.split(",");
            if(arrTagNames.length > 3){
                jsonObject.accumulate(CODE, -1);
                jsonObject.accumulate(MSG, i18nMsg("tag_choose"));
                return jsonObject.toString();
            }
            article.setFtag(tagName);
        }else{
            article.setFtag("");
        }
        article.setFisout(false);  //web网站添加，不能添加外部的
        article.setFcontent(fcontent);
        article.setForigin(forigin);
        article.setFlastModifyDate(Utils.getTimestamp());
        article.setFcreateDate(Utils.getTimestamp());
        Fadmin admin = new Fadmin();
        admin.setFid(1);  //为避免异常，给默认值
        article.setFadminByFcreateAdmin(admin);
        article.setFadminByFmodifyAdmin(admin);

        Fuser new_fuser = new Fuser();
        new_fuser.setFid(1);
        article.setFuser(new_fuser);

        if(StringUtils.isNotBlank(furl)) {
            article.setFurl(furl);
        }

        //保存
        if(fid != null) {
            this.articleService.updateObj(article);
        }else{
            this.articleService.saveObj(article);
        }

        jsonObject.accumulate(CODE,200);
        jsonObject.accumulate(MSG, i18nMsg("submit_success"));

        return jsonObject.toString();
    }

    /**
     * 用户添加的资讯列表
     * @return
     */
    @RequestMapping(value = "/external/user/articleListByWeb" , produces = {JsonEncode})
    public ModelAndView articleListByWeb(@RequestParam(required = false) Integer currentPage) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/front/article/articleListByUser");

        Fuser fuser = GetSecondLoginSession(request);
        if(fuser == null) {
            modelAndView.addObject(CODE, -1);
            modelAndView.addObject(MSG, i18nMsg("s_user_not_login"));
            return modelAndView;
        }

        if(currentPage == null) {
            currentPage = 1;
        }

        //查询该用户已添加资讯(分页)
        StringBuffer filter = new StringBuffer();
        filter.append(" where 1=1 ");
        filter.append(" and fstatus in (0,1,2,3) ");
//        filter.append(" and fuser.fid = " + fuser.getFid());
        filter.append(" order by fstatus asc, flastModifyDate desc");

        List<Farticle> list = this.articleService.list((currentPage - 1) * numPerPage, numPerPage, filter + "", true);
        modelAndView.addObject("articleList", list);

        int totalCount = this.adminService.getAllCount("Farticle", filter.toString());

        String url = "/external/user/articleListByWeb.html?";
        String pagin = PaginUtil.generatePagin(totalCount / numPerPage + ((totalCount % numPerPage) == 0 ? 0 : 1), currentPage, url);

        modelAndView.addObject("pagin", pagin);
        modelAndView.addObject("currentPage_page", currentPage);
        modelAndView.addObject("totalCount", totalCount);
        return modelAndView;
    }


    /**
     * 用户资讯详情
     * @return
     */
    @RequestMapping(value = "/external/user/articleDetailByWeb" , produces = {JsonEncode})
    @ResponseBody
    public String articleDetailByWeb(@RequestParam(required = true) Integer fid, @RequestParam(required = false, defaultValue = "false") boolean isEdit) {
        JSONObject jsonObject = new JSONObject();

        Fuser fuser = GetSecondLoginSession(request);
        if(fuser == null) {
            jsonObject.accumulate(CODE, -1);
            jsonObject.accumulate(MSG, i18nMsg("s_user_not_login"));
            return jsonObject.toString();
        }

        if(fid == null) {
            jsonObject.accumulate(CODE, -1);
            jsonObject.accumulate(MSG, i18nMsg("param_execption"));
            return jsonObject.toString();
        }

        Farticle farticle = this.articleService.findById(fid);

        if(farticle == null) {
            jsonObject.accumulate(CODE, -1);
            jsonObject.accumulate(MSG, i18nMsg("s_network_anomaly"));
            return jsonObject.toString();
        }

        jsonObject.accumulate(CODE, 0);

        jsonObject.accumulate("fid", farticle.getFid());
        jsonObject.accumulate("content", farticle.getFcontent());
        jsonObject.accumulate("origin", farticle.getForigin());
        jsonObject.accumulate("title", farticle.getFtitle());
        jsonObject.accumulate("title_short", farticle.getFtitle_short());
        jsonObject.accumulate("article_type_id", farticle.getFarticletype().getFid());
        jsonObject.accumulate("imgurl", farticle.getFurl());
        jsonObject.accumulate("pushtime", DateHelper.date2String(farticle.getFlastModifyDate(), DateHelper.DateFormatType.YearMonthDay_HourMinuteSecond));
        Date lastModifyDate = farticle.getFlastModifyDate();
        //标签拆分
        if(StringUtils.isNotBlank(farticle.getFtag())) {
            String[] tag = farticle.getFtag().split(",");
            jsonObject.accumulate("tag", tag);
        }else{
            jsonObject.accumulate("tag", new String[0]);
        }

        //不是修改，而是查询详情，则不查类型
        if(!isEdit) {
            return jsonObject.toString();
        }


        //查询资讯类型
        JSONArray jsonArray = new JSONArray();
        //为避免不兼容
        List<Farticletype> farticletypeList = this.articleTypeService.findAll();
        if(farticletypeList == null || farticletypeList.size() == 0) {
            return null;
        }

        for(Farticletype farticletype : farticletypeList) {
            if(farticletype.getFname().indexOf("公告") != -1 || farticletype.getFname().indexOf("新闻") != -1) {
                continue;
            }
            JSONObject js = new JSONObject();
            js.accumulate("fid", farticletype.getFid());
            js.accumulate("fname", farticletype.getFname());
            jsonArray.add(js);
        }

        jsonObject.accumulate("articleTypeList", jsonArray);

        return jsonObject.toString();
    }


    /**
     * 删除资讯
     * @param fid
     * @return
     */
    @RequestMapping(value = "/external/user/deleteArticleByWeb" , produces = {JsonEncode})
    @ResponseBody
    public String deleteArticleByWeb(@RequestParam(required = true) Integer fid) {
        JSONObject jsonObject = new JSONObject();

        Fuser fuser = GetSecondLoginSession(request);
        if(fuser == null) {
            jsonObject.accumulate(CODE, -1);
            jsonObject.accumulate(MSG, i18nMsg("s_user_not_login"));
            return jsonObject.toString();
        }

        if(fid == null) {
            jsonObject.accumulate(CODE, -1);
            jsonObject.accumulate(MSG, i18nMsg("param_execption"));
            return jsonObject.toString();
        }


        this.articleService.deleteObj(fid);
        jsonObject.accumulate(CODE, 0);
        jsonObject.accumulate(MSG, i18nMsg("s_operation_success"));

        return jsonObject.toString();
    }


    @RequestMapping("/external/user/articleTagLookup")
    @ResponseBody
    public String articleTagLookup() {
        JSONObject jsonObject = new JSONObject();
        try {
            //搜索关键字
            String keyWord = request.getParameter("keywords");

            StringBuffer filter = new StringBuffer();
            filter.append("where 1=1 \n");
            if(keyWord != null && keyWord.trim().length() >0){
                filter.append("and (fname like '%"+keyWord+"%' )\n");
                jsonObject.accumulate("keywords", keyWord);
            }
            List<Ftag> list = this.ftagService.list(0, 0, filter.toString(), false);
            jsonObject.accumulate("articleTag", list);
            jsonObject.accumulate(CODE, 0);
        }catch (Exception e) {
            e.printStackTrace();
            LOG.e(ArticleJsonController.class, e.getMessage());
            jsonObject.accumulate(CODE, -1);
            jsonObject.accumulate(MSG, "网络繁忙");
        }
        return jsonObject.toString();
    }


    @RequestMapping("/external/user/initAddArticle")
    public ModelAndView initAddArticle() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/front/article/addArticleByWeb");
        //查询资讯类型
        List<Farticletype> list = new ArrayList<>();
        //为避免不兼容
        List<Farticletype> farticletypeList = this.articleTypeService.findAll();
        if(farticletypeList == null || farticletypeList.size() == 0) {
            return null;
        }

        for(Farticletype farticletype : farticletypeList) {
            if(farticletype.getFname().indexOf("公告") != -1 || farticletype.getFname().indexOf("新闻") != -1) {
                continue;
            }
            list.add(farticletype);
        }

        modelAndView.addObject("articleTypeList", list);
        return modelAndView;
    }


}
