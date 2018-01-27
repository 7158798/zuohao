package com.otc.facade.virtual.service.console;

import com.otc.facade.base.CountVoEx;
import com.otc.facade.virtual.pojo.po.VirtualRecord;
import com.otc.facade.virtual.pojo.vo.VirtualRecordVo;
import com.otc.facade.virtual.service.VirtualRecordFacade;

import java.util.Date;
import java.util.List;

/**
 * Created by lx on 17-4-17.
 */
public interface VirtualRecordConsoleFacade extends VirtualRecordFacade {

    VirtualRecordVo queryConsoleByConditionPage(VirtualRecordVo vo);

    boolean lockWithdraw(Long id);

    boolean unlockWithdraw(Long id);

    boolean cancelWithdraw(Long id,String reason);

    /**
     * 提现
     * @param recordId
     * @param empId
     * @param confirmPwd
     * @param walletPwd
     * @return
     */
    boolean auditWithdraw(Long recordId,Long empId,String confirmPwd,String walletPwd);

    /**
     * 综合统计
     * @return
     */
    List<CountVoEx> countVitralRecord(String type);

    /**
     * 综合统计
     * @param dayTime   日期
     * @param status    状态
     * @param type      类型
     * @return
     */
    List<CountVoEx> countVitralRecord(Date dayTime, String status, String type);


    VirtualRecord queryById(Long id);
}
