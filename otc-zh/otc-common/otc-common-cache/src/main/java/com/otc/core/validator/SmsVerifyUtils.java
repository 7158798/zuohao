package com.otc.core.validator;

import com.jucaifu.common.constants.StringPool;
import com.jucaifu.common.log.LOG;
import com.jucaifu.common.property.PropertiesUtils;
import com.jucaifu.common.util.SerializeHelper;
import com.jucaifu.common.validate.ValidateManager;
import com.jucaifu.common.validate.ValidateType;
import com.otc.core.cache.CacheHelper;
import com.otc.core.cache.SMSCacheManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.util.*;

/**
 * Created by zhaiyz on 15-11-19.
 */
public final class SmsVerifyUtils {


    //从配置文件中读取验证码模板
    public static final String REGISTER  = PropertiesUtils.getProperty("alidayu.ver.register");
    public static final String RESET_PWD  = PropertiesUtils.getProperty("alidayu.ver.reset_pwd");
    public static final String LOGIN  = PropertiesUtils.getProperty("alidayu.ver.login");
    public static final String BIN_PHONE  = PropertiesUtils.getProperty("alidayu.ver.bin_phone");
    public static final String SET_PWD  = PropertiesUtils.getProperty("alidayu.ver.set_pwd");
    public static final String CHANGE_PHONE  = PropertiesUtils.getProperty("alidayu.ver.change_phone");
    public static final String DEFAULT_TEMPLATE_CODE = PropertiesUtils.getProperty("alidayu.templateCode");

    /**
     * 短信验证码失败时间：30分钟
     */
    private static final long INTERVAL = 30 * 60 * 1000;

    /**
     *
     */
    private static final long REPEAT = 1*60*1000;

    /**
     * 短信验证码记录超时时间24小时
     */
    private static final Integer TIMEOUT = 24 * 60 * 60;

    /**
     * 一天中一个业务短信最多发送次数
     */
    private static final int MAX_COUNT = 3;

    /**
     * 短信业务在redis中的schema
     */
    private static final String REDIS_SMS_SCHEMA = "email";

    private SmsVerifyUtils() {
    }


    /**
     * Gets verify code.
     *
     * @param email the email address
     * @param smsType the sms type
     * @return the verify code
     */
    public static SmsCodeGet getVerifyCode(String email, SmsType smsType,Long maxAccount) {
        // 手机号格式检查
        if (!ValidateManager.validateValue(email, ValidateType.EMAIL_ADDRESS.getValidateRuleRegex())) {
            return new SmsCodeGet(false, "邮箱格式不正确");
        }

        // 短信类型检查
        if (smsType == null) {
            return new SmsCodeGet(false, "验证类型为空");
        }

        // 根据phoneNumber和smsType查询出验证短信发送记录
        SmsCodeBean smsValidatorVo = getSmsValidatorVo(email, smsType);

        String code;

        if (smsValidatorVo != null) {
            Date now = new Date();
            Date createDate = smsValidatorVo.getCreateDate();
            int count;
            if (DateUtils.isSameDay(now, createDate)) {
                // 如果在同一天，检查发送次数
                if (smsValidatorVo.getCount() >= maxAccount) {
                    return new SmsCodeGet(false, "您今日可获取验证码次数额度已用完。");
                }

                if(isRepeat(createDate,now)){
                    return new SmsCodeGet(false, "发送频繁，请稍后再试");
                }

                // 发送次数加1
                count = smsValidatorVo.getCount() + 1;

                if (isExpired(createDate, now) || smsValidatorVo.isExpired()) {
                    // 验证码过期后，重新生成
                    code = getCode();
                } else {
                    // 没有过期，使用上次一生成的验证码
                    code = smsValidatorVo.getCode();
                }
            } else {
                // 今天没有有发送记录
                // 计数器从1开始
                count = 1;
                // 重新生成验证码
                code = getCode();
            }

            smsValidatorVo.setCount(count);
            smsValidatorVo.setCode(code);
            smsValidatorVo.setCreateDate(now);


        } else {
            // 没有发送记录
            smsValidatorVo = new SmsCodeBean();

            code = getCode();
            smsValidatorVo.setPhoneNumber(email);
            smsValidatorVo.setSmsType(smsType);
            smsValidatorVo.setCode(code);
            smsValidatorVo.setCount(1);
            smsValidatorVo.setCreateDate(new Date());
        }

        smsValidatorVo.setExpired(false);

        // 保存至redis
        setSmsValidatorVo(smsValidatorVo);

        return new SmsCodeGet(true, code);
    }

    /**
     * Verify sms code verify.
     *
     * @param email the phone number
     * @param smsType the sms type
     * @param code the code
     * @return the sms code verify
     */
    public static SmsCodeVerify verify(String email, SmsType smsType, String code) {

        if (StringUtils.isEmpty(code)){
            return new SmsCodeVerify(false, "验证码不能为空");
        }

        // 手机号格式检查
        if (!ValidateManager.validateValue(email, ValidateType.EMAIL_ADDRESS.getValidateRuleRegex())) {
            return new SmsCodeVerify(false, "邮箱地址格式不正确");
        }

        // 验证码格式检查
        if (!ValidateManager.validateValue(code, ValidateType.VERIFICATION_CODE.getValidateRuleRegex())) {
            return new SmsCodeVerify(false, "验证码格式不正确");
        }

        // 短信类型检查
        if (smsType == null) {
            return new SmsCodeVerify(false, "验证类型为空");
        }

        // 根据phoneNumber和smsType查询出验证短信发送记录
        SmsCodeBean smsValidatorVo = getSmsValidatorVo(email, smsType);
        if (smsValidatorVo == null) {
            return new SmsCodeVerify(false, "验证码不存在");
        }

        // 查询发送时间与现在的时间间隔是否大于30分钟
        Date createDate = smsValidatorVo.getCreateDate();
        Date now = new Date();
        if (isExpired(createDate, now)) {
            return new SmsCodeVerify(false, "验证码已过期");
        }

        // 判断验证码是否被使用过
        if (smsValidatorVo.isExpired()) {
            return new SmsCodeVerify(false, "验证码已失效");
        }

        // 验证成功返回true，失败返回false
        boolean effective = StringUtils.equals(code, smsValidatorVo.getCode());
        if (effective) {
            // 设置此记录为过期
            smsValidatorVo.setExpired(true);
            // 更新redis
            setSmsValidatorVo(smsValidatorVo);

            return new SmsCodeVerify(true, null);
        } else {
            return new SmsCodeVerify(false, "验证码有误！");
        }
    }


    /**
     * 取得保存到redis中的key
     *
     * @param email 手机号码
     * @param smsType     短信类型
     * @return redis中的key
     */
    private static String getRedisKey(String email, SmsType smsType) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(REDIS_SMS_SCHEMA);
        stringBuilder.append(StringPool.COLON);
        stringBuilder.append(email);
        stringBuilder.append(StringPool.COLON);
        if (smsType != null) {
            stringBuilder.append(smsType.toString().toLowerCase());
        }
        return stringBuilder.toString();
    }


    /**
     * 取得保存到redis中的key 模糊查找
     *
     * @param phoneNumber 手机号码
     * @param smsType     短信类型
     * @return redis中的key
     */
    private static String getRedisKeys(String phoneNumber, SmsType smsType) {
        StringBuilder stringBuilder = new StringBuilder();

        if(StringUtils.isBlank(phoneNumber) && smsType == null){
            stringBuilder.append(REDIS_SMS_SCHEMA);
            return stringBuilder.toString();
        }

        if(StringUtils.isBlank(phoneNumber) && smsType != null){
            return smsType.toString().toLowerCase();
        }

        if(StringUtils.isNotBlank(phoneNumber) && smsType == null){
            stringBuilder.append(REDIS_SMS_SCHEMA);
            stringBuilder.append(StringPool.COLON);
            stringBuilder.append(phoneNumber);
            stringBuilder.append(StringPool.COLON);
            return stringBuilder.toString();
        }

        stringBuilder.append(REDIS_SMS_SCHEMA);
        stringBuilder.append(StringPool.COLON);
        stringBuilder.append(phoneNumber);
        stringBuilder.append(StringPool.COLON);
        stringBuilder.append(smsType.toString().toLowerCase());

        return stringBuilder.toString();
    }

    /**
     * Gets console sms record list.
     *
     * @param phoneNumber the phone number
     * @param smsType the sms type
     * @return the console sms record list
     */
    public static List<SmsCodeBean> getUserSmsRecordList(String phoneNumber, SmsType smsType) throws Exception {

        List<SmsCodeBean> list = new ArrayList<>();

        String key = getRedisKeys(phoneNumber, smsType);
        Set<byte[]> set = CacheHelper.keys(key);

        SmsCodeBean smsCodeBean;

        for (byte[] k : set) {
            key = SerializeHelper.unSerialize(k);
            smsCodeBean = SMSCacheManager.getSmsObj(key);
            smsCodeBean.setId(key);

            list.add(smsCodeBean);
        }

        LOG.d("getUserSmsRecordList", list);

        return list;
    }


    /**
     * Delete console sms record.
     *
     * @param id the id
     * @return the boolean
     */
    public static final boolean deleteUserSmsRecord(String id) {
        return SMSCacheManager.deleteSmsObj(id);
    }

    /**
     * 通过phoneNumber和smsType从redis中取得SmsValidatorVo对象，如果没有返回null
     *
     * @param email 手机号码
     * @param smsType     短信类型
     * @return SmsValidatorVo对象，如果没有返回null
     */
    private static SmsCodeBean getSmsValidatorVo(String email, SmsType smsType) {
        String key = getRedisKey(email, smsType);
        return SMSCacheManager.getSmsObj(key);
    }


    /**
     * 保存smsValidatorVo对象
     *
     * @param smsValidatorVo 短信验证码对象
     */
    private static void setSmsValidatorVo(SmsCodeBean smsValidatorVo) {
        String key = getRedisKey(smsValidatorVo.getPhoneNumber(), smsValidatorVo.getSmsType());
        smsValidatorVo.setUpdateDate(new Date());
        SMSCacheManager.saveSmsObj(key, smsValidatorVo, TIMEOUT);
    }

    /**
     * 判断验证码时间上是否过期
     *
     * @param fromDate 开始时间
     * @param toDate   结束时间
     * @return 是否有效
     */
    private static boolean isExpired(Date fromDate, Date toDate) {
        long fromDateMillisecond = fromDate.getTime();
        long toDateMillisecond = toDate.getTime();

        return toDateMillisecond - fromDateMillisecond > INTERVAL;
    }

    /**
     * 判断验证码是否重复发送
     *
     * @param fromDate 开始时间
     * @param toDate   结束时间
     * @return 是否有效
     */
    private static boolean isRepeat(Date fromDate, Date toDate) {
        long fromDateMillisecond = fromDate.getTime();
        long toDateMillisecond = toDate.getTime();

        return toDateMillisecond - fromDateMillisecond <= REPEAT;
    }

    /**
     * 生成6位数字，范围100000~999999
     *
     * @return 6位数字
     */
    private static String getCode() {
        Random random = new Random();
        int x = random.nextInt(899999);
        return String.valueOf(x + 100000);
    }

}
