package com.ruizton.main.controller.admin;

import com.ruizton.main.Enum.CoinTypeEnum;
import com.ruizton.main.auto.TestAutoTask;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.controller.front.response.WebResponse;
import com.ruizton.main.model.*;
import com.ruizton.main.service.admin.*;
import com.ruizton.main.service.front.FtradeMappingService;
import com.ruizton.util.Utils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by lx on 17-1-24.
 */
@Controller
public class AutoOrderController extends BaseController {

    //每页显示多少条数据
    private int numPerPage = Utils.getNumPerPage();

    @RequestMapping("/ssadmin/autoOrderList")
    public ModelAndView index() throws Exception{

        ModelAndView modelAndView = new ModelAndView() ;
        modelAndView.setViewName("ssadmin/autoOrderList") ;
        //当前页
        int currentPage = 1;
        if(request.getParameter("pageNum") != null){
            currentPage = Integer.parseInt(request.getParameter("pageNum"));
        }
        StringBuffer filter = new StringBuffer();
        filter.append("where 1=1 \n");
        filter.append("order by id \n");
        filter.append("asc \n");
        List<AutoOrder> list = this.autoOrderService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);
        modelAndView.addObject("autoOrderList", list);
        modelAndView.addObject("numPerPage", numPerPage);
        modelAndView.addObject("rel", "virtualCoinTypeList");
        modelAndView.addObject("currentPage", currentPage);
        //总数量
        modelAndView.addObject("totalCount",this.adminService.getAllCount("AutoOrder", filter+""));
        return modelAndView ;
    }

    @RequestMapping("ssadmin/goAutoOrderJSP")
    public ModelAndView goAutoOrderJSP() throws Exception{
        String url = request.getParameter("url");
        ModelAndView modelAndView = new ModelAndView() ;
        modelAndView.setViewName(url) ;
        if(request.getParameter("uid") != null){
            int fid = Integer.parseInt(request.getParameter("uid"));
            AutoOrder autoOrder = this.autoOrderService.findByIdAll(fid);
            modelAndView.addObject("autoOrder", autoOrder);
        } else {
            List<Fvirtualcointype> allType = this.virtualCoinService.findAll(CoinTypeEnum.FB_CNY_VALUE,1);
            modelAndView.addObject("allType", allType);
        }
        return modelAndView;
    }

    @RequestMapping("ssadmin/updateAutoOrder")
    public ModelAndView updateAutoOrder(AutoOrder req) throws Exception{
        ModelAndView modelAndView = new ModelAndView() ;
        modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
        AutoOrder autoOrder = this.autoOrderService.findById(req.getId());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:sss");
        String dateString = sdf.format(new java.util.Date());
        Timestamp tm = Timestamp.valueOf(dateString);
        autoOrder.setRandom(req.getRandom());
        autoOrder.setRatio(req.getRatio());
        autoOrder.setCancelTime(req.getCancelTime());
        autoOrder.setMobilePhone(req.getMobilePhone());
        // 设置买/卖单用户
        getUser(autoOrder);
        autoOrder.setSleepTime(req.getSleepTime());
        autoOrder.setMaxOrder(req.getMaxOrder());
        autoOrder.setAllCny(req.getAllCny());
        autoOrder.setAllXnb(req.getAllXnb());
        autoOrder.setReqSleepTime(req.getReqSleepTime());
        autoOrder.setReleaseTime(req.getReleaseTime());
        autoOrder.setModifiedDate(tm);
        autoOrder.setUrls(req.getUrls());
        autoOrder.setToggleTime(req.getToggleTime());
        autoOrder.setAdjustPrice(req.getAdjustPrice());
        autoOrder.setPauseMsgTime(req.getPauseMsgTime());
        autoOrder.setCostPrice(req.getCostPrice());
        autoOrder.setPriceScope(req.getPriceScope());
        if (!filter(modelAndView,req)){
            return modelAndView;
        }
        // 保存自动挂单配置
        this.autoOrderService.updateObj(autoOrder);
        modelAndView.addObject("statusCode",200);
        modelAndView.addObject("message","更新成功");
        modelAndView.addObject("callbackType","closeCurrent");
        return modelAndView;
    }

    private boolean filter(ModelAndView modelAndView,AutoOrder req){
        boolean flag = false;
        modelAndView.addObject("statusCode",300);
        if (StringUtils.isNotEmpty(req.getPriceScope())){
            if (!req.getPriceScope().matches("^[0-9]*,[0-9]*")){
                modelAndView.addObject("message","金额区间格式不正确");
                return flag;
            }
            if (!filter(req.getPriceScope())){
                modelAndView.addObject("message","金额区间开始和结束范围不正确");
                return flag;
            }
        }
        return Boolean.TRUE;
    }

    /**
     * 校验业务数据
     * @param value
     * @return
     */
    private boolean filter(String value){
        boolean flag = Boolean.FALSE;
        String[] strs = value.split(",");
        if (strs.length != 2){
            return flag;
        }
        if (Integer.parseInt(strs[0]) >= Integer.parseInt(strs[1])){
            return flag;
        }
        return Boolean.TRUE;
    }

    private void getUser(AutoOrder autoOrder){
        // 挂单用户
        int userId = Integer.parseInt(request.getParameter("userLookup.id"));
        Fuser user = this.userService.findById(userId);
        if (autoOrder.getUser() != null){
            if (autoOrder.getUser().getFid() != userId){
                // 切换了当前挂单用户，则需要清空挂单金额
                autoOrder.setRmbFrozen(null);
                autoOrder.setXnbFrozen(null);
            }
        }
        autoOrder.setUser(user);
        // 备选用户
        userId = Integer.parseInt(request.getParameter("reserveUserLookup.id"));
        user = this.userService.findById(userId);
        autoOrder.setReserveUser(user);
    }

    @RequestMapping("ssadmin/saveAutoOrder")
    public ModelAndView saveAutoOrder(AutoOrder req) throws Exception{
        ModelAndView modelAndView = new ModelAndView() ;
        modelAndView.setViewName("ssadmin/comm/ajaxDone");
        // 交易货币是否已经配置过了
        int vid = Integer.parseInt(request.getParameter("vid"));
        boolean flag = this.autoOrderService.isCoinTypeExists(vid);
        if (flag){
            // 已经配置过了
            modelAndView.addObject("statusCode",300);
            modelAndView.addObject("message","请不要重复配置");
            return modelAndView;
        }
        List<Ftrademapping> list = tradeMappingService.findByProperty("fvirtualcointypeByFvirtualcointype2.fid",vid);
        if (list == null || list.size() != 1){
            // 已经配置过了
            modelAndView.addObject("statusCode",300);
            modelAndView.addObject("message","货币没有找到");
            return modelAndView;
        }
        req.setFtrademapping(list.get(0));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:sss");
        String dateString = sdf.format(new java.util.Date());
        Timestamp tm = Timestamp.valueOf(dateString);
        // 设置买/卖单用户
        getUser(req);
        req.setCreateDate(tm);
        req.setModifiedDate(tm);
        if (!filter(modelAndView,req)){
            return modelAndView;
        }
        // 保存自动挂单配置
        this.autoOrderService.saveObj(req);
        modelAndView.addObject("statusCode",200);
        modelAndView.addObject("message","更新成功");
        modelAndView.addObject("callbackType","closeCurrent");
        return modelAndView;
    }

    @RequestMapping("ssadmin/autoOrder")
    public ModelAndView autoOrder() throws Exception{
        ModelAndView modelAndView = new ModelAndView() ;
        modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
        int fid = Integer.parseInt(request.getParameter("uid"));
        AutoOrder autoOrder = this.autoOrderService.findByIdAll(fid);
        String status = request.getParameter("status");
        if (autoOrder == null){
            modelAndView.addObject("message","货币没有找到");
            modelAndView.addObject("statusCode",300);
        } else {
            if ("1".equals(status)){
                testAutoTask.start(autoOrder);
                modelAndView.addObject("message","启动成功");
            } else {
                testAutoTask.stop(autoOrder);
                modelAndView.addObject("message","停止成功");
            }
            modelAndView.addObject("statusCode",200);
        }
        return modelAndView;
    }
}
