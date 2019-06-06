package com.neuedu.hospitalbackend.model.dao;

import com.neuedu.hospitalbackend.model.po.LogLogin;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: Raven
 * @Date: 2019/6/5 6:52 PM
 */
@Mapper
public interface LogLoginMapper {
    int insert(LogLogin logLogin);
}
