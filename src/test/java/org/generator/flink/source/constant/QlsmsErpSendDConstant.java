package org.generator.flink.source.constant;

import java.util.Arrays;
import java.util.List;

/** 
 * @author yangbin216
 * @version V1.0
 * @date 2021 03-03 17:01.
 */
public class QlsmsErpSendDConstant {

    
    public static final String SOURCE_FIELD_SEND_CODE = "send_code";
    
    public static final String SOURCE_FIELD_BOX_CODE = "box_code";
    
    public static final String SOURCE_FIELD_BOXING_TYPE = "boxing_type";
    
    public static final String SOURCE_FIELD_CREATE_SITE_ID = "create_site_id";
    
    public static final String SOURCE_FIELD_CREATE_TIME = "create_time";
    
    public static final String SOURCE_FIELD_CREATE_USER = "create_user";
    
    public static final String SOURCE_FIELD_CREATE_USER_ID = "create_user_id";

    public final static List<String> PARAM_LIST = Arrays.asList(
        SOURCE_FIELD_SEND_CODE,
        SOURCE_FIELD_BOX_CODE,
        SOURCE_FIELD_BOXING_TYPE,
        SOURCE_FIELD_CREATE_SITE_ID,
        SOURCE_FIELD_CREATE_TIME,
        SOURCE_FIELD_CREATE_USER,
        SOURCE_FIELD_CREATE_USER_ID
    
    );

    public final static List<String> BASIC_LIST = Arrays.asList(
    );

}