package com.ruizton.main.quartz;

import com.ruizton.main.auto.TestAutoTask;
import com.ruizton.main.auto.order.User;
import com.ruizton.main.model.AutoOrder;
import com.ruizton.main.model.Fuser;
import com.ruizton.main.service.admin.AutoOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Map;

/**
 * 零点切换挂单用户
 * Created by lx on 17-2-6.
 */
public class AutoOrderQuartz {

    private static final Logger logger = LoggerFactory.getLogger(AutoOrderQuartz.class);
    @Autowired
    private TestAutoTask testAutoTask;
    @Autowired
    private AutoOrderService autoOrderService;

    public void work(){
        logger.info("启动切换挂单用户开始");
        Map<Integer,TestAutoTask.OrderThread> map = testAutoTask.getThreadMap();
        for (Map.Entry<Integer,TestAutoTask.OrderThread> entry : map.entrySet()){
            final Integer id = entry.getKey();
            new Thread(new Runnable() {

                public void run() {
                    logger.info("切换数据开始id=" + id);
                    AutoOrder autoOrder = autoOrderService.findByIdAll(id);
                    // 停止线程
                    testAutoTask.stop(autoOrder);
                    // 更新用户数据
                    Fuser user = autoOrder.getUser();
                    autoOrder.setUser(autoOrder.getReserveUser());
                    autoOrder.setReserveUser(user);
                    // 更新数据
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:sss");
                    String dateString = sdf.format(new java.util.Date());
                    Timestamp tm = Timestamp.valueOf(dateString);
                    autoOrder.setModifiedDate(tm);
                    // 清空冻结数量/金额
                    autoOrder.setRmbFrozen(null);
                    autoOrder.setXnbFrozen(null);
                    autoOrderService.updateObj(autoOrder);
                    // 启动线程开始
                    testAutoTask.start(autoOrder);
                    logger.info("切换数据结束id=" + id);

                }
            }).start() ;
        }
        logger.info("启动切换挂单用户结束");
    }
}
