package com.ruizton.main.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by lx on 17-2-13.
 */
@Entity
@Table(name = "t_trade_log")
@Cache(usage= CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TradeLog implements Serializable {

    private Integer id;

    private Ftrademapping ftrademapping;

    private Fuser user;

    private BigDecimal price;

    private BigDecimal count;

    private Fentrust fentrust;

    private String orderId;

    private int type;

    private int status;

    private Timestamp createDate;

    private Timestamp modifiedDate;

    private int version;
    // 成交数量
    private BigDecimal dealCount;
    // 交易帐号
    private TradeAccount tradeAccount;
    // 关联订单
    private Fentrust relFentrust;
    // 错误代码
    private String errorCode;


    @GenericGenerator(name = "generator", strategy = "increment")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "id", unique = true, nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trade_mapping_id")
    public Ftrademapping getFtrademapping() {
        return ftrademapping;
    }

    public void setFtrademapping(Ftrademapping ftrademapping) {
        this.ftrademapping = ftrademapping;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public Fuser getUser() {
        return user;
    }

    public void setUser(Fuser user) {
        this.user = user;
    }

    @Column(name = "price")
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Column(name = "count")
    public BigDecimal getCount() {
        return count;
    }

    public void setCount(BigDecimal count) {
        this.count = count;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fentrust_id")
    public Fentrust getFentrust() {
        return fentrust;
    }

    public void setFentrust(Fentrust fentrust) {
        this.fentrust = fentrust;
    }

    @Column(name = "order_id")
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Column(name = "type")
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Column(name = "status")
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Column(name = "create_date")
    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    @Column(name = "modified_date")
    public Timestamp getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Timestamp modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    @Version
    @Column(name = "version")
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Column(name = "deal_count")
    public BigDecimal getDealCount() {
        return dealCount;
    }

    public void setDealCount(BigDecimal dealCount) {
        this.dealCount = dealCount;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trade_account_id")
    public TradeAccount getTradeAccount() {
        return tradeAccount;
    }

    public void setTradeAccount(TradeAccount tradeAccount) {
        this.tradeAccount = tradeAccount;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rel_fentrust_id")
    public Fentrust getRelFentrust() {
        return relFentrust;
    }

    public void setRelFentrust(Fentrust relFentrust) {
        this.relFentrust = relFentrust;
    }

    @Column(name = "error_code")
    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
