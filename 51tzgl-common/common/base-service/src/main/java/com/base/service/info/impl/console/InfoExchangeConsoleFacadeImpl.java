package com.base.service.info.impl.console;

import com.base.facade.info.pojo.po.InfoExchange;
import com.base.facade.info.pojo.po.InfoExchangeDetail;
import com.base.facade.info.pojo.vo.InfoExchangeVo;
import com.base.facade.info.service.console.InfoExchangeConsoleFacade;
import com.base.service.info.impl.InfoExchangeFacadeImpl;
import com.base.service.pool.BaseServiceBizPool;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by liuxun on 16-8-22.
 */
@Service("infoExchangeConsoleFacade")
public class InfoExchangeConsoleFacadeImpl extends InfoExchangeFacadeImpl implements InfoExchangeConsoleFacade {

    @Override
    public InfoExchangeVo getExchangePage(InfoExchangeVo vo) {
        return BaseServiceBizPool.getInstance().infoExchangeBiz.getExchangePage(vo);
    }

    @Override
    public List<String> syncExchange() {
        return BaseServiceBizPool.getInstance().infoExchangeBiz.syncExchange();
    }

    @Override
    public BigDecimal calcExchangeRate(String sCurNo, String tCurNo, BigDecimal amount) {
        return BaseServiceBizPool.getInstance().infoExchangeBiz.calcExchangeRate(sCurNo, tCurNo, amount);
    }

    @Override
    public void deleteExchange(String exchangeId) {
        BaseServiceBizPool.getInstance().infoExchangeBiz.deleteExchange(exchangeId);
    }

    @Override
    public void pushExchange(String exchangeId) {
        BaseServiceBizPool.getInstance().infoExchangeBiz.pushExchange(exchangeId);
    }

    @Override
    public void offExchange(String exchangeId) {
        BaseServiceBizPool.getInstance().infoExchangeBiz.offExchange(exchangeId);
    }

    @Override
    public List<InfoExchangeDetail> getExchangeDetailListByExchangeId(String exchangeId) {
        return BaseServiceBizPool.getInstance().infoExchangeDetailBiz.getExchangeDetailListByExchangeId(exchangeId);
    }

    @Override
    public void upload(String fileUrl) {
        BaseServiceBizPool.getInstance().infoExchangeBiz.upload(fileUrl);
    }

    @Override
    public void upload(byte[] bytes, String url) {
        BaseServiceBizPool.getInstance().infoExchangeBiz.upload(bytes, url);
    }

    @Override
    public List<InfoExchange> getExchangeByOverdue() {
        return BaseServiceBizPool.getInstance().infoExchangeBiz.getExchangeByOverdue();
    }

    @Override
    public void clearExchange(String clearDate) {
        BaseServiceBizPool.getInstance().infoExchangeBiz.clearExchange(clearDate);
    }
}
