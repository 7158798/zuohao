package com.ruizton.main.controller.admin;

import com.ruizton.main.auto.TradeTask;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.log.LOG;
import com.ruizton.main.model.AutoOrder;
import com.ruizton.main.model.Ftrademapping;
import com.ruizton.main.model.Fuser;
import com.ruizton.main.model.TradeSet;
import com.ruizton.main.service.admin.AdminService;
import com.ruizton.main.service.admin.TradeMappingService;
import com.ruizton.main.service.admin.TradeSetService;
import com.ruizton.main.service.admin.UserService;
import com.ruizton.util.JsonHelper;
import com.ruizton.util.Utils;
import org.apache.poi.hssf.record.formula.functions.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by luwei on 17-2-14.
 */
@Controller
public class TradeSetController extends BaseController{


    //每页显示多少条数据
    private int numPerPage = Utils.getNumPerPage();

    //列表页
    @RequestMapping("/ssadmin/tradeSetList")
    public ModelAndView init() throws Exception{
        ModelAndView modelAndView =  new ModelAndView();
        modelAndView.setViewName("ssadmin/tradeSetList");
        //当前页
        int currentPage = 1;
        if(request.getParameter("pageNum") != null) {
            currentPage = Integer.parseInt(request.getParameter("pageNum"));
        }

        //查询条件
        StringBuffer filter = new StringBuffer();
        filter.append("where 1=1 \n");
        filter.append("order by id \n");
        filter.append("asc \n");

        int firstResult = (currentPage -1) * numPerPage;
        List<TradeSet> list = this.tradeSetService.list(firstResult, numPerPage, filter+"", true);
        modelAndView.addObject("tradeSetList", list);
        modelAndView.addObject("numPerPage", numPerPage);
        modelAndView.addObject("currentPage", currentPage);

        //总数量
        modelAndView.addObject("totalCount", this.adminService.getAllCount("TradeSet", filter+""));

        modelAndView.addObject("rel", "autoOrderList");
        return modelAndView;
    }


    //新增，修改弹出框页面
    @RequestMapping("ssadmin/goTradeSetJsp")
    public ModelAndView goTradeSetJsp() throws Exception {
        String url =  request.getParameter("url");
        ModelAndView modelAndView = new ModelAndView(url);
        if(request.getParameter("uid") != null) {  //修改
            int fid = Integer.parseInt(request.getParameter("uid"));
            TradeSet tradeSet =  this.tradeSetService.findById(fid);
            modelAndView.addObject("tradeSet", tradeSet);
        }else {
            List<Ftrademapping> ftrademappings =  tradeSetService.findAllTrademapping();
            modelAndView.addObject("ftrademappings", ftrademappings);
        }

        return modelAndView;
    }

    //保存交易设置
    @RequestMapping("ssadmin/saveTradeSet")
    public ModelAndView saveTradeSet(TradeSet req) throws Exception {
        ModelAndView modelAndView = new ModelAndView() ;
        modelAndView.setViewName("ssadmin/comm/ajaxDone");

        //交易货币是否已经配置过
        int vid = Integer.parseInt(request.getParameter("vid"));
        boolean flag = this.tradeSetService.isCoinTypeExists(vid);
        if (flag){
            // 已经配置过了
            modelAndView.addObject("statusCode",300);
            modelAndView.addObject("message","请不要重复配置");
            return modelAndView;
        }
        List<Ftrademapping> list = tradeMappingService.findByProperty("fid",vid);
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
        //保存
        this.tradeSetService.save(req);

        modelAndView.addObject("statusCode",200);
        modelAndView.addObject("message","更新成功");
        modelAndView.addObject("callbackType","closeCurrent");
        return modelAndView;
    }


    //修改交易设置
    @RequestMapping("ssadmin/updateTradeSet")
    public ModelAndView updateTradeSet(TradeSet req) throws Exception {
        ModelAndView modelAndView = new ModelAndView() ;
        modelAndView.setViewName("ssadmin/comm/ajaxDone");
        //根据主键查询
        TradeSet vo = this.tradeSetService.findById(req.getId());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:sss");
        String dateString = sdf.format(new java.util.Date());
        Timestamp tm = Timestamp.valueOf(dateString);
        vo.setModifiedDate(tm);
        getUser(req);
        vo.setFuser(req.getFuser());
        vo.setRatio(req.getRatio());
        vo.setUpperLimit(req.getUpperLimit());
        vo.setMobileNumber(req.getMobileNumber());
        vo.setUnTradeOrderNum(req.getUnTradeOrderNum());
        vo.setPauseTime(req.getPauseTime());
        vo.setSingleNum(req.getSingleNum());
        vo.setPauseMsgTime(req.getPauseMsgTime());
        vo.setMinSingleNum(req.getMinSingleNum());

        this.tradeSetService.update(vo);

        modelAndView.addObject("statusCode",200);
        modelAndView.addObject("message","更新成功");
        modelAndView.addObject("callbackType","closeCurrent");
        return modelAndView;

    }


    @RequestMapping("ssadmin/trade")
    public ModelAndView trade() throws Exception{
        ModelAndView modelAndView = new ModelAndView() ;
        modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
        int fid = Integer.parseInt(request.getParameter("uid"));
        TradeSet tradeSet = this.tradeSetService.findById(fid);
        String status = request.getParameter("status");
        if (tradeSet == null){
            modelAndView.addObject("message","货币配置没有找到");
            modelAndView.addObject("statusCode",300);
        } else {
            if ("1".equals(status)){
                tradeTask.start(tradeSet);
                modelAndView.addObject("message","启动成功");
            } else {
                tradeTask.stop(tradeSet);
                modelAndView.addObject("message","停止成功");
            }
            modelAndView.addObject("statusCode",200);
        }
        return modelAndView;
    }


    private void getUser(TradeSet tradeSet){
        // 挂单用户
        int userId = Integer.parseInt(request.getParameter("userLookup.id"));
        Fuser user = this.userService.findById(userId);
        if (tradeSet.getFuser() != null){
           //修改时，才会出现
            LOG.i(this, "tradeSet.getFuser非空");
        }
        tradeSet.setFuser(user);
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }
}
