package com.neuedu.hospitalbackend.model.testDAO;

import com.neuedu.hospitalbackend.model.testPO.ArrangementRule;

public interface ArrangementRuleMapper {
    int insert(ArrangementRule record);

    int insertSelective(ArrangementRule record);
}