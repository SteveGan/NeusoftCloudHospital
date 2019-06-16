package com.neuedu.hospitalbackend.service.serviceimplementation.doctorstationservice;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
import java.util.ArrayList;
import java.util.HashMap;
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
    private RecipeMapper recipeMapper;

    @Resource
    private TransactionService transactionService;
    @Resource
    private InvoiceService invoiceService;

    /**
     * 根据当前病历号，找到目前所有的检验申请单
     * @param caseId
     */
    @Override
    public CommonResult listCollections(Integer caseId, Integer type){
        //检查1   检验2   处置3
        JSONObject returnJson = new JSONObject();
        JSONArray collectionArray = new JSONArray();
        List<HashMap> collections = new ArrayList<>();
        if (type == 1)
            collections = inspectionMapper.listCollectionInfo(caseId);
        else if(type == 2)
            collections = examinationMapper.listCollectionInfo(caseId);
        else if(type == 3)
            collections = treatmentMapper.listCollectionInfo(caseId);

        for(HashMap collection:collections) {
            //collection信息
            JSONObject collectionJson = new JSONObject();
            Long collectionIdLong = (Long)collection.get("collectionId");
            Integer collectionId = new Integer(String.valueOf(collectionIdLong));
            collectionJson.put("collectionId", collectionId);
            collectionJson.put("applicantRoleId", collection.get("applicantRoleId"));
            JSONArray projectArray = new JSONArray();
            List<HashMap> projects = new ArrayList<>();
            if (type == 1)
                projects = inspectionMapper.listProjectInfo(collectionId);
            else if(type == 2)
                projects = examinationMapper.listProjectInfo(collectionId);
            else if(type == 3)
                projects = treatmentMapper.listProjectInfo(collectionId);
            for(HashMap project:projects){
                //project信息
                JSONObject projectJson = new JSONObject();
                Integer projectId = (Integer)project.get("projectId");
                projectJson.put("projectId",projectId );
                projectJson.put("projectName", project.get("projectName"));
                projectJson.put("departmentId", project.get("departmentId"));
                projectJson.put("departmentName", project.get("departmentName"));
                projectJson.put("status", project.get("status"));
                projectJson.put("goal", project.get("goal"));
                projectJson.put("requirement", project.get("requirement"));
                JSONArray itemArray = new JSONArray();
                if(type == 1 || type == 2) {
                    projectJson.put("resultDescription", project.get("resultDescription"));
                    projectJson.put("resultImage", project.get("resultImage"));
                    projectJson.put("advice", project.get("advice"));
                    List<HashMap> items = new ArrayList<>();
                    if (type == 1)
                        items = inspectionMapper.listItems(collectionId, projectId);
                    else if (type == 2)
                        items = examinationMapper.listItems(collectionId, projectId);
                    for (HashMap item : items) {
                        //item信息
                        JSONObject itemJson = new JSONObject();
                        itemJson.put("itemId", item.get("itemId"));
                        itemJson.put("itemName", item.get("itemName"));
                        itemJson.put("amount", item.get("amount"));
                        itemArray.add(itemJson);
                    }
                }
                projectJson.put("items", itemArray);
                projectArray.add(projectJson);
            }
            collectionJson.put("projects", projectArray);
            collectionArray.add(collectionJson);
        }
        returnJson.put("collections", collectionArray);
        return CommonResult.success(returnJson);
    }




    /**
     * 申请新的申请清单
     * @Param collectionType
     */
    @Override
    public CommonResult applyNewCollection(Integer collectionType){
        JSONObject returnJson = new JSONObject();

        //参数检验
        if(collectionType == null)
            return CommonResult.fail(ResultCode.E_801);

        //设定申请清单id
        int count;
        Integer collectionId;
        if (collectionType == 1) {
            collectionId = inspectionMapper.getLatestId();
            if (collectionId == null)//数据库为空
                collectionId = 20000000;
            collectionId = collectionId + 1;
            Inspection inspection = new Inspection();
            inspection.setId(collectionId);
            inspection.setProjectId(0);//主键不能为空
            count = inspectionMapper.insertSelective(inspection);
        }
        else if (collectionType == 2) {
            collectionId = examinationMapper.getLatestId();
            if (collectionId == null)//数据库为空
                collectionId = 30000000;
            collectionId = collectionId + 1;
            Examination examination = new Examination();
            examination.setId(collectionId);
            examination.setProjectId(0);//主键不能为空
            count = examinationMapper.insertSelective(examination);
        }
        else if(collectionType == 3){
            collectionId = treatmentMapper.getLatestId();
            if (collectionId == null)//数据库为空
                collectionId = 40000000;
            collectionId = collectionId + 1;
            Treatment treatment = new Treatment();
            treatment.setId(collectionId);
            treatment.setProjectId(0);//主键不能为空
            count = treatmentMapper.insertSelective(treatment);
        }

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
        return chooseByType(collectionParam, 1);
    }

    /**
     * 开立申请清单
     * @param collectionParam
     */
    @Override
    public CommonResult submitPresentCollection(CollectionParam collectionParam) {
        return chooseByType(collectionParam, 2);
    }

    /**
     * 根据项目选择方法
     * @param collectionParam
     * @param operation
     * @return
     */
    public CommonResult chooseByType(CollectionParam collectionParam, int operation){
        Integer collectionType = collectionParam.getCollectionType();
        if (collectionType == null)
            return CommonResult.fail(ResultCode.E_801);
        else if (collectionType == 1)
            return saveInspection(collectionParam, operation);
        else if (collectionType == 2)
            return saveExamination(collectionParam, operation);
        else if (collectionType == 3)
            return saveTreatment(collectionParam, operation);
        else
            return CommonResult.fail(ResultCode.E_801);
    }


    /**
     * 暂存/开立 检查清单
     * @param collectionParam
     */
    public CommonResult saveInspection(CollectionParam collectionParam, int operation){
        int count = 0;
//        String newInvoiceCode = ""; //发票号
        Integer caseId = collectionParam.getCaseId();
        Integer collectionId = collectionParam.getCollectionId();
        Integer applicantRoleId = collectionParam.getApplicantRoleId();
        List<ProjectParam> projectParams = collectionParam.getProjects();

        //参数检查
        if(caseId == null || collectionId == null || applicantRoleId == null)
            return CommonResult.fail(ResultCode.E_801);

        //若删除或清空
        if(projectParams.size() == 0){
            inspectionMapper.deleteByCollectionId(collectionId);//删除项目
            inspectionProjectMapper.deleteItemsByCollectionId(collectionId);//删除小项
            Inspection inspection = new Inspection();
            inspection.setId(collectionId);
            inspection.setProjectId(0);
            inspection.setCaseId(caseId);
            inspection.setCreatorRoleId(applicantRoleId);
            count = inspectionMapper.insertSelective(inspection);//保存collectionId
            if(count <= 0)
                return CommonResult.fail(ResultCode.E_802);
            return CommonResult.success(count);
        }

//        //若开立，获取可用发票号
//        if(operation == 2){
//            synchronized (this) {
//                //通过查询invoice表得到新的缴费记录的发票号并将其状态改为已用
//                CommonResult result = invoiceService.getNextInvoiceCode();
//                newInvoiceCode = (String) result.getData();
//            }
//        }

        //更新清单内容
        List<Integer> existedProjectIds = inspectionMapper.listProjectIdsByCollectionId(collectionId); //数据库该清单中项目
        for(ProjectParam projectParam: projectParams) {
            Integer projectId = projectParam.getProjectId();
            if(projectId == null)
                return CommonResult.fail(ResultCode.E_801);
            //创建inspection对象
            Inspection inspection = new Inspection();
            inspection.setId(collectionId);
            inspection.setProjectId(projectId);
            inspection.setStatus(projectParam.getStatus());
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
                        existedItemIds.remove(itemId);
                    }
                    //若小项不存在，插入小项
                    else {
                        count = insertInspectionProject(collectionId, projectId, itemParam);
                        if(count <= 0)
                            return CommonResult.fail(ResultCode.E_802);
                    }
                    //若开立，创建缴费清单
                    if (operation == 2) {
                        CommonResult commonResult = insertTransactionLog(collectionParam, projectParam, itemParam);
                        if (commonResult.getCode() != 200)
                            return CommonResult.fail(ResultCode.E_802);//保存失败
                    }
                }
                //剩余已存在小项，删除
                for (String leftItemId : existedItemIds) {
                    inspectionProjectMapper.delete(collectionId, projectId, leftItemId);
                }
                existedProjectIds.remove(projectId);
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
                    if (operation == 2) {
                        CommonResult commonResult = insertTransactionLog(collectionParam, projectParam, itemParam);
                        if (commonResult.getCode() != 200)
                            return CommonResult.fail(ResultCode.E_802);//保存失败
                    }
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

        return CommonResult.success(count);
    }


    /**
     * 暂存/开立 检验清单
     * @param collectionParam
     */
    public CommonResult saveExamination(CollectionParam collectionParam, int operation){
        int count = 0;
//        String newInvoiceCode = ""; //发票号
        Integer caseId = collectionParam.getCaseId();
        Integer collectionId = collectionParam.getCollectionId();
        Integer applicantRoleId = collectionParam.getApplicantRoleId();
        List<ProjectParam> projectParams = collectionParam.getProjects();

        //参数检查
        if(caseId == null || collectionId == null || applicantRoleId == null)
            return CommonResult.fail(ResultCode.E_801);


        //若删除或清空
        if(projectParams.size() == 0){
            examinationMapper.deleteByCollectionId(collectionId);//删除项目
            examinationProjectMapper.deleteItemsByCollectionId(collectionId);//删除小项
            Examination examination = new Examination();
            examination.setId(collectionId);
            examination.setProjectId(0);
            examination.setCaseId(caseId);
            examination.setCreatorRoleId(applicantRoleId);
            count = examinationMapper.insertSelective(examination);//保存collectionId
            if(count <= 0)
                return CommonResult.fail(ResultCode.E_802);
            return CommonResult.success(count);
        }

//        //若开立，获取可用发票号
//        if(operation == 2){
//            synchronized (this) {
//                //通过查询invoice表得到新的缴费记录的发票号并将其状态改为已用
//                CommonResult result = invoiceService.getNextInvoiceCode();
//                newInvoiceCode = (String) result.getData();
//            }
//        }

        //更新清单内容
        List<Integer> existedProjectIds = examinationMapper.listProjectIdsByCollectionId(collectionId); //数据库该清单中项目
        for(ProjectParam projectParam: projectParams) {
            Integer projectId = projectParam.getProjectId();
            if(projectId == null)
                return CommonResult.fail(ResultCode.E_801);
            //创建examination对象
            Examination examination = new Examination();
            examination.setId(collectionId);
            examination.setProjectId(projectId);
            examination.setStatus(projectParam.getStatus());
            examination.setGoal(projectParam.getGoal());
            examination.setRequirement(projectParam.getRequirement());

            //若检验项目已在数据库，更新项目内容
            if(existedProjectIds.contains(projectId)) {
                //更新项目基本信息
                count = examinationMapper.updateInfo(examination);
                if (count <= 0)
                    return CommonResult.fail();
                //更新项目中小项信息
                List<ItemParam> itemParams = projectParam.getItems();
                List<String> existedItemIds = examinationProjectMapper.
                        listItemIdsByCollectionIdAndProjectId(collectionId, projectId); //数据库该项目中小项
                for (ItemParam itemParam : itemParams) {
                    String itemId = itemParam.getItemId();
                    if (itemId == null)
                        return CommonResult.fail(ResultCode.E_801);
                    //若小项已存在，更新小项
                    if (existedItemIds.contains(itemId)) {
                        Short amount = itemParam.getAmount();
                        count = examinationProjectMapper.updateAmount(collectionId, projectId, itemId, amount);
                        if (count <= 0)
                            return CommonResult.fail();
                        existedItemIds.remove(itemId);
                    }
                    //若小项不存在，插入小项
                    else {
                        count = insertExaminationProject(collectionId, projectId, itemParam);
                        if(count <= 0)
                            return CommonResult.fail(ResultCode.E_802);
                    }
                    //若开立，创建缴费清单
                    if (operation == 2) {
                        CommonResult commonResult = insertTransactionLog(collectionParam, projectParam, itemParam);
                        if (commonResult.getCode() != 200)
                            return CommonResult.fail(ResultCode.E_802);//保存失败
                    }
                }
                //剩余已存在小项，删除
                for (String leftItemId : existedItemIds) {
                    examinationProjectMapper.delete(collectionId, projectId, leftItemId);
                }
                existedProjectIds.remove(projectId);
            }

            //若检验项目不在数据库，插入
            else{
                //创建项目
                Integer departmentId = techProjectMapper.getDepartmentIdByProjectId(projectId);
                System.out.println(departmentId);
                if (departmentId == null)
                    return CommonResult.fail(ResultCode.E_800);
                examination.setDepartmentId(departmentId);
                examination.setCaseId(caseId);
                examination.setCreatorRoleId(applicantRoleId);
                count = examinationMapper.insertSelective(examination);
                if (count <= 0)
                    return CommonResult.fail();
                //创建小项
                List<ItemParam> itemParams = projectParam.getItems();
                for(ItemParam itemParam : itemParams) {
                    count = insertExaminationProject(collectionId, projectId, itemParam);
                    if(count <= 0)
                        return CommonResult.fail(ResultCode.E_802);
                    //若开立，创建缴费清单
                    if (operation == 2) {
                        CommonResult commonResult = insertTransactionLog(collectionParam, projectParam, itemParam);
                        if (commonResult.getCode() != 200)
                            return CommonResult.fail(ResultCode.E_802);//保存失败
                    }
                }
            }
        }
        //剩余已存在检验项目，删除
        for(Integer leftProjectId: existedProjectIds){
            //清单中删除项目
            examinationMapper.delete(collectionId, leftProjectId);
            //删除小项
            examinationProjectMapper.deleteItemsByCidAndPid(collectionId, leftProjectId);
        }

        return CommonResult.success(count);
    }


    /**
     * 暂存/开立 处置清单
     * @param collectionParam
     */
    public CommonResult saveTreatment(CollectionParam collectionParam, int operation){
        int count = 0;
//        String newInvoiceCode = ""; //发票号
        Integer caseId = collectionParam.getCaseId();
        Integer collectionId = collectionParam.getCollectionId();
        Integer applicantRoleId = collectionParam.getApplicantRoleId();
        List<ProjectParam> projectParams = collectionParam.getProjects();

        //参数检查
        if(caseId == null || collectionId == null || applicantRoleId == null)
            return CommonResult.fail(ResultCode.E_801);


        //若删除或清空
        if(projectParams.size() == 0){
            treatmentMapper.deleteByCollectionId(collectionId);//删除项目
            Treatment treatment = new Treatment();
            treatment.setId(collectionId);
            treatment.setProjectId(0);
            treatment.setCaseId(caseId);
            treatment.setCreatorRoleId(applicantRoleId);
            count = treatmentMapper.insertSelective(treatment);//保存collectionId
            if(count <= 0)
                return CommonResult.fail(ResultCode.E_802);
            return CommonResult.success(count);
        }

//        //若开立，获取可用发票号
//        if(operation == 2){
//            synchronized (this) {
//                //通过查询invoice表得到新的缴费记录的发票号并将其状态改为已用
//                CommonResult result = invoiceService.getNextInvoiceCode();
//                newInvoiceCode = (String) result.getData();
//            }
//        }

        //更新清单内容
        List<Integer> existedProjectIds = treatmentMapper.listProjectIdsByCollectionId(collectionId); //数据库该清单中项目
        for(ProjectParam projectParam: projectParams) {
            Integer projectId = projectParam.getProjectId();
            if(projectId == null)
                return CommonResult.fail(ResultCode.E_801);
            //创建treatment对象
            Treatment treatment = new Treatment();
            treatment.setId(collectionId);
            treatment.setProjectId(projectId);
            treatment.setStatus(projectParam.getStatus());

            //若处置项目已在数据库，更新项目内容
            if(existedProjectIds.contains(projectId)) {
                //更新项目基本信息
               count = treatmentMapper.updateInfo(treatment);
                //若开立，创建缴费清单
                if (operation == 2) {
                    CommonResult commonResult = insertTransactionLog(collectionParam, projectParam, null);
                    if (commonResult.getCode() != 200)
                        return CommonResult.fail(ResultCode.E_802);//保存失败
                }
                existedProjectIds.remove(projectId);
            }
            //若处置项目不在数据库，插入
            else{
                //创建项目
                Integer departmentId = techProjectMapper.getDepartmentIdByProjectId(projectId);
                if (departmentId == null)
                    return CommonResult.fail(ResultCode.E_800);
                treatment.setDepartmentId(departmentId);
                treatment.setCaseId(caseId);
                treatment.setCreatorRoleId(applicantRoleId);
                count = treatmentMapper.insertSelective(treatment);
                if (count <= 0)
                    return CommonResult.fail();
                //若开立，创建缴费清单
                if (operation == 2) {
                    CommonResult commonResult = insertTransactionLog(collectionParam, projectParam, null);
                    if (commonResult.getCode() != 200)
                        return CommonResult.fail(ResultCode.E_802);//保存失败
                }
                existedProjectIds.remove(projectId);
            }
        }
        //剩余已存在处置项目，删除
        for(Integer leftProjectId: existedProjectIds){
            treatmentMapper.delete(collectionId, leftProjectId);
        }

        return CommonResult.success(count);
    }

    /**
     * 插入检查小项
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
     * 插入检验小项
     * @param collectionId
     * @param projectId,itemParam
     */
    public int insertExaminationProject(Integer collectionId, Integer projectId, ItemParam itemParam) {
        String projectName = techProjectMapper.getProjectNameByProjectId(projectId);
        ExaminationProject examinationProject = new ExaminationProject();
        examinationProject.setCollectionId(collectionId);
        examinationProject.setProjectId(projectId);
        examinationProject.setProjectName(projectName);
        examinationProject.setItemId(itemParam.getItemId());
        examinationProject.setItemName(itemParam.getItemName());
        examinationProject.setAmount(itemParam.getAmount());
        int count = examinationProjectMapper.insert(examinationProject);
        return count;
    }

    /**
     * 创建缴费清单
     */
    public CommonResult insertTransactionLog(CollectionParam collectionParam, ProjectParam projectParam,
                                             ItemParam itemParam){
        Integer registrationId = collectionParam.getCaseId();
        Integer patientId = patientCaseMapper.getPatientIdByCaseId(registrationId);
        if(patientId == null)
            return CommonResult.fail(ResultCode.E_800);
        Integer typeInt = collectionParam.getCollectionType();
        String type;
        if(typeInt == 1)
            type = "检查费";
        else if(typeInt == 2)
            type = "检验费";
        else if(typeInt == 3)
            type = "处置费";
        else
            return CommonResult.fail(ResultCode.E_801);
        Integer collectionId = collectionParam.getCollectionId();
        Integer projectId = projectParam.getProjectId();
        String itemId;
        String itemName = "";
        BigDecimal price;
        Short amount;
        BigDecimal totalMoney; //计算总金额
        if(typeInt == 1 || typeInt == 2) {
            itemId = itemParam.getItemId();
            itemName = itemParam.getItemName();
            price = techProjectMapper.getPriceByItemId(itemId);
            amount = itemParam.getAmount();
            totalMoney = new BigDecimal(amount).multiply(price);
        }
        else{
            price = techProjectMapper.getPriceByProjectId(projectId);
            totalMoney = price;
            itemId = null;
            amount = 1;
        }

        //创建transactionLog对象
        TransactionLog transactionLog = new TransactionLog();
//        transactionLog.setInvoiceCode(newInvoiceCode);
        transactionLog.setRegistrationId(registrationId);
        transactionLog.setPatientId(patientId);
        transactionLog.setType(type);
        transactionLog.setCollectionId(collectionId);
        transactionLog.setProjectId(projectId);
        transactionLog.setItemId(itemId);
        transactionLog.setItemName(itemName);
        transactionLog.setAmount(amount);
        transactionLog.setTotalMoney(totalMoney);

        //增加缴费清单
        return transactionService.insertTransactionLog(transactionLog);
    }


    /**
     * 作废申请项目
     * 已开立的项目才可以被作废
     * @param collectionId
     */
    public CommonResult cancelSubmittedCollection(Integer collectionId, Integer type){
        int count = 0;
        Byte status = 3;

        if (type == 1){ // 检查
            count = inspectionMapper.updateStatusToCancel(collectionId, status);
        }
        else if (type == 2){ // 检验
            count = examinationMapper.updateStatusToCancel(collectionId, status);
        }
        else if (type == 3){ //处置
            count = treatmentMapper.updateStatusToCancel(collectionId, status);
        }
        else
            return CommonResult.fail(ResultCode.E_802);

        if(count <= 0)
            return CommonResult.fail(ResultCode.E_804);
        return CommonResult.success(count);
    }

    /**
     * 删除检验检查处置清单或处方
     * @param collectionId,type
     * @param type 1.检查 2.检验 3.处置 4.处方
     * @return
     */
    public CommonResult deleteCollection(Integer collectionId, Integer type){
        if(type == 1) {
            inspectionMapper.deleteByCollectionId(collectionId);
            inspectionProjectMapper.deleteItemsByCollectionId(collectionId);
        }
        else if(type == 2){
            examinationMapper.deleteByCollectionId(collectionId);
            examinationProjectMapper.deleteItemsByCollectionId(collectionId);
        }
        else if(type == 3){
            treatmentMapper.deleteByCollectionId(collectionId);
        }
        else if(type == 4){
            recipeMapper.deleteById(collectionId);
        }
        else
            return CommonResult.fail(ResultCode.E_801);
        return CommonResult.success(null);
    }

    /**
     * 获取项目检查结果
     * @param projectParam
     */
    public CommonResult getProjectResult(ProjectParam projectParam){
        JSONObject returnJson = new JSONObject();
        Integer collectionId = projectParam.getCollectionId();
        Integer projectId = projectParam.getProjectId();
        Integer projectType = projectParam.getProjectType();
        HashMap result;

        if(projectType == 1) //检查
            result = inspectionMapper.getResultByCIdAndPId(collectionId, projectId);
        else if(projectType == 2)  //检验
            result = examinationMapper.getResultByCIdAndPId(collectionId, projectId);
        else
            return CommonResult.fail(ResultCode.E_801);

        returnJson.put("result", result);
        return CommonResult.success(returnJson);
    }
}
