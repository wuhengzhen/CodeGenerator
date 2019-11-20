package me.aaron.utils.database.metadata.bean;

import lombok.Data;

import java.sql.SQLType;

/**
 * Created with IntelliJ IDEA
 *
 * @ClassName ColumnMeta
 * @Description
 * @Author wuhengzhen
 * @Date 2019-11-20 11:39
 */
@Data
public class ColumnMeta {

    String name;
    String alias;
    String type;
    String comment;

    int size;
    int digits;
    boolean nullable;
    boolean isPrimaryKey;

    Boolean isDate;
    String javaType;
    String javaImport;
    SQLType sqlType;


    public static ColumnMeta of(String name, String type, String comment, int size, int digits, boolean nullable, boolean isPrimaryKey, String javaType, String javaImport, Boolean isDate, SQLType sqlType){
        ColumnMeta columnMeta = new ColumnMeta();
        columnMeta.setName(name);
        columnMeta.setType(type);
        columnMeta.setComment(comment);
        columnMeta.setSize(size);
        columnMeta.setDigits(digits);
        columnMeta.setNullable(nullable);
        columnMeta.setPrimaryKey(isPrimaryKey);
        columnMeta.setJavaType(javaType);
        columnMeta.setJavaImport(javaImport);
        columnMeta.setIsDate(isDate);
        columnMeta.setSqlType(sqlType);
        return columnMeta;
    }
}
