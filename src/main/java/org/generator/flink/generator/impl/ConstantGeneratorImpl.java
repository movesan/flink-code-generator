package org.generator.flink.generator.impl;

import org.apache.velocity.VelocityContext;
import org.generator.flink.config.FieldsConfig;
import org.generator.flink.generator.base.BaseGeneratorImpl;
import org.generator.flink.generator.context.GeneratorContext;

/**
 * @description:
 *
 * @author yangbin216
 * @date 2021/2/25 16:25
 * @version 1.0
 */
public class ConstantGeneratorImpl extends BaseGeneratorImpl {

    @Override
    public void initVelocityContext(VelocityContext velocityContext, GeneratorContext generatorContext) {
        super.initVelocityContext(velocityContext, generatorContext);

//        velocityContext.put("fields", generateSourceFields(FieldsConfig.getFields()));
        velocityContext.put("fieldsParamMap", generateSourceFieldsParam(FieldsConfig.getFieldsMap()));
        velocityContext.put("fieldsMap", generateSourceFields(FieldsConfig.getFieldsMap()));
    }
}