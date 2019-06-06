package com.neuedu.hospitalbackend.model.vo;

import java.sql.Date;

public class DiagnoseParam {

    private Integer diseaseId; //疾病id
    private String startTimeStr; //发病时间

    public Integer getDiseaseId() {
        return diseaseId;
    }

    public void setDiseaseId(Integer diseaseId) {
        this.diseaseId = diseaseId;
    }

    public String getStartTimeStr() {
        return startTimeStr;
    }

    public void setStartTimeStr(String startTimeStr) {
        this.startTimeStr = startTimeStr;
    }
}
