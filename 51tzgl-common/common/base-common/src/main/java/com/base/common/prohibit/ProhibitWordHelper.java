package com.base.common.prohibit;

import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * Created by lx on 16-11-4.
 */
public class ProhibitWordHelper {

    private static String replceStr = "*";

    private static String getReplaceStr(Integer endIndex,Integer startIndex){
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < endIndex - startIndex; i++){
            buffer.append(replceStr);
        }
        return buffer.toString();
    }

    /*public static String filter(String text,Set<String> set){

        StringBuilder buffer = new StringBuilder(text);
        HashMap<Integer, Integer> hash = new HashMap<>(set.size());
        for (String temp : set) {
            if (StringUtils.isEmpty(temp)){
                continue;
            }
            int findIndexSize = 0;
            for(int start = -1;(start=buffer.indexOf(temp,findIndexSize)) > -1;) {
                //从已找到的后面开始找
                findIndexSize = start+temp.length();
                //起始位置
                Integer mapStart = hash.get(start);
                //满足1个，即可更新map
                if(mapStart == null || (mapStart != null && findIndexSize > mapStart)) {
                    hash.put(start, findIndexSize);
                }
            }
        }
        Collection<Integer> values = hash.keySet();
        for(Integer startIndex : values) {
            Integer endIndex = hash.get(startIndex);
            //buffer.replace(startIndex, endIndex, replaceAll.substring(0,endIndex-startIndex));
            buffer.replace(startIndex, endIndex, getReplaceStr(endIndex,startIndex));
        }
        hash.clear();
        return buffer.toString();
    }*/

    public static Set<String> findProhibitWord(String text,Set<String> set){

        StringBuilder buffer = new StringBuilder(text);
        HashMap<Integer, Integer> hash = new HashMap<>(set.size());
        Set<String> list = new HashSet<>();
        for (String temp : set) {
            if (StringUtils.isEmpty(temp)){
                continue;
            }
            int findIndexSize = 0;
            for(int start = -1;(start=buffer.indexOf(temp,findIndexSize)) > -1;) {
                //从已找到的后面开始找
                findIndexSize = start+temp.length();
                //起始位置
                Integer mapStart = hash.get(start);
                //满足1个，即可更新map
                if(mapStart == null || (mapStart != null && findIndexSize > mapStart)) {
                    list.add(temp);
                    hash.put(start, findIndexSize);
                }
            }
        }
        hash.clear();
        return list;
    }

    public static void main(String[] args) {

        Set<String> set = new HashSet<>();
        set.add("中国");
        set.add("刘刘");
        set.add("大厦");
        set.add("我的");
        set.add("啥事");

        String test = "萨达发散看大家发上来的萨达发几上来看中国大饭店是到沙发科技大厦来开发萨达大水库激发拉萨看对方是刘刘萨达发散看大家发上来的萨达发几上来看中国大饭店是到沙发科技大厦来开发萨达大水库激发拉萨看对方是刘刘萨达发散看大家发上来的萨达发几上来看中国大饭店是到沙发科技大厦来开发萨达大水库激发拉萨看对方是刘刘萨达发散看大家发上来的萨达发几上来看中国大饭店是到沙发科技大厦来开发萨达大水库激发拉萨看对方是刘刘萨达发散看大家发上来的萨达发几上来看中国大饭店是到沙发科技大厦来开发萨达大水库激发拉萨看对方是刘刘萨达发散看大家发上来的萨达发几上来看中国大饭店是到沙发科技大厦来开发萨达大水库激发拉萨看对方是刘刘萨达发散看大家发上来的萨达发几上来看中国大饭店是到沙发科技大厦来开发萨达大水库激发拉萨看对方是刘刘萨达发散看大家发上来的萨达发几上来看中国大饭店是到沙发科技大厦来开发萨达大水库激发拉萨看对方是刘刘萨达发散看大家发上来的萨达发几上来看中国大饭店是到沙发科技大厦来开发萨达大水库激发拉萨看对方是刘刘萨达发散看大家发上来的萨达发几上来看中国大饭店是到沙发科技大厦来开发萨达大水库激发拉萨看对方是刘刘萨达发散看大家发上来的萨达发几上来看中国大饭店是到沙发科技大厦来开发萨达大水库激发拉萨看对方是刘刘萨达发散看大家发上来的萨达发几上来看中国大饭店是到沙发科技大厦来开发萨达大水库激发拉萨看对方是刘刘萨达发散看大家发上来的萨达发几上来看中国大饭店是到沙发科技大厦来开发萨达大水库激发拉萨看对方是刘刘萨达发散看大家发上来的萨达发几上来看中国大饭店是到沙发科技大厦来开发萨达大水库激发拉萨看对方是刘刘萨达发散看大家发上来的萨达发几上来看中国大饭店是到沙发科技大厦来开发萨达大水库激发拉萨看对方是刘刘萨达发散看大家发上来的萨达发几上来看中国大饭店是到沙发科技大厦来开发萨达大水库激发拉萨看对方是刘刘萨达发散看大家发上来的萨达发几上来看中国大饭店是到沙发科技大厦来开发萨达大水库激发拉萨看对方是刘刘萨达发散看大家发上来的萨达发几上来看中国大饭店是到沙发科技大厦来开发萨达大水库激发拉萨看对方是刘刘萨达发散看大家发上来的萨达发几上来看中国大饭店是到沙发科技大厦来开发萨达大水库激发拉萨看对方是刘刘萨达发散看大家发上来的萨达发几上来看中国大饭店是到沙发科技大厦来开发萨达大水库激发拉萨看对方是刘刘萨达发散看大家发上来的萨达发几上来看中国大饭店是到沙发科技大厦来开发萨达大水库激发拉萨看对方是刘刘萨达发散看大家发上来的萨达发几上来看中国大饭店是到沙发科技大厦来开发萨达大水库激发拉萨看对方是刘刘萨达发散看大家发上来的萨达发几上来看中国大饭店是到沙发科技大厦来开发萨达大水库激发拉萨看对方是刘刘萨达发散看大家发上来的萨达发几上来看中国大饭店是到沙发科技大厦来开发萨达大水库激发拉萨看对方是刘刘萨达发散看大家发上来的萨达发几上来看中国大饭店是到沙发科技大厦来开发萨达大水库激发拉萨看对方是刘刘萨达发散看大家发上来的萨达发几上来看中国大饭店是到沙发科技大厦来开发萨达大水库激发拉萨看对方是刘刘萨达发散看大家发上来的萨达发几上来看中国大饭店是到沙发科技大厦来开发萨达大水库激发拉萨看对方是刘刘萨达发散看大家发上来的萨达发几上来看中国大饭店是到沙发科技大厦来开发萨达大水库激发拉萨看对方是刘刘萨达发散看大家发上来的萨达发几上来看中国大饭店是到沙发科技大厦来开发萨达大水库激发拉萨看对方是刘刘萨达发散看大家发上来的萨达发几上来看中国大饭店是到沙发科技大厦来开发萨达大水库激发拉萨看对方是刘刘萨达发散看大家发上来的萨达发几上来看中国大饭店是到沙发科技大厦来开发萨达大水库激发拉萨看对方是刘刘萨达发散看大家发上来的萨达发几上来看中国大饭店是到沙发科技大厦来开发萨达大水库激发拉萨看对方是刘刘萨达发散看大家发上来的萨达发几上来看中国大饭店是到沙发科技大厦来开发萨达大水库激发拉萨看对方是刘刘萨达发散看大家发上来的萨达发几上来看中国大饭店是到沙发科技大厦来开发萨达大水库激发拉萨看对方是刘刘萨达发散看大家发上来的萨达发几上来看中国大饭店是到沙发科技大厦来开发萨达大水库激发拉萨看对方是刘刘萨达发散看大家发上来的萨达发几上来看中国大饭店是到沙发科技大厦来开发萨达大水库激发拉萨看对方是刘刘萨达发散看大家发上来的萨达发几上来看中国大饭店是到沙发科技大厦来开发萨达大水库激发拉萨看对方是刘刘萨达发散看大家发上来的萨达发几上来看中国大饭店是到沙发科技大厦来开发萨达大水库激发拉萨看对方是刘刘萨达发散看大家发上来的萨达发几上来看中国大饭店是到沙发科技大厦来开发萨达大水库激发拉萨看对方是刘刘萨达发散看大家发上来的萨达发几上来看中国大饭店是到沙发科技大厦来开发萨达大水库激发拉萨看对方是刘刘萨达发散看大家发上来的萨达发几上来看中国大饭店是到沙发科技大厦来开发萨达大水库激发拉萨看对方是刘刘萨达发散看大家发上来的萨达发几上来看中国大饭店是到沙发科技大厦来开发萨达大水库激发拉萨看对方是刘刘萨达发散看大家发上来的萨达发几上来看中国大饭店是到沙发科技大厦来开发萨达大水库激发拉萨看对方是刘刘萨达发散看大家发上来的萨达发几上来看中国大饭店是到沙发科技大厦来开发萨达大水库激发拉萨看对方是刘刘萨达发散看大家发上来的萨达发几上来看中国大饭店是到沙发科技大厦来开发萨达大水库激发拉萨看对方是刘刘萨达发散看大家发上来的萨达发几上来看中国大饭店是到沙发科技大厦来开发萨达大水库激发拉萨看对方是刘刘萨达发散看大家发上来的萨达发几上来看中国大饭店是到沙发科技大厦来开发萨达大水库激发拉萨看对方是刘刘萨达发散看大家发上来的萨达发几上来看中国大饭店是到沙发科技大厦来开发萨达大水库激发拉萨看对方是刘刘萨达发散看大家发上来的萨达发几上来看中国大饭店是到沙发科技大厦来开发萨达大水库激发拉萨看对方是刘刘萨达发散看大家发上来的萨达发几上来看中国大饭店是到沙发科技大厦来开发萨达大水库激发拉萨看对方是刘刘萨达发散看大家发上来的萨达发几上来看中国大饭店是到沙发科技大厦来开发萨达大水库激发拉萨看对方是刘刘萨达发散看大家发上来的萨达发几上来看中国大饭店是到沙发科技大厦来开发萨达大水库激发拉萨看对方是刘刘萨达发散看大家发上来的萨达发几上来看中国大饭店是到沙发科技大厦来开发萨达大水库激发拉萨看对方是刘刘萨达发散看大家发上来的萨达发几上来看中国大饭店是到沙发科技大厦来开发萨达大水库激发拉萨看对方是刘刘萨达发散看大家发上来的萨达发几上来看中国大饭店是到沙发科技大厦来开发萨达大水库激发拉萨看对方是刘刘萨达发散看大家发上来的萨达发几上来看中国大饭店是到沙发科技大厦来开发萨达大水库激发拉萨看对方是刘刘萨达发散看大家发上来的萨达发几上来看中国大饭店是到沙发科技大厦来开发萨达大水库激发拉萨看对方是刘刘萨达发散看大家发上来的萨达发几上来看中国大饭店是到沙发科技大厦来开发萨达大水库激发拉萨看对方是刘刘萨达发散看大家发上来的萨达发几上来看中国大饭店是到沙发科技大厦来开发萨达大水库激发拉萨看对方是刘刘萨达发散看大家发上来的萨达发几上来看中国大饭店是到沙发科技大厦来开发萨达大水库激发拉萨看对方是刘刘萨达发散看大家发上来的萨达发几上来看中国大饭店是到沙发科技大厦来开发萨达大水库激发拉萨看对方是刘刘萨达发散看大家发上来的萨达发几上来看中国大饭店是到沙发科技大厦来开发萨达大水库激发拉萨看对方是刘刘";

        System.out.println(findProhibitWord("q234", set));
    }
}
