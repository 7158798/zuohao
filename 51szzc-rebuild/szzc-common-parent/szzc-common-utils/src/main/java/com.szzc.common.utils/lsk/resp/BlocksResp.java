package com.szzc.common.utils.lsk.resp;


import com.szzc.common.utils.lsk.bean.Block;

import java.util.List;

/**
 * Created by lx on 17-5-15.
 */
public class BlocksResp extends BaseJson {

    private List<Block> blocks;

    public List<Block> getBlocks() {
        return blocks;
    }

    public void setBlocks(List<Block> blocks) {
        this.blocks = blocks;
    }
}
