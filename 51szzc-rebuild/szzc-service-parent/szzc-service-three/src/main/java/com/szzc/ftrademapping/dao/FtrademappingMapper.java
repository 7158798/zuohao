package com.szzc.ftrademapping.dao;

import com.szzc.facade.ftrademapping.pojo.dto.FtrademappingDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FtrademappingMapper {

    List<FtrademappingDto> getActiveCoin();
}