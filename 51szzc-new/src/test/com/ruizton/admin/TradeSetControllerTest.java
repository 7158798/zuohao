package test.com.ruizton.admin;

import com.ruizton.main.controller.admin.TradeSetController;
import com.ruizton.main.model.TradeSet;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import test.com.ruizton.base.BaseSpringTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by luwei on 17-2-20.
 */

public class TradeSetControllerTest extends BaseSpringTest{

    @Autowired
    private TradeSetController tradeSetController;

    public TradeSetControllerTest() {
        setRequest();
    }

    @Override
    public void doTest() {}


    //列表页数据测试
    @Test
    public void tradeSetListTest() throws Exception{
        this.setRequest();
        ModelAndView modelAndView  = tradeSetController.init();
        printData(modelAndView);

        //获取封装的list
        List<TradeSet> list = (List<TradeSet>)modelAndView.getModel().get("tradeSetList");
        for(TradeSet tradeSet : list) {
            //tradeSet.getFtrademapping().getFvirtualcointypeByFvirtualcointype2().getFname()
            //输出值，（含懒加载数据）
            System.out.println(tradeSet.getId() + " "+tradeSet.getMobileNumber() + " " +tradeSet.getFtrademapping().getFvirtualcointypeByFvirtualcointype2().getFname() + " "+ tradeSet.getFuser().getFloginName());
        }
    }


    @Test
    public void addTradeSet() throws Exception {

        TradeSet req = new TradeSet();
        req.setPauseTime(5);
        req.setRatio(new BigDecimal(1));
        req.setUpperLimit(new BigDecimal(10));
        req.setSingleNum(new BigDecimal(0.2));
        req.setMobileNumber("12345678901");
        req.setUnTradeOrderNum(5);

        //模拟get，以url方式传参
        request.setParameter("vid", "2");
        request.setParameter("userLookup.id", "25");
        //覆盖request对象
        tradeSetController.setRequest(request);
        //实际使用时，需要传递request参数，所以controller里面必须有reqest的set方法。
        //基于每个controller都有request，所以建议把request定义到baseController中，并给出get、set方法
        //开放applicationContext.xml中的id="request"的bean对象

        ModelAndView modelAndView = tradeSetController.saveTradeSet(req);
        printData(modelAndView);
    }




    public void printData(ModelAndView modelAndView) {
        //获取封装的数据
        Map<String, Object> map = modelAndView.getModel();
        for(Map.Entry<String, Object> m : map.entrySet()) {
            System.out.println("key=" +m.getKey() + "   value =" + m.getValue());
        }
        System.out.println("-------------------------------虚线后面的异常，可忽略-----------------------------------------------------------------------");
    }


}
