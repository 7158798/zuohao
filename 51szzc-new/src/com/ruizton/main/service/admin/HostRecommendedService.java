package com.ruizton.main.service.admin;

import com.ruizton.main.dao.zuohao.FhostRecommendedDAO;
import com.ruizton.main.dao.zuohao.FvideotypeDao;
import com.ruizton.main.model.FhostRecommended;
import com.ruizton.main.model.Fvideotype;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zygong on 17-2-28.
 */
@Service
public class HostRecommendedService {
    
    @Autowired
    private FhostRecommendedDAO fhostRecommendedDAO;

    public void save(FhostRecommended obj) {
        this.fhostRecommendedDAO.save(obj);
    }

    public void updateObj(FhostRecommended obj) {
        this.fhostRecommendedDAO.attachDirty(obj);
    }

    public FhostRecommended findById(int id) {
        return this.fhostRecommendedDAO.findById(id);
    }

    public List<FhostRecommended> findAll() {
        return this.fhostRecommendedDAO.findAll();
    }
}
