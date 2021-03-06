package org.generator.flink.source.map;

import com.jd.bdp.jdw.avro.JdwData;
import org.apache.flink.api.common.functions.MapFunction;

/** 
 * @author yangbin216
 * @version V1.0
 * @date 2021 03-03 17:01.
 */
public class WaybillOthPickTaskMap implements MapFunction<JdwData, JdwData> {

    @Override
    public JdwData map(JdwData jdwData) throws Exception {

        return jdwData;
    }

}
