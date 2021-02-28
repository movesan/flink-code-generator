package org.generator.flink.generator.impl;

import java.util.List;
import org.apache.velocity.VelocityContext;
import org.generator.flink.config.FieldsConfig;
import org.generator.flink.generator.base.BaseGeneratorImpl;
import org.generator.flink.generator.context.GeneratorContext;

/**
 * @author yangbin216
 * @version 1.0
 * @description:
 * @date 2021/2/25 16:25
 */
public class JobConstantGeneratorImpl extends BaseGeneratorImpl {

    @Override
    public void initVelocityContext(VelocityContext velocityContext, GeneratorContext generatorContext) {
        super.initVelocityContext(velocityContext, generatorContext);

        List<String> tableNames = (List<String>) generatorContext.getAttribute("tableNames");

        velocityContext.put("modelName", generatorContext.getModelName());
        velocityContext.put("keyWords", generateKeyWords(tableNames, generatorContext.getProperties()));
        velocityContext.put("tableNames", generateTableNames(tableNames));
    }
}