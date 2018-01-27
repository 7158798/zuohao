package com.base.service.integral.biz;

import com.base.facade.exception.BaseCommonBizException;
import com.jucaifu.core.biz.AbsBaseBiz;
import com.base.facade.integral.enums.IntegralOperateType;
import com.base.facade.integral.enums.IntegralType;
import com.base.facade.integral.pojo.bean.IntegralDetailBean;
import com.base.facade.integral.pojo.po.UserIntegralDetail;
import com.base.facade.integral.pojo.vo.UserIntegralDetailVo;
import com.base.service.integral.dao.UserIntegralDetailMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by yangyy on 16-4-29.
 */
@Service
public class UserIntegralDetailBiz extends AbsBaseBiz<UserIntegralDetail, UserIntegralDetailVo, UserIntegralDetailMapper> {

    private static final Logger _logger = LoggerFactory.getLogger(UserIntegralDetailBiz.class);

    @Autowired
    private UserIntegralDetailMapper userIntegralDetailMapper;

    @Override
    public UserIntegralDetailMapper getDefaultMapper() {
        return userIntegralDetailMapper;
    }

//    public List<IntegralDetailCountEx> selectAllIntegralsByDate(UserIntegralDetailVo vo) {
//        return userIntegralDetailMapper.selectAllIntegralsByDate(vo);
//    }

    /**
     * 添加积分明细
     *
     * @param userId      用户id
     * @param type        积分类型
     * @param operateType 操作类型 0：支出，1：收入
     * @param beforeAmt   操作前可用积分数
     * @param operateAmt  操作积分数
     * @param remark      备注
     */
    protected void addUserIntegralDetail(Long userId, int type, int operateType, BigDecimal beforeAmt,
                                      BigDecimal operateAmt, String remark,Long relationId) {
        if(userId == null || operateAmt == null ){
            return;
        }
        if (BigDecimal.ZERO.compareTo(operateAmt) != -1) {
            _logger.info("操作积分不能小于或等于零");
            throw new BaseCommonBizException("操作积分不能小于或等于零");
        }

        List<Integer> list = IntegralType.getIntegralTypeList();
        if (!list.contains(type)) {
            _logger.info("积分明细类型不正确");
            throw new BaseCommonBizException("积分明细类型不正确");
        }

        UserIntegralDetail detail = new UserIntegralDetail();

        Date now = new Date();
        detail.setUserId(userId);
        detail.setBeforeAmount(beforeAmt);
        detail.setOperateAmount(operateAmt);
        if (operateType == IntegralOperateType.INCOME.getType()) {
            detail.setAfterAmount(beforeAmt.add(operateAmt));
        } else if (operateType == IntegralOperateType.EXPENSE.getType()) {
            if (beforeAmt.compareTo(operateAmt) == -1) {
                _logger.info("操作积分不能大于用户可用积分");
                throw new BaseCommonBizException("用户可用积分不足");
            }
            detail.setAfterAmount(beforeAmt.subtract(operateAmt));
        } else {
            _logger.info("积分操作类型不正确");
            throw new BaseCommonBizException("积分操作类型不正确");
        }
        detail.setOperateType(operateType);
        detail.setType(type);
        detail.setCreateDate(now);
        detail.setRemark(remark);
        detail.setDeleted(Boolean.FALSE);
        detail.setRelationId(relationId);

        this.insertEnhance(userIntegralDetailMapper, detail);
    }




    /**
     * 条件获取用户积分流水
     *
     * @param vo
     * @return
     */
    public UserIntegralDetailVo getUserIntegralListByConditionPage(UserIntegralDetailVo vo) {
        List<UserIntegralDetail> list = userIntegralDetailMapper.getUserIntegralListByConditionPage(vo);
        vo.setResultList(list);
        return vo;
    }

//    public IntegralCountResult getUserIntegralCountResult(String userId) {
//        if (StringUtils.isBlank(userId)) {
//            throw new UserBizException("请求参数用户id为空");
//        }
//        BigDecimal todayGain = userIntegralDetailMapper.sumTodayGainIntegral(userId);
//        UserIntegralAccount userIntegralAccount = OrderBizPool.getInstance().userIntegralAccountBiz.select(userId);
//        if (todayGain == null) {
//            todayGain = BigDecimal.ZERO;
//        }
//        BigDecimal left = BigDecimal.ZERO;
//        BigDecimal totalConsume = BigDecimal.ZERO;
//        if (userIntegralAccount != null) {
//            left = userIntegralAccount.getUsableAmount();
//            // BigDecimal totalAmount = userIntegralAccount.getTotalAmount();
//            // totalConsume = totalAmount.subtract(left);
//
//            // 已兑换积分不包括后台调整 16/07/26 update
//            totalConsume = userIntegralDetailMapper.sumOperateAmountByType(userId,11);
//        }
//        IntegralCountResult result = new IntegralCountResult(todayGain, left, totalConsume);
//        return result;
//    }



    /**
     *获取用户获取积分次数
     * @param userId
     * @param type  类型
     * @return
     */
    public Long getUserIntegralCountByType(String userId,int type){
        return userIntegralDetailMapper.getUserIntegralCountByType(userId, type);
    }


    public List<IntegralDetailBean> getDetailListByConditionPage(UserIntegralDetailVo vo){
        return  userIntegralDetailMapper.getDetailListByConditionPage(vo);
    }

    public List<UserIntegralDetail> getUserIntegerDetailByGroup(Long userId){
        return userIntegralDetailMapper.getDetailByGroup(userId, IntegralOperateType.INCOME.getType());
    }

    public int deleteByUserId(Long userId){
        return userIntegralDetailMapper.deleteByUserId(userId);
    }


    public BigDecimal getAccountByDateAndType(Long userId,Integer type,String strDate){
        BigDecimal integral =  userIntegralDetailMapper.getAccountByDateAndType(userId, type,strDate);
        return integral == null?BigDecimal.ZERO:integral;
    }


}
