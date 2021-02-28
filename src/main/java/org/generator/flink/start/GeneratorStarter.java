package org.generator.flink.start;

import org.generator.flink.generator.Generator;

import java.util.Set;

/**
 * 功能描述：自动化生成代码接口
 * @author:Hujifang
 */
public interface GeneratorStarter {

    /**
     * 启动创建代码
     */
    void start(Set<Generator> sourceGeneratorSet, Set<Generator> jobGeneratorSet);
}
