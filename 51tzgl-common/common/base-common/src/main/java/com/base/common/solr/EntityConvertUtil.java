package com.base.common.solr;

import com.jucaifu.common.log.LOG;
import com.jucaifu.common.util.JsonHelper;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import java.util.List;
import java.util.ArrayList;

/**
 * 类型转换
 * @author luwei
 * @Date 12/5/16 5:11 PM
 */
public class EntityConvertUtil {


    /**
     * solrDocumentList转bean list
     * @param solrDocumentList
     * @param clazz
     * @return
     */
    public static Object solrDocumentListToEntityList(SolrDocumentList solrDocumentList, Class clazz) {
        List list = new ArrayList();
        for (SolrDocument solrDocument : solrDocumentList) {
            list.add(solrDocumentToEntity(solrDocument, clazz));
        }
        return list;
    }

    /**
     * solrDocument转bean
     * @param solrDocument
     * @param clazz
     * @return
     */
    public static Object solrDocumentToEntity(SolrDocument solrDocument, Class clazz) {
        Object o = null;
        try {
            o = clazz.newInstance();

            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                Object value = solrDocument.get(field.getName());
                BeanUtils.setProperty(o, field.getName(), value);
            }

        } catch (Exception e) {
            LOG.e(EntityConvertUtil.class, e.getMessage(), e);
        }
        return o;
    }


    public static SolrInputDocument entity2SolrInputDocument(Object obj) {
        SolrInputDocument solrInputDocument = new SolrInputDocument();
        try {
            if (obj == null) {
                LOG.i(EntityConvertUtil.class, "参数obj为空");
                return null;
            }

            LOG.i(EntityConvertUtil.class, "参数obj:{}", JsonHelper.obj2JsonStr(obj));
            Class<?> cls = obj.getClass();
            Field[] fieldArrays = cls.getDeclaredFields(); //获取类中所有的属性
            Method m = null;

            for (Field f : fieldArrays) {
                //如果对象序列化之后，会增加该属性，不用对该属性进行反射
                if (!f.getName().equals("serialVersionUID")) {
                    //跟进属性xx构造对应的getXx()方法
                    String dynamicGetMethod = dynamicMethodName(f.getName(), "get");
                    //调用构造的getXx()方法
                    m = cls.getMethod(dynamicGetMethod); //无参
                    //属性名，与对应的属性值，get方法获取到的值
                    LOG.i(EntityConvertUtil.class, f.getName() + ":" + m.invoke(obj));
                    solrInputDocument.addField(f.getName() + "", m.invoke(obj));
                }
            }

        } catch (Exception e) {
            LOG.e(EntityConvertUtil.class, e.getMessage());
        }
        return solrInputDocument;

    }


    private static String dynamicMethodName(String fieldName, String prefix) throws Exception{
        if(StringUtils.isBlank(fieldName)){
            throw new RuntimeException("参数异常，fieldName不能为空");
        }

        if(StringUtils.isBlank(prefix)){
            throw new RuntimeException("参数异常，prefix不能为空");
        }

        //自动生成的get方法名称 get单词首字母大写，如：getMethod()
        char[] cs=fieldName.toCharArray();
        cs[0]-=32;  //首字母进行ascii编码迁移
        return prefix+String.valueOf(cs);

    }
}
