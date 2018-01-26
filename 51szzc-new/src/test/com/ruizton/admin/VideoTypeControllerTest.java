package test.com.ruizton.admin;

import com.ruizton.main.controller.admin.VideoTypeController;
import com.ruizton.main.model.Fvideotype;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import test.com.ruizton.base.BaseSpringTest;

import java.util.List;
import java.util.Map;

/**
 * Created by zygong on 17-2-28.
 */
public class VideoTypeControllerTest extends BaseSpringTest{
    @Autowired
    private VideoTypeController videoTypeController;

    public VideoTypeControllerTest() {
        setRequest();
    }

    @Override
    public void doTest() {}


    //列表页数据测试
    @Test
    public void videoTypeListTest() throws Exception{
        this.setRequest();
        videoTypeController.setRequest(request);
        ModelAndView modelAndView  = videoTypeController.Index();
        printData(modelAndView);

        //获取封装的list
        List<Fvideotype> list = (List<Fvideotype>)modelAndView.getModel().get("videoTypeList");
        for(Fvideotype fvideotype : list) {
            //tradeSet.getFtrademapping().getFvirtualcointypeByFvirtualcointype2().getFname()
            //输出值，（含懒加载数据）
            System.out.println(fvideotype.getFid() + " "+fvideotype.getfName() + " " + fvideotype.getfDescription());
        }
    }

    //新增视频类型
    @Test
    public void videoTypeSaveTest() throws Exception{
        Fvideotype req = new Fvideotype();
        req.setfDescription("aaaa");
        req.setfName("bbb");
        this.setRequest();
        videoTypeController.setRequest(request);
        ModelAndView modelAndView  = videoTypeController.saveVideoType(req);
        printData(modelAndView);

        }

    //新增视频类型
    @Test
    public void deleteVideoTypeSaveTest() throws Exception{

        this.setRequest();
        request.setParameter("uid", "7");
        videoTypeController.setRequest(request);
        ModelAndView modelAndView  = videoTypeController.deleteVideoType();
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
