package org.generator.flink.sink;

import com.jd.bdp.jdw.avro.JdwData;
import org.apache.flink.api.java.functions.KeySelector;

/** 
 * @author yangbin216
 * @version V1.0
 * @date 2021 02-26 17:54.
 */
public class WaybillModelSelector implements KeySelector<JdwData, String> {

    @Override
    public String getKey(JdwData value) throws Exception {

        return null;
    }

}
