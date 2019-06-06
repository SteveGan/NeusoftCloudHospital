package com.neuedu.hospitalbackend.controller.basicinfomanagement;

import com.neuedu.hospitalbackend.model.po.NonMedicine;
import com.neuedu.hospitalbackend.service.serviceimplementation.basicinfomanagementservice.NonMedicineManagementImpl;
import com.neuedu.hospitalbackend.util.CommonResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 1.6 非药品收费项目管理
 * @author Raven
 */
@RestController
@RequestMapping("/basicinfo")
@CrossOrigin
public class NonMedicineManagementController {
    @Resource
    private NonMedicineManagementImpl nonMedicineManagementImpl;

    @ApiOperation("1.6.1 根据id获取非药品项目信息")
    @RequestMapping(value = "/non-medicine/{id}", method = RequestMethod.GET)
    public CommonResult getNonMedicineById(@PathVariable Integer id) {
        return nonMedicineManagementImpl.getNonMedicineById(id);
    }

    @ApiOperation("1.6.2 新增疾病非药品项目信息")
    @RequestMapping(value = "/non-medicine", method = RequestMethod.POST)
    public CommonResult addNonMedicine(@RequestBody NonMedicine nonMedicine) {
        return nonMedicineManagementImpl.insertNonMedicine(nonMedicine);
    }

    @ApiOperation("1.6.3 修改疾病非药品项目信息")
    @RequestMapping(value = "/non-medicine", method = RequestMethod.PUT)
    public CommonResult updateNonMedicine(@RequestBody NonMedicine nonMedicine) {
        return nonMedicineManagementImpl.updateNonMedicineById(nonMedicine);
    }

    @ApiOperation("1.6.4 删除疾病非药品项目信息")
    @RequestMapping(value = "/non-medicine/{id}", method = RequestMethod.DELETE)
    public CommonResult deleteNonMedicine(@PathVariable Integer id) {
        return nonMedicineManagementImpl.deleteNonMedicineById(id);
    }

}
