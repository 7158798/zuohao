package com.otc.service.trade.biz;

import com.jucaifu.common.constants.TimeConstant;
import com.jucaifu.common.exceptions.biz.BizException;
import com.jucaifu.common.log.LOG;
import com.jucaifu.common.util.DateHelper;
import com.jucaifu.common.validate.ValidateManager;
import com.jucaifu.common.validate.ValidateType;
import com.jucaifu.core.biz.AbsBaseBiz;
import com.otc.core.cache.UserCacheManager;
import com.otc.core.validator.SmsCodeVerify;
import com.otc.core.validator.SmsType;
import com.otc.core.validator.SmsVerifyUtils;
import com.otc.facade.advertisement.pojo.dto.CalculatePricedto;
import com.otc.facade.advertisement.pojo.enums.AdvertisementStatusEnum;
import com.otc.facade.advertisement.pojo.enums.PriceTypeEnum;
import com.otc.facade.advertisement.pojo.po.Advertisement;
import com.otc.facade.advertisement.pojo.vo.CalculatePriceVo;
import com.otc.facade.base.CountVoEx;
import com.otc.facade.sys.enums.AuditProcessEnum;
import com.otc.facade.sys.enums.SystemArgsEnum;
import com.otc.facade.sys.pojo.po.Employee;
import com.otc.facade.trade.enums.*;
import com.otc.facade.trade.pojo.po.Trade;
import com.otc.facade.trade.pojo.vo.TradeVo;
import com.otc.facade.user.enums.UserMessageConstantEnum;
import com.otc.facade.user.exceptions.UserBizException;
import com.otc.facade.user.pojo.po.User;
import com.otc.facade.virtual.pojo.po.VirtualCoin;
import com.otc.pool.OTCBizPool;
import com.otc.service.trade.dao.TradeMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by fenggq on 17-5-10.
 */
@Service
public class TradeBiz extends AbsBaseBiz<Trade,TradeVo,TradeMapper> {
    @Autowired
    private TradeMapper tradeMapper;
    @Override
    public TradeMapper getDefaultMapper() {
        return tradeMapper;
    }

    //存储交易序号
    public static Map<String,Integer> map = new HashMap<>();

    /**
     *  发起交易
     * @param token
     * @param advertId
     */
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public Long addTade(String token, Long advertId, BigDecimal account,String remark){
        LOG.dStart(this,"生成交易");

        Long userId = UserCacheManager.getUidWithToken(token);
        User user = OTCBizPool.getInstance().userBiz.select(userId);
        if (user == null) {
            throw new UserBizException(UserBizException.USER_NOT_LOGIN, "用户未登录");
        }
        Advertisement advertisement = OTCBizPool.getInstance().advertisementBiz.select(advertId);

        if(advertisement == null){
            throw new BizException("广告不存在");
        }
        //广告关闭
        if(advertisement.getStatus() != AdvertisementStatusEnum.PUBLISHED.getValue()){
            throw new BizException("广告状态异常");
        }
        if(account == null){
            throw new BizException("交易数量不能为空");
        }
        if(advertisement.getIsOpenSafetyVeri()){
            if(!OTCBizPool.getInstance().userAuBiz.isKycStatus(userId)){
                throw new BizException("该广告需要完成KYC认证");
            }
        }

        //获取交易价格
        BigDecimal price = BigDecimal.ZERO;
        if(advertisement.getPriceType() == PriceTypeEnum.GDJG.getValue()){
            price = advertisement.getTransactionPrice();
        }else if(advertisement.getPriceType() == PriceTypeEnum.YJ.getValue()){
            CalculatePriceVo vo = new CalculatePriceVo();
            vo.setAdvertisementId(advertId);
            CalculatePricedto calculatePricedto = OTCBizPool.getInstance().advertisementBiz.calculatePrice(vo);
            if(calculatePricedto != null && calculatePricedto.getPrice() != null){
                String calPriece = calculatePricedto.getPrice();
                price = StringUtils.isBlank(calPriece)?BigDecimal.ZERO:new BigDecimal(calculatePricedto.getPrice());
            }
        }else{
            throw new BizException("广告数据异常");
        }
        if(price == null || price.compareTo(BigDecimal.ZERO) <= 0){
            throw new BizException("交易单价必须大于零");
        }
        //取广告信息
//        BigDecimal maxAmount = advertisement.getMaxTransAmount() == null?BigDecimal.ZERO:advertisement.getMaxTransAmount();
//        BigDecimal minAmount = advertisement.getMinTransAmount() == null?BigDecimal.ZERO:advertisement.getMinTransAmount();
        BigDecimal maxAccount = advertisement.getMaxTransCount() == null?BigDecimal.ZERO:advertisement.getMaxTransCount();
        BigDecimal minAccount = advertisement.getMinTransAmount() == null?BigDecimal.ZERO:advertisement.getMinTransCount();
        Long coinId = Long.valueOf(advertisement.getCoinType());
        Integer tradeType = advertisement.getTransactionType();
        Long seller = 0L;
        Long buyer = 0L;

        if(tradeType == TradeTypeEnum.BUY.getCode()){
            buyer = advertisement.getUserId();
            seller = userId;
        }else if (tradeType == TradeTypeEnum.SELL.getCode()){
            seller = advertisement.getUserId();
            buyer = userId;
        }else{
            throw new BizException("广告数据异常");
        }
        if(seller == buyer){
            throw new BizException("买方与卖方不能为同一个人");
        }

        //判断次数限制
        List<Trade> runTradeList = this.selectRunTrade(userId);
        Long tradeLimit = OTCBizPool.getInstance().systemArgsBiz.getSystemArgsForLong(SystemArgsEnum.TRADE_ALL_TIMES);
        if(runTradeList.size() >= tradeLimit){
            throw new BizException("您进行中的交易已达到上限");
        }
        //判断广告交易限制
        if(minAccount != null && account.compareTo(minAccount) < 0){
            throw new BizException("交易量小于最低限制");
        }
        if(maxAccount != null && account.compareTo(maxAccount) > 0){
            throw new BizException("交易量已超出最高限制");
        }

        BigDecimal amount = account.multiply(price);
//        if(minAmount != null && amount.compareTo(minAmount) < 0){
//            throw new BizException("交易总额小于最低限制");
//        }
//        if(maxAmount != null && amount.compareTo(maxAmount) > 0){
//            throw new BizException("交易总额已超出最高限制");
//        }
        //钱包余额判断
        VirtualCoin coin = OTCBizPool.getInstance().virtualCoinBiz.select(coinId);
        if(coin == null){
            throw new BizException("交易币种不存在");
        }
        //手续费
        BigDecimal feerate = coin.getTradeFees();
        BigDecimal fee = feerate == null?BigDecimal.ZERO:feerate.multiply(account);

        if(coin.getLowTradeFees() != null && (fee == null || fee.compareTo(coin.getLowTradeFees()) < 0)){
            fee = coin.getLowTradeFees();
        }
        BigDecimal realAccount;
        if(tradeType == TradeTypeEnum.BUY.getCode()){
            //如果是买家，手续费直接在总数里扣除
            realAccount = account.subtract(fee);
        }else{
            realAccount = account;
        }

        Boolean frozenFlag = OTCBizPool.getInstance().virtualWalletBiz.frozenSeller(seller,coinId,realAccount.add(fee));

        if(!frozenFlag){
            throw new BizException("卖家钱包余额不足，不能交易");
        }

        //发生交易
        Date now = new Date();
        Trade trade = new Trade();

        trade.setCreateTime(now);
        trade.setUpdateTime(now);
        trade.setCoinId(Long.valueOf(advertisement.getCoinType()));
        trade.setAdvertId(advertisement.getId());
        trade.setTradeAccount(account);
        trade.setTradePrice(price);
        trade.setTradeAmount(amount);
        trade.setTradeStatus(TradeStatusEnum.INIT.getCode());
        trade.setBuyUserId(buyer);
        trade.setSellUserId(seller); //买
        trade.setRemark(remark);
        trade.setRealAccount(realAccount);

        //获取超时时间
        Long limitTime = OTCBizPool.getInstance().systemArgsBiz.getSystemArgsForLong(SystemArgsEnum.TRADE_TIME_LIMIT);
        Date payEndTime = new Date(now.getTime()+limitTime*TimeConstant.ONE_MINUTE_UNIT_MILLISECONDS);
        trade.setPayEndTime(payEndTime);

        trade.setTradeFee(fee);
        String tradeNo = getCode();
        trade.setTradeNo(tradeNo);

        //保存交易
        this.insert(trade);

        //生成交易消息
        if(StringUtils.isNotBlank(remark)){
            OTCBizPool.getInstance().messageBiz.sendTradeMessage(userId,advertisement.getUserId(),remark,trade.getId());
        }

        //增加日志记录
        OTCBizPool.getInstance().tradeLogBiz.addLog(userId,trade.getId(),null,TradeStatusEnum.INIT.getCode(), TradeOperationEnum.USER_OPERATION);
        //增加信用记录
        OTCBizPool.getInstance().userCreditBiz.addAccountForInit(buyer,seller);

        //发送通知
        OTCBizPool.getInstance().messageBiz.sendMessage(buyer, UserMessageConstantEnum.TRAE_WAIT_PAY,tradeNo,trade.getId());
        LOG.dEnd(this,"生成交易");

        return trade.getId();
    }


    /**
     * 买家完成付款
     * @param token
     * @param tradeId
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public void buyerPay(String token, Long tradeId){
        LOG.dStart(this,"买家完成付款");
        Long userId = UserCacheManager.getUidWithToken(token);
        User user = OTCBizPool.getInstance().userBiz.select(userId);
        if (user == null) {
            throw new UserBizException(UserBizException.USER_NOT_LOGIN, "用户未登录");
        }
        Trade trade = this.select(tradeId);
        if(trade == null){
            throw  new BizException("交易不存在");
        }
        Integer beforeStatus = trade.getTradeStatus();
        if(trade == null){
            throw new BizException("交易不存在");
        }
        if(!TradeStatusEnum.validateStatus(beforeStatus,TradeStatusEnum.PAYED.getCode())){
            throw new BizException("交易状态异常");
        }
        if(trade.getBuyUserId() != userId){
            throw new BizException("非本人交易不能操作");
        }

        Date now = new Date();
        Date payEndTime = trade.getPayEndTime();
        if(now.compareTo(payEndTime) > 0){
            throw new BizException("已超出支付限制时间");
        }
        trade.setTradeStatus(TradeStatusEnum.PAYED.getCode());
        trade.setUpdateTime(now);
        trade.setPayTime(now);

        this.update(trade);

        //生成交易消息
  //      String content = "交易订单"+trade.getTradeNo()+"买家已完成付款,请及时付款。";
      //  OTCBizPool.getInstance().messageBiz.sendTradeMessage(userId,trade.getSellUserId(),content,trade.getId());

        //增加操作日志
        OTCBizPool.getInstance().tradeLogBiz.addLog(userId,tradeId,
                beforeStatus,TradeStatusEnum.PAYED.getCode(), TradeOperationEnum.USER_OPERATION);


        //发送通知
        OTCBizPool.getInstance().messageBiz.sendMessage(trade.getSellUserId(), UserMessageConstantEnum.TRADE_WAIT_COMMINT,trade.getTradeNo(),tradeId);

        LOG.dEnd(this,"买家完成付款");
    }


    /**
     * 卖家确认  --- 完成交易
     * @param token
     * @param tradeId
     */
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public void sellerConfirm(String token, Long tradeId,String verCode){
        LOG.dStart(this,"卖家确认");
        Long userId = UserCacheManager.getUidWithToken(token);
        User user = OTCBizPool.getInstance().userBiz.select(userId);
        if (user == null) {
            throw new UserBizException(UserBizException.USER_NOT_LOGIN, "用户未登录");
        }
        if (!ValidateManager.validateValue(verCode, ValidateType.VERIFICATION_CODE.getValidateRuleRegex())) {
            throw new UserBizException(UserBizException.AUTHCODE_FORMAT_INCORRECT, "验证码格式不正确");
        }
        SmsCodeVerify smsCodeVerify = SmsVerifyUtils.verify(user.getEmailAddress(), SmsType.CONFIRM_TRADE, verCode);
        if (!smsCodeVerify.isStatus()) {
            throw new UserBizException(UserBizException.AUTHCODE_VALUE_INCORRECT, smsCodeVerify.getConetent());
        }
        Trade trade = this.select(tradeId);
        if(trade == null){
            throw new BizException("交易不存在");
        }
        Integer beforeStatus = trade.getTradeStatus();
        if(!TradeStatusEnum.validateStatus(beforeStatus,TradeStatusEnum.COMPLETE.getCode())){
            throw new BizException("交易状态异常");
        }
        if(trade.getSellUserId() != userId){
            throw new BizException("非本人交易不能操作");
        }
        Long buyer = trade.getBuyUserId();
        Long seller = trade.getSellUserId();
        Long coindId = trade.getCoinId();
        BigDecimal realAccount = trade.getRealAccount() == null?BigDecimal.ZERO:trade.getRealAccount();
        BigDecimal fee = trade.getTradeFee() == null?BigDecimal.ZERO:trade.getTradeFee();
        //手续费+交易数量
        BigDecimal allAccount = realAccount.add(fee);
        //扣除卖家卖家币
        Boolean sellFlag = OTCBizPool.getInstance().virtualWalletBiz.
                subtractSellerFrozen(seller,coindId,allAccount);
        //给买家发币
        Boolean buyFlag = OTCBizPool.getInstance().virtualWalletBiz.
                addBuyer(buyer,coindId,realAccount);

        if(!sellFlag || !buyFlag){
            throw new BizException("钱包数据异常");
        }

        Date now = new Date();
        trade.setTradeStatus(TradeStatusEnum.COMPLETE.getCode());
        trade.setUpdateTime(now);
        trade.setCompleteTime(now);
        trade.setConfirmTime(now);

        this.update(trade);
        //生成交易消息
        String content = "交易订单"+trade.getTradeNo()+"卖家已确认。";
//        OTCBizPool.getInstance().messageBiz.sendTradeMessage(userId,trade.getBuyUserId(),content,trade.getId());
//
        //增加操作日志
        OTCBizPool.getInstance().tradeLogBiz.addLog(userId,tradeId,
                beforeStatus,TradeStatusEnum.COMPLETE.getCode(), TradeOperationEnum.USER_OPERATION);
        //增加信用记录
        Long confirmTime = (now.getTime() - trade.getPayTime().getTime())/(TimeConstant.ONE_MINUTE_UNIT_SECONDS*1000);
        OTCBizPool.getInstance().userCreditBiz.addAccountForConfirm(buyer,seller,Integer.parseInt(confirmTime.toString()));

        //发送通知
        OTCBizPool.getInstance().messageBiz.sendMessage(trade.getBuyUserId(), UserMessageConstantEnum.TRADE_COMPLETE,trade.getTradeNo(),tradeId);
        OTCBizPool.getInstance().messageBiz.sendMessage(trade.getSellUserId(), UserMessageConstantEnum.TRADE_COMPLETE,trade.getTradeNo(),tradeId);

        LOG.dEnd(this,"卖家确认");
    }


    /**
     * 卖家申诉
     * @param token
     * @param tradeId
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public void sellerAppeal(String token, Long tradeId){
        LOG.dStart(this,"卖家申诉");
        Long userId = UserCacheManager.getUidWithToken(token);
        User user = OTCBizPool.getInstance().userBiz.select(userId);
        if (user == null) {
            throw new UserBizException(UserBizException.USER_NOT_LOGIN, "用户未登录");
        }
        Trade trade = this.select(tradeId);
        if(trade == null){
            throw new BizException("交易不存在");
        }
        Integer beforeStatus = trade.getTradeStatus();
        if(!TradeStatusEnum.validateStatus(beforeStatus,TradeStatusEnum.APPEAL.getCode())){
            throw new BizException("交易状态异常");
        }
        if(trade.getSellUserId() != userId){
            throw new BizException("非本人交易不能操作");
        }

        Date now = new Date();
        trade.setTradeStatus(TradeStatusEnum.APPEAL.getCode());
        trade.setUpdateTime(now);
        trade.setAppealTime(now);
        trade.setIsAppeal(true);
        this.update(trade);
        //生成交易消息
        String content = "交易订单"+trade.getTradeNo()+"卖家已申诉。";
      //  OTCBizPool.getInstance().messageBiz.sendTradeMessage(userId,trade.getBuyUserId(),content,trade.getId());

        //增加操作日志
        OTCBizPool.getInstance().tradeLogBiz.addLog(userId,tradeId,
                beforeStatus,TradeStatusEnum.APPEAL.getCode(), TradeOperationEnum.USER_OPERATION);
        //信用记录
        OTCBizPool.getInstance().userCreditBiz.
                addAccountForAppeal(trade.getBuyUserId(),trade.getSellUserId());

        //发送通知
        OTCBizPool.getInstance().messageBiz.sendMessage(trade.getBuyUserId(), UserMessageConstantEnum.TRADE_APPEAL,trade.getTradeNo(),tradeId);
        //OTCBizPool.getInstance().messageBiz.sendMessage(trade.getSellUserId(), UserMessageConstantEnum.TRADE_APPEAL,trade.getTradeNo(),tradeId);

        LOG.dEnd(this,"卖家确认交易");
    }


    /**
     * 买家取消交易
     * @param token
     * @param tradeId
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public void buyerCancel(String token, Long tradeId){
        LOG.dStart(this,"买家取消交易");
        Long userId = UserCacheManager.getUidWithToken(token);
        User user = OTCBizPool.getInstance().userBiz.select(userId);
        if (user == null) {
            throw new UserBizException(UserBizException.USER_NOT_LOGIN, "用户未登录");
        }
        Trade trade = this.select(tradeId);
        if(trade == null){
            throw new BizException("交易不存在");
        }
        Integer beforeStatus = trade.getTradeStatus();
        if(!TradeStatusEnum.validateStatus(beforeStatus,TradeStatusEnum.CANCEL.getCode())){
            throw new BizException("交易状态异常");
        }
        if(trade.getBuyUserId() != userId){
            throw new BizException("非本人交易不能操作");
        }

        //解除卖家冻结
        boolean flag = OTCBizPool.getInstance().virtualWalletBiz.
                restoreSellerFrozen(trade.getSellUserId(),trade.getCoinId(),trade.getRealAccount().add(trade.getTradeFee()));
        if(!flag){
            throw new BizException("卖家账户异常");
        }

        Date now = new Date();
        trade.setTradeStatus(TradeStatusEnum.CANCEL.getCode());
        trade.setUpdateTime(now);
        trade.setCompleteTime(now);
        trade.setCancelType(TradeCancelEnum.BUYER_TIMEOUT.getCode());
        this.update(trade);
        //生成交易消息
        String content = "交易订单"+trade.getTradeNo()+"买家已取消交易。";
       // OTCBizPool.getInstance().messageBiz.sendTradeMessage(userId,trade.getSellUserId(),content,trade.getId());

        //增加操作日志
        OTCBizPool.getInstance().tradeLogBiz.addLog(userId,tradeId,
                beforeStatus,TradeStatusEnum.CANCEL.getCode(), TradeOperationEnum.USER_OPERATION);

        OTCBizPool.getInstance().messageBiz.sendMessage(trade.getSellUserId(), UserMessageConstantEnum.TRADE_CANEL,trade.getTradeNo(),tradeId);
        OTCBizPool.getInstance().messageBiz.sendMessage(trade.getBuyUserId(), UserMessageConstantEnum.TRADE_CANEL,trade.getTradeNo(),tradeId);
        LOG.dEnd(this,"买家取消交易");
    }


    /**
     * 后台取消交易
     * @param employeeid
     * @param tradeId
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public void cancelTradeByAdmin(Long employeeid, Long tradeId,String remark,String cofirmPwd){
        LOG.dStart(this,"后台取消交易");
        Employee employee = OTCBizPool.getInstance().employeeBiz.select(employeeid);
        if (employee == null) {
            throw new UserBizException(UserBizException.USER_NOT_LOGIN, "员工不存在");
        }
        Trade trade = this.select(tradeId);
        if(trade == null){
            throw new BizException("交易不存在");
        }
        Integer beforeStatus = trade.getTradeStatus();
        if(!TradeStatusEnum.validateConsoleAppealCancel(beforeStatus)){
            throw new BizException("交易状态异常");
        }
        Date now = new Date();
        TradeStatusEnum node;
        if(beforeStatus == TradeStatusEnum.APPEAL.getCode()){
            node = TradeStatusEnum.APPEAL_CANCEL;
        }else{
            node = TradeStatusEnum.getStatusMap().get(beforeStatus);
        }

        String _statue = OTCBizPool.getInstance().auditProcessBiz.getAuditProcess(node,employee, AuditProcessEnum.TRADE_CANCEL.getCode(),cofirmPwd);
        if(Integer.parseInt(_statue) != TradeStatusEnum.COMPLETE.getCode()){
            trade.setUpdateTime(now);
            trade.setTradeStatus(Integer.parseInt(_statue));
            this.update(trade);
            return;
        }
        //解除卖家冻结
        boolean flag = OTCBizPool.getInstance().virtualWalletBiz.
                restoreSellerFrozen(trade.getSellUserId(),trade.getCoinId(),trade.getRealAccount().add(trade.getTradeFee()));
        if(!flag){
            throw new BizException("卖家账户异常");
        }

        trade.setTradeStatus(TradeStatusEnum.CANCEL.getCode());
        trade.setUpdateTime(now);
        trade.setAppealTime(now);
        trade.setRemarkConsole(remark);
        trade.setCancelType(TradeCancelEnum.APPEAL_CANCEL.getCode());
        trade.setCompleteTime(now);
        this.update(trade);

        //生成交易消息
//        String content = "交易订单"+trade.getTradeNo()+"已由管理员取消交易。";
//        OTCBizPool.getInstance().messageBiz.sendConsoleMessage(employeeid,trade.getBuyUserId(),
//                trade.getSellUserId(),trade.getId(),content);

        //发送通知
        OTCBizPool.getInstance().messageBiz.sendMessage(trade.getBuyUserId(), UserMessageConstantEnum.TRADE_TIME_OUT,trade.getTradeNo(),tradeId);
        OTCBizPool.getInstance().messageBiz.sendMessage(trade.getSellUserId(), UserMessageConstantEnum.TRADE_TIME_OUT,trade.getTradeNo(),tradeId);

        //增加操作日志
        OTCBizPool.getInstance().tradeLogBiz.addLog(employeeid,tradeId,
                beforeStatus,TradeStatusEnum.CANCEL.getCode(), TradeOperationEnum.ADMIN_OPERATION);
        LOG.dEnd(this,"后台取消交易");
    }


    /**
     * 后台确认  --- 完成交易
     * @param employeeId
     * @param tradeId
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public void adminConfirm(Long employeeId, Long tradeId,String remark,String cofirmPwd){
        LOG.dStart(this,"后台确认放币");
        Employee employee = OTCBizPool.getInstance().employeeBiz.select(employeeId);
        if (employee == null) {
            throw new UserBizException(UserBizException.USER_NOT_LOGIN, "员工不存在");
        }
        Trade trade = this.select(tradeId);
        if(trade == null){
            throw new BizException("交易不存在");
        }
        Integer beforeStatus = trade.getTradeStatus();

        if(!TradeStatusEnum.validateConsoleAppealConfirm(beforeStatus)){
            throw new BizException("交易状态异常");
        }

        TradeStatusEnum node;
        if(beforeStatus == TradeStatusEnum.APPEAL.getCode()){
            node = TradeStatusEnum.APPEAL_CONFIRM;
        }else{
            node = TradeStatusEnum.getStatusMap().get(beforeStatus);
        }
        String _statue = OTCBizPool.getInstance().auditProcessBiz.getAuditProcess(node,employee, AuditProcessEnum.TRADE_CONFRIM.getCode(),cofirmPwd);
        Date now = new Date();
        if(Integer.parseInt(_statue) != TradeStatusEnum.COMPLETE.getCode()){
            trade.setUpdateTime(now);
            trade.setTradeStatus(Integer.parseInt(_statue));
            this.update(trade);
            return;
        }

        Long buyer = trade.getBuyUserId();
        Long seller = trade.getSellUserId();
        Long coindId = trade.getCoinId();
        BigDecimal realAccount = trade.getRealAccount() == null?BigDecimal.ZERO:trade.getRealAccount();
        BigDecimal fee = trade.getTradeFee() == null?BigDecimal.ZERO:trade.getTradeFee();
        //手续费+交易数量
        BigDecimal allAccount = realAccount.add(fee);
        //扣除卖家卖家币
        Boolean sellFlag = OTCBizPool.getInstance().virtualWalletBiz.
                subtractSellerFrozen(seller,coindId,allAccount);
        //给买家发币
        Boolean buyFlag = OTCBizPool.getInstance().virtualWalletBiz.
                addBuyer(buyer,coindId,realAccount);

        if(!sellFlag || !buyFlag){
            throw new BizException("钱包数据异常");
        }


        trade.setTradeStatus(TradeStatusEnum.COMPLETE.getCode());
        trade.setRemarkConsole(remark);
        trade.setUpdateTime(now);
        trade.setCompleteTime(now);
        trade.setConfirmTime(now);

        this.update(trade);

        //生成交易消息
//        String content = "交易订单"+trade.getTradeNo()+"已由管理员确认完成。";
//        OTCBizPool.getInstance().messageBiz.sendConsoleMessage(employeeId,trade.getBuyUserId(),
//                trade.getSellUserId(),trade.getId(),content);
        //增加操作日志
        OTCBizPool.getInstance().tradeLogBiz.addLog(employeeId,tradeId,
                beforeStatus,TradeStatusEnum.COMPLETE.getCode(), TradeOperationEnum.ADMIN_OPERATION);

        //增加信用记录
        Long confirmTime = (now.getTime() - trade.getPayTime().getTime())/TimeConstant.ONE_MINUTE_UNIT_SECONDS;
        OTCBizPool.getInstance().userCreditBiz.addAccountForConfirm(buyer,seller,Integer.parseInt(confirmTime.toString()));

        //发送通知
        OTCBizPool.getInstance().messageBiz.sendMessage(trade.getBuyUserId(), UserMessageConstantEnum.SYS_TRADE_COMPLETE,trade.getTradeNo(),trade.getCoinId());
        OTCBizPool.getInstance().messageBiz.sendMessage(trade.getSellUserId(), UserMessageConstantEnum.SYS_TRADE_COMPLETE,trade.getTradeNo(),trade.getCoinId());


        LOG.dEnd(this,"后台确认放币");
    }


    /**
     * 后台确认(进行中) --- 完成交易
     * @param employeeId
     * @param tradeId
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public void adminConfirmForRun(Long employeeId, Long tradeId,String remark,String cofirmPwd){
        LOG.dStart(this,"后台确认放币");
        Employee employee = OTCBizPool.getInstance().employeeBiz.select(employeeId);
        if (employee == null) {
            throw new UserBizException(UserBizException.USER_NOT_LOGIN, "员工不存在");
        }
        Trade trade = this.select(tradeId);
        if(trade == null){
            throw new BizException("交易不存在");
        }
        Integer beforeStatus = trade.getTradeStatus();

        if(!TradeStatusEnum.validateConsoleRunConfrim(beforeStatus)){
            throw new BizException("交易状态异常");
        }

        TradeStatusEnum node;
        if(beforeStatus == TradeStatusEnum.PAYED.getCode()){
            node = TradeStatusEnum.RUN_CONFIRM;
        }else{
            node = TradeStatusEnum.getStatusMap().get(beforeStatus);
        }
        String _statue = OTCBizPool.getInstance().auditProcessBiz.getAuditProcess(node,employee, AuditProcessEnum.TRADE_CONFRIM_RUN.getCode(),cofirmPwd);
        Date now = new Date();
        if(Integer.parseInt(_statue) != TradeStatusEnum.COMPLETE.getCode()){
            trade.setUpdateTime(now);
            trade.setTradeStatus(Integer.parseInt(_statue));
            this.update(trade);
            return;
        }

        Long buyer = trade.getBuyUserId();
        Long seller = trade.getSellUserId();
        Long coindId = trade.getCoinId();
        BigDecimal realAccount = trade.getRealAccount() == null?BigDecimal.ZERO:trade.getRealAccount();
        BigDecimal fee = trade.getTradeFee() == null?BigDecimal.ZERO:trade.getTradeFee();
        //手续费+交易数量
        BigDecimal allAccount = realAccount.add(fee);
        //扣除卖家卖家币
        Boolean sellFlag = OTCBizPool.getInstance().virtualWalletBiz.
                subtractSellerFrozen(seller,coindId,allAccount);
        //给买家发币
        Boolean buyFlag = OTCBizPool.getInstance().virtualWalletBiz.
                addBuyer(buyer,coindId,realAccount);

        if(!sellFlag || !buyFlag){
            throw new BizException("钱包数据异常");
        }
        trade.setTradeStatus(TradeStatusEnum.COMPLETE.getCode());
        trade.setRemarkConsole(remark);
        trade.setUpdateTime(now);
        trade.setCompleteTime(now);
        trade.setConfirmTime(now);

        this.update(trade);

        //生成交易消息
//        String content = "交易订单"+trade.getTradeNo()+"已由管理员确认完成。";
//        OTCBizPool.getInstance().messageBiz.sendConsoleMessage(employeeId,trade.getBuyUserId(),
//                trade.getSellUserId(),trade.getId(),content);

        //发送通知
        OTCBizPool.getInstance().messageBiz.sendMessage(trade.getBuyUserId(), UserMessageConstantEnum.SYS_TRADE_COMPLETE,trade.getTradeNo(),trade.getCoinId());
        OTCBizPool.getInstance().messageBiz.sendMessage(trade.getSellUserId(), UserMessageConstantEnum.SYS_TRADE_COMPLETE,trade.getTradeNo(),trade.getCoinId());
        //增加操作日志
        OTCBizPool.getInstance().tradeLogBiz.addLog(employeeId,tradeId,
                beforeStatus,TradeStatusEnum.COMPLETE.getCode(), TradeOperationEnum.ADMIN_OPERATION);

        //增加信用记录
        Long confirmTime = (now.getTime() - trade.getPayTime().getTime())/TimeConstant.ONE_MINUTE_UNIT_SECONDS;
        OTCBizPool.getInstance().userCreditBiz.addAccountForConfirm(buyer,seller,Integer.parseInt(confirmTime.toString()));

        LOG.dEnd(this,"后台确认放币");
    }

    /**
     * 前端交易记录
     * @param vo
     * @return
     */
    public List<Trade>  queryByConditionPage(TradeVo vo){
        return this.getByConditionPage(vo);
    }


    /**
     *  后台交易记录
     * @param vo
     * @return
     */
    public List<Trade> getConsoleListByConditionPage(TradeVo vo){
        return this.getDefaultMapper().getConsoleListByConditionPage(vo);
    }

    /**
     *  查找正在交易的记录
     * @return
     */
    public List<Trade> selectRunTrade(Long userId){
        TradeVo vo = new TradeVo();
        vo.setStatuList(TradeStatusEnum.getRunStatus());
        vo.setUserId(userId);
        return this.getDefaultMapper().getByCondition(vo);
    }







    public static void main(String[] args) {
//        Date now = new Date();
//        Long limitTime = 90L;
//        int a = TimeConstant.ONE_MINUTE_UNIT_MILLISECONDS;
//
//        Date payEndTime = new Date(now.getTime()+limitTime*a);
//        System.out.print(payEndTime);
//        System.out.print(DateHelper.date2String(payEndTime, DateHelper.DateFormatType.YearMonthDay_HourMinuteSecond));
    }


    /**
     * 生成编码  170508118001（年后两位+月+日+200-400随机数+当日广告自然数）自然数001，为三位
     * @return
     */
    public String getCode(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
        String code = "";
        //年后两位+月+日
        code += sdf.format(new Date());

        // 100-200随机数
        int i = new Random().nextInt(200) + 200;
        code += i;
        // 当日交易自然数
        return code+getTradeNo();
    }

    /**
     * 获取交易当日自然序号
     * @return
     */
    public String getTradeNo(){
        String today = DateHelper.getCurrentStrDate(DateHelper.DateFormatType.YearMonthDay);
        Date now = DateHelper.string2Date(today, DateHelper.DateFormatType.YearMonthDay);
        Integer no = map.get(today);
        if(no == null){
            Trade trade = this.getDefaultMapper().getLastTrade(now);
            if(trade != null){
                String tradeNo = trade.getTradeNo();
                tradeNo  = tradeNo.substring(tradeNo.length() - 4, tradeNo.length());
                no = Integer.valueOf(tradeNo);
            }
        }
        if(no == null){
            no = 0;
        }
        no = no+1;

        map.put(today,no);

        return  String.format("%04d", no);
    }

    /**
     * 综合统计
     * @return
     */
    public List<CountVoEx> countTrade(){
        return countTrade(new Date(), String.valueOf(TradeStatusEnum.COMPLETE.getCode()));
    }

    /**
     *综合统计
     * @param dayTime  日期
     * @param status    状态
     * @return
     */
    public List<CountVoEx> countTrade(Date dayTime, String status){
        BigDecimal total = new BigDecimal(0);
        CountVoEx totalVo = new CountVoEx();
        List<CountVoEx> countVos = tradeMapper.countTrade(DateHelper.date2String(dayTime, DateHelper.DateFormatType.YearMonthDay), status);
        if(countVos != null && countVos.size() > 0){
            for(CountVoEx vo : countVos){
                total = total.add(vo.getCountTotal());
            }
        }
        totalVo.setCountName("total");
        totalVo.setCountTotal(total);
        countVos.add(totalVo);

        return countVos;
    }

    /**
     * 系统取消交易
     * @param trade
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public void cancelForTimeOut(Trade trade){
        if(trade == null){
            return;
        }
        Long userId = trade.getSellUserId();
        //解除卖家冻结
        boolean flag = OTCBizPool.getInstance().virtualWalletBiz.
                restoreSellerFrozen(trade.getSellUserId(),trade.getCoinId(),trade.getRealAccount().add(trade.getTradeFee()));
        if(!flag){
            throw new BizException("系统取消交易=======解除冻结异常");
        }
        trade.setTradeStatus(TradeStatusEnum.CANCEL.getCode());
        trade.setCancelType(TradeCancelEnum.BUYER_TIMEOUT.getCode());
        trade.setCompleteTime(new Date());
        trade.setUpdateTime(new Date());
        this.update(trade);
        OTCBizPool.getInstance().messageBiz.sendMessage(trade.getSellUserId(), UserMessageConstantEnum.TRADE_TIME_OUT,trade.getTradeNo(),trade.getId());
        OTCBizPool.getInstance().messageBiz.sendMessage(trade.getBuyUserId(), UserMessageConstantEnum.TRADE_TIME_OUT,trade.getTradeNo(),trade.getId());


    }


    public void cancelForTimeOut(){
        this.getDefaultMapper().cancelForTimeOut(TradeCancelEnum.BUYER_TIMEOUT.getCode());
    }

    public List<Trade> getTimeOutList(){
         List<Trade> list = this.getDefaultMapper().getTimeOutList(TradeStatusEnum.INIT.getCode());
        return list;
    }

    public List<Trade> getJudgeTimeOutList(){
        Date endTime = new Date(new Date().getTime() - 3 * TimeConstant.ONE_DAY_UNIT_MILLISECONDS);

        List<Trade> list
                =  tradeMapper.getJudgeTimeOutList(TradeStatusEnum.COMPLETE.getCode(),endTime, TradeJudgeEnum.COMPLETE.getCode());
        return list;
    }

    /**
     * 系统单条取消交易
     * @param token
     * @param tradeId
     */
    public void sysCancel(String token, Long tradeId) {
        LOG.dStart(this,"===========系统单条取消交易开始============");
        Long userId = UserCacheManager.getUidWithToken(token);
        User user = OTCBizPool.getInstance().userBiz.select(userId);
        if (user == null) {
            throw new UserBizException(UserBizException.USER_NOT_LOGIN, "用户未登录");
        }
        Trade trade = this.select(tradeId);
        if(trade == null){
            throw new BizException("交易不存在");
        }
        Integer beforeStatus = trade.getTradeStatus();
        if(!TradeStatusEnum.validateStatus(beforeStatus,TradeStatusEnum.CANCEL.getCode())){
            throw new BizException("交易状态异常");
        }
        try{
            cancelForTimeOut(trade);
            this.getDefaultMapper().cancelForTradeId(trade.getId(),TradeCancelEnum.BUYER_TIMEOUT.getCode());
        }catch (Exception e){
            LOG.e(this.getClass(),"取消交易出错,id:"+trade.getId()+"交易编号："+trade.getTradeNo());
            e.printStackTrace();
        }
    }
}
