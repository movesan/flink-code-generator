package org.generator.flink.config;

import org.generator.flink.domain.FieldBean;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
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

    public static Map<String, Object> loadYaml() {
        Yaml yaml = new Yaml();
        InputStream inputStream = FieldsConfig.class
                .getClassLoader()
                .getResourceAsStream("config-fields.yaml");

        return yaml.load(inputStream);
    }

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

    public static Map<String, List<FieldBean>> getFieldsMap() {
        Map<String, List<FieldBean>> fieldMap = new HashMap<>();
        for (Map.Entry<String, Map<String, String>> entry : getTableFields().entrySet()) {
            String tableName = entry.getKey();
            Map<String, String> fieldsMap = entry.getValue();

            List<FieldBean> set = new ArrayList<>();
            for (Map.Entry<String, String> fieldEntry : fieldsMap.entrySet()) {
                String field = fieldEntry.getKey();
                String fieldName = fieldEntry.getValue();
                FieldBean bean = new FieldBean(field, fieldName);
                set.add(bean);
            }
            fieldMap.put(tableName, set);
        }
        return fieldMap;
    }

    private static Map<String, Map<String, String>> getTableFields() {
        Map<String, Object> yamlMap = loadYaml();
        Map<String, Map<String, String>> map = new HashMap<>();
        for (Map.Entry<String, Object> entry : yamlMap.entrySet()) {
            String tableName = entry.getKey();
            Map<String, String> fieldsMap = (Map<String, String>) entry.getValue();
            map.put(tableName, fieldsMap);
        }

        return map;
    }
}