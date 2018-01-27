package com.jucaifu.common.validate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.base.BaseTest;
import com.jucaifu.common.log.LOG;
import com.jucaifu.common.pojo.TestUserPo;
import org.junit.Test;

/**
 * ValidateManager Tester.
 *
 * @author scofieldcai-dev
 * @version 1.0
 * @since 十一月 18, 2015
 */
public class ValidateManagerTest extends BaseTest {


    @Override
    public void doTest() {
        //TODO: Test goes here...
    }

    /**
     * Method: validateObj(Object recever, Object... validateObj)
     */
    @Test
    public void testValidateObj() throws Exception {
        TestUserPo userPo = new TestUserPo();
//        userPo.setName();
        userPo.setMobileNo("15500000001");
        userPo.setPwd("123456");

        // 1.校验所有的
        String result = ValidateManager.validateObj(userPo);
        LOG.d(this, "result---->" + result);

//         2.校验指定的
        userPo.setMobileNo("1234");
        result = ValidateManager.validateObj(userPo, userPo.getMobileNo());
        if (result == null) {
            LOG.d(this, "result---->成功");
        } else {
            LOG.d(this, "result---->" + result);
        }

        userPo.setMobileNo("");
        result = ValidateManager.validateObj(userPo, userPo.getMobileNo());
        if (result == null) {
            LOG.d(this, "result---->手机号码为空的时候跳过校验");
        }

        //========================
        // 单个字段的校验
        //========================
        boolean success = ValidateManager.validateValue("15500000001", ValidateType.PHONE_NUMBER.getValidateRuleRegex());
        LOG.d(this, "success---->" + success);
    }

    /**
     * Method: validateValue(String content, String regexString)
     */
    @Test
    public void testValidateValue() throws Exception {
        String check = ValidateType.EMAIL_ADDRESS.getValidateRuleRegex();
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher("dffdfdf@126.com");
        boolean isMatched = matcher.matches();
        System.out.println(isMatched);
    }


    /**
     * Method: validateSingleObj(ValidateEntity itemObj)
     */
    @Test
    public void testValidateSingleObj() throws Exception {
        boolean success = ValidateManager.validateValue("dffdfdf@126.com",ValidateType.EMAIL_ADDRESS.getValidateRuleRegex());
        LOG.d(this,"success---->"+success);
    }


} 
