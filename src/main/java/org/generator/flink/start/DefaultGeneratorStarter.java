package org.generator.flink.start;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.generator.flink.config.GeneratorConfigurer;
import org.generator.flink.config.GeneratorConfigurerFactory;
import org.generator.flink.config.PackageConfigTypes;
import org.generator.flink.generator.Generator;
import org.generator.flink.generator.context.GeneratorContext;
import org.generator.flink.utils.GeneratorFileUtils;
import org.generator.flink.utils.GeneratorStringUtils;
import org.generator.flink.utils.PropertiesUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;


/**
 * 功能描述：Flink Stream 自动化生成代码执行实现
 */
public class DefaultGeneratorStarter implements GeneratorStarter {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultGeneratorStarter.class);

    /**
     * 读取配置
     */
    private static Properties properties;

    static {
        GeneratorConfigurer generatorConfigurer = GeneratorConfigurerFactory.getGeneratorConfigurer();
        properties = generatorConfigurer.getProperties();
        generatorConfigurer.initConfigParams();
    }

    /**
     * 执行生成方法
     */
    @Override
    public void start(Set<Generator> sourceGeneratorSet, Set<Generator> jobGeneratorSet) {
        try {
            generator(sourceGeneratorSet, jobGeneratorSet);
        } catch (Exception e) {
            throw new RuntimeException("启动创建代码工具出现异常", e);
        }
    }

    /**
     * 自动化创建代码文件
     */
    protected void generator(Set<Generator> sourceGeneratorSet, Set<Generator> jobGeneratorSet) {
        LOGGER.info("**********代码生成工具,开始自动生成代码**********");

        /** ++++++++++++++++++++++++++++++++ 自动化生成文件（一个job 只生成一份）++++++++++++++++++++++++++++++++ */
        doGenerator(jobGeneratorSet, "waybill_model");

        /** ++++++++++++++++++++++++++++++++ 自动化生成文件（每个数据源生成一份）++++++++++++++++++++++++++++++++ */
        List<String> tableNames = PropertiesUtils.getTableList(properties);
        if (CollectionUtils.isEmpty(tableNames)) {
            throw new RuntimeException("配置代码表配置或者表前缀配置无法获取数据库表，请检查表配置和表前缀配置.");
        }
        for (String tableName : tableNames) {
            doGenerator(sourceGeneratorSet, tableName);
        }
        LOGGER.info("**********代码生成工具,已经结束生成代码**********");
    }

    protected void doGenerator(Set<Generator> generatorSet, String tableName) {
        Map<String, String> allTypeAliasesMap = new TreeMap<>();
        Set<PackageConfigTypes> packageConfigTypesHashSet = new HashSet<>();
        for (Generator generator : generatorSet) {
            packageConfigTypesHashSet.add(generator.getPackageConfigTypes());
        }
        Map<String, String> allPackageNameMap = GeneratorFileUtils.getAllPackageName(properties, packageConfigTypesHashSet);
        for (Map.Entry<String, String> entry : allPackageNameMap.entrySet()) {
            LOGGER.info("**********包名映射[键:{} , 值:{}]",
                    entry.getKey(),
                    entry.getValue());
        }

        Map<String, String> packageFileSuffixMap = GeneratorFileUtils.getAllPackageFileSuffix(packageConfigTypesHashSet);
        for (Map.Entry<String, String> entry : packageFileSuffixMap.entrySet()) {
            LOGGER.info("**********包名对应文件后缀映射[键:{} , 值:{}]", entry.getKey(), entry.getValue());
        }
        LOGGER.info("**********代码生成工具,开始自动生成代码>>>{}", tableName);
        // 要生成的模块分层
        String layerConfig = PropertiesUtils.getLayers(properties);
        String[] layers = StringUtils.split(layerConfig, ",");
        if (ArrayUtils.isEmpty(layers)) {
            throw new RuntimeException(tableName + " 读取配置文件分层结构为空,请检查配置是否按照逗号隔开.");
        }
        Set<String> typeSet = new HashSet<>(Arrays.asList(layers));
        LOGGER.info("**********代码生成工具,开始自动生成代码>>>{}", layerConfig);
        Set<PackageConfigTypes> packageConfigTypesSet = new HashSet<>();
        for (Generator generator : generatorSet) {
            if (typeSet.contains(generator.getPackageConfigTypes().getType().key)) {
                packageConfigTypesSet.add(generator.getPackageConfigTypes());
            }
        }

        // 创建目录
        GeneratorFileUtils.createPackageDirectory(properties, packageConfigTypesSet);

        for (Generator generator : generatorSet) {
            PackageConfigTypes packageConfigTypes = generator.getPackageConfigTypes();
            if (!packageConfigTypesSet.contains(packageConfigTypes)) {
                continue;
            }

            try {
                GeneratorContext generatorContext = initBaseContext(tableName, allTypeAliasesMap);

                for (Map.Entry<String, String> entry : packageFileSuffixMap.entrySet()) {
                    generatorContext.addAttribute(entry.getKey(), entry.getValue());
                }

                doGeneratorService(generator, generatorContext, allPackageNameMap);
            } catch (Exception e) {
                LOGGER.error(String.format("Can not Generate tableName:%s,configTypes:%s",
                        tableName,
                        packageConfigTypes.getType()),
                        e);
            }
        }
        LOGGER.info("**********代码生成工具,已经结束生成代码>>>{}", tableName);
    }

    /**
     * 调用创建模板的方式
     *
     * @param generator         生成器
     * @param generatorContext  生成器上下文
     * @param allPackageNameMap 包名
     */
    protected void doGeneratorService(Generator generator,
                                      GeneratorContext generatorContext,
                                      Map<String, String> allPackageNameMap) {
        generator.defaultGenerator(generatorContext, allPackageNameMap);
    }

    /**
     * 初始化渲染模板基本参数上下文
     *
     * @param tableName 表名
     */
    protected GeneratorContext initBaseContext(String tableName,
                                               Map<String, String> allTypeAliasesMap) {
//        Map<String, String> propMap = connector.getPrimaryKey(tableName);
        List<String> tableNames = PropertiesUtils.getTableList(properties);
        String modelName = PropertiesUtils.getModelName(properties);
        String authorName = PropertiesUtils.getAuthorName(properties);
        String upClassName = GeneratorStringUtils.firstUpperAndNoPrefix(tableName, properties);
        String lowClassName = GeneratorStringUtils.formatAndNoPrefix(tableName, properties);
        String packageName = PropertiesUtils.getPackage(properties);
//        String primaryKeyType = propMap.get("primaryKeyType");
        String columnPrimaryKey = "primaryKey";
        String primaryKeyType = "primaryKeyType";
        String primaryKey = GeneratorStringUtils.firstUpperNoFormat(GeneratorStringUtils.format(columnPrimaryKey, properties));
//        String columnPrimaryKey = propMap.get("primaryKey");
        String normalPrimaryKey = GeneratorStringUtils.format(columnPrimaryKey, properties);

//        String tableRemark = connector.getTableRemark(tableName);
        GeneratorContext generatorContext = new GeneratorContext(authorName,
                tableName,
                upClassName,
                lowClassName,
                packageName,
                primaryKeyType,
                primaryKey,
                modelName,
                properties);
//        generatorContext.addAttribute("connector", connector);
        generatorContext.addAttribute("properties", properties);
        generatorContext.addAttribute("columnPrimaryKey", columnPrimaryKey);
        generatorContext.addAttribute("normalPrimaryKey", normalPrimaryKey);
        generatorContext.addAttribute("typeAliases", allTypeAliasesMap);
        generatorContext.addAttribute("tableNames", tableNames);
//        generatorContext.addAttribute("mappers", allMappersMap);
//        generatorContext.addAttribute("tableRemark", tableRemark);
        return generatorContext;
    }
}
