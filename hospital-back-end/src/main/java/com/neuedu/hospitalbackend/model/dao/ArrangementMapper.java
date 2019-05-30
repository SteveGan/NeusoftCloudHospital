package com.neuedu.hospitalbackend.model.dao;

import com.neuedu.hospitalbackend.model.po.Arrangement;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.List;

public interface ArrangementMapper {
    int insert(Arrangement record);

    int insertSelective(Arrangement record);

    List<Arrangement> listAvailableDoctors(@Param("appointmentDateStr") String appointmentDateStr, @Param("registrationLevelId") Integer registrationLevelId, @Param("departmentId") Integer departmentId);
}