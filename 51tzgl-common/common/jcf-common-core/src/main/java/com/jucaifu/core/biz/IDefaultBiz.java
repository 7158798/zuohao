package com.jucaifu.core.biz;

import com.jucaifu.core.dao.BaseMapper;

/**
 * IDefaultBiz
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 16/1/12.
 */
public interface IDefaultBiz<DefaultMapper extends BaseMapper> {

    DefaultMapper getDefaultMapper();
}
