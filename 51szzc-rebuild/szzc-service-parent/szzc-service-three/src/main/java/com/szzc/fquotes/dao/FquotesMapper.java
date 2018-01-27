package com.szzc.fquotes.dao;

import com.szzc.facade.fquotes.pojo.po.Fquotes;

import java.util.List;

public interface FquotesMapper{
    /**
     * 批量保存
     * @param list
     * @return
     */
    int saveList(List<Fquotes> list);
}