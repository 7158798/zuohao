package com.ruizton.main.service.admin;

import com.ruizton.main.dao.zuohao.FtagDao;
import com.ruizton.main.model.Farticletype;
import com.ruizton.main.model.Ftag;
import com.ruizton.main.model.Ftagmanage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zygong on 17-2-28.
 */
@Service
public class FtagService {
    
    @Autowired
    private FtagDao ftagDao;

    public void saveList(List<Ftag> list){
        this.ftagDao.deleteAll();

        this.ftagDao.saveList(list);
    }

    public List<Ftag> list(int firstResult, int maxResults,
                                   String filter, boolean isFY) {
        return this.ftagDao.list(firstResult, maxResults, filter,isFY);
    }

    public List<Ftag> findAll() {
        return this.ftagDao.findAll();
    }
}
