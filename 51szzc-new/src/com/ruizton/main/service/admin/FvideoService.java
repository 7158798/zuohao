package com.ruizton.main.service.admin;

import com.ruizton.main.dao.zuohao.FvideoDao;
import com.ruizton.main.dao.zuohao.FvideotypeDao;
import com.ruizton.main.model.Fvideo;
import com.ruizton.main.model.Fvideotype;
import com.ruizton.main.model.vo.FvideoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by luwei on 17-2-28.
 */
@Service
public class FvideoService {

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:sss");

    @Autowired
    private FvideoDao fvideoDao;

    @Autowired
    private FvideotypeDao fvideotypeDao;

    /**
     * 查询所有的视频类别
     * @return
     */
    public List<Fvideotype> findAllVideoType() {
        return fvideotypeDao.findAll();
    }

    /**
     * 根据id查询视频类别
     * @param ftypeid
     * @return
     */
    public Fvideotype findVideoTypeById(int ftypeid) {
        return fvideotypeDao.findById(ftypeid);
    }

    public List<Fvideo> list(int firstResult, int maxResults,
                             String filter, boolean isFY) {

        List<Fvideo> list = this.fvideoDao.list(firstResult, maxResults, filter, isFY);
        if (list != null && list.size() > 0) {
            for (Fvideo fvideo : list) {
                fvideo.getFvideotype().getfName();
                fvideo.getfUpdateUser().getFname();
            }
        }

        return list;
    }

    public List<FvideoVo> listAllSql(String filter) {

        List<FvideoVo> list = this.fvideoDao.listAllSql(filter);

        return list;
    }

    public Fvideo findById(int id) {
        Fvideo fvideo = this.fvideoDao.findById(id);
        if(fvideo != null) {
            fvideo.getFvideotype().getfName();
            fvideo.getfUpdateUser().getFname();
        }
        return fvideo;
    }

    public void save(Fvideo transientInstance) {
        fvideoDao.save(transientInstance);
    }

    public Fvideo getLastVideo(){
        return this.fvideoDao.getLastVideo();
    }

    public void update(Fvideo instance) {
        fvideoDao.attachDirty(instance);
    }

    public List findByProperty(String propertyName, Object value) {
        return this.fvideoDao.findByProperty(propertyName, value);
    }

    public Fvideo getPreviousVideo(int priority){
        return this.fvideoDao.getPreviousVideo(priority);
    }

    public Fvideo getNextVideo(int priority){
        return this.fvideoDao.getNextVideo(priority);
    }


    /**
     * 删除
     * @param persistentInstance
     */
    public void delete(Fvideo persistentInstance) {
        fvideoDao.delete(persistentInstance);
    }
}
