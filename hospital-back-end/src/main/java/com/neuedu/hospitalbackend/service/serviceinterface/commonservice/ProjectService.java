package com.neuedu.hospitalbackend.service.serviceinterface.commonservice;


import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.model.po.Project;
import com.sun.tools.javac.util.List;

/**
 * 包含检查/检验项目公共服务方法
 */
public interface ProjectService {


    /**
     * 查找所有的检查/检验项目
     * @return 所有的检查/检验项目
     */
    List<Project> listExaminationProject();

    /**
     * 暂存前端传来的 检查/检验 项目
     * @param obj 检查/检验 项目数据
     */
    void saveProject(JSONObject obj);

    /**
     * 确认提交 检查/检验 项目
     * @param obj 检查/检验 项目内容
     */
    void submitProject(JSONObject obj);


    /**
     * 删除还未登记的检查/检验项目：这里我还不确定传入啥参数，先写个JSONObject
     * @param obj 传入的参数
     */
    void deleteProject(JSONObject obj);

}
