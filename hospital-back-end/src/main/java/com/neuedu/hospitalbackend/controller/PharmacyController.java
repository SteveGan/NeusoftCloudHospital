package com.neuedu.hospitalbackend.controller;

import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.model.po.Registration;
import com.neuedu.hospitalbackend.model.vo.RecipeParam;
import com.neuedu.hospitalbackend.service.serviceinterface.commonservice.PatientService;
import com.neuedu.hospitalbackend.service.serviceinterface.pharmacystationservice.PharmacyService;
import com.neuedu.hospitalbackend.util.CommonResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/pharmacy")
@CrossOrigin
public class PharmacyController {

    @Autowired
    private PharmacyService pharmacyService;
    @Autowired
    private PatientService patientService;

    @ApiOperation("根据病历号或日期，查询开立的处方信息")
    @RequestMapping(value = "/recipes", method = RequestMethod.GET)
    public CommonResult listAvailableRecipes(Integer caseId, String chargeDateStr){
        return pharmacyService.listRecipesByRegistrationIdOrDate(caseId, chargeDateStr);
    }

    @ApiOperation("根据患者的病历号，查询开立的处方信息和患者挂号信息")
    @RequestMapping(value = "/recipe/{id}", method = RequestMethod.GET)
    public CommonResult listRecipeInfo(@PathVariable(value = "id") Integer registrationId){
        JSONObject jsonObject = new JSONObject();
        Registration registration = (Registration) patientService.getRegistrationInfo(registrationId).getData();
        List<HashMap> recipe = (List<HashMap>) pharmacyService.listRecipeByRegistrationId(registrationId).getData();
        jsonObject.put("registration", registration);
        jsonObject.put("recipe", recipe);
        return CommonResult.success(jsonObject);
    }

    @ApiOperation("执行发药操作")
    @RequestMapping(value = "/delivery", method = RequestMethod.POST)
    public CommonResult deliverMedicine(@RequestBody List<RecipeParam> recipeParams){
        return pharmacyService.deliverMedicine(recipeParams);
    }

    @ApiOperation("执行退药操作")
    @RequestMapping(value = "/return", method = RequestMethod.POST)
    public CommonResult returnMedicine(@RequestBody List<RecipeParam> recipeParams){
        System.out.println("hahahahah");
        return pharmacyService.returnMedicine(recipeParams);
    }
}
