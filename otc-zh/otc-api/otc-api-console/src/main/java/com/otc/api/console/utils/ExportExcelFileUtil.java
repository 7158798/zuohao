package com.otc.api.console.utils;

import com.jucaifu.common.constants.StringPool;
import com.jucaifu.common.util.DateHelper;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.slf4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

/**
 * 导出excel格式为2007
 * <p>
 * Created by yangyy on 16-4-5.
 */
public class ExportExcelFileUtil {

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ExportExcelFileUtil.class);

    /**
     * 生成Excel(xlsx格式)文件
     *
     * @param fileName   文件名不包含路径和后缀
     * @param colMap     key为文件对应的列名，value为对应的字段名
     * @param contents   所要写入数据对应的bean列表
     * @return
     */
    public static <T> File generateExcelFile(String fileName, LinkedHashMap<String, String> colMap, List<T> contents) {
        return generateExcelFile(fileName, colMap, contents, false, null, null);
    }

    /**
     * 生成Excel(xlsx格式)文件
     *
     * @param fileName   文件名不包含路径和后缀
     * @param colMap     key为文件对应的列名，value为对应的字段名
     * @param contents   所要写入数据对应的bean列表
     * @param hasTotal   是否支持列表数据统计 true：支持；false：不支持
     * @param totalName  支持统计时的统计名称
     * @param totalValue 支持统计时的统计值
     * @return
     */
    public static <T> File generateExcelFile(String fileName, LinkedHashMap<String, String> colMap,
                                             List<T> contents, boolean hasTotal, String totalName, String totalValue) {
        // 声明一个工作簿
        XSSFWorkbook workbook = new XSSFWorkbook();
        // 生成一个表格
        XSSFSheet sheet = workbook.createSheet("Sheet1");
        sheet.setDefaultRowHeight((short) 400);
        // 生成一个样式，用来设置标题样式
        XSSFCellStyle style = workbook.createCellStyle();

        // 生成一个字体
        XSSFFont font = workbook.createFont();
        font.setColor((short) 0);
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        font.setFontHeight((short) 230);
        style.setFont(font);

        //生成一个样式，用来设置标题头样式
        XSSFCellStyle style1 = workbook.createCellStyle();
        style1.setBorderBottom(XSSFCellStyle.BORDER_DOTTED);//下边框
        style1.setBorderLeft(XSSFCellStyle.BORDER_DOTTED);//左边框
        style1.setBorderRight(XSSFCellStyle.BORDER_THIN);//右边框
        style1.setBorderTop(XSSFCellStyle.BORDER_THIN);//上边框

        XSSFFont font1 = workbook.createFont();
        font1.setFontHeightInPoints((short) 15);
        font1.setBoldweight(XSSFFont.BOLDWEIGHT_NORMAL);
        style1.setFont(font1);
        style1.setAlignment(XSSFCellStyle.ALIGN_CENTER);//左右居中

        List<String> titleNames = new ArrayList<String>(colMap.keySet());
        List<String> colNames = new ArrayList<String>(colMap.values());
        //excel的列数
        int cols = titleNames.size();
        //合并第一行作为标题头
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, cols - 1));

        XSSFRow row = sheet.createRow(0);
        row.setHeightInPoints(25);
        XSSFCell row0cel = row.createCell(0);

        row0cel.setCellStyle(style1);
        row0cel.setCellValue(fileName);
        // 产生表格标题行
        row = sheet.createRow(1);
        for (int i = 0; i < titleNames.size(); i++) {
            sheet.setColumnWidth(i, 20 * 160);
            XSSFCell cell = row.createCell(i);
            cell.setCellStyle(style);
            XSSFRichTextString text = new XSSFRichTextString(titleNames.get(i));
            cell.setCellValue(text);
        }

        Iterator<T> it = contents.iterator();
        int rowNo = 2;
        while (it.hasNext()) {
            T rowContent = it.next();
            row = sheet.createRow(rowNo);
            rowNo++;

            for (int cellNo = 0; cellNo < colNames.size(); cellNo++) {

                Object value = null;
                try {
                    value = PropertyUtils.getSimpleProperty(rowContent, colNames.get(cellNo));
                } catch (Exception e) {
                    logger.error("读取字段出现错误", e);
                    continue;
                }
                if (value == null) {
                    continue;
                }
                if (value instanceof String) {
                    row.createCell(cellNo).setCellValue((String) value);
                } else if (value instanceof Long) {
                    row.createCell(cellNo).setCellValue((Long) value);
                } else if (value instanceof Integer) {
                    row.createCell(cellNo).setCellValue((Integer) value);
                } else if (value instanceof Date) {
                    row.createCell(cellNo).setCellValue(DateHelper.date2String((Date) value, DateHelper.DateFormatType.YearMonthDay_HourMinuteSecond));
                } else if (value instanceof BigDecimal) {
                    row.createCell(cellNo).setCellValue(((BigDecimal) value).toString());
                } else {
                    row.createCell(cellNo).setCellValue(value.toString());
                }
            }
        }
        if (hasTotal) {
            sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo, 1, cols - 1));
            totalName = totalName == null ? StringPool.BLANK : totalName;
            totalValue = totalValue == null ? StringPool.BLANK : totalValue;
            row = sheet.createRow(rowNo);
            row.setHeightInPoints(16);
            row.createCell(0).setCellValue(totalName);
            row.createCell(1).setCellValue(totalValue);
        }

        File file = null;
        FileOutputStream out = null;
        try {
            String tmpFolder = System.getProperty("java.io.tmpdir");
            if (tmpFolder != null) {
                file = new File(tmpFolder + File.separator + fileName + ".xlsx");
            } else {
                file = File.createTempFile(fileName, ".xlsx");
            }
            out = new FileOutputStream(file);
            workbook.write(out);
            out.close();
        } catch (IOException e) {
            logger.info("生成excel文件出错", e);
        } finally {
            IOUtils.closeQuietly(out);
        }

        return file;
    }

}
