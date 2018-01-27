package com.base.service.welcomepage.dao;


import com.base.facade.welcomepage.pojo.po.WelcomePage;
import com.base.facade.welcomepage.pojo.vo.WelcomePageVo;
import com.jucaifu.core.dao.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface WelcomePageMapper extends BaseMapper<WelcomePage, WelcomePageVo> {

    /**
     * 分页查询欢迎页信息
     *
     * @param vo
     * @return
     */
    public List<WelcomePage> queryWelcomePageListByConditionPage(WelcomePageVo vo);


    List<WelcomePage> getCurrentWelcomePage(@Param("now") Date now);

    /**
     * 查询是否存在交替的欢迎页信息
     * @param vo
     * @return
     */
    public Long queryMergeWelcomePage(WelcomePage vo);

    /**
     * 根据日期查询最大的组序号
     * @param date 日期 YYYYmmdd格式
     * @return
     */
    public String queryMaxTeamNoByDate(String date);

}