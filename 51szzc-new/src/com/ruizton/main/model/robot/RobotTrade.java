package com.ruizton.main.model.robot;

import com.ruizton.main.Enum.robot.RobotStatusEnum;
import com.ruizton.main.Enum.robot.RobotTypeEnum;
import com.ruizton.main.model.Fadmin;
import com.ruizton.main.model.Ftrademapping;
import com.ruizton.main.model.Fuser;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by lx on 17-3-20.
 */
@Entity
@Table(name = "t_robot_trade")
@Cache(usage= CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RobotTrade {

    private Integer id;

    private Ftrademapping ftrademapping;

    private BigDecimal amount;

    private BigDecimal cost;

    private String status;

    private BigDecimal dealAmount;

    private Fadmin fadmin;

    private Date createDate;

    private Date modifiedDate;

    private Integer version;

    private Fuser fuser;

    private String status_s;

    private String type;

    private String type_s;

    @GenericGenerator(name = "generator", strategy = "increment")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "id",unique = true, nullable = false)
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

    @Column(name = "amount")
    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Column(name = "cost")
    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    @Column(name = "status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Column(name = "deal_amount")
    public BigDecimal getDealAmount() {
        return dealAmount;
    }

    public void setDealAmount(BigDecimal dealAmount) {
        this.dealAmount = dealAmount;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "create_user")
    public Fadmin getFadmin() {
        return fadmin;
    }

    public void setFadmin(Fadmin fadmin) {
        this.fadmin = fadmin;
    }

    @Column(name = "create_date")
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Column(name = "modified_date")
    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    @Version
    @Column(name = "version")
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public Fuser getFuser() {
        return fuser;
    }

    public void setFuser(Fuser fuser) {
        this.fuser = fuser;
    }

    @Transient
    public String getStatus_s() {
        return RobotStatusEnum.getDescByCode(status);
    }

    public void setStatus_s(String status_s) {
        this.status_s = status_s;
    }

    @Column(name = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Transient
    public String getType_s() {
        return RobotTypeEnum.getDescByCode(type);
    }

    public void setType_s(String type_s) {
        this.type_s = type_s;
    }
}
