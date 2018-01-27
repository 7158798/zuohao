package com.jucaifu.common.util.mask;

import com.base.BaseTest;
import com.jucaifu.common.log.LOG;
import org.junit.Test;

/**
 * MaskHelper Tester.
 *
 * @author scofieldcai-dev
 * @version 1.0
 * @since 十一月 25, 2015
 */
public class MaskHelperTest extends BaseTest {

    @Override
    public void doTest() {
        //TODO: Test goes here...
    }

    /**
     * Method: maskPhoneNo(String phoneNo)
     */
    @Test
    public void testMaskPhoneNo() throws Exception {
        //TODO: Test goes here... 
    }

    /**
     * Method: maskBankCardNo(String bankCardNo)
     */
    @Test
    public void testMaskBankCardNo() throws Exception {
        //TODO: Test goes here... 
    }

    /**
     * Method: maskIdCardNo(String bankCardNo)
     */
    @Test
    public void testMaskIdCardNo() throws Exception {
        //TODO: Test goes here... 
    }

    /**
     * Method: maskNo(String srcNo, EnumMaskType type)
     */
    @Test
    public void testMaskNo() throws Exception {
        //TODO: Test goes here... 
    }


    /**
     * Method: maskCardNo(String cardNo)
     */
    @Test
    public void testMaskCardNo() throws Exception {

        String phoneNo = "13300000001";
        String idCardNo = "122223300000001xxxxx";
        String banckCardNo = "333333300000001";

        LOG.d(this, MaskHelper.maskNo(phoneNo, EnumMaskType.PHONE_NO));
        LOG.d(this, MaskHelper.maskNo(idCardNo, EnumMaskType.BANKCARD_NO));
        LOG.d(this, MaskHelper.maskNo(banckCardNo, EnumMaskType.IDCARD_NO));
        
    }


} 
