package org.generator.flink.domain;

import lombok.Data;

/**
 * @description:
 *
 * @author yangbin216
 * @date 2021/3/3 14:15
 * @version 1.0
 */
@Data
public class FieldBean {

    private String tableName;

    private String field;

    private String fieldName;

    private String fieldType;

    private String fieldSource;

    public FieldBean() {

    }

    public FieldBean(String field, String fieldName) {
        this.field = field;
        this.fieldName = fieldName;
    }
}