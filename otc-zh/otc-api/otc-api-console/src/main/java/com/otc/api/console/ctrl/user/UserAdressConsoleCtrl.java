package com.otc.api.console.ctrl.user;

import com.jucaifu.common.log.LOG;
import com.jucaifu.common.util.JsonHelper;
import com.jucaifu.common.util.ReflectHelper;
import com.otc.api.console.base.BaseConsoleCtrl;
import com.otc.api.console.constants.ConsoleApiUserAdress;
import com.otc.api.console.ctrl.user.reponse.UserAdressListResp;
import com.otc.api.console.ctrl.user.req.UserAdressListReq;
import com.otc.common.api.packet.web.WebApiResponse;
import com.otc.facade.user.exceptions.UserBizException;
import com.otc.facade.user.pojo.poex.UserReportEx;
import com.otc.facade.user.pojo.vo.UserVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fenggq on 17-5-2.
 */
@Controller
public class UserAdressConsoleCtrl extends BaseConsoleCtrl implements ConsoleApiUserAdress {

    /**
     * 条件查询用户充值地址列表
     */
    @RequestMapping(value = LIST_QUERY_API, method = RequestMethod.GET)
    @ResponseBody
    public WebApiResponse getUserAddressListByConditionPage(@PathVariable String queryJson)throws Exception {

        LOG.dStart(this, "查询console用户地址列表开始");
        UserAdressListReq req = JsonHelper.jsonStr2Obj(queryJson, UserAdressListReq.class);
        if (req == null) {
            throw new UserBizException("请求参数为空");
        }
        Long coinId = req.getCoinId();
        String userInfo = req.getUserInfo();

        UserVo vo = new UserVo();
        vo.setPageShow(req.fetchPageFilterSize());
        vo.setNowPage(req.fetchPageFilterPage());

        //会员信息
        vo.setUserInfo(userInfo);
        if(coinId != null && coinId != 0){
            vo.setCoinId(coinId);
        }

        vo = otc.userConsoleFacade.selectUserAddressList(vo);
        List<UserReportEx> list = vo.fatchTransferList();

        List<UserAdressListResp> respList = new ArrayList<>();

        for(UserReportEx ex:list){
            UserAdressListResp resp = new UserAdressListResp();
            ReflectHelper.injectAttrFromSrcObj(ex,resp);
            respList.add(resp);
        }
        LOG.d(this, JsonHelper.obj2JsonStr(respList));

        LOG.dEnd(this, "查询console用户地址列表结束");
        return buildWebApiPageResponse(vo, respList);
    }

}
