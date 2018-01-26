package com.ruizton.main.service.admin;

import com.ruizton.main.dao.zuohao.FbannerDao;
import com.ruizton.main.log.LOG;
import com.ruizton.main.model.Fbanner;
import com.ruizton.main.model.Fvideo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by luwei on 17-3-7.
 */
@Service
public class FbannerService {

    @Autowired
    private FbannerDao fbannerDao;


    public List<Fbanner> list(int firstResult, int maxResults,
                              String filter, boolean isFY) {
        List<Fbanner> list = this.fbannerDao.list(firstResult, maxResults, filter, isFY);
        if(list != null && list.size() > 0) {
            for(Fbanner fbanner : list) {
                fbanner.getFcreateUser().getFname();
            }
        }
        return list;
    }


    public Fbanner findById(int id) {
        return this.fbannerDao.findById(id);
    }


    public void save(Fbanner transientInstance) {
        this.fbannerDao.save(transientInstance);
    }

    public void update(Fbanner fbanner) {
        this.fbannerDao.attachDirty(fbanner);
    }

    public List findByProperty(String propertyName, Object value) {
        return this.fbannerDao.findByProperty(propertyName, value);
    }

    public void updateStatus(Integer fid, Integer status) throws Exception{
        if(fid == null) {
            LOG.e(FbannerService.class, "修改banner状态错误，参数fid不能为空");
            return;
        }

        if(status == null) {
            LOG.e(FbannerService.class, "修改banner状态错误，参数status不能为空");
            return;
        }

        Fbanner fbanner = this.fbannerDao.findById(fid);
        if(fbanner == null) {
            LOG.e(FbannerService.class, "根据fid查询banner信息为空");
            return;
        }

        fbanner.setFstatus(status);
        this.fbannerDao.attachDirty(fbanner);

    }

    /**
     * 获取最新的一个
     * @return
     */
    public Fbanner getLastBanner(int fseat){
        return this.fbannerDao.getLastBanner(fseat);
    }

    public Fbanner getPreviousBanner(int priority, int fseat) {
        return this.fbannerDao.getPreviousBanner(priority, fseat);
    }

    public Fbanner getNextBanner(int priority, int fseat) {
        return this.fbannerDao.getNextBanner(priority, fseat);
    }


}
