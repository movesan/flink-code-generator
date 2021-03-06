package org.generator.flink.source.filter;

import com.jd.bdp.jdw.avro.JdwData;
import org.apache.flink.api.common.functions.FilterFunction;
import com.jd.wl.rtdw.common.util.CharSequenceMapUtils;
import org.apache.commons.collections.MapUtils;
import org.generator.flink.source.constant.QlsmsErpSendDConstant;
import java.util.Map;

/** 
 * @author yangbin216
 * @version V1.0
 * @date 2021 03-03 17:01.
 */
public class QlsmsErpSendDParamFilter implements FilterFunction<JdwData> {

    @Override
    public boolean filter(JdwData value) throws Exception {
        if (value == null) {
            return false;
        }
        Map<String, String> allJdwDataParam = CharSequenceMapUtils.getAllJdwDataParam(value);
        if (MapUtils.isEmpty(allJdwDataParam)) {
            return false;
        }
        // 基础字段不全，直接过滤
        for (String s : QlsmsErpSendDConstant.BASIC_LIST) {
            if (!allJdwDataParam.containsKey(s)) {
                return false;
            }
        }
        return true;
    }
}
