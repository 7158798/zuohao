package com.base.facade.exception;

import com.jucaifu.common.exceptions.biz.BizException;

/**
 * @author luwei
 * @Date 16-10-8 下午4:26
 */
public class BaseCommonBizException extends BizException {

    /** 请求参数不正确 */
    public static final Integer REQUEST_PARAM_INCORRENT = 10059999;
    /** 参数格式不正确 */
    public static final Integer PARAM_FORMAT_INCORRECT = 10050001;
    /** 参数不能为空 */
    public static final Integer PARAM_IS_NOT_BLANK = 10050002;
    /** 数据不存在 */
    public static final Integer DATA_NOT_EXIST = 10050006;

    public BaseCommonBizException(String msg) {
        super(msg);
    }

    public BaseCommonBizException(Integer code, String msg) {
        super(code, msg);
    }

    public BaseCommonBizException(Integer code, String msgFormat, Object... args) {
        super(code, msgFormat, args);
    }

}
