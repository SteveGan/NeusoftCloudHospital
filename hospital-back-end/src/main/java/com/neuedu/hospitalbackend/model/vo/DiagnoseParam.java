package com.neuedu.hospitalbackend.model.vo;


import java.sql.Date;

public class DiagnoseParam {

    private String diseaseIcdCode; //疾病icd编码
    private String startTimeStr; //发病时间


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
