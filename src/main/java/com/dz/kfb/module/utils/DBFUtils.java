package com.dz.kfb.module.utils;


import com.linuxense.javadbf.DBFDataType;
import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFReader;
import com.linuxense.javadbf.DBFWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DBFUtils {

    private static Logger logger = LoggerFactory.getLogger(DBFUtils.class);


    /**
     * 在存在的dbf中写记录， 传入的记录需要人为自己定义好规范
     * @param file
     * @param values
     */
    public static void writeDBF(File file, Object[] values) {
        DBFWriter writer = new DBFWriter(file, Charset.forName("GBK"));
        writer.addRecord(values);
        writer.close();
    }

    /**
     * 新生成一个dbf再写入记录
     * @param file
     * @param headParams
     * @param values
     */
    public static void writeDBF(File file, Map<String, Object[]> headParams, Object[] values) {
        try {
            if (!file.exists()) {
                file.createNewFile();
                DBFWriter writer = new DBFWriter(file, Charset.forName("GBK"));
                DBFField[] fields = new DBFField[headParams.size()];
                Object[] columnNames = headParams.get("columnNames");
                Object[] columnDataType = headParams.get("columnDataType");
                Object[] columnDataLength = headParams.get("columnDataLength");
                for (int i = 0; i < columnNames.length; i++) {
                    fields[i] = new DBFField(String.valueOf(columnNames[i]), transDataType(columnDataType[i]), Integer.parseInt(String.valueOf(columnDataLength[i])));
                }
                writer.setFields(fields);
                writer.addRecord(values);
                writer.close();
            } else {
                writeDBF(file, values);
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (file.exists()) {
                file.delete();
            }
        }

    }

    private static DBFDataType transDataType(Object object) throws Exception {
        if ("string".equals(String.valueOf(object))) {
            return DBFDataType.CHARACTER;
        } else if ("int".equals(String.valueOf(object))) {
            return DBFDataType.NUMERIC;
        } else if ("double".equals(String.valueOf(object))) {
            return DBFDataType.DOUBLE;
        } else if ("long".equals(String.valueOf(object))) {
            return DBFDataType.LONG;
        } else if ("date".equals(String.valueOf(object))) {
            return DBFDataType.DATE;
        } else {
            logger.info("{}类型暂不支持", String.valueOf(object));
            throw new Exception("暂不支持的列数据类型");
        }
    }

    public static List<Object[]> readDBF(File file, boolean readHead) throws Exception {
        FileInputStream fis = null;
        List<Object[]> result = new ArrayList<>();
        try {
            if (!file.exists()) {
                throw new Exception("读取的文件【" + file.getName() + "】不存在");
            }
            fis = new FileInputStream(file);
            DBFReader reader = new DBFReader(fis, Charset.forName("GBK"));
            Object[] row;
            if (readHead) {
                String[] headNames = new String[reader.getFieldCount()];
                for (int i = 0; i < reader.getFieldCount(); i++) {
                    headNames[i] = String.valueOf(reader.getField(i).getName());
                }
                row = headNames;
                result.add(row);
            }

            while ((row = reader.nextRecord()) != null) {
                result.add(row);
            }
            return result;
        } finally {
            if (fis != null) {
                fis.close();
            }
        }
    }


}
