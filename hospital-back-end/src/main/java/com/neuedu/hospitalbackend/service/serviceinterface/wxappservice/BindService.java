package com.neuedu.hospitalbackend.service.serviceinterface.wxappservice;

import com.neuedu.hospitalbackend.util.CommonResult;

/**
 * @Author: Raven
 * @Date: 2019/6/11 4:40 PM
 */
public interface BindService {

    CommonResult bind(String idCard);
}
