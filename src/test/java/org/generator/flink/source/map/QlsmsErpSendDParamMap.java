package org.generator.flink.source.map;

import com.jd.wl.rtdw.common.map.JdqCurParamReMap;
import org.generator.flink.source.constant.QlsmsErpSendDConstant;

import java.util.List;

/** 
 * @author yangbin216
 * @version V1.0
 * @date 2021 03-03 17:01.
 */
public class QlsmsErpSendDParamMap extends JdqCurParamReMap {

    @Override
    protected List<String> getParamList() {
        return QlsmsErpSendDConstant.PARAM_LIST;
    }

    @Override
    protected List<String> getBasicList() {
        return QlsmsErpSendDConstant.BASIC_LIST;
    }

}
