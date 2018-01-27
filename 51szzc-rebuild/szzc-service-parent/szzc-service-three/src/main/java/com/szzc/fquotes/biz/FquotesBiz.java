package com.szzc.fquotes.biz;

import com.szzc.facade.fquotes.pojo.po.Fquotes;
import com.szzc.fquotes.dao.FquotesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zygong on 17-5-27.
 */
@Service
public class FquotesBiz {

    @Autowired
    private FquotesMapper fquotesMapper;

    /**
     * 批量保存
     * @param list
     * @return
     */
    public boolean saveList(List<Fquotes> list){
        int result = 0;
        boolean isSuc = false;
        result = fquotesMapper.saveList(list);
        if(result > 0){
            isSuc = true;
        }
        return isSuc;
    }
}
