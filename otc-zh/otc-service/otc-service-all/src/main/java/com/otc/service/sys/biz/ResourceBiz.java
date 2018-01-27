package com.otc.service.sys.biz;

import com.jucaifu.core.biz.AbsBaseBiz;
import com.otc.facade.sys.pojo.po.Resource;
import com.otc.facade.sys.pojo.vo.ResourceVo;
import com.otc.service.sys.dao.ResourceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by lx on 17-4-28.
 */
@Service
@Transactional
public class ResourceBiz extends AbsBaseBiz<Resource,ResourceVo,ResourceMapper>{

    @Autowired
    private ResourceMapper resourceMapper;
    @Override
    public ResourceMapper getDefaultMapper() {
        return resourceMapper;
    }

    public List<Resource> queryResourceByEmpId(Long empId) {
        return this.resourceMapper.queryResourceByEmpId(empId);
    }

}
