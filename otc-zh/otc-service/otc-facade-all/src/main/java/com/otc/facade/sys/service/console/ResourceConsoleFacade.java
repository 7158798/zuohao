package com.otc.facade.sys.service.console;

import com.otc.facade.sys.pojo.po.Resource;

import java.util.List;

/**
 * Created by lx on 17-4-28.
 */
public interface ResourceConsoleFacade {


    List<Resource> queryResourceByEmpId(Long empId);

    List<Resource> queryAll();
}
