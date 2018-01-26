package com.ruizton.main.service.admin;

import com.ruizton.main.dao.integral.FusergradeDAO;
import com.ruizton.main.model.integral.Fusergrade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by fenggq on 17-2-22.
 */
@Service
public class UserGradeService {
    @Autowired
    private FusergradeDAO fusergradeDAO;

    public Fusergrade findById(int id) {
        return this.fusergradeDAO.findById(id);
    }

    public void saveObj(Fusergrade obj) {
        this.fusergradeDAO.save(obj);
    }

    public void deleteObj(int id) {
        Fusergrade obj = this.fusergradeDAO.findById(id);
        this.fusergradeDAO.delete(obj);
    }

    public void updateObj(Fusergrade obj) {
        this.fusergradeDAO.attachDirty(obj);
    }

    public List<Fusergrade> findByProperty(String name, Object value) {
        return this.fusergradeDAO.findByProperty(name, value);
    }

    public List<Fusergrade> findAll() {
        return this.fusergradeDAO.findAll();
    }

    public List<Fusergrade> list(int firstResult, int maxResults,
                             String filter,boolean isFY) {
        return this.fusergradeDAO.list(firstResult, maxResults, filter,isFY);
    }
}
