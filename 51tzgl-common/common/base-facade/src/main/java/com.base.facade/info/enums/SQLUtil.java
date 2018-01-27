package com.base.facade.info.enums;

/**
 * Created by yangyy on 16-8-23.
 */
public class SQLUtil {

    private static String CUR = "数字货币,区块链,比特币,数字资产,未来银行";

    private static String SQL1 = "INSERT t_keyword(keyword,create_user_id,create_date,deleted) VALUE (";

    private static String SQL2 = "0,SYSDATE(),false);";

    public static void getSQL(){

        String[] array = CUR.split(",");
        StringBuffer sql = new StringBuffer();
        for (String str : array){
            sql.append(SQL1);
            /*String[] array1 = str.split("-");
            sql.append("'" + array1[1] + "',");
            sql.append("'" + array1[0] + "',");*/
            sql.append("'" + str + "',");

            sql.append(SQL2);

        }

        System.out.println(sql.toString());
    }

    public static void main(String[] args) {
        SQLUtil.getSQL();
    }
}
