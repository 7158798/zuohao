package com.otc.api.console.ctrl.coin;

import com.base.wallet.utils.BTCMessage;
import com.base.wallet.utils.BTCUtils;
import com.jucaifu.common.log.LOG;
import com.jucaifu.common.util.JsonHelper;
import com.otc.api.console.base.BaseConsoleCtrl;
import com.otc.api.console.constants.ConsoleApiCoin;
import com.otc.api.console.ctrl.coin.request.*;
import com.otc.api.console.ctrl.coin.response.VirtualCoinListResp;
import com.otc.api.console.ctrl.coin.response.VirtualCoinTypeResp;
import com.otc.api.console.exceptions.ConsoleBizException;
import com.otc.common.api.packet.web.WebApiResponse;
import com.otc.common.api.packet.web.request.WebApiBaseReq;
import com.otc.facade.virtual.enums.VirtualCoinStatus;
import com.otc.facade.virtual.pojo.po.VirtualCoin;
import com.otc.facade.virtual.pojo.po.Withdrawfees;
import com.otc.facade.virtual.pojo.vo.VirtualCoinVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by lx on 17-4-24.
 */
@RestController
public class VirtualCoinCtrl extends BaseConsoleCtrl implements ConsoleApiCoin {

    /**
     * Get coin list web api response.
     *
     * @param queryJson the query json
     * @return the web api response
     */
    @RequestMapping(value = LIST_COIN_CONSOLE_API,method = RequestMethod.GET)
    public WebApiResponse getCoinList(@PathVariable String queryJson){
        WebApiBaseReq req = encodeJsonStr2Obj(queryJson, WebApiBaseReq.class);

        VirtualCoinVo vo = new VirtualCoinVo();
        vo.setNowPage(req.fetchPageFilterPage());
        vo.setPageShow(req.fetchPageFilterSize());

        vo = otc.virtualCoinConsoleFacade.queryCountByConditionPage(vo);
        List<VirtualCoin> list = vo.fatchTransferList();
        List<VirtualCoinListResp> respList = new ArrayList<>();
        VirtualCoinListResp resp;
        for (VirtualCoin coin : list){
            resp = new VirtualCoinListResp();
            resp.copy(coin);
            respList.add(resp);
        }
        LOG.d(this,respList);
        return buildWebApiPageResponse(vo,respList);
    }

    /**
     * 测试钱包
     *
     * @param queryJson the query json
     * @return web api response
     * @throws Exception
     */
    @RequestMapping(value = TEST_WALLET_CONSOLE_API,method = RequestMethod.GET)
    public WebApiResponse testWallet(@PathVariable String queryJson) {
        LOG.dStart(this, "测试钱包开始");
        WebApiBaseReq req = encodeJsonStr2Obj(queryJson, WebApiBaseReq.class);
        if (req == null){
            throw new ConsoleBizException("请求参数不能空");
        }
        VirtualCoin coin = otc.virtualCoinConsoleFacade.isExist(req.getId());
        BTCMessage btcMessage = new BTCMessage() ;
        btcMessage.setACCESS_KEY(coin.getAccessKey());
        btcMessage.setIP(coin.getIp());
        btcMessage.setPORT(coin.getPort());
        btcMessage.setSECRET_KEY(coin.getSecretKey());

        BTCUtils btcUtils = new BTCUtils(btcMessage);
        String msg;
        try {
            double amount = btcUtils.getbalanceValue();
            msg = "测试钱包成功,余额= " + amount;
        } catch (Exception e) {
            LOG.e(this,e.getMessage(),e);
            msg = "测试钱包失败";
        }
        LOG.dEnd(this, msg);
        return buildWebApiResponse(SUCCESS,msg);
    }

    /**
     * Get coin type list web api response.
     *
     * @param queryJson the query json
     * @return the web api response
     */
    @RequestMapping(value = LIST_COIN_TYPE_CONSOLE_API,method = RequestMethod.GET)
    public WebApiResponse getCoinTypeList(@PathVariable String queryJson){

        Map<Long,VirtualCoin> map = otc.virtualCoinConsoleFacade.getAllVirtualCoin();
        List<VirtualCoinTypeResp> respList = new ArrayList<>();
        VirtualCoinTypeResp resp;
        for (Map.Entry<Long,VirtualCoin> entry : map.entrySet()){
            resp = new VirtualCoinTypeResp();
            resp.setCoinId(entry.getKey());
            resp.setCoinName(entry.getValue().getName());
            respList.add(resp);
        }
        LOG.d(this,respList);
        return buildWebApiResponse(SUCCESS, respList);
    }

    /**
     * Create address web api response.
     *
     * @param req the req
     * @return the web api response
     */
    @RequestMapping(value = CREATE_ADDRESS_CONSOLE_API,method = RequestMethod.POST)
    public WebApiResponse createAddress(@RequestBody VirtualAddressReq req){
        if (req == null){
            throw new ConsoleBizException("请求参数不能空");
        }
        if (req.getId() == null){
            throw new ConsoleBizException("虚拟币ID不能空");
        }
        VirtualCoin coin = otc.virtualCoinConsoleFacade.isExist(req.getId());
        if (StringUtils.isEmpty(coin.getAccessKey())){
            throw new ConsoleBizException("accessKey不能为空");
        }
        if (StringUtils.isEmpty(coin.getSecretKey())){
            throw new ConsoleBizException("secretKey不能为空");
        }
        if (StringUtils.isEmpty(coin.getIp())){
            throw new ConsoleBizException("钱包id不能为空");
        }
        if (StringUtils.isEmpty(coin.getPort())){
            throw new ConsoleBizException("钱包端口不能为空");
        }
        if (StringUtils.isEmpty(req.getWalletPwd())){
            throw new ConsoleBizException("钱包密码不能为空");
        }
        new Thread(new Runnable() {
            public void run() {
                try {
                    otc.virtualCoinConsoleFacade.createAddress(coin,req.getWalletPwd());
                } catch (Exception e) {
                    LOG.e(this,coin.getName() + "->生成地址失败",e);
                }
            }
        }).start() ;
        return buildWebApiResponse("后台执行中!",null);
    }

    /**
     * Get coin type detail web api response.
     *
     * @param queryJson the query json
     * @return the web api response
     */
    @RequestMapping(value = DETAIL_COIN_CONSOLE_API,method = RequestMethod.GET)
    public WebApiResponse getCoinTypeDetail(@PathVariable String queryJson){
        WebApiBaseReq req = encodeJsonStr2Obj(queryJson, WebApiBaseReq.class);
        VirtualCoin coin = otc.virtualCoinConsoleFacade.isExist(req.getId());
        VirtualCoinListResp resp = new VirtualCoinListResp();
        resp.copy(coin);
        return buildWebApiResponse(SUCCESS,resp);
    }

    /**
     * Cut status web api response.
     *
     * @param req the req
     * @return the web api response
     */
    @RequestMapping(value = CUT_STATUS_COIN_CONSOLE_API,method = RequestMethod.POST)
    public WebApiResponse cutStatus(@RequestBody VirtualCoinCutReq req){
        LOG.dStart(this, "切换数据状态开始");
        if (req == null){
            throw new ConsoleBizException("请求参数不能空");
        }
        if (req.getCoinId() == null){
            throw new ConsoleBizException("虚拟币ID不能空");
        }
        if (StringUtils.isEmpty(req.getStatus())){
            throw new ConsoleBizException("虚拟币状态不能空");
        }
        VirtualCoinStatus status = VirtualCoinStatus.statusMap.get(req.getStatus());
        if (status == null){
            throw new ConsoleBizException("虚拟币状态不正确");
        }
        if (VirtualCoinStatus.ENABLED.getCode().equals(req.getStatus())){
            // 启用需要密码
            if (StringUtils.isEmpty(req.getWalletPwd())){
                throw new ConsoleBizException("钱包密码不能为空");
            }
        }
        otc.virtualCoinConsoleFacade.updateStatus(req.getCoinId(),req.getStatus(),req.getWalletPwd());
        LOG.dEnd(this, "切换数据状态结束");
        return buildWebApiResponse(SUCCESS,null);
    }

    /**
     * Add coin web api response.
     *
     * @param req the req
     * @return the web api response
     */
    @RequestMapping(value = ADD_COIN_CONSOLE_API,method = RequestMethod.POST)
    public WebApiResponse addCoin(@RequestBody VirtualCoinAddReq req){
        LOG.dStart(this, "新增币种开始");
        if (req == null){
            throw new ConsoleBizException("请求参数不能空");
        }
        VirtualCoin coin =  new VirtualCoin();
        coin.setName(req.getName());
        coin.setShortName(req.getShortName());
        if(otc.virtualCoinConsoleFacade.checkIfExist(coin)){
            LOG.i(this,"币种已存在");
            LOG.dEnd(this, "新增币种结束");
            throw new ConsoleBizException("币种已存在，新增币种失败");
        }
        coin.setIp(req.getIp());
        coin.setPort(req.getPort());
        coin.setAccessKey(req.getAccessKey());
        coin.setSecretKey(req.getSecretKey());
        coin.setStartBlock(req.getStartBlock());
        coin.setMainAddress(req.getMainAddress());
        coin.setIconUrl(req.getIconUrl());
        coin.setLowTradeFees(req.getLowTradeFees());
        coin.setTradeFees(req.getTradeFees());
        coin.setLowRechargeFees(req.getLowRechargeFees());
        coin.setRechargeFees(req.getRechargeFees());
        coin.setLowWithdrawFees(req.getLowWithdrawFees());
        coin.setWithdrawFees(req.getWithdrawFees());
        coin.setSingleLowRecharge(req.getSingleLowRecharge());
        coin.setSingleHighRecharge(req.getSingleHighRecharge());
        coin.setDayHighRecharge(req.getDayHighRecharge());
        coin.setSingleLowWithdraw(req.getSingleLowWithdraw());
        coin.setSingleHighWithdraw(req.getSingleHighWithdraw());
        coin.setDayHighWithdraw(req.getDayHighWithdraw());
        coin.setIconUrl2(req.getIconUrl2());
        coin.setWithdrawDes(req.getWithdrawDes());
        coin.setRechargeDes(req.getRechargeDes());
        filter(coin,req);
        coin.setStatus(VirtualCoinStatus.DISABLED.getCode());
        otc.virtualCoinConsoleFacade.saveVirtualCoin(coin);
        LOG.dEnd(this, "新增币种结束");
        return buildWebApiResponse(SUCCESS,null);
    }

    /**
     * Update coin web api response.
     *
     * @param req the req
     * @return the web api response
     */
    @RequestMapping(value = UPDATE_COIN_CONSOLE_API,method = RequestMethod.POST)
    public WebApiResponse updateCoin(@RequestBody VirtualCoinAddReq req){
        LOG.dStart(this, "更新币种开始");
        if (req == null){
            throw new ConsoleBizException("请求参数不能空");
        }
        if (req.getId() == null){
            throw new ConsoleBizException("币种id不能为空");
        }
        VirtualCoin coin =  new VirtualCoin();
        coin.setId(req.getId());
        coin.setName(req.getName());
        coin.setShortName(req.getShortName());
        coin.setIp(req.getIp());
        coin.setPort(req.getPort());
        coin.setAccessKey(req.getAccessKey());
        coin.setSecretKey(req.getSecretKey());
        coin.setStartBlock(req.getStartBlock());
        coin.setMainAddress(req.getMainAddress());
        coin.setIconUrl(req.getIconUrl());
        coin.setLowTradeFees(req.getLowTradeFees());
        coin.setTradeFees(req.getTradeFees());
        coin.setLowRechargeFees(req.getLowRechargeFees());
        coin.setRechargeFees(req.getRechargeFees());
        coin.setLowWithdrawFees(req.getLowWithdrawFees());
        coin.setWithdrawFees(req.getWithdrawFees());

        coin.setSingleLowRecharge(req.getSingleLowRecharge());
        coin.setSingleHighRecharge(req.getSingleHighRecharge());
        coin.setDayHighRecharge(req.getDayHighRecharge());
        coin.setSingleLowWithdraw(req.getSingleLowWithdraw());
        coin.setSingleHighWithdraw(req.getSingleHighWithdraw());
        coin.setDayHighWithdraw(req.getDayHighWithdraw());
        coin.setIconUrl2(req.getIconUrl2());
        coin.setWithdrawDes(req.getWithdrawDes());
        coin.setRechargeDes(req.getRechargeDes());
        filter(coin,req);
        coin.setStatus(VirtualCoinStatus.DISABLED.getCode());
        otc.virtualCoinConsoleFacade.updateVirtualCoin(coin);
        LOG.dEnd(this, "更新币种结束");
        return buildWebApiResponse(SUCCESS,null);
    }

    @RequestMapping(value = ADD_COINFEE_CONSOLE_API,method = RequestMethod.POST)
    public WebApiResponse addCoinFee(@RequestBody WithdrawfeesAddReq req){
        if(req == null || req.getList() == null || req.getList().isEmpty()){
            throw new ConsoleBizException("数据错误");
        }
        List<Withdrawfees> list = new ArrayList<>();
        Withdrawfees withdrawfees = null;
        Long id = req.getList().get(0).getId();
        for(WithdrawfeesReq withdrawfeesReq : req.getList()){
            withdrawfees = new Withdrawfees();
            withdrawfees.setFee(withdrawfeesReq.getFee());
            withdrawfees.setLevel(withdrawfeesReq.getLevel());
            withdrawfees.setVid(withdrawfeesReq.getVid());
            list.add(withdrawfees);
        }
        boolean b = false;
        if(id == null){
            b = otc.virtualCoinConsoleFacade.addCoinFee(list);
        }else{
            b = otc.virtualCoinConsoleFacade.updateCoinFee(list);
        }
        if(b){
            return buildWebApiResponse(SUCCESS, null);
        }else{
            return buildWebApiResponse(FAILURE, "保存失败");
        }

    }

    @RequestMapping(value = DETAIL_COINFEE_CONSOLE_API,method = RequestMethod.GET)
    public WebApiResponse detailCoinFee(@PathVariable String queryJson){
        WebApiBaseReq req = JsonHelper.jsonStr2Obj(queryJson, WebApiBaseReq.class);
        if(req == null && req.getId() == null){
            throw new ConsoleBizException("数据错误");
        }

        List<Withdrawfees> fees = otc.virtualCoinConsoleFacade.getFees(req.getId());
        return buildWebApiResponse(SUCCESS, fees);
    }

    private void filter(VirtualCoin coin,VirtualCoinAddReq req){
        Boolean isWithDraw = StringUtils.isEmpty(req.getIsWithDraw())?false:true;
        coin.setIsWithDraw(isWithDraw);
        Boolean isAuto = StringUtils.isEmpty(req.getIsAuto())?false:true;
        coin.setIsAuto(isAuto);
        Boolean isRecharge = StringUtils.isEmpty(req.getIsRecharge())?false:true;
        coin.setIsRecharge(isRecharge);
    }
}
