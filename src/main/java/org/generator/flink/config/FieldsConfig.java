package org.generator.flink.config;

import org.generator.flink.domain.FieldBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @description:
 *
 * @author yangbin216
 * @date 2021/2/24 18:32
 * @version 1.0
 */
public class FieldsConfig {

    public static Map<String, String> getColMap() {
        Map<String, String> map = new HashMap<>();
        map.put("name", "String");
        map.put("phone", "String");
        return map;
    }

    public static Map<String, String> getColRemarkMap() {
        Map<String, String> map = new HashMap<>();
        map.put("name", "名称");
        map.put("phone", "电话");
        return map;
    }

    public static Set<String> getFields() {
        Set<String> set = new HashSet<>();
        set.add("name");
        set.add("phone");
        set.add("address");
        set.add("age");
        return set;
    }

    public static Map<String, List<FieldBean>> getFieldsMap() {
        Map<String, List<FieldBean>> fieldMap = new HashMap<>();
        for (Map.Entry<String, Map<String, String>> entry : getTableFields().entrySet()) {
            String tableName = entry.getKey();
            Map<String, String> fieldsMap = entry.getValue();

            List<FieldBean> set = new ArrayList<>();
            for (Map.Entry<String, String> fieldEntry : fieldsMap.entrySet()) {
                String field = fieldEntry.getKey();
                String fieldName = fieldEntry.getValue();
                FieldBean bean = new FieldBean(field, fieldName);
                set.add(bean);
            }
            fieldMap.put(tableName, set);
        }
        return fieldMap;
    }

    private static Map<String, Map<String, String>> getTableFields() {
        Map<String, Map<String, String>> map = new HashMap<>();
        Map<String, String> mapSourceA = new LinkedHashMap<>();
        mapSourceA.put("pickup_task_id", "取件信息表ID");
        mapSourceA.put("customer_name", "用户名称");
        mapSourceA.put("customer_address", "用户地址");
        mapSourceA.put("telphone", "用户电话");
        mapSourceA.put("g_weight", "重量");
        mapSourceA.put("invoice_id", "发票号");
        mapSourceA.put("g_volume", "体积");
        mapSourceA.put("site_id", "站点ID");
        mapSourceA.put("new_waybill_code", "取件换新单号");
        mapSourceA.put("pickup_code", "取件单号");
        mapSourceA.put("product_code", "商品编号");
        mapSourceA.put("product_name", "商品名称");
        mapSourceA.put("attaches", "附件明细");
        mapSourceA.put("amount", "货物件数");
        mapSourceA.put("fetch_time", "取货时间");
        mapSourceA.put("receipt_yn", "是否收货");
        mapSourceA.put("pre_allocation_yn", "是否预分配");
        mapSourceA.put("end_courier", "终止操作人ID");
        mapSourceA.put("end_time", "终止时间");
        mapSourceA.put("end_reason", "终止原因");
        mapSourceA.put("status", "状态");
        mapSourceA.put("remark", "备注");
        mapSourceA.put("create_time", "创建时间");
        mapSourceA.put("update_time", "UPDATE_TIME");
        mapSourceA.put("is_invoice", "是否要发票");
        mapSourceA.put("is_invoice_copy", "是否要发票复印件");
        mapSourceA.put("is_packing", "是否要包装");
        mapSourceA.put("is_test_report", "是否要测试报告");
        mapSourceA.put("yn", "作废标志");
        mapSourceA.put("pickup_type", "取件单类型");
        mapSourceA.put("surface_code", "取件单对应的面单号");
        mapSourceA.put("org_id", "机构id");
        mapSourceA.put("org_name", "机构名称");
        mapSourceA.put("cky2", "配送中心");
        mapSourceA.put("store_id", "库房编号");
        mapSourceA.put("province_id", "省编号");
        mapSourceA.put("province_name", "省名称");
        mapSourceA.put("city_id", "城市编号");
        mapSourceA.put("city_name", "城市名称");
        mapSourceA.put("county_id", "县编号");
        mapSourceA.put("county_name", "县名称");
        mapSourceA.put("town_id", "镇编号");
        mapSourceA.put("town_name", "镇名称");
        mapSourceA.put("old_waybill_code", "取件单的原订单号");
        mapSourceA.put("sendpay", "配送标识");
        mapSourceA.put("customer_id", "客户ID");
        mapSourceA.put("rec_zipcode", "收件人邮编");
        mapSourceA.put("is3pl", "null");
        mapSourceA.put("new_pickup_type", "null");
        mapSourceA.put("first_time", "数据创建时间");
        mapSourceA.put("operate_time", "操作时间");
        mapSourceA.put("service_code", "售后服务单号");
        mapSourceA.put("again_weight", "称重重量");
        mapSourceA.put("rec_money", "应收金额");
        mapSourceA.put("payment", "付款类型0:现金。6扫码付");
        mapSourceA.put("merchant_id", "商家id");
        mapSourceA.put("pickup_sign", "取件单标识");
        mapSourceA.put("after_sale_type", "售后类型(10京东售后,20商家售后）");
        mapSourceA.put("merchant_name", "商家名称");
        mapSourceA.put("appoint_type", "预约类型");
        mapSourceA.put("appoint_begin_time", "预约开始时间(0:未预约,1:全天,2:上午,3:下午)");
        mapSourceA.put("appoint_end_time", "预约结束时间");
        mapSourceA.put("site_name", "站点名称(pickup_way=1时会有值)");
        mapSourceA.put("pickup_way", "取件途径(1自送，2上门)");
        mapSourceA.put("pickup_source", "取件来源");
        mapSourceA.put("road_code", "路区编号");
        mapSourceA.put("get_invoice", "是否取回发票");
        map.put("waybill_oth_pick_task", mapSourceA);

        Map<String, String> mapSourceB = new LinkedHashMap<>();
        mapSourceB.put("pick_merchant_id", "商家编码");
        mapSourceB.put("pick_operator_id", "实际取件操作人ID");
        mapSourceB.put("pick_operator_name", "实际取件操作人姓名");
        mapSourceB.put("pick_site_id", "实际取件操作单位ID");
        mapSourceB.put("pick_site_name", "实际取件操作单位名称");
        mapSourceB.put("pick_remark", "取件备注");
        mapSourceB.put("pick_create_time", "取件创建时间");
        mapSourceB.put("repick_operator_id", "再取操作人ID");
        mapSourceB.put("repick_operator_name", "再取操作人姓名");
        mapSourceB.put("repick_site_id", "再取操作单位ID");
        mapSourceB.put("repick_site_name", "再取件操作单位名称");
        mapSourceB.put("repick_remark", "再取备注");
        mapSourceB.put("repick_create_time", "再取创建时间");
        mapSourceB.put("cancle_operator_id", "取消操作人ID");
        mapSourceB.put("cancel_operator_name", "取消操作人姓名");
        mapSourceB.put("cancel_site_id", "取消操作单位ID");
        mapSourceB.put("cancel_site_name", "取消操作单位名称");
        mapSourceB.put("cancel_remark", "取消备注");
        mapSourceB.put("cancel_create_time", "取消创建时间");
        map.put("waybill_oth_pick_ope", mapSourceB);

        Map<String, String> mapSourceC = new LinkedHashMap<>();
        mapSourceC.put("send_code", "发货交接单号");
        mapSourceC.put("box_code", "箱号");
        mapSourceC.put("boxing_type", "装箱类型（1箱包装2单件包裹）");
        mapSourceC.put("create_site_id", "发货站点");
        mapSourceC.put("create_time", "发货时间");
        mapSourceC.put("create_user", "发货操作人");
        mapSourceC.put("create_user_id", "发货操作人id");
        map.put("qlsms_erp_send_d", mapSourceC);

        Map<String, String> mapSourceD = new LinkedHashMap<>();
        mapSourceD.put("send_code", "发货交接单号");
        mapSourceD.put("box_code", "箱号");
        mapSourceD.put("boxing_type", "装箱类型（1箱包装2单件包裹）");
        mapSourceD.put("create_site_id", "发货站点");
        mapSourceD.put("create_time", "发货时间");
        mapSourceD.put("create_user", "发货操作人");
        mapSourceD.put("create_user_id", "发货操作人id");
        map.put("qlsms_3pl_send_d", mapSourceD);

        return map;
    }
}