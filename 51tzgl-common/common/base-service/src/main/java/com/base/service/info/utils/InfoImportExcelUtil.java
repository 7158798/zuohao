package com.base.service.info.utils;

import com.base.facade.exception.BaseCommonBizException;
import com.base.service.pool.BaseServiceBizPool;
import com.jucaifu.common.poi.ImportExcelToolsHelper;
import com.base.facade.info.enums.InfoRateType;
import com.base.facade.info.pojo.po.InfoBank;
import com.base.facade.info.pojo.po.InfoBankProduct;
import com.base.facade.info.pojo.po.InfoRateDetail;
import com.base.facade.info.pojo.poex.InfoRateExcelEx;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zh on 16-8-24.
 */
public class InfoImportExcelUtil {



    /**
     * 导入银行利率
     * @param inputStream
     * @param isExcel2003
     * @return
     */
    public static List<InfoRateExcelEx> importStreamExcelForInfoRate(InputStream inputStream, boolean isExcel2003) throws BaseCommonBizException{

        ImportExcelToolsHelper poi = new ImportExcelToolsHelper();
        List<Object> listObject = poi.readAllSheet(inputStream, isExcel2003);
        List<List<String>> list;
        List<InfoRateExcelEx> resultList = new ArrayList<>();
        InfoRateExcelEx ex = null;
        if(listObject == null){
            throw new BaseCommonBizException("文件为空，请重新导入");
        }
        String batchNo = null;
        String tempBatchNo = null;
        for (Object object : listObject) {
            list = (List<List<String>>) object;
            if(list.size() == 0) {
                continue;
            }
            if (list != null) {
                batchNo = list.get(1).get(1);
                if(StringUtils.isBlank(batchNo)){
                    batchNo = tempBatchNo;
                }else if(!StringUtils.isBlank(tempBatchNo)&&!batchNo.equals(tempBatchNo)){
                    throw new BaseCommonBizException("批次号不一致");
                }
                if(StringUtils.isBlank(tempBatchNo)){
                    tempBatchNo = batchNo;
                }
                String bankName = list.get(2).get(1);
                if(StringUtils.isBlank(bankName)){
                    throw new BaseCommonBizException("银行名称必须输入，请重新导入");
                }

                ex = new InfoRateExcelEx();
                ex.setBankName(bankName);
                if(list.get(0).size() == 3){
                    ex.setType(InfoRateType.LOAN_RATE.getCode());
                }else if(list.get(0).size() == 4){
                    ex.setType(InfoRateType.DEPOSIT_RATE.getCode());
                }else{
                    throw new BaseCommonBizException("文件格式不正确，请重新导入");
                }
                ex.setBatchNo(batchNo);
                List<InfoRateDetail> details = getInfoRateDetailList(list);
                if(details == null || details.size() ==0){
                    throw new BaseCommonBizException("银行利率为空，请重新导入");
                }
                ex.setDetailList(details);
            }
            resultList.add(ex);
        }
        return resultList;

    }


    public static List<InfoBankProduct> importStreamExcelForBankProduct(InputStream inputStream, boolean isExcel2003) throws BaseCommonBizException{

        ImportExcelToolsHelper poi = new ImportExcelToolsHelper();
        List<Object> listObject = poi.readAllSheet(inputStream, isExcel2003);
        List<List<String>> list;
        List<InfoBankProduct> resultList = new ArrayList<>();
        if(listObject == null){
            throw new BaseCommonBizException("文件为空，请重新导入");
        }
        String batchNo = null;
        String tempBatchNo = null;
        InfoBank infoBank = null;
        String period ;
        String rate ;
        String amount;
        InfoBankProduct product;
        for (Object object : listObject) {
            list = (List<List<String>>) object;
            if(list.size() == 0) {
                continue;
            }
            if (list != null) {
                batchNo = list.get(0).get(1);
                if(StringUtils.isBlank(batchNo)){
                    batchNo = tempBatchNo;
                }else if(!StringUtils.isBlank(tempBatchNo)&&!batchNo.equals(tempBatchNo)){
                    throw new BaseCommonBizException("批次号不一致");
                }
                if(StringUtils.isBlank(tempBatchNo)){
                    tempBatchNo = batchNo;
                }
                String bankName = list.get(1).get(1);
                if(StringUtils.isBlank(bankName)){
                    throw new BaseCommonBizException("银行名称必须输入，请重新导入");
                }
                infoBank = BaseServiceBizPool.getInstance().infoBankBiz.getInfoBankByName(bankName);
                if(infoBank == null){
                    throw new BaseCommonBizException(bankName+"未维护，请修正后导入重新导入");
                }
                if(list.size() <= 3){
                    continue;
                }
                for (int j = 3; j < list.size(); j++) {
                  //  period = list.get(j).get(0);
                    period = list.get(j).get(0);
                    rate = list.get(j).get(1);
                    amount = list.get(j).get(2);

                    if(StringUtils.isBlank(period)){
                        throw new BaseCommonBizException(bankName+"第"+(j+1)+"行期限为空");
                    }
                    if(StringUtils.isBlank(rate)){
                        throw new BaseCommonBizException(bankName+"第"+(j+1)+"行利率为空");
                    }
                    if(StringUtils.isBlank(amount)){
                        throw new BaseCommonBizException(bankName+"第"+(j+1)+"行起投金额为空");
                    }
                    product = new InfoBankProduct();
                    product.setBankId(infoBank.getUuid());
                    product.setBatchNo(batchNo);

                    product.setPeriod(Integer.valueOf(period));
                    product.setExpectedInterestRate(new BigDecimal(rate));
                    product.setMinInvestmentAmount(new BigDecimal(amount));
                    resultList.add(product);
                }
            }
        }
        return resultList;

    }







    public static List<InfoRateDetail> getInfoRateDetailList(List<List<String>> list){
        List<InfoRateDetail> details = new ArrayList<>();
        InfoRateDetail detail = null;
        String tempItem1 = "";
        String tempItem2 = "";
        for (int j = 4; j < list.size(); j++) {
            String item1 = list.get(j).get(0);
            String item2 = null;
            String period = null;
            String rate = null;

            if(list.get(j).size() == 4){
                //存款利率
                 item2 = list.get(j).get(1);
                 period = list.get(j).get(2);
                 rate = list.get(j).get(3);
            }else if(list.get(j).size() == 3){
                //贷款利率
                period = list.get(j).get(1);
                rate = list.get(j).get(2);
            }
            if(StringUtils.isBlank(item1)){
                item1 = tempItem1;
            }
            if(StringUtils.isBlank(item2) && tempItem1.equals(item1)){
                item2 = tempItem2;
            }

            detail = new InfoRateDetail();
            detail.setItemFisrt(item1);
            detail.setItemSecond(item2);
            detail.setPeriod(period);
            detail.setRate(rate);

            details.add(detail);

            tempItem1 = item1;
            tempItem2 = item2;
        }
        return details;
    }



    public static String getHtmlConentByUrl(String ssourl) {
        try {
            URL url = new URL(ssourl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setInstanceFollowRedirects(false);
            con.setUseCaches(false);
            con.setAllowUserInteraction(false);
            con.connect();
            StringBuffer sb = new StringBuffer();
            String line = "";
            BufferedReader URLinput = new BufferedReader(new InputStreamReader(con.getInputStream()));
            while ((line = URLinput.readLine()) != null) {
                sb.append(new String(line.getBytes(),"utf-8")+"\n");
            }
            con.disconnect();

            return sb.toString().toLowerCase();
        } catch (Exception e) {

            return null;
        }
    }

//    public static void main(String[] args) throws Exception
//    {
//        String reader = getHtmlConentByUrl("http://www.jb51.net/article/69936.htm");
//        System.out.print(reader);
//
//    }
    public static  void main(String args[]){
        File file = new File("/home/zh/test.xlsx");
        try{
            InputStream is = new FileInputStream(file);
//            ImportExcelToolsHelper poi = new ImportExcelToolsHelper();
//            List<Object> list = poi.readAllSheet(is,false);
            List<InfoRateExcelEx> list = importStreamExcelForInfoRate(is, false);
            System.out.print(list.size());
        }catch (Exception e){

        }


    }
}
