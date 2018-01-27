package com.otc.service.quotes.biz;

import com.jucaifu.core.biz.AbsBaseBiz;
import com.otc.facade.quotes.pojo.po.Quotes;
import com.otc.facade.quotes.pojo.vo.QuotesVo;
import com.otc.service.quotes.dao.QuotesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by zygong on 17-5-9.
 */
@Service
@Transactional
public class QuotesBiz extends AbsBaseBiz<Quotes,QuotesVo,QuotesMapper> {

    @Autowired
    private QuotesMapper quotesMapper;

    @Override
    public QuotesMapper getDefaultMapper() {
        return quotesMapper;
    }

    public void saveList(List<Quotes> list){
        if(list != null && list.size() > 0){
            quotesMapper.deleteAll();
            quotesMapper.saveList(list);
        }
    }
}
