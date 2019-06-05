package com.neuedu.hospitalbackend.model.dao;

import com.neuedu.hospitalbackend.model.po.Constant;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ConstantMapper {
    List<Constant> list();
}
