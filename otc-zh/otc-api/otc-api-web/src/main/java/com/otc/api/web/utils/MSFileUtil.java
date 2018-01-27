package com.otc.api.web.utils;

import com.jucaifu.common.log.LOG;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlException;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by yangmin on 2014/6/16.
 */
public class MSFileUtil {

    /**
     * 通过参数map替换docx word文件中的内容,并返回byte数组
     *
     * @param wordFile
     * @param map
     * @return
     */
    public static void replaceWordFileContentByMap(String wordFile, Map<String, String> map) {
        XWPFDocument document = null;
        byte[] bytes = null;
        try {
            document = new XWPFDocument(POIXMLDocument.openPackage(wordFile));
            // replace the text of header
            if (document.getHeaderList() != null && document.getHeaderList().size() > 0) {
                XWPFHeader header = document.getHeaderArray(0);
                List<XWPFParagraph> listHeader = header.getParagraphs();
                for (XWPFParagraph paragraph : listHeader) {
                    replaceWordText(paragraph, document, map);
                }
            }
            // replace the text of body
            Iterator<XWPFParagraph> itPara = document.getParagraphsIterator();
            while (itPara.hasNext()) {
                XWPFParagraph paragraph = itPara.next();
                // replace text and pictures
                replaceWordText(paragraph, document, map);
            }
            // replace the text of table
            Iterator<XWPFTable> itTable = document.getTablesIterator();
            while (itTable.hasNext()) {
                XWPFTable table = itTable.next();
                readWordTableRangeRule(table, map);

            }
            FileOutputStream fos = new FileOutputStream(new File("/home/yong/aa.docx"));
            document.write(fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static byte[] replaceWordFileContentByMap(InputStream inputStream, Map<String, String> map) {
        XWPFDocument document = null;
        byte[] bytes = null;
        try {
            document = new XWPFDocument(inputStream);
            // replace the text of header
            if (document.getHeaderList() != null && document.getHeaderList().size() > 0) {
                XWPFHeader header = document.getHeaderArray(0);
                List<XWPFParagraph> listHeader = header.getParagraphs();
                for (XWPFParagraph paragraph : listHeader) {
                    replaceWordText(paragraph, document, map);
                }
            }
            // replace the text of body
            Iterator<XWPFParagraph> itPara = document.getParagraphsIterator();
            while (itPara.hasNext()) {
                XWPFParagraph paragraph = itPara.next();
                // replace text and pictures
                replaceWordText(paragraph, document, map);
            }
            // replace the text of table
            Iterator<XWPFTable> itTable = document.getTablesIterator();
            while (itTable.hasNext()) {
                XWPFTable table = itTable.next();
                readWordTableRangeRule(table, map);

            }
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            document.write(byteArrayOutputStream);
            bytes = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.flush();
            byteArrayOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    private static void replaceWordText(XWPFParagraph paragraph, XWPFDocument document, Map<String, String> map) {
        if (null != map && map.size() > 0) {
            List<XWPFRun> runs = paragraph.getRuns();
            for (XWPFRun run : runs) {
                String oneparaString = run.getText(run.getTextPosition());
                if (StringUtils.isNotBlank(oneparaString)) {
                    for (Map.Entry<String, String> entry : map.entrySet()) {
                        String key = entry.getKey();
                        String value = entry.getValue();
                        if (oneparaString.contains(key)) {
                            LOG.d("", key);
                            LOG.d("", value);
                            value = getSafeString(value);
                            oneparaString = oneparaString.replace(key, value);
                            LOG.d("=================", "================");
                        }
                    }
                    run.setText(oneparaString, 0);
                }
            }
        }
    }


    private static String getSafeString(String value) {
        String result;
        if (StringUtils.isBlank(value)) {
            result = "    ";
        } else {
            result = value;
        }
        return result;
    }

    private static void readWordTableRangeRule(XWPFTable table, Map<String, String> map) {
        if (null != map && map.size() > 0) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                table.getText().replaceAll(entry.getKey(), entry.getValue());
            }
        }
    }

    public static void main(String[] args) throws XmlException, OpenXML4JException, IOException {

        Map<String, String> map = new HashMap<String, String>();
        map.put("$(contractNo)", "1212121");

        replaceWordFileContentByMap("f://wumu001_20140613075309.docx", map);
    }

    /**
     * 格式化html文件
     *
     * @param input
     * @param map
     * @return
     */
    public static String formatHtml(String input, Map<String, String> map) {

        return replaceHtml(input, map);
    }

    private static String replaceHtml(String input, Map<String, String> map) {
        if (null != map && map.size() > 0) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                if (input.contains(key)) {
                    value = getSafeString(value);
                    input = input.replace(key, value);
                }
            }
        }
        return input;
    }
}
