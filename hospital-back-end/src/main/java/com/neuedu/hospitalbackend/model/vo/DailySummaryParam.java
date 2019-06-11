package com.neuedu.hospitalbackend.model.vo;

import com.neuedu.hospitalbackend.model.po.TransactionLog;

import java.util.List;

public class DailySummaryParam {
    private Integer cashierId;
    private List<TransactionLog> transactionLogs;
    private String beginDateStr;
    private String endDateStr;

    public Integer getCashierId() {
        return cashierId;
    }

    public void setCashierId(Integer cashierId) {
        this.cashierId = cashierId;
    }

    public List<TransactionLog> getTransactionLogs() {
        return transactionLogs;
    }

    public void setTransactionLogs(List<TransactionLog> transactionLogs) {
        this.transactionLogs = transactionLogs;
    }

    public String getBeginDateStr() {
        return beginDateStr;
    }

    public void setBeginDateStr(String beginDateStr) {
        this.beginDateStr = beginDateStr;
    }

    public String getEndDateStr() {
        return endDateStr;
    }

    public void setEndDateStr(String endDateStr) {
        this.endDateStr = endDateStr;
    }
}
