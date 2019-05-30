package com.neuedu.hospitalbackend.service.serviceinterface.basicinfomanagementservice;

import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.model.vo.LoginParam;

public interface OauthService {
    Object login(LoginParam loginParam);
    JSONObject listAllUsersAndRoles();
}
