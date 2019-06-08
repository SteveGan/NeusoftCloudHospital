package com.neuedu.hospitalbackend.service.serviceimplementation.doctorstationservice;

import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.model.bo.Project;
import com.neuedu.hospitalbackend.model.vo.ProjectParam;
import com.neuedu.hospitalbackend.service.serviceinterface.doctorstationservice.ProjectCollectionManagementService;
import com.neuedu.hospitalbackend.util.CommonResult;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectCollectionManagementServiceImpl implements ProjectCollectionManagementService {

    /**
     * 加入新的检查/检验组
     * @param projectParam: caseId,creatorRoleId,projectType,projectId,goal,requirement
     */
    @Override
    public CommonResult insertProjectCollection(ProjectParam projectParam){
        Integer caseId = projectParam.getCaseId();
        Integer creatorRoleId = projectParam.getCreatorRoleId();
        Integer projectType = projectParam.getProjectType();
        Integer projectId = projectParam.getProjectId();
        String goal = projectParam.getGoal();
        String requirement = projectParam.getRequirement();


        return null;
    }




    /**
     * 得到所有的检查/检验项目
     * @param obj 包含了一个字段，可以告诉方法内逻辑：操作的是检查还是检验，
     *            后面的方法默认有这个字段
     * @return 所有的检查/检验项目
     */
    public List<Project> listAllProject(JSONObject obj){
        return null;
    }

    /**
     * 更新 检查/检验 组
     * @param obj 当前检查/检验组的信息
     */
    public void updateProjectCollection(JSONObject obj){

    }


    /**
     * 删除 检查/检验组
     * @param projectCollectionId 检查/检验组 id
     */
    public void deleteProjectCollection(Integer projectCollectionId){

    }


}
