package com.jucaifu.common.util;

import java.util.Locale;

import com.base.BaseTest;
import com.jucaifu.common.log.LOG;

/**
 * Created by scofieldcai-dev on 15/9/8.
 * <p>
 * i18n_en_US.properties
 */
public class TestResourceHelper extends BaseTest {

    @Override
    public void doTest() {
        LOG.d(this, ResourcesHelper.getKeyList("i18n"));
        LOG.d(this, ResourcesHelper.getValue("i18n", "username"));
        LOG.d(this, ResourcesHelper.getValue("i18n", "password"));


        //从资源文件取配置信息，不用传参
        LOG.d(this, ResourcesHelper.getValue("error", "101"));

        //从资源文件取配置信息，需要传参
        LOG.d(this, ResourcesHelper.getValue("error", "102", new Object[]{10, 20}));

        //根据操作系统环境获取语言环境
        Locale locale = Locale.getDefault();
        LOG.d(this, locale);

        //自定义Locale
        Locale customLocal = new Locale("zh", "XINXIN");
        LOG.d(this, customLocal);
        LOG.d(this, ResourcesHelper.getValue(customLocal, "error", "101"));
        LOG.d(this, ResourcesHelper.getValue(customLocal, "error", "102", new Object[]{10, 20}));
    }
}
