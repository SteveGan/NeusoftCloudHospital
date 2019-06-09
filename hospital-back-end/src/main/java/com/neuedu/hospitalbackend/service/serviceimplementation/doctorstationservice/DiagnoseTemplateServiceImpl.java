package com.neuedu.hospitalbackend.service.serviceimplementation.doctorstationservice;

import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.model.dao.DiagnoseTemplateMapper;
import com.neuedu.hospitalbackend.model.dao.DiseaseMapper;
import com.neuedu.hospitalbackend.model.po.DiagnoseTemplate;
import com.neuedu.hospitalbackend.model.po.Disease;
import com.neuedu.hospitalbackend.model.vo.DiagnoseParam;
import com.neuedu.hospitalbackend.model.vo.DiagnoseTemplateParam;
import com.neuedu.hospitalbackend.service.serviceinterface.doctorstationservice.DiagnoseTemplateService;
import com.neuedu.hospitalbackend.util.CommonResult;
import com.neuedu.hospitalbackend.util.ResultCode;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service
public class DiagnoseTemplateServiceImpl implements DiagnoseTemplateService {

    @Resource
    private DiagnoseTemplateMapper diagnoseTemplateMapper;
    @Resource
    private DiseaseMapper diseaseMapper;

    /**
     * 创建常用诊断
     * @param diagnoseTemplateParam
     */
    @Override
    public CommonResult saveDiagnoseTemplate(DiagnoseTemplateParam diagnoseTemplateParam){
        int count = 0;
        Integer roleId = diagnoseTemplateParam.getRoleId();
        String name = diagnoseTemplateParam.getName();
        List<DiagnoseParam> diagnoseParams = diagnoseTemplateParam.getDiagnoseList();
        //检验名称是否已存在
        if(diagnoseTemplateMapper.getDiagnoseTemplateByName(name) != null)
            return CommonResult.fail(ResultCode.E_806);
        //一个常用诊断下可以有多个疾病
        for(DiagnoseParam diagnoseParam: diagnoseParams){
            String diseaseIcdCode = diagnoseParam.getDiseaseIcdCode();
            Disease disease = diseaseMapper.getDiseaseByIcdCode(diseaseIcdCode);
            if(disease == null)
                return CommonResult.fail(ResultCode.E_800);
            Byte type;
            if(disease.getName().equals("中医疾病"))
                type = 0;
            else
                type = 1;

            count = diagnoseTemplateMapper.insert(diseaseIcdCode, disease.getName(), type, roleId, name);

            if(count <= 0)
                return CommonResult.fail(ResultCode.E_802);
        }

        return CommonResult.success(count);
    }

    /**
     * 修改常用诊断
     * @param diagnoseTemplateParam
     */
    @Override
    public CommonResult modifyDiagnoseTemplate(DiagnoseTemplateParam diagnoseTemplateParam){
        int count = 0;
        Integer roleId = diagnoseTemplateParam.getRoleId();
        String newName = diagnoseTemplateParam.getNewName();
        String pastName = diagnoseTemplateParam.getName();
        List<DiagnoseParam> diagnoseParams = diagnoseTemplateParam.getDiagnoseList();

        //参数检验
        if(newName == null || pastName == null)
            return CommonResult.fail(ResultCode.E_805);
        if(diagnoseTemplateMapper.getDiagnoseTemplateByName(pastName) == null)
            return CommonResult.fail(ResultCode.E_800);

        //模板名称是否被修改
        if (!newName.equals(pastName)){ //被修改
            //检验名称是否已存在
            if(diagnoseTemplateMapper.getDiagnoseTemplateByName(newName) != null)
                return CommonResult.fail(ResultCode.E_806);
            //修改该常用诊断的名称
            diagnoseTemplateMapper.updateName(pastName, newName);
        }

        //更新常用诊断内容
        List<String> existedDiseaseIcdCodes = diagnoseTemplateMapper.listDiseaseIcdCodesByTemplateName(newName); //该集合已存在的疾病
        for(DiagnoseParam diagnoseParam: diagnoseParams) {
            //若该诊断已存该疾病不更新
            if(existedDiseaseIcdCodes.contains(diagnoseParam.getDiseaseIcdCode())) {
                existedDiseaseIcdCodes.remove(diagnoseParam.getDiseaseIcdCode());
            }
            //若该诊断不存在该诊断，要求更新，则增加该疾病
             else if (!existedDiseaseIcdCodes.contains(diagnoseParam.getDiseaseIcdCode())) {
                Disease disease = diseaseMapper.getDiseaseByIcdCode(diagnoseParam.getDiseaseIcdCode());
                Byte type;
                if(disease.getType().equals("中医疾病"))
                    type = 0;
                else
                    type = 1;
                //插入数据库
                count = diagnoseTemplateMapper.insert(diagnoseParam.getDiseaseIcdCode(), disease.getName(), type, roleId, newName);
                if (count <= 0)
                    return CommonResult.fail(ResultCode.E_802);//保存失败
            }

        }
        //若该诊断已存该疾病，但更新内容中无该疾病，则删除该疾病
        while(!existedDiseaseIcdCodes.isEmpty()) {
            for (String leftDiseaseIcdCode : existedDiseaseIcdCodes)
                count = diagnoseTemplateMapper.deleteByDiseaseIcdCode(leftDiseaseIcdCode);
        }

        if(count > 0 )
            return CommonResult.success(count);
        else
            return CommonResult.fail();
    }

    /**
     * 修改常用诊断
     * @param roleId
     */
    @Override
    public CommonResult listMyDiagnoseTemplate(Integer roleId){
        JSONObject returnJson = new JSONObject();
        //参数检验
        if(roleId == null){
            return CommonResult.fail(ResultCode.E_801);
        }

        List<HashMap> myList = diagnoseTemplateMapper.listMyDiagnoseTemplate(roleId);
        returnJson.put("myDiagnoseTemplates", myList);
        return CommonResult.success(returnJson);
    }

    /**
     * 删除
     * 删除常用诊断
     * @param roleId,diagnoseTemplateName
     * @return
     */
    public CommonResult deleteMyDiagnoseTemplate(Integer roleId, String diagnoseTemplateName){
        int count = 0;
        //参数验证
        if(roleId == null || diagnoseTemplateName == null)
            CommonResult.fail(ResultCode.E_801);
        DiagnoseTemplate diagnoseTemplate = diagnoseTemplateMapper.getDiagnoseTemplateByName(diagnoseTemplateName);
        if(diagnoseTemplate == null)
            CommonResult.fail(ResultCode.E_801);//名称不存在
        //权限验证
        if(roleId != diagnoseTemplate.getRoleId())
            return CommonResult.fail(ResultCode.E_804);

        //删除该名称下所有常用诊断疾病
        count = diagnoseTemplateMapper.deleteByName(diagnoseTemplateName);

        if(count > 0 )
            return CommonResult.success(count);
        else
            return CommonResult.fail();
    }

}
