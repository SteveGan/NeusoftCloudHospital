package com.neuedu.hospitalbackend.controller.wxappcontroller;

import com.neuedu.hospitalbackend.model.vo.WxappBindParam;
import com.neuedu.hospitalbackend.util.CommonResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * 用于完成小程序端注册绑定操作
 * @Author: Raven
 * @Date: 2019/6/9 7:42 PM
 */
@RestController
@RequestMapping("/wxapp")
@CrossOrigin
public class BindController {

    @ApiOperation("绑定，在医院有就诊记录的患者才可以绑定，支持扫码绑定")
    @RequestMapping(value = "/bind", method = RequestMethod.POST)
    public CommonResult getPatientNotification(@RequestBody WxappBindParam wxappBindParam)
    {
        return null;
    }
}
