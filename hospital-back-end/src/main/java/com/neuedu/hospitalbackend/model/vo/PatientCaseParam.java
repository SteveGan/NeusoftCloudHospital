package com.neuedu.hospitalbackend.model.vo;

import java.util.List;

/**
 * 门诊病历
 */
public class PatientCaseParam {
    private Integer caseId;
    private String narrate; //主述
    private String curDisease; //现病史
    private String curTreatCondition; //现病治疗情况
    private String pastDisease; //既往史
    private String allergy; //过敏史
    private String physicalCondition; //体格检查
//    private String assistantInspection;//辅助检查
    private List<DiagnoseParam> diagnoses; //诊断

    public String getPastDisease() {
        return pastDisease;
    }

    public List<DiagnoseParam> getDiagnoses() {
        return diagnoses;
    }

    public void setDiagnoses(List<DiagnoseParam> diagnoses) {
        this.diagnoses = diagnoses;
    }

    public void setPastDisease(String pastDisease) {
        this.pastDisease = pastDisease;
    }

    public Integer getCaseId() {
        return caseId;
    }

    public void setCaseId(Integer caseId) {
        this.caseId = caseId;
    }

    public String getNarrate() {
        return narrate;
    }

    public void setNarrate(String narrate) {
        this.narrate = narrate;
    }

    public String getCurDisease() {
        return curDisease;
    }

    public void setCurDisease(String curDisease) {
        this.curDisease = curDisease;
    }

    public String getCurTreatCondition() {
        return curTreatCondition;
    }

    public void setCurTreatCondition(String curTreatCondition) {
        this.curTreatCondition = curTreatCondition;
    }

    public String getAllergy() {
        return allergy;
    }

    public void setAllergy(String allergy) {
        this.allergy = allergy;
    }

    public String getPhysicalCondition() {
        return physicalCondition;
    }

    public void setPhysicalCondition(String physicalCondition) {
        this.physicalCondition = physicalCondition;
    }
}
