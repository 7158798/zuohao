package com.szzc.facade.out.pojo;


import com.szzc.facade.fentrustLog.pojo.dto.TradeVo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zygong on 17-5-25.
 */
public class TradeListDto implements Serializable {
    private List<TradeVo> list;

    public List<TradeVo> getList() {
        return list;
    }

    public void setList(List<TradeVo> list) {
        this.list = list;
    }
}
