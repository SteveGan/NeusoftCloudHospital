package com.neuedu.hospitalbackend.service.serviceimplementation.doctorstationservice;

import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.model.po.Patient;
import com.neuedu.hospitalbackend.service.serviceinterface.doctorstationservice.PreliminaryCaseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PreliminaryCaseServiceImpl implements PreliminaryCaseService {

    /**
     * get all  waited patient of the doctor
     * @param doctorId of the doctor
     * @return list of patient
     */
    @Override
    public List<Patient> listWaitingPatients(Integer doctorId){
        return null;
    }




    /**
     * 将当前病历首页中的内容暂时存入数据库
     * @param caseInfo 当前病历首页中的内容
     */
    public void savePreCase(JSONObject caseInfo){

    }

    /**
     * 提交最终版病历首页中的内容保存到数据库, 注意：最终版本
     * @param caseInfo 当前病历首页中的内容
     */
    public void submitPreCase(JSONObject caseInfo){

    }

}
