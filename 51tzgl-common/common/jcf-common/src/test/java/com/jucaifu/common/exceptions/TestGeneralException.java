package com.jucaifu.common.exceptions;

import com.base.BaseTest;
import com.jucaifu.common.exceptions.general.GeneralException;
import com.jucaifu.common.exceptions.general.GeneralExceptionType;
import com.jucaifu.common.log.LOG;

/**
 * Created by scofieldcai-dev on 15/8/31.
 */
public class TestGeneralException extends BaseTest {


    @Override
    public void doTest() {
        try {
//            GeneralException.throwException("1","用户名密码错误");

            ExceptionHelper.throwException(GeneralExceptionType.NO_PERMISSION_EXCEPTION);

        } catch (GeneralException e) {
            LOG.e(this, e, e);
            e.printStackTrace();
        }
    }
}
