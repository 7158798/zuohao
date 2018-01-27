package com.otc.service.sys.biz;

import com.jucaifu.common.exceptions.biz.BizException;
import com.jucaifu.common.log.LOG;
import com.jucaifu.core.biz.AbsBaseBiz;
import com.otc.facade.sys.enums.SystemArgsEnum;
import com.otc.facade.sys.pojo.po.SystemArgs;
import com.otc.facade.sys.pojo.vo.SystemArgsVo;
import com.otc.service.sys.dao.SystemArgsMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by fenggq on 17-5-10.
 */
@Service
public class SystemArgsBiz extends AbsBaseBiz<SystemArgs,SystemArgsVo,SystemArgsMapper>{

    @Autowired
    private SystemArgsMapper systemArgsMapper;

    @Override
    public SystemArgsMapper getDefaultMapper() {
        return systemArgsMapper;
    }

    /**
     * 获取系统参数配置String
     * @param systemArgsEnum
     * @return
     */
    public String getSystemArgs(SystemArgsEnum systemArgsEnum){
        LOG.dStart(this,"获取系统参数");
        if(systemArgsEnum == null){
            throw new BizException("参数类型无效");
        }
        SystemArgs systemArgs = this.systemArgsMapper.getSystemArgsByKey(systemArgsEnum.getKey());
        if(systemArgs != null){
            return systemArgs.getValue();
        }
        LOG.dEnd(this,"获取系统参数");
        return "";
    }


    /**
     * 获取Long类型的参数
     * @param systemArgsEnum
     * @return
     */
    public Long getSystemArgsForLong(SystemArgsEnum systemArgsEnum){
        String value = this.getSystemArgs(systemArgsEnum);
        if(StringUtils.isBlank(value)){
            return null;
        }
        return Long.parseLong(value);
    }


    /**
     * 获取Long类型的参数
     * @param systemArgsEnum
     * @return
     */
    public Long getSystemArgsForLong(SystemArgsEnum systemArgsEnum,Long defaultValue){
        Long result = getSystemArgsForLong(systemArgsEnum);
        return result == null?defaultValue:result;
    }







}
