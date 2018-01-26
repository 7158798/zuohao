package com.ruizton.main.service.front;

import com.ruizton.main.dao.zuohao.FquotesDao;
import com.ruizton.main.model.OtherPlatform.Fquotes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zygong on 17-2-28.
 */
@Service
public class FquotesService {
    
    @Autowired
    private FquotesDao fquotesDao;

    public void saveList(List<Fquotes> list) {
        // 判断list是否有数据
        if (list != null && list.size() > 0) {
            this.fquotesDao.deleteAll();
            this.fquotesDao.saveList(list);
        }
    }

    public List<Fquotes> list(int firstResult, int maxResults,
                              String filter, boolean isFY) {
        return this.fquotesDao.list(firstResult, maxResults, filter,isFY);
    }

    public List<Fquotes> findAll() {
        return this.fquotesDao.findAll();
    }

    public List<Fquotes> findLast(int typeId){
        return this.fquotesDao.findLast(typeId);
    };
}
