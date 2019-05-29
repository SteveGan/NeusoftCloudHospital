package com.neuedu.hospitalbackend;

import com.neuedu.hospitalbackend.controller.RegistrationController;
import com.neuedu.hospitalbackend.model.dto.RegistrationParam;
import org.junit.Test;

import java.sql.Date;

public class ArrangementMapperTest {

    @Test
    public void testListAvailableDoctors(){
        RegistrationController registrationController = new RegistrationController();
        RegistrationParam registrationParam = new RegistrationParam();
        registrationParam.setAppointmentDateStr("2019-05-29");
        registrationParam.setDepartmentId(1);
        registrationParam.setRegistrationLevelId(2);
        System.out.println(registrationController.listAvailableDoctors(registrationParam));
    }
}
