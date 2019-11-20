package me.aaron.utils.database.metadata;

import lombok.Data;
import me.aaron.utils.database.crud.DataBaseConnectType;
import me.aaron.utils.database.metadata.bean.TableMeta;
import me.aaron.utils.database.metadata.impl.MSSQLAnalyze;
import me.aaron.utils.database.metadata.impl.MySQLAnalyze;
import me.aaron.utils.database.metadata.impl.OracleAnalyze;

import java.util.List;

/**
 * Created with IntelliJ IDEA
 *
 * @ClassName DataBaseMetaData
 * @Description
 * @Author wuhengzhen
 * @Date 2019-11-20 11:29
 */
@Data
public class DataBaseMetaData {

    DataBaseConnectType connectType;

    public static DataBaseMetaDataBuilder builder() {
        return new DataBaseMetaDataBuilder();
    }


    public static final String TYPE_TABLE = "TABLE";
    public static final String TYPE_VIEW = "VIEW";

    public List<TableMeta> listTable() {
        Analyze analyze = getAnalyze(connectType.getDataBaseType());
        return analyze.list(connectType, connectType.getCatalog(), connectType.getSchema(), new String[]{TYPE_TABLE, TYPE_VIEW});
    }

    public TableMeta analyze(String tableName, List<String> excludeColumns) {
        Analyze analyze = getAnalyze(connectType.getDataBaseType());
        return analyze.analyze(connectType, connectType.getCatalog(), connectType.getSchema(), tableName, excludeColumns);
    }

    public TableMeta analyze(String tableName) {
        return analyze(tableName, null);
    }

    public Analyze getAnalyze(String type) {
        Analyze analyze = null;
        if (connectType.getDataBaseType().equals(DataBaseConnectType.MYSQL)) {
            analyze = new MySQLAnalyze();
        }

        if (connectType.getDataBaseType().equals(DataBaseConnectType.ORACLE)) {
            analyze = new OracleAnalyze();
        }

        if (connectType.getDataBaseType().equals(DataBaseConnectType.MSSQL)) {
            analyze = new MSSQLAnalyze();
        }
        return analyze;
    }

}
