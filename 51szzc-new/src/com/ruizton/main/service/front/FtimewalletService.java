package com.ruizton.main.service.front;

import com.ruizton.main.dao.zuohao.FtimewalletDao;
import com.ruizton.main.model.Ftimewallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by luwei on 17-3-6.
 */
@Service
public class FtimewalletService {

    @Autowired
    private FtimewalletDao ftimewalletDao;



    public void save(Ftimewallet transientInstance) {
        this.ftimewalletDao.save(transientInstance);
    }


    /**
     * 求用户，指定时间的持币数量、市价
     * @param userId
     * @param time
     * @param fvi_fid
     * @return
     */
    public Map<Integer, Ftimewallet> queryByUserAndTime(Integer userId, String time, String fvi_fid) {
        return this.ftimewalletDao.queryByUserAndTime(userId, time, fvi_fid);
    }

    /**
     * 判断当天是否有数据
     * @return
     */
    public boolean queryExistsData() {
        return this.ftimewalletDao.queryExistsData();
    }

    /**
     * 删除
     * @param persistentInstance
     */
    public void delete(Ftimewallet persistentInstance) {
        this.ftimewalletDao.delete(persistentInstance);
    }

    /**
     * 删除当天的数据
     */
    public void deleteTodayData() {
        this.ftimewalletDao.deleteTodayData();
    }

}
