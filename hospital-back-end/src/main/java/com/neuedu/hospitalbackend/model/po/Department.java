package com.neuedu.hospitalbackend.model.po;

/**
 *
 */
public class Department {
    private int id;
    private String code;
    private String name;
    private String classification;
    private String type;

    public Department() {
    }

    public Department(int id, String code, String name, String classification, String type) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.classification = classification;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
