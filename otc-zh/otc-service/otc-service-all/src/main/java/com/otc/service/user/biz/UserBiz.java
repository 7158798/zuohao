package com.otc.service.user.biz;

import com.jucaifu.common.configs.TimeOutConfigConstant;
import com.jucaifu.common.log.LOG;
import com.jucaifu.common.util.DateHelper;
import com.jucaifu.common.util.IpHelper;
import com.jucaifu.common.util.UUIDHelper;
import com.jucaifu.common.util.ValueHelper;
import com.jucaifu.common.validate.ValidateManager;
import com.jucaifu.common.validate.ValidateType;
import com.jucaifu.core.biz.AbsBaseBiz;
import com.otc.core.cache.CacheHelper;
import com.otc.core.cache.UserCacheManager;
import com.otc.core.token.TokenFactory;
import com.otc.core.validator.SmsCodeVerify;
import com.otc.core.validator.SmsType;
import com.otc.core.validator.SmsVerifyUtils;
import com.otc.facade.base.CountVo;
import com.otc.facade.user.enums.UserAutStatusEnum;
import com.otc.facade.user.enums.UserMessageConstantEnum;
import com.otc.facade.user.enums.UserOperationEnum;
import com.otc.facade.user.enums.UserStatusEnum;
import com.otc.facade.user.exceptions.UserBizException;
import com.otc.facade.user.pojo.po.User;
import com.otc.facade.user.pojo.po.UserAuthentication;
import com.otc.facade.user.pojo.po.UserCreditRecord;
import com.otc.facade.user.pojo.poex.*;
import com.otc.facade.user.pojo.vo.UserVo;
import com.otc.pool.OTCBizPool;
import com.otc.service.user.dao.UserMapper;
import com.otc.util.PwdEncryptor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by a123 on 17-4-19.
 */
@Service
public class UserBiz extends AbsBaseBiz<User,UserVo,UserMapper>{

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserMapper getDefaultMapper() {
        return userMapper;
    }


    /**
     *  邮箱注册
     * @param loginName
     * @param verificationCode
     * @param pwd
     * @param email_address
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public User registerUser(String loginName,String email_address, String verificationCode,String pwd, String IPAddress) {
        LOG.dStart(this,"注册用户");

        if (!ValidateManager.validateValue(email_address, ValidateType.EMAIL_ADDRESS.getValidateRuleRegex())) {
            throw new UserBizException(UserBizException.MOBILEPHONE_FORMAT_INCORRECT, "邮箱格式不正确");
        }

        if (!ValidateManager.validateValue(verificationCode, ValidateType.VERIFICATION_CODE.getValidateRuleRegex())) {
            throw new UserBizException(UserBizException.AUTHCODE_FORMAT_INCORRECT, "验证码格式不正确");
        }
        if (!ValidateManager.validateValue(pwd, ValidateType.TZGL_LOGIN_PWD3.getValidateRuleRegex())) {
            throw new UserBizException(UserBizException.PASSWORD_FORMAT_INCORRECT, "用户密码格式不正确");
        }
        if(StringUtils.isBlank(loginName)){
            throw new UserBizException("用户登录名不能为空");
        }
        SmsCodeVerify smsCodeVerify = SmsVerifyUtils.verify(email_address, SmsType.REGISTER, verificationCode);
        if (!smsCodeVerify.isStatus()) {
            throw new UserBizException(UserBizException.AUTHCODE_VALUE_INCORRECT, smsCodeVerify.getConetent());
        }
        if (this.selectUserByEmail(email_address) != null) {
            throw new UserBizException(UserBizException.MOBILEPHONE_IS_REGISTED, "该邮箱已经被注册");
        }
        if (this.selectUserByLoginName(loginName) != null) {
            throw new UserBizException(UserBizException.MOBILEPHONE_IS_REGISTED, "该登录名已经被注册");
        }
        LOG.dEnd(this, "－ 注册用户");
        User newUser = new User();
        newUser.setLoginName(loginName);
        newUser.setEmailAddress(email_address);

        Date now = new Date();
        newUser.setCreateDate(now);
        newUser.setModifiedDate(now);
        newUser.setDeleted(Boolean.FALSE);
        newUser.setRegisterIp(IPAddress);
        newUser.setStatus(UserStatusEnum.COMMON.getCode());
        newUser.setLoginPassword(PwdEncryptor.encrypt(pwd));


        this.insert(newUser);
       // newUser.setId(new Long(id));
        //创建钱包
        OTCBizPool.getInstance().virtualWalletBiz.createWalletByUser(newUser.getId());

        //生成用户类型
        LOG.dEnd(this, "－ 注册完成");
        return newUser;
    }

    /**
     *  通过手机、密码登录
     * @param loginName
     * @param loginPwd
     * @param IPAddress
     * @param loginPlatform
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public UserLoginInfo userLoginByPassword(String loginName, String loginPwd, String IPAddress, String loginPlatform) {

        if (!ValidateManager.validateValue(loginPwd,ValidateType.TZGL_LOGIN_PWD3.getValidateRuleRegex())) {
            throw new UserBizException(UserBizException.PASSWORD_FORMAT_INCORRECT, "用户密码格式不正确");
        }
        //验证用户是否存在
        User user = this.selectUserForLogin(loginName);
        if (user == null) {
            throw new UserBizException(UserBizException.MOBILEPHONE_OR_PASSWOD_NO_SUCCESS, "用户名或密码不正确");
        }

        Date now = new Date();
        int failedTimes = user.getFailedLoginAttempts() == null ? 0 : user.getFailedLoginAttempts();

        if(failedTimes > 3 ){
            Date lastTime = user.getLoginTime();
            if(DateHelper.isSameDate(lastTime,new Date())){
                throw UserBizException.LOGIN_PWD_ERROR_COUNT_LIMITED_EXCEPTION;
            }
        }
        String password = PwdEncryptor.encrypt(loginPwd);
        if (!user.getLoginPassword().equals(password)) {
            user.setFailedLoginAttempts(failedTimes + 1);
            user.setLoginTime(new Date());
            userMapper.update(user);
            throw new UserBizException(UserBizException.MOBILEPHONE_OR_PASSWOD_NO_SUCCESS, "用户名或密码不正确");
        }
        if(StringUtils.equals(user.getStatus(),UserStatusEnum.FORBIDDEN.getCode())){
            throw new UserBizException("账户出现安全隐患被禁止登录，请尽快联系客服");
        }

        LOG.dEnd(this, "2-1 逻辑-帐号密码正确");

        UserLoginInfo userLoginInfo = this.getUserLoginInfo(user,now,IPAddress);
        //记录登录

        OTCBizPool.getInstance().userOperationBiz.add(user.getId(), UserOperationEnum.LOGIN);
        LOG.dEnd(this, "2-2 逻辑-登录成功");

        return userLoginInfo;
    }

    /**
     * 验证短信验证码
     * @param email
     * @param smsAuthCode
     * @param smsType
     * @return
     * @throws UserBizException
     */
    @Transactional(rollbackFor = Exception.class)
    public VerifyResult verifySmsAuthCode(String email, String smsAuthCode, SmsType smsType) throws
            UserBizException {
        LOG.dStart(this, "校验验证码开始");
        VerifyResult result = new VerifyResult();
        result.setPhoneNumber(email);
        LOG.dTag(this, "<1> 验证提交参数格式正确");
        if (!ValidateManager.validateValue(email, ValidateType.EMAIL_ADDRESS.getValidateRuleRegex())) {
            throw new UserBizException(UserBizException.MOBILEPHONE_FORMAT_INCORRECT, "邮箱格式不正确");
        }
        if (!ValidateManager.validateValue(smsAuthCode, ValidateType.VERIFICATION_CODE.getValidateRuleRegex())) {
            throw new UserBizException(UserBizException.AUTHCODE_FORMAT_INCORRECT, "验证码格式不正确");
        }

        User user = this.selectUserByEmail(email);
        if (user == null) {
            throw new UserBizException(UserBizException.USER_NOT_EXIST, "用户不存在");
        }
        //查询用户短信验证码，并与传来的验证码做对比，相同则返回true
        LOG.dEnd(this, "－ 验证验证码是否正确");
        SmsCodeVerify smsCodeVerify = SmsVerifyUtils.verify(email, smsType, smsAuthCode);
        if (!smsCodeVerify.isStatus()) {
            throw new UserBizException(UserBizException.AUTHCODE_VALUE_INCORRECT, smsCodeVerify.getConetent());
        } else {
            result.setResult(true);
            String authCodeCache = UUIDHelper.getUUID();
            result.setAuthCodeCache(authCodeCache);
            result.setStepType("1");

            CacheHelper.saveObj(authCodeCache, email, TimeOutConfigConstant.STEP_DEPEND_TOKEN_TIMEOUT);
        }
        LOG.d(this, result);
        LOG.dEnd(this, "校验验证码结束");
        return result;
    }



    /**
     * 重置密码
     * @param email
     * @param newPassword
     * @param authCodeCache
     * @return
     * @throws UserBizException
     */
    @Transactional(rollbackFor = Exception.class)
    public VerifyResult resetPassword(String email, String newPassword, String authCodeCache) throws
            UserBizException {
        VerifyResult result = new VerifyResult();
        result.setPhoneNumber(email);
        LOG.dTag(this, "<1> 验证提交参数格式是否正确");
        if (!ValidateManager.validateValue(email, ValidateType.EMAIL_ADDRESS.getValidateRuleRegex())) {
            throw new UserBizException(UserBizException.MOBILEPHONE_FORMAT_INCORRECT, "邮箱格式不正确");
        }
        if (!ValidateManager.validateValue(newPassword, ValidateType.TZGL_LOGIN_PWD3.getValidateRuleRegex())) {
            throw new UserBizException(UserBizException.PASSWORD_FORMAT_INCORRECT, "用户密码格式不正确");
        }
        if (StringUtils.isBlank(authCodeCache)) {
            throw new UserBizException(UserBizException.PARAM_IS_NOT_BLANK, "用户验证码校验结果为空");
        }
        //校验短信验证结果缓存是否过期
        String phoneCache = CacheHelper.getObj(authCodeCache);
        if (!email.equals(phoneCache)) {
            throw new UserBizException(UserBizException.OPERATE_IS_FAILURE, "验证码校验结果过期");
        }

        User user = this.selectUserByEmail(email);
        if (user == null) {
            throw new UserBizException(UserBizException.USER_NOT_EXIST, "用户不存在");
        }
        String password = PwdEncryptor.encrypt(newPassword);
        user.setLoginPassword(password);
        user.setModifiedDate(new Date());
        user.setFailedLoginAttempts(0);
        userMapper.update(user);
        //校验短信验证结果缓存清除
        CacheHelper.deleteObj(authCodeCache);
        result.setResult(true);
        result.setStepType("2");

        OTCBizPool.getInstance().userOperationBiz.add(user.getId(), UserOperationEnum.RESET_LOGIN_PWD);

        UserCacheManager.deleteAllCache(user.getId());
        LOG.d(this, "重置密码成功－－清楚所有的登录token数据");
        return result;
    }


    /**
     *  修改密码
     * @param newPassword
     * @param oldPassword
     * @param token
     * @return
     * @throws UserBizException
     */
    @Transactional(rollbackFor = Exception.class)
    public User updateUserPassword(String newPassword, String oldPassword, String token,String code) throws UserBizException {
        LOG.dTag(this, "<1> 验证提交参数格式正确");
        if (!ValidateManager.validateValue(oldPassword, ValidateType.TZGL_LOGIN_PWD3.getValidateRuleRegex())) {
            throw new UserBizException(UserBizException.PASSWORD_FORMAT_INCORRECT, "用户旧密码格式不正确");
        }
        if (!ValidateManager.validateValue(newPassword, ValidateType.TZGL_LOGIN_PWD3.getValidateRuleRegex())) {
            throw new UserBizException(UserBizException.PASSWORD_FORMAT_INCORRECT, "用户新密码格式不正确");
        }
        if (!ValidateManager.validateValue(code, ValidateType.VERIFICATION_CODE.getValidateRuleRegex())) {
            throw new UserBizException(UserBizException.AUTHCODE_FORMAT_INCORRECT, "验证码格式不正确");
        }
        Long userId = UserCacheManager.getUidWithToken(token);
        User user = this.select(userId);
        if (user == null) {
            throw new UserBizException(UserBizException.USER_NOT_LOGIN, "用户未登录");
        }

        String oldPwd = PwdEncryptor.encrypt(oldPassword);
        String newPwd = PwdEncryptor.encrypt(newPassword);
        if(StringUtils.isBlank(user.getLoginPassword())){
            throw new UserBizException("用户未设置密码");
        }
        if (!user.getLoginPassword().equals(oldPwd)) {
            throw new UserBizException(UserBizException.PARAM_VALUE_INCORRECT, "用户旧密码不正确");
        }
        SmsCodeVerify smsCodeVerify = SmsVerifyUtils.verify(user.getEmailAddress(), SmsType.CHANGE_PWD, code);
        if (!smsCodeVerify.isStatus()) {
            throw new UserBizException(UserBizException.AUTHCODE_VALUE_INCORRECT, smsCodeVerify.getConetent());
        }

        user.setLoginPassword(newPwd);
        user.setModifiedDate(new Date());
        user.setFailedLoginAttempts(0);
        userMapper.update(user);

        OTCBizPool.getInstance().userOperationBiz.add(userId,UserOperationEnum.MODIFY_LOGIN_PWD);

        LOG.dEnd(this, "密码修改成功");

        return user;
    }


    /**
     *  设置、修改防钓鱼码
     * @return
     * @throws UserBizException
     */
    @Transactional(rollbackFor = Exception.class)
    public User updateUserFish(Long userId,String fishCode) throws UserBizException {
        LOG.dStart(this, "开始设置用户防钓鱼码");
        if (StringUtils.isBlank(fishCode) || fishCode.length() > 16 || fishCode.length() < 10) {
            throw new UserBizException(UserBizException.PASSWORD_FORMAT_INCORRECT, "防钓鱼码格式错误");
        }

        User user = this.select(userId);
        if (user == null) {
            throw new UserBizException(UserBizException.USER_NOT_LOGIN, "用户不存在");
        }
        user.setPreventFish(fishCode);
        user.setModifiedDate(new Date());
        userMapper.update(user);

        OTCBizPool.getInstance().userOperationBiz.add(user.getId(), UserOperationEnum.SET_FISH_CODE);

        LOG.dEnd(this, "设置防钓鱼码成功");

        UserCacheManager.deleteCacheObj(userId);
        return user;
    }




    /**
     * 获取用户登录信息
     * @param user
     * @param now
     * @param IPAddress
     * @return
     */
    public UserLoginInfo getUserLoginInfo(User user,Date now,String IPAddress){
        String token = TokenFactory.generatorUserToken();
        UserLoginInfo userLoginInfo = new UserLoginInfo();
        userLoginInfo.setToken(token);

        CacheUserInfo cacheUserInfo = this.getCacheUserInfo(token, user.getId(),IPAddress);

        userLoginInfo.setCacheUserInfo(cacheUserInfo);

        LOG.d(this, userLoginInfo);

        //绑定用户信息到redis缓存
        this.bindUserToken(token, cacheUserInfo);

        user.setLastLoginTime(user.getLoginTime());
        user.setLoginTime(now);
        user.setLoginIp(IPAddress);
        user.setFailedLoginAttempts(0);
        userMapper.update(user);

        return userLoginInfo;
    }


    /**
     * 获取缓存用户信息
     * @param token
     * @param userId
     * @return
     * @throws UserBizException
     */
    public CacheUserInfo getCacheUserInfo(String token, Long userId,String ip) throws UserBizException {
        CacheUserInfo cacheUserInfo = UserCacheManager.getCacheObj(userId);
       // CacheUserInfo cacheUserInfo = null; //暂时去除缓存
        if (cacheUserInfo == null) {
            cacheUserInfo = new CacheUserInfo();
            User user = this.select(userId);
            //用户基本信息
            cacheUserInfo.setBaseInfo(user);
            //用户认证信息
            UserAuthentication userAuthentication = OTCBizPool.getInstance().userAuBiz.selectByUserId(userId);
            cacheUserInfo.setUserAuthentication(userAuthentication);

            UserCreditRecord userCreditRecord = OTCBizPool.getInstance().userCreditBiz.getByUserId(userId);
            cacheUserInfo.setUserCreditRecord(userCreditRecord);
            //绑定用户信息到redis缓存
            this.bindUserToken(token, cacheUserInfo);
        }
        cacheUserInfo.setLoginIp(ip);
        cacheUserInfo.setLoginAddress(IpHelper.getAddress(ip));
        return cacheUserInfo;
    }

    /**
     * 获取缓存用户信息
     * @param userId
     * @return
     * @throws UserBizException
     */
    public CacheUserInfo getCacheUserInfo(Long userId) throws UserBizException {
        CacheUserInfo cacheUserInfo = UserCacheManager.getCacheObj(userId);
        if (cacheUserInfo == null) {
            cacheUserInfo = new CacheUserInfo();
            User user = this.select(userId);
            //用户基本信息
            cacheUserInfo.setBaseInfo(user);
            //用户认证信息
            UserAuthentication userAuthentication = OTCBizPool.getInstance().userAuBiz.selectByUserId(userId);
            cacheUserInfo.setUserAuthentication(userAuthentication);

            UserCreditRecord userCreditRecord = OTCBizPool.getInstance().userCreditBiz.getByUserId(userId);
            cacheUserInfo.setUserCreditRecord(userCreditRecord);
        }
        return cacheUserInfo;
    }




    /**
     * 绑定用户token
     * @param token
     * @param cacheUserInfo
     * @throws UserBizException
     */
    public void bindUserToken(String token, CacheUserInfo cacheUserInfo) throws UserBizException {
        if (cacheUserInfo != null && token != null) {
            UserCacheManager.bindUserToken(token, cacheUserInfo.getUid(), cacheUserInfo,null);
        } else {
            throw new UserBizException(UserBizException.OPERATE_IS_FAILURE, "根据token绑定用户信息失败。。");
        }
    }



    /**
     * 通过eamil查询用户
     * @param email
     * @return
     */
    public User selectUserByEmail(String email) {
        List<User> list = selectUserByContion(null, email);
        if(list.size() > 0){
            return list.get(0);
        }
        return null;
    }

    /**
     * 通过登录名查询用户
     * @param loginName
     * @return
     */
    public User selectUserByLoginName(String loginName) {
        List<User> list = selectUserByContion(loginName, null);
        if(list.size() > 0){
            return list.get(0);
        }
        return null;
    }

    /**
     *  匹配登录名和邮箱
     * @param loginName
     * @return
     */
    public User selectUserForLogin(String loginName){
        List<User> list = selectUserByContion(null, loginName);
        if(list.size() > 0){
            return list.get(0);
        }
        list = selectUserByContion(loginName, null);
        if(list.size() > 0){
            return list.get(0);
        }
        return null;
    }

    /**
     * 通过登录名或邮箱查找
     * @param loginName
     * @param email
     * @return
     */
    public List<User> selectUserByContion(String loginName,String email){
        UserVo vo = new UserVo();
        vo.setLoginName(loginName);
        vo.setEmail(email);
        return userMapper.selectbycondition(vo);
    }

    /**
     * 分页查找  用户列表、KYC列表
     * @param vo
     * @return
     */
    public UserVo selectUserListByConditionPage(UserVo vo){
        List<UserReportEx> list =  this.getDefaultMapper().getListByConditionPage(vo);
        vo.setResultList(list);
        return vo;
    }


    /**
     * 分页查询   用户地址
     * @param vo
     * @return
     */
    public UserVo getUserAddressListByConditionPage(UserVo vo){
        List<UserReportEx> list =  this.getDefaultMapper().getUserAddressListByConditionPage(vo);
        vo.setResultList(list);
        return vo;
    }

    /**
     *  分页查询 用户操作记录 --后台
     * @param vo
     * @return
     */
    public UserVo getUserOperationListByConditionPage(UserVo vo){
        List<UserReportEx> list =  this.getDefaultMapper().getUserOperationListByConditionPage(vo);
        vo.setResultList(list);
        return vo;
    }


    /**
     * 分页查询 用户资产记录 --后台
     * @param vo
     * @return
     */
    public UserVo getUserAssetListByConditionPage(UserVo vo){
        List<UserReportEx> list =  this.getDefaultMapper().getUserAssetListByConditionPage(vo);
        vo.setResultList(list);
        return vo;
    }


    /**
     * 通过id查询用户 不存在时抛异常
     * @param userId
     * @return
     */
    public User selectByIdForException(Long userId){
        User user = this.select(userId);
        if(user == null){
            throw new UserBizException("用户不存在");
        }
        return user;
    }


    /**
     * 修改用户状态
     * @param userId
     * @param status
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateUserStatus(Long userId,String status){
        LOG.dStart(this,"修改用户状态");
        User user = this.selectByIdForException(userId);
        if(StringUtils.equals(status, UserStatusEnum.COMMON.getCode())){
            if(!StringUtils.equals(user.getStatus(), UserStatusEnum.FORBIDDEN.getCode())){
                throw new UserBizException("用户状态不正确");
            }

        }else if (StringUtils.equals(status, UserStatusEnum.FORBIDDEN.getCode())){
            if(!StringUtils.equals(user.getStatus(), UserStatusEnum.COMMON.getCode())){
                throw new UserBizException("用户状态不正确");
            }
        }else{
            throw new UserBizException("请求状态不正确");
        }
        user.setStatus(status);
        this.update(user);

        UserCacheManager.deleteAllCache(userId);


        LOG.dEnd(this,"修改用户状态");
    }

    /**
     * 通过后台重置用户登录密码
     * @param userId
     */
    @Transactional(rollbackFor = Exception.class)
    public String resetPwdByAdmin(Long userId,String adminToken){
        LOG.dStart(this,"重置用户密码");
        Long adminId = UserCacheManager.getUidWithToken(adminToken);

        //判断管理员是否登录
        String pwd = this.generatePassword();
        User user = this.selectByIdForException(userId);
        user.setLoginPassword(PwdEncryptor.encrypt(pwd));
        this.update(user);

//        MailSendInfo sendInfo = new MailSendInfo();
//        sendInfo.setTo(user.getEmailAddress());
//        sendInfo.setSubject("OTC密码重置");
//        sendInfo.setContent("您的密码重置为："+pwd);
//
//        boolean sendResult = MailSendHelper.getInstance().send(sendInfo);
//        if(!sendResult){
//            throw new UserBizException("短信通知异常");
//        }
        LOG.dEnd(this,"重置用户密码");
        UserCacheManager.deleteAllCache(user.getId());
        //发送通知
        OTCBizPool.getInstance().messageBiz.sendMessage(userId, UserMessageConstantEnum.RESET_PWD_SUCESS,pwd,null);

        return pwd;
    }

   //生成随机密码
    private String generatePassword() {
        String[] pa = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O",
                "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
        StringBuffer sb = new StringBuffer("");
        for (int i = 0; i < 4; i++) {
            sb.append(pa[(Double.valueOf(Math.random() * pa.length).intValue())]);
        }
        sb.append((int)(Math.random()*100));
        return sb.toString();
    }

    public List<Long> selectUserByFilter(String filter){
        return userMapper.selectUserByFilter(filter);
    }



    /**
     * 修改用户邮箱
     * @param newEmail
     * @param authCodeCache
     * @param authCode
     * @return
     * @throws UserBizException
     */
    public VerifyResult changeEmail(Long userId, String newEmail, String authCodeCache, String authCode) throws
            UserBizException {
        VerifyResult result = new VerifyResult();
        result.setPhoneNumber(newEmail);
        LOG.dTag(this, "<1> 验证提交参数格式是否正确");
        if (!ValidateManager.validateValue(newEmail, ValidateType.EMAIL_ADDRESS.getValidateRuleRegex())) {
            throw new UserBizException(UserBizException.MOBILEPHONE_FORMAT_INCORRECT, "邮箱格式不正确");
        }
        if (!ValidateManager.validateValue(authCode, ValidateType.VERIFICATION_CODE.getValidateRuleRegex())) {
            throw new UserBizException(UserBizException.AUTHCODE_FORMAT_INCORRECT, "验证码格式不正确");
        }
        if (StringUtils.isBlank(authCodeCache)) {
            throw new UserBizException(UserBizException.PARAM_IS_NOT_BLANK, "原邮箱验证结果为空");
        }

        User user = this.select(userId);
        if(user == null){
            throw new UserBizException("用户未登录");
        }
        String email = user.getEmailAddress();
        if(StringUtils.isBlank(email)){
            throw new UserBizException("用户未设置邮箱");
        }

        //校验短信验证结果缓存是否过期
        String phoneCache = CacheHelper.getObj(authCodeCache);
        if (!email.equals(phoneCache)) {
            throw new UserBizException(UserBizException.OPERATE_IS_FAILURE, "验证码校验结果过期");
        }

        SmsCodeVerify smsCodeVerify = SmsVerifyUtils.verify(newEmail, SmsType.CHANGE_EMAIL, authCode);
        if (!smsCodeVerify.isStatus()) {
            throw new UserBizException(UserBizException.AUTHCODE_VALUE_INCORRECT, smsCodeVerify.getConetent());
        }

        User queryUser = selectUserByEmail(newEmail);
        if(queryUser != null){
            throw new UserBizException("该邮箱已经被注册");
        }

        //发送通知
        OTCBizPool.getInstance().messageBiz.sendMessage(user.getId(), UserMessageConstantEnum.MODIFY_EMAILE_SUCESS,newEmail,null);


        user.setEmailAddress(newEmail);
        user.setModifiedDate(new Date());
        userMapper.update(user);
        //校验短信验证结果缓存清除
        CacheHelper.deleteObj(authCodeCache);
        result.setResult(true);
        result.setStepType("2");

        UserCacheManager.deleteCacheObj(userId);
        LOG.d(this, "重置密码成功－－清楚所有的登录token数据");
        return result;
    }

    /**
     * 综合统计-全部
     * @return
     */
    public List<CountVo> countUser(){
        return this.countUser(null);
    }

    /**
     * 综合统计-一天
     * @param dayTime
     * @return
     */
    public List<CountVo> countUser(Date dayTime){
        BigDecimal total = new BigDecimal(0);
        CountVo countTotal = null;
        BigDecimal countNull = new BigDecimal(0);
        List<CountVo> countVos = userMapper.countUser(DateHelper.date2String(dayTime, DateHelper.DateFormatType.YearMonthDay));

        if(countVos != null && countVos.size() > 0){
            for(CountVo vo : countVos){
                // 如果vo对象name值为空则加到name值为00上
                if(ValueHelper.checkStringIsEmpty(vo.getCountName())){
                    countNull = vo.getCountTotal();
                    continue;
                }else if(vo.getCountName().equals(UserAutStatusEnum.NO_REALNAME.getCode())){
                    vo.setCountTotal(vo.getCountTotal().add(countNull));
                }
                total = total.add(vo.getCountTotal());
            }
            countTotal = new CountVo();
            countTotal.setCountName("total");
            countTotal.setCountTotal(total);
            countVos.add(countTotal);
        }
        return countVos;
    }

    public List<User> queryNonExistentWallet(Long coinId){
        return userMapper.queryNonExistentWallet(coinId);
    }

    /**
     * 用户信息处理
     * @param token
     * @param cacheUserInfo
     * @return
     */
    public UserWebLoginInfo getUserWebLoginInfo(String token, CacheUserInfo cacheUserInfo) {
        User user = cacheUserInfo.getBaseInfo();
        UserAuthentication userAuthentication = cacheUserInfo.getUserAuthentication();
        UserCreditRecord userCredit = cacheUserInfo.getUserCreditRecord();

        UserWebLoginInfo userWebLoginInfo = new UserWebLoginInfo();
        userWebLoginInfo.setToken(token);
        UserInfo userInfo = new UserInfo();

        userInfo.setLoginName(user.getLoginName());
        userInfo.setEmail(user.getEmailAddress());
        userInfo.setId(user.getId());
        userInfo.setFishCode(user.getPreventFish());

        userInfo.setRealName(userAuthentication == null ? "" : userAuthentication.getRealName());
        userInfo.setHasRealName(userAuthentication == null ? false : UserAutStatusEnum.isRealName(userAuthentication.getStatus()));
        userInfo.setKycStatus(userAuthentication == null ? "00" : userAuthentication.getStatus());
        if(userAuthentication != null && StringUtils.equals(userAuthentication.getStatus(),UserAutStatusEnum.KYC_NO_PASS.getCode())){
            //被拒绝 添加拒绝原因
            userInfo.setRejectionReason(userAuthentication.getRejectionReason());
        }

        if (userCredit != null) {
            userInfo.setTradeTimes(userCredit.getTradeAllTimes()); //交易总次数
            userInfo.setGoodJudgeTimes(userCredit.getGoodJudgeTimes()); //好评次数
            userInfo.setCommonJudgeTimes(userCredit.getCommonJudgeTimes()); //中评
            userInfo.setBadJudgeTimes(userCredit.getBadJudgeTimes());//差评
            userInfo.setLastTradeTime(userCredit.getLastTradeTime());// 最后交易时间

            //好评率
            if (userCredit.getJudgeTimes() != null && userCredit.getJudgeTimes() > 0) {
                userInfo.setGoodJudgeRate(new BigDecimal(userCredit.getGoodJudgeTimes().doubleValue()
                        / userCredit.getJudgeTimes()).multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP));
            } else {
                userInfo.setGoodJudgeRate(BigDecimal.ZERO);
            }
            //放行时间
            if (userCredit.getSellSucessTimes() != null && userCredit.getSellSucessTimes() > 0) {
                userInfo.setConfirmMinute(userCredit.getConfirmMinute() / userCredit.getTradeSucessTimes());
            } else {
                userInfo.setConfirmMinute(0);
            }
        } else {
            userInfo.setTradeTimes(0); //交易总次数
            userInfo.setGoodJudgeTimes(0); //好评次数
            userInfo.setCommonJudgeTimes(0);
            userInfo.setBadJudgeTimes(0);
            userInfo.setGoodJudgeRate(BigDecimal.ZERO);
            userInfo.setConfirmMinute(0);
        }

        userInfo.setRegistDate(user.getCreateDate());
        //获取普通用户信息
        userWebLoginInfo.setUserInfo(userInfo);
        userWebLoginInfo.setToken(token);
        return userWebLoginInfo;
    }

    /**
     * 用户信息处理
     * @param cacheUserInfo
     * @return
     */
    public UserWebLoginInfo getUserWebLoginInfo(CacheUserInfo cacheUserInfo) {
        return this.getUserWebLoginInfo(null,cacheUserInfo);
    }




}
