package com.otc.facade.user.pojo.po;

import com.jucaifu.common.pojo.po.BasePo;

import java.io.Serializable;
import java.util.Date;

public class UserAsset extends BasePo implements Serializable {


    /**
     * 以json保存用户资产记录，对应表字段为：t_user_asset.record_detail
     */
    private String recordDetail;

    /**
     * 用户id，对应表字段为：t_user_asset.user_id
     */
    private Long userId;

    /**
     * 创建日期，对应表字段为：t_user_asset.create_date
     */
    private Date createDate;

    private static final long serialVersionUID = 1L;


    public String getRecordDetail() {
        return recordDetail;
    }

    public void setRecordDetail(String recordDetail) {
        this.recordDetail = recordDetail == null ? null : recordDetail.trim();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}