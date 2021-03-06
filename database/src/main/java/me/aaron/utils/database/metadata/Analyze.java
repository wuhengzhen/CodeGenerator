package me.aaron.utils.database.metadata;

import me.aaron.utils.database.crud.DataBaseConnectType;
import me.aaron.utils.database.metadata.bean.ColumnMeta;
import me.aaron.utils.database.metadata.bean.PrimaryKeyMeta;
import me.aaron.utils.database.metadata.bean.TableMeta;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA
 *
 * @ClassName Analyze
 * @Description
 * @Author wuhengzhen
 * @Date 2019-11-20 11:29
 */
public abstract class Analyze {


    public abstract Properties getConnectionProperties(DataBaseConnectType connectType);

    // 数据库类型与 java 类型映射
    // https://blog.csdn.net/weixin_34195546/article/details/87611601
    public abstract FieldType getFieldType(String columnType);

    public TableMeta analyze(DataBaseConnectType connectType, String catalog, String schema, String tableName, List<String> excludeColumns) {

        tableName = tableName.toUpperCase();
        try {
            Class.forName(connectType.getDriver());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try (Connection connection = DriverManager.getConnection(connectType.getUrl(), getConnectionProperties(connectType))) {
            TableMeta tableMeta = new TableMeta();

            DatabaseMetaData metadata = connection.getMetaData();

            /**
             * 来源：https://blog.csdn.net/hekewangzi/article/details/41390155
             *
             * types 典型的类型是 "TABLE"、"VIEW"、"SYSTEM TABLE"、"GLOBAL TEMPORARY"、"LOCAL TEMPORARY"、"ALIAS" 和 "SYNONYM"。
             * 来源：https://www.cnblogs.com/lbangel/p/3487796.html
             */
            String[] types = {"TABLE", "VIEW"};

            ResultSet tableInfo = metadata.getTables(catalog, schema, tableName, types);
            if (tableInfo.next()) {
                tableMeta = TableMeta.of(
                        tableInfo.getString("TABLE_CAT"),
                        tableInfo.getString("TABLE_SCHEM"),
                        tableInfo.getString("TABLE_NAME"),
                        tableInfo.getString("TABLE_TYPE"),  // 表类型
                        tableInfo.getString("REMARKS"),     // 表注释
                        connectType.getDataBaseType()
                );
            }

            List<PrimaryKeyMeta> primaryKeyMetas = new ArrayList<>();
            Set<String> primaryKeySet = new HashSet<>();
            ResultSet primaryKeyResultSet = metadata.getPrimaryKeys(catalog, schema, tableName);
            while (primaryKeyResultSet.next()) {
                String primaryKeyColumnName = primaryKeyResultSet.getString("COLUMN_NAME");
                String pkName = primaryKeyResultSet.getString("PK_NAME");
                short keySeq = primaryKeyResultSet.getShort("KEY_SEQ");

                PrimaryKeyMeta primaryKeyMeta = PrimaryKeyMeta.of(primaryKeyColumnName, keySeq);
                primaryKeyMetas.add(primaryKeyMeta);
                primaryKeySet.add(primaryKeyColumnName.toUpperCase());
            }

            List<ColumnMeta> columnMetas = new ArrayList<>();
            if (excludeColumns != null) {
                excludeColumns = excludeColumns.stream().map(v -> v.toUpperCase()).collect(Collectors.toList());
            }
            ResultSet columnsInfo = metadata.getColumns(catalog, schema, tableName, "%");
            while (columnsInfo.next()) {

                String columnName = columnsInfo.getString("COLUMN_NAME");
                String columnType = columnsInfo.getString("TYPE_NAME");

                columnType = columnType.toLowerCase();
                columnName = columnName.toUpperCase();

                if (excludeColumns != null && excludeColumns.contains(columnName)) {
                    continue;
                }

                int size = columnsInfo.getInt("COLUMN_SIZE");
                int digits = columnsInfo.getInt("DECIMAL_DIGITS");
                int nullable = columnsInfo.getInt("NULLABLE");
                String remarks = columnsInfo.getString("REMARKS");

                FieldType fieldType = getFieldType(columnType);

                boolean isPrimaryKey = primaryKeySet.contains(columnName);

                columnMetas.add(ColumnMeta.of(columnName.toUpperCase(), columnType, remarks, size, digits, nullable == 1,
                        isPrimaryKey, fieldType.getJavaType(), fieldType.getJavaPackage(), fieldType.isDate(), fieldType.getSqlType()));
            }

            tableMeta.setColumns(columnMetas);
            tableMeta.setPrimaryKeys(primaryKeyMetas);

            return tableMeta;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<TableMeta> list(DataBaseConnectType connectType, String catalog, String schema, String[] types) {
        try {
            Class.forName(connectType.getDriver());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try (Connection connection = DriverManager.getConnection(connectType.getUrl(), getConnectionProperties(connectType))) {


            DatabaseMetaData metadata = connection.getMetaData();
            ResultSet tableInfo = metadata.getTables(catalog, schema, "%", types);
            List<TableMeta> list = new ArrayList<>();
            while (tableInfo.next()) {
                TableMeta tableMeta = TableMeta.of(
                        tableInfo.getString("TABLE_CAT"),
                        tableInfo.getString("TABLE_SCHEM"),
                        tableInfo.getString("TABLE_NAME"),
                        tableInfo.getString("TABLE_TYPE"),  // 表类型
                        tableInfo.getString("REMARKS"),     // 表注释
                        connectType.getDataBaseType()
                );

                list.add(tableMeta);
            }

            return list;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;


    }

}
