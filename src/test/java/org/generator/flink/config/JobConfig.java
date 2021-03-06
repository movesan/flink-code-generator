package org.generator.flink.config;

import org.apache.flink.api.java.utils.ParameterTool;
import org.generator.flink.common.JobConstant;

import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

/** 
 * @author yangbin216
 * @version V1.0
 * @date 2021 03-03 17:01.
 */
@Getter
@Setter
public class JobConfig extends com.jd.wl.rtdw.common.config.JobConfig implements Serializable {

    public static final String JOB_CONFIG = "job.properties";

    private static final String QLSMS_ERP_SEND_D_PARA = JobConstant.ConfigKeyWords.QLSMS_ERP_SEND_D_SOURCE + ".source.parallel";
    private static final String QLSMS_3PL_SEND_D_PARA = JobConstant.ConfigKeyWords.QLSMS_3PL_SEND_D_SOURCE + ".source.parallel";
    private static final String WAYBILL_OTH_PICK_TASK_PARA = JobConstant.ConfigKeyWords.WAYBILL_OTH_PICK_TASK_SOURCE + ".source.parallel";
    private static final String WAYBILL_OTH_PICK_OPE_PARA = JobConstant.ConfigKeyWords.WAYBILL_OTH_PICK_OPE_SOURCE + ".source.parallel";

    // union parallelism TODO

    private static final String KAFKA_SINK_PARA = "kafka.sink.parallel";

    private static final String SO_START_BY_TIME = "so.jdq.start.time";
    private static final String SO_START_BY_TIME_SECONDES = "so.jdq.start.time.seconds";

    private boolean jdqStartByTime = false;    //从昨天开始，为了测试方便
    private long jdqStartTimeSeconds = 86400 * 3;
    private boolean mock = false;

    // jdq source topic default parallelism
    private int qlsmsErpSendDPara = 20;
    private int qlsms3plSendDPara = 20;
    private int waybillOthPickTaskPara = 20;
    private int waybillOthPickOpePara = 20;

    // union parallelism TODO

    // jdq sink topic parallelism
    private int kafkaSinkPara = 20;

    public static JobConfig fromParameters(ParameterTool parameterTool) {

        JobConfig jobConfig = new JobConfig();

        if (parameterTool.has("mock")) {
            jobConfig.mock = true;
            jobConfig.esDefaultBulkSize = 5;
//            salesOutboundConfig.defaultRedisPipeSize = 10;
        }

        if (parameterTool.has(ES_HOSTS_URL)) {
            jobConfig.esHosts = parameterTool.get(ES_HOSTS_URL);
        }

        if (parameterTool.has(ES_USER_NAME)) {

            jobConfig.esUsername = parameterTool.get(ES_USER_NAME);
        }

        if (parameterTool.has(ES_PASSWORD)) {
            jobConfig.esPassword = parameterTool.get(ES_PASSWORD);
        }

        if (parameterTool.has(SO_START_BY_TIME)) {
            jobConfig.jdqStartByTime = parameterTool.getBoolean(SO_START_BY_TIME);
        }

        if (parameterTool.has(SO_START_BY_TIME_SECONDES)) {
            jobConfig.jdqStartTimeSeconds = parameterTool.getInt(SO_START_BY_TIME_SECONDES);
        }

        // source parallelism
        if (parameterTool.has(QLSMS_ERP_SEND_D_PARA)) {
            jobConfig.qlsmsErpSendDPara = parameterTool.getInt(QLSMS_ERP_SEND_D_PARA);
        }
        if (parameterTool.has(QLSMS_3PL_SEND_D_PARA)) {
            jobConfig.qlsms3plSendDPara = parameterTool.getInt(QLSMS_3PL_SEND_D_PARA);
        }
        if (parameterTool.has(WAYBILL_OTH_PICK_TASK_PARA)) {
            jobConfig.waybillOthPickTaskPara = parameterTool.getInt(WAYBILL_OTH_PICK_TASK_PARA);
        }
        if (parameterTool.has(WAYBILL_OTH_PICK_OPE_PARA)) {
            jobConfig.waybillOthPickOpePara = parameterTool.getInt(WAYBILL_OTH_PICK_OPE_PARA);
        }
    
        // union TODO

        // sink parallelism
        if (parameterTool.has(KAFKA_SINK_PARA)) {
            jobConfig.kafkaSinkPara = parameterTool.getInt(KAFKA_SINK_PARA);
        }

        return jobConfig;
    }

    @Override
    public boolean isJdqStartByTime() {
        return this.jdqStartByTime;
    }

    @Override
    public long getJdqStartTimeSeconds() {
        return this.jdqStartTimeSeconds;
    }

}
