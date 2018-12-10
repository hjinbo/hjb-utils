package com.dz.kfb.module.demo.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSONObject;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ReadExcel {

    static Logger logger = LoggerFactory.getLogger(ReadExcel.class);

    public static List<List<Object>> readExcel(File file) throws IOException {
        String fileName = file.getName();
        String extension = fileName.lastIndexOf(".") == -1 ? "" : fileName.substring(fileName.lastIndexOf(".") + 1);
        if ("xls".equals(extension)) {
            logger.info("读取文件【{}】", file.getName());
            return read2003Excel(file, true, new int[]{0, 1}, 0);
        } else if ("xlsx".equals(extension)) {
            logger.info("读取文件【{}】", file.getName());
            return read2007Excel(file, true, new int[]{0, 1}, 0);
        } else {
            throw new IOException("不支持的文件类型");
        }
    }

    private static List<List<Object>> read2003Excel(File file, boolean readFirstRow, int[] needReadColumns, int sheetIndex) throws IOException {
        List<List<Object>> list = new ArrayList<>();
        HSSFWorkbook hwb = new HSSFWorkbook(new FileInputStream(file));
        HSSFSheet sheet = hwb.getSheetAt(sheetIndex);
        HSSFRow row;
        for (int rowCounter = 0; rowCounter <= sheet.getLastRowNum(); rowCounter++) {
            // 每一个row对应一个rowList
            List<Object> rowList = new ArrayList<>();
            if (!readFirstRow) {
                if (rowCounter == 0) {
                    continue;
                }
                row = sheet.getRow(rowCounter);
                for (int i = 0; i < needReadColumns.length; i++) {
                    rowList.add(dataTypeTrans(row.getCell(needReadColumns[i])));
                }
            } else {
                // 读取表头文件
                row = sheet.getRow(rowCounter);
                for (int i = 0; i < needReadColumns.length; i++) {
                    rowList.add(dataTypeTrans(row.getCell(needReadColumns[i])));
                }
            }
            // 到此将每一个cell放到rowList中了
            list.add(rowList);
        }
        return list;
    }

    private static String dataTypeTrans(Cell cell) {
        CellType type = cell.getCellTypeEnum();
        if (type == CellType.NUMERIC) {
            DecimalFormat df = new DecimalFormat("0");
            return String.valueOf(df.format(cell.getNumericCellValue()));
        } else if (type == CellType.BOOLEAN) {
            return String.valueOf(cell.getBooleanCellValue());
        } else if (type == CellType.STRING) {
            return cell.getStringCellValue();
        } else if (type == CellType.ERROR) {
            return String.valueOf(cell.getErrorCellValue());
        } else if (type == CellType.FORMULA) {
            logger.info("{}类型暂不支持转成字符串", type);
            return "类型不支持转字符串";
        } else if (type == CellType.BLANK) {
            return "";
        } else {
            logger.info("不知名的cell类型: {}", type);
            return "不知名的cell类型";
        }
    }

    public static String excelResultTransToJson(List<List<Object>> excelList, String jsonModel) {
        StringBuilder sb = new StringBuilder();
        List<Object> firstRow = new ArrayList<>();

        for (int i = 0; i < excelList.size(); i++) {
            JSONObject obj = JSONObject.parseObject(jsonModel);
            Map<String, String> map = new HashMap<>();
            for (String str : obj.keySet()) {
                map.put(str, String.valueOf(obj.get(str)));
            }
            if (i == 0) {
                firstRow = excelList.get(0); // 读取表头时该方法才生效
                logger.info("表头：{}", firstRow);
            } else {

                List<Object> rows = excelList.get(i);
                for (int index = 0; index < firstRow.size(); index++) {
                    Object o = firstRow.get(index);
                    if (map.containsKey(String.valueOf(o))) {
                        map.put(String.valueOf(o), String.valueOf(rows.get(index)));
                    }
                }

            }
            sb.append(JSONUtils.toJSONString(map)).append("&");
        }
        return sb.toString().substring(0, sb.toString().length() - 1);
    }

    private static List<List<Object>> read2007Excel(File file, boolean readFirstRow, int[] needReadColumns, int sheetIndex) throws IOException {
        List<List<Object>> list = new ArrayList<>();
        XSSFWorkbook xwb = new XSSFWorkbook(new FileInputStream(file));
        // 读取第一章表格内容
        XSSFSheet sheet = xwb.getSheetAt(sheetIndex);
        XSSFRow row;
        for (int rowCounter = 0; rowCounter <= sheet.getLastRowNum(); rowCounter++) {
            // 每一个row对应一个rowList
            List<Object> rowList = new ArrayList<>();
            if (!readFirstRow) {
                if (rowCounter == 0) {
                    continue;
                }
                row = sheet.getRow(rowCounter);
                for (int i = 0; i < needReadColumns.length; i++) {
                    rowList.add(dataTypeTrans(row.getCell(needReadColumns[i])));
                }
            } else {
                // 读取表头文件
                row = sheet.getRow(rowCounter);
                for (int i = 0; i < needReadColumns.length; i++) {
                    rowList.add(dataTypeTrans(row.getCell(needReadColumns[i])));
                }
            }
            // 到此将每一个cell放到rowList中了
            list.add(rowList);
        }
        return list;
    }

    public static void main(String[] args) {
        try {
            List<List<Object>> list=readExcel(new File("D:\\test\\test.xlsx"));
            String jsonModel = "{\"YWLB\": \"02\", \n" +
                    "\"KHMC\": \"小黑\", \n" +
                    "\"ZJLB\": \"\", \n" +
                    "\"ZJDM\": \"\", \n" +
                    "\"SJHM\": \"13911069173\",\n" +
                    "\"BRANCHNO\": \"9999\",\n" +
                    "\"FUNDACCOUNT\": \"123\",\n" +
                    "\"ISHISTORY\": \"1\"}";

            logger.info("" + list);
//            String resultJson = excelResultTransToJson(list, jsonModel);
//            for (String s : resultJson.split("&")) {
//                logger.info("" + s);
//            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}