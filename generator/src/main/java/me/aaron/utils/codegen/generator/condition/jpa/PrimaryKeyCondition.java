package me.aaron.utils.codegen.generator.condition.jpa;

import me.aaron.utils.codegen.bean.build.BuildConfig;
import me.aaron.utils.codegen.generator.Condition;

/**
 * Created with IntelliJ IDEA
 *
 * @ClassName PrimaryKeyCondition
 * @Description 复合主键判断条件
 * @Author wuhengzhen
 * @Date 2019-11-20 13:16
 */
public class PrimaryKeyCondition implements Condition {

    @Override
    public boolean judge(BuildConfig config) {
        try {
            if (config.getTable().getPrimaryKeys().size() > 1) {
                return true;
            }
        } catch (Exception ex) {
        }
        return false;

    }
}
