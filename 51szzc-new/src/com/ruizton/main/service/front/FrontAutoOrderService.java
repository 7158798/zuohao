package com.ruizton.main.service.front;

import com.ruizton.main.dao.zuohao.AutoOrderDao;
import com.ruizton.main.model.AutoOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by lx on 17-1-24.
 */
@Service
public class FrontAutoOrderService {

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
}
