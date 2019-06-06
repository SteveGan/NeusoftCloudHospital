package com.neuedu.hospitalbackend.model.dao;

import com.neuedu.hospitalbackend.model.po.Disease;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DiseaseMapper {
    Disease get(Short id);

    int insert(Disease disease);

    int update(Disease disease);

    int delete(Short id);

    String getTypeOfDiseaseById(Integer id);

    List<Disease> listDiseaseByType(Integer type);

    List<Disease> ListChineseDisease();

    List<Disease> ListWesternDisease();
}
