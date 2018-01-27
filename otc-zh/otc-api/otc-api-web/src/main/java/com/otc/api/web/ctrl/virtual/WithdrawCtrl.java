package com.otc.api.web.ctrl.virtual;

import com.jucaifu.common.log.LOG;
import com.jucaifu.common.util.JsonHelper;
import com.otc.api.web.base.BaseWebCtrl;
import com.otc.api.web.constants.WebApiWithdraw;
import com.otc.api.web.ctrl.virtual.request.WithdrawAddReq;
import com.otc.api.web.ctrl.virtual.request.WithdrawInitReq;
import com.otc.api.web.ctrl.virtual.response.WithdrawInitResp;
import com.otc.api.web.exceptions.WebBizException;
import com.otc.api.web.utils.FeesUtils;
import com.otc.common.api.packet.web.WebApiResponse;
import com.otc.common.api.packet.web.request.WebApiBaseReq;
import com.otc.core.cache.UserCacheManager;
import com.otc.core.validator.SmsCodeVerify;
import com.otc.core.validator.SmsType;
import com.otc.core.validator.SmsVerifyUtils;
import com.otc.facade.user.enums.UserAutStatusEnum;
import com.otc.facade.user.pojo.poex.CacheUserInfo;
import com.otc.facade.virtual.enums.VirtualRecordOutStatus;
import com.otc.facade.virtual.enums.VirtualRecordType;
import com.otc.facade.virtual.pojo.cond.VirtualWalletCond;
import com.otc.facade.virtual.pojo.po.VirtualCoin;
import com.otc.facade.virtual.pojo.po.VirtualRecord;
import com.otc.facade.virtual.pojo.po.VirtualWallet;
import com.otc.facade.virtual.pojo.po.Withdrawfees;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by lx on 17-4-25.
 */
@RestController
public class WithdrawCtrl extends BaseWebCtrl implements WebApiWithdraw {

    /**
     * 提现初始化接口
     *
     * @param queryJson the query json
     * @return web api response
     */
    @RequestMapping(value = INIT_WITHDRAW_WEB_API,method = RequestMethod.GET)
    public WebApiResponse init(@PathVariable String queryJson){
        LOG.dStart(this, "提现初始化页面");
        WithdrawInitReq req = JsonHelper.jsonStr2Obj(queryJson, WithdrawInitReq.class);
        if (req == null){
            throw new WebBizException("请求参数不能为空");
        }
        if (req.getCoinId() == null){
            throw new WebBizException("币种ID不能为空");
        }
        WithdrawInitResp resp = new WithdrawInitResp();
        Long userId = UserCacheManager.getUidWithToken(req.getToken());
        // 提现地址
        String address = otc.virtualRecordWebFacade.queryWithdrawAddress(req.getCoinId(),userId);
        VirtualWalletCond cond = new VirtualWalletCond();
        cond.setCoinId(req.getCoinId());
        cond.setUserId(userId);
        // 获取钱包余额
        VirtualWallet wallet = otc.virtualWalletWebFacade.queryVirtualWallet(cond);
        BigDecimal amount = BigDecimal.ZERO;
        if (wallet != null && wallet.getTotal() != null){
            amount = wallet.getTotal();
        }
        //提现说明
        Map<Long, VirtualCoin> allVirtualCoin = otc.virtualCoinWebFacade.getAllVirtualCoin();
        VirtualCoin virtualCoin = allVirtualCoin.get(req.getCoinId());
        // 加载手续费
        resp.setWithdrawAddress(address);
        resp.setTotal(amount.toString());
        resp.setWithdrawDes(virtualCoin != null ? virtualCoin.getWithdrawDes() : "");
        LOG.dEnd(this, "提现初始化页面");
        return buildWebApiResponse(SUCCESS,resp);
    }

    /**
     * Add withdraw web api response.
     *
     * @param req the req
     * @return the web api response
     */
    @RequestMapping(value = ADD_WITHDRAW_WEB_API,method = RequestMethod.POST)
    public WebApiResponse addWithdraw(@RequestBody WithdrawAddReq req){
        LOG.dStart(this, "生成提现数据");
        if (req == null){
            throw new WebBizException("请求参数不能为空");
        }
        Long userId = UserCacheManager.getUidWithToken(req.getToken());
        CacheUserInfo cacheUserInfo = UserCacheManager.getCacheObj(userId);
        if (!UserAutStatusEnum.isRealName(cacheUserInfo.getUserAuthentication().getStatus())){
            throw new WebBizException("未实名认证,不能提现");
        }
        if (!UserAutStatusEnum.isKyc(cacheUserInfo.getUserAuthentication().getStatus())){
            throw new WebBizException("未kyc认证，不能提现");
        }
        String emailAddress = cacheUserInfo.getBaseInfo().getEmailAddress();
        // 验证邮箱验证码
        SmsCodeVerify smsCodeVerify = SmsVerifyUtils.verify(emailAddress, SmsType.DRAW_COIN, req.getMailCode());
        if (!smsCodeVerify.isStatus()){
            throw new WebBizException(smsCodeVerify.getConetent());
        }
        VirtualRecord record = new VirtualRecord();
        record.setCoinId(req.getCoinId());
        record.setUserId(userId);
        record.setType(VirtualRecordType.WITHDRAW.getCode());
        record.setStatus(VirtualRecordOutStatus.APPLY.getCode());
        if (StringUtils.isEmpty(req.getAmount())){
            throw new WebBizException("提现数量不能为空");
        }
        record.setAmount(new BigDecimal(req.getAmount()));
        BigDecimal fees = BigDecimal.ZERO;
        if (StringUtils.isNotEmpty(req.getFees())){
            fees = new BigDecimal(req.getFees());
        }
        // 计算提币手续费
        List<Withdrawfees> feesList = otc.virtualCoinWebFacade.getFees(req.getCoinId());
        if(feesList == null || feesList.isEmpty()){
            throw new WebBizException("管理员暂没配置提现手续费");
        }
        boolean flag = false;
        for(Withdrawfees f : feesList){
            if(fees.compareTo(f.getFee()) == 0){
                flag = true;
                break;
            }
        }
        if (!flag){
            throw new WebBizException("手续费不正确");
        }
        if (record.getAmount().compareTo(fees) != 1){
            throw new WebBizException("提现数量小于等于手续费数量");
        }
        record.setFees(fees);
        record.setAddress(req.getAddress());
        // 新增提现记录
        otc.virtualRecordWebFacade.saveVirtualRecord(record);
        LOG.dEnd(this, "生成提现数据");
        return buildWebApiResponse(SUCCESS,null);
    }

    /**
     * 获取手续费
     * @param req
     * @return
     */
    @RequestMapping(value = FEES_WITHDRAW_WEB_API,method = RequestMethod.POST)
    public WebApiResponse getFees(@RequestBody WebApiBaseReq req){
        if (req == null){
            throw new WebBizException("请求参数不能为空");
        }
        if (req.getId() == null){
            throw new WebBizException("币种ID不能为空");
        }
        List<Withdrawfees> fees = otc.virtualCoinWebFacade.getFees(req.getId());
        return buildWebApiResponse(SUCCESS,fees);
    }

    @RequestMapping(value = CANCEL_WITHDRAW_WEB_API,method = RequestMethod.POST)
    public WebApiResponse cancel(@RequestBody WebApiBaseReq req){
        LOG.dStart(this, "取消提现申请");
        if (req == null){
            throw new WebBizException("请求参数不能为空");
        }
        Long userId = UserCacheManager.getUidWithToken(req.getToken());
        otc.virtualRecordWebFacade.cancelWithdrawByUser(req.getId(),userId);
        LOG.dEnd(this, "取消提现申请");
        return buildWebApiResponse(SUCCESS,null);
    }

}
