package com.szzc.common.utils.siac.resp;

import java.util.List;

/**
 * Created by a123 on 17-6-16.
 */
public class Consensus {

    /**
     * synced : false
     * height : 95418
     * currentblock : 00000000000001ca53f72d8df5d2e99a047b1b8e517fd48b4b2ea6f5a66bda60
     * target : [0,0,0,0,0,0,3,250,0,165,130,240,233,159,200,105,94,232,198,224,219,97,142,165,8,8,71,121,15,87,195,216]
     * difficulty : 18120528791970000
     */

    private boolean synced;
    private Long height;
    private String currentblock;
    private String difficulty;
    private List<Integer> target;

    public boolean isSynced() {
        return synced;
    }

    public Long getHeight() {
        return height;
    }

    public void setHeight(Long height) {
        this.height = height;
    }

    public void setSynced(boolean synced) {
        this.synced = synced;
    }



    public String getCurrentblock() {
        return currentblock;
    }

    public void setCurrentblock(String currentblock) {
        this.currentblock = currentblock;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public List<Integer> getTarget() {
        return target;
    }

    public void setTarget(List<Integer> target) {
        this.target = target;
    }
}
