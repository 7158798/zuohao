package com.base.service.integral.biz;

import com.base.facade.exception.BaseCommonBizException;
import com.base.service.pool.BaseServiceBizPool;
import com.fasterxml.jackson.databind.deser.Deserializers;
import com.jucaifu.common.exceptions.biz.BizException;
import com.jucaifu.common.log.LOG;
import com.jucaifu.common.util.DateHelper;
import com.jucaifu.core.biz.AbsBaseBiz;
import com.base.facade.integral.enums.IntegralOperateType;
import com.base.facade.integral.enums.IntegralType;
import com.base.facade.integral.pojo.bean.IntegralAccountBean;
import com.base.facade.integral.pojo.po.IntegralTask;
import com.base.facade.integral.pojo.po.UserIntegralAccount;
import com.base.facade.integral.pojo.vo.UserIntegralAccountVo;
import com.base.service.integral.dao.UserIntegralAccountMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by yangyy on 16-4-29.
 */
@Service
public class UserIntegralAccountBiz extends AbsBaseBiz<UserIntegralAccount, UserIntegralAccountVo, UserIntegralAccountMapper> {

    private static final Logger _logger = LoggerFactory.getLogger(UserIntegralAccountBiz.class);

    @Autowired
    private UserIntegralAccountMapper userIntegralAccountMapper;

    @Override
    public UserIntegralAccountMapper getDefaultMapper() {
        return userIntegralAccountMapper;
    }

    /**
     * 扣除用户账户积分
     *
     * @param userId
     * @param integral
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public UserIntegralAccount deductUserIntegral(Long userId, BigDecimal integral,IntegralType integralType) {
        _logger.info("扣除积分开始");
        UserIntegralAccount account = userIntegralAccountMapper.getByUserId(userId);
        int row = userIntegralAccountMapper.deductUserIntegral(userId, integral);
        if (row == 0) {
            throw new BaseCommonBizException("扣除账户积分失败,请检查账户积分余额是否不足");
        }
        //添加明细
        BaseServiceBizPool.getInstance().userIntegralDetailBiz.addUserIntegralDetail(userId, integralType.getType(),
                IntegralOperateType.EXPENSE.getType(),account.getUsableAmount(),integral,integralType.getDesc(),null);

        account = this.getByUserId(userId);
        _logger.info("扣除积分开始");
        return account;
    }


    /**
     * 添加积分
     * @param userId
     * @param integralType   积分类型
     * @param relationId    来源id
     * @return
     */
    public UserIntegralAccount addByTypeAndRelationId(Long userId,IntegralType integralType,Long relationId){
        BigDecimal integral = this.getIntegralByType(userId, integralType);
        return this.addUserIntegral(userId,integral,integralType,relationId);
    }


    /**
     * 给用户添加积分   指定类型和积分数
     * @param userId
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public UserIntegralAccount addUserIntegral(Long userId,BigDecimal integral ,IntegralType integralType,Long relationId) {
        UserIntegralAccount account = userIntegralAccountMapper.getByUserId(userId);

        if(integral.compareTo(BigDecimal.ZERO) == 0){
            return account;
        }

        BigDecimal before = BigDecimal.ZERO;

        if (account != null) {
            before = account.getUsableAmount();
            int row = userIntegralAccountMapper.addUserIntegral(userId, integral);
            if (row == 0) {
                throw new BaseCommonBizException("给用户添加积分失败");
            }
        } else {
            account = new UserIntegralAccount();
            account.setUserId(userId);
            account.setCreateDate(new Date());
            account.setUsableAmount(integral);
            account.setTotalAmount(integral);
            account.setModifiedDate(new Date());
            account.setDeleted(Boolean.FALSE);
            userIntegralAccountMapper.insert(account);
        }
        //添加明细
        BaseServiceBizPool.getInstance().userIntegralDetailBiz.addUserIntegralDetail(userId, integralType.getType(),
                IntegralOperateType.INCOME.getType(),before,integral,integralType.getDesc(),relationId);
        return account;
    }

    /**
     *  获取配置积分数
     * @param userId
     * @param integralType
     * @return
     */
    private BigDecimal getIntegralByType(Long userId,IntegralType integralType){
        BigDecimal integral = BigDecimal.ZERO;
        if(userId == null || integralType == null){
            return integral;
        }
//        User user = BaseServiceBizPool.getInstance().userBiz.select(userId);
//        if(user == null){
//            throw new BizException("用户id无效");
//        }
        IntegralTask task =  BaseServiceBizPool.getInstance().integralTaskBiz.getIntegralTaskByTaskNo(integralType.getCode());
        if(task == null){
            LOG.i(this,"积分配置不存在");
            return integral;
        }

        //获取积分配置数
        integral = task.getIntegralAccount() == null?BigDecimal.ZERO:task.getIntegralAccount();
        //每日限制数
        BigDecimal daily =task.getDailyLimit();
        String now = DateHelper.date2String(new Date(), DateHelper.DateFormatType.YearMonthDay);
        if(daily == null || daily.compareTo(BigDecimal.ZERO) <= 0){
            return integral;
        }
        //今天获取积分数
        BigDecimal todayIntegral = BaseServiceBizPool.getInstance().
                userIntegralDetailBiz.getAccountByDateAndType(userId,integralType.getType(),now);

        if(todayIntegral.compareTo(daily) >= 0){
            LOG.i(this,"积分已超出每日限制");
            //超出限制
            return BigDecimal.ZERO;
        }else if(todayIntegral.add(integral).compareTo(daily) > 0){
            //本次后超出
            return daily.subtract(todayIntegral);
        }else{
            //未超出
            return integral;
        }
    }




    public List<IntegralAccountBean> getAccountListByConditionPage(UserIntegralAccountVo vo){
        return  getDefaultMapper().getAccountListByConditionPage(vo);
    }

    public UserIntegralAccount getByUserId(Long userId){
        UserIntegralAccount account = null;
        if(userId != null){
            account = this.getDefaultMapper().getByUserId(userId);
        }
        return account;
    }


    /**
     * 获取用户所有积分
      * @param validTag   0： 所有  1：有效的
     * @return
     */
//    public BigDecimal getAllUsableIntegral(String validTag){
//        String expOrgCode = BusinessConfig.EXPERIENCE_ORGAN_CODE;
//        return userIntegralAccountMapper.getAllUsableIntegral(validTag,expOrgCode);
//    }
}
