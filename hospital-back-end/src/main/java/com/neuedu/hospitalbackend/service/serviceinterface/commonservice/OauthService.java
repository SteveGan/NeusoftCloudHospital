package com.neuedu.hospitalbackend.service.serviceinterface.commonservice;

import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.model.vo.LoginParam;
import com.neuedu.hospitalbackend.util.CommonResult;

public interface OauthService {
    CommonResult login(LoginParam loginParam);
    JSONObject listAllUsersAndRoles();
}
