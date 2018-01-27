package com.jucaifu.common.pojo.vo;

import java.util.List;

import com.jucaifu.common.log.LOG;
import com.jucaifu.common.util.page.Page;

/**
 * BaseSubPageResp
 *
 * @param <Result>  the type parameter
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/11/16.
 */
public class BaseSubPageResp<Result> {

    /**
     * The Page.
     */
    protected Page<Result> page;


    /**
     * Update page.
     *
     * @param basePageVo the base vo
     * @param list the list
     */
    public void updatePage(BasePageVo basePageVo, List<Result> list) {
//        LOG.dStart(this,"updatePage");
//        LOG.d(this, baseVo);
//        LOG.d(this,list);
        Page<Result> body_page = basePageVo.getPage();
        body_page.setResult(list);
        this.page = body_page;
//        LOG.dEnd(this,"updatePage");
        LOG.d(this,page);
    }

    public Page<Result> getPage() {
        return page;
    }
}
