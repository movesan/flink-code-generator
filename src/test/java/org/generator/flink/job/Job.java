package org.generator.flink.job;

import com.jd.bdp.jdw.avro.JdwData;
import com.jd.wl.rtdw.common.map.JdqParamCaseReMap;
import com.jd.wl.rtdw.common.mq.AnotherJdwDataDeserSchema;
import com.jd.wl.rtdw.common.mq.JDQKafkaFactory;
import org.generator.flink.common.JobConstant;
import org.generator.flink.config.JobConfig;
import org.generator.flink.source.filter.WaybillOthPickTaskParamFilter;
import org.generator.flink.source.map.WaybillOthPickTaskParamMap;
import org.generator.flink.source.filter.WaybillOthPickOpeParamFilter;
import org.generator.flink.source.map.WaybillOthPickOpeParamMap;
import org.generator.flink.source.filter.QlsmsErpSendDParamFilter;
import org.generator.flink.source.map.QlsmsErpSendDParamMap;
import org.generator.flink.source.filter.Qlsms3plSendDParamFilter;
import org.generator.flink.source.map.Qlsms3plSendDParamMap;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.CheckpointConfig;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.OutputTag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;

import static com.jd.wl.rtdw.common.Constants.BDP_LOGGER;

/** 
 * @author yangbin216
 * @version V1.0
 * @date 2021 03-03 17:01.
 */
public class Job {

    private static final Logger logger = LoggerFactory.getLogger(BDP_LOGGER);

    public static void main(String[] args) throws Exception {
        ParameterTool parameterA = ParameterTool.fromArgs(args);
        InputStream file = Job.class.getResourceAsStream("/" + JobConfig.JOB_CONFIG);
        ParameterTool parameterFile = ParameterTool.fromPropertiesFile(file);

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        ParameterTool parameterTool = parameterA.mergeWith(parameterFile);
        env.getConfig().setGlobalJobParameters(parameterTool);

        logger.info("start with param:{}", parameterTool.toMap().toString());
        JobConfig jobConfig = JobConfig.fromParameters(parameterTool);
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        env.setMaxParallelism(500);
        env.enableCheckpointing(60000, CheckpointingMode.EXACTLY_ONCE);

        CheckpointConfig config = env.getCheckpointConfig();
        config.enableExternalizedCheckpoints(CheckpointConfig.ExternalizedCheckpointCleanup.RETAIN_ON_CANCELLATION);
        config.setMinPauseBetweenCheckpoints(60000);
        config.setCheckpointTimeout(60 * 1000 * 5);
        config.setMaxConcurrentCheckpoints(1);

        //
        SingleOutputStreamOperator<JdwData> waybillOthPickTaskSourceStream = JDQKafkaFactory
                .addSource(env, JobConstant.ConfigKeyWords.WAYBILL_OTH_PICK_TASK_SOURCE, new AnotherJdwDataDeserSchema(), jobConfig, jobConfig.getWaybillOthPickTaskPara())
                .map(new JdqParamCaseReMap(JdqParamCaseReMap.ParamCase.LOWER_CASE)).name("WaybillOthPickTaskParamCaseMap").uid("WaybillOthPickTaskParamCaseMap").setParallelism(jobConfig.getWaybillOthPickTaskPara())
                .filter(new WaybillOthPickTaskParamFilter()).name("WaybillOthPickTaskParamFilter").uid("WaybillOthPickTaskParamFilter").setParallelism(jobConfig.getWaybillOthPickTaskPara())
                .map(new WaybillOthPickTaskParamMap()).name("WaybillOthPickTaskParamMap").uid("WaybillOthPickTaskParamMap").setParallelism(jobConfig.getWaybillOthPickTaskPara());
        //
        SingleOutputStreamOperator<JdwData> waybillOthPickOpeSourceStream = JDQKafkaFactory
                .addSource(env, JobConstant.ConfigKeyWords.WAYBILL_OTH_PICK_OPE_SOURCE, new AnotherJdwDataDeserSchema(), jobConfig, jobConfig.getWaybillOthPickOpePara())
                .map(new JdqParamCaseReMap(JdqParamCaseReMap.ParamCase.LOWER_CASE)).name("WaybillOthPickOpeParamCaseMap").uid("WaybillOthPickOpeParamCaseMap").setParallelism(jobConfig.getWaybillOthPickOpePara())
                .filter(new WaybillOthPickOpeParamFilter()).name("WaybillOthPickOpeParamFilter").uid("WaybillOthPickOpeParamFilter").setParallelism(jobConfig.getWaybillOthPickOpePara())
                .map(new WaybillOthPickOpeParamMap()).name("WaybillOthPickOpeParamMap").uid("WaybillOthPickOpeParamMap").setParallelism(jobConfig.getWaybillOthPickOpePara());
        //
        SingleOutputStreamOperator<JdwData> qlsmsErpSendDSourceStream = JDQKafkaFactory
                .addSource(env, JobConstant.ConfigKeyWords.QLSMS_ERP_SEND_D_SOURCE, new AnotherJdwDataDeserSchema(), jobConfig, jobConfig.getQlsmsErpSendDPara())
                .map(new JdqParamCaseReMap(JdqParamCaseReMap.ParamCase.LOWER_CASE)).name("QlsmsErpSendDParamCaseMap").uid("QlsmsErpSendDParamCaseMap").setParallelism(jobConfig.getQlsmsErpSendDPara())
                .filter(new QlsmsErpSendDParamFilter()).name("QlsmsErpSendDParamFilter").uid("QlsmsErpSendDParamFilter").setParallelism(jobConfig.getQlsmsErpSendDPara())
                .map(new QlsmsErpSendDParamMap()).name("QlsmsErpSendDParamMap").uid("QlsmsErpSendDParamMap").setParallelism(jobConfig.getQlsmsErpSendDPara());
        //
        SingleOutputStreamOperator<JdwData> qlsms3plSendDSourceStream = JDQKafkaFactory
                .addSource(env, JobConstant.ConfigKeyWords.QLSMS_3PL_SEND_D_SOURCE, new AnotherJdwDataDeserSchema(), jobConfig, jobConfig.getQlsms3plSendDPara())
                .map(new JdqParamCaseReMap(JdqParamCaseReMap.ParamCase.LOWER_CASE)).name("Qlsms3plSendDParamCaseMap").uid("Qlsms3plSendDParamCaseMap").setParallelism(jobConfig.getQlsms3plSendDPara())
                .filter(new Qlsms3plSendDParamFilter()).name("Qlsms3plSendDParamFilter").uid("Qlsms3plSendDParamFilter").setParallelism(jobConfig.getQlsms3plSendDPara())
                .map(new Qlsms3plSendDParamMap()).name("Qlsms3plSendDParamMap").uid("Qlsms3plSendDParamMap").setParallelism(jobConfig.getQlsms3plSendDPara());
    
        JDQKafkaFactory.addSink(unionStream, JobConstant.SinkKeyWords.CDM_SINK_TOPIC_CONFIG, JobConstant.SinkKeyWords.CDM_SINK_TOPIC_CONFIG, jobConfig.getKafkaSinkPara(), new KafkaSinJdwDataSchemaWrapper());
        logger.trace("plan visualizer == \n{}", env.getExecutionPlan());
        env.execute();
    }

}
