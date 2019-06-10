package com.neuedu.hospitalbackend.service.serviceimplementation.doctorstationservice;

import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.model.bo.Project;
import com.neuedu.hospitalbackend.model.dao.*;
import com.neuedu.hospitalbackend.model.po.Inspection;
import com.neuedu.hospitalbackend.model.po.InspectionProject;
import com.neuedu.hospitalbackend.model.po.TransactionLog;
import com.neuedu.hospitalbackend.model.vo.CollectionParam;
import com.neuedu.hospitalbackend.model.vo.ItemParam;
import com.neuedu.hospitalbackend.model.vo.ProjectParam;
import com.neuedu.hospitalbackend.service.serviceinterface.commonservice.InvoiceService;
import com.neuedu.hospitalbackend.service.serviceinterface.commonservice.TransactionService;
import com.neuedu.hospitalbackend.service.serviceinterface.doctorstationservice.ProjectCollectionManagementService;
import com.neuedu.hospitalbackend.util.CommonResult;
import com.neuedu.hospitalbackend.util.ResultCode;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.Convert;
import java.math.BigDecimal;
import java.util.List;

@Service
public class ProjectCollectionManagementServiceImpl implements ProjectCollectionManagementService {

    @Resource
    private InspectionMapper inspectionMapper;
    @Resource
    private ExaminationMapper examinationMapper;
    @Resource
    private InspectionProjectMapper inspectionProjectMapper;
    @Resource
    private PatientCaseMapper patientCaseMapper;
    @Resource
    private TechProjectMapper techProjectMapper;

    @Resource
    private TransactionService transactionService;
    @Resource
    private InvoiceService invoiceService;


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

        //设定申请清单id
        Integer id = inspectionMapper.getLatestId();
        if (id == null)
            return CommonResult.fail(ResultCode.E_800);
        Integer collectionId = id + 1;
        collectionParam.setCollectionId(collectionId);

        if (collectionType == 1) {
            System.out.println("ppppppppppppp "+projectParams.size());
            //创建inspection(project)
            for(ProjectParam projectParam: projectParams) {
                Integer projectId = projectParam.getProjectId();
                if (id == null)
                    return CommonResult.fail(ResultCode.E_801);
                Integer departmentId = inspectionMapper.getDepartmentIdByProjectId(projectId);
                if (departmentId == null)
                    return CommonResult.fail(ResultCode.E_800);
                String goal = projectParam.getGoal();
                String requirement = projectParam.getRequirement();
                //创建inspection对象
                Inspection inspection = new Inspection();
                inspection.setId(collectionId);
                inspection.setProjectId(projectId);
                inspection.setCaseId(caseId);
                inspection.setCreatorRoleId(applicantRoleId);
                inspection.setDepartmentId(departmentId);
                inspection.setStatus(status);
                inspection.setGoal(goal);
                inspection.setRequirement(requirement);
                //插入数据库
                count = inspectionMapper.insertSelective(inspection);
                if (count <= 0)
                    return CommonResult.fail();

                //创建inspection_project(items)
                List<ItemParam> itemParams = projectParam.getItems();
                System.out.println("iiiiiiiiiiiiiiii "+itemParams.size());
                for(ItemParam itemParam : itemParams) {
                    String projectName = inspectionMapper.getProjectNameByProjectId(projectId);
                    String itemId = itemParam.getItemId();
                    String itemName = itemParam.getItemName();
                    Short amount = itemParam.getAmount();

                    //创建inspectionProject对象
                    InspectionProject inspectionProject = new InspectionProject();
                    inspectionProject.setCollectionId(collectionId);
                    inspectionProject.setProjectId(projectId);
                    inspectionProject.setProjectName(projectName);
                    inspectionProject.setItemId(itemId);
                    inspectionProject.setItemName(itemName);
                    inspectionProject.setAmount(amount);

                    count = inspectionProjectMapper.insert(inspectionProject);
                    if (count <= 0)
                        return CommonResult.fail();

                    //创建缴费清单
                    CommonResult commonResult = insertTransactionLog(collectionParam, projectParam, itemParam);
                    if(commonResult.getCode() != 200){
                        return CommonResult.fail(ResultCode.E_802);//保存失败
                    }
                }
            }
        }

        return CommonResult.success(count);
    }

    /**
     * 创建缴费清单
     */
    public CommonResult insertTransactionLog(CollectionParam collectionParam, ProjectParam projectParam, ItemParam itemParam){
        String newInvoiceCode;//获取可用发票号
        synchronized (this) {
            //通过查询invoice表得到新的缴费记录的发票号并将其状态改为已用
            CommonResult result = invoiceService.getNextInvoiceCode();
            newInvoiceCode = (String) result.getData();
        }
        Integer registrationId = collectionParam.getCaseId();
        Integer patientId = patientCaseMapper.getPatientIdByCaseId(registrationId);
        if(patientId == null)
            return CommonResult.fail(ResultCode.E_800);
        Integer roleId = collectionParam.getApplicantRoleId();
        String type;
        if(collectionParam.getCollectionType() == 1)
            type = "检查费";
        else if(collectionParam.getCollectionType() == 2)
            type = "检验费";
        else if(collectionParam.getCollectionType() == 3)
            type = "处置费";
        else
            return CommonResult.fail(ResultCode.E_801);
        Integer collectionId = collectionParam.getCollectionId();
        Integer projectId = projectParam.getProjectId();
        String itemId = itemParam.getItemId();
        Short amount = itemParam.getAmount();
        if(collectionId == null || projectId == null || itemId == null || amount == null)
            return CommonResult.fail(ResultCode.E_801);
        BigDecimal price = techProjectMapper.getPriceByItemId(itemId);
        if(price == null)
            return CommonResult.fail(ResultCode.E_800);
        System.out.println("amouttttttttttt "+amount +"  priceeeeeeeeeeeee "+price);
        //计算总金额
        BigDecimal totalMoney = new BigDecimal(amount).multiply(price);

        //创建transactionLog对象
        TransactionLog transactionLog = new TransactionLog();
        transactionLog.setInvoiceCode(newInvoiceCode);
        transactionLog.setRegistrationId(registrationId);
        transactionLog.setPatientId(patientId);
        transactionLog.setRoleId(roleId);
        transactionLog.setType(type);
        transactionLog.setCollectionId(collectionId);
        transactionLog.setProjectId(projectId);
        transactionLog.setItemId(itemId);
        transactionLog.setAmount(amount);
        transactionLog.setTotalMoney(totalMoney);

        //增加缴费清单
        return transactionService.insertTransactionLog(transactionLog);
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
