package com.ruizton.main.controller.admin.robot;

import com.ruizton.main.Enum.CoinTypeEnum;
import com.ruizton.main.Enum.ModuleConstont;
import com.ruizton.main.Enum.robot.RobotStatusEnum;
import com.ruizton.main.Enum.robot.RobotTypeEnum;
import com.ruizton.main.aspect.SysLog;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.controller.admin.robot.request.EntrustWarnReq;
import com.ruizton.main.controller.admin.robot.request.RobotTradeReq;
import com.ruizton.main.model.Ftrademapping;
import com.ruizton.main.model.Fuser;
import com.ruizton.main.model.Fvirtualcointype;
import com.ruizton.main.model.robot.EntrustWarn;
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
public class EntrustWarnCtrl extends BaseController {

    //每页显示多少条数据
    private int numPerPage = Utils.getNumPerPage();

    @RequestMapping("/ssadmin/entrustWarnList")
    public ModelAndView entrustWarnList() throws Exception{

        ModelAndView modelAndView = new ModelAndView() ;
        modelAndView.setViewName("ssadmin/warn/entrustWarnList") ;
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
        List<EntrustWarn> list = this.entrustWarnService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);
        modelAndView.addObject("entrustWarnList", list);
        modelAndView.addObject("numPerPage", numPerPage);
        modelAndView.addObject("rel", "entrustWarnList");
        modelAndView.addObject("currentPage", currentPage);
        //总数量
        modelAndView.addObject("totalCount",this.adminService.getAllCount("EntrustWarn", filter+""));
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

    @RequestMapping("ssadmin/goEntrustWarnJSP")
    public ModelAndView goEntrustWarnJSP() throws Exception{
        String url = request.getParameter("url");
        ModelAndView modelAndView = new ModelAndView() ;
        modelAndView.setViewName(url) ;
        if(request.getParameter("uid") != null){
            int fid = Integer.parseInt(request.getParameter("uid"));
            EntrustWarn entrustWarn = this.entrustWarnService.findById(fid);
            modelAndView.addObject("entrustWarn", entrustWarn);
        } else {
            List<Ftrademapping> ftrademappings =  tradeSetService.findAllTrademapping();
            modelAndView.addObject("ftrademappings", ftrademappings);
            modelAndView.addObject("typeEnumList", RobotTypeEnum.values());
        }
        return modelAndView;
    }

    @SysLog(code = ModuleConstont.SYSTEM_OPERATION, method = "新建委托预警")
    @RequestMapping("ssadmin/saveEntrustWarn")
    public ModelAndView saveEntrustWarn(EntrustWarnReq req) throws Exception{
        ModelAndView modelAndView = new ModelAndView() ;
        modelAndView.setViewName("ssadmin/comm/ajaxDone");
        EntrustWarn entrustWarn = new EntrustWarn();
        int vid = Integer.parseInt(request.getParameter("vid"));
        Ftrademapping ftrademapping = tradeMappingService.findById(vid);
        if (ftrademapping == null){
            // 已经配置过了
            modelAndView.addObject("statusCode",300);
            modelAndView.addObject("message","货币没有找到");
            return modelAndView;
        }
        if (entrustWarnService.isExist(ftrademapping,req.getType())){
            // 已经配置过了
            modelAndView.addObject("statusCode",300);
            String name = ftrademapping.getFvirtualcointypeByFvirtualcointype2().getFname();
            modelAndView.addObject("message",name + "的类型已经配置过了,不需要重复配置");
            return modelAndView;
        }
        entrustWarn.setFtrademapping(ftrademapping);
        Date date = new Date();
        entrustWarn.setCreateDate(date);
        entrustWarn.setModifiedDate(date);
        entrustWarn.setType(req.getType());
        entrustWarn.setMergeAmount(req.getMergeAmount());
        entrustWarn.setSingleAmount(req.getSingleAmount());
        entrustWarn.setWaitMinute(req.getWaitMinute());
        entrustWarn.setMobileNumber(req.getMobileNumber());
        entrustWarn.setPauseMsgTime(req.getPauseMsgTime());
        // 保存自动挂单配置
        this.entrustWarnService.save(entrustWarn);
        modelAndView.addObject("statusCode",200);
        modelAndView.addObject("message","更新成功");
        modelAndView.addObject("callbackType","closeCurrent");
        return modelAndView;
    }

    @SysLog(code = ModuleConstont.SYSTEM_OPERATION, method = "修改预警信息")
    @RequestMapping("ssadmin/updateEntrustWarn")
    public ModelAndView updateEntrustWarn(EntrustWarnReq req) throws Exception{
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("ssadmin/comm/ajaxDone");
        modelAndView.addObject("statusCode",300);
        EntrustWarn entrustWarn = this.entrustWarnService.findById(req.getId());
        if (entrustWarn != null){
            entrustWarn.setWaitMinute(req.getWaitMinute());
            entrustWarn.setMergeAmount(req.getMergeAmount());
            entrustWarn.setSingleAmount(req.getSingleAmount());
            entrustWarn.setMobileNumber(req.getMobileNumber());
            entrustWarn.setPauseMsgTime(req.getPauseMsgTime());
            this.entrustWarnService.update(entrustWarn);
            modelAndView.addObject("statusCode",200);
            if (!entrustWarnTask.isStart(entrustWarn.getFtrademapping().getFid())){
                modelAndView.addObject("message","更新成功,重启线程后,修改的数据才会生效!");
            } else {
                modelAndView.addObject("message","更新成功");
            }
            modelAndView.addObject("callbackType","closeCurrent");
        } else {
            modelAndView.addObject("message","预警数据不存在");
        }
        return modelAndView;
    }

    @SysLog(code = ModuleConstont.SYSTEM_OPERATION, method = "启动/停止预警线程")
    @RequestMapping("ssadmin/entrustWarn")
    public ModelAndView entrustWarn() throws Exception{
        ModelAndView modelAndView = new ModelAndView() ;
        modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
        modelAndView.addObject("statusCode",300);
        int fid = Integer.parseInt(request.getParameter("uid"));
        EntrustWarn entrustWarn = this.entrustWarnService.findById(fid);
        String status = request.getParameter("status");
        if (entrustWarn == null || entrustWarn.getFtrademapping() == null){
            modelAndView.addObject("message","预警配置没有找到");
        } else {
            if ("1".equals(status)){
                try {
                    entrustWarnTask.start(entrustWarn.getFtrademapping(),entrustWarn);
                    modelAndView.addObject("statusCode",200);
                    modelAndView.addObject("message","启动成功");
                } catch (RuntimeException ex) {
                    modelAndView.addObject("message",ex.getMessage());
                }
            } else {
                try {
                    entrustWarnTask.stop(entrustWarn.getFtrademapping(),entrustWarn.getType());
                    modelAndView.addObject("statusCode",200);
                    modelAndView.addObject("message","停止成功");
                } catch (RuntimeException ex){
                    modelAndView.addObject("message",ex.getMessage());
                }
            }
        }
        return modelAndView;
    }

}
