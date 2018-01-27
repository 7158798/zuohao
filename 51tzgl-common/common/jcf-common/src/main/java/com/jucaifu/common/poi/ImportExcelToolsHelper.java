package com.jucaifu.common.poi;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by zh on 16-8-24.
 */
public class ImportExcelToolsHelper {

    /**
     * 总行数
     */

    private int totalRows = 0;

    /**
     * 总列数
     */

    private int totalCells = 3;

    /**
     * @描述：得到总列数
     * @author zhijun
     * @参数：@return
     * @返回值：int
     */

    public int getTotalCells() {

        return totalCells;

    }

    /**
     * @描述：根据文件名读取excel文件
     * @author zhijun
     * @参数：@param filePath 文件完整路径
     * @参数：@return
     * @返回值：List
     */

    public List<List<String>> read(String filePath) {

        List<List<String>> dataLst = new ArrayList<>();

        InputStream is = null;

        try {
            /** 判断文件的类型，是2003还是2007 */

            boolean isExcel2003 = true;

            if (WDWUtil.isExcel2007(filePath)) {

                isExcel2003 = false;

            }

            /** 调用本类提供的根据流读取的方法 */

            File file = new File(filePath);

            is = new FileInputStream(file);

            dataLst = read(is, isExcel2003);

            is.close();

        } catch (Exception ex) {

            ex.printStackTrace();

        } finally {

            if (is != null) {

                try {

                    is.close();

                } catch (IOException e) {

                    is = null;

                    e.printStackTrace();

                }

            }

        }

        /** 返回最后读取的结果 */

        return dataLst;

    }






    public List<Object> readAllSheet(InputStream inputStream,Boolean isExcel2003){
        List<Object> result = new ArrayList<>();

        List<List<String>> dataLst = null;

        try {

            /** 根据版本选择创建Workbook的方式 */

            Workbook wb ;

            if (isExcel2003) {
                wb = new HSSFWorkbook(inputStream);
            } else {
                wb = new XSSFWorkbook(inputStream);
            }
            int sheets = wb.getNumberOfSheets();
            for(int i = 0;i<sheets;i++){
                dataLst = read(wb, isExcel2003,i);
                result.add(dataLst);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  result;

    }

    /**
     * @描述：根据流读取Excel文件
     * @author zhijun
     * @参数：@param inputStream
     * @参数：@param isExcel2003
     * @参数：@return
     * @返回值：List
     */

    public List<List<String>> read(InputStream inputStream, boolean isExcel2003) {

        List<List<String>> dataLst = null;

        try {

            /** 根据版本选择创建Workbook的方式 */

            Workbook wb ;

            if (isExcel2003) {
                wb = new HSSFWorkbook(inputStream);
            } else {
                wb = new XSSFWorkbook(inputStream);
            }
            dataLst = read(wb, isExcel2003,0);

        } catch (IOException e) {

            e.printStackTrace();

        }

        return dataLst;
    }

    /**
     * @描述：读取数据
     * @author zhijun
     * @参数：@param Workbook
     * @参数：@return
     * @返回值：List<List<String>>
     */

    private List<List<String>> read(Workbook wb, Boolean isExcel2003,int sheetNumber) {

        List<List<String>> dataLst = new ArrayList<>();

        /** 得到第一个shell */

        Sheet sheet = wb.getSheetAt(sheetNumber);

        /** 得到Excel的行数 */

        this.totalRows = sheet.getPhysicalNumberOfRows();

        /** 得到Excel的列数 */

        if (this.totalRows >= 1 && sheet.getRow(0) != null) {

            this.totalCells = sheet.getRow(0).getPhysicalNumberOfCells();

        }

        /** 循环Excel的行 */

        for (int r = 0; r < this.totalRows; r++) {

            Row row = sheet.getRow(r);

            if (row == null) {

                continue;

            }

            List<String> rowLst = new ArrayList<>();

            /** 循环Excel的列 */

            for (int c = 0; c < this.getTotalCells(); c++) {

                Cell cell = row.getCell(c);

                String cellValue = "";

                if (null != cell) {

                    if (cell.getCellType() == HSSFCell.CELL_TYPE_BOOLEAN) {
                        cellValue = String.valueOf(cell.getBooleanCellValue()).trim();
                    } else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                        DecimalFormat df = new DecimalFormat("#");
                        cellValue = df.format(BigDecimal.valueOf(cell.getNumericCellValue()));
                    } else {
                        cellValue = String.valueOf(cell.getStringCellValue()).trim();
                    }
                }

                rowLst.add(cellValue);
            }

            dataLst.add(rowLst);
        }
        return dataLst;
    }


}

