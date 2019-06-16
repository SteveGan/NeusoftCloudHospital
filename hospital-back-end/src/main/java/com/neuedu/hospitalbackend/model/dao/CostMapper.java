package com.neuedu.hospitalbackend.model.dao;

import com.neuedu.hospitalbackend.model.po.Cost;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CostMapper {
    Cost get(Integer id);

    List<Cost> list();

    Cost getById(Integer id);

    int insertSelective(Cost cost);

    int update(Cost cost);

    int delete(Integer id);

}
