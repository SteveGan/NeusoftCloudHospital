package com.neuedu.hospitalbackend.model.testdao;

import com.neuedu.hospitalbackend.model.po.ArrangementRule;

public interface ArrangementRuleMapper {
    int insert(ArrangementRule record);

    int insertSelective(ArrangementRule record);
}