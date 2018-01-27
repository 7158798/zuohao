package com.jucaifu.common.validate;

/**
 * ****************************************
 *
 * @author : scofieldcai@126.com
 * @ClassName : IValidate
 * @date : 2014-3-12 下午2:20:04
 * @Description: 控制数据是否需要校验。
 * ****************************************
 **/
public interface IValidate {

    /*****************************************
     * @return
     * @author : scofieldcai@126.com
     * @Title : needValidate
     * @returnType : boolean
     * @Description: 控制是否需要执行数据校验流程。
     *****************************************/
    public boolean needValidate();

    /*****************************************
     * @return
     * @author : scofieldcai@126.com
     * @Title : executeDataValidate
     * @returnType : String 返回校验错误信息. null:表示校验通过 不为null:表示校验失败,未通过。
     * @Description: 执行数据校验
     *****************************************/
    public String executeDataValidate();

    /*****************************************
     * @param errorInfo 错误信息
     * @author : scofieldcai@126.com
     * @Title : onValidateError
     * @returnType : void
     * @Description: 在校验失败的情况下调用。
     *****************************************/
    public void onValidateError(String errorInfo);
}
