package me.aaron.utils.database.metadata;

import lombok.Data;

import java.sql.JDBCType;
import java.sql.SQLType;

/**
 * Created with IntelliJ IDEA
 *
 * @ClassName FieldType
 * @Description 封装字段类型
 * @Author wuhengzhen
 * @Date 2019-11-20 11:28
 */
@Data
public class FieldType {
    boolean isDate = false;
    String javaPackage;
    String javaType;
    SQLType sqlType;
    String origin;

    public static FieldType of(boolean isDate, String javaPackage, String javaType, SQLType sqlType, String origin) {
        FieldType t = new FieldType();
        t.setDate(isDate);
        t.setJavaPackage(javaPackage);
        t.setJavaType(javaType);
        t.setSqlType(sqlType);
        t.setOrigin(origin);
        return t;
    }

    public static FieldType error(String origin) {
        FieldType t = new FieldType();
        t.setDate(false);
        t.setJavaPackage(null);
        t.setJavaType(null);
        t.setSqlType(JDBCType.OTHER);
        t.setOrigin(origin);
        return t;
    }
}
