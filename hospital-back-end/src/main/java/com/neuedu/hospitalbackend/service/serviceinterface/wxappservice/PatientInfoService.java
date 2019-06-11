package com.neuedu.hospitalbackend.service.serviceinterface.wxappservice;

import com.neuedu.hospitalbackend.util.CommonResult;

/**
 * @Author: Raven
 * @Date: 2019/6/11 5:54 PM
 */
public interface PatientInfoService {

    CommonResult getPatientInfo(Integer id);
}
