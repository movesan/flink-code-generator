package org.generator.flink.config;

import java.util.Set;

/**
 * @author Hujifang
 * 
 */
public class PackageConfigTypes {
    /**
     * 生成文件类型
     */
    private ConfigType type;

    /**
     * 包类型配置
     */
    private Set<PackageConfigType> packageConfigTypeSet;

    public PackageConfigTypes() {}

    public PackageConfigTypes(ConfigType type, Set<PackageConfigType> packageConfigTypeSet) {
        this.type = type;
        this.packageConfigTypeSet = packageConfigTypeSet;
    }

    public ConfigType getType() {
        return type;
    }

    public void setType(ConfigType type) {
        this.type = type;
    }

    public Set<PackageConfigType> getPackageConfigTypeSet() {
        return packageConfigTypeSet;
    }

    public void setPackageConfigTypeSet(Set<PackageConfigType> packageConfigTypeSet) {
        this.packageConfigTypeSet = packageConfigTypeSet;
    }

    public enum ConfigType {
        COMMON("common"),

        JOB("job"),

        SINK("sink"),

        SOURCE("source"),

        PROCESS("process");

        public String key;

        ConfigType(String key) {
            this.key = key;
        }
    }
}
