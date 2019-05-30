package com.neuedu.hospitalbackend.model.po;

import java.math.BigDecimal;

public class RegistrationLevel {
    private Byte id;

    private String code;

    private String name;

    private Short limit;

    private BigDecimal cost;

    public Byte getId() {
        return id;
    }

    public void setId(Byte id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Short getLimit() {
        return limit;
    }

    public void setLimit(Short limit) {
        this.limit = limit;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }
}