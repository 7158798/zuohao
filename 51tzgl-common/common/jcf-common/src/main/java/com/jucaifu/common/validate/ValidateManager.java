package com.jucaifu.common.validate;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jucaifu.common.log.LOG;
import com.jucaifu.common.util.ValueHelper;
import org.apache.commons.lang3.StringUtils;


/**
 * ****************************************
 *
 * @author : scofieldcai@126.com
 * @ClassName : SCValidateManager
 * @date : 2014-3-12 下午2:33:35
 * @Description: 数据校验管理类
 * ****************************************
 **/
public class ValidateManager {

    /*****************************************
     * @param recever     the recever
     * @param validateObj the validate obj
     * @return string
     * @author : scofieldcai@126.com
     * @Title : validateObj
     * @returnType : String
     * @Description: 校验多个参数
     */
    public static String validateObj(Object recever, Object... validateObj) {

        String result = null;

        boolean validateAll = false;
        HashSet<Object> validateSet = new HashSet();

        if (validateObj == null) {
            validateAll = true;
        } else {
            validateAll = false;

            for (Object obj : validateObj) {
                validateSet.add(obj);
            }
        }

        List<ValidateEntity> validateEntityList = new ArrayList();

        Field[] fieldArray = recever.getClass().getDeclaredFields();

        Object obj;
        ValidateEntity itemEntity;
        for (Field field : fieldArray) {
            try {
                if (field.isAnnotationPresent(DataValidateRuleAnno.class)) {
                    field.setAccessible(true);
                    obj = field.get(recever);

                    if (validateAll || validateSet.contains(obj)) {
                        itemEntity = new ValidateEntity();
                        itemEntity.field = field;
                        itemEntity.obj = obj==null?"":obj;
                        validateEntityList.add(itemEntity);
                    }

                }
            } catch (Exception e) {
                LOG.e(ValidateManager.class, "", e);
            }
        }

        String itemResult;
        for (ValidateEntity item : validateEntityList) {
            itemResult = validateSingleObj(item);

            if (ValueHelper.checkStringIsEmpty(itemResult)) {
                continue;
            } else {
                result = itemResult;
                break;
            }
        }
        return result;
    }


    /*****************************************
     * @param itemObj
     * @return
     * @author : scofieldcai@126.com
     * @Title : validateSingleObj
     * @returnType : String
     * @Description: 校验单个对象
     *****************************************/
    private static String validateSingleObj(ValidateEntity itemObj) {
        Field field = itemObj.field;
        Object obj = itemObj.obj;

        //校验结果数据类型。
        String validateResult = null;

        Class<?> cls = obj.getClass();
        String content = getObjContent(obj);

        if (field.isAnnotationPresent(DataValidateRuleAnno.class)) {
            DataValidateRuleAnno dataValidateRuleAnno = field.getAnnotation(DataValidateRuleAnno.class);
            ValidateType validateType = dataValidateRuleAnno.validateType();

            boolean needValidateWhenEmpty = dataValidateRuleAnno.needValidateWhenEmpty();
            String emptyMSG = dataValidateRuleAnno.emptyMSG();
            String errorMSG = dataValidateRuleAnno.errorMSG();

            if (validateType != ValidateType.CUSTOM_TYPE) {
                if (ValueHelper.checkStringIsEmpty(emptyMSG)) {
                    emptyMSG = validateType.getEmptyMSG();
                }
                if (ValueHelper.checkStringIsEmpty(errorMSG)) {
                    errorMSG = validateType.getErrorMSG();
                }
            }

            if (ValueHelper.checkStringIsEmpty(content)) {
                if (needValidateWhenEmpty) {
                    validateResult = emptyMSG;
                } else {
                    return null;
                }

            } else {
                String validateRuleRegex;
                switch (validateType) {
                    case NOT_EMPTY: {
                        //已经校验成功了。仅仅非空校验。
                        return null;
                    }
                    case CUSTOM_TYPE: {
                        // 仅仅自定义的时候，才使用注入的正值表达式。
                        validateRuleRegex = dataValidateRuleAnno.validateRuleRegex();
                        break;
                    }
                    default: {
                        validateRuleRegex = validateType.getValidateRuleRegex();
                        break;
                    }
                }

                if (validateValue(content, validateRuleRegex)) {
                    // 验证通过
                    validateResult = null;
                } else {
                    // 验证失败错误信息
                    validateResult = errorMSG;
                }
            }
        } else {
            LOG.e(ValidateManager.class, "hava no DataValidateRule Annotation, can not validate data. ");
        }

        return validateResult;
    }


    /*****************************************
     * @param obj
     * @return
     * @author : scofieldcai@126.com
     * @Title : getObjContent
     * @returnType : String
     * @Description: 获取对象对应的内容
     *****************************************/
    private static String getObjContent(Object obj) {
        String content = null;
        if (obj instanceof String) {
            content = (String) obj;
        } else {
            if (obj != null) {
                LOG.e(ValidateManager.class, "Validate obj [Exception] : type is " + obj.getClass().getSimpleName());
            }
        }
        return content;
    }


    /*****************************************
     * @param content
     * @param regexString
     * @return
     * @author : scofieldcai@126.com
     * @Title : validateValue
     * @returnType : boolean
     * @Description: 根据正值格式校验内容。
     *****************************************/
    public static boolean validateValue(String content, String regexString) {
        if(StringUtils.isEmpty(content) || StringUtils.isEmpty(regexString)){
            return false;
        }
        Pattern pattern = Pattern.compile(regexString);
        Matcher matcher = pattern.matcher(content);
        return matcher.matches();
    }


    private static class ValidateEntity {
        public Field field;
        public Object obj;
    }

}
