package com.base.facade.info.utils;

import com.base.facade.info.enums.InfoRateDataSource;
import com.base.facade.info.pojo.po.InfoRateDetail;
import com.jucaifu.common.util.JsoupHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 上海银行解析类
 * Created by liuxun on 16-8-30.
 */
public class SHRateUtil {

    /**
     * 存款利率
     * @param dataSource
     * @throws IOException
     */
    public static List<InfoRateDetail> analysisDeposit(InfoRateDataSource dataSource) throws IOException {
        List<List<String>> list = JsoupHelper.parseToTable(JsoupHelper.parse(dataSource.getUrl()), dataSource.getJquery());

        List<InfoRateDetail> detailList = new ArrayList<InfoRateDetail>();
        String item1 = "城乡居民存款";
        String item2 = null;
        for (int j = 2; j < list.size() ; j++ ) {
            InfoRateDetail detail = new InfoRateDetail();
            String period = null;
            String rate;
            List<String> tList = list.get(j);
            if (tList.size() == 3) {
                if (j == 2 || j < 13){
                    item2 = tList.get(0);
                } else {
                    item1 = tList.get(0);
                    item2 = null;
                }
                period = tList.get(1);
                rate = tList.get(2);
            } else if (tList.size() == 4){
                item2 = tList.get(1);
                period = tList.get(2);
                rate = tList.get(3);
            } else {
                if (j == 12){
                    item2 = tList.get(0);
                } else {
                    period = tList.get(0);
                }
                rate = tList.get(1);
            }
            detail.setItemFisrt(item1);
            detail.setItemSecond(item2);
            detail.setPeriod(period);
            detail.setRate(BaseRateUtil.trim(rate));
            detailList.add(detail);
        }

        return detailList;
    }


    /**
     * 解析贷款利率
     * @param dataSource
     * @throws IOException
     */
    public static List<InfoRateDetail> analysisCredit(InfoRateDataSource dataSource) throws IOException {

        List<List<String>> list = JsoupHelper.parseToTable(JsoupHelper.parse(dataSource.getUrl()), dataSource.getJquery());

        List<InfoRateDetail> detailList = new ArrayList<InfoRateDetail>();
        String item1 = null;
        for (int j = 2; j < list.size() ; j++ ) {
            InfoRateDetail detail = new InfoRateDetail();
            List<String> tList = list.get(j);
            String period;
            String rate;
            if (tList.size() == 3 || j == 9){
                item1 = tList.get(0).trim();
                if (j == 9){
                    period = null;
                    rate = tList.get(1).trim();
                } else {
                    period = tList.get(1).trim();
                    rate = tList.get(2).trim();
                }
            } else {
                period = tList.get(0).trim();
                rate = tList.get(1).trim();
            }


            detail.setItemFisrt(item1);
            detail.setPeriod(period);
            detail.setRate(BaseRateUtil.trim(rate));
            detailList.add(detail);
        }

        return detailList;
    }

}
