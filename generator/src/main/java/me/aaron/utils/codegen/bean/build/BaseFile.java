package me.aaron.utils.codegen.bean.build;

import lombok.Data;

/**
 * Created with IntelliJ IDEA
 *
 * @ClassName BaseFile
 * @Description
 * @Author wuhengzhen
 * @Date 2019-11-20 13:09
 */
@Data
public class BaseFile {

    String condition;
    String key;
    String fileTpl;
    String fileName;
    String filePath;

}
