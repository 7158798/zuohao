package com.ruizton.main.controller.admin;

import com.ruizton.main.Enum.IntegralTypeEnum;
import com.ruizton.main.Enum.IntegralactivityStatusEnum;
import com.ruizton.main.Enum.ModuleConstont;
import com.ruizton.main.aspect.SysLog;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.model.Fadmin;
import com.ruizton.main.model.Farticletype;
import com.ruizton.main.model.integral.Fintegralactivity;
import com.ruizton.util.KeyAndValue;
import com.ruizton.util.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zygong on 17-3-2.
 */
@Controller
public class FintegralactivityController extends BaseController {
    //每页显示多少条数据
    private int numPerPage = Utils.getNumPerPage();

    @RequestMapping("/ssadmin/integralactivityList")
    public ModelAndView Index() throws Exception{
        ModelAndView modelAndView = new ModelAndView() ;
        modelAndView.setViewName("ssadmin/integralactivityList");
        //当前页
        int currentPage = 1;
        String orderField = request.getParameter("orderField");
        String orderDirection = request.getParameter("orderDirection");
        if(request.getParameter("pageNum") != null){
            currentPage = Integer.parseInt(request.getParameter("pageNum"));
        }
        StringBuffer filter = new StringBuffer();

        filter.append(" where status != 0 ");

        if(orderField != null && orderField.trim().length() >0){
            filter.append(" order by "+orderField+"\n");
        }else{
            filter.append(" order by createTime \n");
        }

        if(orderDirection != null && orderDirection.trim().length() >0){
            filter.append(orderDirection+"\n");
        }else{
            filter.append("desc \n");
        }
        List<Fintegralactivity> list = this.fintegralactivityService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);
        modelAndView.addObject("integralactivityList", list);
        modelAndView.addObject("numPerPage", numPerPage);
        modelAndView.addObject("rel", "integralactivityList");
        modelAndView.addObject("currentPage", currentPage);
        //总数量
        modelAndView.addObject("totalCount",this.adminService.getAllCount("Fintegralactivity", filter+""));
        return modelAndView ;
    }

    @RequestMapping("ssadmin/goIntegralactivityJSP")
    public ModelAndView goIntegralactivityJSP() throws Exception{
        String url = request.getParameter("url");
        ModelAndView modelAndView = new ModelAndView() ;
        modelAndView.setViewName(url) ;
        List<KeyAndValue> typeNameList = new ArrayList<KeyAndValue>();
        KeyAndValue typeName = null;

        // 获取类型
        int item = 0;
        for(Map.Entry<Integer, String> entry : IntegralTypeEnum.getMap().entrySet()){
            typeName = new KeyAndValue();
            typeName.setKey(entry.getKey());
            typeName.setValue(entry.getValue());
            typeNameList.add(typeName);
            item++;
            if(item == 7){
                break;
            }
        }
        modelAndView.addObject("typeNameList", typeNameList);

        if(request.getParameter("uid") != null){
            int fid = Integer.parseInt(request.getParameter("uid"));
            Fintegralactivity integralactivity = this.fintegralactivityService.findById(fid);
            modelAndView.addObject("integralactivity", integralactivity);
        }
        return modelAndView;
    }

    /**
     * 新增积分获取途径
     * @return
     * @throws Exception
     */
    @RequestMapping("ssadmin/saveIntegralactivity")
    public ModelAndView saveIntegralactivity(HttpServletRequest request, Fintegralactivity integralactivity) throws Exception{
        String beginTimeStr = request.getParameter("beginTimeStr");
        String endTimeStr = request.getParameter("endTimeStr");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:sss");
        String dateString = sdf.format(new java.util.Date());
        Timestamp tm = Timestamp.valueOf(dateString);
        Timestamp beginTime = Timestamp.valueOf(beginTimeStr + " 00:00:00");
        Timestamp endTime = Timestamp.valueOf(endTimeStr + " 00:00:00");
        integralactivity.setCreateTime(tm);
        integralactivity.setUpdateTime(tm);
        integralactivity.setBeginTime(beginTime);
        integralactivity.setEndTime(endTime);
        // 如果当前时间小于开始时间，状态为待进行
        if(tm.getTime() < beginTime.getTime()){
            integralactivity.setStatus("1");
        }else{
            integralactivity.setStatus("2");
        }
        this.fintegralactivityService.saveObj(integralactivity);

        ModelAndView modelAndView = new ModelAndView() ;
        modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
        modelAndView.addObject("statusCode",200);
        modelAndView.addObject("message","新增成功");
        modelAndView.addObject("callbackType","closeCurrent");
        return modelAndView;
    }

    /**
     * 更新积分获取途径
     * @return
     * @throws Exception
     */
    @RequestMapping("ssadmin/updateIntegralactivity")
    public ModelAndView updateIntegralactivity(HttpServletRequest request, Fintegralactivity integralactivity) throws Exception{
        String beginTimeStr = request.getParameter("beginTimeStr");
        String endTimeStr = request.getParameter("endTimeStr");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:sss");
        String dateString = sdf.format(new java.util.Date());
        Timestamp tm = Timestamp.valueOf(dateString);
        Timestamp beginTime = Timestamp.valueOf(beginTimeStr + " 00:00:00");
        Timestamp endTime = Timestamp.valueOf(endTimeStr + " 00:00:00");
        integralactivity.setUpdateTime(tm);
        integralactivity.setBeginTime(beginTime);
        integralactivity.setEndTime(endTime);
        // 如果当前时间小于开始时间，状态为待进行
        if(tm.getTime() < beginTime.getTime()){
            integralactivity.setStatus("1");
        }else{
            integralactivity.setStatus("2");
        }
        Fintegralactivity byId = this.fintegralactivityService.findById(integralactivity.getId());
        integralactivity.setCreateTime(byId.getCreateTime());
        this.fintegralactivityService.updateObj(integralactivity);

        ModelAndView modelAndView = new ModelAndView() ;
        modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
        modelAndView.addObject("statusCode",200);
        modelAndView.addObject("message","修改成功");
        modelAndView.addObject("callbackType","closeCurrent");
        return modelAndView;
    }

    /**
     * 删除积分获取途径
     * @return
     * @throws Exception
     */
    @RequestMapping("ssadmin/deleteIntegralactivity")
    public ModelAndView deleteIntegralactivity() throws Exception{
        Fintegralactivity integralactivity = null;
        ModelAndView modelAndView = new ModelAndView() ;
        String fid = request.getParameter("uid");
        if(null != fid && !"".equals(fid)){
            integralactivity = fintegralactivityService.findById(Integer.parseInt(fid));
        }else{
            modelAndView.addObject("statusCode",300);
            modelAndView.addObject("message","操作失败");
        }

        if(integralactivity != null){
            integralactivity.setStatus(IntegralactivityStatusEnum.getValueByName("已删除").getValue());
            fintegralactivityService.updateObj(integralactivity);
        }else{
            modelAndView.addObject("statusCode",300);
            modelAndView.addObject("message","操作失败");
        }

        modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
        modelAndView.addObject("statusCode",200);
        modelAndView.addObject("message","删除成功");
        return modelAndView;
    }

    /**
     * 启动和暂停积分获取途径
     * @return
     * @throws Exception
     */
    @RequestMapping("ssadmin/startOrShutDown")
    public ModelAndView startOrShutDown() throws Exception{
        Fintegralactivity integralactivity = null;
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("ssadmin/comm/ajaxDone");
        String fid = request.getParameter("uid");
        String startOrShutDown = request.getParameter("startOrShutDown");
        if(null != fid && !"".equals(fid) && null != startOrShutDown && !"".equals(startOrShutDown)){
            integralactivity = fintegralactivityService.findById(Integer.parseInt(fid));

            Timestamp beginTime = integralactivity.getBeginTime();
            Timestamp endTime = integralactivity.getEndTime();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            String dateString = sdf.format(new java.util.Date());
            Timestamp tm = Timestamp.valueOf(dateString + " 00:00:00");
            if(beginTime.getTime() <= tm.getTime() && endTime.getTime() >= tm.getTime()){
                if(startOrShutDown.equals("shutdown") && IntegralactivityStatusEnum.getValueByName("已进行").getValue().equals(integralactivity.getStatus())){
                    integralactivity.setStatus(IntegralactivityStatusEnum.getValueByName("已暂停").getValue());
                    integralactivity.setUpdateTime(tm);
                    fintegralactivityService.updateObj(integralactivity);
                }else if(startOrShutDown.equals("start") && IntegralactivityStatusEnum.getValueByName("已暂停").getValue().equals(integralactivity.getStatus())){
                    integralactivity.setStatus(IntegralactivityStatusEnum.getValueByName("已进行").getValue());
                    integralactivity.setUpdateTime(tm);
                    fintegralactivityService.updateObj(integralactivity);
                }else{
                    modelAndView.addObject("statusCode",300);
                    modelAndView.addObject("message","该条数据无法进行此操作");
                    return modelAndView;
                }

            }

        }else{
            modelAndView.addObject("statusCode",300);
            modelAndView.addObject("message","操作失败");
        }
        modelAndView.addObject("statusCode",200);
        modelAndView.addObject("message","操作成功");
        return modelAndView;
    }

}
