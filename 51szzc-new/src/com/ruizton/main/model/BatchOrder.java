package com.ruizton.main.model;

import com.ruizton.main.Enum.EntrustStatusEnum;
import com.ruizton.main.Enum.EntrustTypeEnum;
import com.ruizton.main.Enum.batch.BatchOrderStatusEnum;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by lx on 17-3-8.
 */
@Entity
@Table(name = "t_batch_order")
@Cache(usage= CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BatchOrder {

    private Integer id;
    private Ftrademapping ftrademapping;
    private Fuser user;
    private int type;
    private String priceScope;
    private String amountScope;
    private int orderNum;

    private String entrusts;
    private Timestamp createDate;
    private Timestamp modifiedDate;
    private int version;
    private String type_s;
    // 改为保留的小数
    private BigDecimal ratio;
    private String status;
    private String status_s;

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

    @Column(name = "type")
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Column(name = "price_scope")
    public String getPriceScope() {
        return priceScope;
    }

    public void setPriceScope(String priceScope) {
        this.priceScope = priceScope;
    }

    @Column(name = "amount_scope")
    public String getAmountScope() {
        return amountScope;
    }

    public void setAmountScope(String amountScope) {
        this.amountScope = amountScope;
    }

    @Column(name = "order_num")
    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    @Column(name = "entrusts")
    public String getEntrusts() {
        return entrusts;
    }

    public void setEntrusts(String entrusts) {
        this.entrusts = entrusts;
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

    @Transient
    public String getType_s() {
        int type = this.getType();
        return EntrustTypeEnum.getEnumString(type);
    }

    public void setType_s(String type_s) {
        this.type_s = type_s;
    }

    @Column(name = "ratio")
    public BigDecimal getRatio() {
        return ratio;
    }

    public void setRatio(BigDecimal ratio) {
        this.ratio = ratio;
    }

    @Column(name = "status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Transient
    public String getStatus_s() {
        if (StringUtils.isEmpty(status)){
            return status;
        }
        return BatchOrderStatusEnum.getDescByCode(status);
    }

    public void setStatus_s(String status_s) {
        this.status_s = status_s;
    }
}
