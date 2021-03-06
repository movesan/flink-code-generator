package org.generator.flink.generator.base;

import org.generator.flink.config.PackageConfigType;
import org.generator.flink.config.PackageConfigTypes;
import org.generator.flink.domain.FieldBean;
import org.generator.flink.generator.Generator;
import org.generator.flink.generator.context.GeneratorContext;
import org.generator.flink.utils.FileUtil;
import org.generator.flink.utils.GeneratorFileUtils;
import org.generator.flink.utils.GeneratorStringUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.util.ClassUtils;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * 功能描述：读取配置文件，生成代码基本实现类
 */
public class BaseGeneratorImpl implements Generator {

    private static String LINE = System.getProperty("line.separator");
    /**
     * velocity上下文
     */
    protected VelocityContext velocityContext;

    /**
     * 模板存放文件夹
     */
    public static String VM_TARGET_PATH = "template";

    /**
     * 模板存放文件后缀
     */
    public static String VM_TARGET_EXT = ".vm";

    /**
     * 获取生成目录类型
     */
    private PackageConfigTypes packageConfigTypes;

    public String getVmTargetPath() {
        return VM_TARGET_PATH;
    }

    public void setVmTargetPath(String vmTargetPath) {
        VM_TARGET_PATH = vmTargetPath;
    }

    public String getVmTargetExt() {
        return VM_TARGET_EXT;
    }

    public void setVmTargetExt(String vmTargetExt) {
        VM_TARGET_EXT = vmTargetExt;
    }

    public PackageConfigTypes getPackageConfigTypes() {
        return packageConfigTypes;
    }

    public void setPackageConfigTypes(PackageConfigTypes packageConfigTypes) {
        this.packageConfigTypes = packageConfigTypes;
    }

    /**
     * 自动化创建业务代码
     *
     * @param context
     */
    @Override
    public void defaultGenerator(GeneratorContext context, Map<String, String> allPackageNameMap) {
        velocityContext = new VelocityContext();
        Properties properties = initDefaultProperties();
        VelocityEngine velocityEngine = new VelocityEngine(properties);
        velocityEngine.init();
        write(context, allPackageNameMap, velocityEngine);
    }

    /**
     * 插件读取模板文件要从jar包中读取
     *
     * @param context
     */
    @Override
    public void pluginGenerator(GeneratorContext context, Map<String, String> allPackageNameMap) {
        velocityContext = new VelocityContext();
        Properties properties = initPluginProperties();
        VelocityEngine velocityEngine = new VelocityEngine(properties);
        velocityEngine.init();
        write(context, allPackageNameMap, velocityEngine);
    }

    /**
     * 读取配置渲染模板，生成文件
     *
     * @param generatorContext
     */
    protected void write(GeneratorContext generatorContext,
                         Map<String, String> allPackageNameMap,
                         VelocityEngine velocityEngine) {
        // 读取模板渲染内容，同时创建文件
        Map<String, String> params = initGeneratorParams(generatorContext, allPackageNameMap);
        for (String templateName : params.keySet()) {
            try {
                Template template = velocityEngine.getTemplate(VM_TARGET_PATH + "/" + templateName, "UTF-8");
                initVelocityContext(velocityContext, generatorContext);
                StringWriter writer = new StringWriter();
                template.merge(velocityContext, writer);
                String content = writer.toString();
                GeneratorFileUtils.write(content, params.get(templateName));
                IOUtils.closeQuietly(writer);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 初始化Velocity配置
     */
    protected Properties initDefaultProperties() {
        Properties properties = new Properties();
        properties.setProperty(VelocityEngine.RUNTIME_LOG_LOGSYSTEM_CLASS,
                "org.apache.velocity.runtime.log.NullLogChute");
        properties.setProperty(Velocity.RESOURCE_LOADER, "class");
        properties.setProperty("class.resource.loader.class",
                "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        properties.setProperty(Velocity.ENCODING_DEFAULT, "UTF-8");
        properties.setProperty(Velocity.INPUT_ENCODING, "UTF-8");
        properties.setProperty(Velocity.OUTPUT_ENCODING, "UTF-8");
        return properties;
    }

    /**
     * 初始化Velocity配置
     */
    protected Properties initPluginProperties() {
        Properties properties = new Properties();
        properties.setProperty(VelocityEngine.RUNTIME_LOG_LOGSYSTEM_CLASS,
                "org.apache.velocity.runtime.log.NullLogChute");
        // 设置velocity资源加载方式为jar
        properties.setProperty("resource.loader", "jar");
        properties.setProperty("jar.resource.loader.class",
                "org.apache.velocity.runtime.resource.loader.JarResourceLoader");
        properties.setProperty("jar.resource.loader.path", "jar:" + getVmFilePath4Plugin());
        properties.setProperty(Velocity.ENCODING_DEFAULT, "UTF-8");
        properties.setProperty(Velocity.INPUT_ENCODING, "UTF-8");
        properties.setProperty(Velocity.OUTPUT_ENCODING, "UTF-8");
        return properties;
    }

    /**
     * 检查模版文件是否存在
     *
     * @param fileDir
     */
    private boolean checkTemplateExists(String fileDir) {
        boolean flag = false;
        File templateFile = new File(fileDir);
        if (templateFile.exists()) {
            FileFilter fileFilter = new FileFilter() {
                public boolean accept(File file) {
                    return file.getName().endsWith(VM_TARGET_EXT);
                }
            };
            List<File> fileList = FileUtil.getAllFilePath(fileDir, fileFilter);
            if (fileList != null && fileList.size() > 0) {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 初始化上下文
     *
     * @param velocityContext
     * @param generatorContext
     * @return
     */
    public void initVelocityContext(VelocityContext velocityContext, GeneratorContext generatorContext) {
        velocityContext.put("tableNames", generatorContext.getAttribute("tableNames"));
        velocityContext.put("tableName", generatorContext.getTableName());
        velocityContext.put("upClassName", generatorContext.getUpClassName());
        velocityContext.put("lowClassName", generatorContext.getLowClassName());
        velocityContext.put("basePackageName", generatorContext.getBasePackageName());
        velocityContext.put("primaryKeyType", generatorContext.getPrimaryKeyType());
        velocityContext.put("primaryKey", generatorContext.getPrimaryKey());
        velocityContext.put("normalPrimaryKey", generatorContext.getAttribute("normalPrimaryKey"));
        velocityContext.put("typeAliases", generatorContext.getAttribute("typeAliases"));
        velocityContext.put("mappers", generatorContext.getAttribute("mappers"));
        Map<String, String> packageNamesMap = generatorContext.getPackageNamesMap();
        if (packageNamesMap != null && packageNamesMap.size() > 0) {
            for (String packageNameKey : packageNamesMap.keySet()) {
                velocityContext.put(packageNameKey, packageNamesMap.get(packageNameKey));
            }
        }
        Map<String, Object> attributes = generatorContext.getAttributes();
        if (attributes != null && attributes.size() > 0) {
            for (String attributeKey : attributes.keySet()) {
                velocityContext.put(attributeKey, attributes.get(attributeKey));
            }
        }

        StringBuilder titleSb = new StringBuilder();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String authorName = StringUtils.isBlank(generatorContext.getAuthorName()) ? System.getProperty("user.name")
                : generatorContext.getAuthorName();

        titleSb.append("/** ")
                .append(LINE)
                .append(" * @description ")
                .append(LINE)
                .append(String.format(" * @author %s", authorName))
                .append(LINE)
                .append(String.format(" * @version %s", "V1.0"))
                .append(LINE)
                .append(String.format(" * @create %s.", dateFormat.format(new Date())))
                .append(LINE)
                .append(" */");
        velocityContext.put("classTitle", titleSb.toString());
        velocityContext.put("poPackageFileSuffix", generatorContext.getAttribute("poPackageFileSuffix"));
        velocityContext.put("modelPackageFileSuffix", generatorContext.getAttribute("modelPackageFileSuffix"));
    }

    /**
     * 初始化生成文件的模板及其文件名称
     *
     * @param context
     */
    protected Map<String, String> initGeneratorParams(GeneratorContext context, Map<String, String> allPackageNameMap) {

        Set<PackageConfigType> packageConfigTypeSet = this.packageConfigTypes.getPackageConfigTypeSet();

        Properties properties = context.getProperties();
        String tableName = context.getTableName();

        Map<String, String> generatorParams = Maps.newHashMap();
        if ("job".equals(tableName)) {
            for (PackageConfigType packageConfigType : packageConfigTypeSet) {
                String targetDir = packageConfigType.getTargetDir();
                String fileNameSuffix = packageConfigType.getFileNameSuffix();
                String template = packageConfigType.getTemplate();

                String fileName;
                context.getPackageNamesMap().putAll(allPackageNameMap);

                fileName = GeneratorFileUtils.getPackageDirectory(targetDir, properties)
                        + fileNameSuffix;
                generatorParams.put(template, fileName);
            }
        } else if (StringUtils.isNotBlank(tableName)) {
            for (PackageConfigType packageConfigType : packageConfigTypeSet) {
                String targetDir = packageConfigType.getTargetDir();
                String fileNameSuffix = packageConfigType.getFileNameSuffix();
                String template = packageConfigType.getTemplate();

                String fileName;
                context.getPackageNamesMap().putAll(allPackageNameMap);

                fileName = GeneratorFileUtils.getPackageDirectory(targetDir, properties)
                        + GeneratorStringUtils.firstUpperAndNoPrefix(tableName, properties)
                        + fileNameSuffix;
                generatorParams.put(template, fileName);
            }
        } else {
            for (PackageConfigType packageConfigType : packageConfigTypeSet) {
                String targetDir = packageConfigType.getTargetDir();
                String fileNameSuffix = packageConfigType.getFileNameSuffix();
                String template = packageConfigType.getTemplate();

                String fileName;
                context.getPackageNamesMap().putAll(allPackageNameMap);

                fileName = GeneratorFileUtils.getPropertiesPackageDirectory(targetDir, properties)
                        + fileNameSuffix;
                generatorParams.put(template, fileName);
            }
        }
        return generatorParams;
    }

    /**
     * 获取目录
     */
    protected String getVmFilePath() {
        String fileDir = null;
        try {
            fileDir = Thread.currentThread().getContextClassLoader().getResource(VM_TARGET_PATH).getPath();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileDir;
    }

    protected String getVmFilePath4Plugin() {
        ClassLoader clToUse = ClassUtils.getDefaultClassLoader();
        try {
            Enumeration<URL> urls = clToUse.getResources(VM_TARGET_PATH);
            URL url = urls.nextElement();
            String filePath = url.getFile();
            return filePath;
        } catch (IOException e) {
            throw new RuntimeException("read velocity templates error, e", e);
        }
    }

    protected String generateLowTableName(String tableName, Properties properties) {
        return GeneratorStringUtils.formatAndNoPrefix(tableName, properties);
    }

    protected String generateUpTableName(String tableName, Properties properties) {
        return GeneratorStringUtils.firstUpperAndNoPrefix(tableName, properties);
    }

    protected List<String> generateSourceParas(List<String> tableNames, Properties properties) {
        List<String> fields = Lists.newArrayList();
        for (String tableName : tableNames) {
            String lowName = GeneratorStringUtils.formatAndNoPrefix(tableName, properties);
            fields.add(lowName);
        }
        return fields;
    }

    protected List<String> generateKeyWords(List<String> tableNames, Properties properties) {
        List<String> fields = Lists.newArrayList();
        for (String tableName : tableNames) {
            StringBuilder sb = new StringBuilder();
            String upperName = tableName.toUpperCase();
            String lowName = GeneratorStringUtils.formatAndNoPrefix(tableName, properties);
            sb.append("public static final String ")
                    .append(upperName).append("_SOURCE").append(" = ")
                    .append("\"")
                    .append(lowName)
                    .append("\"")
                    .append(";");
            fields.add(sb.toString());
        }
        return fields;
    }

    protected List<String> generateTableNames(List<String> tableNames) {
        List<String> fields = Lists.newArrayList();
        for (String tableName : tableNames) {
            StringBuilder sb = new StringBuilder();
            String upperName = tableName.toUpperCase();
            sb.append("public static final String ")
                    .append(upperName).append(" = ")
                    .append("\"")
                    .append(tableName)
                    .append("\"")
                    .append(";");
            fields.add(sb.toString());
        }
        return fields;
    }

    protected Map<String, List<FieldBean>> generateSourceFields(Map<String, List<FieldBean>> fieldMap) {
        for (Map.Entry<String, List<FieldBean>> entry : fieldMap.entrySet()) {
            List<FieldBean> fieldBeanSet = entry.getValue();
            for (FieldBean fieldBean : fieldBeanSet) {
                String field = fieldBean.getField();
                StringBuilder sb = new StringBuilder();
                String upperField = field.toUpperCase();
                sb.append("public static final String SOURCE_FIELD_")
                        .append(upperField).append(" = ")
                        .append("\"")
                        .append(field)
                        .append("\"")
                        .append(";");
                fieldBean.setFieldSource(sb.toString());
            }
        }

        return fieldMap;
    }

    protected Map<String, List<String>> generateSourceFieldsParam(Map<String, List<FieldBean>> fieldMap) {
        Map<String, List<String>> map = new HashMap<>();
        for (Map.Entry<String, List<FieldBean>> entry : fieldMap.entrySet()) {
            List<String> fields = Lists.newArrayList();
            String key = entry.getKey();
            List<FieldBean> fieldBeanSet = entry.getValue();
            int i = fieldBeanSet.size();
            for (FieldBean fieldBean : fieldBeanSet) {
                i--;
                String field = fieldBean.getField();
                StringBuilder sb = new StringBuilder();
                String upperField = field.toUpperCase();
                sb.append("SOURCE_FIELD_")
                        .append(upperField);
                if (i > 0) {
                    sb.append(",");
                }
                fields.add(sb.toString());
            }
            map.put(key, fields);
        }
        return map;
    }



    protected List<String> generateFields(Map<String, String> map,
                                          Map<String, String> columnRemarkMap,
                                          Properties properties) {
        Set<String> keySet = map.keySet();
        List<String> fields = Lists.newArrayList();
        for (String key : keySet) {
            StringBuilder sb = new StringBuilder();
            String value = map.get(key);
            if (!StringUtils.isBlank(columnRemarkMap.get(key))) {
                sb.append("/**").append(LINE);
                sb.append("\t").append(" * ").append(columnRemarkMap.get(key).trim()).append(LINE);
                sb.append("\t").append(" */").append(LINE);
                sb.append("\t");
            }
            sb.append("private ")
                    .append(value + " ")
                    .append(GeneratorStringUtils.format(key, properties) + ";")
                    .append(LINE);
            fields.add(sb.toString());
        }
        return fields;
    }

    protected List<String> generateGetAndSetMethods(Map<String, String> map, Properties properties) {
        Set<String> keySet = map.keySet();
        List<String> methods = Lists.newArrayList();
        for (String key : keySet) {
            StringBuilder getSb = new StringBuilder();
            StringBuilder setSb = new StringBuilder();
            String field = GeneratorStringUtils.format(key, properties);
            String fieldType = map.get(key);
            // generate get method
            getSb.append("public ")
                    .append(fieldType + " ")
                    .append("get" + GeneratorStringUtils.firstUpperNoFormat(field) + "() {")
                    .append(LINE)
                    .append("\t\t")
                    .append("return " + field + ";")
                    .append(LINE)
                    .append("\t}");
            // generate set method
            setSb.append("public ")
                    .append("void ")
                    .append("set" + GeneratorStringUtils.firstUpperNoFormat(field) + "(" + fieldType + " " + field + ") {")
                    .append(LINE)
                    .append("\t\t")
                    .append("this." + field + " = " + field + ";")
                    .append(LINE)
                    .append("\t}");
            methods.add(getSb.toString());
            methods.add(setSb.toString());
        }
        return methods;
    }
}
