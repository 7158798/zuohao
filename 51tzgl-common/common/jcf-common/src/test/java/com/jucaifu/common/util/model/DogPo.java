package com.jucaifu.common.util.model;

import com.jucaifu.common.annotation.ColumnAnno;

/**
 * Created by scofieldcai-dev on 15/8/25.
 */
public class DogPo {

    @ColumnAnno("name")
    private String name1;
    private String pwd;

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }
}
