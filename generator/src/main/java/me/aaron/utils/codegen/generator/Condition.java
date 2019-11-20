package me.aaron.utils.codegen.generator;

import me.aaron.utils.codegen.bean.build.BuildConfig;

/**
 * Created with IntelliJ IDEA
 *
 * @Description
 * @Author wuhengzhen
 * @Date 2019-11-20 13:16
 */
public interface Condition {

    boolean judge(BuildConfig config);
}
