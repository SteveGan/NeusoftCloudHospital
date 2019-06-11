package com.neuedu.hospitalbackend.controller.basicinfomanagement;

import com.neuedu.hospitalbackend.model.po.Medicine;
import com.neuedu.hospitalbackend.service.serviceimplementation.basicinfomanagementservice.MedicineManagementImpl;
import com.neuedu.hospitalbackend.util.CommonResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 1.8 / 5.3 药品收费项目管理
 * @author Raven
 */
@RestController
@RequestMapping("/basicinfo")
@CrossOrigin
public class MedicineController {
    @Resource
    private MedicineManagementImpl medicineManagementImpl;

    @ApiOperation("1.8.1 根据id获取药品信息")
    @RequestMapping(value = "/medicine/{id}", method = RequestMethod.GET)
    public CommonResult getMedicineById(@PathVariable Short id) {
        return medicineManagementImpl.getMedicineById(id);
    }

    @ApiOperation("1.8.2 新增药品信息")
    @RequestMapping(value = "/medicine", method = RequestMethod.POST)
    public CommonResult addMedicine(@RequestBody Medicine medicine) {
        return medicineManagementImpl.insertMedicine(medicine);
    }

    @ApiOperation("1.8.3 修改药品信息")
    @RequestMapping(value = "/medicine", method = RequestMethod.PUT)
    public CommonResult updateMedicine(@RequestBody Medicine medicine) {
        return medicineManagementImpl.updateMedicineById(medicine);
    }

    @ApiOperation("1.8.4 删除药品信息")
    @RequestMapping(value = "/medicine/{id}", method = RequestMethod.DELETE)
    public CommonResult deleteMedicine(@PathVariable Short id) {
        return medicineManagementImpl.deleteMedicineById(id);
    }

    @ApiOperation("所有中药/西药")
    @RequestMapping(value = "/medicinetype/{type}", method = RequestMethod.GET)
    public CommonResult listTraditionalMedicine(@PathVariable Integer type) {
        return medicineManagementImpl.listMedicineByType(type);
    }

}
