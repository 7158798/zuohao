package com.base.facade.welcomepage.console;


import com.base.facade.welcomepage.WelcomePageFacade;
import com.base.facade.welcomepage.pojo.po.WelcomePage;
import com.base.facade.welcomepage.pojo.vo.WelcomePageVo;

/**
 * Created by zh on 16-9-5.
 */
public interface WelcomePageConsoleFacade extends WelcomePageFacade {

    /**
     * 添加欢迎页
     * @param welcomePage
     * @return rows 受影响的行数
     */
    int addWelcomePage(WelcomePage welcomePage);

    /**
     * 编辑欢迎页
     * @param welcomePage
     * @return
     */
    int updateWelcomePage(WelcomePage welcomePage);

    /**
     * 根据UUID查询欢迎页信息
     * @param uuid
     * @return
     */
    WelcomePage queryWelcomePageByUUID(String uuid);

    /**
     * 根据UUID删除欢迎页
     * @param uuid
     * @return
     */
    int deleteWelcomePage(String uuid);

    /**
     * 分页查询欢迎页信息
     * @param vo
     * @return
     */
    WelcomePageVo queryWelcomePageListByConditionPage(WelcomePageVo vo);

    /**
     * 修改欢迎页的生效状态
     * @param uuid
     * @param active
     * @return
     */
    int updateActiveWelcomePage(String uuid, Boolean active);
}
