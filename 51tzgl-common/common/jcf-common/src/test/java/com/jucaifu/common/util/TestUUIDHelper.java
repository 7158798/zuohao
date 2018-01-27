package com.jucaifu.common.util;

import org.junit.Test;

/**
 * Created by scofieldcai-dev on 15/8/25.
 */
public class TestUUIDHelper {

    @Test
    public void uuid() {
        String uuid = UUIDHelper.getUUID();
        System.out.println("uuid = " + uuid);
    }

    @Test
    public void uuid32() {
        String uuid32 = UUIDHelper.get32UUID();
        System.out.println("uuid32 = " + uuid32);
    }
}
