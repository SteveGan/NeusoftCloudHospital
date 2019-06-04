package com.neuedu.hospitalbackend.service.serviceimplementation.basicinfomanagementservice;

import com.neuedu.hospitalbackend.model.dao.DiseaseMapper;
import com.neuedu.hospitalbackend.model.po.Disease;
import com.neuedu.hospitalbackend.service.serviceinterface.basicinfomanagementservice.DiseaseManagementService;
import com.neuedu.hospitalbackend.util.CommonResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static com.neuedu.hospitalbackend.util.ResultCode.E_602;

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
}
