package com.selenium;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelRead {
    public List<String[]> getValues(String filePath) {
        String values = null;
        List<String[]> list=new ArrayList<>();
        try {
            InputStream is = new FileInputStream(filePath);
            // 构造 XSSFWorkbook 对象，strPath 传入文件路径
            XSSFWorkbook xwb = new XSSFWorkbook(is);
            // 读取第一章表格内容
            XSSFSheet sheet = xwb.getSheetAt(0);
            // 定义 row、cell
            XSSFRow row;
            String cell;
            // 循环输出表格中的内容
            for (int i = sheet.getFirstRowNum() + 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                row = sheet.getRow(i);
                String[] arr=new String[row.getPhysicalNumberOfCells()];
                    for (int j = row.getFirstCellNum(); j < row.getPhysicalNumberOfCells(); j++) {
                        // 通过 row.getCell(j).toString() 获取单元格内容，
                    cell = row.getCell(j).toString();
                    arr[j]=cell;

                }
                list.add(arr);
            }
        } catch (Exception e) {
            System.out.println("已运行xlRead() : " + e);
        }
        return list;
    }

    public static void main(String args[]) {
        String filePath = "C:\\Users\\user\\Documents\\WeChat Files\\wxid_o2t83pvwj6o712\\Files\\789.xlsx";
        ExcelRead excelRead = new ExcelRead();
        excelRead.getValues(filePath);
    }
}
