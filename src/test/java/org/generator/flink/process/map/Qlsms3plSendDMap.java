package org.generator.flink.process.map;

import com.jd.bdp.jdw.avro.JdwData;
import org.apache.flink.api.common.functions.RichFlatMapFunction;
import org.apache.flink.api.common.state.StateTtlConfig;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.api.common.time.Time;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.util.Collector;

/** 
 * @author yangbin216
 * @version V1.0
 * @date 2021 03-03 17:01.
 */
public class Qlsms3plSendDMap extends RichFlatMapFunction<JdwData, JdwData> {

    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
        StateTtlConfig ttlConfig = StateTtlConfig.newBuilder(Time.days(30))
                .setUpdateType(StateTtlConfig.UpdateType.OnReadAndWrite)
                .setStateVisibility(StateTtlConfig.StateVisibility.ReturnExpiredIfNotCleanedUp)
                .cleanupInRocksdbCompactFilter(6000L).build();

    }

    @Override
    public void flatMap(JdwData value, Collector<JdwData> out) throws Exception {
        return;
    }

}
