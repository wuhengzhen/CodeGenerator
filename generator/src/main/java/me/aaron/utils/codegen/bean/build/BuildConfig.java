package me.aaron.utils.codegen.bean.build;

import lombok.Data;
import me.aaron.utils.database.metadata.bean.TableMeta;

/**
 * Created with IntelliJ IDEA
 *
 * @ClassName BuildConfig
 * @Description
 * @Author wuhengzhen
 * @Date 2019-11-20 13:09
 */
@Data
public class BuildConfig {

    public static final String ftlPath = "/schemas/%s/tpl";
    public static final String buildJsonPath = "/schemas/%s/build.json";

    String schema;
    String output;

    Copyright copyright;
    TableMeta table;
    JavaFiles javas;

    public static BuildConfig create(){
        return new BuildConfig();
    }
}
