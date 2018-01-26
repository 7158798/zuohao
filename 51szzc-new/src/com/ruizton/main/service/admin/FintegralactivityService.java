package com.ruizton.main.service.admin;

import com.ruizton.main.dao.integral.FintegralactivityDAO;
import com.ruizton.main.dao.integral.FintegralactivityDAO;
import com.ruizton.main.model.integral.Fintegralactivity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by fenggq on 17-2-22.
 */
@Service
public class FintegralactivityService {
    @Autowired
    private FintegralactivityDAO fintegralactivityDAO;

    public Fintegralactivity findById(int id) {
        return this.fintegralactivityDAO.findById(id);
    }

    public void saveObj(Fintegralactivity obj) {
        this.fintegralactivityDAO.save(obj);
    }

    public void deleteObj(int id) {
        Fintegralactivity obj = this.fintegralactivityDAO.findById(id);
        this.fintegralactivityDAO.delete(obj);
    }

    public void updateObj(Fintegralactivity obj) {
        this.fintegralactivityDAO.attachDirty(obj);
    }

    public List<Fintegralactivity> findByProperty(String name, Object value) {
        return this.fintegralactivityDAO.findByProperty(name, value);
    }

    public List<Fintegralactivity> findAll() {
        return this.fintegralactivityDAO.findAll();
    }

    public List<Fintegralactivity> list(int firstResult, int maxResults,
                             String filter,boolean isFY) {
        return this.fintegralactivityDAO.list(firstResult, maxResults, filter,isFY);
    }

    public List<Fintegralactivity> findOpenActivity(){
        return this.fintegralactivityDAO.findOpenActivityList();
    }
}
