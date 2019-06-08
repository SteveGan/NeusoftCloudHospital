package com.neuedu.hospitalbackend.model.vo;


public class DiagnoseParam {

    private String diseaseIcdCode; //疾病icd编码
    private String startTimeStr; //发病时间

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

    public String getStartTimeStr() {
        return startTimeStr;
    }

    public void setStartTimeStr(String startTimeStr) {
        this.startTimeStr = startTimeStr;
    }
}
