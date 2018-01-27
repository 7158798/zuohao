package com.base.service.info.utils;

import com.base.facade.exception.BaseCommonBizException;
import com.jucaifu.common.poi.ImportExcelToolsHelper;
import com.jucaifu.common.util.DateHelper;
import com.base.facade.info.pojo.po.InfoExchange;
import com.base.facade.info.pojo.po.InfoExchangeDetail;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by liuxun on 16-8-24.
 */
public class InfoExchangeImportExcelUtil {

    /**
     * 解析业务数据
     * @param inputStream
     * @param isExcel2003
     * @return
     */
    public static Map<InfoExchange,List<InfoExchangeDetail>> importStreamExcel(InputStream inputStream, boolean isExcel2003) {

        ImportExcelToolsHelper poi = new ImportExcelToolsHelper();
        List<List<String>> list = poi.read(inputStream, isExcel2003);

        InfoExchange exchange = new InfoExchange();
        List<InfoExchangeDetail> detailList = new ArrayList<>();
        Map<InfoExchange,List<InfoExchangeDetail>> map = new HashMap<>();
        InfoExchangeDetail detail;
        if (list != null) {
            String batchNO = list.get(1).get(1);
            if (StringUtils.isBlank(batchNO)){
                throw new BaseCommonBizException("批次号不能为空");
            }
            String updateTime = list.get(2).get(1);
            if (StringUtils.isBlank(updateTime)){
                throw new BaseCommonBizException("更新日期不能为空");
            }
            Date date = DateHelper.string2Date(updateTime,DateHelper.DateFormatType.YearMonthDay_HourMinuteSecond);
            if (date == null){
                throw new BaseCommonBizException("更新日期格式不正确");
            }
            exchange.setBatchNo(batchNO);
            for (int j = 4; j < list.size(); j++){
                detail = new InfoExchangeDetail();
                //
                String curName = list.get(j).get(0);
                String tradeUnit = list.get(j).get(1);
                String seBuy = list.get(j).get(2);
                String cnBuy = list.get(j).get(3);
                String seSell = list.get(j).get(4);
                String cnSell = list.get(j).get(5);
                String bankConversionPri = list.get(j).get(6);

                detail.setCurrencyName(curName);
                detail.setTradeUnit(convert(tradeUnit));
                detail.setfBuyPri(convert(seBuy));
                detail.setmBuyPri(convert(cnBuy));
                detail.setfSellPri(convert(seSell));
                detail.setmSellPri(convert(cnSell));
                detail.setBankConversionPri(convert(bankConversionPri));
                detail.setUpdateDatetime(date);

                if (detail.getBankConversionPri() == null){
                    throw new BaseCommonBizException("中行折算价格不能为空");
                }

                detailList.add(detail);
            }
            if (detailList.isEmpty()) {
                throw new BaseCommonBizException("excel文件不存在相应的列");
            }
        } else {
            throw new BaseCommonBizException("导入的excel文件为空,请重新导入");
        }
        map.put(exchange,detailList);
        return map;
    }

    /**
     * 数据转换
     * @param value
     * @return
     */
    private static BigDecimal convert(String value){

        if(StringUtils.isBlank(value)){

            return null;
        }
        BigDecimal decimal = new BigDecimal(value);

        if (decimal.compareTo(BigDecimal.ZERO) != 1){
            return null;
        }

        return decimal;
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {

         /*SQLUtil.getSQL();
       Class test = Class.forName(BaseRateUtil.class.getName());

        Method method = test.getMethod("analysisDeposit",InfoRateDataSource.class);
        //Method method = test.getMethod("analysisCredit",InfoRateDataSource.class);

        Object object = method.invoke(null, InfoRateDataSource.CGB_DEPOSIT);

        object.toString();*/
        System.out.println(DateHelper.date2String(new Date(),DateHelper.DateFormatType.YearMonthDay_HourMinuteSecond));
        File file = new File("/home/yangyy/Downloads/新手专盈.html");
        File out = new File("/home/yangyy/Downloads/1.pdf");
        if (!out.exists()){
            out.createNewFile();
        }

        InputStream inputStream = new FileInputStream(file);
        OutputStream outputStream = new FileOutputStream(out);

       /* InputStreamReader isr = new InputStreamReader(inputStream);//读取
        BufferedReader bufr = new BufferedReader(isr);

        StringBuffer buffer = new StringBuffer();
        String line = null;
        while((line = bufr.readLine())!=null){
            buffer.append(line);
        }*/

        //PDFHelper.htmlToPDF(inputStream,outputStream);
        //FlyPDFHelper.htmlToPDF(buffer.toString(),outputStream);
        //PDFHelper.htmlToPDF(buffer.toString(), outputStream);
        System.out.println(DateHelper.date2String(new Date(),DateHelper.DateFormatType.YearMonthDay_HourMinuteSecond));
    }
}
