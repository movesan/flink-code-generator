package org.generator.flink.domain;


import lombok.Getter;
import lombok.Setter;

/**
 * @description:
 *
 * @author yangbin216
 * @date 2021/3/2 20:03
 * @version 1.0
 */
@Getter
@Setter
public class JobBean {

    /**
     * camelCase
     */
    private String lowCamelCase;
    /**
     * CamelCase
     */
    private String upperCamelCase;
    /**
     * snake_case
     */
    private String lowSnakeCase;
    /**
     * SNAKE_CASE
     */
    private String upperSnakeCase;
}