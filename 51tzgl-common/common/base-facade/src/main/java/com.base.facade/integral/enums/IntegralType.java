package com.base.facade.integral.enums;

import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * 积分类型
 * <p>
 * Created by yangyy on 16-5-3.
 */
public enum IntegralType {

    PUBLISH_RAIDERS("PUBLISH_RAIDERS", "发攻略",1),
    PUBLISH_POSTS("PUBLISH_POSTS", "发帖子", 2),
    COMMONT("COMMONT", "评论", 3),
    SHARE("SHARE", "分享", 4),
    SIGIN_ERVERDY("SIGIN_ERVERDY", "签到", 5),
    CONVERT_CASH("CONVERT_CASH", "积分提现", 6),
    MERGE_ACCOUNT("MERGE_ACCOUNT","账户合并",7),
    DUIBA_BUY_PRODUCT_FAIL("DUIBA_BUY_PRODUCT_FAIL","兑换失败返还",8),

    //兑吧相关
    ALIPAY("alipay", "提现",101),

    QB("qb", "Q币",102),

    COUPON("coupon", "优惠券",103),

    OBJECT("object", "实物",104),

    PHONEBILL("phonebill", "话费",105),

    PHONEFLOW("phoneflow", "流量",106),

    VIRTUAL("virtual", "虚拟商品",107),

    TURNTABLE("turntable", "大转盘",108),

    SINGLELOTTERY("singleLottery", "单品抽奖",109),

    HDTOOLLOTTERY("hdtoolLottery", "活动抽奖",110),

    MANUALLOTTERY("manualLottery", "手动开奖",111),

    GAMELOTTERY("gameLottery", "游戏",112),

    OTHER("gameLottery", "其他",113);


    //ALL_ACOUNT_INTEGRAL("ALL_ACOUNT_INTEGRAL","奖励合计",999)

    private String code;

    private String desc;

    private Integer type;


    /**
     * 反射获取对象
     * @return
     */
    public static IntegralType getTypeFromCode(String code) {
        if(StringUtils.isBlank(code)){
            return null;
        }
        for (IntegralType integralType : IntegralType.values()) {
             if (StringUtils.equals(code,integralType.getCode())) {
                   return integralType;
             }
        }
        return OTHER;
    }



    IntegralType(String code, String desc, int type) {
        this.type = type;
        this.desc = desc;
        this.code = code;
    }

    private static Map<String, String> map = new HashMap<>();

    static {
        for (IntegralType integralType : IntegralType.values()) {
            map.put(integralType.getCode(), integralType.getDesc());
        }
    }

    //增加型积分类型map
    private static Map<Integer, String> incomeMap = new HashMap<>();

    static {
        incomeMap.put(PUBLISH_RAIDERS.getType(), PUBLISH_RAIDERS.getDesc());
        incomeMap.put(PUBLISH_POSTS.getType(), PUBLISH_POSTS.getDesc());
        incomeMap.put(COMMONT.getType(), COMMONT.getDesc());
        incomeMap.put(SHARE.getType(), SHARE.getDesc());
        incomeMap.put(SIGIN_ERVERDY.getType(), SIGIN_ERVERDY.getDesc());
        incomeMap.put(MERGE_ACCOUNT.getType(), MERGE_ACCOUNT.getDesc());
        incomeMap.put(DUIBA_BUY_PRODUCT_FAIL.getType(), DUIBA_BUY_PRODUCT_FAIL.getDesc());
    }


    //显示积分map
    private static Map<Integer, String> viewMap = new HashMap<>();

    static {
        viewMap.put(PUBLISH_RAIDERS.getType(), PUBLISH_RAIDERS.getDesc());
        viewMap.put(PUBLISH_POSTS.getType(), PUBLISH_POSTS.getDesc());
        viewMap.put(COMMONT.getType(), COMMONT.getDesc());
        viewMap.put(SHARE.getType(), SHARE.getDesc());
        viewMap.put(SIGIN_ERVERDY.getType(), SIGIN_ERVERDY.getDesc());
    }

    public static Map<Integer, String> getViewMap() {
        return viewMap;
    }

    public static void setViewMap(Map<Integer, String> viewMap) {
        IntegralType.viewMap = viewMap;
    }

    public static Map<Integer, String> getIncomeMap() {
        return incomeMap;
    }


    private static Map<String, Integer> typeMap = new HashMap<>();

    static {
        for (IntegralType integralType : IntegralType.values()) {
            typeMap.put(integralType.getCode(), integralType.getType());
        }
    }

    private static Map<Integer, String> typeCodeMap = new HashMap<>();

    static {
        for (IntegralType integralType : IntegralType.values()) {
            typeCodeMap.put(integralType.getType(), integralType.getCode());
        }
    }

    public static Map<Integer, String> getTypeCodeMap() {
        return typeCodeMap;
    }

    public static String getCodeByType(Integer type) {
        return typeCodeMap.get(type);
    }


    public static Map<String, String> getMap() {
        return map;
    }

    public static Map<String, Integer> getTypeMap() {
        return typeMap;
    }

    public static String getDescByCode(String code) {
        return map.get(code);
    }

    public static List<Integer> getIntegralTypeList() {
        List<Integer> list = new ArrayList<>();
        for (IntegralType integralType : IntegralType.values()) {
            list.add(integralType.getType());
        }
        return list;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }



    private static Map<String, String> taskListMap = new HashMap<>();

    static {
        for (IntegralType integralType : IntegralType.values()) {
            taskListMap.put(integralType.getCode(), integralType.getDesc());
        }
    }

    public static Map<String, String> getTaskListMap() {
        return taskListMap;
    }

    private static Map<Integer,String> integerStringMap=new HashMap<>();

    static {
        for (IntegralType integralType : IntegralType.values()) {
            integerStringMap.put(integralType.getType(), integralType.getDesc());
        }
    }

    public static String getDescByType(int code) {
        return integerStringMap.get(code);
    }

    public static Map<Integer, String> getIntegerStringMap() {
        return integerStringMap;
    }

    //获取所有的发放积分类型，仅积分统计用
    private static Map<String, String> grantMap = new TreeMap<>();

    public static Map<String, String> queryGrantType() {
        grantMap.clear();
        grantMap.put(PUBLISH_RAIDERS.getType().toString(), PUBLISH_RAIDERS.getDesc());
        grantMap.put(PUBLISH_POSTS.getType().toString(), PUBLISH_POSTS.getDesc());
        grantMap.put(COMMONT.getType().toString(), COMMONT.getDesc());
        grantMap.put(SHARE.getType().toString(), SHARE.getDesc());
        grantMap.put(SIGIN_ERVERDY.getType().toString(), SIGIN_ERVERDY.getDesc());
        grantMap.put(MERGE_ACCOUNT.getType().toString(), MERGE_ACCOUNT.getDesc());
        grantMap.put("totalIntegral", "汇总");
        return grantMap;
    }

    private static  Map<String, String> convertMap = new TreeMap<>();
    public static Map<String, String> queryConvertMap() {
        convertMap.clear();
        convertMap.put(ALIPAY.getType().toString(), ALIPAY.getDesc());
        convertMap.put(QB.getType().toString(), QB.getDesc());
        convertMap.put(COUPON.getType().toString(), COUPON.getDesc());
        convertMap.put(OBJECT.getType().toString(), OBJECT.getDesc());
        convertMap.put(PHONEBILL.getType().toString(), PHONEBILL.getDesc());
        convertMap.put(PHONEFLOW.getType().toString(), PHONEFLOW.getDesc());
        convertMap.put(VIRTUAL.getType().toString(), VIRTUAL.getDesc());
        convertMap.put(TURNTABLE.getType().toString(), TURNTABLE.getDesc());
        convertMap.put(SINGLELOTTERY.getType().toString(), SINGLELOTTERY.getDesc());
        convertMap.put(HDTOOLLOTTERY.getType().toString(), HDTOOLLOTTERY.getDesc());
        convertMap.put(MANUALLOTTERY.getType().toString(), MANUALLOTTERY.getDesc());
        convertMap.put(GAMELOTTERY.getType().toString(), GAMELOTTERY.getDesc());
        convertMap.put("totalIntegral", "汇总");
        return convertMap;
    }
}
