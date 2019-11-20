package me.aaron.utils.codegen;

import com.alibaba.fastjson.JSONObject;
import me.aaron.utils.codegen.bean.build.BuildConfig;
import me.aaron.utils.codegen.bean.build.JavaFile;
import me.aaron.utils.codegen.bean.build.JavaFiles;
import me.aaron.utils.codegen.generator.JavaGenerator;
import me.aaron.utils.codegen.util.GenUtil;
import me.aaron.utils.database.metadata.DataBaseMetaData;
import me.aaron.utils.database.metadata.bean.ColumnMeta;
import me.aaron.utils.database.metadata.bean.TableMeta;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created with IntelliJ IDEA
 *
 * @ClassName CodeGenUtil
 * @Description
 * @Author wuhengzhen
 * @Date 2019-11-20 13:08
 */
public class CodeGenUtil {

    public static void main(String[] args) throws IOException {


        // 获取数据表元数据
        DataBaseMetaData metadata = DataBaseMetaData.builder()
                .mysql("localhost", "3306", "databaseName", "username", "password")
                .build();

        TableMeta tableMeta = metadata.analyze("table_name");

        // 转换build.json
        String schemaName = "springboot";

        InputStream stream = CodeGenUtil.class.getResourceAsStream(String.format(BuildConfig.buildJsonPath, schemaName));
        String json = IOUtils.toString(stream, "UTF-8");

        BuildConfig buildConfig = JSONObject.parseObject(json, BuildConfig.class);
        buildConfig.setSchema(schemaName);
        buildConfig.setTable(tableMeta);

        // 表名转驼峰命名
        String beanName = GenUtil.getCamelCase(buildConfig.getTable().getName(), buildConfig.getJavas().getIgnoreTableName(), true);
        buildConfig.getTable().setAlias(beanName);

        // 列名转驼峰命名
        for (ColumnMeta column : buildConfig.getTable().getColumns()) {
            String fieldName = GenUtil.getCamelCase(column.getName(), buildConfig.getJavas().getIgnoreColumnName(), false);
            column.setAlias(fieldName);
        }

        // 生成文件
        JavaFiles javaFiles = buildConfig.getJavas();
        if (javaFiles != null) {
            JavaGenerator javaGenerator = new JavaGenerator();
            for (JavaFile javaFile : javaFiles.getList()) {
                javaGenerator.create(javaFile, buildConfig);
            }
        }
    }
}
