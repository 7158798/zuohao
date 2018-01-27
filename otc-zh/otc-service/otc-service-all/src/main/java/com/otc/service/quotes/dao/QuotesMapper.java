package com.otc.service.quotes.dao;

import com.jucaifu.core.dao.BaseMapper;
import com.otc.facade.quotes.pojo.po.Quotes;
import com.otc.facade.quotes.pojo.vo.QuotesVo;

import java.util.List;

public interface QuotesMapper extends BaseMapper<Quotes, QuotesVo> {
    int saveList(List<Quotes> list);

    void deleteAll();
}