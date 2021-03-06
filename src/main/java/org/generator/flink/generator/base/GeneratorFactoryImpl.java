package org.generator.flink.generator.base;

import org.generator.flink.generator.Generator;
import org.generator.flink.start.DefaultGeneratorStarter;

import java.util.Set;

/**
 * 功能描述：分发生成代码适配器
 */
public class GeneratorFactoryImpl {

    /**
     * 生成器集合（每个数据源都需要生成一份）
     */
    private Set<Generator> sourceGeneratorSet;
    /**
     * 生成器集合（整个项目生成一份）
     */
    private Set<Generator> jobGeneratorSet;
    /**
     * 配置文件生成器集合（整个项目生成一份）
     */
    private Set<Generator> propertiesGeneratorSet;

    public void setSourceGeneratorSet(Set<Generator> sourceGeneratorSet) {
        this.sourceGeneratorSet = sourceGeneratorSet;
    }

    public void setJobGeneratorSet(Set<Generator> jobGeneratorSet) {
        this.jobGeneratorSet = jobGeneratorSet;
    }

    public void setPropertiesGeneratorSet(Set<Generator> propertiesGeneratorSet) {
        this.propertiesGeneratorSet = propertiesGeneratorSet;
    }

    public void flinkGeneratorStarter() {
        DefaultGeneratorStarter flinkGeneratorStarter = new DefaultGeneratorStarter();
        flinkGeneratorStarter.start(sourceGeneratorSet, jobGeneratorSet, propertiesGeneratorSet);
    }

}
