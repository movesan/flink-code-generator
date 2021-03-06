package org.generator.flink.source.constant;

import java.util.Arrays;
import java.util.List;

/** 
 * @author yangbin216
 * @version V1.0
 * @date 2021 03-03 17:01.
 */
public class WaybillOthPickOpeConstant {

    
    public static final String SOURCE_FIELD_PICK_MERCHANT_ID = "pick_merchant_id";
    
    public static final String SOURCE_FIELD_PICK_OPERATOR_ID = "pick_operator_id";
    
    public static final String SOURCE_FIELD_PICK_OPERATOR_NAME = "pick_operator_name";
    
    public static final String SOURCE_FIELD_PICK_SITE_ID = "pick_site_id";
    
    public static final String SOURCE_FIELD_PICK_SITE_NAME = "pick_site_name";
    
    public static final String SOURCE_FIELD_PICK_REMARK = "pick_remark";
    
    public static final String SOURCE_FIELD_PICK_CREATE_TIME = "pick_create_time";
    
    public static final String SOURCE_FIELD_REPICK_OPERATOR_ID = "repick_operator_id";
    
    public static final String SOURCE_FIELD_REPICK_OPERATOR_NAME = "repick_operator_name";
    
    public static final String SOURCE_FIELD_REPICK_SITE_ID = "repick_site_id";
    
    public static final String SOURCE_FIELD_REPICK_SITE_NAME = "repick_site_name";
    
    public static final String SOURCE_FIELD_REPICK_REMARK = "repick_remark";
    
    public static final String SOURCE_FIELD_REPICK_CREATE_TIME = "repick_create_time";
    
    public static final String SOURCE_FIELD_CANCLE_OPERATOR_ID = "cancle_operator_id";
    
    public static final String SOURCE_FIELD_CANCEL_OPERATOR_NAME = "cancel_operator_name";
    
    public static final String SOURCE_FIELD_CANCEL_SITE_ID = "cancel_site_id";
    
    public static final String SOURCE_FIELD_CANCEL_SITE_NAME = "cancel_site_name";
    
    public static final String SOURCE_FIELD_CANCEL_REMARK = "cancel_remark";
    
    public static final String SOURCE_FIELD_CANCEL_CREATE_TIME = "cancel_create_time";

    public final static List<String> PARAM_LIST = Arrays.asList(
        SOURCE_FIELD_PICK_MERCHANT_ID,
        SOURCE_FIELD_PICK_OPERATOR_ID,
        SOURCE_FIELD_PICK_OPERATOR_NAME,
        SOURCE_FIELD_PICK_SITE_ID,
        SOURCE_FIELD_PICK_SITE_NAME,
        SOURCE_FIELD_PICK_REMARK,
        SOURCE_FIELD_PICK_CREATE_TIME,
        SOURCE_FIELD_REPICK_OPERATOR_ID,
        SOURCE_FIELD_REPICK_OPERATOR_NAME,
        SOURCE_FIELD_REPICK_SITE_ID,
        SOURCE_FIELD_REPICK_SITE_NAME,
        SOURCE_FIELD_REPICK_REMARK,
        SOURCE_FIELD_REPICK_CREATE_TIME,
        SOURCE_FIELD_CANCLE_OPERATOR_ID,
        SOURCE_FIELD_CANCEL_OPERATOR_NAME,
        SOURCE_FIELD_CANCEL_SITE_ID,
        SOURCE_FIELD_CANCEL_SITE_NAME,
        SOURCE_FIELD_CANCEL_REMARK,
        SOURCE_FIELD_CANCEL_CREATE_TIME
    
    );

    public final static List<String> BASIC_LIST = Arrays.asList(
    );

}