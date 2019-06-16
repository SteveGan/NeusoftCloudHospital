package com.neuedu.hospitalbackend.controller.basicinfomanagement;

import com.neuedu.hospitalbackend.model.po.RegistrationLevel;
import com.neuedu.hospitalbackend.service.serviceimplementation.basicinfomanagementservice.RegistrationLevelManagementImpl;
import com.neuedu.hospitalbackend.util.CommonResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 1.4 挂号级别管理
 * @author Raven
 */
@RestController
@RequestMapping("/basicinfo")
@CrossOrigin
public class RegistrationLevelManagementController {
    @Resource
    private RegistrationLevelManagementImpl registrationLevelManagementImpl;

    @ApiOperation("1.4.1 根据id获取挂号级别信息")
    @RequestMapping(value = "/registration-level/{id}", method = RequestMethod.GET)
    public CommonResult getRegistrationLevelById(@PathVariable Short id) {
        return registrationLevelManagementImpl.getRegistrationLevelById(id);
    }

    @ApiOperation("1.4.2 新增挂号级别信息")
    @RequestMapping(value = "/registration-level", method = RequestMethod.POST)
    public CommonResult addRegistrationLevel(@RequestBody RegistrationLevel registrationLevel) {
        return registrationLevelManagementImpl.insertRegistrationLevel(registrationLevel);
    }

    @ApiOperation("1.4.3 修改挂号级别信息")
    @RequestMapping(value = "/registration-level", method = RequestMethod.PUT)
    public CommonResult updateRegistrationLevel(@RequestBody RegistrationLevel registrationLevel) {
        return registrationLevelManagementImpl.updateRegistrationLevelById(registrationLevel);
    }

    @ApiOperation("1.4.4 删除挂号级别信息")
    @RequestMapping(value = "/registration-level/{id}", method = RequestMethod.DELETE)
    public CommonResult deleteRegistrationLevel(@PathVariable Short id) {
        return registrationLevelManagementImpl.deleteRegistrationLevelById(id);
    }

    @ApiOperation("1.4.5 列出全部疾病信息")
    @RequestMapping(value = "/registration-levels", method = RequestMethod.GET)
    public CommonResult listAllRegistrationLevels() {
        return registrationLevelManagementImpl.listAllRegistrationLevels();
    }
}
