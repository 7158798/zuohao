package com.otc.service.virtual.biz;

import com.jucaifu.core.biz.AbsBaseBiz;
import com.otc.facade.virtual.pojo.bean.PoolBean;
import com.otc.facade.virtual.pojo.po.Pool;
import com.otc.facade.virtual.pojo.vo.PoolVo;
import com.otc.service.virtual.dao.PoolMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by lx on 17-4-20.
 */
@Service
public class PoolBiz extends AbsBaseBiz<Pool,PoolVo,PoolMapper> {

    @Autowired
    private PoolMapper poolMapper;
    @Override
    public PoolMapper getDefaultMapper() {
        return poolMapper;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean saveList(List<Pool> list){
        boolean flag = false;
        int ret = poolMapper.saveList(list);
        if (ret != 0)
            flag = Boolean.TRUE;
        return flag;
    }

    public Pool queryOnePool(Long coinId){
        Pool pool = poolMapper.queryOnePool(coinId);
        return pool;
    }

    public PoolVo queryCountByConditionPage(PoolVo vo){
        List<PoolBean> list = poolMapper.queryCountByConditionPage(vo);
        if (list != null){
            vo.setResultList(list);
        }
        return vo;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean updateCore(Pool pool){
        boolean flag = false;
        pool.setCreateDate(new Date());
        int ret = poolMapper.update(pool);
        if (ret > 0){
            flag = Boolean.TRUE;
        }
        return flag;
    }

}
