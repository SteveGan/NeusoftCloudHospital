package com.neuedu.hospitalbackend.model.vo;


/**
 * @author Steve
 */
public class UserAvatarParam {
    private Integer id;
    private String avatarUrl;

    public UserAvatarParam() {
    }

    public UserAvatarParam(Integer id, String avatarUrl) {
        this.id = id;
        this.avatarUrl = avatarUrl;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
