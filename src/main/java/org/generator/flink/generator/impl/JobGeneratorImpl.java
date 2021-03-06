package org.generator.flink.generator.impl;

import org.apache.velocity.VelocityContext;
import org.generator.flink.domain.JobBean;
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
public class JobGeneratorImpl extends BaseGeneratorImpl {

    @Override
    public void initVelocityContext(VelocityContext velocityContext, GeneratorContext generatorContext) {
        super.initVelocityContext(velocityContext, generatorContext);

        velocityContext.put("sourceConstants", generatorSourceConstants(generatorContext));
    }

    private List<JobBean> generatorSourceConstants(GeneratorContext generatorContext) {
        List<String> tableNames = (List<String>) generatorContext.getAttribute("tableNames");
        List<JobBean> list = new ArrayList<>();
        for (String tableName : tableNames) {
            String lowCamelCase = generateLowTableName(tableName, generatorContext.getProperties());
            String upperCamelCase = generateUpTableName(tableName, generatorContext.getProperties());
            String lowSnakeCase = tableName;
            String upperSnakeCase = tableName.toUpperCase();

            JobBean jobBean = new JobBean();
            jobBean.setLowCamelCase(lowCamelCase);
            jobBean.setUpperCamelCase(upperCamelCase);
            jobBean.setLowSnakeCase(lowSnakeCase);
            jobBean.setUpperSnakeCase(upperSnakeCase);
            list.add(jobBean);
        }

        return list;
    }
}