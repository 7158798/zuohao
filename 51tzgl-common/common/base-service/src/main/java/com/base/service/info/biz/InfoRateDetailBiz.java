package com.base.service.info.biz;

import com.base.facade.exception.BaseCommonBizException;
import com.base.service.info.dao.InfoRateDetailMapper;
import com.jucaifu.core.biz.AbsBaseBiz;
import com.base.facade.info.enums.InfoRateType;
import com.base.facade.info.pojo.po.InfoRateDetail;
import com.base.facade.info.pojo.poex.InfoRateDetailEx;
import com.base.facade.info.pojo.vo.InfoRateDetailVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by zh on 16-8-22.
 */
@Service
@Transactional
public class InfoRateDetailBiz extends AbsBaseBiz<InfoRateDetail, InfoRateDetailVo, InfoRateDetailMapper> {

    @Autowired
    private InfoRateDetailMapper infoRateDetailMapper;

    @Override
    public InfoRateDetailMapper getDefaultMapper() {
        return infoRateDetailMapper;
    }

    public List<InfoRateDetailEx> selectByRateId(String rateId) {
        if (StringUtils.isBlank(rateId)) {
            throw new BaseCommonBizException("银行利率id不能为空");
        }
        return infoRateDetailMapper.selectByRateId(rateId);
    }

    /**
     * 增加银行明细
     *
     * @param rateId
     * @param type
     * @param itemFisrt
     * @param itemSecond
     * @param period
     * @param rate
     * @return
     */
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public int addInfoRateDetail(String rateId, String type, String itemFisrt, String itemSecond,
                                 String period, String rate) {
        validItem(rateId, type, itemFisrt, rate,itemSecond);

        InfoRateDetail detail = new InfoRateDetail();
        Date now = new Date();
        detail.setRateId(rateId);
        detail.setItemFisrt(itemFisrt);
        detail.setItemSecond(itemSecond);
        detail.setPeriod(period);
        detail.setRate(rate);
        detail.setCreateDatetime(now);
        detail.setModifedDatetime(now);
        return this.insert(detail);
    }

    /**
     * 参数验证
     * @param rateId
     * @param type
     * @param itemFisrt
     * @param rate
     */
    private void validItem(String rateId, String type, String itemFisrt, String rate,String itemSecond) {
        if (StringUtils.isBlank(rateId)) {
            throw new BaseCommonBizException("主表id不能为空");
        }

        if (StringUtils.isBlank(type)) {
            throw new BaseCommonBizException("利率类型不能为空");
        }
        if (StringUtils.isBlank(rate)) {
            throw new BaseCommonBizException(itemFisrt+"    "+itemSecond+" "+"利率不能为空");
        }
        if (type.equals(InfoRateType.DEPOSIT_RATE.getCode())) {
            if (StringUtils.isBlank(itemFisrt)) {
                throw new BaseCommonBizException("项目1不能为空");
            }
        } else if (type.equals(InfoRateType.LOAN_RATE.getCode())) {
            if (StringUtils.isBlank(itemFisrt)) {
                throw new BaseCommonBizException("项目不能为空");
            }
        } else {
            throw new BaseCommonBizException("类型不正确");
        }

//        if(StringUtils.isBlank(period)){
//            throw new BaseCommonBizException("期限不能为空");
//        }
    }

}
