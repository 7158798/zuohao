package com.otc.service.sys.dao;

import com.jucaifu.core.dao.BaseMapper;
import com.otc.facade.sys.pojo.po.Resource;
import com.otc.facade.sys.pojo.vo.ResourceVo;

import java.util.List;

public interface ResourceMapper extends BaseMapper<Resource,ResourceVo> {

    List<Resource> queryResourceByEmpId(Long empId);
}