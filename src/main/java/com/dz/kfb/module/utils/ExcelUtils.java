package com.dz.kfb.module.utils;

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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelUtils {

    private static Logger logger = LoggerFactory.getLogger(ExcelUtils.class);

    public static void main(String[] args) throws Exception {
        List<List<Object>> list = readExcel(new File("d:/test/test2.xlsx"), true, new int[]{0, 1}, 0);
        for (List<Object> rows : list) {
            for (Object o : rows) {
                System.out.print(o.toString() + "\t");
            }
            System.out.println();
        }
    }

    public static List<List<Object>> readExcel(File file, boolean readFirstRow, int[] needReadColumns, int sheetIndex) throws Exception {
        if (!file.exists()) {
            throw new Exception("找不到该文件");
        }
        String fileName = file.getName();
        // 获得文件扩展名
        String extension = fileName.lastIndexOf(".") == -1 ? "" : fileName
                .substring(fileName.lastIndexOf(".") + 1);
        if ("xls".equals(extension)) {
            logger.info("读取xls文件【{}】", file.getName());
            return read2003Excel(file, readFirstRow, needReadColumns, sheetIndex);
        } else if ("xlsx".equals(extension)) {
            logger.info("读取xlsx文件【{}】", file.getName());
            return read2007Excel(file, readFirstRow, needReadColumns, sheetIndex);
        } else {
            throw new Exception("不支持的文件后缀");
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

    private static List<List<Object>> read2007Excel(File file, boolean readFirstRow, int[] needReadColumns, int sheetIndex) throws IOException {
        List<List<Object>> list = new ArrayList<>();
        XSSFWorkbook xwb = new XSSFWorkbook(new FileInputStream(file));
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
}
