package com.ruizton.main.service.admin;

import com.ruizton.main.dao.zuohao.AutoOrderDao;
import com.ruizton.main.model.AutoOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lx on 17-1-24.
 */
@Service
public class AutoOrderService {

    @Autowired
    private AutoOrderDao autoOrderDao;

    public AutoOrder findById(int fid) {
        return this.autoOrderDao.findById(fid);
    }

    public AutoOrder findByIdAll(int fid) {
        AutoOrder autoOrder = this.autoOrderDao.findById(fid);
        autoOrder.getFtrademapping().getFvirtualcointypeByFvirtualcointype2().getFname();
        autoOrder.getUser().getFloginName();
        autoOrder.getReserveUser().getFloginName();
        return autoOrder;
    }


    public List<AutoOrder> findAll() {
        return this.autoOrderDao.findAll();
    }

    public List<AutoOrder> list(int firstResult, int maxResults,
                                       String filter,boolean isFY) {
        List<AutoOrder> all = this.autoOrderDao.list(firstResult, maxResults, filter,isFY);
        for (AutoOrder autoOrder : all){
            autoOrder.getFtrademapping().getFvirtualcointypeByFvirtualcointype2().getFname();
            autoOrder.getUser().getFloginName();
            autoOrder.getReserveUser().getFloginName();
        }
        return all;
    }

    public void saveObj(AutoOrder obj) {
        this.autoOrderDao.save(obj);
    }

    public void updateObj(AutoOrder obj) {
        this.autoOrderDao.attachDirty(obj);
    }

    public boolean isCoinTypeExists(int coinTypeId) throws Exception{
        boolean flag = false ;
        List<AutoOrder> list = this.autoOrderDao.findByProperty("ftrademapping.fvirtualcointypeByFvirtualcointype2.fid", coinTypeId) ;
        flag = list.size()>0 ;
        return flag ;
    }

    public List<AutoOrder> findByProperty(String name, Object value) {
        return this.autoOrderDao.findByProperty(name, value);
    }
}
