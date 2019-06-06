package com.neuedu.hospitalbackend.service.serviceimplementation.doctorstationservice;

import com.neuedu.hospitalbackend.model.vo.ProjectParam;
import com.neuedu.hospitalbackend.util.CommonResult;
import org.springframework.stereotype.Service;

@Service
public class ProjectCollectionManagementServiceImpl {

    /**
     * 加入新的检查/检验组
     * @param projectParam: caseId,creatorRoleId,projectType,projectId,goal,requirement
     */
    CommonResult insertProjectCollection(ProjectParam projectParam){
        Integer caseId = projectParam.getCaseId();
        Integer creatorRoleId = projectParam.getCreatorRoleId();
        Integer projectType = projectParam.getProjectType();
        Integer projectId = projectParam.getProjectId();
        String goal = projectParam.getGoal();
        String requirement = projectParam.getRequirement();


        return null;
    }

}
