package com.neuedu.hospitalbackend.model.vo;

import org.springframework.context.annotation.Bean;

public class LoginParam {

    private Integer userId;
    private String password;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
