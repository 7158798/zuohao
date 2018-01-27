package com.otc.service.virtual.biz;

import com.base.wallet.utils.BTCUtils;
import com.base.wallet.utils.IWalletUtil;
import com.jucaifu.common.constants.TimeConstant;
import com.jucaifu.common.log.LOG;
import com.jucaifu.common.util.DateHelper;
import com.jucaifu.core.biz.AbsBaseBiz;
import com.otc.core.cache.CacheHelper;
import com.otc.facade.base.CountVoEx;
import com.otc.facade.cache.CacheKey;
import com.otc.facade.sys.enums.AuditProcessEnum;
import com.otc.facade.sys.pojo.po.Employee;
import com.otc.facade.user.enums.UserMessageConstantEnum;
import com.otc.facade.virtual.enums.VirtualRecordInStatus;
import com.otc.facade.virtual.enums.VirtualRecordOutStatus;
import com.otc.facade.virtual.enums.VirtualRecordType;
import com.otc.facade.virtual.exceptions.VirtualBizException;
import com.otc.facade.virtual.pojo.cond.UserAddressCond;
import com.otc.facade.virtual.pojo.cond.VirtualRecordCond;
import com.otc.facade.virtual.pojo.po.UserAddress;
import com.otc.facade.virtual.pojo.po.VirtualCoin;
import com.otc.facade.virtual.pojo.po.VirtualRecord;
import com.otc.facade.virtual.pojo.po.VirtualWallet;
import com.otc.facade.virtual.pojo.poex.VirtualRecordEx;
import com.otc.facade.virtual.pojo.vo.VirtualRecordVo;
import com.otc.pool.OTCBizPool;
import com.otc.service.virtual.dao.VirtualRecordMapper;
import com.otc.util.WalletUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by lx on 17-4-17.
 */
@Service
public class VirtualRecordBiz extends AbsBaseBiz<VirtualRecord,VirtualRecordVo,VirtualRecordMapper> {

    @Autowired
    private VirtualRecordMapper virtualRecordMapper;
    @Override
    public VirtualRecordMapper getDefaultMapper() {
        return virtualRecordMapper;
    }

    private void filter(VirtualRecord record){
        if (record.getCoinId() == null){
            throw new VirtualBizException("币种id不能为空");
        }
        if (record.getUserId() == null){
            throw new VirtualBizException("用户id不能为空");
        }
        if (StringUtils.isEmpty(record.getAddress())){
            throw new VirtualBizException("币种地址不能为空");
        }
        if (StringUtils.isEmpty(record.getType())){
            throw new VirtualBizException("操作类型不能为空");
        }
        VirtualRecordType type = VirtualRecordType.typeMap.get(record.getType());
        if (type == null){
            throw new VirtualBizException("操作类型不正确");
        }
        if (StringUtils.isEmpty(record.getStatus())){
            throw new VirtualBizException("操作状态不能为空");
        }
        VirtualRecordOutStatus status = VirtualRecordOutStatus.statusMap.get(record.getStatus());
        if (status == null){
            throw new VirtualBizException("操作状态不正确");
        }
        if (record.getAmount() == null){
            throw new VirtualBizException("数量不能为空");
        }
        if (record.getAmount().compareTo(BigDecimal.ZERO) != 1){
            throw new VirtualBizException("数量必须大于零");
        }
        if (record.getFees() == null){
            throw new VirtualBizException("手续费不能为空");
        }
        // 禁止平台转账
        if (VirtualRecordType.WITHDRAW.getCode().equals(record.getType())){
            // 提币提币地址是否平台内地址
            UserAddressCond cond = new UserAddressCond();
            cond.setAddress(record.getAddress());
            List<UserAddress> rList = OTCBizPool.getInstance().userAddressBiz.queryListUserAddress(cond);
            if (rList != null && rList.size() > 0){
                throw new VirtualBizException("禁止平台内转账");
            }
            // 校验地址的正确性
            BTCUtils btcUtils = (BTCUtils) WalletUtils.getWalletObj(record.getCoinId(),null);
            boolean flag = btcUtils.validateaddress(record.getAddress());
            if (!flag){
                throw new VirtualBizException("提币地址不正确");
            }

            VirtualWallet wallet = OTCBizPool.getInstance().virtualWalletBiz.getByUserIdAndCoinId(record.getUserId(),record.getCoinId());
            if (wallet == null){
                throw new VirtualBizException("钱包数据不存在");
            }
            BigDecimal amount = record.getAmount().add(record.getFees());
            if (wallet.getTotal().compareTo(amount) == -1){
                throw new VirtualBizException("钱包的余额不足");
            }
        }
    }

    /**
     * Save virtual record boolean.
     *
     * @param record the record
     * @return the boolean
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean saveVirtualRecord(VirtualRecord record) {
        boolean flag = false;
        filter(record);
        Date date = new Date();
        record.setCreateDate(date);
        record.setModifiedDate(date);
        int ret = virtualRecordMapper.insert(record);
        if (ret != 0)
            flag = true;
        // 更新钱包的地址
        OTCBizPool.getInstance().virtualWalletBiz.frozenSeller(record.getUserId(),record.getCoinId(),record.getAmount());

        String key = "";
        if(VirtualRecordType.RECHARGE.getCode().equals(record.getType())){
            key = CacheKey.RECHARGE_ADDRESS_KEY + record.getCoinId() + record.getUserId();
        }else if(VirtualRecordType.WITHDRAW.getCode().equals(record.getType())){
            key = CacheKey.WITHDRAW_ADDRESS_KEY + record.getCoinId() + record.getUserId();
        }
        // 更新缓存数据
        if(StringUtils.isNotBlank(key)){
            CacheHelper.saveObj(key,record.getAddress());
        }

        //通知
        OTCBizPool.getInstance().messageBiz.sendMessage(record.getUserId(), UserMessageConstantEnum.COIN_WITHOUT_AUDIT,null,record.getCoinId());

        return flag;
    }

    /**
     * Query by condition page virtual record vo.
     *
     * @param vo the vo
     * @return the virtual record vo
     */
    public VirtualRecordVo queryByConditionPage(VirtualRecordVo vo){
        List<VirtualRecord> list = virtualRecordMapper.queryByConditionPage(vo);
        if (list != null && list.size() > 0){
            vo.setResultList(list);
        }
        return vo;
    }

    /**
     * Query list virtual record list.
     *
     * @param cond the cond
     * @return the list
     */
    public List<VirtualRecord> queryListVirtualRecord(VirtualRecordCond cond){
        return virtualRecordMapper.queryListVirtualRecord(cond);
    }

    /**
     * Query list by tx id list.
     *
     * @param txId    the tx id
     * @param address the address
     * @return the list
     */
    public List<VirtualRecord> queryListByTxId(String txId,String address){
        VirtualRecordCond cond = new VirtualRecordCond();
        cond.setTxId(txId);
        cond.setAddress(address);
        return virtualRecordMapper.queryListVirtualRecord(cond);
    }

    private void withdrawFilter(VirtualRecord record){
        if (StringUtils.isNotEmpty(record.getTxId())){
            throw new VirtualBizException("提现订单号已经存在");
        }
        if (!VirtualRecordType.WITHDRAW.getCode().equals(record.getType())){
            throw new VirtualBizException("数据类型异常");
        }
    }

    /**
     * 查询可用的提现地址
     *
     * @param coinId the coin id
     * @param userId the user id
     * @return string
     */
    public String queryWithdrawAddress(Long coinId, Long userId){
        String address;
        String key = CacheKey.WITHDRAW_ADDRESS_KEY + coinId + userId;
        address = CacheHelper.getObj(key);
        if (StringUtils.isEmpty(address)){
            // 缓存中不存在
            VirtualRecordCond cond = new VirtualRecordCond();
            cond.setType(VirtualRecordType.WITHDRAW.getCode());
            cond.setUserId(userId);
            cond.setCoinId(coinId);
            List<VirtualRecord> list = queryListVirtualRecord(cond);
            if (list != null && list.size() > 0){
                address = list.get(0).getAddress();
                CacheHelper.saveObj(key,address);
            }
        }
        return address;
    }

    /**
     * 共通更新
     *
     * @param record the record
     * @return boolean
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean updateCore(VirtualRecord record){
        boolean flag = false;
        record.setModifiedDate(new Date());
        int ret = virtualRecordMapper.update(record);
        if (ret != 0)
            flag = Boolean.TRUE;
        return flag;
    }

    /**
     * Query console by condition page virtual record vo.
     *
     * @param vo the vo
     * @return the virtual record vo
     */
    public VirtualRecordVo queryConsoleByConditionPage(VirtualRecordVo vo) {
        List<VirtualRecordEx> list = virtualRecordMapper.queryConsoleByConditionPage(vo);
        if (list != null){
            vo.setResultList(list);
        }
        return vo;
    }

    /**
     * 提现校验方法
     * @param record
     * @param btcUtils
     */
    private void withdrawCheck(VirtualRecord record,BTCUtils btcUtils){
        LOG.dStart(this,"提币验证开始");
        withdrawFilter(record);
        // 提现合计数量
        BigDecimal amount = record.getAmount().add(record.getFees());
        // 用户帐号余额
        VirtualWallet wallet = OTCBizPool.getInstance().virtualWalletBiz.isExist(record.getCoinId(),record.getUserId());
        if (wallet.getFrozen().compareTo(amount) == -1){
            // 帐号冻结数量小于提现数量,不正确。
            throw new VirtualBizException("帐号冻结数量不足" + amount);
        }
        try {
            boolean isPasswordTrue = btcUtils.walletpassphrase(10);
            if(!isPasswordTrue){
                throw new VirtualBizException("钱包密码验证失败");
            }
            double balance = btcUtils.getbalanceValue();
            BigDecimal _balance = new BigDecimal(String.valueOf(balance));
            if (_balance.compareTo(amount) == -1){
                throw new VirtualBizException("虚拟币钱包数量:" + _balance.toString() + "小于提现数量:" + amount.toString());
            }
            // 验证提现地址
            boolean addressFlag = btcUtils.validateaddress(record.getAddress());
            if (!addressFlag){
                throw new VirtualBizException("提现地址不正确,提现失败");
            }
        } catch (VirtualBizException e){
            LOG.e(this,e.getMessage(),e);
            throw e;
        }catch (Exception e) {
            LOG.e(this,e.getMessage(),e);
            throw new VirtualBizException("提现调用钱包失败");
        }
        LOG.dEnd(this,"提币验证结束");
    }


    /**
     * Send coin.
     *
     * @param record      the record
     * @param iWalletUtil the wallet util
     */
    @Transactional(rollbackFor = Exception.class)
    public void sendCoin(VirtualRecord record,IWalletUtil iWalletUtil){
        LOG.i(this,"发送虚拟币开始");
        BTCUtils btcUtils = (BTCUtils) iWalletUtil;
        withdrawCheck(record,btcUtils);
        String address = record.getAddress();
        double fees = record.getFees().doubleValue();
        String txId;
        // 提现合计数量
        BigDecimal amount = record.getAmount().subtract(record.getFees());
        // 用户帐号余额
        VirtualWallet wallet = OTCBizPool.getInstance().virtualWalletBiz.isExist(record.getCoinId(),record.getUserId());
        try {
            txId = btcUtils.sendtoaddressValue(address,amount.doubleValue(),fees,record.getId().toString());
            LOG.i(this,"钱包发送币的交易id=" + txId);
        }catch (Exception e) {
            LOG.e(this,e.getMessage(),e);
            throw new VirtualBizException("提币调用钱包失败,发送币失败");
        }
        if (StringUtils.isNotEmpty(txId)){
            record.setTxId(txId);
            updateCore(record);
            // 更新用户帐号余额
            wallet.setFrozen(wallet.getFrozen().subtract(amount));
            OTCBizPool.getInstance().virtualWalletBiz.update(wallet);
        } else {
            throw new VirtualBizException("交易id,发布发生异常,可能提现失败");
        }
        LOG.i(this,"发送虚拟币结束");
    }

    /**
     * Lock withdraw boolean.
     *
     * @param id the id
     * @return the boolean
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean lockWithdraw(Long id) {
        VirtualRecord record = virtualRecordMapper.select(id);
        if (record == null){
            throw new VirtualBizException("数据不存在");
        }
        if (!VirtualRecordOutStatus.APPLY.getCode().equals(record.getStatus())){
            throw new VirtualBizException("数据状态不正确,锁定失败");
        }
        record.setStatus(VirtualRecordOutStatus.PROCESSING.getCode());
        Boolean flag = false;
        int ret = virtualRecordMapper.update(record);
        if (ret != 0){
            flag = Boolean.TRUE;
        }
        return flag;
    }

    /**
     * Unlock withdraw boolean.
     *
     * @param id the id
     * @return the boolean
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean unlockWithdraw(Long id) {
        VirtualRecord record = virtualRecordMapper.select(id);
        if (record == null){
            throw new VirtualBizException("数据不存在");
        }
        if (!VirtualRecordOutStatus.PROCESSING.getCode().equals(record.getStatus())){
            throw new VirtualBizException("数据状态不正确,解锁失败");
        }
        record.setStatus(VirtualRecordOutStatus.APPLY.getCode());
        Boolean flag = false;
        int ret = virtualRecordMapper.update(record);
        if (ret != 0){
            flag = Boolean.TRUE;
        }
        return flag;
    }

    /**
     * Audit withdraw boolean.
     *
     * @param recordId   the record id
     * @param empId      the emp id
     * @param confirmPwd the confirm pwd
     * @param walletPwd  the wallet pwd
     * @return the boolean
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean auditWithdraw(Long recordId, Long empId, String confirmPwd, String walletPwd){
        VirtualRecord record = virtualRecordMapper.select(recordId);
        if (record == null){
            throw new VirtualBizException("数据不存在");
        }
        if (!(VirtualRecordOutStatus.PROCESSING.getCode().equals(record.getStatus())
                || VirtualRecordOutStatus.TWO_TIME_AUDIT.getCode().equals(record.getStatus())
                || VirtualRecordOutStatus.THREE_TIME_AUDIT.getCode().equals(record.getStatus()))){
            // 申请状态
            throw new VirtualBizException("当前状态不正确");
        }
        Employee employee = OTCBizPool.getInstance().employeeBiz.getEmployee(empId);
        VirtualRecordOutStatus node = VirtualRecordOutStatus.statusMap.get(record.getStatus());
        String _statue = OTCBizPool.getInstance().auditProcessBiz.getAuditProcess(node,employee, AuditProcessEnum.VIRTUAL_OUT.getCode(),confirmPwd);
        if (!VirtualRecordOutStatus.WAIT_SYSTEM_PROCESS.getCode().equals(_statue)){
            // 审核流程中
            record.setStatus(_statue);
            return updateCore(record);
        }
        BTCUtils btcUtils = (BTCUtils) WalletUtils.getWalletObj(record.getCoinId(),walletPwd);
        withdrawCheck(record,btcUtils);
        record.setStatus(_statue);
        updateCore(record);
        // 保存到redis
        // 缓存提现工具类,一个小时
        CacheHelper.saveObj(CacheKey.buildWithdrawKey(record.getId()),btcUtils, TimeConstant.ONE_DAY_UNIT_SECONDS);
        return Boolean.TRUE;
    }

    /**
     * 综合统计-全部
     *
     * @param type the type
     * @return list
     */
    public List<CountVoEx> countVitralRecord(String type){
        return this.countVitralRecord(new Date(), VirtualRecordOutStatus.SUCCESS.getCode(), type);
    }

    /**
     * 综合统计-一天
     *
     * @param dayTime the day time
     * @param status  the status
     * @param type    the type
     * @return list
     */
    public List<CountVoEx> countVitralRecord(Date dayTime, String status, String type){
        BigDecimal total = new BigDecimal(0);
        CountVoEx totalVo = new CountVoEx();
        List<CountVoEx> countVos = virtualRecordMapper.countVitralRecord(DateHelper.date2String(dayTime, DateHelper.DateFormatType.YearMonthDay), status, type);
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
     * Cancel withdraw boolean.
     *
     * @param id     the id
     * @param reason the reason
     * @return the boolean
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean cancelWithdraw(Long id,String reason){
        boolean flag = true;
        VirtualRecord record = virtualRecordMapper.select(id);
        if (record == null){
            throw  new VirtualBizException("记录不存在");
        }
        if (StringUtils.isEmpty(reason)){
            throw new VirtualBizException("拒绝理由不能为空");
        }
        if (!VirtualRecordOutStatus.APPLY.getCode().equals(record.getStatus())){
            throw new VirtualBizException("记录状态不正确");
        }
        record.setStatus(VirtualRecordOutStatus.REVOKE.getCode());
        record.setReason(reason);
        updateCore(record);
        //通知
        OTCBizPool.getInstance().messageBiz.sendMessage(record.getUserId(), UserMessageConstantEnum.COIN_WITHOUT_FAILD, null,record.getCoinId());
        // 返还用户冻结
        OTCBizPool.getInstance().virtualWalletBiz.restoreSellerFrozen(record.getUserId(),record.getCoinId(),record.getAmount());
        return flag;
    }


    /**
     * Auto recharge.
     *
     * @param record the record
     */
    @Transactional(rollbackFor = Exception.class)
    public void autoRecharge(VirtualRecord record){
        VirtualRecord temp = virtualRecordMapper.select(record.getId());
        if (!VirtualRecordInStatus.SUCCESS.getCode().equals(temp.getStatus())){
            if (VirtualRecordInStatus.SUCCESS.getCode().equals(record.getStatus())){
                // 钱包,为用户自动充值
                VirtualWallet wallet = OTCBizPool.getInstance().virtualWalletBiz.getByUserIdAndCoinId(record.getUserId(),record.getCoinId());
                if (wallet != null){
                    wallet.setTotal(wallet.getTotal().add(record.getAmount()));
                    OTCBizPool.getInstance().virtualWalletBiz.updateCore(wallet);
                }
            }
            // 更新充值记录
            updateCore(record);
            VirtualCoin virtualCoin = OTCBizPool.getInstance().virtualCoinBiz.select(record.getCoinId());
            if(virtualCoin != null){
                OTCBizPool.getInstance().messageBiz.sendMessage(record.getUserId(), UserMessageConstantEnum.COIN_RECHARGE_SUCESS, virtualCoin.getShortName() + ": " + record.getAmount(), record.getCoinId());
            }
        }
    }

    /**
     * Cancel withdraw by user.
     *
     * @param id     the id
     * @param userId the user id
     */
    @Transactional(rollbackFor = Exception.class)
    public void cancelWithdrawByUser(Long id, Long userId) {
        VirtualRecord record = virtualRecordMapper.select(id);
        if (record == null){
            throw new VirtualBizException("记录不存在");
        }
        if (record.getUserId() != userId){
            throw new VirtualBizException("只能撤销自己的数据");
        }
        if (!VirtualRecordOutStatus.APPLY.getCode().equals(record.getStatus())){
            throw new VirtualBizException("记录状态不正确");
        }
        record.setStatus(VirtualRecordOutStatus.REVOKE.getCode());
        updateCore(record);
        BigDecimal amount = record.getAmount().add(record.getFees());
        // 返还用户冻结
        OTCBizPool.getInstance().virtualWalletBiz.restoreSellerFrozen(record.getUserId(), record.getCoinId(), amount);
    }
}
