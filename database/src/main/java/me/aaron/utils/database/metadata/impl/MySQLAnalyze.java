package me.aaron.utils.database.metadata.impl;

import me.aaron.utils.database.crud.DataBaseConnectType;
import me.aaron.utils.database.metadata.Analyze;
import me.aaron.utils.database.metadata.FieldType;

import java.sql.JDBCType;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA
 *
 * @ClassName MySQLAnalyze
 * @Description
 * @Author wuhengzhen
 * @Date 2019-11-20 11:42
 */
public class MySQLAnalyze extends Analyze {

    @Override
    public Properties getConnectionProperties(DataBaseConnectType connectType) {
        /**
         * mysql  需要 useInformationSchema=true 和 remarks=true
         * oracle 需要 remarks=true
         * 来源： http://www.tinygroup.org/docs/6638819901697136844
         */
        Properties props = new Properties();
        props.setProperty("user", connectType.getUsername());
        props.setProperty("password", connectType.getPassword());
        // 设置可以获取remarks信息
        props.setProperty("remarks", "true");
        // 设置可以获取tables remarks信息
        props.setProperty("useInformationSchema", "true");
        return props;
    }

    /**
     * 设置字段类型 MySql数据类型
     * 来源：https://dev.mysql.com/doc/connector-j/5.1/en/connector-j-reference-type-conversions.html
     *
     * @param origin 列类型字符串
     * @return
     */
    public FieldType getFieldType(String origin) {
        String columnType = origin.toLowerCase();

        if (columnType.startsWith("varchar")
                || columnType.startsWith("nvarchar")
                || columnType.startsWith("char")
                || columnType.startsWith("tinytext")
                || columnType.startsWith("text")
                || columnType.startsWith("mediumtext")
                || columnType.startsWith("longtext")
        ) {
            return FieldType.of(false, null, "String", JDBCType.VARCHAR, origin);
        }

        if (columnType.startsWith("tinyblob")
                || columnType.startsWith("blob")
                || columnType.startsWith("mediumblob")
                || columnType.startsWith("longblob")
        ) {
            return FieldType.of(false, null, "byte[]", JDBCType.BINARY, origin);
        }

        if (columnType.startsWith("datetime")
                || columnType.startsWith("date")
                || columnType.startsWith("timestamp")
                || columnType.startsWith("time")
                || columnType.startsWith("year")
        ) {
            return FieldType.of(true, "import java.util.Date;", "Date", JDBCType.DATE, origin);
        }

        if (columnType.startsWith("bit")
                || columnType.startsWith("int")
                || columnType.startsWith("tinyint")
                || columnType.startsWith("smallint")
                || columnType.startsWith("bool")
                || columnType.startsWith("mediumint")
        ) {
            return FieldType.of(false, null, "Integer", JDBCType.INTEGER, origin);
        }

        if (columnType.startsWith("bigint")) {
            return FieldType.of(false, null, "Long", JDBCType.INTEGER, origin);
        }


        if (columnType.startsWith("float")) {
            return FieldType.of(false, null, "Float", JDBCType.FLOAT, origin);
        }

        if (columnType.startsWith("double")) {
            return FieldType.of(false, null, "Double", JDBCType.DOUBLE, origin);
        }

        if (columnType.startsWith("decimal")) {
            return FieldType.of(false, "import java.math.BigDecimal;", "BigDecimal", JDBCType.DECIMAL, origin);
        }

        switch (columnType) {
        }


        return FieldType.error(origin);
    }
}
