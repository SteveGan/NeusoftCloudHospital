package com.neuedu.hospitalbackend.model.vo;

import com.neuedu.hospitalbackend.model.po.TransactionLog;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

public class DailySummaryParam {
    private Integer cashierId;
    private List<HashMap> invoiceCollection;
    private String beginDateStr;
    private String endDateStr;
    private String invoiceCodeBegin;
    private String invoiceCodeEnd;
    private BigDecimal totalMoney; //统计时间段内的总金额
    private String createDateStr; //日结单创建时间

    public Integer getCashierId() {
        return cashierId;
    }

    public void setCashierId(Integer cashierId) {
        this.cashierId = cashierId;
    }

    public List<HashMap> getInvoiceCollection() {
        return invoiceCollection;
    }

    public void setInvoiceCollection(List<HashMap> invoiceCollection) {
        this.invoiceCollection = invoiceCollection;
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

    public String getInvoiceCodeBegin() {
        return invoiceCodeBegin;
    }

    public void setInvoiceCodeBegin(String invoiceCodeBegin) {
        this.invoiceCodeBegin = invoiceCodeBegin;
    }

    public String getInvoiceCodeEnd() {
        return invoiceCodeEnd;
    }

    public void setInvoiceCodeEnd(String invoiceCodeEnd) {
        this.invoiceCodeEnd = invoiceCodeEnd;
    }

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }

    public String getCreateDateStr() {
        return createDateStr;
    }

    public void setCreateDateStr(String createDateStr) {
        this.createDateStr = createDateStr;
    }
}
