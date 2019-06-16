package com.neuedu.hospitalbackend.service.serviceinterface.finicialservice;

import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.util.CommonResult;

/**
 * 6.3 门诊科室工作量统计
 * 应用场景：用于财务工作人员统计指定时间段各科室的总收入及各分项收入情况。可按开单科室统计或执行科室统计。
 * 操作描述：临床科室工作量统计、医技科室工作量统计。
 *          输入统计起始和终止时间，点击查询按钮，查询出各科室的总收入及各分项收入情况。通过点击导出按钮，对查询结果进行导出
 * @author Polaris
 */
public interface DepartmentStatisticsService {

    /**
     * 6.3.1 临床科室工作量统计
     * @return
     */
    CommonResult clinicianDepartmentStatistics(String beginDateStr, String endDateStr);

    /**
     * 6.3.2 医技科室工作量统计
     * @return
     */
    CommonResult technicianDepartmentStatistics(String beginDateStr, String endDateStr);
}
