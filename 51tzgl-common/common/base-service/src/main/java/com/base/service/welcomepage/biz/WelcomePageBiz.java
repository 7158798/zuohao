package com.base.service.welcomepage.biz;

import com.base.facade.exception.BaseCommonBizException;
import com.base.service.welcomepage.dao.WelcomePageMapper;
import com.base.facade.welcomepage.pojo.po.WelcomePage;
import com.base.facade.welcomepage.pojo.vo.WelcomePageVo;
import com.jucaifu.common.log.LOG;
import com.jucaifu.common.util.DateHelper;
import com.jucaifu.core.biz.AbsBaseBiz;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by zh on 16-9-5.
 */
@Service
public class WelcomePageBiz extends AbsBaseBiz<WelcomePage, WelcomePageVo, WelcomePageMapper> {
    @Override
    public WelcomePageMapper getDefaultMapper() {
        return welcomePageMapper;
    }

    @Autowired
    private WelcomePageMapper welcomePageMapper;


    /**
     * 添加欢迎页
     *
     * @param welcomePage
     * @return rows受影响的行数
     */
    public int addWelcomePage(WelcomePage welcomePage) {
        LOG.dStart(this, "添加欢迎页开始");
        //数据校验
        validateWelcomePage(welcomePage);
        //生成组号
        String prefix = "HYY";
        Date date = new Date();
        String dateStr = DateHelper.date2String(date, DateHelper.DateFormatType.YearMonthDay_Log);
        String team_no = prefix + dateStr;
        //取出当天最大的组序号
        String maxTeamNo = this.welcomePageMapper.queryMaxTeamNoByDate(dateStr);
        if(StringUtils.isBlank(maxTeamNo) || maxTeamNo.equals("0")){
            team_no += "01";
        }else{
             maxTeamNo = String.valueOf(Long.valueOf(maxTeamNo)+1);
            if (maxTeamNo.length() == 1) {
                maxTeamNo = "0" + maxTeamNo;
            }
        }
        team_no += maxTeamNo;
        welcomePage.setTeamNo(team_no);
        welcomePage.setActive(Boolean.FALSE);
        welcomePage.setCreateDate(date);
        welcomePage.setModifiedDate(date);
        int rows = this.insert(welcomePage);
        LOG.dEnd(this, "添加欢迎页结束");
        return rows;
    }


    /**
     * 编辑欢迎页
     *
     * @param welcomePage
     * @return
     */
    public int updateWelcomePage(WelcomePage welcomePage) {
        LOG.dStart(this, "编辑欢迎页开始");
        //数据校验
        validateWelcomePage(welcomePage);
        if(StringUtils.isBlank(welcomePage.getUuid())){
            throw new BaseCommonBizException("编辑欢迎页时,UUID不能为空");
        }
        //根据UUID查询当前数据
        WelcomePage vo = this.select(welcomePage.getUuid());
        if(vo == null){
            throw new BaseCommonBizException("编辑欢迎页状态,根据UUID查询不到记录");
        }

        //修改除状态、组号之外的，其它有效字段
        Date date = new Date();
        vo.setModifiedDate(date);
        vo.setPageType(welcomePage.getPageType());
        vo.setCloseType(welcomePage.getCloseType());
        if (welcomePage.getCloseType().intValue() == 2) {
            vo.setCloseTimes(welcomePage.getCloseTimes());
        }
        vo.setPageUrl(welcomePage.getPageUrl());
        vo.setStartDate(welcomePage.getStartDate());
        vo.setEndDate(welcomePage.getEndDate());
        int rows = this.update(vo);
        LOG.dEnd(this, "编辑欢迎页结束");
        return rows;
    }

    /**
     * 根据UUID查询欢迎页信息
     *
     * @param uuid
     * @return
     */
    public WelcomePage queryWelcomePageByUUID(String uuid) {
        LOG.dStart(this, "根据UUID查询欢迎页开始");
        if (StringUtils.isBlank(uuid)) {
            throw new BaseCommonBizException("删除欢迎页时，UUID不能为空");
        }
        WelcomePage vo = this.select(uuid);

        LOG.dStart(this, "根据UUID查询欢迎页结束");
        return vo;
    }

    /**
     * 根据UUID删除欢迎页
     *
     * @param uuid
     * @return
     */
    public int deleteWelcomePage(String uuid) {
        LOG.dStart(this, "删除欢迎页开始");
        if (StringUtils.isBlank(uuid)) {
            throw new BaseCommonBizException("删除欢迎页时，UUID不能为空");
        }
        int rows = this.delete(uuid);
        LOG.dEnd(this, "删除欢迎页结束");
        return rows;
    }

    /**
     * 分页查询欢迎页信息
     *
     * @param vo
     * @return
     */
    public WelcomePageVo queryWelcomePageListByConditionPage(WelcomePageVo vo) {
        List<WelcomePage> list = welcomePageMapper.queryWelcomePageListByConditionPage(vo);
        vo.setResultList(list);
        return vo;
    }

    /**
     * 修改生效状态
     * @param uuid
     * @param active
     * @return
     */
    public int updateActiveWelcomePage(String uuid, Boolean active) {
        LOG.dStart(this, "修改欢迎页状态开始");
        //判断同一时间是否已存在欢迎页
        if(StringUtils.isBlank(uuid)){
            throw new BaseCommonBizException("修改欢迎页状态时,UUID不能为空");
        }
        if(active == null){
            throw new BaseCommonBizException("修改欢迎页状态时,状态不能为空");
        }
        //根据UUID查询当前的数据信息
        WelcomePage vo = this.select(uuid);
        if(vo == null){
            throw new BaseCommonBizException("修改欢迎页状态时,根据UUID查询不到记录");
        }
        //如果是启用，则查询是否存在交互
        if(active){
            Long result = welcomePageMapper.queryMergeWelcomePage(vo);
            if(result > 0){
                throw new BaseCommonBizException("同一时间段，不能存在多个欢迎页，请核对时间后，再修改");
            }
        }
        //仅根据UUID修改active、modifiedDate字段
        vo.setModifiedDate(new Date());
        vo.setActive(active);
        int rows = welcomePageMapper.update(vo);
        LOG.dEnd(this, "修改欢迎页状态结束");
        return rows;
    }

    /**
     * 获取当前欢迎页
     * @return
     */
    public WelcomePage getCurrentWelcomePage(){
        String currentDate_str = DateHelper.date2String(new Date(), DateHelper.DateFormatType.YearMonthDay);
        Date currentDate = DateHelper.string2Date(currentDate_str, DateHelper.DateFormatType.YearMonthDay);
        List<WelcomePage> list = getDefaultMapper().getCurrentWelcomePage(currentDate);
        if(list.size() != 1){
            return null;
        }
        return list.get(0);
    }


    /**
     * 校验参数
     *
     * @param welcomePage
     */
    private void validateWelcomePage(WelcomePage welcomePage) {
        if (welcomePage == null) {
            throw new BaseCommonBizException("入参对象welcomePage不能为空");
        }

        if (welcomePage.getPageType() == null) {
            throw new BaseCommonBizException("欢迎页类型不能为空");
        }

        if (welcomePage.getCloseType() == null) {
            throw new BaseCommonBizException("欢迎页关闭方式不能为空");
        } else {
            if (welcomePage.getCloseType().intValue() == 2) {
                if (welcomePage.getCloseTimes() == null) {
                    throw new BaseCommonBizException("欢迎页关闭方式为“倒计时”，倒计秒数不能为空");
                }
            }
        }

        if (StringUtils.isBlank(welcomePage.getPageUrl())) {
            throw new BaseCommonBizException("欢迎页链接地址不能为空");
        }

        if (welcomePage.getStartDate() == null) {
            throw new BaseCommonBizException("启用日期的开始时间不能为空");
        }

        if (welcomePage.getEndDate() == null) {
            throw new BaseCommonBizException("启用日期的结束时间不能为空");
        }

        //启用时间不为空，则判断结束时间一定要大于开始时间
        if (welcomePage.getStartDate() != null && welcomePage.getEndDate() != null && welcomePage.getStartDate().getTime() != welcomePage.getEndDate().getTime()) {
            if(!welcomePage.getEndDate().after(welcomePage.getStartDate())){
                throw new BaseCommonBizException("启用日期的结束时间必须大于等于开始时间");
            }
        }
    }
}
