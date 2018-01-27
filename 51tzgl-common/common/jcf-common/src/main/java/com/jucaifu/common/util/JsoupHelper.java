package com.jucaifu.common.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by liuxun on 16-8-26.
 */
public class JsoupHelper {

    public static final String TABLE = "table";
    public static final String TR = "tr";
    public static final String TD = "td";
    public static final String POST = "post";

    private JsoupHelper(){
    }

    /**
     * 解析页面
     * @param url
     * @param post
     * @return
     * @throws IOException
     */
    public static Document parse(String url,String post) throws IOException {

        Document doc;
        if (POST.equals(post)){
            doc = Jsoup.connect(url).post();
        } else {
            doc = Jsoup.connect(url).get();
        }

        return doc;
    }

    public static Document parse(String url) throws IOException {

        return parse(url,null);
    }

    public static List<List<String>> parseToTable(Document doc){
        return parseToTable(doc,"");
    }

    /**
     * 解析表格里面的内容
     * @param doc
     * @param tbJquery  表格选择器
     * @param tdJquery  TD选择器
     * @return
     */
    public static List<List<String>> parseToTable(Document doc, String tbJquery,String tdJquery){

        String select = StringUtils.isEmpty(tbJquery)?TABLE:tbJquery;
        Elements elements = doc.select(select);
        List<List<String>> tableList = new ArrayList<>();
        for (Element element : elements){
            Elements trs = element.select(TR);
            for (Element tr : trs){
                List<String> tdList = parseText(tr,tdJquery);
                if (tdList.size() == 0){
                    // 是表头
                    continue;
                }
                tableList.add(tdList);
            }
        }

        return tableList;
    }
    public static List<List<String>> parseToTable(Document doc, String tbJquery){
        return parseToTable(doc,tbJquery,"");
    }

    /**
     * 解析TD里面的内容
     * @param element
     * @param tdJquery
     * @return
     */
    private static List<String> parseText(Element element,String tdJquery){

        List<String> list = new ArrayList<>();
        Elements tds =  element.select(TD);
        for (Element temp : tds){
            String value;
            if (StringUtils.isBlank(tdJquery)){
                value = temp.text();
            } else {
                Elements elements =temp.select(tdJquery);
                if (elements.size() == 0){
                    value = temp.text();
                } else {
                    value = elements.text();
                }
            }

            list.add(value == null || " ".equals(value) ? "" : value.trim());
        }
        return list;
    }
}
