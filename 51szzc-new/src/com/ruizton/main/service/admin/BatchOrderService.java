package com.ruizton.main.service.admin;

import com.ruizton.main.dao.zuohao.BatchOrderDao;
import com.ruizton.main.model.BatchOrder;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lx on 17-3-7.
 */
@Service
public class BatchOrderService {

    @Autowired
    private BatchOrderDao batchOrderDao;

    public List<BatchOrder> list(int firstResult, int maxResults,
                               String filter, boolean isFY) {
        List<BatchOrder> list = this.batchOrderDao.list(firstResult, maxResults, filter,isFY);
        for (BatchOrder batchOrder : list){
            batchOrder.getFtrademapping().getFvirtualcointypeByFvirtualcointype2().getFname();
            batchOrder.getUser().getFloginName();
        }
        return list;
    }

    public BatchOrder findById(int fid) {
        BatchOrder batchOrder = this.batchOrderDao.findById(fid);
        batchOrder.getFtrademapping().getFvirtualcointypeByFvirtualcointype2().getFname();
        batchOrder.getUser().getFloginName();
        batchOrder.getUser().getFid();
        return batchOrder;
    }

    public void save(BatchOrder transientInstance) {
        this.batchOrderDao.save(transientInstance);
    }

    public void update(BatchOrder instance) {
        this.batchOrderDao.attachDirty(instance);
    }


    public boolean isCoinTypeExists(int coinTypeId) throws Exception{
        boolean flag = false ;
        List<BatchOrder> list = this.batchOrderDao.findByProperty("ftrademapping.fid", coinTypeId) ;
        flag = list.size()>0 ;
        return flag ;
    }

    public List<BatchOrder> findByProperty(String name, Object value) {
        return this.batchOrderDao.findByProperty(name, value);
    }

    /**
     * 更新订单状态
     * @param userId
     * @param status
     */
    public void updateStatus(int userId,String status) {
        this.batchOrderDao.updateStatus(userId,status);
    }
}
