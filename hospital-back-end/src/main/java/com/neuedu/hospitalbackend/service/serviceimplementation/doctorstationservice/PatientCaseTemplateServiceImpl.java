package com.neuedu.hospitalbackend.service.serviceimplementation.doctorstationservice;

import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.model.bo.PatientCaseTemplate;
import com.neuedu.hospitalbackend.model.dao.CaseTemplateMapper;
import com.neuedu.hospitalbackend.model.dao.RoleMapper;
import com.neuedu.hospitalbackend.model.vo.PatientCaseTemplateParam;
import com.neuedu.hospitalbackend.service.serviceinterface.doctorstationservice.PatientCaseTemplateService;
import com.neuedu.hospitalbackend.util.CommonResult;

import com.neuedu.hospitalbackend.util.ResultCode;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;


@Service
public class PatientCaseTemplateServiceImpl implements PatientCaseTemplateService {

    @Resource
    private CaseTemplateMapper caseTemplateMapper;
    @Resource
    private RoleMapper roleMapper;


    /**
     * 将当前页面中的内容保存到病历模版中
     * @param patientCaseTemplateParam 当前模版中的内容
     */
    @Override
    public CommonResult saveAsCaseTemplate(PatientCaseTemplateParam patientCaseTemplateParam){
        Integer roleId = patientCaseTemplateParam.getRoleId();
        Byte scope = patientCaseTemplateParam.getScope(); // 1.个人  2.科室  3.全院
        String name = patientCaseTemplateParam.getName();

        //参数检验
        if (roleId == null || scope == null || name == null){
            return CommonResult.fail(ResultCode.E_801);
        }
        //检验名称是否已存在
        if(caseTemplateMapper.getPatientCaseTemplateByRoleIdAndName(roleId, name) != null)
            return CommonResult.fail(ResultCode.E_806);
        //查找部门id
        if(scope == 1 || scope == 2 || scope == 3) {
            Integer departmentId = roleMapper.getDepartmentIdByRoleId(roleId);
            if (departmentId == null)
                return CommonResult.fail(ResultCode.E_800);
            patientCaseTemplateParam.setDepartmentId(departmentId);
        }
        else
            return CommonResult.fail(ResultCode.E_801);

        //插入病历模板
        int count = caseTemplateMapper.insert(patientCaseTemplateParam);

        if(count > 0 )
            return CommonResult.success(count);
        else
            return CommonResult.fail();
    }

    /**
     * 修改病历模板
     * @param patientCaseTemplateParam 模板修改后内容
     */
    public CommonResult modifyPatientCaseTemplate(PatientCaseTemplateParam patientCaseTemplateParam){
        Integer id = patientCaseTemplateParam.getId();
        Integer roleId = patientCaseTemplateParam.getRoleId();
        String name = patientCaseTemplateParam.getName();
        //TODO role权限验证
        //模板名称是否被修改
        String curName = caseTemplateMapper.getNameById(id);
        if(curName == null)
            return CommonResult.fail(ResultCode.E_800);
        if (!curName.equals(name)){ //被修改
            //检验名称是否已存在
            if(caseTemplateMapper.getPatientCaseTemplateByRoleIdAndName(roleId, name) != null)
                return CommonResult.fail(ResultCode.E_806);
        }

        //更新病历模板
        int count = caseTemplateMapper.update(patientCaseTemplateParam);

        if(count > 0 )
            return CommonResult.success(count);
        else
            return CommonResult.fail();
    }

    /**
     * 查询该医生所有可用模版
     * @param roleId 医生的角色id
     * @return 可用病历模版列表
     */
    public CommonResult listPatientCaseTemplate(Integer roleId){
        JSONObject returnJson = new JSONObject();
        //参数检验
        if(roleId == null)
            return CommonResult.fail(ResultCode.E_801);
        //角色所在科室
        Integer departmentId = roleMapper.getDepartmentIdByRoleId(roleId);
        //可用模板列表
        List<HashMap> caseTemplates= caseTemplateMapper.listAvailableByRoleId(roleId, departmentId);
        returnJson.put("caseTemplates", caseTemplates);
        return CommonResult.success(returnJson);
    }

    /**
     * 删除病历模板
     * @param roleId,caseTemplateId
     */
    public CommonResult deletePatientCaseTemplate(Integer roleId, Integer caseTemplateId){
        int count = 0;
        //参数检验
        if (roleId == null || caseTemplateId == null)
            return CommonResult.fail(ResultCode.E_801);
        //权限检验
        if(roleId != caseTemplateMapper.getRoleIdById(caseTemplateId))
            return CommonResult.fail(ResultCode.E_804);
        //删除病历模板
        count = caseTemplateMapper.deleteById(caseTemplateId);

        if(count > 0 )
            return CommonResult.success(count);
        else
            return CommonResult.fail();
    }




    /**
     * 查看该病人的历史过往病历
     * @param patientId 病人的id
     * @return 该病人的所有历史病历
     */
    public CommonResult listPastPatientCase(Integer patientId){
        return null;
    }

}
