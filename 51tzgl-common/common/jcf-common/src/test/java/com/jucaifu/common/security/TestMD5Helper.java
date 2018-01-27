package com.jucaifu.common.security;

import java.security.NoSuchAlgorithmException;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.junit.Test;

/**
 * Created by scofieldcai-dev on 15/8/27.
 */
public class TestMD5Helper {

    @Test
    public void testMD5() throws NoSuchAlgorithmException {

        String name = "scofieldcai";
        String pwd = "123456";

    }

    @Test
    public void md5Hash() {

        // 原始 密码
        String source = "111111";
        // 盐
        String salt = "jcf";
        // 散列次数
        int hashIterations = 1;
        // 上边散列1次：dd37d65fe8597569a02b4d5094df0831
        // 上边散列2次：0814ad246cb8d45a3e202db278dc883a

        String password_md5 = MD5Helper.md5Hash(source, salt, hashIterations);
        System.out.println(password_md5);

        // 第一个参数：散列算法
        SimpleHash simpleHash = new SimpleHash("md5", source, salt, hashIterations);

        System.out.println(simpleHash.toString());
    }

}
