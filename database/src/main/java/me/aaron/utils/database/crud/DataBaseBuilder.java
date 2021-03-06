package me.aaron.utils.database.crud;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.Data;
import me.aaron.utils.database.crud.impl.MSSQLDataBaseType;
import me.aaron.utils.database.crud.impl.MySQLDataBaseType;
import me.aaron.utils.database.crud.impl.OracleDataBaseType;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA
 *
 * @ClassName DataBaseBuilder
 * @Description jdbc 简单封装
 * @Author wuhengzhen
 * @Date 2019-11-20 11:32
 */
@Data
public class DataBaseBuilder {
    DataBaseConnectType dataBaseType;

    int initialSize = 1;
    int minIdle = 1;
    int maxActive = 20;
    int maxWait = 60000;
    boolean usePool = false;

    public DataBaseBuilder databaseType(DataBaseConnectType dataBaseType) {
        this.dataBaseType = dataBaseType;
        return this;
    }

    public DataBaseBuilder mysql(String host, String port, String database, String username, String password) {
        this.dataBaseType = new MySQLDataBaseType(host, port, database, username, password);
        return this;
    }

    public DataBaseBuilder oracle(String host, String port, String serviceName, String username, String password) {
        this.dataBaseType = new OracleDataBaseType(host, port, true, serviceName, username, password);
        return this;
    }

    public DataBaseBuilder oracle(String host, String port, boolean useServiceName, String database, String username, String password) {
        this.dataBaseType = new OracleDataBaseType(host, port, useServiceName, database, username, password);
        return this;
    }

    public DataBaseBuilder mssql(String host, String port, String database, String username, String password) {
        this.dataBaseType = new MSSQLDataBaseType(host, port, database, username, password);
        return this;
    }

    public DataBaseBuilder mssql(String host, String port, String database, String username, String password, String schema) {
        this.dataBaseType = new MSSQLDataBaseType(host, port, database, username, password, schema);
        return this;
    }

    public DataBaseBuilder smartInit(String url, String username, String password) {
        if (url.indexOf("sqlserver") > -1) {
            this.dataBaseType = new MSSQLDataBaseType(url, username, password);
        } else if (url.indexOf("mysql") > -1) {
            this.dataBaseType = new MySQLDataBaseType(url, username, password);
        } else if (url.indexOf("oracle") > -1) {
            this.dataBaseType = new OracleDataBaseType(url, username, password);
        } else {
            throw new RuntimeException("无法解析url");
        }
        return this;
    }

    /**
     * 连接池设置
     *
     * @param initialSize
     * @param minIdle
     * @param maxActive
     * @param maxWait
     * @return
     */
    public DataBaseBuilder pool(int initialSize, int minIdle, int maxActive, int maxWait) {
        this.initialSize = initialSize;
        this.minIdle = minIdle;
        this.maxActive = maxActive;
        this.maxWait = maxWait;
        this.usePool = true;
        return this;
    }

    public DataSource createDataSource() {
        DruidDataSource ds = new DruidDataSource();
        ds.setUrl(dataBaseType.getUrl());
        ds.setUsername(dataBaseType.getUsername());
        ds.setPassword(dataBaseType.getPassword());
        ds.setInitialSize(initialSize);
        ds.setMinIdle(minIdle);
        ds.setMaxActive(maxActive);
        ds.setMaxWait(maxWait);
        ds.setTimeBetweenEvictionRunsMillis(60000);
        ds.setMinEvictableIdleTimeMillis(300000);
        ds.setTestWhileIdle(true);
        ds.setTestOnBorrow(false);
        ds.setTestOnReturn(false);
        ds.setPoolPreparedStatements(true);
        ds.setMaxPoolPreparedStatementPerConnectionSize(20);
        ds.setValidationQuery(dataBaseType.getValidQuery());
        return ds;
    }

    public Connection createConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(dataBaseType.getUrl(), dataBaseType.getUsername(), dataBaseType.getPassword());
        return connection;
    }

    public DataBase build() {
        try {
            Class.forName(dataBaseType.getDriver());

            DataBase dataBase = new DataBase();
            dataBase.setDataBaseConnectType(dataBaseType);

            if (usePool) {
                dataBase.setDataSource(createDataSource());
            }

            return dataBase;

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
