package me.aaron.utils.codegen.bean.build;

import lombok.Data;

/**
 * Created with IntelliJ IDEA
 *
 * @ClassName JavaFile
 * @Description
 * @Author wuhengzhen
 * @Date 2019-11-20 13:10
 */
@Data
public class JavaFile extends BaseFile {

    /**
     * 类的包路径和文件名
     */
    String javaPackage;
    String javaName;
    /**
     * 主键的包路径和文件名
     */
    String idPackage;
    String idClass;

}
