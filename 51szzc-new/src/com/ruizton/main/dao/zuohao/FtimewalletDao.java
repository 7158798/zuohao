package com.ruizton.main.dao.zuohao;

import com.ruizton.main.dao.comm.HibernateDaoSupport;
import com.ruizton.main.log.LOG;
import com.ruizton.main.model.Ftimewallet;
import org.apache.commons.collections.map.HashedMap;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by luwei on 17-3-6.
 */
@Repository
public class FtimewalletDao extends HibernateDaoSupport {


    private static final Logger log = LoggerFactory.getLogger(FtimewalletDao.class);



    public List<Ftimewallet> list(int firstResult, int maxResults,
                               String filter, boolean isFY) {
        List<Ftimewallet> list = null;
        log.debug("finding Ftimewallet instance with filter");
        try {
            String queryString = "from Ftimewallet "+filter;
            Query queryObject = getSession().createQuery(queryString);
            if(isFY){
                queryObject.setFirstResult(firstResult);
                queryObject.setMaxResults(maxResults);
            }
            list = queryObject.list();
        } catch (RuntimeException re) {
            log.error("find Ftimewallet by filter name failed", re);
            throw re;
        }
        return list;
    }


    public Ftimewallet findById(int id) {
        log.debug("getting Ftimewallet instance with id: " + id);
        try {
            Ftimewallet instance = (Ftimewallet) getSession()
                    .get("com.ruizton.main.model.Ftimewallet", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }


    public void save(Ftimewallet transientInstance) {
        log.debug("saving Ftimewallet instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void attachDirty(Ftimewallet instance) {
        log.debug("attaching dirty Ftimewallet instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding Ftimewallet instance with property: " + propertyName
                + ", value: " + value);
        try {
            String queryString = "from Ftimewallet as model where model."
                    + propertyName + "= ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    /**
     * 求用户，指定时间的持币数量、市价
     * @param userId
     * @param time
     * @param fvi_fid
     * @return
     * 修改：精确到H时，比较，不精确到分、秒
     */
    public Map<Integer, Ftimewallet> queryByUserAndTime(Integer userId, String time, String fvi_fid) {
        Map<Integer, Ftimewallet> map = new HashedMap();
        try {

            StringBuffer sb = new StringBuffer();
            sb.append(" select fid, fvi_fid,ftotal,fprice from ftimewallet a ");
            sb.append(" where fuid = " + userId + " and date_format(a.fCreateTime, '%Y-%m-%d %H') = date_format('" + time + "', '%Y-%m-%d %H') ");
            sb.append(" and a.fvi_fid in ( " + fvi_fid + ")");
            Query queryObject = getSession().createSQLQuery(sb.toString());
            List allList = queryObject.list();
            if(allList == null || allList.size() == 0) {
                return map;
            }

            for (int i = 0; i < allList.size(); i++) {
                Object[] o = (Object[]) allList.get(i);
                Ftimewallet vo = new Ftimewallet();
                vo.setFid(Integer.valueOf(o[0].toString()));
                vo.setFtotal(new BigDecimal(o[2].toString()));
                vo.setFprice(new BigDecimal(o[3].toString()));
                map.put(Integer.valueOf(o[1].toString()), vo);   //注：key 是币种类型id
            }
        }catch (RuntimeException re) {
            log.error("find by queryByUserAndTime name failed", re);
            throw re;
        }

        return map;
    }

    /**
     * 判断当天是否有数据
     * @return
     */
    public boolean queryExistsData() {
        boolean flag = false;
        StringBuffer sb = new StringBuffer();
        sb.append(" select * from ftimewallet a ");
        sb.append(" where  date_format(a.fCreateTime, '%Y-%m-%d') = date_format(now(), '%Y-%m-%d') ");
        Query queryObject = getSession().createSQLQuery(sb.toString());
        List allList = queryObject.list();
        if( allList !=  null && allList.size() > 0) {
            flag = true;
        }
        return flag;
    }


    /**
     * 删除
     * @param persistentInstance
     */
    public void delete(Ftimewallet persistentInstance) {
        LOG.d(this, "deleting Ftimewallet instance");
        try {
            getSession().delete(persistentInstance);
            LOG.d(this, "delete successful");
        } catch (RuntimeException re) {
            LOG.e(this, "delete failed", re);
            throw re;
        }
    }

    /**
     * 删除当天的数据
     */
    public void deleteTodayData() {
        String sql  = "  delete from ftimewallet where  date_format(fCreateTime, '%Y-%m-%d') = date_format(now(), '%Y-%m-%d') ";
        Query queryObject = getSession().createSQLQuery(sql);
        queryObject.executeUpdate();
    }
}
