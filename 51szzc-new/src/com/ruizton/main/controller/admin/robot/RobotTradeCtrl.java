package com.ruizton.main.controller.admin.robot;

import com.ruizton.main.Enum.CoinTypeEnum;
import com.ruizton.main.Enum.ModuleConstont;
import com.ruizton.main.Enum.robot.RobotStatusEnum;
import com.ruizton.main.Enum.robot.RobotTypeEnum;
import com.ruizton.main.aspect.SysLog;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.controller.admin.robot.request.RobotTradeReq;
import com.ruizton.main.model.*;
import com.ruizton.main.model.robot.RobotTrade;
import com.ruizton.util.Utils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lx on 17-3-20.
 */
@Controller
public class RobotTradeCtrl extends BaseController {

    //每页显示多少条数据
    private int numPerPage = Utils.getNumPerPage();

    @RequestMapping("/ssadmin/robotTradeList")
    public ModelAndView index() throws Exception{

        ModelAndView modelAndView = new ModelAndView() ;
        modelAndView.setViewName("ssadmin/robot/robotTradeList") ;
        //当前页
        int currentPage = 1;
        if(request.getParameter("pageNum") != null){
            currentPage = Integer.parseInt(request.getParameter("pageNum"));
        }
        StringBuffer filter = new StringBuffer();
        filter.append("where 1=1 \n");
        String status = request.getParameter("status");
        if (StringUtils.isNotEmpty(status) && !"0".equals(status)){
            filter.append("and ftrademapping.fvirtualcointypeByFvirtualcointype2.fid = " + status + " ");
        }
        filter.append("order by id \n");
        filter.append("desc \n");
        List<RobotTrade> list = this.robotTradeService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);
        modelAndView.addObject("robotTradeList", list);
        modelAndView.addObject("numPerPage", numPerPage);
        modelAndView.addObject("rel", "robotTradeList");
        modelAndView.addObject("currentPage", currentPage);
        //总数量
        modelAndView.addObject("totalCount",this.adminService.getAllCount("RobotTrade", filter+""));
        List<Fvirtualcointype> type = this.virtualCoinService.findAll(CoinTypeEnum.COIN_VALUE,0);
        Map<Integer,String> typeMap = new HashMap<Integer,String>();
        for (Fvirtualcointype fvirtualcointype : type) {
            typeMap.put(fvirtualcointype.getFid(), fvirtualcointype.getFname());
        }
        typeMap.put(0, "全部");
        modelAndView.addObject("typeMap", typeMap);
        modelAndView.addObject("status", status);
        return modelAndView ;
    }

    @RequestMapping("ssadmin/goRobotTradeJSP")
    public ModelAndView goRobotTradeJSP() throws Exception{
        String url = request.getParameter("url");
        ModelAndView modelAndView = new ModelAndView() ;
        modelAndView.setViewName(url) ;
        if(request.getParameter("uid") != null){
            int fid = Integer.parseInt(request.getParameter("uid"));
            RobotTrade robotTrade = this.robotTradeService.findById(fid);
            modelAndView.addObject("robotTrade", robotTrade);
        } else {
            List<Ftrademapping> ftrademappings =  tradeSetService.findAllTrademapping();
            modelAndView.addObject("ftrademappings", ftrademappings);
            modelAndView.addObject("typeEnumList", RobotTypeEnum.values());
        }
        return modelAndView;
    }

    @SysLog(code = ModuleConstont.SYSTEM_OPERATION, method = "新建机器交易数据")
    @RequestMapping("ssadmin/saveRobotTrade")
    public ModelAndView saveRobotTrade(RobotTradeReq req) throws Exception{
        ModelAndView modelAndView = new ModelAndView() ;
        modelAndView.setViewName("ssadmin/comm/ajaxDone");
        RobotTrade robotTrade = new RobotTrade();
        // 交易货币是否已经配置过了
        int vid = Integer.parseInt(request.getParameter("vid"));
        Ftrademapping ftrademapping = tradeMappingService.findById(vid);
        if (ftrademapping == null){
            // 已经配置过了
            modelAndView.addObject("statusCode",300);
            modelAndView.addObject("message","货币没有找到");
            return modelAndView;
        }
        robotTrade.setFtrademapping(ftrademapping);
        getUser(robotTrade);
        Date date = new Date();
        robotTrade.setCreateDate(date);
        robotTrade.setModifiedDate(date);
        robotTrade.setDealAmount(BigDecimal.ZERO);
        robotTrade.setStatus(RobotStatusEnum.INIT.getCode());
        robotTrade.setCost(req.getCost());
        robotTrade.setAmount(req.getAmount());
        robotTrade.setType(req.getType());
        // 保存自动挂单配置
        this.robotTradeService.save(robotTrade);
        modelAndView.addObject("statusCode",200);
        modelAndView.addObject("message","更新成功");
        modelAndView.addObject("callbackType","closeCurrent");
        return modelAndView;
    }

    @SysLog(code = ModuleConstont.SYSTEM_OPERATION, method = "修改机器交易数据")
    @RequestMapping("ssadmin/updateRobotTrade")
    public ModelAndView updateRobotTrade(RobotTradeReq req) throws Exception{
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("ssadmin/comm/ajaxDone");
        modelAndView.addObject("statusCode",300);
        RobotTrade robotTrade = this.robotTradeService.findById(req.getId());
        if (StringUtils.equals(robotTrade.getStatus(), RobotStatusEnum.INIT.getCode())){
            getUser(robotTrade);
            robotTrade.setCost(req.getCost());
            robotTrade.setAmount(req.getAmount());
            this.robotTradeService.update(robotTrade);
            modelAndView.addObject("statusCode",200);
            if (!robotTradeTask.isStart(robotTrade.getFtrademapping().getFid())){
                modelAndView.addObject("message","更新成功,重启线程后,修改的数据才会生效!");
            } else {
                modelAndView.addObject("message","更新成功");
            }
            modelAndView.addObject("callbackType","closeCurrent");
        } else {
            modelAndView.addObject("message","交易记录状态不是待处理的状态");
        }
        return modelAndView;
    }

    @SysLog(code = ModuleConstont.SYSTEM_OPERATION, method = "修改机器交易数据")
    @RequestMapping("ssadmin/cancelRobotTrade")
    public ModelAndView cancelRobotTrade() throws Exception{
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("statusCode",300);
        modelAndView.setViewName("ssadmin/comm/ajaxDone");
        int fid = Integer.parseInt(request.getParameter("uid"));
        RobotTrade robotTrade = this.robotTradeService.findById(fid);
        if (robotTrade == null){
            modelAndView.addObject("message","交易配置数据不存在");
            return modelAndView;
        }
        if (StringUtils.equals(robotTrade.getStatus(), RobotStatusEnum.INIT.getCode())){
            if (!robotTradeTask.isStart(robotTrade.getFtrademapping().getFid())){
                modelAndView.addObject("message","交易线程已经启动,请先停止");
                return modelAndView;
            }
            // 可以锁定
            robotTrade.setStatus(RobotStatusEnum.CANCEL.getCode());
            robotTrade.setModifiedDate(new Date());
            this.robotTradeService.update(robotTrade);
            modelAndView.addObject("statusCode",200);
            modelAndView.addObject("message","取消成功");
        } else {
            modelAndView.addObject("message","交易记录状态不正确");
        }
        return modelAndView;
    }

    @SysLog(code = ModuleConstont.SYSTEM_OPERATION, method = "启动/停止机器人线程")
    @RequestMapping("ssadmin/robotTrade")
    public ModelAndView robotTrade() throws Exception{
        ModelAndView modelAndView = new ModelAndView() ;
        modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
        modelAndView.addObject("statusCode",300);
        int fid = Integer.parseInt(request.getParameter("uid"));
        RobotTrade robotTrade = this.robotTradeService.findById(fid);
        String status = request.getParameter("status");
        if (robotTrade == null || robotTrade.getFtrademapping() == null){
            modelAndView.addObject("message","货币交易配置没有找到");
        } else {
            if ("1".equals(status)){
                try {
                    robotTradeTask.start(robotTrade.getFtrademapping(),robotTrade.getType());
                    modelAndView.addObject("statusCode",200);
                    modelAndView.addObject("message","启动成功");
                } catch (RuntimeException ex) {
                    modelAndView.addObject("message",ex.getMessage());
                }
            } else {
                try {
                    robotTradeTask.stop(robotTrade.getFtrademapping(),robotTrade.getType());
                    modelAndView.addObject("statusCode",200);
                    modelAndView.addObject("message","停止成功");
                } catch (RuntimeException ex){
                    modelAndView.addObject("message",ex.getMessage());
                }
            }
        }
        return modelAndView;
    }

    private void getUser(RobotTrade robotTrade){
        // 挂单用户
        int userId = Integer.parseInt(request.getParameter("userLookup.id"));
        Fuser user = this.userService.findById(userId);
        robotTrade.setFuser(user);
    }
}
