package com.neuedu.hospitalbackend.service.serviceinterface.commonservice;

import com.neuedu.hospitalbackend.model.poback.Patient;
import com.neuedu.hospitalbackend.model.poback.Registration;

import java.util.List;

public interface PatientService {

    /**
     * get one patient by case id
     * @param caseId of the patient
     * @return the patient
     */
    Patient getPatientByCaseId(Integer caseId);


    /**
     * get the patient by patient names
     * @param patientName of the patient
     * @return list of patient with provided patient name.
     */
    List<Patient> listPatientByPatientName(String patientName);

    /**
     * get the patient by patient id_card
     * @param patientIdCard of the patient
     * @return the patient
     */
    Patient getPatientByIdCard(String patientIdCard);

    /**
     * 通过患者病历号，显示患者挂号信息
     * @param caseId
     * @return
     */
    Registration getPatientInfoByCaseId(Integer caseId);

}
