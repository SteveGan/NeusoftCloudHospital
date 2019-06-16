package com.neuedu.hospitalbackend.service.serviceinterface.basicinfomanagementservice;

import com.neuedu.hospitalbackend.model.vo.ArrangementParam;
import com.neuedu.hospitalbackend.model.vo.ArrangementRuleParam;
import com.neuedu.hospitalbackend.util.CommonResult;

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
     * 生成排班规则
     * @param arrangementParam
     */
    CommonResult insertArrangement(ArrangementParam arrangementParam);

    /**
     * 查看某科室排班规则
     * @param departmentId 科室id
     */
    CommonResult listArrangementRules(Integer departmentId);

}
