package com.szzc.core.wallet.biz;

import com.jucaifu.core.biz.AbsBaseBiz;
import com.szzc.core.wallet.dao.PlatformBalanceMapper;
import com.szzc.facade.wallet.pojo.po.PlatformBalance;
import com.szzc.facade.wallet.pojo.vo.PlatformBalanceVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by luwei on 17-6-13.
 */
@Service
public class PlatformBalanceBiz extends AbsBaseBiz<PlatformBalance, PlatformBalanceVo, PlatformBalanceMapper> {

    @Autowired
    private PlatformBalanceMapper platformBalanceMapper;

    @Override
    public PlatformBalanceMapper getDefaultMapper() {
        return platformBalanceMapper;
    }



    /**
     * 根据日期判断当天是否有数据存在
     * @param date  yyyy-mm-dd格式
     * @return
     */
    public Integer queryExistsByDate(String date) {
        return platformBalanceMapper.queryExistsByDate(date);
    }

    /**
     * 根据日期删除指定时间的数据
     * @param date yyyy-mm-dd格式
     */
    public void deleteByDate(String date) {
        this.platformBalanceMapper.deleteByDate(date);
    }
}
