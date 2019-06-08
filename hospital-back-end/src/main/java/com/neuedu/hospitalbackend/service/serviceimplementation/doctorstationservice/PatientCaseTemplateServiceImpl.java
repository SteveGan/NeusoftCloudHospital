package com.neuedu.hospitalbackend.service.serviceimplementation.doctorstationservice;

import com.neuedu.hospitalbackend.model.vo.TemplateParam;
import com.neuedu.hospitalbackend.service.serviceinterface.doctorstationservice.PatientCaseTemplateService;
import com.neuedu.hospitalbackend.util.CommonResult;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientCaseTemplateServiceImpl implements PatientCaseTemplateService {

    /**
     * 将当前页面中的内容保存到病理模版中
     * @param templateParam 当前模版中的内容
     */
    @Override
    public CommonResult saveAsCaseTemplate(TemplateParam templateParam){


        return null;
    }


    /**
     * 查询该医生所有可用模版
     * @param doctorId 医生的id
     * @return 可用病历模版集合
     */
    public CommonResult listCaseTemplate(Integer doctorId){
        return null;
    }


    /**
     * 查看该病人的历史过往病历
     * @param patientId 病人的id
     * @return 该病人的所有历史病历
     */
    public CommonResult listPastPatientCase(Integer patientId){
        return null;
    }

}
