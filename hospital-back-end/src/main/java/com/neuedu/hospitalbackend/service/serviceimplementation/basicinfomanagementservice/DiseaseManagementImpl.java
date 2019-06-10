package com.neuedu.hospitalbackend.service.serviceimplementation.basicinfomanagementservice;

import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.model.dao.DiseaseMapper;
import com.neuedu.hospitalbackend.model.po.Disease;
import com.neuedu.hospitalbackend.service.serviceinterface.basicinfomanagementservice.DiseaseManagementService;
import com.neuedu.hospitalbackend.util.CommonResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.neuedu.hospitalbackend.util.ResultCode.E_602;
import static com.neuedu.hospitalbackend.util.ResultCode.E_801;

@Service
public class DiseaseManagementImpl implements DiseaseManagementService {
    @Resource
    DiseaseMapper diseaseMapper;

    @Override
    public CommonResult getDiseaseById(Short id) {
        Disease disease = diseaseMapper.get(id);
        return CommonResult.success(disease);
    }

    @Override
    public CommonResult insertDisease(Disease disease) {
        int count = diseaseMapper.insert(disease);
        if (count == 0) {
            return CommonResult.fail();
        }
        return CommonResult.success(count);
    }

    @Override
    public CommonResult updateDiseaseById(Disease disease) {
        if (diseaseMapper.get(disease.getId()) == null) {
            return CommonResult.fail(E_602);
        }
        int count = diseaseMapper.update(disease);
        if (count == 0) {
            return CommonResult.fail();
        }
        return CommonResult.success(count);
    }

    @Override
    public CommonResult deleteDiseaseById(Short id) {
        if (diseaseMapper.get(id) == null) {
            return CommonResult.fail(E_602);
        }
        int count = diseaseMapper.delete(id);
        if (count == 0) {
            return CommonResult.fail();
        }
        return CommonResult.success(count);
    }

    @Override
    public CommonResult listDiseaseByType(Integer type){
        JSONObject returnJson = new JSONObject();
        List<HashMap> diseases;
        if(type == 0)//中医疾病
            diseases = diseaseMapper.ListChineseDisease();
        else if(type == 1) //西医疾病
            diseases = diseaseMapper.ListWesternDisease();
        else
            return CommonResult.fail(E_801);//参数异常
        returnJson.put("disease", diseases);
        return CommonResult.success(returnJson);
    }

}
