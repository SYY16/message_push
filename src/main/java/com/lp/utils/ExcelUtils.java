package com.lp.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description: Excel工具类
 * @Author: 师岩岩
 * @Date: 2019/5/8 17:37
 */
public class ExcelUtils {

    private static final Logger logger = LoggerFactory.getLogger(ExcelUtils.class);

    /**
     * 导出Excel
     */
    public static void exportExcel(List<String> messageList) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("信息表");
        //设置要导出的文件的名字
        String fileName = System.currentTimeMillis() + ".xls";
        String[] headers = {"消息记录"};
        HSSFRow row = sheet.createRow(0);
        //添加表头
        for (int i = 0; i < headers.length; i++) {
          HSSFCell cell = row.createCell(i);
          HSSFRichTextString text = new HSSFRichTextString(headers[i]);
          cell.setCellValue(text);
        }
        //在表中数据放入对应的列
        for (int i = 0; i < messageList.size(); i++) {
          HSSFRow row1 = sheet.createRow(i + 1);
          row1.createCell(0).setCellValue(messageList.get(i));
        }
        String filePath = "C:\\wsLog";
        File file = new File(filePath);
        if (!file.exists()) {
          file.mkdir();
        }
        OutputStream out = null;
        try {
          out = new FileOutputStream(filePath + "\\" + fileName + ".xlsx");
          workbook.write(out);
          out.close();
          logger.info("[===> Excel文件已输出");
        } catch (IOException e) {
          logger.error("[===> 导出Excel文件时发生异常，error{}]",e.getStackTrace());
          throw new RuntimeException("导出Excel文件时发生IO异常");
        }
    }
}
