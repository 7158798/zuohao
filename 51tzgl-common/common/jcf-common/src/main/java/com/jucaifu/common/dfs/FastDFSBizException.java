package com.jucaifu.common.dfs;

import com.jucaifu.common.exceptions.biz.BizException;

/**
 * FastDFSBizException
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 16/1/7.
 */
public class FastDFSBizException extends BizException {
    public FastDFSBizException() {
    }

    public FastDFSBizException(String message, Throwable cause) {
        super(message, cause);
    }

    public FastDFSBizException(Throwable cause) {
        super(cause);
    }

    public FastDFSBizException(String msg) {
        super(msg);
    }

    public FastDFSBizException(Integer code, String msg) {
        super(code, msg);
    }
}
