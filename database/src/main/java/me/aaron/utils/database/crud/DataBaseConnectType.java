package me.aaron.utils.database.crud;

/**
 * Created with IntelliJ IDEA
 *
 * @Description
 * @Author wuhengzhen
 * @Date 2019-11-20 11:33
 */
public interface DataBaseConnectType {

    public static final String MSSQL = "mssql";
    public static final String MYSQL = "mysql";
    public static final String ORACLE = "oracle";
    public static final String DB2 = "db2";
    public static final String SQLITE = "sqlite";

    String getDataBaseType();

    String getDriver();

    String getUrl();

    String getUsername();

    String getPassword();

    String getValidQuery();

    /**
     * Catalog和Schema都属于抽象概念，主要用来解决命名冲突问题
     * 数据库对象表的全限定名可表示为：Catalog.Schema.表名
     * | 供应商        | Catalog支持                       | Schema支持                 |
     * | ------------- | --------------------------------- | -------------------------- |
     * | Oracle        | 不支持                            | Oracle User ID            |
     * | MySQL         | 数据库名                          | 不支持                     |
     * | MS SQL Server | 数据库名                          | 对象属主名，2005版开始有变    |
     * | DB2           | 指定数据库对象时，Catalog部分省略    | Catalog属主名              |
     *
     * @return
     */
    String getCatalog();

    String getSchema();
}
