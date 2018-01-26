package com.ruizton.main.controller.admin;

import com.ruizton.main.Enum.FileUploadTypeEnum;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.log.LOG;
import com.ruizton.main.model.Fadmin;
import com.ruizton.main.model.Ftag;
import com.ruizton.main.model.Ftagmanage;
import com.ruizton.util.Constant;
import com.ruizton.util.OSSPostObject;
import com.ruizton.util.Utils;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zygong on 17-3-6.
 */
@Controller
public class TagManagementController extends BaseController{

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:sss");
    //每页显示多少条数据
    private int numPerPage = Utils.getNumPerPage();

    @RequestMapping("/ssadmin/tagManagementView")
    public ModelAndView tegManagementList(){
        ModelAndView mv = new ModelAndView();

        List<Ftagmanage> all = this.ftagManageService.findAll();

        mv.setViewName("ssadmin/tagManagementView");
        mv.addObject("tagManage", all);
        mv.addObject("totalCount", all.size());
        mv.addObject("rel", "tagManagementView");

        return mv;
    }

    @ResponseBody
    @RequestMapping("/ssadmin/saveTagManagement")
    public String saveTagManagement(HttpServletRequest request, MultipartFile file, String code) throws Exception{
        ModelAndView mv = new ModelAndView();
        JSONObject js = new JSONObject();
        Ftagmanage ftagmanage = null;
        Ftag ftag = null;
        int item = 0;
        List<Ftag> ftagList = new ArrayList<Ftag>();
        List<String> tagList = new ArrayList<String>();;
        String line = "";

        if(null != file){
            String name = file.getOriginalFilename();
            String[] split = name.split("\\.");
            if(!"txt".equals(split[split.length-1].toLowerCase())){
                js.accumulate("code", 1);
                js.accumulate("message", "请上传txt文件");
                return js.toString();
            }
        }

        //上传阿里
        String url = uploadAli(request, file, code);
        if("".equals(url)){
            js.accumulate("code", 1);
            return js.toString();
        }

        String savePath = request.getSession().getServletContext().getRealPath("/")
                + "/upload/" + file.getOriginalFilename();
        File saveFile = new File(savePath);
        file.transferTo(saveFile);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:sss");
        String dateString = sdf.format(new java.util.Date());
        Timestamp tm = Timestamp.valueOf(dateString);
        Fadmin admin = (Fadmin)request.getSession().getAttribute("login_admin");

        InputStreamReader reader = new InputStreamReader(file.getInputStream(), Utils.getFileEncode(new File(savePath)));
        Utils.clearFiles(savePath);
        BufferedReader br = new BufferedReader(reader);
        while((line = br.readLine()) != null){
            if(item == 0){
                ftagmanage = new Ftagmanage();
                ftagmanage.setFname(file.getOriginalFilename());
                ftagmanage.setFcreateuser(admin);
                ftagmanage.setFcreatetime(tm);
                ftagmanage.setFupdateuser(admin);
                ftagmanage.setFlastupdatetime(tm);
                ftagmanage.setFurl(url);
            }
            ftag = new Ftag();
            ftag.setFname(line);
            ftagList.add(ftag);
            item++;

            // 将标签放到内存里面
            tagList.add(line);
        }
        if(ftagList.size() > 0){
            this.ftagService.saveList(ftagList);

            this.ftagManageService.deleteAll();

            this.ftagManageService.save(ftagmanage);

            // 将标签放到内存里面
            Constant.setTagList(tagList);

            js.accumulate("code", 0);
        }else {
            js.accumulate("code", 1);
        }
        return js.toString();
    }

    public String uploadAli(HttpServletRequest request, MultipartFile file, String code) throws Exception{
        JSONObject js = new JSONObject();
        String resultUrl = "";  //绝对路径
        if (file != null && !file.isEmpty()) {
            InputStream inputStream = file.getInputStream();
            String fileRealName = file.getOriginalFilename();
            if (fileRealName != null && fileRealName.trim().length() > 0) {
                String[] nameSplit = fileRealName.split("\\.");
                String ext = nameSplit[nameSplit.length - 1];

                String realPath = request.getSession().getServletContext().getRealPath("/") + FileUploadTypeEnum.getObjectByCode(code).getFolderName();
                String fileName = Utils.getRandomImageName()+"."+ext;
                LOG.i(this, "common upload file name：" + fileName);
//                boolean flag = Utils.saveFile(realPath, fileName, inputStream);
                boolean flag = Utils.saveFile(fileName, inputStream);
                LOG.i(this, "common upload file result：" + flag);
                if (flag) {
                    resultUrl = OSSPostObject.URL + "/" + fileName;
                    js.accumulate("resultUrl", resultUrl);
                    js.accumulate("code", 0);
                }else{
                    js.accumulate("code", 1);
                }
            }
        }
        return resultUrl;
    }

    @RequestMapping("/ssadmin/articleTagLookup")
    public ModelAndView articleTagLookup() throws Exception{
        ModelAndView modelAndView = new ModelAndView() ;
        modelAndView.setViewName("ssadmin/articleTagLookup") ;
        //当前页
        int currentPage = 1;
        //搜索关键字
        String keyWord = request.getParameter("keywords");

        if(request.getParameter("pageNum") != null){
            currentPage = Integer.parseInt(request.getParameter("pageNum"));
        }
        StringBuffer filter = new StringBuffer();
        filter.append("where 1=1 \n");
        if(keyWord != null && keyWord.trim().length() >0){
            filter.append("and (fname like '%"+keyWord+"%' )\n");
            modelAndView.addObject("keywords", keyWord);
        }
        List<Ftag> list = this.ftagService.findAll();
        modelAndView.addObject("articleTag", list);
        modelAndView.addObject("rel", "articleTagLookup");
        //总数量
        modelAndView.addObject("totalCount",list.size());
        return modelAndView ;
    }
}
