package com.jucaifu.common.util;

import com.base.BaseTest;
import com.jucaifu.common.log.LOG;
import com.jucaifu.common.util.model.UserPo;
import org.junit.Test;

/** 
* SerializeHelper Tester. 
* 
* @author scofieldcai-dev 
* @since  十一月 19, 2015 
* @version 1.0 
*/ 
public class SerializeHelperTest extends BaseTest {

    @Override
    public void doTest() {
        UserPo userPo = new UserPo();
        userPo.setName("==name===");

        try {
            byte[] bytes = SerializeHelper.serialize(userPo);
            LOG.d(this,bytes);
            LOG.dEnd(this, "序列化完成");
            userPo = SerializeHelper.unSerialize(bytes);
            LOG.d(this,userPo);
            LOG.dEnd(this, "反序列化完成");

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /** 
     * 
     * Method: serialize(Object object) 
     * 
     */ 
    @Test
    public void testSerialize() throws Exception { 
        //TODO: Test goes here... 
    } 

    /** 
     * 
     * Method: unSerialize(byte[] bytes) 
     * 
     */ 
    @Test
    public void testUnSerialize() throws Exception { 
        //TODO: Test goes here... 
    } 




} 
