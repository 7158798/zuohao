package com.ruizton.main.service.admin;

import com.ruizton.main.Enum.AuditProcessEnum;
import com.ruizton.main.dao.zuohao.FauditprocessDAO;
import com.ruizton.main.model.Fauditprocess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by fenggq on 17-2-22.
 */
@Service
public class AuditProcessService {
    @Autowired
    private FauditprocessDAO FauditprocessDAO;

    public Fauditprocess findById(int id) {
        return this.FauditprocessDAO.findById(id);
    }

    public void saveObj(Fauditprocess obj) {
        this.FauditprocessDAO.save(obj);
    }

    public void deleteObj(int id) {
        Fauditprocess obj = this.FauditprocessDAO.findById(id);
        this.FauditprocessDAO.delete(obj);
    }

    public void updateObj(Fauditprocess obj) {
        this.FauditprocessDAO.attachDirty(obj);
    }

    public List<Fauditprocess> findByProperty(String name, Object value) {
        return this.FauditprocessDAO.findByProperty(name, value);
    }

    public List<Fauditprocess> findAll() {
        return this.FauditprocessDAO.findAll();
    }

    public List<Fauditprocess> list(int firstResult, int maxResults,
                             String filter,boolean isFY) {
        List<Fauditprocess> list =  this.FauditprocessDAO.list(firstResult, maxResults, filter,isFY);
        return list;
    }

    public Fauditprocess findByftype(AuditProcessEnum auditProcessEnum){
        List<Fauditprocess> list = this.findByProperty("ftype",auditProcessEnum.getCode());
        if(list.size()>0){
            return list.get(0);
        }
        return null;
    }
}
