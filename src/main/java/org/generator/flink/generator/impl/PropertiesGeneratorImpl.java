package org.generator.flink.generator.impl;

import org.apache.velocity.VelocityContext;
import org.generator.flink.generator.base.BaseGeneratorImpl;
import org.generator.flink.generator.context.GeneratorContext;
import org.generator.flink.utils.GeneratorStringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @description:
 *
 * @author yangbin216
 * @date 2021/2/25 16:25
 * @version 1.0
 */
public class PropertiesGeneratorImpl extends BaseGeneratorImpl {

    @Override
    public void initVelocityContext(VelocityContext velocityContext, GeneratorContext generatorContext) {
        super.initVelocityContext(velocityContext, generatorContext);
        List<String> tableNames = (List<String>) generatorContext.getAttribute("tableNames");
        velocityContext.put("sources", trans(tableNames, generatorContext.getProperties()));
    }

    private List<String> trans(List<String> tableNames, Properties properties) {
        List<String> list = new ArrayList<>();
        for (String tableName : tableNames) {
            list.add(GeneratorStringUtils.formatAndNoPrefix(tableName, properties));
        }
        return list;
    }
}