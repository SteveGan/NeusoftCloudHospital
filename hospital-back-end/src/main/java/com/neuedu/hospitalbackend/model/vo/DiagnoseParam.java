package com.neuedu.hospitalbackend.model.vo;


public class DiagnoseParam {

    private String icdCode; //疾病icd编码
    private String startTimeStr; //发病时间


    public String getIcdCode() {
        return icdCode;
    }

    public void setIcdCode(String icdCode) {
        this.icdCode = icdCode;
    }

    public String getStartTimeStr() {
        return startTimeStr;
    }

    public void setStartTimeStr(String startTimeStr) {
        this.startTimeStr = startTimeStr;
    }
}
