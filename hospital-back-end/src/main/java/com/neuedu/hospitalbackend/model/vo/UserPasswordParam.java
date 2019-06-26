package com.neuedu.hospitalbackend.model.vo;


/**
 * @author Steve
 */
public class UserPasswordParam {
    private Integer id;
    private String oldPassword;
    private String newPassword;

    public UserPasswordParam() {
    }

    public UserPasswordParam(Integer id, String oldPassword, String newPassword) {
        this.id = id;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
