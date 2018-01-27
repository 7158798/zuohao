package com.szzc.api.app.base;

import com.szzc.api.app.service.AppService;
import com.szzc.common.api.controller.AbsAppController;
import com.jucaifu.common.constants.CommonConstant;
import com.jucaifu.common.constants.WebConstant;

import javax.annotation.Resource;

/**
 * BaseAppCtrl
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/12/22.
 */
public class BaseAppCtrl extends AbsAppController implements WebConstant, CommonConstant {

    @Resource(name = "appService")
    protected AppService szzc;
}
