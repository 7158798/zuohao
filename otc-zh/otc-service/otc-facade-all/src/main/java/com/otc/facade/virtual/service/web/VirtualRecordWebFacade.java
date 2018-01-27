package com.otc.facade.virtual.service.web;

import com.otc.facade.virtual.pojo.po.VirtualRecord;
import com.otc.facade.virtual.service.VirtualRecordFacade;

/**
 * Created by lx on 17-4-17.
 */
public interface VirtualRecordWebFacade extends VirtualRecordFacade {

    default boolean saveVirtualRecord(VirtualRecord record) {
        return false;
    }
}
