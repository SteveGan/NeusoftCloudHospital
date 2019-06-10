package com.neuedu.hospitalbackend.controller;


import com.neuedu.hospitalbackend.model.vo.CaseRecipesParam;
import com.neuedu.hospitalbackend.model.vo.RecipeParam;
import com.neuedu.hospitalbackend.service.serviceinterface.doctorstationservice.RecipeManagementService;
import com.neuedu.hospitalbackend.util.CommonResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recipe")
@CrossOrigin
public class RecipeController {

    @Autowired
    private RecipeManagementService recipeManagementService;


    @ApiOperation("得到当前病历的所有处方")
    @RequestMapping(value = "/invoiceId/{caseId}/{recipeType}", method = RequestMethod.GET)
    public CommonResult listCurrentCaseRecipes(@PathVariable(value="caseId") Integer caseId, @PathVariable(value="recipeType") Integer recipeType){
        return null;
    }


    @ApiOperation("暂存当前页面中所有的处方")
    @RequestMapping(value = "/preservation", method = RequestMethod.PUT)
    public CommonResult saveRecipes(@RequestBody CaseRecipesParam caseRecipesParam){
        return null;
    }



    // 这个次要
    @ApiOperation("（清屏）删除当前病历中未开立的处方")
    @RequestMapping(value = "/clear/{caseId}/{recipeType}", method = RequestMethod.DELETE)
    public CommonResult clearRecipes(@PathVariable(value="caseId") Integer caseId, @PathVariable(value="recipeType") Integer recipeType){
        return null;
    }

    @ApiOperation("开立")
    @RequestMapping(value = "/submission", method = RequestMethod.POST)
    public CommonResult submitRecipes(@RequestBody RecipeParam recipeParam){
        return null;
    }











}
