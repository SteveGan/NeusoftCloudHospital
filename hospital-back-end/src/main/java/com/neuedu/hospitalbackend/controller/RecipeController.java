package com.neuedu.hospitalbackend.controller;


import com.neuedu.hospitalbackend.model.vo.RecipeCollectionParam;
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
    @RequestMapping(value = "/list/{caseId}", method = RequestMethod.GET)
    public CommonResult listCurrentCaseRecipes(@PathVariable(value="caseId") Integer caseId){
        return recipeManagementService.listCurrentCaseRecipes(caseId);
    }

    @ApiOperation("新增处方")
    @RequestMapping(value = "/application", method = RequestMethod.POST)
    public CommonResult insertNewRecipe(){
        return recipeManagementService.insertNewRecipe();
    }


    @ApiOperation("暂存处方")
    @RequestMapping(value = "/preservation", method = RequestMethod.PUT)
    public CommonResult preserveRecipes(@RequestBody RecipeCollectionParam recipeCollectionParam){
        return recipeManagementService.preserveRecipes(recipeCollectionParam);
    }


    @ApiOperation("开立处方")
    @RequestMapping(value = "/submission", method = RequestMethod.PUT)
    public CommonResult submitRecipes(@RequestBody RecipeCollectionParam recipeCollectionParam){
        return recipeManagementService.submitRecipes(recipeCollectionParam);
    }



    @ApiOperation("（清屏）删除当前病历中未开立的处方")
    @RequestMapping(value = "/clear/{caseId}/{recipeType}", method = RequestMethod.DELETE)
    public CommonResult clearRecipes(@PathVariable(value="caseId") Integer caseId, @PathVariable(value="recipeType") Integer recipeType){
        return null;
    }

}
