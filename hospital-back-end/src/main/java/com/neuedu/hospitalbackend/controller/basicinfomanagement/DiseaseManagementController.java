package com.neuedu.hospitalbackend.controller.basicinfomanagement;

import com.neuedu.hospitalbackend.model.po.Disease;
import com.neuedu.hospitalbackend.service.serviceimplementation.basicinfomanagementservice.DiseaseManagementImpl;
import com.neuedu.hospitalbackend.util.CommonResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 1.5 诊断目录管理
 * @author Raven
 */
@RestController
@RequestMapping("/basicinfo")
@CrossOrigin
public class DiseaseManagementController {
    @Resource
    private DiseaseManagementImpl diseaseManagementImpl;

    @ApiOperation("1.5.1 根据id获取疾病信息")
    @RequestMapping(value = "/disease/{id}", method = RequestMethod.GET)
    public CommonResult selectDiseaseById(@PathVariable Short id) {
        return diseaseManagementImpl.getDiseaseById(id);
    }

    @ApiOperation("1.5.2 新增疾病信息")
    @RequestMapping(value = "/disease", method = RequestMethod.POST)
    public CommonResult addDisease(@RequestBody Disease disease) {
        return diseaseManagementImpl.insertDisease(disease);
    }

    @ApiOperation("1.5.3 修改疾病信息")
    @RequestMapping(value = "/disease", method = RequestMethod.PUT)
    public CommonResult updateDisease(@RequestBody Disease disease) {
        return diseaseManagementImpl.updateDiseaseById(disease);
    }

    @ApiOperation("1.5.4 删除疾病信息")
    @RequestMapping(value = "/disease/{id}", method = RequestMethod.DELETE)
    public CommonResult deleteDisease(@PathVariable Short id) {
        return diseaseManagementImpl.deleteDiseaseById(id);
    }

}
