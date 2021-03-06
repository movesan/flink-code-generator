package org.generator.flink.source.filter;

import com.jd.bdp.jdw.avro.JdwData;
import org.apache.flink.api.common.functions.FilterFunction;

/** 
 * @author yangbin216
 * @version V1.0
 * @date 2021 03-03 17:01.
 */
public class WaybillOthPickTaskFilter implements FilterFunction<JdwData> {

    @Override
    public boolean filter(JdwData value) throws Exception {
        return true;
    }
}
