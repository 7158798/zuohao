package com.ruizton.main.dao.zuohao;

import com.ruizton.main.dao.comm.HibernateDaoSupport;
import com.ruizton.main.log.LOG;
import com.ruizton.main.model.Fvideo;
import com.ruizton.main.model.Fvideotype;
import com.ruizton.main.model.vo.FvideoVo;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 视频文件上传
 * Created by luwei on 17-2-28.
 */
@Repository
public class FvideoDao extends HibernateDaoSupport {

    /**
     * 多条件查询
     * @param firstResult  起始行
     * @param maxResults  最大返回行数
     * @param filter  sql条件
     * @param isFY  是否分页
     * @return
     */
    public List<Fvideo> list(int firstResult, int maxResults,
                             String filter, boolean isFY) {
        List<Fvideo> list = null;
        LOG.d(this, "finding Fvideo instance with filter");
        try {
            String queryString = "from Fvideo "+filter;
            Query queryObject = getSession().createQuery(queryString);
            if(isFY){
                queryObject.setFirstResult(firstResult);
                queryObject.setMaxResults(maxResults);
            }
            list = queryObject.list();
        } catch (RuntimeException re) {
            LOG.e(this, "find Fvideo by filter name failed", re);
            throw re;
        }
        return list;
    }

    /**
     * 多条件查询
     * @return
     */
    public List<FvideoVo> listAllSql(String filter) {
        List<FvideoVo> list = null;
        FvideoVo vo;
        LOG.d(this, "finding Fvideo instance with filter");
        try {
            String sql = " select fid, fTypeId, fTitle, fDescription, fPictureUrl, fVideoUrl, fpriority from fvideo ";
            sql += filter;
            Query queryObject = getSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

            List listMap = queryObject.list();
            if(null != listMap && listMap.size() > 0){
                list = new ArrayList<FvideoVo>();
                for(Object obj : listMap){
                    vo = new FvideoVo();
                    Map map = (Map) obj;
                    vo.setFid((Integer) map.get("fid"));
                    vo.setfTypeId((Integer) map.get("fTypeId"));
                    vo.setfTitle((String) map.get("fTitle"));
                    vo.setfDescription((String) map.get("fDescription"));
                    vo.setfPictureUrl((String) map.get("fPictureUrl"));
                    vo.setfVideoUrl((String) map.get("fVideoUrl"));
                    vo.setfPriority((Integer) map.get("fpriority"));
                    list.add(vo);
                }
            }
        } catch (RuntimeException re) {
            LOG.e(this, "find Fvideo by filter name failed", re);
            throw re;
        }
        return list;
    }


    /**
     * 根据主键id查询
     * @param id
     * @return
     */
    public Fvideo findById(int id) {
        LOG.d(this, "getting Fvideo instance with id: " + id);
        try {
            Fvideo instance = (Fvideo) getSession()
                    .get("com.ruizton.main.model.Fvideo", id);
            return instance;
        } catch (RuntimeException re) {
            LOG.e(this, "get failed", re);
            throw re;
        }
    }


    /**
     * 保存
     * @param transientInstance
     */
    public void save(Fvideo transientInstance) {
        LOG.d(this, "saving Fvideo instance");
        try {
            getSession().save(transientInstance);
            LOG.d(this, "save successful");
        } catch (RuntimeException re) {
            LOG.e(this, "save failed", re);
            throw re;
        }
    }

    /**
     * 保存或修改
     * @param instance
     */
    public void attachDirty(Fvideo instance) {
        LOG.d(this, "attaching dirty Fvideo instance");
        try {
            getSession().saveOrUpdate(instance);
            LOG.d(this, "attach successful");
        } catch (RuntimeException re) {
            LOG.e(this, "attach failed", re);
            throw re;
        }
    }

    /**
     * 根据属性查询
     * @param propertyName 属性名
     * @param value 属性值
     * @return
     */
    public List findByProperty(String propertyName, Object value) {
        LOG.d(this, "finding Fvideo instance with property: " + propertyName
                + ", value: " + value);
        try {
            String queryString = "from Fvideo as model where model."
                    + propertyName + "= ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            return queryObject.list();
        } catch (RuntimeException re) {
            LOG.e(this, "find by property name failed", re);
            throw re;
        }
    }

    /**
     * 获取最新的一个视频
     * @return
     */
    public Fvideo getLastVideo(){
        LOG.d(this, "finding Fvideo last video");
        try {
            String queryString = "from Fvideo as model order by model.fPriority desc";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setMaxResults(1);
            return (Fvideo)queryObject.uniqueResult();
        } catch (RuntimeException re) {
            LOG.e(this, "find by property name failed", re);
            throw re;
        }
    }

    /**
     *
     * @param priority
     * @return
     */
    public Fvideo getPreviousVideo(int priority) {
        LOG.d(this, "finding Fvideo getPreviousVideo");
        try {
            String queryString = "from Fvideo as model where model.fPriority > ? order by model.fPriority asc";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, priority);
            queryObject.setMaxResults(1);
            return (Fvideo)queryObject.uniqueResult();
        } catch (RuntimeException re) {
            LOG.e(this, "find getPreviousVideo failed", re);
            throw re;
        }
    }

    /**
     *
     * @param priority
     * @return
     */
    public Fvideo getNextVideo(int priority) {
        LOG.d(this, "finding Fvideo getPreviousVideo");
        try {
            String queryString = "from Fvideo as model where model.fPriority < ? order by model.fPriority desc";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, priority);
            queryObject.setMaxResults(1);
            return (Fvideo)queryObject.uniqueResult();
        } catch (RuntimeException re) {
            LOG.e(this, "find getPreviousVideo failed", re);
            throw re;
        }
    }


    /**
     * 删除
     * @param persistentInstance
     */
    public void delete(Fvideo persistentInstance) {
        LOG.d(this, "deleting Fvideotype instance");
        try {
            getSession().delete(persistentInstance);
            LOG.d(this, "delete successful");
        } catch (RuntimeException re) {
            LOG.e(this, "delete failed", re);
            throw re;
        }
    }
}
