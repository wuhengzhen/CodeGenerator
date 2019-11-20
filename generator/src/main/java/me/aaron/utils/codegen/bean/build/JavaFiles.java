package me.aaron.utils.codegen.bean.build;

import lombok.Data;

import java.util.List;

/**
 * Created with IntelliJ IDEA
 *
 * @ClassName JavaFiles
 * @Description
 * @Author wuhengzhen
 * @Date 2019-11-20 13:11
 */
@Data
public class JavaFiles {

    String basePath;
    String basePackage;

    String[] ignoreTableName;
    String[] ignoreColumnName;

    List<JavaFile> list;

}
