package com.neuedu.hospitalbackend.service.serviceimplementation.wxappservice;

import com.neuedu.hospitalbackend.model.dao.PatientMapper;
import com.neuedu.hospitalbackend.service.serviceinterface.wxappservice.BindService;
import com.neuedu.hospitalbackend.util.CommonResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: Raven
 * @Date: 2019/6/11 4:42 PM
 */
@Service
public class BindServiceImpl implements BindService {
    @Resource
    private PatientMapper patientMapper;

    @Override
    public CommonResult bind(String idCard) {
        Integer id = patientMapper.getPatientByIdCard(idCard);
        System.out.println(id);
        return CommonResult.success(id);
    }
}
