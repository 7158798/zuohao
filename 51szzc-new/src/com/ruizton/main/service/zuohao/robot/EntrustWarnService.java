package com.ruizton.main.service.zuohao.robot;

import com.ruizton.main.Enum.robot.RobotStatusEnum;
import com.ruizton.main.dao.zuohao.EntrustWarnDao;
import com.ruizton.main.model.Ftrademapping;
import com.ruizton.main.model.robot.EntrustWarn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lx on 17-3-20.
 */
@Service
public class EntrustWarnService {

    @Autowired
    private EntrustWarnDao entrustWarnDao;

    public List<EntrustWarn> list(int firstResult, int maxResults,
                                 String filter, boolean isFY) {
        List<EntrustWarn> list = this.entrustWarnDao.list(firstResult, maxResults, filter,isFY);
        for (EntrustWarn entrustWarn : list){
            entrustWarn.getFtrademapping().getFvirtualcointypeByFvirtualcointype2().getFname();
        }
        return list;
    }

    public List<EntrustWarn> list(String filter) {
        List<EntrustWarn> list = this.entrustWarnDao.list(0, 0, filter,Boolean.FALSE);
        for (EntrustWarn entrustWarn : list){
            entrustWarn.getFtrademapping().getFvirtualcointypeByFvirtualcointype2().getFid();
            entrustWarn.getFtrademapping().getFvirtualcointypeByFvirtualcointype2().getFname();
        }
        return list;
    }

    public EntrustWarn findById(int fid) {
        EntrustWarn EntrustWarn = this.entrustWarnDao.findById(fid);
        EntrustWarn.getFtrademapping().getFvirtualcointypeByFvirtualcointype2().getFname();
        EntrustWarn.getFtrademapping().getFvirtualcointypeByFvirtualcointype2().getFid();
        return EntrustWarn;
    }

    public void save(EntrustWarn transientInstance) {
        this.entrustWarnDao.save(transientInstance);
    }

    public void update(EntrustWarn instance) {
        this.entrustWarnDao.attachDirty(instance);
    }

    public List<EntrustWarn> findByProperty(String name, Object value) {
        return this.entrustWarnDao.findByProperty(name, value);
    }

    public Boolean isExist(Ftrademapping ftrademapping,String type){
        StringBuilder filter = new StringBuilder();
        filter.append("where 1=1 \n");
        filter.append("and type = " + type + " ");
        filter.append("and ftrademapping.fid=" + ftrademapping.getFid() + " ");
        List<EntrustWarn> list = list(filter.toString());
        Boolean flag = Boolean.FALSE;
        if (list != null && list.size() != 0){
            flag = Boolean.TRUE;
        }
        return flag;
    }
}
