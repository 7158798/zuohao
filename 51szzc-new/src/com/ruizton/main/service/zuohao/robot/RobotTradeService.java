package com.ruizton.main.service.zuohao.robot;

import com.ruizton.main.dao.zuohao.RobotTradeDao;
import com.ruizton.main.model.robot.RobotTrade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lx on 17-3-20.
 */
@Service
public class RobotTradeService {

    @Autowired
    private RobotTradeDao robotTradeDao;

    public List<RobotTrade> list(int firstResult, int maxResults,
                                 String filter, boolean isFY) {
        List<RobotTrade> list = this.robotTradeDao.list(firstResult, maxResults, filter,isFY);
        for (RobotTrade robotTrade : list){
            robotTrade.getFuser().getFloginName();
            robotTrade.getFtrademapping().getFvirtualcointypeByFvirtualcointype2().getFname();
        }
        return list;
    }

    public List<RobotTrade> list(String filter) {
        List<RobotTrade> list = this.robotTradeDao.list(0, 0, filter,Boolean.FALSE);
        for (RobotTrade robotTrade : list){
            robotTrade.getFuser().getFid();
            robotTrade.getFuser().getFloginName();
        }
        return list;
    }

    public RobotTrade findById(int fid) {
        RobotTrade robotTrade = this.robotTradeDao.findById(fid);
        robotTrade.getFuser().getFloginName();
        robotTrade.getFtrademapping().getFvirtualcointypeByFvirtualcointype2().getFname();
        return robotTrade;
    }

    public void save(RobotTrade transientInstance) {
        this.robotTradeDao.save(transientInstance);
    }

    public void update(RobotTrade instance) {
        this.robotTradeDao.attachDirty(instance);
    }

    public List<RobotTrade> findByProperty(String name, Object value) {
        return this.robotTradeDao.findByProperty(name, value);
    }
}
