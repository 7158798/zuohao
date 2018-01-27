package com.base.facade.info.utils;

import com.base.facade.info.enums.InfoRateDataSource;
import com.base.facade.info.enums.InfoRateGainWay;
import com.base.facade.info.enums.InfoRateStatus;
import com.base.facade.info.pojo.po.InfoRate;
import com.base.facade.info.pojo.po.InfoRateDetail;
import com.jucaifu.common.util.JsoupHelper;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuxun on 16-8-30.
 */
public class BaseRateUtil {

    /**
     * 获取利率对象
     * @param type     0：存款利率  1：贷款利率
     * @param bankId   银行ID
     * @return
     */
    public static InfoRate getInfoRate(String type,String bankId){

        InfoRate info = new InfoRate();

        info.setBankId(bankId);
        info.setType(type);
        info.setDeleted(Boolean.FALSE);
        info.setStatus(InfoRateStatus.READY_RELEASE.getCode());
        info.setGainWay(InfoRateGainWay.INSERT_BY_CRAWLER.getCode());

        return info;
    }

    /**
     * 获取POST
     * @param dataSource
     * @return
     */
    private static String getPost(InfoRateDataSource dataSource){

        if (dataSource.getBankName().indexOf("工商银行") >= 0){

            return JsoupHelper.POST;
        }
        return null;
    }

    /**
     * 存款利率(公用)
     * @param dataSource
     * @throws IOException
     */
    public static List<InfoRateDetail> analysisDeposit(InfoRateDataSource dataSource) throws IOException {
        List<List<String>> list = JsoupHelper.parseToTable(JsoupHelper.parse(dataSource.getUrl(),getPost(dataSource)), dataSource.getJquery());

        List<InfoRateDetail> detailList = new ArrayList<InfoRateDetail>();
        String item1 = null;
        String item2 = null;
        for (int j = 0; j < list.size() ; j++ ) {
            InfoRateDetail detail = new InfoRateDetail();
            List<String> tList = list.get(j);
            if (StringUtils.isBlank(item1) && tList.size() < 2){
                continue;
            }
            String first = tList.get(0).trim();
            // 清除.
            if ("项目".equals(first) || "存款种类".equals(first)){
                continue;
            }
            String second = "";
            if (tList.size() > 1){
                second = trim(tList.get(1).trim());
            }
            second = second.equals(".")?null:second;

            if (first.indexOf("一、") >= 0 || first.indexOf("二、") >= 0 || first.indexOf("三、") >= 0
                    || first.indexOf("四、") >= 0 || first.indexOf("五、") >= 0){
                if ((first.indexOf("活期") >= 0 || first.indexOf("、定期") >= 0) && first.indexOf("城乡居民") < 0 ){
                    // 特殊处理
                    item1 = "城乡居民存款";
                } else {
                    item1 = first;
                }
                item2 = null;
            } else if (StringUtils.isBlank(second) || first.indexOf("定活两便") >= 0){
                item2 = first;
            }
            if (StringUtils.isBlank(second)){
                continue;
            }
            detail.setItemFisrt(trim(item1));
            detail.setItemSecond(trim(first.indexOf("活期")>= 0?first:item2));
            if(item1 != null && !item1.equals(first)){
                detail.setPeriod(trim(first));
            }
            detail.setRate(second);
            detailList.add(detail);
        }

        return detailList;
    }


    /**
     * 解析贷款利率（公用）
     * @param dataSource
     * @throws IOException
     */
    public static List<InfoRateDetail> analysisCredit(InfoRateDataSource dataSource) throws IOException {

        List<List<String>> list = JsoupHelper.parseToTable(JsoupHelper.parse(dataSource.getUrl(),getPost(dataSource)), dataSource.getJquery());

        List<InfoRateDetail> detailList = new ArrayList<InfoRateDetail>();
        String item1 = null;
        for (int j = 0; j < list.size() ; j++ ) {
            InfoRateDetail detail = new InfoRateDetail();
            List<String> tList = list.get(j);
            if (tList.size() < 2){
                continue;
            }
            String first = tList.get(0).trim();
            String second = tList.get(1).trim();
            if ("项目".equals(first) || second.indexOf("年利率") >= 0){
                continue;
            }
            if (first.indexOf("、") > 0){
                //
                item1 = first;
            }
            if (StringUtils.isBlank(second)){
                continue;
            }
            detail.setItemFisrt(trim(item1));
            detail.setPeriod(trim(first));
            detail.setRate(trim(second));
            detailList.add(detail);
        }

        return detailList;
    }

    /**
     * 过滤特殊字符
     * @param value
     * @return
     */
    public static String trim(String value){
        if (value != null) {
            value = value.replaceAll("[^0-9a-zA-Z\\u4e00-\\u9fa5\\u3002\\uff1b\\uff0c\\uff1a\\u201c\\u201d\\uff08\\uff09\\u3001\\uff1f\\u300a\\u300b.%-]", "");
        }
        return value;
    }
}
