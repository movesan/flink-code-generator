package org.generator.flink.config;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @description:
 *
 * @author yangbin216
 * @date 2021/2/24 18:32
 * @version 1.0
 */
public class FieldsConfig {

    public static Map<String, String> getColMap() {
        Map<String, String> map = new HashMap<>();
        map.put("name", "String");
        map.put("phone", "String");
        return map;
    }

    public static Map<String, String> getColRemarkMap() {
        Map<String, String> map = new HashMap<>();
        map.put("name", "名称");
        map.put("phone", "电话");
        return map;
    }

    public static Set<String> getFields() {
        Set<String> set = new HashSet<>();
        set.add("name");
        set.add("phone");
        set.add("address");
        set.add("age");
        return set;
    }
}