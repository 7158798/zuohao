package com.jucaifu.common.dfs;

import com.jucaifu.common.exceptions.biz.BizException;

/**
 * FileBizException
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/12/4.
 */
public class FileBizException extends BizException {

    public FileBizException(String msg) {
        super(msg);
    }

    public FileBizException(Integer code, String msg) {
        super(code, msg);
    }

    public FileBizException(Integer code, String msgFormat, Object... args) {
        super(code, msgFormat, args);
    }
}
