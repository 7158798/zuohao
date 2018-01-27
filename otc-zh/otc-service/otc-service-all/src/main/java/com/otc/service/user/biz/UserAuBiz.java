package com.otc.service.user.biz;

import com.base.common.idcard.IdcartUtil;
import com.jucaifu.common.exceptions.biz.BizException;
import com.jucaifu.common.log.LOG;
import com.jucaifu.common.util.DateHelper;
import com.jucaifu.common.validate.ValidateManager;
import com.jucaifu.common.validate.ValidateType;
import com.jucaifu.core.biz.AbsBaseBiz;
import com.otc.core.cache.UserCache;
import com.otc.core.cache.UserCacheManager;
import com.otc.facade.sys.enums.SystemArgsEnum;
import com.otc.facade.user.enums.IdentityTypeEnum;
import com.otc.facade.user.enums.UserAutStatusEnum;
import com.otc.facade.user.enums.UserMessageConstantEnum;
import com.otc.facade.user.enums.UserOperationEnum;
import com.otc.facade.user.exceptions.UserBizException;
import com.otc.facade.user.pojo.po.User;
import com.otc.facade.user.pojo.po.UserAuthentication;
import com.otc.facade.user.pojo.poex.CacheUserInfo;
import com.otc.facade.user.pojo.vo.UserAuthenticationVo;
import com.otc.pool.OTCBizPool;
import com.otc.service.user.dao.UserAuthenticationMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;

import java.util.*;

/**
 * Created by fenggq on 17-4-25.
 */
@Service
public class UserAuBiz extends AbsBaseBiz<UserAuthentication,UserAuthenticationVo,UserAuthenticationMapper>{
    @Autowired
    private UserAuthenticationMapper userAuMapper;

    @Override
    public UserAuthenticationMapper getDefaultMapper() {
        return userAuMapper;
    }

    /**
     * 实名认证
     * @param userId
     * @param passportNo
     * @param identityType
     * @param realName
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public UserAuthentication realName(Long userId, String passportNo, String identityType, String realName){
        LOG.dStart(this,"实名认证开始");
        realName = HtmlUtils.htmlEscape(realName);
        User fuser = OTCBizPool.getInstance().userBiz.select(userId);
        if(fuser == null){
            throw new BizException("用户不存在");
        }

        if(StringUtils.isBlank(identityType) || !StringUtils.equals(identityType, IdentityTypeEnum.ID_CARD.getCode())){
            throw new BizException("证件类型不正确");
        }

        if(!ValidateManager.validateValue(passportNo, ValidateType.USER_ID_CARD.getValidateRuleRegex())){
            throw new UserBizException("身份证号码格式不正确");
        }

        if(StringUtils.isBlank(realName)){
            throw new UserBizException("姓名不能为空");
        }


        UserAuthentication userAuthentication = this.getDefaultMapper().selectByCondition(passportNo,null,null);
        if(userAuthentication != null){
            throw new UserBizException("该身份证已被实名认证!");
        }
        userAuthentication = this.selectByUserId(userId);
        if(userAuthentication != null){
            throw new UserBizException("用户已存在实名认证信息!");
        }

        Long isRealOpen =OTCBizPool.getInstance().systemArgsBiz.getSystemArgsForLong(SystemArgsEnum.OPERN_REALNAME,1L);
        if(isRealOpen == 1L){
            boolean isTrue = IdcartUtil.isRealPerson(realName,passportNo);
            if(!isTrue){
                throw new UserBizException("您的姓名与身份证号有误,请核对!");
            }
        }
        UserAuthentication userAu = new UserAuthentication();
        Date now = new Date();
        userAu.setUserId(userId);
        userAu.setCreateDate(now);
        userAu.setRealName(realName);
        userAu.setPassportNo(passportNo);
        userAu.setStatus(UserAutStatusEnum.REALNAME_PASS.getCode());

        String birthDay = IdcartUtil.getBirthday(passportNo);
        userAu.setBirthday(DateHelper.string2Date(birthDay, DateHelper.DateFormatType.YearMonthDay));
        userAu.setGender(IdcartUtil.getGender(passportNo));
        userAu.setPassportType(identityType);


        this.insert(userAu);
        //操作记录
        OTCBizPool.getInstance().userOperationBiz.add(userId, UserOperationEnum.REAL_NAME_AUT);

        UserCacheManager.deleteCacheObj(userId);
        LOG.dEnd(this,"实名认证完成");
        return userAu;
    }

    /**
     *  kyc 认证提交
     * @param userId
     * @param identityurlOn
     * @param identityurlOff
     * @param identityurlHold
     */
    public void kycAuth(Long userId, String identityurlOn, String identityurlOff, String identityurlHold) {
        LOG.dStart(this,"KYC认证开始");
        User fuser = OTCBizPool.getInstance().userBiz.select(userId);
        if(fuser == null){
            throw new BizException("用户不存在");
        }
        if(StringUtils.isBlank(identityurlOn)){
            throw new BizException("身份证正面照不能为空");
        }
        if(StringUtils.isBlank(identityurlOff)){
            throw new BizException("身份证反面照不能为空");
        }
        if(StringUtils.isBlank(identityurlHold)){
            throw new BizException("手持身份证照不能为空");
        }
        UserAuthentication userAuthentication = this.selectByUserId(userId);
        if(userAuthentication == null){
            throw new BizException("不存在实名认证！");
        }
        if(!StringUtils.equals(userAuthentication.getStatus(), UserAutStatusEnum.REALNAME_PASS.getCode())
                && !StringUtils.equals(userAuthentication.getStatus(), UserAutStatusEnum.KYC_NO_PASS.getCode())){
            throw new BizException("当前认证状态不正确！");
        }
        userAuthentication.setIdentityurlOn(identityurlOn);
        userAuthentication.setIdentityurlOff(identityurlOff);
        userAuthentication.setIdentityurlHold(identityurlHold);
        userAuthentication.setCommitIdentityDate(new Date());
        userAuthentication.setStatus(UserAutStatusEnum.KYC_POST.getCode());
        userAuthentication.setRejectionReason("");

        this.update(userAuthentication);

        //操作记录
        OTCBizPool.getInstance().userOperationBiz.add(userId, UserOperationEnum.KYC_AUT);

        UserCacheManager.deleteCacheObj(userId);

        LOG.dEnd(this,"KYC认证结束");
    }


    /**
     * 通过useriD查找
     * @param userId
     * @return
     */
    public UserAuthentication selectByUserId(Long userId){
        return this.getDefaultMapper().selectByCondition(null,null,userId);
    }


    /**
     *
     * 获取用户kyc认证状态 =====登录状态下取值
     * @param userId
     * @return
     */
    public Boolean isKycStatus(Long userId){
        CacheUserInfo userInfo = UserCacheManager.getCacheObj(userId);
        UserAuthentication userAuthentication;
        if(userInfo != null){
            userAuthentication = userInfo.getUserAuthentication();
        }else{
            userAuthentication = this.selectByUserId(userId);
        }
        return userAuthentication == null?Boolean.FALSE:UserAutStatusEnum.isKyc(userAuthentication.getStatus());
    }


    /**
     *  修改kyc审核状态
     * @param id
     * @param status
     */
    public void updateUserKycStatus(Long id,Long adminId,String status,String rejectReason){
        LOG.dStart(this,"修改KYC认证状态");
        UserAuthentication userAuthentication = this.select(id);
        if(userAuthentication == null){
            throw new BizException("记录不存在！");
        }
        if(!StringUtils.equals(status, UserAutStatusEnum.KYC_NO_PASS.getCode())
                && !StringUtils.equals(status, UserAutStatusEnum.KYC_PASS.getCode())){
            throw new BizException("状态参数无效！");
        }

        if(!StringUtils.equals(UserAutStatusEnum.KYC_POST.getCode(),userAuthentication.getStatus())){
            throw new BizException("认证记录状态无效！");
        }
        if(StringUtils.equals(status, UserAutStatusEnum.KYC_NO_PASS.getCode()) &&
        StringUtils.isBlank(rejectReason)){
            throw new BizException("拒绝原因不能为空！");
        }

        Date now = new Date();
        userAuthentication.setStatus(status);
        userAuthentication.setAuditorDate(now);
        userAuthentication.setAuditorId(adminId);
        userAuthentication.setRejectionReason(rejectReason);
        if(StringUtils.equals(UserAutStatusEnum.KYC_PASS.getCode(),status)){
            userAuthentication.setPassDate(now);
        }

        this.update(userAuthentication);
        UserCacheManager.deleteCacheObj(userAuthentication.getUserId());
        //发送通知
        if(StringUtils.equals(status, UserAutStatusEnum.KYC_PASS.getCode())){
            OTCBizPool.getInstance().messageBiz.sendMessage(userAuthentication.getUserId(), UserMessageConstantEnum.KYC_PASS,null,id);
        }else if(StringUtils.equals(status, UserAutStatusEnum.KYC_NO_PASS.getCode())){
            OTCBizPool.getInstance().messageBiz.sendMessage(userAuthentication.getUserId(), UserMessageConstantEnum.KYC_NO_PASS,rejectReason,id);
        }
         LOG.dEnd(this,"修改KYC认证状态");
    }

    public List<Long> selectUserByFilter(String filter){
        return userAuMapper.selectUserByFilter(filter);
    }

}
