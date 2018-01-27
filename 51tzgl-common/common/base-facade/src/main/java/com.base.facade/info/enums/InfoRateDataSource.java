package com.base.facade.info.enums;


import com.base.facade.info.utils.BaseRateUtil;
import com.base.facade.info.utils.SHRateUtil;

/**
 * 银行利率
 * Created by liuxun on 16-8-30.
 */
public enum InfoRateDataSource {

    // 农业银行存款(类型,银行名称,数据的地址，数据选择器，数据解析类，数据解析方法)
    ABC_DEPOSIT(InfoRateType.DEPOSIT_RATE,"农业银行","http://www.abchina.com/cn/PersonalServices/Quotation/bwbll/201511/t20151126_807920.htm",".DataList", BaseRateUtil.class.getName(),"analysisDeposit"),
    // 农业银行贷款
    ABC_CREDIT(InfoRateType.LOAN_RATE,"农业银行","http://www.abchina.com/cn/PersonalServices/Quotation/bwbll/201511/t20151126_807918.htm",".DataList",BaseRateUtil.class.getName(),"analysisCredit"),
    // 建设银行
    CCB_DEPOSIT(InfoRateType.DEPOSIT_RATE,"建设银行","http://ccb.com/cn/personal/interest/20151024_1445618390.html","",BaseRateUtil.class.getName(),"analysisDeposit"),
    CCB_CREDIT(InfoRateType.LOAN_RATE,"建设银行","http://ccb.com/cn/personal/interest/20151024_1445618923.html","",BaseRateUtil.class.getName(),"analysisCredit"),
    // 中国银行
    BOC_DEPOSIT(InfoRateType.DEPOSIT_RATE,"中国银行","http://www.boc.cn/fimarkets/lilv/fd31/201510/t20151023_5824963.html","",BaseRateUtil.class.getName(),"analysisDeposit"),
    BOC_CREDIT(InfoRateType.LOAN_RATE,"中国银行","http://www.boc.cn/fimarkets/lilv/fd32/201510/t20151023_5824975.html","",BaseRateUtil.class.getName(),"analysisCredit"),
    // 南京银行
    NJ_DEPOSIT(InfoRateType.DEPOSIT_RATE,"南京银行","http://www.njcb.com.cn/njcb/index/khfw3/jrxx84/ckll/420655/416892/index.html","#news_content table",BaseRateUtil.class.getName(),"analysisDeposit"),
    NJ_CREDIT(InfoRateType.LOAN_RATE,"南京银行","http://www.njcb.com.cn/njcb/index/khfw3/jrxx84/396366/420650/416921/index.html","#news_content table",BaseRateUtil.class.getName(),"analysisCredit"),
    // 光大银行
    CEB_DEPOSIT(InfoRateType.DEPOSIT_RATE,"光大银行","http://www.cebbank.com/site/gryw/xqkb90/rmbckll16/rmbckll/8368956/index.html","",BaseRateUtil.class.getName(),"analysisDeposit"),
    CEB_CREDIT(InfoRateType.LOAN_RATE,"光大银行","http://www.cebbank.com/site/gryw/xqkb90/rmbdkll/8040474/index.html","",BaseRateUtil.class.getName(),"analysisCredit"),
    // 上海银行
    SH_DEPOSIT(InfoRateType.DEPOSIT_RATE,"上海银行","http://www.bankofshanghai.com/zh/wxyh/27883.shtml",".MsoNormalTable",SHRateUtil.class.getName(),"analysisDeposit"),
    SH_CREDIT(InfoRateType.LOAN_RATE,"上海银行","http://www.bankofshanghai.com/zh/wxyh/27883.shtml","table:contains(人民币贷款利率表)", SHRateUtil.class.getName(),"analysisCredit"),
    // 工商银行
    ICBC_DEPOSIT(InfoRateType.DEPOSIT_RATE,"工商银行","http://www.icbc.com.cn/ICBCDynamicSite2/other/rmbdeposit.aspx","table[rules=all]",BaseRateUtil.class.getName(),"analysisDeposit"),
    ICBC_CREDIT(InfoRateType.LOAN_RATE,"工商银行","http://www.icbc.com.cn/ICBCDynamicSite2/other/rmbcredit.aspx","table[rules=all]",BaseRateUtil.class.getName(),"analysisCredit"),
    // 广发银行
    CGB_DEPOSIT(InfoRateType.DEPOSIT_RATE,"广发银行","http://www.cgbchina.com.cn/showCHYDepositRate.gsp",".second_tb",BaseRateUtil.class.getName(),"analysisDeposit"),
    CGB_CREDIT(InfoRateType.LOAN_RATE,"广发银行","http://www.cgbchina.com.cn/showCHYLoanRate.gsp","",BaseRateUtil.class.getName(),"analysisCredit");

    private InfoRateType type;
    // 银行名称
    private String bankName;
    // 数据获取地址
    private String url;
    // 数据选择器
    private String jquery;
    // 解析的类名
    private String className;
    // 解析的方法
    private String method;

    InfoRateDataSource(InfoRateType type,String bankName,String url,String jquery,String className,String method){
        this.type = type;
        this.bankName = bankName;
        this.url = url;
        this.jquery = jquery;
        this.className = className;
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getJquery() {
        return jquery;
    }

    public void setJquery(String jquery) {
        this.jquery = jquery;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public InfoRateType getType() {
        return type;
    }

    public void setType(InfoRateType type) {
        this.type = type;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public static void main(String[] args) {
        System.out.print(BaseRateUtil.class.getName());
    }
}
