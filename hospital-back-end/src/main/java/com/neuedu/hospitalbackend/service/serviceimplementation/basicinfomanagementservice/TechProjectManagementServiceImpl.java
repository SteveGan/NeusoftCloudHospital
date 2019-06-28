package com.neuedu.hospitalbackend.service.serviceimplementation.basicinfomanagementservice;

import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.model.dao.TechProjectMapper;
import com.neuedu.hospitalbackend.service.serviceinterface.basicinfomanagementservice.TechProjectManagementService;
import com.neuedu.hospitalbackend.util.CommonResult;
import com.neuedu.hospitalbackend.util.ResultCode;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service
public class TechProjectManagementServiceImpl implements TechProjectManagementService {

    @Resource
    TechProjectMapper techProjectMapper;

    @Override
    public CommonResult listProjectBasicInfoByType(Integer type) {
        JSONObject returnJson = new JSONObject();
        List<HashMap> info;
        if (type == 1)
            info = techProjectMapper.listProjectBasicInfoByType("检查");
        else if (type == 2)
            info = techProjectMapper.listProjectBasicInfoByType("检验");
        else if (type == 3)
            info = techProjectMapper.listProjectBasicInfoByType("处置");
        else
            return CommonResult.fail(ResultCode.E_801);
        returnJson.put("projects", info);
        return CommonResult.success(info);
    }

    @Override
    public CommonResult listItemBasicInfoByProject(Integer projectId){
        JSONObject returnJson = new JSONObject();
        List<HashMap> items = techProjectMapper.listItemBasicInfoByProject(projectId);
        returnJson.put("items", items);
        return CommonResult.success(items);
    }

}
