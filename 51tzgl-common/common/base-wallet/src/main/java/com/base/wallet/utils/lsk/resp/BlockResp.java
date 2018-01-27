package com.base.wallet.utils.lsk.resp;


import com.base.wallet.utils.lsk.bean.Block;

/**
 * Created by lx on 17-5-15.
 */
public class BlockResp extends BaseJson {

    private Block block;

    public Block getBlock() {
        return block;
    }

    public void setBlock(Block block) {
        this.block = block;
    }
}
