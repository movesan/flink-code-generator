package org.generator.flink.process.selector;

import com.jd.bdp.jdw.avro.JdwData;
import org.apache.flink.api.java.functions.KeySelector;

/** 
 * @author yangbin216
 * @version V1.0
 * @date 2021 03-03 17:01.
 */
public class QlsmsErpSendDSelector implements KeySelector<JdwData, String> {

    @Override
    public String getKey(JdwData value) throws Exception {

        return null;
    }

}