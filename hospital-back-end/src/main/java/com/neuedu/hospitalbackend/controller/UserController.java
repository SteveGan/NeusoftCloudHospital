package com.neuedu.hospitalbackend.controller;


import com.neuedu.hospitalbackend.model.vo.UserAvatarParam;
import com.neuedu.hospitalbackend.model.vo.UserPasswordParam;
import com.neuedu.hospitalbackend.service.serviceinterface.commonservice.UserService;
import com.neuedu.hospitalbackend.util.CommonResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author Steve
 */

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController
{

    @Resource
    private UserService userService;

    @ApiOperation("设置用户头像")
    @RequestMapping(value="/avatar/modification", method= RequestMethod.POST)
    public CommonResult setUserAvatar(@RequestBody UserAvatarParam userAvatarParam){
        return userService.setUserAvatar(userAvatarParam.getId(),userAvatarParam.getAvatarUrl());
    }

    @ApiOperation("更新用户密码")
    @RequestMapping(value="/password/modification", method = RequestMethod.POST)
    public CommonResult setUserPassword(@RequestBody UserPasswordParam userPasswordParam){
        return userService.setUserPassword(userPasswordParam.getId(), userPasswordParam.getOldPassword(), userPasswordParam.getNewPassword());
    }

}
