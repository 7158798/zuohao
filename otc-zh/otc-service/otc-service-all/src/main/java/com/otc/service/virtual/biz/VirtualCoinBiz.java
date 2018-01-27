package com.otc.service.virtual.biz;

import com.base.wallet.utils.BTCMessage;
import com.base.wallet.utils.BTCUtils;
import com.base.wallet.utils.IWalletUtil;
import com.jucaifu.common.log.LOG;
import com.jucaifu.common.util.GroupHelper;
import com.jucaifu.core.biz.AbsBaseBiz;
import com.otc.core.cache.CacheHelper;
import com.otc.facade.cache.CacheKey;
import com.otc.facade.user.pojo.po.User;
import com.otc.facade.virtual.enums.PoolStatus;
import com.otc.facade.virtual.enums.VirtualCoinStatus;
import com.otc.facade.virtual.exceptions.VirtualBizException;
import com.otc.facade.virtual.pojo.cond.VirtualCoinCond;
import com.otc.facade.virtual.pojo.po.Pool;
import com.otc.facade.virtual.pojo.po.VirtualCoin;
import com.otc.facade.virtual.pojo.po.Withdrawfees;
import com.otc.facade.virtual.pojo.vo.VirtualCoinVo;
import com.otc.pool.OTCBizPool;
import com.otc.service.virtual.dao.VirtualCoinMapper;
import com.otc.service.virtual.dao.WithdrawfeesMapper;
import com.otc.util.WalletUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by lx on 17-4-20.
 */
@Service
public class VirtualCoinBiz extends AbsBaseBiz<VirtualCoin,VirtualCoinVo,VirtualCoinMapper> {

    @Autowired
    private VirtualCoinMapper virtualCoinMapper;

    @Autowired
    private WithdrawfeesMapper withdrawfeesMapper;
    @Override
    public VirtualCoinMapper getDefaultMapper() {
        return virtualCoinMapper;
    }

    private void filter(VirtualCoin coin){
        if (coin == null){
            throw new VirtualBizException("请求对象不能为空");
        }
        if (StringUtils.isEmpty(coin.getName())){
            throw new VirtualBizException("虚拟币名称不能为空");
        }
        if (StringUtils.isEmpty(coin.getShortName())){
            throw new VirtualBizException("虚拟币简称不能为空");
        }
        if (StringUtils.isEmpty(coin.getAccessKey())){
            throw new VirtualBizException("AccessKey不能为空");
        }
        if (StringUtils.isEmpty(coin.getSecretKey())){
            throw new VirtualBizException("SecretKey不能为空");
        }
        if (StringUtils.isEmpty(coin.getIp())){
            throw new VirtualBizException("虚拟币IP不能为空");
        }
        if (StringUtils.isEmpty(coin.getPort())){
            throw new VirtualBizException("虚拟币端口不能为空");
        }
        if (StringUtils.isEmpty(coin.getStatus())){
            throw new VirtualBizException("虚拟币状态不能为空");
        }
        VirtualCoinStatus status = VirtualCoinStatus.statusMap.get(coin.getStatus());
        if (status == null){
            throw new VirtualBizException("虚拟币状态不正确");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean saveVirtualCoin(VirtualCoin coin){
        boolean flag = false;
        filter(coin);
        int ret = virtualCoinMapper.insert(coin);
        if (ret != 0)
            flag = Boolean.FALSE;
        return flag;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean updateVirtualCoin(VirtualCoin coin){
        filter(coin);
        if (coin.getId() == null){
            throw new VirtualBizException("虚拟币ID不能为空");
        }
        VirtualCoin temp = isExist(coin.getId());
        temp.setIsAuto(coin.getIsAuto());
        temp.setAccessKey(coin.getAccessKey());
        temp.setSecretKey(coin.getSecretKey());
        temp.setIp(coin.getIp());
        temp.setIsRecharge(coin.getIsRecharge());
        temp.setIsWithDraw(coin.getIsWithDraw());
        temp.setName(coin.getName());
        temp.setPort(coin.getPort());
        temp.setShortName(coin.getShortName());
        temp.setMainAddress(coin.getMainAddress());
        temp.setIconUrl(coin.getIconUrl());
        temp.setLowTradeFees(coin.getLowTradeFees());
        temp.setTradeFees(coin.getTradeFees());
        temp.setLowRechargeFees(coin.getLowRechargeFees());
        temp.setRechargeFees(coin.getRechargeFees());
        temp.setLowWithdrawFees(coin.getLowWithdrawFees());
        temp.setWithdrawFees(coin.getWithdrawFees());
        temp.setSingleLowRecharge(coin.getSingleLowRecharge());
        temp.setSingleHighRecharge(coin.getSingleHighRecharge());
        temp.setDayHighRecharge(coin.getDayHighRecharge());
        temp.setSingleLowWithdraw(coin.getSingleLowWithdraw());
        temp.setSingleHighWithdraw(coin.getSingleHighWithdraw());
        temp.setDayHighWithdraw(coin.getDayHighWithdraw());
        temp.setIconUrl2(coin.getIconUrl2());
        temp.setRechargeDes(coin.getRechargeDes());
        temp.setWithdrawDes(coin.getWithdrawDes());
        boolean flag = updateCore(temp);
        if (flag){
            updateCache(CacheKey.VIRTUAL_COIN_KEY,temp);
        }
        return flag;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean updateStatus(Long id,String status,String walletPwd){
        if (id == null){
            throw new VirtualBizException("虚拟币ID不能为空");
        }
        if (StringUtils.isEmpty(status)){
            throw new VirtualBizException("虚拟币状态不能为空");
        }
        VirtualCoinStatus temp = VirtualCoinStatus.statusMap.get(status);
        if (temp == null)
            throw new VirtualBizException("虚拟币状态不正确");
        VirtualCoin coin = isExist(id);
        if (StringUtils.equals(status,coin.getStatus())){
            throw new VirtualBizException("虚拟币状态与修改状态一致");
        }
        if (VirtualCoinStatus.ENABLED.getCode().equals(status)){
            // 启用需要密码
            if (StringUtils.isEmpty(walletPwd)){
                throw new VirtualBizException("钱包密码不能为空");
            }

            IWalletUtil iWalletUtil = WalletUtils.getWalletObj(coin, walletPwd);
            BTCUtils btcUtils = (BTCUtils) iWalletUtil;
            try {
                boolean flag = btcUtils.walletpassphrase(10);
                if (!flag){
                    throw new VirtualBizException("钱包密码不正确");
                }
                // 锁定钱包
                btcUtils.walletlock();
            } catch (Exception e) {
                String error = "钱包连接失败";
                if (e instanceof VirtualBizException){
                    error = e.getMessage();
                }
                LOG.e(this,e.getMessage(),e);
                throw new VirtualBizException(error);
            }
        }
        coin.setStatus(status);
        boolean flag = updateCore(coin);
        if (flag){
           updateCache(CacheKey.VIRTUAL_COIN_KEY,coin);
        }
        if (VirtualCoinStatus.ENABLED.getCode().equals(coin.getStatus())){
            // 启用币种
            List<User> list = OTCBizPool.getInstance().userBiz.queryNonExistentWallet(coin.getId());
            if (list != null && list.size() > 0){
                // 查询还没有创建钱包的用户
                OTCBizPool.getInstance().virtualWalletBiz.createWalletByCoin(coin.getId(), list);
            }
        }
        return flag;
    }

    /**
     * 更新数据到缓存中
     * @param cacheKey
     * @param coin
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateCache(String cacheKey,VirtualCoin coin){
        HashMap<Long,VirtualCoin> coinMap = CacheHelper.getObj(cacheKey);
        if (coinMap == null){
            coinMap = new HashMap<>();
        }
        if (VirtualCoinStatus.ENABLED.getCode().equals(coin.getStatus())){
            //　币种添加到缓存
            coinMap.put(coin.getId(),coin);
        } else {
            // 清理币种
            coinMap.remove(coin.getId());
        }
        CacheHelper.saveObj(cacheKey,coinMap);
    }

    /**
     * 共通更新
     * @param coin
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    private boolean updateCore(VirtualCoin coin){
        boolean flag = false;
        coin.setModifiedDate(new Date());
        int ret = virtualCoinMapper.update(coin);
        if (ret != 0)
            flag = Boolean.TRUE;
        return flag;
    }

    public VirtualCoin isExist(Long id){
        VirtualCoin coin = virtualCoinMapper.select(id);
        if (coin == null)
            throw new VirtualBizException("虚拟币不存在");
        return coin;
    }

    @Transactional(rollbackFor = Exception.class)
    public void createAddress(VirtualCoin coin,String walletPwd){
        BTCMessage message = new BTCMessage();
        message.setACCESS_KEY(coin.getAccessKey()) ;
        message.setIP(coin.getIp()) ;
        message.setPORT(coin.getPort()) ;
        message.setSECRET_KEY(coin.getSecretKey()) ;
        message.setPASSWORD(walletPwd);
        BTCUtils walletUtil = new BTCUtils(message);
        List<Pool> poolList = new ArrayList<>();
        Pool pool;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:sss");
        try {
            for (int i=0;i<50;i++){
                Date date = new Date();
                String address = walletUtil.getNewaddressValueForAdmin(sdf.format(date));
                pool = new Pool();
                pool.setAddress(address);
                pool.setCoinId(coin.getId());
                pool.setCreateDate(new Date());
                pool.setStatus(PoolStatus.UNUSED.getCode());
                poolList.add(pool);
            }
            if (poolList.size() > 0){
                OTCBizPool.getInstance().poolBiz.saveList(poolList);
            }
        } catch (Exception e) {
            LOG.e(this,coin.getName() + ": 生成地址失败,原因=" + e.getMessage(),e);
        } finally {
            try {
                walletUtil.walletlock();
            } catch (Exception e) {
                LOG.e(this,coin.getName() + "：锁定钱包失败.",e);
            }
        }
    }

    public VirtualCoinVo queryCountByConditionPage(VirtualCoinVo vo){
        List<VirtualCoin> list = virtualCoinMapper.queryCountByConditionPage(vo);
        if (list != null){
            vo.setResultList(list);
        }
        return vo;
    }

    /**
     * 获取币种数据
     * @param isAll
     * @return
     */
    public Map<Long,VirtualCoin> getVirtualCoin(Boolean isAll){
        Map<Long,VirtualCoin> map = null;
        if (!isAll){
            map = CacheHelper.getObj(CacheKey.VIRTUAL_COIN_KEY);
        }
        if (map == null || map.isEmpty()){
            VirtualCoinCond cond = new VirtualCoinCond();
            if (!isAll){
                // 已发布
                cond.setStatus(VirtualCoinStatus.ENABLED.getCode());
            }
            List<VirtualCoin> list = virtualCoinMapper.queryListByCondition(cond);
            if (list != null){
                try {
                    map = GroupHelper.groupTypeByFieldName(Long.class,"id",list);
                } catch (Exception e) {
                    LOG.e(this,"货币数据分组失败",e);
                }
            }
        }
        return map == null?new HashMap<>():map;
    }


    /**
     * 增加提现手续费配置
     * @param list
     * @return
     */
    public boolean addCoinFee(List<Withdrawfees> list) {
        boolean flag = false;
        if(list == null || list.isEmpty()){
            return flag;
        }
        int insert = withdrawfeesMapper.insertList(list);
        if(insert > 0){
            flag = true;
        }
        return flag;
    }

    /**
     * 更新提现手续费配置
     * @param list
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean updateCoinFee(List<Withdrawfees> list) {
        boolean flag = false;
        if(list == null || list.isEmpty()){
            return flag;
        }
        int delete = withdrawfeesMapper.deleteByCoinId(list.get(0).getVid());
        if(delete <= 0){
            return flag;
        }
        int insert = withdrawfeesMapper.insertList(list);
        if(insert > 0){
            flag = true;
        }
        return flag;
    }

    /**
     * 通过币种id获取手续费配置
     * @param coinId
     * @return
     */
    public List<Withdrawfees> getFees(Long coinId) {
        List<Withdrawfees> list = new ArrayList<>();
        if(coinId == null){
            return list;
        }
        list = withdrawfeesMapper.selectByConId(coinId);
        return list;
    }

    /**
     * 通过币种简称判断币种是否已存在
     * @param coin
     * @return
     */
    public boolean checkIfExist(VirtualCoin coin) {
        List<VirtualCoin> list = virtualCoinMapper.queryListByShortName(coin);
        if(list.isEmpty()){
            return false;
        }
        return true;
    }
}
