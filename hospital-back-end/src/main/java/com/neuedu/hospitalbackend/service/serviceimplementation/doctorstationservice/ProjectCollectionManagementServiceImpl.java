package com.neuedu.hospitalbackend.service.serviceimplementation.doctorstationservice;

import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.model.bo.Project;
import com.neuedu.hospitalbackend.model.dao.*;
import com.neuedu.hospitalbackend.model.po.*;
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
import java.math.BigDecimal;
import java.util.List;

@Service
public class ProjectCollectionManagementServiceImpl implements ProjectCollectionManagementService {

    @Resource
    private InspectionMapper inspectionMapper;
    @Resource
    private InspectionProjectMapper inspectionProjectMapper;
    @Resource
    private ExaminationMapper examinationMapper;
    @Resource
    private ExaminationProjectMapper examinationProjectMapper;
    @Resource
    private PatientCaseMapper patientCaseMapper;
    @Resource
    private TechProjectMapper techProjectMapper;
    @Resource
    private TreatmentMapper treatmentMapper;

    @Resource
    private TransactionService transactionService;
    @Resource
    private InvoiceService invoiceService;



    /**
     * 申请新的申请清单
     * @param collectionParam
     */
    public CommonResult applyNewCollection(CollectionParam collectionParam){
        JSONObject returnJson = new JSONObject();
        Integer roleId = collectionParam.getApplicantRoleId();
        Integer caseId = collectionParam.getCaseId();
        Integer collectionType = collectionParam.getCollectionType();

        //参数检验
        if(roleId == null || caseId == null || collectionType == null)
            return CommonResult.fail(ResultCode.E_801);

        //设定申请清单id
        int count;
        Integer collectionId;
        if (collectionType == 1) {
            collectionId = inspectionMapper.getLatestId();
            if (collectionId == null)
                return CommonResult.fail(ResultCode.E_800);
            collectionId = collectionId + 1;
            Inspection inspection = new Inspection();
            inspection.setId(collectionId);
            inspection.setCaseId(caseId);
            inspection.setCreatorRoleId(roleId);
            count = inspectionMapper.insertSelective(inspection);
        }
        else if (collectionType == 2) {
            collectionId = examinationMapper.getLatestId();
            if (collectionId == null)
                return CommonResult.fail(ResultCode.E_800);
            collectionId = collectionId + 1;
            Examination examination = new Examination();
            examination.setId(collectionId);
            examination.setCaseId(caseId);
            examination.setCreatorRoleId(roleId);
            count = examinationMapper.insertSelective(examination);
        }
        //TODO 处置
//        else if(collectionType == 3)
//            id = treatmentMapper.getLastestId();
        else
            return CommonResult.fail(ResultCode.E_801);

        if(count <= 0)
            return CommonResult.fail(ResultCode.E_802);

        returnJson.put("collectionId", collectionId);
        return CommonResult.success(returnJson);
    }


    /**
     * 暂存申请清单
     * @param collectionParam
     */
    @Override
    public CommonResult savePresentCollection(CollectionParam collectionParam) {
        Byte status = 1;
        return chooseByType(collectionParam, status);
    }

    /**
     * 开立申请清单
     * @param collectionParam
     */
    @Override
    public CommonResult submitPresentCollection(CollectionParam collectionParam) {
        Byte status = 2;
        return chooseByType(collectionParam, status);
    }

    /**
     * 根据项目选择方法
     * @param collectionParam
     * @param status
     * @return
     */
    public CommonResult chooseByType(CollectionParam collectionParam, Byte status){
        Integer collectionType = collectionParam.getCollectionType();
        if (collectionType == null)
            return CommonResult.fail(ResultCode.E_801);
        else if (collectionType == 1)
            return saveInspection(collectionParam, status); //暂存状态: 1
        else if (collectionType == 2)
            return saveExamination(collectionParam, status); //暂存状态: 1
        else if (collectionType == 3)
            return saveTreatment(collectionParam, status); //暂存状态: 1
        else
            return CommonResult.fail(ResultCode.E_801);
    }


    /**
     * 暂存/开立 检查清单
     * @param collectionParam
     */
    public CommonResult saveInspection(CollectionParam collectionParam, Byte status){
        int count = 0;
        Integer caseId = collectionParam.getCaseId();
        Integer collectionId = collectionParam.getCollectionId();
        Byte curStatus = collectionParam.getStatus();
        Integer applicantRoleId = collectionParam.getApplicantRoleId();

        //参数检查
        if(caseId == null || collectionId == null || curStatus == null || applicantRoleId == null)
            return CommonResult.fail(ResultCode.E_801);
        //状态检验
        if(curStatus != 1)
            return CommonResult.fail(ResultCode.E_804);

        //更新清单内容
        List<ProjectParam> projectParams = collectionParam.getProjects();
        List<Integer> existedProjectIds = inspectionMapper.listProjectIdsByCollectionId(collectionId); //数据库该清单中项目
        for(ProjectParam projectParam: projectParams) {
            Integer projectId = projectParam.getProjectId();
            if(projectId == null)
                return CommonResult.fail(ResultCode.E_801);
            //创建inspection对象
            Inspection inspection = new Inspection();
            inspection.setId(collectionId);
            inspection.setProjectId(projectId);
            inspection.setStatus(status);
            inspection.setGoal(projectParam.getGoal());
            inspection.setRequirement(projectParam.getRequirement());

            //若检查项目已在数据库，更新项目内容
            if(existedProjectIds.contains(projectId)) {
                //更新项目基本信息
                count = inspectionMapper.updateInfo(inspection);
                if (count <= 0)
                    return CommonResult.fail();
                //更新项目中小项信息
                List<ItemParam> itemParams = projectParam.getItems();
                List<String> existedItemIds = inspectionProjectMapper.
                        listItemIdsByCollectionIdAndProjectId(collectionId, projectId); //数据库该项目中小项
                for (ItemParam itemParam : itemParams) {
                    String itemId = itemParam.getItemId();
                    if (itemId == null)
                        return CommonResult.fail(ResultCode.E_801);
                    //若小项已存在，更新小项
                    if (existedItemIds.contains(itemId)) {
                        Short amount = itemParam.getAmount();
                        count = inspectionProjectMapper.updateAmount(collectionId, projectId, itemId, amount);
                        if (count <= 0)
                            return CommonResult.fail();
                    }
                    //若小项不存在，插入小项
                    else {
                        count = insertInspectionProject(collectionId, projectId, itemParam);
                        if(count <= 0)
                            return CommonResult.fail(ResultCode.E_802);
                    }
                    //若开立，创建缴费清单
                    if (status == 2) {
                        CommonResult commonResult = insertTransactionLog(collectionParam, projectParam, itemParam);
                        if (commonResult.getCode() != 200)
                            return CommonResult.fail(ResultCode.E_802);//保存失败
                    }
                }
                //剩余已存在小项，删除
                for (String leftItemId : existedItemIds) {
                    inspectionProjectMapper.delete(collectionId, projectId, leftItemId);
                }
            }

            //若检查项目不在数据库，插入
            else{
                //创建项目
                Integer departmentId = techProjectMapper.getDepartmentIdByProjectId(projectId);
                if (departmentId == null)
                    return CommonResult.fail(ResultCode.E_800);
                inspection.setDepartmentId(departmentId);
                inspection.setCaseId(caseId);
                inspection.setCreatorRoleId(applicantRoleId);
                count = inspectionMapper.insertSelective(inspection);
                if (count <= 0)
                    return CommonResult.fail();
                //创建小项
                List<ItemParam> itemParams = projectParam.getItems();
                for(ItemParam itemParam : itemParams) {
                    count = insertInspectionProject(collectionId, projectId, itemParam);
                    if(count <= 0)
                        return CommonResult.fail(ResultCode.E_802);
                    //若开立，创建缴费清单
                    if (status == 2) {
                        CommonResult commonResult = insertTransactionLog(collectionParam, projectParam, itemParam);
                        if (commonResult.getCode() != 200)
                            return CommonResult.fail(ResultCode.E_802);//保存失败
                    }
                }
            }

            //剩余已存在检查项目，删除
            for(Integer leftProjectId: existedProjectIds){
                //清单中删除项目
                inspectionMapper.delete(collectionId, leftProjectId);
                //删除小项
                inspectionProjectMapper.deleteItemsByCidAndPid(collectionId, leftProjectId);
            }
        }

        return CommonResult.success(count);
    }

    /**
     * 插入小项到数据库
     * @param collectionId
     * @param projectId,itemParam
     */
    public int insertInspectionProject(Integer collectionId, Integer projectId, ItemParam itemParam) {
        String projectName = techProjectMapper.getProjectNameByProjectId(projectId);
        InspectionProject inspectionProject = new InspectionProject();
        inspectionProject.setCollectionId(collectionId);
        inspectionProject.setProjectId(projectId);
        inspectionProject.setProjectName(projectName);
        inspectionProject.setItemId(itemParam.getItemId());
        inspectionProject.setItemName(itemParam.getItemName());
        inspectionProject.setAmount(itemParam.getAmount());
        int count = inspectionProjectMapper.insert(inspectionProject);
        return count;
    }













    /**
     * 暂存/开立 检验清单
     * @param collectionParam
     */
    public CommonResult saveExamination(CollectionParam collectionParam, Byte status){
        int count = 0;
        Integer caseId = collectionParam.getCaseId();
//        Byte status = collectionParam.getStatus();
        Integer applicantRoleId = collectionParam.getApplicantRoleId();
        List<ProjectParam> projectParams = collectionParam.getProjects();

        //设定申请清单id
        Integer id = examinationMapper.getLatestId();
        if (id == null)
            return CommonResult.fail(ResultCode.E_800);
        Integer collectionId = id + 1;
        collectionParam.setCollectionId(collectionId);

        //创建inspection(project)
        for(ProjectParam projectParam: projectParams) {
            Integer projectId = projectParam.getProjectId();
            if (id == null)
                return CommonResult.fail(ResultCode.E_801);
            Integer departmentId = techProjectMapper.getDepartmentIdByProjectId(projectId);
            if (departmentId == null)
                return CommonResult.fail(ResultCode.E_800);
            String goal = projectParam.getGoal();
            String requirement = projectParam.getRequirement();
            //创建inspection对象
            Examination examination= new Examination();
            examination.setId(collectionId);
            examination.setProjectId(projectId);
            examination.setCaseId(caseId);
            examination.setCreatorRoleId(applicantRoleId);
            examination.setDepartmentId(departmentId);
            examination.setStatus(status);
            examination.setGoal(goal);
            examination.setRequirement(requirement);
            //插入数据库
            count = examinationMapper.insertSelective(examination);
            if (count <= 0)
                return CommonResult.fail();

            //创建inspection_project(items)
            List<ItemParam> itemParams = projectParam.getItems();
            for(ItemParam itemParam : itemParams) {
                String projectName = techProjectMapper.getProjectNameByProjectId(projectId);
                String itemId = itemParam.getItemId();
                String itemName = itemParam.getItemName();
                Short amount = itemParam.getAmount();

                //创建inspectionProject对象
                ExaminationProject examinationProject = new ExaminationProject();
                examinationProject.setCollectionId(collectionId);
                examinationProject.setProjectId(projectId);
                examinationProject.setProjectName(projectName);
                examinationProject.setItemId(itemId);
                examinationProject.setItemName(itemName);
                examinationProject.setAmount(amount);
                //插入数据库
                count = examinationProjectMapper.insert(examinationProject);
                if (count <= 0)
                    return CommonResult.fail();

                //创建缴费清单
                CommonResult commonResult = insertTransactionLog(collectionParam, projectParam, itemParam);
                if(commonResult.getCode() != 200){
                    return CommonResult.fail(ResultCode.E_802);//保存失败
                }
            }
        }
        return CommonResult.success(count);
    }

    /**
     * 加入新的处置组
     * @param collectionParam
     */
    public CommonResult saveTreatment(CollectionParam collectionParam, Byte status){
        return null;
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
