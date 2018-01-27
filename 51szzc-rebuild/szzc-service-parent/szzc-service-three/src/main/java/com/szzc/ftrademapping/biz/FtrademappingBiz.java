package com.szzc.ftrademapping.biz;

import com.szzc.facade.ftrademapping.pojo.dto.FtrademappingDto;
import com.szzc.ftrademapping.dao.FtrademappingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zygong on 17-7-19.
 */
@Service
public class FtrademappingBiz {
    @Autowired
    private FtrademappingMapper ftrademappingMapper;

    public List<FtrademappingDto> getActiveCoin(){
        List<FtrademappingDto> list = null;
        list = ftrademappingMapper.getActiveCoin();
        return list;
    }
}
