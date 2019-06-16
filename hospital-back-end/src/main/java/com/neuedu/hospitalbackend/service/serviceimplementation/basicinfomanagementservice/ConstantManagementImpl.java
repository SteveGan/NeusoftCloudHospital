package com.neuedu.hospitalbackend.service.serviceimplementation.basicinfomanagementservice;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.model.dao.ConstantMapper;
import com.neuedu.hospitalbackend.model.po.Constant;
import com.neuedu.hospitalbackend.service.serviceinterface.basicinfomanagementservice.ConstantManagementService;
import com.neuedu.hospitalbackend.util.CommonResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

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

        Map<String, JSONArray> map2 = new HashMap<>();

        // 三层判断，没有则插入
        for (Constant constant : list) {
            if (!map2.containsKey(constant.getType())) {
                map2.put(constant.getType(), new JSONArray());
            }
            JSONObject object = new JSONObject();
            object.put("label", constant.getName());
            object.put("value", constant.getChildId());
            if (!map2.get(constant.getType()).contains(object)) {
                map2.get(constant.getType()).add(object);
            }
//            if ("".equals(map.get(constant.getType()).get(constant.getChildId()))) {
//                map.get(constant.getType()).put(constant.getChildId(), constant.getName());
//            }
        }
        return CommonResult.success(map2);
    }
}
