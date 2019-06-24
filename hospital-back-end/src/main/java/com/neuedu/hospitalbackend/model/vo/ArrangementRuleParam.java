package com.neuedu.hospitalbackend.model.vo;

import com.neuedu.hospitalbackend.model.po.ArrangementRule;

import java.util.HashMap;
import java.util.List;

public class ArrangementRuleParam {

    private Integer adminId;

    private Integer departmentId;

    private String ruleName;

    private List<ArrangementRule> arrangementRules;

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public List<ArrangementRule> getArrangementRules() {
        return arrangementRules;
    }

    public void setArrangementRules(List<ArrangementRule> arrangementRules) {
        this.arrangementRules = arrangementRules;
    }
}
