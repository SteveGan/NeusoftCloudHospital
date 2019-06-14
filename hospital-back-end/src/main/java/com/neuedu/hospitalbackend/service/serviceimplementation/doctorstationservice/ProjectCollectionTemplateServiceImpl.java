package com.neuedu.hospitalbackend.service.serviceimplementation.doctorstationservice;

import com.neuedu.hospitalbackend.model.dao.ExaminationTemplateMapper;
import com.neuedu.hospitalbackend.model.dao.InspectionTemplateMapper;
import com.neuedu.hospitalbackend.model.dao.TreatmentTemplateMapper;
import com.neuedu.hospitalbackend.model.po.ExaminationTemplate;
import com.neuedu.hospitalbackend.model.po.InspectionTemplate;
import com.neuedu.hospitalbackend.model.po.TreatmentTemplate;
import com.neuedu.hospitalbackend.model.vo.CollectionTemplateParam;
import com.neuedu.hospitalbackend.model.vo.ProjectTemplateParam;
import com.neuedu.hospitalbackend.service.serviceinterface.doctorstationservice.ProjectCollectionTemplateService;
import com.neuedu.hospitalbackend.util.CommonResult;
import com.neuedu.hospitalbackend.util.ResultCode;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ProjectCollectionTemplateServiceImpl implements ProjectCollectionTemplateService {

    @Resource
    ExaminationTemplateMapper examinationTemplateMapper;
    @Resource
    InspectionTemplateMapper inspectionTemplateMapper;
    @Resource
    TreatmentTemplateMapper treatmentTemplateMapper;


    /**
     * 申请检查检验处置模板
     */
    public CommonResult insertCollectionTemplate(CollectionTemplateParam collectionTemplateParam){
        int count = 0;
        Integer type = collectionTemplateParam.getType();
        String name = collectionTemplateParam.getNewName();
        Integer roleId = collectionTemplateParam.getRoleId();
        Integer departmentId = collectionTemplateParam.getDepartmentId();

        //参数检查
        if(roleId == null || name == null || departmentId == null || (type != 1 && type != 2 && type !=3))
            return CommonResult.fail(ResultCode.E_801);
        //对于一个role，模板名称是否已存在
        if(false == isAvailable(roleId, name, type))
            return CommonResult.fail(ResultCode.E_806);

        //插入项目模板信息
        List<ProjectTemplateParam> projectTemplateParams = collectionTemplateParam.getProjects();
        for (ProjectTemplateParam projectTemplateParam: projectTemplateParams){
            //参数检验
            if (projectTemplateParam.getProjectId() == null)
                return CommonResult.fail(ResultCode.E_801);

            //插入处置项目模板
            if(type == 3) {
                if (200 != saveItems(collectionTemplateParam, projectTemplateParam, null).getCode()) {
                    return CommonResult.fail(ResultCode.E_802);
                }
            }
            //插入检查检验项目以及小项
            else {
                List<String> items = projectTemplateParam.getItems();
                for (String item : items) {
                    if (200 != saveItems(collectionTemplateParam, projectTemplateParam, item).getCode()) {
                        return CommonResult.fail(ResultCode.E_802);
                    }
                }
            }
        }
        return CommonResult.success(count);
    }

    /**
     * 根据项目类型插入项目小项
     */
    public CommonResult saveItems(CollectionTemplateParam collectionTemplateParam,
                                  ProjectTemplateParam projectTemplateParam, String item){
        int count = 0;
        Integer type = collectionTemplateParam.getType();
        if(type == 1){ //检查项目小项
            InspectionTemplate inspectionTemplate = new InspectionTemplate();
            inspectionTemplate.setName(collectionTemplateParam.getNewName());
            inspectionTemplate.setProjectId(projectTemplateParam.getProjectId());
            inspectionTemplate.setItemId(item);
            inspectionTemplate.setRoleId(collectionTemplateParam.getRoleId());
            inspectionTemplate.setDepartmentId(collectionTemplateParam.getDepartmentId());
            inspectionTemplate.setScope(collectionTemplateParam.getScope());
            inspectionTemplate.setRequirement(projectTemplateParam.getRequirement());
            inspectionTemplate.setGoal(projectTemplateParam.getGoal());
            count = inspectionTemplateMapper.insertSelective(inspectionTemplate);
        }
        else if(type == 2) { //检验项目小项
            ExaminationTemplate examinationTemplate = new ExaminationTemplate();
            examinationTemplate.setName(collectionTemplateParam.getNewName());
            examinationTemplate.setProjectId(projectTemplateParam.getProjectId());
            examinationTemplate.setItemId(item);
            examinationTemplate.setRoleId(collectionTemplateParam.getRoleId());
            examinationTemplate.setDepartmentId(collectionTemplateParam.getDepartmentId());
            examinationTemplate.setScope(collectionTemplateParam.getScope());
            examinationTemplate.setRequirement(projectTemplateParam.getRequirement());
            examinationTemplate.setGoal(projectTemplateParam.getGoal());
            count = examinationTemplateMapper.insertSelective(examinationTemplate);
        }
        else if(type == 3){ //处置项目小项
            TreatmentTemplate treatmentTemplate = new TreatmentTemplate();
            treatmentTemplate.setName(collectionTemplateParam.getNewName());
            treatmentTemplate.setProjectId(projectTemplateParam.getProjectId());
            treatmentTemplate.setRoleId(collectionTemplateParam.getRoleId());
            treatmentTemplate.setDepartmentId(collectionTemplateParam.getDepartmentId());
            treatmentTemplate.setScope(collectionTemplateParam.getScope());
            count = treatmentTemplateMapper.insertSelective(treatmentTemplate);
        }
        if(count <= 0)
            return CommonResult.fail(ResultCode.E_802);
        return CommonResult.success(count);
    }

    /**
     * 对于一个role，模板名称是否已存在
     */
    public Boolean isAvailable(Integer roleId, String name, Integer type) {
        if (type == 1) {
            if (0 != inspectionTemplateMapper.getByRIdAndName(roleId, name).size()) {
                return false;
            }
        } else if (type == 2) {
            if (0 != examinationTemplateMapper.getByRIdAndName(roleId, name).size()) {
                return false;
            }
        } else if (type == 3) {
            if (0 != treatmentTemplateMapper.getByRIdAndName(roleId, name).size()) {
                return false;
            }
        }
        return true;
    }




}
