package org.generator.flink.common;

/** 
 * @author yangbin216
 * @version V1.0
 * @date 2021 03-03 17:01.
 */
public class JobConstant{

    public static final String MODEL_NAME = "rcdm_l03_dis_terminal_pickup";

    public static class ConfigKeyWords {

    public static final String WAYBILL_OTH_PICK_TASK_SOURCE = "waybillOthPickTask";
    public static final String WAYBILL_OTH_PICK_OPE_SOURCE = "waybillOthPickOpe";
    public static final String QLSMS_ERP_SEND_D_SOURCE = "qlsmsErpSendD";
    public static final String QLSMS_3PL_SEND_D_SOURCE = "qlsms3plSendD";

    }

    public static class SinkKeyWords {

        public final static String CDM_SINK_TOPIC_CONFIG = "cdmSink";
    }

    public static class SourceTableName {

    public static final String WAYBILL_OTH_PICK_TASK = "waybill_oth_pick_task";
    public static final String WAYBILL_OTH_PICK_OPE = "waybill_oth_pick_ope";
    public static final String QLSMS_ERP_SEND_D = "qlsms_erp_send_d";
    public static final String QLSMS_3PL_SEND_D = "qlsms_3pl_send_d";

    }

}