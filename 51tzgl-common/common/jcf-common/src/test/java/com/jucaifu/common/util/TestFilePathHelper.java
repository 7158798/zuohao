package com.jucaifu.common.util;

import com.base.BaseTest;
import com.jucaifu.common.constants.FilePathConstant;
import com.jucaifu.common.log.LOG;
import com.jucaifu.common.util.file.FilePathHelper;

/**
 * TestFilePathHelper
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/9/16.
 */
public class TestFilePathHelper extends BaseTest implements FilePathConstant {

    @Override
    public void doTest() {

        LOG.d(this, FilePathHelper.getClassPath());

        LOG.d(this, FilePathHelper.getClassResources());

//-- /jucaifu_server/jucaifu-architecture/jucaifu-common/target/test-classes/../../target/test-classes
        String saveDir = FilePathHelper.getClassPath() + UPLOAD_PATH_FILE;

        LOG.d(this, saveDir);
    }

}
