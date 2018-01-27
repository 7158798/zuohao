package com.base.service.job;

import com.jucaifu.common.log.LOG;
import com.jucaifu.common.property.PropertiesUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 定时任务常量
 * Created by lx on 16-12-27.
 */
public interface JobConstant {

    String  JOB_SWITCH = PropertiesUtils.getProperty("base.job.switch");
    // 关闭定时任务
    String JOB_CLOSE = "close";

    /**
     * 获取定时任务是否关闭
     * @return
     */
    default boolean isJobClose(){
        boolean flag = Boolean.FALSE;
        if (StringUtils.isNotEmpty(JOB_SWITCH)){
            if (JOB_CLOSE.equals(JOB_SWITCH)){
                LOG.d(this,"定时任务已经关闭,不处理");
                flag = Boolean.TRUE;
            }
        }
        return flag;
    }
}
