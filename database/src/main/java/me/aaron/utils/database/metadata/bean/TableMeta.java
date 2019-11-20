package me.aaron.utils.database.metadata.bean;

import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA
 *
 * @ClassName TableMeta
 * @Description
 * @Author wuhengzhen
 * @Date 2019-11-20 11:39
 */
@Data
public class TableMeta {

    String databaseType;
    String catalog;
    String schema;
    String name;
    String alias;
    String type;
    String comment;

    List<PrimaryKeyMeta> primaryKeys;
    List<ColumnMeta> columns;


    public static TableMeta of(String catalog, String schema, String name, String type, String comment, String databaseType) {

        TableMeta tableMeta = new TableMeta();

        tableMeta.setCatalog(catalog);
        tableMeta.setSchema(schema);
        tableMeta.setName(name);
        tableMeta.setType(type);
        tableMeta.setComment(comment);
        tableMeta.setDatabaseType(databaseType);
        return tableMeta;
    }

    public String createInsertSQL() {
        return createInsertSQL(null);
    }

    public String createInsertSQL(List<String> excludeColumns) {

        if (excludeColumns != null) {
            excludeColumns = excludeColumns.stream().map(v -> v.toUpperCase()).collect(Collectors.toList());
        }

        StringBuffer firstPart = new StringBuffer();
        StringBuffer secondPart = new StringBuffer();
        for (ColumnMeta columnMeta : columns) {

            String columnName = columnMeta.getName().toUpperCase();
            if (excludeColumns != null && excludeColumns.contains(columnName)) {
                continue;
            }

            firstPart.append(columnName + ",");
            secondPart.append("?,");
        }

        if (firstPart.length() == 0) {
            throw new RuntimeException("TableMeta create sql error，column size is zero!");
        }

        StringBuffer sql = new StringBuffer();
        sql.append("insert into ")
                .append(name.toUpperCase())
                .append(" ( ")
                .append(firstPart.substring(0, firstPart.length() - 1))
                .append(" ) ")
                .append(" values ( ")
                .append(secondPart.substring(0, secondPart.length() - 1))
                .append(" ) ");

        return sql.toString();
    }
}