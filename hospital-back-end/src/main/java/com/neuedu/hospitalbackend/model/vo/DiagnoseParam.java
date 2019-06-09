package com.neuedu.hospitalbackend.model.vo;


public class DiagnoseParam {

    private String diseaseIcdCode; //疾病icd编码
    private String startTime; //发病时间

    private String diseaseName; //疾病名称


    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    public String getDiseaseIcdCode() {
        return diseaseIcdCode;
    }

    public void setDiseaseIcdCode(String diseaseIcdCode) {
        this.diseaseIcdCode = diseaseIcdCode;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
}
