package com.ruizton.main.model.integral;

import com.ruizton.main.Enum.IntegralTypeEnum;
import com.ruizton.main.Enum.IntegralactivityStatusEnum;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * Created by a123 on 17-2-28.
 */
@Entity
@Table(name = "fintegralactivity")
@Cache(usage= CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Fintegralactivity {
    private int id;
    private int type;
    private String typeName;
    private int integral;
    private String content;
    private Integer dayMax;
    private String status;
    private String status_str;
    private Timestamp beginTime;
    private Timestamp endTime;
    private Timestamp createTime;
    private Timestamp updateTime;
    private int needAmount;

    @GenericGenerator(name = "generator", strategy = "increment")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "type")
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Basic
    @Column(name = "integral")
    public int getIntegral() {
        return integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
    }

    @Basic
    @Column(name = "content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Basic
    @Column(name = "day_max")
    public Integer getDayMax() {
        return dayMax;
    }

    public void setDayMax(Integer dayMax) {
        this.dayMax = dayMax;
    }

    @Basic
    @Column(name = "status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Basic
    @Column(name = "begin_time")
    public Timestamp getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Timestamp beginTime) {
        this.beginTime = beginTime;
    }

    @Basic
    @Column(name = "end_time")
    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    @Basic
    @Column(name = "create_time")
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "update_time")
    public Timestamp getUpdateTime() {
        return updateTime;
    }

    @Basic
    @Column(name = "need_amount")
    public int getNeedAmount() {
        return needAmount;
    }

    public void setNeedAmount(int needAmount) {
        this.needAmount = needAmount;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    @Transient
    public String getTypeName() {
        return IntegralTypeEnum.getMap().get(type);
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Transient
    public String getStatus_str() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        String dateString = sdf.format(new java.util.Date());
        Timestamp tm = Timestamp.valueOf(dateString + " 00:00:00");
        if(endTime.getTime() < tm.getTime()){
            this.status_str = IntegralactivityStatusEnum.getNameByValue("4").getName();
        }else{
            this.status_str = IntegralactivityStatusEnum.getNameByValue(status).getName();
        }

        return status_str;
    }

    public void setStatus_str(String status_str) {
        this.status_str = status_str;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Fintegralactivity that = (Fintegralactivity) o;

        if (id != that.id) return false;
        if (type != that.type) return false;
        if (integral != that.integral) return false;
        if (content != null ? !content.equals(that.content) : that.content != null) return false;
        if (dayMax != null ? !dayMax.equals(that.dayMax) : that.dayMax != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (beginTime != null ? !beginTime.equals(that.beginTime) : that.beginTime != null) return false;
        if (endTime != null ? !endTime.equals(that.endTime) : that.endTime != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (updateTime != null ? !updateTime.equals(that.updateTime) : that.updateTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + type;
        result = 31 * result + integral;
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (dayMax != null ? dayMax.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (beginTime != null ? beginTime.hashCode() : 0);
        result = 31 * result + (endTime != null ? endTime.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
        return result;
    }
}
