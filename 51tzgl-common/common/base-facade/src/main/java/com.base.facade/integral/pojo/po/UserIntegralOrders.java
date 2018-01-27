package com.base.facade.integral.pojo.po;

import com.jucaifu.common.pojo.po.BasePo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class UserIntegralOrders extends BasePo implements Serializable {

    /**
     * id，对应表字段为：t_user_integral_orders.id
     */
    private Long id;

    /**
     * 用户主键，对应表字段为：t_user_integral_orders.user_id
     */
    private Long userId;

    /**
     * 应用key，对应表字段为：t_user_integral_orders.app_key
     */
    private String appKey;

    /**
     * 兑吧商城订单号，对应表字段为：t_user_integral_orders.order_num
     */
    private String orderNum;

    /**
     * 订单消耗积分数，对应表字段为：t_user_integral_orders.credits
     */
    private BigDecimal credits;

    /**
     * 积分对应的金额，对应表字段为：t_user_integral_orders.actual_price
     */
    private BigDecimal actualPrice;

    /**
     * 0:初始；1:成功；2:失败，对应表字段为：t_user_integral_orders.order_status
     */
    private Integer orderStatus;

    /**
     * 兑换商品的市场价格，对应表字段为：t_user_integral_orders.face_price
     */
    private BigDecimal facePrice;

    /**
     * 0:预扣；1:成功；2:返还，对应表字段为：t_user_integral_orders.credits_status
     */
    private Integer creditsStatus;

    /**
     * 详情参数，不同的类型，返回不同的内容，中间用英文冒号分隔，对应表字段为：t_user_integral_orders.params
     */
    private String params;

    /**
     * 用户ip，对应表字段为：t_user_integral_orders.ip_addr
     */
    private String ipAddr;

    /**
     * 订单类型，对应表字段为：t_user_integral_orders.type
     */
    private String type;

    /**
     * 描述信息，对应表字段为：t_user_integral_orders.description
     */
    private String description;

    /**
     * 创建时间，对应表字段为：t_user_integral_orders.create_date
     */
    private Date createDate;

    /**
     * 修改时间，对应表字段为：t_user_integral_orders.modified_date
     */
    private Date modifiedDate;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey == null ? null : appKey.trim();
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum == null ? null : orderNum.trim();
    }

    public BigDecimal getCredits() {
        return credits;
    }

    public void setCredits(BigDecimal credits) {
        this.credits = credits;
    }

    public BigDecimal getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(BigDecimal actualPrice) {
        this.actualPrice = actualPrice;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public BigDecimal getFacePrice() {
        return facePrice;
    }

    public void setFacePrice(BigDecimal facePrice) {
        this.facePrice = facePrice;
    }

    public Integer getCreditsStatus() {
        return creditsStatus;
    }

    public void setCreditsStatus(Integer creditsStatus) {
        this.creditsStatus = creditsStatus;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params == null ? null : params.trim();
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr == null ? null : ipAddr.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
}