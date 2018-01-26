package com.ruizton.main.service.admin;

import com.ruizton.main.dao.integral.FuserintegraldetailDAO;
import com.ruizton.main.dao.integral.FuserintegraldetailDAO;
import com.ruizton.main.model.integral.Fuserintegraldetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by fenggq on 17-2-22.
 */
@Service
public class UserintegraldetailService {
    @Autowired
    private FuserintegraldetailDAO fuserintegraldetailDAO;

    public Fuserintegraldetail findById(int id) {
        return this.fuserintegraldetailDAO.findById(id);
    }

    public void saveObj(Fuserintegraldetail obj) {
        this.fuserintegraldetailDAO.save(obj);
    }

    public void deleteObj(int id) {
        Fuserintegraldetail obj = this.fuserintegraldetailDAO.findById(id);
        this.fuserintegraldetailDAO.delete(obj);
    }

    public void updateObj(Fuserintegraldetail obj) {
        this.fuserintegraldetailDAO.attachDirty(obj);
    }

    public List<Fuserintegraldetail> findByProperty(String name, Object value) {
        return this.fuserintegraldetailDAO.findByProperty(name, value);
    }

    public List<Fuserintegraldetail> findAll() {
        return this.fuserintegraldetailDAO.findAll();
    }

    public List<Fuserintegraldetail> list(int firstResult, int maxResults,
                             String filter,boolean isFY) {
        return this.fuserintegraldetailDAO.list(firstResult, maxResults, filter,isFY);
    }
}
