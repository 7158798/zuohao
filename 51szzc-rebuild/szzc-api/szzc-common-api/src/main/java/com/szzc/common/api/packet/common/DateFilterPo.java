package com.szzc.common.api.packet.common;

import java.io.Serializable;
import java.util.Date;

/**
 * DateFilterPo
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/12/18.
 */
public class DateFilterPo implements Serializable {

    private String name;
    private Date start;
    private Date end;

    public DateFilterPo() {
    }

    public DateFilterPo(String name, Date start, Date end) {
        this.name = name;
        this.start = start;
        this.end = end;
    }

    public DateFilterPo(Date start, Date end) {
        this.start = start;
        this.end = end;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }
}
