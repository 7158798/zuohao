package com.szzc.fentrustLog.biz;

import com.jucaifu.core.biz.AbsBaseBiz;
import com.szzc.facade.fentrustLog.pojo.dto.FentrustDto;
import com.szzc.facade.fentrustLog.pojo.po.Fentrust;
import com.szzc.facade.fentrustLog.pojo.vo.FentrustVo;
import com.szzc.fentrustLog.dao.FentrustMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zygong on 17-7-21.
 */
@Service
public class FentrustBiz extends AbsBaseBiz<Fentrust, FentrustVo, FentrustMapper> {

    @Autowired
    private FentrustMapper fentrustMapper;

    @Override
    public FentrustMapper getDefaultMapper() {
        return fentrustMapper;
    }

    /**
     * 获取委托列表
     * @param vo
     * @return
     */
    public FentrustVo getFentrustByConditionPage(FentrustVo vo){
        if(vo.getSymbol() == null || vo.getSymbol() == 0){
            return vo;
        }
        List<FentrustDto> list = fentrustMapper.getFentrustByConditionPage(vo);
        vo.setResultList(list);
        return vo;
    }
}
