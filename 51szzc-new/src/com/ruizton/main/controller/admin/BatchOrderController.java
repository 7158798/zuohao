package com.ruizton.main.controller.admin;

import com.ruizton.main.Enum.batch.BatchOrderStatusEnum;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.controller.front.response.WebResponse;
import com.ruizton.main.model.*;
import com.ruizton.util.Utils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by lx on 17-3-8.
 */
@Controller
public class BatchOrderController extends BaseController {

    //每页显示多少条数据
    private int numPerPage = Utils.getNumPerPage();

    @RequestMapping("/ssadmin/batchOrderList")
    public ModelAndView batchOrderList() throws Exception{

        ModelAndView modelAndView = new ModelAndView() ;
        modelAndView.setViewName("ssadmin/batchOrderList") ;
        //当前页
        int currentPage = 1;
        //搜索关键字
        if(request.getParameter("pageNum") != null){
            currentPage = Integer.parseInt(request.getParameter("pageNum"));
        }

        StringBuffer filter = new StringBuffer();
        filter.append("where 1=1 \n");
        filter.append("order by id desc \n");

        List<BatchOrder> list = this.batchOrderService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);
        modelAndView.addObject("batchOrderList", list);
        modelAndView.addObject("numPerPage", numPerPage);
        modelAndView.addObject("currentPage", currentPage);
        modelAndView.addObject("rel", "batchOrderList");
        //总数量
        modelAndView.addObject("totalCount",this.adminService.getAllCount("BatchOrder", filter+""));
        return modelAndView ;
    }

    //新增弹出框页面
    @RequestMapping("ssadmin/goBatchOrderJsp")
    public ModelAndView goBatchOrderJsp() throws Exception {
        String url =  request.getParameter("url");
        ModelAndView modelAndView = new ModelAndView(url);
        List<Ftrademapping> ftrademappings =  tradeSetService.findAllTrademapping();
        // 货币类型
        modelAndView.addObject("ftrademappings", ftrademappings);
        return modelAndView;
    }

    //保存交易设置
    @RequestMapping("ssadmin/addBatchOrder")
    public ModelAndView addBatchOrder(BatchOrder req) throws Exception {
        ModelAndView modelAndView = new ModelAndView() ;
        modelAndView.setViewName("ssadmin/comm/ajaxDone");

        //交易货币是否已经配置过
        int vid = Integer.parseInt(request.getParameter("vid"));
        List<Ftrademapping> list = tradeMappingService.findByProperty("fid",vid);
        if (list == null || list.size() != 1){
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
            // 过滤不通过
            return modelAndView;
        }
        req.setStatus(BatchOrderStatusEnum.SUCCESS.getCode());
        WebResponse<Fentrust> response = fastTradeTask.batchOrder(req);
        String msg = response.getMsg();
        if (StringUtils.isNotEmpty(req.getEntrusts())){
            // 部分下单成功了
            this.batchOrderService.save(req);
            msg = msg + "，已成功的订单：" + req.getEntrusts();
            modelAndView.addObject("callbackType","closeCurrent");
        }
        if (response.getResultCode() != 0){
            if (StringUtils.isNotEmpty(req.getEntrusts())){
                // 部分下单成功了
                this.batchOrderService.save(req);
            }
            // 下单发生异常
            modelAndView.addObject("statusCode",300);
            modelAndView.addObject("message", msg);
        } else {
            modelAndView.addObject("statusCode",200);
            modelAndView.addObject("message","下单成功");
        }
        return modelAndView;
    }

    @RequestMapping("ssadmin/cancelBatchOrder")
    public ModelAndView cancelBatchOrder() throws Exception{
        ModelAndView modelAndView = new ModelAndView() ;
        modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
        int fid = Integer.parseInt(request.getParameter("uid"));
        BatchOrder batchOrder = this.batchOrderService.findById(fid);
        if (batchOrder == null){
            modelAndView.addObject("message","批量下单数据不存在");
            modelAndView.addObject("statusCode",300);
        } else {
            this.fastTradeTask.cancelBatchOrder(batchOrder);
            batchOrder.setStatus(BatchOrderStatusEnum.CANCEL.getCode());
            this.batchOrderService.update(batchOrder);
            modelAndView.addObject("message","取消订单成功");
            modelAndView.addObject("statusCode",200);
        }
        return modelAndView;
    }

    @RequestMapping("ssadmin/cancelUserOrder")
    public ModelAndView cancelUserOrder() throws Exception{
        ModelAndView modelAndView = new ModelAndView() ;
        modelAndView.addObject("statusCode",300);
        modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
        String mobilePhone = request.getParameter("mobilePhone");
        List<Fuser> users = userService.findByProperty("ftelephone",mobilePhone);
        if (users.size() == 1){
            Fuser fuser = users.get(0);
            StringBuffer filter = new StringBuffer();
            filter.append("where 1=1 ");
            filter.append("and (fstatus=1 or fstatus = 2)");
            filter.append("and fuser.fid = " + fuser.getFid());
            List<Fentrust> list = entrustService.list(0, 0, filter.toString(), Boolean.FALSE);
            if (list != null){
                fastTradeTask.cancelUserOrder(list,fuser);
                // 更新用户的委托订单状态为取消
                this.batchOrderService.updateStatus(fuser.getFid(),BatchOrderStatusEnum.CANCEL.getCode());
                modelAndView.addObject("statusCode",200);
                modelAndView.addObject("message","取消用户订单成功");
            } else {
                modelAndView.addObject("message","用户不存在委托订单");
            }
        } else {
            modelAndView.addObject("message","手机号码不正确");
        }
        return modelAndView;
    }

    private boolean filter(ModelAndView modelAndView,BatchOrder req){
        boolean flag = false;
        modelAndView.addObject("statusCode",300);
        if (req.getOrderNum() < 1){
            modelAndView.addObject("message","订单数不能小于1");
            return flag;
        }
        if (StringUtils.isBlank(req.getPriceScope())){
            modelAndView.addObject("message","金额区间不能为空");
            return flag;
        }
        if (StringUtils.isBlank(req.getAmountScope())){
            modelAndView.addObject("message","数量区间不能为空");
            return flag;
        }
        if (!req.getPriceScope().matches("^[0-9]*.[0-9]*,[0-9]*.[0-9]*")){
            modelAndView.addObject("message","金额区间格式不正确");
            return flag;
        }
        if (!filter(req.getPriceScope())){
            modelAndView.addObject("message","金额区间开始和结束范围不正确");
            return flag;
        }
        if (!req.getAmountScope().matches("^[0-9]*.[0-9]*,[0-9]*.[0-9]*")){
            modelAndView.addObject("message","数量区间格式不正确");
            return flag;
        }
        if (!filter(req.getAmountScope())){
            modelAndView.addObject("message","数量区间开始和结束范围不正确");
            return flag;
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
        if (Double.parseDouble(strs[0]) >= Double.parseDouble(strs[1])){
            return flag;
        }
        return Boolean.TRUE;
    }

    private void getUser(BatchOrder batchOrder){
        // 挂单用户
        int userId = Integer.parseInt(request.getParameter("userLookup.id"));
        Fuser user = this.userService.findById(userId);
        batchOrder.setUser(user);
    }

    public static void main(String[] args) {
        String str = "3143214234123";
        System.out.println(str.substring(0,str.length() -1));
    }
}
