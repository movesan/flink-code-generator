package org.generator.flink.generator.impl;

import org.apache.velocity.VelocityContext;
import org.generator.flink.generator.base.BaseGeneratorImpl;
import org.generator.flink.generator.context.GeneratorContext;
import org.generator.flink.utils.GeneratorStringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @description:
 *
 * @author yangbin216
 * @date 2021/2/25 16:25
 * @version 1.0
 */
public class ConfigGeneratorImpl extends BaseGeneratorImpl {

    @Override
    public void initVelocityContext(VelocityContext velocityContext, GeneratorContext generatorContext) {
        super.initVelocityContext(velocityContext, generatorContext);

        List<String> tableNames = (List<String>) generatorContext.getAttribute("tableNames");
        velocityContext.put("sourceFinalParas", generateSourceParas(tableNames, generatorContext.getProperties()));
        velocityContext.put("sourceParaMap", transToMap(tableNames, generatorContext.getProperties()));
    }


    private Map<String, String> transToMap(List<String> tableNames, Properties properties) {
        Map<String, String> map = new HashMap<>();
        for (String tableName : tableNames) {
            String key = GeneratorStringUtils.formatAndNoPrefix(tableName, properties);
            String value = tableName.toUpperCase();
            map.put(key + "Para", value);
        }
        return map;
    }
}