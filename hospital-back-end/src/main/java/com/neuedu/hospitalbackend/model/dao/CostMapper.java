package com.neuedu.hospitalbackend.model.dao;

import com.neuedu.hospitalbackend.model.po.Cost;

import java.util.List;

public interface CostMapper {
    Cost get(Integer id);
    List<Cost> list();
}
