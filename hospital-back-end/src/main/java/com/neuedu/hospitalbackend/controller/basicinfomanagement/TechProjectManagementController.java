package com.neuedu.hospitalbackend.controller.basicinfomanagement;

import com.neuedu.hospitalbackend.service.serviceinterface.basicinfomanagementservice.TechProjectManagementService;
import com.neuedu.hospitalbackend.util.CommonResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/project")
@CrossOrigin
public class TechProjectManagementController {

    @Resource
    private TechProjectManagementService techProjectManagementService;


    @ApiOperation("请求所有的检验或检查项目编号名称医以及对应科室名）")
    @RequestMapping(value = "/list/type/{type}", method = RequestMethod.GET)
    public CommonResult listDepartmentsByType(@PathVariable Integer type)
    {
        return techProjectManagementService.listProjectBasicInfoByType(type);
    }

    @ApiOperation("请求项目小项id,name,unit")
    @RequestMapping(value = "/items/{projectId}", method = RequestMethod.GET)
    public CommonResult listItemBasicInfoByProject(@PathVariable Integer projectId)
    {
        return techProjectManagementService.listItemBasicInfoByProject(projectId);
    }


}
