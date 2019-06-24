package com.neuedu.hospitalbackend.model.dao;

import com.neuedu.hospitalbackend.model.po.TransactionLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public interface TransactionLogMapper {
    int insert(TransactionLog transactionLog);

    int insertSelective(TransactionLog transactionLog);

    int update(TransactionLog transactionLog);

    //在退号时得到挂号的缴费记录
    TransactionLog getLogByRegistrationIdAndType(@Param("registrationId") Integer registrationId, @Param("type") String type);

    //列出 指定病历号的 挂号缴费记录及挂号状态
    HashMap getRegistrationLog(Integer registrationId);

    //列出指定用户的所有发票号、缴费状态
    List<HashMap> listInvoiceCodeAndStatusByRegistrationId(Integer registrationId);

    //列出 指定病历号的所有医技项目(检查/检验/处置) 缴费记录
    List<HashMap> listLogsByTableName(@Param("tableName") String tableName, @Param("registrationId") Integer registrationId);

    //列出 指定病历号的药方 缴费记录
    List<HashMap> listRecipeLogs(Integer registrationId);

    //查询同一发票号下的所有缴费记录
    List<TransactionLog> listLogsByInvoiceCode(String invoiceCode);

    //更改 与退费的项目所在同一发票号的 相关缴费记录状态 --已退费
    int updateSelectiveAsReturned(String invoiceCode);

    List<Integer> listIdByCollectionAndProjectId(@Param("invoiceCode") String invoiceCode, @Param("collectionId") Integer collectionId, @Param("projectId") Integer projectId);

    //列出 指定集合清单、指定项目下的小项详情
    List<TransactionLog> listItemsByCollectionIdAndProjectId(@Param("collectionId") Integer collectionId, @Param("projectId") Integer projectId);

    //根据病历号（必填）、起始日期查询指定患者的所有收费项目列表
    List<TransactionLog> listLogsByRegistrationIdAndDate(@Param("registrationId") Integer registrationId, @Param("beginDateStr") String beginDateStr, @Param("endDateStr") String endDateStr);

    //根据收费员id、起始日期查询指定收费员的所有发票信息
    List<HashMap> listLogsByCashierIdAndDate(@Param("cashierId") Integer cashierId, @Param("beginDateStr") String beginDateStr, @Param("endDateStr") String endDateStr);

    //冻结收费记录
    int updateTransactionLogsByInvoiceCode(String invoiceCode);

    //根据收费员Id,找到其第一条收费记录时间
    String getFirstLogDateByCashierId(Integer cashierId);

    //根据发票号范围和收银员id查询所有发票信息
    List<HashMap> listLogsByInvoiceRangeAndCashierId(@Param("cashierId") Integer cashierId, @Param("invoiceCodeBegin") String invoiceCodeBegin, @Param("invoiceCodeEnd") String invoiceCodeEnd);

    //查询在统计范围内的发票号数量
    int countInvoiceCodeInRangeByCashierId(@Param("cashierId") Integer cashierId, @Param("invoiceCodeBegin") String invoiceCodeBegin, @Param("invoiceCodeEnd") String invoiceCodeEnd);

    //日结时得到不同类型状态的发票号
    List<String> listInvoiceCodeByStatus(@Param("cashierId") Integer cashierId, @Param("invoiceCodeBegin") String invoiceCodeBegin,
                                         @Param("invoiceCodeEnd") String invoiceCodeEnd, @Param("status") Byte status);

    //日结时计算每种费用类型的总金额
    List<HashMap> listTotalMoneyByType(@Param("cashierId") Integer cashierId, @Param("invoiceCodeBegin") String invoiceCodeBegin, @Param("invoiceCodeEnd") String invoiceCodeEnd);

    //开单/执行科室工作量统计
    List<HashMap> calculateMoneyByDepartmentAndType(@Param("tableName") String tableName, @Param("projectIdName") String projectIdName, @Param("roleIdName") String roleIdName,
                                                        @Param("beginDateStr") String beginDateStr, @Param("endDateStr") String endDateStr);

    List<HashMap> countPatientCasesByDepartmentName(@Param("beginDateStr") String beginDateStr, @Param("endDateStr") String endDateStr);
}
