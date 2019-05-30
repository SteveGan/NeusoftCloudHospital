package com.neuedu.hospitalbackend.model.po;

import java.util.Date;

public class TransactionExceptionLog {
    private Integer id;

    private Integer originalInvoiceCode;

    private Integer newInvoiceCode;

    private Integer reverseInvoiceCode;

    private Integer userId;

    private String reason;

    private Date gmtCreate;

    private Date gmtModified;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOriginalInvoiceCode() {
        return originalInvoiceCode;
    }

    public void setOriginalInvoiceCode(Integer originalInvoiceCode) {
        this.originalInvoiceCode = originalInvoiceCode;
    }

    public Integer getNewInvoiceCode() {
        return newInvoiceCode;
    }

    public void setNewInvoiceCode(Integer newInvoiceCode) {
        this.newInvoiceCode = newInvoiceCode;
    }

    public Integer getReverseInvoiceCode() {
        return reverseInvoiceCode;
    }

    public void setReverseInvoiceCode(Integer reverseInvoiceCode) {
        this.reverseInvoiceCode = reverseInvoiceCode;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
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