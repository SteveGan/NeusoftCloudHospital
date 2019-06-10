package com.neuedu.hospitalbackend.service.serviceimplementation.basicinfomanagementservice;

import com.neuedu.hospitalbackend.model.dao.ConstantMapper;
import com.neuedu.hospitalbackend.model.po.Constant;
import com.neuedu.hospitalbackend.model.po.Department;
import com.neuedu.hospitalbackend.service.serviceinterface.basicinfomanagementservice.ConstantManagementService;
import com.neuedu.hospitalbackend.util.CommonResult;
import io.swagger.models.auth.In;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Raven
 */
@Service
public class ConstantManagementImpl implements ConstantManagementService {
    @Resource
    ConstantMapper constantMapper;

    @Override
    public List<Constant> list() {
        List<Constant> list = constantMapper.list();
        return null;
    }

    @Override
    public CommonResult listConstantsTree() {
        List<Constant> list = constantMapper.list();
        Map<String, Map<Byte, String>> map = new HashMap<>();

        // 三层判断，没有则插入
        for (Constant constant : list) {
            if (!map.containsKey(constant.getType())) {
                map.put(constant.getType(), new HashMap<>());
            }
            if (!map.get(constant.getType()).containsKey(constant.getChildId())) {
                map.get(constant.getType()).put(constant.getChildId(), "");
            }
            if ("".equals(map.get(constant.getType()).get(constant.getChildId()))) {
                map.get(constant.getType()).put(constant.getChildId(), constant.getName());
            }
        }

        return CommonResult.success(map);
    }
}
