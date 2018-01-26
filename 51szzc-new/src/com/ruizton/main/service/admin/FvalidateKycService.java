package com.ruizton.main.service.admin;

import com.ruizton.main.dao.zuohao.FvalidatekycDAO;
import com.ruizton.main.model.Fvalidatekyc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by fenggq on 17-2-22.
 */
@Service
public class FvalidateKycService {
    @Autowired
    private FvalidatekycDAO FvalidatekycDAO;

    public Fvalidatekyc findById(int id) {
        return this.FvalidatekycDAO.findById(id);
    }

    public void saveObj(Fvalidatekyc obj) {
        this.FvalidatekycDAO.save(obj);
    }

    public void deleteObj(int id) {
        Fvalidatekyc obj = this.FvalidatekycDAO.findById(id);
        this.FvalidatekycDAO.delete(obj);
    }

    public void updateObj(Fvalidatekyc obj) {
        this.FvalidatekycDAO.attachDirty(obj);
    }

    public List<Fvalidatekyc> findByProperty(String name, Object value) {
        return this.FvalidatekycDAO.findByProperty(name, value);
    }

    public List<Fvalidatekyc> findAll() {
        return this.FvalidatekycDAO.findAll();
    }

    public List<Fvalidatekyc> list(int firstResult, int maxResults,
                             String filter,boolean isFY) {
        List<Fvalidatekyc> list = this.FvalidatekycDAO.list(firstResult, maxResults, filter,isFY);

        for(Fvalidatekyc fvalidatekyc:list){
            fvalidatekyc.getFuser().getFloginName();
            fvalidatekyc.getFuser().getFnickName();
            fvalidatekyc.getFuser().getFrealName();
            fvalidatekyc.getFuser().getFrealName();
            fvalidatekyc.getFuser().getFidentityType_s();
            fvalidatekyc.getFuser().getFidentityNo();
            fvalidatekyc.getFuser().getFregisterTime();
        }
        return list;
    }

    public Fvalidatekyc findByUserId(int userId){
        return this.FvalidatekycDAO.findFvalidatekycByUserId(userId);
    }
}
