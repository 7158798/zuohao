package com.base.facade.info.service.console;

import com.base.facade.info.pojo.po.InfoExchange;
import com.base.facade.info.pojo.po.InfoExchangeDetail;
import com.base.facade.info.pojo.vo.InfoExchangeVo;
import com.base.facade.info.service.InfoExchangeFacade;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by liuxun on 16-8-22.
 */
public interface InfoExchangeConsoleFacade extends InfoExchangeFacade {

    /**
     * 获取汇率列表分页
     * @param vo    分页Bean
     * @return
     */
    InfoExchangeVo getExchangePage(InfoExchangeVo vo);

    /**
     * 删除汇率信息
     * @param exchangeId    汇率ID
     */
    void deleteExchange(String exchangeId);

    /**
     * 发布汇率
     * @param exchangeId    汇率ID
     */
    void pushExchange(String exchangeId);


    /**
     * 下架汇率
     * @param exchangeId
     */
    void offExchange(String exchangeId);

    /**
     * 同步汇率
     */
    List<String> syncExchange();

    /**
     * 获取汇率明细
     * @param exchangeId    汇率ID
     * @return
     */
    List<InfoExchangeDetail> getExchangeDetailListByExchangeId(String exchangeId);

    /**
     * 货币兑换
     * @param sCurNo    持有的币种
     * @param tCurNo    兑换的币种
     * @param amount    持有的金额
     * @return
     */
    BigDecimal calcExchangeRate(String sCurNo, String tCurNo, BigDecimal amount);

    /**
     * 导入汇率信息
     * @param fileUrl
     */
    void upload(String fileUrl);


    void upload(byte[] bytes, String url);

    /**
     * 删除过期外汇数据
     * @return
     */
    List<InfoExchange> getExchangeByOverdue();

    /**
     * 清理货币外汇数据
     * @param clearDate
     */
    void clearExchange(String clearDate);
}
