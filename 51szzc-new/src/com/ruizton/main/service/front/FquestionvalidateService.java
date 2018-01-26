package com.ruizton.main.service.front;

import com.ruizton.main.dao.zuohao.FquestionvalidateDao;
import com.ruizton.main.model.Fquestionvalidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 安全问题验证
 * Created by luwei on 17-3-28.
 */
@Service
public class FquestionvalidateService {

    @Autowired
    private FquestionvalidateDao fquestionvalidateDao;

    /**
     * 多条件查询
     * @param firstResult 起始行
     * @param maxResults  最大返回行数
     * @param filter  sql条件
     * @param isFY  是否分页
     * @return
     */
    public List<Fquestionvalidate> list(int firstResult, int maxResults,
                                        String filter, boolean isFY) {
        return fquestionvalidateDao.list(firstResult, maxResults, filter, isFY);
    }


    /**
     * 根据主键id查询
     * @param id
     * @return
     */
    public Fquestionvalidate findById(int id) {
        return fquestionvalidateDao.findById(id);
    }


    /**
     * 根据属性查询
     * @param propertyName 属性名
     * @param value 属性值
     * @return
     */
    public List findByProperty(String propertyName, Object value) {
        return fquestionvalidateDao.findByProperty(propertyName, value);
    }

    /**
     * 判断用户是否进行过安全问题验证
     * @param userId
     * @return
     */
    public boolean isQuestionVal(Integer userId) {
        List<Fquestionvalidate> list = this.fquestionvalidateDao.findByProperty("fuser.fid", userId);
        if(list != null && list.size() > 0) {
            return Boolean.TRUE;
        }else{
            return Boolean.FALSE;
        }
    }

    /**
     * 保存
     * @param transientInstance
     */
    public void save(Fquestionvalidate transientInstance) {
        fquestionvalidateDao.save(transientInstance);
    }

    /**
     * 修改
     * @param transientInstance
     */
    public void update(Fquestionvalidate transientInstance){
        fquestionvalidateDao.attachDirty(transientInstance);
    }

    /**
     * 删除
     * @param transientInstance
     */
    public void delete(Fquestionvalidate transientInstance){
        fquestionvalidateDao.delete(transientInstance);
    }


    /**
     * 根据用户id和问题，判断是否存在
     * @param fuid
     * @param question
     * @return
     */
    public boolean exists(Integer fuid, String question){
        StringBuffer filter = new StringBuffer();
        filter.append(" where 1=1 ");
        filter.append(" and fuser.fid = " + fuid);
        filter.append(" and fquestion = '"+question+"' ");
        List list =this.fquestionvalidateDao.list(0, 0, filter.toString(), false);
        if(list != null && list.size() > 0) {
            return Boolean.TRUE;
        }else {
            return Boolean.FALSE;
        }
    }
}
