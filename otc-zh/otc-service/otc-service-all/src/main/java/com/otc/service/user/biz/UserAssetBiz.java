package com.otc.service.user.biz;

import com.jucaifu.common.log.LOG;
import com.jucaifu.common.util.DateHelper;
import com.jucaifu.common.util.JsonHelper;
import com.jucaifu.common.util.ReflectHelper;
import com.jucaifu.core.biz.AbsBaseBiz;
import com.otc.facade.user.pojo.po.User;
import com.otc.facade.user.pojo.po.UserAsset;
import com.otc.facade.user.pojo.poex.UserAssetRecord;
import com.otc.facade.user.pojo.vo.UserAssetVo;
import com.otc.facade.virtual.pojo.cond.VirtualWalletCond;
import com.otc.facade.virtual.pojo.po.VirtualWallet;
import com.otc.pool.OTCBizPool;
import com.otc.service.user.dao.UserAssetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by fenggq on 17-5-3.
 */
@Service
public class UserAssetBiz extends AbsBaseBiz<UserAsset,UserAssetVo,UserAssetMapper> {

    @Autowired
    private UserAssetMapper userAssetMapper;

    @Override
    public UserAssetMapper getDefaultMapper() {
        return userAssetMapper;
    }


    /**
     * 生成用户资产记录
     */
    @Transactional(rollbackFor = Exception.class)
    public void addAll(){
        LOG.dStart(this,"增加资产记录");

        List<User> userList = OTCBizPool.getInstance().userBiz.selectUserByContion(null,null);
        Date now = new Date();
        for(User user:userList){
            try{
                VirtualWalletCond cond = new VirtualWalletCond();
                cond.setUserId(user.getId());
                List<VirtualWallet> list = OTCBizPool.getInstance().virtualWalletBiz.queryListVirtualWallet(cond);

                UserAsset userAsset = new UserAsset();
                userAsset.setUserId(user.getId());
                userAsset.setCreateDate(now);

                List<UserAssetRecord> records = new ArrayList<>();
                for(VirtualWallet virtualWallet:list){
                    UserAssetRecord record = new UserAssetRecord();
                    record.setCoinId(virtualWallet.getCoinId());
                    record.setTotal(virtualWallet.getTotal().add(virtualWallet.getFrozen()));
                    record.setFrozen(virtualWallet.getFrozen());
                    records.add(record);
                }
                userAsset.setRecordDetail(JsonHelper.obj2JsonStr(records));
                userAssetMapper.insert(userAsset);
            }catch (Exception e){
                LOG.e(this.getClass(),"记录用户资产出错，用户id:"+user.getId());
                e.printStackTrace();
            }
        }
        LOG.dEnd(this,"增加资产记录");
    }
}
