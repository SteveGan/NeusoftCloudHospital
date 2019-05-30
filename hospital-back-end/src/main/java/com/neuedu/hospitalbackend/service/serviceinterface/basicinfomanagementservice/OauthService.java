package com.neuedu.hospitalbackend.service.serviceinterface.basicinfomanagementservice;

import com.alibaba.fastjson.JSONArray;
import com.neuedu.hospitalbackend.model.dto.LoginParam;

public interface OauthService {
    Object login(LoginParam loginParam);
    JSONArray listAllUsersAndRoles();
}
