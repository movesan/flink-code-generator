package org.generator.flink.source.filter;

import com.jd.bdp.jdw.avro.JdwData;
import org.apache.flink.api.common.functions.FilterFunction;

/** 
 * @author yangbin216
 * @version V1.0
 * @date 2021 02-26 17:54.
 */
public class WaybillMFilter implements FilterFunction<JdwData> {

    @Override
    public boolean filter(JdwData value) throws Exception {
        return true;
    }
}
