package com.ruizton.main.service.admin;

import com.ruizton.main.dao.zuohao.FvideotypeDao;
import com.ruizton.main.model.Fvideotype;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zygong on 17-2-28.
 */
@Service
public class VideoTypeService {
    
    @Autowired
    private FvideotypeDao fvideotypeDao;

    public void save(Fvideotype obj) {
        this.fvideotypeDao.save(obj);
    }

    public void deleteObj(int id) {
        Fvideotype obj = this.fvideotypeDao.findById(id);
        this.fvideotypeDao.delete(obj);
    }

    public void updateObj(Fvideotype obj) {
        this.fvideotypeDao.attachDirty(obj);
    }

    public Fvideotype findById(int id) {
        return this.fvideotypeDao.findById(id);
    }

    public List<Fvideotype> list(int firstResult, int maxResults,
                                   String filter, boolean isFY) {
        return this.fvideotypeDao.list(firstResult, maxResults, filter,isFY);
    }

    public List<Fvideotype> findAll() {
        return this.fvideotypeDao.findAll();
    }
}
