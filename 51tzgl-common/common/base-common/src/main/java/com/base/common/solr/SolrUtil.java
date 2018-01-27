package com.base.common.solr;

import com.jucaifu.common.log.LOG;
import com.jucaifu.common.property.PropertiesUtils;
import com.jucaifu.common.util.page.Page;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * solr操作
 * @author luwei
 * @Date 12/5/16 5:59 PM
 */
public class SolrUtil {

    //solr服务地址
    public static String SOLR_URL = PropertiesUtils.getProperty("solr.url", "");

    /**
     * 创建solr客户端操作对象
     * @param coreName
     * @return
     */
    private static HttpSolrClient build(String coreName){
        SOLR_URL = PropertiesUtils.getProperty("solr.url", "");
        HttpSolrClient solr = new HttpSolrClient.Builder(SOLR_URL + "/"+coreName).build();
        return solr;
    }

    public static <T> Boolean addBeanToSolr(T bean,String coreName){
        LOG.i(SolrUtil.class,"增加数据到solr中Start，coreName:" + coreName);
        LOG.d(SolrUtil.class,bean);
        boolean flag = false;
        HttpSolrClient httpSolrClient = build(coreName);
        try {
            if (bean == null){
                throw new RuntimeException("参数异常，list为空");
            }
            httpSolrClient.addBean(bean);
            // 提交数据
            httpSolrClient.commit();
        } catch (IOException e) {
            LOG.e(SolrUtil.class, e.getMessage(), e);
        } catch (SolrServerException e) {
            LOG.e(SolrUtil.class, e.getMessage(), e);
        } finally {
            try {
                httpSolrClient.close();
            }catch (Exception e) {
                LOG.e(SolrUtil.class, e.getMessage(), e);
            }
        }
        LOG.i(SolrUtil.class,"增加数据到solr中End，coreName:" + coreName);
        return flag;
    }

    public static Boolean deleteById(String id,String coreName){
        LOG.i(SolrUtil.class,"删除solr中的数据start，coreName:" + coreName);
        LOG.i(SolrUtil.class,"删除的solr主键:" + id);
        boolean flag = false;
        HttpSolrClient httpSolrClient = build(coreName);
        try {
            httpSolrClient.deleteById(id);
            // 提交数据
            httpSolrClient.commit();
        } catch (IOException e) {
            LOG.e(SolrUtil.class, e.getMessage(), e);
        } catch (SolrServerException e) {
            LOG.e(SolrUtil.class, e.getMessage(), e);
        } finally {
            try {
                httpSolrClient.close();
            }catch (Exception e) {
                LOG.e(SolrUtil.class, e.getMessage(), e);
            }
        }
        LOG.i(SolrUtil.class,"删除solr中的数据end，coreName:" + coreName);
        return flag;
    }

    public static Boolean deleteAll(String coreName){
      return  deleteAll("*:*",coreName);
    }

    public static Boolean deleteAll(String query,String coreName){
        LOG.i(SolrUtil.class,"清空solr数据Start，coreName:" + coreName);
        boolean flag = false;
        HttpSolrClient httpSolrClient = build(coreName);
        try {
            httpSolrClient.deleteByQuery(query);
            // 提交数据
            httpSolrClient.commit();
        } catch (IOException e) {
            LOG.e(SolrUtil.class, e.getMessage(), e);
        } catch (SolrServerException e) {
            LOG.e(SolrUtil.class, e.getMessage(), e);
        } finally {
            try {
                httpSolrClient.close();
            }catch (Exception e) {
                LOG.e(SolrUtil.class, e.getMessage(), e);
            }
        }
        LOG.i(SolrUtil.class,"清空solr数据End，coreName:" + coreName);
        return flag;
    }


    public static <T> Boolean addListToSolr(List<T> list, String coreName) {
        LOG.i(SolrUtil.class,"增加批量数据到solr中Start，coreName:" + coreName);
        //LOG.d(SolrUtil.class,list);
        boolean flag = false;
        HttpSolrClient httpSolrClient = build(coreName);
        try {
            if(list == null || list.size() == 0){
                throw new RuntimeException("参数异常，list为空");
            }
            //将转换后的list数据保存到solr
            httpSolrClient.addBeans(list);
            httpSolrClient.commit();
        } catch (Exception e) {
            LOG.e(SolrUtil.class, e.getMessage(), e);
        } finally {
            try {
                httpSolrClient.close();
            }catch (Exception e) {
                LOG.e(SolrUtil.class, e.getMessage(), e);
            }
        }
        LOG.i(SolrUtil.class,"增加批量数据到solr中End，coreName:" + coreName);
        return flag;
    }

    public static <T> Page<T> queryPageBySolr(Page<T> page,Class<T> clz,SolrQuery solrQuery,String coreName){
        LOG.i(SolrUtil.class,"分页查询solr中数据start，coreName:" + coreName);
        HttpSolrClient httpSolrClient = build(coreName);
        List<T> rList;
        try {
            Integer start = solrQuery.getStart();
            start = (start - 1) * solrQuery.getRows();
            solrQuery.setStart(start);
            QueryResponse response = httpSolrClient.query(solrQuery);
            rList = response.getBeans(clz);
            LOG.d(SolrUtil.class,rList);
            // 总条数
            Long numFound = response.getResults().getNumFound();
            page.setTotalCount(numFound.intValue());
            page.setTotalPage(numFound.intValue() / page.getPageShow());
            page.setResult(rList);
            LOG.d(SolrUtil.class,page);
        } catch (SolrServerException e) {
            LOG.e(SolrUtil.class, e.getMessage(), e);
        } catch (IOException e) {
            LOG.e(SolrUtil.class, e.getMessage(), e);
        } finally {
            try {
                httpSolrClient.close();
            }catch (Exception e) {
                LOG.e(SolrUtil.class, e.getMessage(), e);
            }
        }
        LOG.i(SolrUtil.class,"分页查询solr中数据end，coreName:" + coreName);
        return page;
    }

    public static <T> List<T> queryListBySolr(SolrQuery solrQuery,Class<T> clz,String coreName){

        HttpSolrClient httpSolrClient = build(coreName);
        List<T> rList = new ArrayList<>();
        try {
            QueryResponse response = httpSolrClient.query(solrQuery);
            rList = response.getBeans(clz);
            response.getResults().getNumFound();
        } catch (SolrServerException e) {
            LOG.e(SolrUtil.class, e.getMessage(), e);
        } catch (IOException e) {
            LOG.e(SolrUtil.class, e.getMessage(), e);
        } finally {
            try {
                httpSolrClient.close();
            }catch (Exception e) {
                LOG.e(SolrUtil.class, e.getMessage(), e);
            }
        }
        return rList;
    }

    /**
     * 添加数据进solr
     * @param list  数据集合
     * @param coreName 核名称
     * @return
     */
    /*public static Boolean addListToSolr(List<Object> list, String coreName) {
        boolean flag = false;
        HttpSolrClient httpSolrClient = build(coreName);
        try {
            if(list == null || list.size() == 0){
                throw new RuntimeException("参数异常，list为空");
            }

            List<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
            //将list对象转换成SolrInputDocument
            for(Object o : list){
                SolrInputDocument solrInputDocument = EntityConvertUtil.entity2SolrInputDocument(o);
                docs.add(solrInputDocument);
            }
            LOG.i(SolrUtil.class, "list:{}", JsonHelper.obj2JsonStr(docs));
            //将转换后的list数据保存到solr
            httpSolrClient.add(docs);
            httpSolrClient.commit();
        } catch (Exception e) {
            LOG.e(SolrUtil.class, e.getMessage(), e);
        } finally {
           try {
               httpSolrClient.close();
           }catch (Exception e) {
               LOG.e(SolrUtil.class, e.getMessage(), e);
           }
        }
       return flag;
    }*/

}
