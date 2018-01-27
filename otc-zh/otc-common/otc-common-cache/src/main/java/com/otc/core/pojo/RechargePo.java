package com.otc.core.pojo;

import java.io.Serializable;

/**
 * Created by lx on 17-4-20.
 */
public class RechargePo implements Serializable {
    // 上次的请求id
    private String lastTxId;
    // 上次的区块数值,
    private Long block;

    public String getLastTxId() {
        return lastTxId;
    }

    public void setLastTxId(String lastTxId) {
        this.lastTxId = lastTxId;
    }

    public Long getBlock() {
        return block;
    }

    public void setBlock(Long block) {
        this.block = block;
    }
}
