package com.neuedu.hospitalbackend.service.serviceinterface.doctorstationservice;


import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.model.poback.Project;

import java.util.List;

/**
 * 包含了医生开 检查/检验 需要用到的service方法
 *
 * @author Steve
 */
public interface ProjectCollectionManagementService {


    /**
     * 得到所有的检查/检验项目
     * @param obj 包含了一个字段，可以告诉方法内逻辑：操作的是检查还是检验，
     *            后面的方法默认有这个字段
     * @return 所有的检查/检验项目
     */
    List<Project> listAllProject(JSONObject obj);


    /**
     * 加入新的检查/检验组
     * @param obj 当前检查/检验组的信息
     */
    void insertProjectCollection(JSONObject obj);


    /**
     * 更新 检查/检验 组
     * @param obj 当前检查/检验组的信息
     */
    void updateProjectCollection(JSONObject obj);


    /**
     * 删除 检查/检验组
     * @param projectCollectionId 检查/检验组 id
     */
    void deleteProjectCollection(Integer projectCollectionId);

}
