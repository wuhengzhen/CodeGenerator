package me.aaron.utils.database.metadata.bean;

import lombok.Data;

/**
 * Created with IntelliJ IDEA
 *
 * @ClassName PrimaryKeyMeta
 * @Description
 * @Author wuhengzhen
 * @Date 2019-11-20 11:40
 */
@Data
public class PrimaryKeyMeta {

    String name;
    int seq;

    public static PrimaryKeyMeta of(String name, int seq) {
        PrimaryKeyMeta primaryKeyMeta = new PrimaryKeyMeta();
        primaryKeyMeta.setName(name);
        primaryKeyMeta.setSeq(seq);
        return primaryKeyMeta;
    }
}
