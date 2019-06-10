package com.neuedu.hospitalbackend.controller;

import com.neuedu.hospitalbackend.model.vo.RecipeParam;
import com.neuedu.hospitalbackend.service.serviceinterface.pharmacystationservice.PharmacyService;
import com.neuedu.hospitalbackend.util.CommonResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pharmacy")
@CrossOrigin
public class PharmacyController {

    @Autowired
    private PharmacyService pharmacyService;

    @ApiOperation("根据患者的病历号，查询开立的处方信息")
    @RequestMapping(value = "/recipe/{id}", method = RequestMethod.GET)
    public CommonResult listRecipeInfo(@PathVariable(value = "id") Integer registrationId){
        return pharmacyService.listRecipeByRegistrationId(registrationId);
    }

    @ApiOperation("执行发药操作")
    @RequestMapping(value = "/delivery", method = RequestMethod.POST)
    public CommonResult deliverMedicine(@RequestBody List<RecipeParam> recipeParams){
        return pharmacyService.deliverMedicine(recipeParams);
    }

    @ApiOperation("执行退药操作")
    @RequestMapping(value = "/return", method = RequestMethod.POST)
    public CommonResult returnMedicine(@RequestBody List<RecipeParam> recipeParams){
        return pharmacyService.returnMedicine(recipeParams);
    }
}
