package com.neuedu.hospitalbackend.service.serviceimplementation.doctorstationservice;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.model.dao.ExaminationTemplateMapper;
import com.neuedu.hospitalbackend.model.dao.InspectionTemplateMapper;
import com.neuedu.hospitalbackend.model.dao.RoleMapper;
import com.neuedu.hospitalbackend.model.dao.TreatmentTemplateMapper;
import com.neuedu.hospitalbackend.model.po.ExaminationTemplate;
import com.neuedu.hospitalbackend.model.po.InspectionTemplate;
import com.neuedu.hospitalbackend.model.po.TreatmentTemplate;
import com.neuedu.hospitalbackend.model.vo.CollectionTemplateParam;
import com.neuedu.hospitalbackend.model.vo.ItemParam;
import com.neuedu.hospitalbackend.model.vo.ProjectTemplateParam;
import com.neuedu.hospitalbackend.service.serviceinterface.doctorstationservice.ProjectCollectionTemplateService;
import com.neuedu.hospitalbackend.util.CommonResult;
import com.neuedu.hospitalbackend.util.ResultCode;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ProjectCollectionTemplateServiceImpl implements ProjectCollectionTemplateService {

    @Resource
    ExaminationTemplateMapper examinationTemplateMapper;
    @Resource
    InspectionTemplateMapper inspectionTemplateMapper;
    @Resource
    TreatmentTemplateMapper treatmentTemplateMapper;
    @Resource
    RoleMapper roleMapper;


    /**
     * 创建申请检查检验处置模板
     */
    @Override
    public CommonResult insertCollectionTemplate(CollectionTemplateParam collectionTemplateParam){
        Integer type = collectionTemplateParam.getType();
        String name = collectionTemplateParam.getNewName();
        Integer roleId = collectionTemplateParam.getRoleId();

        //参数检查
        if(roleId == null || name == null || (type != 1 && type != 2 && type !=3))
            return CommonResult.fail(ResultCode.E_801);
        //对于一个role，模板名称是否已存在
        if(false == isAvailable(roleId, name, type))
            return CommonResult.fail(ResultCode.E_806);
        //role所在科室id
        Integer departmentId = roleMapper.getDepartmentIdByRoleId(roleId);
        collectionTemplateParam.setDepartmentId(departmentId);

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
                List<ItemParam> items = projectTemplateParam.getItems();
                for (ItemParam item : items) {
                    if (200 != saveItems(collectionTemplateParam, projectTemplateParam, item.getItemId()).getCode()) {
                        return CommonResult.fail(ResultCode.E_802);
                    }
                }
            }
        }
        return CommonResult.success(0);
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


    /**
     * 查询检查检验处置模板
     */
   @Override
    public CommonResult listCollectionTemplate(Integer roleId, Integer type){
        //检查1   检验2   处置3
       JSONObject returnJson = new JSONObject();
       JSONArray personalArray = new JSONArray();
       JSONArray departmentArray = new JSONArray();
       JSONArray hospitalArray = new JSONArray();
       List<HashMap> collections = new ArrayList<>();

       Integer departmentId = roleMapper.getDepartmentIdByRoleId(roleId);

       if (type == 1)
           collections = inspectionTemplateMapper.listTemplateNameAndCreator(roleId, departmentId);
       else if(type == 2)
           collections = examinationTemplateMapper.listTemplateNameAndCreator(roleId, departmentId);
       else if(type == 3)
           collections = treatmentTemplateMapper.listTemplateNameAndCreator(roleId, departmentId);

       for(HashMap collection : collections) {
           //模板信息
           JSONObject collectionJson = new JSONObject();
           String name = (String)collection.get("name");
           Integer scope = (Integer)collection.get("scope");
           Integer creator = (Integer)collection.get("roleId");
           collectionJson.put("name", name);
           collectionJson.put("scope", scope);
           collectionJson.put("roleId", creator);
           collectionJson.put("newName", "");
           collectionJson.put("departmentId", 0);
           collectionJson.put("type", type);

           JSONArray projectArray = new JSONArray();
           List<HashMap> projects = new ArrayList<>();
           if (type == 1)
               projects = inspectionTemplateMapper.listProject(creator, name);
           else if(type == 2)
               projects = examinationTemplateMapper.listProject(creator, name);
           else if(type == 3)
               projects = treatmentTemplateMapper.listProject(creator, name);
           for(HashMap project:projects){
               //项目信息
               JSONObject projectJson = new JSONObject();
               String projectName = (String)project.get("projectName");
               Integer projectId = (Integer)project.get("projectId");
               projectJson.put("projectId", projectId);
               projectJson.put("projectName", projectName);
               projectJson.put("requirement", project.get("requirement"));
               projectJson.put("goal", project.get("goal"));
               projectJson.put("departmentId", project.get("departmentId"));
               projectJson.put("departmentName", project.get("departmentName"));
               projectJson.put("resultDescription", "");
               projectJson.put("resultImage", "");
               projectJson.put("status", 0);
               projectJson.put("advice", "");

               //检验检查项目 小项信息
               JSONArray itemArray = new JSONArray();
               List<HashMap> items = new ArrayList<>();
               if (type == 1)
                   items = inspectionTemplateMapper.listItems(roleId, name, projectId);
               else if (type == 2)
                   items = examinationTemplateMapper.listItems(roleId, name, projectId);
               for (HashMap item : items) {
                   //item信息
                   JSONObject itemJson = new JSONObject();
                   itemJson.put("itemId", item.get("itemId"));
                   itemJson.put("itemName", item.get("itemName"));
                   itemJson.put("amount", 1);
                   itemArray.add(itemJson);
               }
               projectJson.put("items", itemArray);
               projectArray.add(projectJson);
           }
           collectionJson.put("projects", projectArray);
           if (scope == 1)
               personalArray.add(collectionJson);
           else if(scope == 2)
               departmentArray.add(collectionJson);
           else if(scope == 3)
               hospitalArray.add(collectionJson);
       }
       returnJson.put("personalTemplates", personalArray);
       returnJson.put("departmentTemplates", departmentArray);
       returnJson.put("hospitalTemplates", hospitalArray);
       return CommonResult.success(returnJson);
    }

    /**
     * 删除检查检验处置模板
     */
    public CommonResult deleteCollectionTemplate(CollectionTemplateParam collectionTemplateParam){
        Integer roleId = collectionTemplateParam.getRoleId();
        String name = collectionTemplateParam.getName();
        Integer type = collectionTemplateParam.getType();

        //参数检查
        if(roleId == null || name == null || (type != 1 && type != 2 && type !=3))
            return CommonResult.fail(ResultCode.E_801);

        if (type == 1) {
            if(0 == inspectionTemplateMapper.getByRIdAndName(roleId, name).size())
                return CommonResult.fail(ResultCode.E_807); //不存在该模板
            inspectionTemplateMapper.deleteTemplateByRoleIdAndName(roleId, name);
        }
        else if(type == 2) {
            if(0 == examinationTemplateMapper.getByRIdAndName(roleId, name).size())
                return CommonResult.fail(ResultCode.E_807); //不存在该模板
            examinationTemplateMapper.deleteTemplateByRoleIdAndName(roleId, name);
        }
        else if(type == 3) {
            if(0 == treatmentTemplateMapper.getByRIdAndName(roleId, name).size())
                return CommonResult.fail(ResultCode.E_807); //不存在该模板
            treatmentTemplateMapper.deleteTemplateByRoleIdAndName(roleId, name);
        }
        else
            return CommonResult.fail(ResultCode.E_801);

        return CommonResult.success(0);

    }

    /**
     * 修改检查检验处置模板
     */
    @Override
    public CommonResult modifyCollectionTemplate(CollectionTemplateParam collectionTemplateParam){
        Integer roleId = collectionTemplateParam.getRoleId();
        String pastName = collectionTemplateParam.getName();//修改前名称
        String newName = collectionTemplateParam.getNewName();//修改后名称
        Integer type = collectionTemplateParam.getType();

        //参数检验
        if(roleId == null || pastName == null || newName == null)
            return CommonResult.fail(ResultCode.E_801);

        //修改名称是否已存在
        if(!newName.equals(pastName)){
            if(type == 1 && 0 != inspectionTemplateMapper.getByRIdAndName(roleId, newName).size())
                return CommonResult.fail(ResultCode.E_806);
            else if(type == 2 && 0 != examinationTemplateMapper.getByRIdAndName(roleId, newName).size())
                return CommonResult.fail(ResultCode.E_806);
            else if(type == 3 && 0 != treatmentTemplateMapper.getByRIdAndName(roleId, newName).size())
                return CommonResult.fail(ResultCode.E_806);
            else if(type != 1 && type != 2 && type != 3)
                return CommonResult.fail(ResultCode.E_801);
        }

        //删除
        if (200 != deleteCollectionTemplate(collectionTemplateParam).getCode())
            return CommonResult.fail();

        //增加
        return insertCollectionTemplate(collectionTemplateParam);
    }




}
