package me.aaron.utils.database.metadata;

import me.aaron.utils.database.crud.DataBase;
import me.aaron.utils.database.crud.DataBaseConnectType;
import me.aaron.utils.database.crud.impl.MSSQLDataBaseType;
import me.aaron.utils.database.crud.impl.MySQLDataBaseType;
import me.aaron.utils.database.crud.impl.OracleDataBaseType;

/**
 * Created with IntelliJ IDEA
 *
 * @ClassName DataBaseMetaDataBuilder
 * @Description
 * @Author wuhengzhen
 * @Date 2019-11-20 11:29
 */
public class DataBaseMetaDataBuilder extends DataBase {

    DataBaseConnectType dataBaseType;

    public DataBaseMetaDataBuilder databaseType(DataBaseConnectType dataBaseType) {
        this.dataBaseType = dataBaseType;
        return this;
    }

    public DataBaseMetaDataBuilder mysql(String host, String port, String database, String username, String password) {
        this.dataBaseType = new MySQLDataBaseType(host, port, database, username, password);
        return this;
    }

    public DataBaseMetaDataBuilder oracle(String host, String port, String serviceName, String username, String password) {
        this.dataBaseType = new OracleDataBaseType(host, port, true, serviceName, username, password);
        return this;
    }

    public DataBaseMetaDataBuilder mssql(String host, String port, String database, String username, String password) {
        this.dataBaseType = new MSSQLDataBaseType(host, port, database, username, password);
        return this;
    }

    public DataBaseMetaDataBuilder mssql(String host, String port, String database, String username, String password, String schema) {
        this.dataBaseType = new MSSQLDataBaseType(host, port, database, username, password, schema);
        return this;
    }

    public DataBaseMetaDataBuilder smartInit(String url, String username, String password) {
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

    public DataBaseMetaData build() {
        DataBaseMetaData metaData = new DataBaseMetaData();
        metaData.setConnectType(this.dataBaseType);
        return metaData;
    }

}
