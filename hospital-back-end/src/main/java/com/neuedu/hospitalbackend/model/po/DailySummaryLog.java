package com.neuedu.hospitalbackend.model.po;

import java.math.BigDecimal;
import java.util.Date;

public class DailySummaryLog {
    private Integer id;

    private Integer casherId;

    private BigDecimal totalMoney;

    private Integer invoiceCodeBegine;

    private Integer invoiceCodeEnd;

    private Date gmtCreate;

    private Date gmtModified;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCasherId() {
        return casherId;
    }

    public void setCasherId(Integer casherId) {
        this.casherId = casherId;
    }

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }

    public Integer getInvoiceCodeBegine() {
        return invoiceCodeBegine;
    }

    public void setInvoiceCodeBegine(Integer invoiceCodeBegine) {
        this.invoiceCodeBegine = invoiceCodeBegine;
    }

    public Integer getInvoiceCodeEnd() {
        return invoiceCodeEnd;
    }

    public void setInvoiceCodeEnd(Integer invoiceCodeEnd) {
        this.invoiceCodeEnd = invoiceCodeEnd;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }
}