package com.neuedu.hospitalbackend.model.po;

import java.util.Date;

public class TransactionExceptionLog {
    private Integer id;

    private String originalInvoiceCode;

    private String newInvoiceCode;

    private String reverseInvoiceCode;

    private Integer roleId;

    private String reason;

    private Date gmtCreate;

    private Date gmtModified;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOriginalInvoiceCode() {
        return originalInvoiceCode;
    }

    public void setOriginalInvoiceCode(String originalInvoiceCode) {
        this.originalInvoiceCode = originalInvoiceCode == null ? null : originalInvoiceCode.trim();
    }

    public String getNewInvoiceCode() {
        return newInvoiceCode;
    }

    public void setNewInvoiceCode(String newInvoiceCode) {
        this.newInvoiceCode = newInvoiceCode == null ? null : newInvoiceCode.trim();
    }

    public String getReverseInvoiceCode() {
        return reverseInvoiceCode;
    }

    public void setReverseInvoiceCode(String reverseInvoiceCode) {
        this.reverseInvoiceCode = reverseInvoiceCode == null ? null : reverseInvoiceCode.trim();
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
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