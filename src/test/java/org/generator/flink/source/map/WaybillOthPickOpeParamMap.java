package org.generator.flink.source.map;

import com.jd.wl.rtdw.common.map.JdqCurParamReMap;
import org.generator.flink.source.constant.WaybillOthPickOpeConstant;

import java.util.List;

/** 
 * @author yangbin216
 * @version V1.0
 * @date 2021 03-03 17:01.
 */
public class WaybillOthPickOpeParamMap extends JdqCurParamReMap {

    @Override
    protected List<String> getParamList() {
        return WaybillOthPickOpeConstant.PARAM_LIST;
    }

    @Override
    protected List<String> getBasicList() {
        return WaybillOthPickOpeConstant.BASIC_LIST;
    }

}
