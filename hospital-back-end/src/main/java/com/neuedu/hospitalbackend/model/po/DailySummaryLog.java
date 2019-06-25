package com.neuedu.hospitalbackend.model.po;

import java.math.BigDecimal;
import java.util.Date;

public class DailySummaryLog {
    private Integer id;

    private Integer cashierRoleId;

    private BigDecimal totalMoney;

    private String invoiceCodeBegin;

    private String invoiceCodeEnd;

    private Date beginDate;

    private Date endDate;

    private boolean isCorrect;

    private Date gmtCreate;

    private Date gmtModified;

    public DailySummaryLog(Integer cashierRoleId, BigDecimal totalMoney, String invoiceCodeBegin, String invoiceCodeEnd, Date beginDate, Date endDate) {
        this.cashierRoleId = cashierRoleId;
        this.totalMoney = totalMoney;
        this.invoiceCodeBegin = invoiceCodeBegin;
        this.invoiceCodeEnd = invoiceCodeEnd;
        this.beginDate = beginDate;
        this.endDate = endDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCashierRoleId() {
        return cashierRoleId;
    }

    public void setCashierRoleId(Integer cashierRoleId) {
        this.cashierRoleId = cashierRoleId;
    }

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }

    public String getInvoiceCodeBegin() {
        return invoiceCodeBegin;
    }

    public void setInvoiceCodeBegin(String invoiceCodeBegin) {
        this.invoiceCodeBegin = invoiceCodeBegin == null ? null : invoiceCodeBegin.trim();
    }

    public String getInvoiceCodeEnd() {
        return invoiceCodeEnd;
    }

    public void setInvoiceCodeEnd(String invoiceCodeEnd) {
        this.invoiceCodeEnd = invoiceCodeEnd == null ? null : invoiceCodeEnd.trim();
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
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