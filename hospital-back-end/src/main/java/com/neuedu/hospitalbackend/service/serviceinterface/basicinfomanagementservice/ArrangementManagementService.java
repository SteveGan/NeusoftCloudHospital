package com.neuedu.hospitalbackend.service.serviceinterface.basicinfomanagementservice;

import com.neuedu.hospitalbackend.model.vo.ArrangementParam;
import com.neuedu.hospitalbackend.model.vo.ArrangementRuleParam;
import com.neuedu.hospitalbackend.util.CommonResult;

import java.sql.Date;

/**
 * 1.7 医生排班管理
 * @author Raven
 */
public interface ArrangementManagementService {

    /**
     * 设置排班规则
     * @param arrangementRuleParam VO:排班规则
     */
    CommonResult insertArrangementRule(ArrangementRuleParam arrangementRuleParam);

    /**
     * 修改排班结果 v1.0
     * 批量修改
     * @param arrangementParam
     */
    CommonResult modifyArrangements(ArrangementParam arrangementParam);

    /**
     * 修改排班结果 v2.0
     * 修改午别
     * @param arrangementId,timeSlot
     */
    CommonResult modifyArrangement(Integer arrangementId, Byte timeSlot);

    /**
     * 生成排班结果
     * @param arrangementParam
     */
    CommonResult insertArrangement(ArrangementParam arrangementParam);

    /**
     * 查看某科室排班规则
     * @param departmentId 科室id
     */
    CommonResult listArrangementRules(Integer departmentId);

    /**
     * 查看某科室排班结果信息
     * @param startDate
     * @param endDate
     * @param departmentId
     */
    CommonResult listArrangements(Date startDate, Date endDate, Integer departmentId);

}
