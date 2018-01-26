package com.ruizton.main.service.front;

import com.ruizton.main.dao.zuohao.FvalidatekycDAO;
import com.ruizton.main.model.Fvalidatekyc;
import com.ruizton.main.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by fenggq on 17-3-28.
 */
@Service
public class FrontValidateKycService extends BaseService{
    @Autowired
    private FvalidatekycDAO fvalidatekycDAO;

    public void save(Fvalidatekyc transientInstance){
        this.fvalidatekycDAO.save(transientInstance);
    }

    public Fvalidatekyc findByUserId(int userId){
        return this.fvalidatekycDAO.findFvalidatekycByUserId(userId);
    }
}
