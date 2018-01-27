package com.jucaifu.common.util;

import com.base.BaseTest;
import com.jucaifu.common.util.mysql.MySqlHelper;

/**
 * TestMySqlHelper
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/9/25.
 */
public class TestMySqlHelper extends BaseTest{


    @Override
    public void doTest() {
        MySqlHelper mySqlHelper = MySqlHelper.getInstance("db_user","root","123456");
        mySqlHelper.backup("10.211.55.4","/Users/scofieldcai-dev/Java/log/backup_db_user.sql");
    }
}
