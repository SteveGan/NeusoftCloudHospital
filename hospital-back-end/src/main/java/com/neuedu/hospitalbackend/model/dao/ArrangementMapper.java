package com.neuedu.hospitalbackend.model.dao;

import com.neuedu.hospitalbackend.model.po.Arrangement;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.List;

@Component
public interface ArrangementMapper {

    List<Arrangement> listAvailableDoctors(@Param("appointmentDate") Date appointmentDate, @Param("registrationLevelId") Integer registrationLevelId, @Param("departmentId") Integer departmentId);
}
