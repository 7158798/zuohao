package com.otc.api.console.ctrl.virtual;

import com.jucaifu.common.log.LOG;
import com.otc.api.console.base.BaseConsoleCtrl;
import com.otc.api.console.constants.ConsoleApiPool;
import com.otc.api.console.ctrl.virtual.request.PoolReq;
import com.otc.common.api.packet.web.WebApiResponse;
import com.otc.facade.virtual.enums.PoolStatus;
import com.otc.facade.virtual.pojo.bean.PoolBean;
import com.otc.facade.virtual.pojo.vo.PoolVo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 可以地址池
 * Created by lx on 17-4-21.
 */
@RestController
public class PoolCtrl extends BaseConsoleCtrl implements ConsoleApiPool {

    /**
     * Get used count web api response.
     *
     * @param queryJson the query json
     * @return the web api response
     */
    @RequestMapping(value = COUNT_USED_POOL_CONSOLE_API,method = RequestMethod.GET)
    public WebApiResponse getUsedCount(@PathVariable String queryJson){
        PoolReq req = encodeJsonStr2Obj(queryJson, PoolReq.class);

        PoolVo vo = new PoolVo();
        vo.setStatus(PoolStatus.UNUSED.getCode());
        vo.setCoinId(req.getCoinId());
        vo.setNowPage(req.fetchPageFilterPage());
        vo.setPageShow(req.fetchPageFilterSize());

        vo = otc.poolConsoleFacade.queryCountByConditionPage(vo);
        List<PoolBean> respList = vo.fatchTransferList();
        LOG.d(this,respList);
        return buildWebApiPageResponse(vo,respList);
    }
}
