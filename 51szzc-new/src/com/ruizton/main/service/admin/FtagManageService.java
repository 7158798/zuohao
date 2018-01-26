package com.ruizton.main.service.admin;

import com.ruizton.main.dao.zuohao.FtagmanageDao;
import com.ruizton.main.model.Ftagmanage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zygong on 17-2-28.
 */
@Service
public class FtagManageService {
    
    @Autowired
    private FtagmanageDao ftagManageDAO;

    public void save(Ftagmanage obj) {
        this.ftagManageDAO.save(obj);
    }

    public void deleteAll(){
        this.ftagManageDAO.deleteAll();
    };

    public void updateObj(Ftagmanage obj) {
        this.ftagManageDAO.attachDirty(obj);
    }

    public Ftagmanage findById(int id) {
        return this.ftagManageDAO.findById(id);
    }

    public List<Ftagmanage> findAll() {
        return this.ftagManageDAO.findAll();
    }
}
