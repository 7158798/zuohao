package com.otc.api.web.base;

import com.jucaifu.common.constants.WebConstant;
import com.otc.api.web.service.WebService;
import com.otc.common.api.controller.AbsBaseController;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * BaseWebCtrl
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/12/22.
 */
public class BaseWebCtrl extends AbsBaseController implements WebConstant {

    @Resource(name = "webService")
    protected WebService otc;

}
