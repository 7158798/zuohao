package com.ruizton.main.controller.admin;

import com.opensymphony.oscache.util.StringUtil;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.controller.front.response.WebResponse;
import com.ruizton.main.log.LOG;
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
 * Created by lx on 17-2-28.
 */
@Controller
public class FastTradeController extends BaseController {

    //每页显示多少条数据
    private int numPerPage = Utils.getNumPerPage();


    @RequestMapping("/ssadmin/fastTradeList")
    public ModelAndView fastTradeList() throws Exception{

        ModelAndView modelAndView = new ModelAndView() ;
        modelAndView.setViewName("ssadmin/fastTradeList") ;
        //当前页
        int currentPage = 1;
        //搜索关键字
        if(request.getParameter("pageNum") != null){
            currentPage = Integer.parseInt(request.getParameter("pageNum"));
        }

        StringBuffer filter = new StringBuffer();
        filter.append("where 1=1 \n");

        List<FastTrade> list = this.fastTradeService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);
        modelAndView.addObject("fastTradeList", list);
        modelAndView.addObject("numPerPage", numPerPage);
        modelAndView.addObject("currentPage", currentPage);
        modelAndView.addObject("rel", "fastTradeList");
        //总数量
        modelAndView.addObject("totalCount",this.adminService.getAllCount("FastTrade", filter+""));
        return modelAndView ;
    }

    //新增，修改弹出框页面
    @RequestMapping("ssadmin/goFastTradeJsp")
    public ModelAndView goFastTradeJsp() throws Exception {
        String url =  request.getParameter("url");
        ModelAndView modelAndView = new ModelAndView(url);
        if(request.getParameter("uid") != null) {  //修改
            int fid = Integer.parseInt(request.getParameter("uid"));
            FastTrade fastTrade =  this.fastTradeService.findById(fid);
            modelAndView.addObject("fastTrade", fastTrade);
        }else {
            List<Ftrademapping> ftrademappings =  tradeSetService.findAllTrademapping();
            modelAndView.addObject("ftrademappings", ftrademappings);
        }
        return modelAndView;
    }

    //保存交易设置
    @RequestMapping("ssadmin/addFastTrade")
    public ModelAndView addFastTrade(FastTrade req) throws Exception {
        ModelAndView modelAndView = new ModelAndView() ;
        modelAndView.setViewName("ssadmin/comm/ajaxDone");

        //交易货币是否已经配置过
        int vid = Integer.parseInt(request.getParameter("vid"));
        boolean flag = this.fastTradeService.isCoinTypeExists(vid);
        if (flag){
            // 已经配置过了
            modelAndView.addObject("statusCode",300);
            modelAndView.addObject("message","请不要重复配置");
            return modelAndView;
        }
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
            return modelAndView;
        }
        //保存
        this.fastTradeService.save(req);

        modelAndView.addObject("statusCode",200);
        modelAndView.addObject("message","更新成功");
        modelAndView.addObject("callbackType","closeCurrent");
        return modelAndView;
    }

    //修改交易设置
    @RequestMapping("ssadmin/updateFastTrade")
    public ModelAndView updateFastTrade(FastTrade req) throws Exception {
        ModelAndView modelAndView = new ModelAndView() ;
        modelAndView.setViewName("ssadmin/comm/ajaxDone");
        //根据主键查询
        FastTrade vo = this.fastTradeService.findById(req.getId());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:sss");
        String dateString = sdf.format(new java.util.Date());
        Timestamp tm = Timestamp.valueOf(dateString);
        vo.setModifiedDate(tm);
        getUser(req);
        vo.setUser(req.getUser());
        vo.setRandomNum(req.getRandomNum());
        vo.setRandomTime(req.getRandomTime());
        if (!filter(modelAndView,vo)){
            return modelAndView;
        }
        this.fastTradeService.update(vo);

        modelAndView.addObject("statusCode",200);
        modelAndView.addObject("message","更新成功");
        modelAndView.addObject("callbackType","closeCurrent");
        return modelAndView;

    }

    private boolean filter(ModelAndView modelAndView,FastTrade req){
        boolean flag = false;
        modelAndView.addObject("statusCode",300);
        if (StringUtils.isBlank(req.getRandomNum())){
            modelAndView.addObject("message","随机数量不能为空");
            return flag;
        }
        if (StringUtils.isBlank(req.getRandomTime())){
            modelAndView.addObject("message","休眠时间不能为空");
            return flag;
        }
        if (!req.getRandomNum().matches("^[0-9]*,[0-9]*")){
            modelAndView.addObject("message","随机数量格式不正确");
            return flag;
        }
        if (!filter(req.getRandomNum())){
            modelAndView.addObject("message","随机数量开始和结束范围不正确");
            return flag;
        }
        if (!req.getRandomTime().matches("^[0-9]*,[0-9]*")){
            modelAndView.addObject("message","休眠时间格式不正确");
            return flag;
        }
        if (!filter(req.getRandomTime())){
            modelAndView.addObject("message","休眠时间开始和结束范围不正确");
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
        if (Integer.parseInt(strs[0]) >= Integer.parseInt(strs[1])){
            return flag;
        }
        return Boolean.TRUE;
    }

    private void getUser(FastTrade tradeSet){
        // 挂单用户
        int userId = Integer.parseInt(request.getParameter("userLookup.id"));
        Fuser user = this.userService.findById(userId);
        /*if (tradeSet.getFuser() != null){
            //修改时，才会出现
            LOG.i(this, "tradeSet.getFuser非空");
        }*/
        tradeSet.setUser(user);
    }

    @RequestMapping("ssadmin/addAmount")
    public ModelAndView addAmount() throws Exception{
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("statusCode",300);
        modelAndView.setViewName("ssadmin/comm/ajaxDone");
        int fid = Integer.parseInt(request.getParameter("uid"));
        FastTrade fastTrade = this.fastTradeService.findById(fid);
        String mobile = request.getParameter("mobile");
        if (StringUtils.isBlank(mobile)){
            modelAndView.addObject("message","手机号码不能为空");
            return modelAndView;
        }
        List<Fuser> list = userService.findByProperty("ftelephone",mobile);
        if (list.size() == 1){
            String amount = request.getParameter("amount");
            if (StringUtils.isBlank(amount)){
                modelAndView.addObject("message","数量不能为空");
                return modelAndView;
            }
            String price = request.getParameter("price");
            if (StringUtils.isBlank(price)){
                modelAndView.addObject("message","单价不能为空");
                return modelAndView;
            }
            String skipPair = request.getParameter("skipPair");
            Boolean inside = "1".equals(skipPair)?Boolean.TRUE:Boolean.FALSE;

            WebResponse<Fentrust> response = testAutoTask.amount(fastTrade.getFtrademapping(), list.get(0).getFid(), Double.valueOf(amount), Double.valueOf(price),inside);
            modelAndView.addObject("statusCode",response.getResultCode() == 0?200:300);
            modelAndView.addObject("message",response.getMsg());
        } else {
            modelAndView.addObject("message","手机号码不正确");
        }
        return modelAndView;
    }

    @RequestMapping("ssadmin/autoAddAmount")
    public ModelAndView autoAddAmount() throws Exception{
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("statusCode",300);
        modelAndView.setViewName("ssadmin/comm/ajaxDone");
        int fid = Integer.parseInt(request.getParameter("uid"));
        FastTrade fastTrade = this.fastTradeService.findById(fid);
        String status = request.getParameter("status");
        if ("1".equals(status)){
            fastTradeTask.start(fastTrade);
            modelAndView.addObject("message","线程启动成功");
            modelAndView.addObject("statusCode",200);
        } else {
            fastTradeTask.stop(fastTrade);
            modelAndView.addObject("statusCode",200);
            modelAndView.addObject("message","线程启动成功");
        }
        return modelAndView;
    }

    public static void main(String[] args) {

        System.out.println("0,988888s".matches("^[0-9]*,[0-9]*"));
    }
}
