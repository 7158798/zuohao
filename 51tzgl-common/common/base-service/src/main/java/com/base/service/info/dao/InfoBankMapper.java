package com.base.service.info.dao;

import com.jucaifu.core.dao.BaseMapper;
import com.base.facade.info.pojo.po.InfoBank;
import com.base.facade.info.pojo.vo.InfoBankVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InfoBankMapper extends BaseMapper<InfoBank,InfoBankVo> {
    InfoBank selectByName(@Param("bankName") String bankName);

    /**
     * 获取所有的银行列表
     * @param bankModle 关联相关的模块
     * @return
     */
    List<InfoBank> getInfoBankForRelease(@Param("bankModle") String bankModle);
}