package com.base.service.integral.biz;


import com.base.facade.exception.BaseCommonBizException;
import com.jucaifu.common.log.LOG;
import com.jucaifu.core.biz.AbsBaseBiz;
import com.base.facade.integral.enums.IntegralType;
import com.base.facade.integral.pojo.po.IntegralTask;
import com.base.facade.integral.pojo.vo.IntegralTaskVo;
import com.base.service.integral.dao.IntegralTaskMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by yong on 16-4-29.
 */
@Service
public class IntegralTaskBiz extends AbsBaseBiz<IntegralTask, IntegralTaskVo, IntegralTaskMapper> {

    @Autowired
    private IntegralTaskMapper integralTaskMapper;

    /**
     * 添加首次送积分项目   == 未使用
     *
     * @param taskNo
     * @param point
     * @param active
     * @param userId
     */
    public void addIntegralTask(String taskNo, BigDecimal point, BigDecimal maxPoint,Boolean active, Long userId) {

        if (StringUtils.isBlank(taskNo)) {
            throw new BaseCommonBizException("请求参数积分任务编号为空");
        }
        if (point == null) {
            throw new BaseCommonBizException("请求积分数为空");
        }
        if (active == null) {
            throw new BaseCommonBizException("请求参数是否启用为空");
        }
        if (userId == null) {
            throw new BaseCommonBizException("请求参数操作人id为空");
        }
        IntegralTask select = integralTaskMapper.selectByTaskNo(taskNo);
        if (select != null) {
            throw new BaseCommonBizException("该送积分项目已经添加过");
        }

        IntegralTask integralTask = new IntegralTask();
        integralTask.setActive(active);
        integralTask.setTaskNo(taskNo);
        integralTask.setTaskName(IntegralType.getDescByCode(taskNo));
        integralTask.setIntegralAccount(point);
        integralTask.setCreateUserId(userId);
        integralTask.setModifiedUserId(userId);
        integralTask.setDeleted(Boolean.FALSE);
        integralTask.setDailyLimit(maxPoint);
        Date now = new Date();
        integralTask.setCreateDate(now);
        integralTask.setModifiedDate(now);
        this.insert(integralTask);
    }

    /**
     * 修改首次送积分项目  == 未使用
     *
     * @param id
     * @param point
     * @param active
     * @param userId
     */
    public void updateIntegralTask(Long id, BigDecimal point, BigDecimal maxPoint,Boolean active, Long userId) {

        if (id == null) {
            throw new BaseCommonBizException("请求参数积分任务uuid为空");
        }
        if (point == null) {
            throw new BaseCommonBizException("请求积分数为空");
        }
        if (active == null) {
            throw new BaseCommonBizException("请求参数是否启用为空");
        }
        if (userId == null) {
            throw new BaseCommonBizException("请求参数操作人id为空");
        }
        IntegralTask integralTask = integralTaskMapper.select(id);
        integralTask.setIntegralAccount(point);
        integralTask.setActive(active);
        integralTask.setModifiedDate(new Date());
        integralTask.setModifiedUserId(userId);
        integralTask.setDailyLimit(maxPoint);
        integralTaskMapper.update(integralTask);
    }

    /**
     * 开启或关闭送积分项目   === 该功能未开启
     *
     * @param uuid
     * @param userId
     */
    public void switchActive(String uuid, Long userId) {

        if (StringUtils.isBlank(uuid)) {
            throw new BaseCommonBizException("请求参数积分任务uuid为空");
        }
        if (userId == null) {
            throw new BaseCommonBizException("请求参数操作人id为空");
        }
        IntegralTask integralTask = integralTaskMapper.select(uuid);
        Boolean active = integralTask.getActive();
        if (active) {
            integralTask.setActive(Boolean.FALSE);
        } else {
            integralTask.setActive(Boolean.TRUE);
        }
        integralTask.setModifiedDate(new Date());
        integralTask.setModifiedUserId(userId);
        integralTaskMapper.update(integralTask);
    }

    /**
     * 获取积分列表
     *
     * @return
     */
    public List<IntegralTask> getAllIntegralTasks() {
        return integralTaskMapper.selectAll();
    }

    /**
     * 新增或修改积分设置
     * @param tasks
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateOrAddTask(List<IntegralTask> tasks){
        LOG.d(this,"开始设置积分");
        if(tasks == null){
            throw new BaseCommonBizException("请求参数号为空");
        }
        IntegralTask integralTask;
        Date now = new Date();
        for(IntegralTask task:tasks){
            String taskNo = task.getTaskNo();
            integralTask = this.getIntegralTaskByTaskNo(taskNo);
            BigDecimal point = task.getIntegralAccount() == null?BigDecimal.ZERO:task.getIntegralAccount();
            BigDecimal dailyLimit = task.getDailyLimit();
            if(point.compareTo(BigDecimal.ZERO) < 0){
                throw new BaseCommonBizException("积分设置不能为负数");
            }

            if(dailyLimit != null){
                if(dailyLimit.compareTo(BigDecimal.ZERO)<0){
                    throw new BaseCommonBizException("积分上限不能为负数");
                }
                if(dailyLimit.compareTo(point)<0){
                    throw new BaseCommonBizException("积分设置不能大于上限数");
                }
            }

            Long employeeid = task.getCreateUserId();

            if(integralTask == null){
                integralTask = new IntegralTask();
                integralTask.setActive(true);
                integralTask.setTaskNo(taskNo);
                integralTask.setTaskName(IntegralType.getDescByCode(taskNo));
                integralTask.setIntegralAccount(point);
                integralTask.setCreateUserId(employeeid);
                integralTask.setModifiedUserId(employeeid);
                integralTask.setDeleted(Boolean.FALSE);
                integralTask.setDailyLimit(dailyLimit);
                integralTask.setCreateDate(now);
                integralTask.setModifiedDate(now);
                integralTaskMapper.insert(integralTask);
            }else{
                integralTask.setIntegralAccount(point);
                integralTask.setActive(true);
                integralTask.setModifiedDate(new Date());
                integralTask.setModifiedUserId(employeeid);
                integralTask.setDailyLimit(dailyLimit);
                integralTaskMapper.update(integralTask);
            }
        }

        LOG.d(this,"积分设置完成");

    }

    /**
     * 查询指定的积分任务
     *
     * @param taskNo
     * @return
     */
    public IntegralTask getIntegralTaskByTaskNo(String taskNo) {
        if (StringUtils.isBlank(taskNo)) {
            throw new BaseCommonBizException("请求参数积分任务编号为空");
        }
        IntegralTask select = integralTaskMapper.selectByTaskNo(taskNo);
        if (select == null) {
            LOG.dTag(this, "未查询到指定的积分任务");
        }
        return select;
    }

    @Override
    public IntegralTaskMapper getDefaultMapper() {
        return integralTaskMapper;
    }

//    public List<IntegralTaskEx> getUserIntegralTasks(String userId) throws Exception {
//
////        List<String> unShowTask = new ArrayList<>();
//////        unShowTask.add(IntegralType.SIGN_IN_1_DAY.getTaskNo());
////        unShowTask.add(IntegralType.SIGN_IN_7_DAY.getTaskNo());
////        unShowTask.add(IntegralType.SIGN_IN_15_DAY.getTaskNo());
////        unShowTask.add(IntegralType.SIGN_IN_30_DAY.getTaskNo());
//        List<String> unShowTask = IntegralType.getSignInActivity();
//        List<IntegralTask> integralTasks = integralTaskMapper.selectAll();
//        List<IntegralTaskEx> result = new ArrayList<>();
//        IntegralTaskEx integralTaskEx;
//        for (IntegralTask integralTask : integralTasks) {
//            Boolean active = integralTask.getActive();
//            if (active) {
//                integralTaskEx = new IntegralTaskEx();
//                String taskNo = integralTask.getTaskNo();
//
//                UserExt userExtByUserId = SysBizPool.getInstance().userConsoleFacade.getUserExtByUserId(userId, taskNo);
//                if (userExtByUserId != null) {
//                    String extValue = userExtByUserId.getExtValue();
//                    boolean hasReceiveFlag = Boolean.parseBoolean(extValue);
//                    if (hasReceiveFlag) {
//                        integralTaskEx.setTaskStatus("3");
//                        //每日分享的任务判断，如果不是同一天，显示任务列表2
//                        if(StringUtils.equals(taskNo,IntegralType.SHARE_EVERYDAY.getCode())){
//                            Date modifiedDate = userExtByUserId.getModifiedDate();
//                            boolean sameDay = DateUtils.isSameDay(modifiedDate, new Date());
//                            if(!sameDay){
//                                integralTaskEx.setTaskStatus("1");
//                            }
//                        }
//                    } else {
//                        integralTaskEx.setTaskStatus("2");
//                    }
//                } else {
//                    boolean contains = unShowTask.contains(integralTask.getTaskNo());
//                    if (contains) {
//                        continue;
//                    }
//                    integralTaskEx.setTaskStatus("1");
//
//                    if (StringUtils.isNoneBlank(userId)) {
//                        if (StringUtils.equals(taskNo, IntegralType.AUTHENTICATION_BANKCARD.getCode())) {
//                            UserAuthentication authentication = SysBizPool.getInstance().userConsoleFacade.getUserAuthenticationById(userId);
//                            if (authentication != null) {
//                                integralTaskEx.setTaskStatus("2");
//                            }
//                        }
//                        if (StringUtils.equals(taskNo, IntegralType.FINISH_RISK_ANSWER.getCode())) {
//                            UserRiskScore riskScore = SysBizPool.getInstance().userRiskAssessmentWebFacade.getRiskScore(userId);
//                            if (riskScore != null) {
//                                integralTaskEx.setTaskStatus("2");
//                            }
//                        }
//                        if (StringUtils.equals(taskNo, IntegralType.SET_USER_AVATAR.getCode())) {
//                            User user = SysBizPool.getInstance().userConsoleFacade.getUserById(userId);
//                            String avatar = user.getAvatar();
//                            if (StringUtils.isNoneBlank(avatar)) {
//                                integralTaskEx.setTaskStatus("2");
//                            }
//                        }
//                        if (StringUtils.equals(taskNo, IntegralType.SET_PAY_PASSWORD.getCode())) {
//                            UserPayPassword userPayPassword = SysBizPool.getInstance().userConsoleFacade.getUserPayPasswordById(userId);
//                            if (userPayPassword != null) {
//                                integralTaskEx.setTaskStatus("2");
//                            }
//                        }
//                    }
//                }
//                ReflectHelper.injectAttrFromSrcObj(integralTask, integralTaskEx);
//                if(StringUtils.equals(taskNo,IntegralType.FIRST_BUY_PRODUCT_REFER.getCode())){
//                    //首投送积分返回1
//                    integralTaskEx.setTaskStatus("1");
//                }
//                result.add(integralTaskEx);
//            }
//        }
//        return result;
//    }

    public List<IntegralTask> getActivitySignInTask(List<String> signInActivity) {
        return integralTaskMapper.getActivitySignInTask(signInActivity);
    }

    /**
     * 获取所有的积分任务（未登陆情况下）
     *
     * @return
     */
//    public List<IntegralTaskEx> selectAllIntegralTasks() throws Exception {
//        List<IntegralTask> allIntegralTasks = this.getAllIntegralTasks();
//        List<String> signInActivity = IntegralType.getSignInActivity();
//
//        List<IntegralTaskEx> resultList = new ArrayList<>();
//        IntegralTaskEx resp;
//        for (IntegralTask integralTask : allIntegralTasks) {
//            String taskNo = integralTask.getTaskNo();
//            if (signInActivity.contains(taskNo)) {
//                continue;
//            }
//            Boolean active = integralTask.getActive();
//            if (active) {
//                resp = new IntegralTaskEx();
//                resp.setTaskStatus("1");
//                ReflectHelper.injectAttrFromSrcObj(integralTask, resp);
//                resultList.add(resp);
//            }
//
//        }
//        return resultList;
//    }
}
