package com.ruizton.main.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by mibook3 on 17-4-12.
 */

@Entity
@Table(name="feassycontestinfo")
public class EassyContestInfo {
  @GenericGenerator(name = "generator", strategy = "increment")
  @Id
  @GeneratedValue(generator = "generator")
  @Column(name = "id", unique = true, nullable = false)
  private int id;
  private   String school;
  private String name;
  private String telephone;
  private String department;
  private String major;
  private String grade;
  private String photoUrl;
  private String eassyUrl;
  private Date  createDate ;
  private Date updateDate;
  private int status;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getSchool() {
    return school;
  }

  public void setSchool(String school) {
    this.school = school;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getTelephone() {
    return telephone;
  }

  public void setTelephone(String telephone) {
    this.telephone = telephone;
  }

  public String getDepartment() {
    return department;
  }

  public void setDepartment(String department) {
    this.department = department;
  }

  public String getMajor() {
    return major;
  }

  public void setMajor(String major) {
    this.major = major;
  }

  public String getGrade() {
    return grade;
  }

  public void setGrade(String grade) {
    this.grade = grade;
  }

  public String getPhotoUrl() {
    return photoUrl;
  }

  public void setPhotoUrl(String photoUrl) {
    this.photoUrl = photoUrl;
  }

  public String getEassyUrl() {
    return eassyUrl;
  }

  public void setEassyUrl(String eassyUrl) {
    this.eassyUrl = eassyUrl;
  }

  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  public Date getUpdateDate() {
    return updateDate;
  }

  public void setUpdateDate(Date update) {
    this.updateDate = update;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }




}
