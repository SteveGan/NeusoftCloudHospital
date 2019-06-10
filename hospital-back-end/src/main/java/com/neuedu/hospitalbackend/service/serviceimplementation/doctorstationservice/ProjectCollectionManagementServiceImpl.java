package com.neuedu.hospitalbackend.service.serviceimplementation.doctorstationservice;

import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.model.bo.Project;
import com.neuedu.hospitalbackend.model.dao.ExaminationMapper;
import com.neuedu.hospitalbackend.model.dao.InspectionMapper;
import com.neuedu.hospitalbackend.model.dao.TechProjectMapper;
import com.neuedu.hospitalbackend.model.po.Inspection;
import com.neuedu.hospitalbackend.model.vo.CollectionParam;
import com.neuedu.hospitalbackend.model.vo.ProjectParam;
import com.neuedu.hospitalbackend.service.serviceinterface.doctorstationservice.ProjectCollectionManagementService;
import com.neuedu.hospitalbackend.util.CommonResult;
import com.neuedu.hospitalbackend.util.ResultCode;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ProjectCollectionManagementServiceImpl implements ProjectCollectionManagementService {

    @Resource
    private InspectionMapper inspectionMapper;
    @Resource
    private ExaminationMapper examinationMapper;
    @Resource
    private TechProjectMapper techProjectMapper;


    /**
     * 加入新的检查组
     * @param collectionParam
     */
    @Override
    public CommonResult insertCollection(CollectionParam collectionParam){
        int count = 0;
        Integer caseId = collectionParam.getCaseId();
        Integer collectionType = collectionParam.getCollectionType();
        Byte status = collectionParam.getStatus();
        Integer applicantRoleId = collectionParam.getApplicantRoleId();
        List<ProjectParam> projectParams = collectionParam.getProjects();

        //创建inspection(project)
        if (collectionType == 1) {
            for(ProjectParam projectParam: projectParams) {
                    Integer id = inspectionMapper.getLatestId();
                    Integer projectId = projectParam.getProjectId();
                    if (id == null || projectId == null)
                        return CommonResult.fail(ResultCode.E_801);
                    Integer departmentId = techProjectMapper.getDepartmentIdByProjectId(projectId);//TODO mapper.xml
                    if (departmentId == null)
                        return CommonResult.fail(ResultCode.E_800);
                    String goal = projectParam.getGoal();
                    String requirement = projectParam.getRequirement();

                    Inspection inspection = new Inspection();
                    inspection.setId(id + 1);
                    inspection.setProjectId(projectId);
                    inspection.setCaseId(caseId);
                    inspection.setCreatorRoleId(applicantRoleId);
                    inspection.setDepartmentId(departmentId);//TODO 部门id
                    inspection.setStatus(status);
                    inspection.setGoal(goal);
                    inspection.setRequirement(requirement);

//                    count = inspectionMapper.insertInspection(inspection);
                    if (count <= 0)
                        return CommonResult.fail();
                    else
                        return CommonResult.success(count);
                }

            //创建inspection_project(items)
        }

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
