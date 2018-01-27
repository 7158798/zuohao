package com.otc.service.advertisement.biz;

import com.jucaifu.common.util.ValueHelper;
import com.jucaifu.core.biz.AbsBaseBiz;
import com.otc.facade.advertisement.exceptions.AdvertisementTimeBizException;
import com.otc.facade.advertisement.pojo.enums.AdvertisementTimeStatusEnum;
import com.otc.facade.advertisement.pojo.enums.AdvertisementTypeEnum;
import com.otc.facade.advertisement.pojo.po.AdvertisementTime;
import com.otc.facade.advertisement.pojo.vo.AdvertisementTimeVo;
import com.otc.service.advertisement.dao.AdvertisementTimeMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by zygong on 17-4-25.
 */
@Service
@Transactional
public class AdvertisementTimeBiz extends AbsBaseBiz<AdvertisementTime,AdvertisementTimeVo,AdvertisementTimeMapper> {

    @Autowired
    private AdvertisementTimeMapper advertisementTimeMapper;

    @Override
    public AdvertisementTimeMapper getDefaultMapper() {
        return advertisementTimeMapper;
    }

    /**
     * 保存or更新
     * @param advertisementTime
     * @return
     */
    public boolean saveOrUpdate(AdvertisementTime advertisementTime) {

        boolean isSuc = false;
        if (!ValueHelper.checkParam(advertisementTime.getStatus(), advertisementTime.getType(), advertisementTime.getUserId())) {
            throw new AdvertisementTimeBizException("请求参数不能为空");
        }

        if (AdvertisementTimeStatusEnum.getByKey(advertisementTime.getStatus()) == null) {
            throw new AdvertisementTimeBizException("请求参数不正确");
        }

        if (AdvertisementTypeEnum.getByKey(advertisementTime.getType()) == null) {
            throw new AdvertisementTimeBizException("请求参数不正确");
        }

        int result = 0;
        // 如果时间不为全部日期，时间段必须有值
        if (advertisementTime.getStatus() != AdvertisementTimeStatusEnum.QBRQ.getValue()) {
            if (StringUtils.isBlank(advertisementTime.getStartTime1()) || StringUtils.isBlank(advertisementTime.getEndTime1())) {
                throw new AdvertisementTimeBizException("时间设置不正确");
            }
        }

        if (advertisementTime.getId() == null || advertisementTime.getId() == 0) {
            advertisementTime.setCreateTime(new Date());
            advertisementTime.setUpdateTime(new Date());
            result = advertisementTimeMapper.insert(advertisementTime);
        } else {
            advertisementTime.setUpdateTime(new Date());
            result = advertisementTimeMapper.update(advertisementTime);
        }

        if (result > 0) {
            isSuc = true;
        } else {
            throw new AdvertisementTimeBizException("保存失败");
        }

        return isSuc;
    }

    /**
     * 获取详情
     * @param userId
     * @return
     */
    public List<AdvertisementTime> getByUserId(Long userId){
        List<AdvertisementTime> list = null;
        if(userId != null && userId != 0){
            list = advertisementTimeMapper.getByUserId(userId);
        }
        return list;
    }

    /**
     * 删除
     * @param id
     * @return
     */
    public boolean deleteById(Long id){
        boolean isSuc = false;
        if(id != null && id != 0){
            int result = advertisementTimeMapper.delete(id);
            if(result > 0){
                isSuc = true;
            }
        }
        return isSuc;
    }

}
