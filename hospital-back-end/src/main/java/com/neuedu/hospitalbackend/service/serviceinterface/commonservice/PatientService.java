package com.neuedu.hospitalbackend.service.serviceinterface.commonservice;

import com.neuedu.hospitalbackend.model.po.Patient;

import java.util.List;

public interface PatientService {

    /**
     * get one patient by case id
     * @param caseId of the patient
     * @return the patient
     */
    public Patient getPatientByCaseId(Integer caseId);


    /**
     * get the patient by patient names
     * @param patientName of the patient
     * @return list of patient with provided patient name.
     */
    public List<Patient> listPatientByPatientName(String patientName);


}
