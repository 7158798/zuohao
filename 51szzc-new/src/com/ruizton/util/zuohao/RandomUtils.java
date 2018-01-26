package com.ruizton.util.zuohao;

import java.util.Random;

/**
 * Created by lx on 17-3-24.
 */
public class RandomUtils {

    /**
     * 获取随机数
     * @param min
     * @param max
     * @return
     */
    public static int getRandom(Integer min,Integer max){
        Random random = new Random();
        int result = random.nextInt(max - min) + min;
        return result;
    }
}
