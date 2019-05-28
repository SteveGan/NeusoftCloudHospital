package com.neuedu.hospitalbackend.service.serviceinterface.commonservice;

import org.apache.ibatis.annotations.Case;

public interface CaseService {

    /**
     * 通过患者病历号，确定患者挂号状态是否是待诊状态
     * @param caseId
     * @return
     */
    String getCaseStatusByCaseId(Integer caseId);
}
