package com.base.weixin;

import com.base.BaseSpringTest;
import com.base.facade.weixin.pojo.bean.WeixinRedPacket;
import com.base.facade.weixin.response.WeixinResponse;
import com.base.facade.weixin.service.web.WeixinWebFacade;
import com.jucaifu.common.log.LOG;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * Created by liuxun on 16-10-14.
 */
public class RaidersAppFacadeTest extends BaseSpringTest {

    @Resource(name = "weixinWebFacade")
    private WeixinWebFacade weixinWebFacade;
    @Override
    public void doTest() {

    }

    @Test
    public void testSaveRaiders(){
        WeixinRedPacket redPack = new WeixinRedPacket();
        redPack.setMch_id("10000098");
        redPack.setWxappid("wx8888888888888888");
        redPack.setSend_name("天虹百货");
        redPack.setRe_openid("oxTWIuGaIt6gTKsQRLau2M0yL16E");
        redPack.setTotal_amount(100);
        redPack.setWishing("test");
        redPack.setClient_ip("110.110.110.110");
        redPack.setAct_name("ok");
        redPack.setRemark("remark");
        WeixinResponse response = weixinWebFacade.sendRedPacket(redPack);
        LOG.d(this,response);
        /*
        if (StringUtils.isEmpty(redPack.getMch_id())){
            throw new BaseCommonBizException("商户号不能为空");
        }
        if (StringUtils.isEmpty(redPack.getWxappid())){
            throw new BaseCommonBizException("公众账号appid不能为空");
        }
        if (StringUtils.isEmpty(redPack.getSend_name())){
            throw new BaseCommonBizException("商户名称不能为空");
        }
        if (StringUtils.isEmpty(redPack.getRe_openid())){
            throw new BaseCommonBizException("用户openid不能为空");
        }
        if (redPack.getTotal_num() == 0){
            throw new BaseCommonBizException("付款金额不能为零");
        }
        // 发放人数
        redPack.setTotal_num(1);
        if (StringUtils.isEmpty(redPack.getWishing())){
            throw new BaseCommonBizException("红包祝福语不能为空");
        }
        if (StringUtils.isEmpty(redPack.getClient_ip())){
            throw new BaseCommonBizException("ip地址不能为空");
        }
        if (StringUtils.isEmpty(redPack.getAct_name())){
            throw new BaseCommonBizException("活动名称不能为空");
        }
        if (StringUtils.isEmpty(redPack.getRemark())){
            throw new BaseCommonBizException("备注不能为空");
        }
        String keywords = "37,38,39,40";
        for (int i = 1; i <= 100000; i ++){
            System.out.print("开始－－－－－" + i);
            Raiders raiders = new Raiders();
            raiders.setTitle("测试压力第三次添加ylabc124-" + i);
            String content = "123123123123123123123123123123";
            raiders.setStatus("01");
            raiders.setRaidersTag("03");
            raiders.setClassId(1009l);
            raiders.setClassName("A股");
            raiders.setContent(content.getBytes());
            raiders.setPushDate(new Date());
            raiders.setCreateDate(new Date());
            raidersAppFacade.saveRaiders(raiders, keywords);
        }*/
    }
}
