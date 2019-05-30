package com.neuedu.hospitalbackend.service.serviceinterface.basicinfomanagementservice;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.model.dto.LoginParam;

public interface OauthService {
    Object login(LoginParam loginParam);
    JSONObject listAllUsersAndRoles();
}
