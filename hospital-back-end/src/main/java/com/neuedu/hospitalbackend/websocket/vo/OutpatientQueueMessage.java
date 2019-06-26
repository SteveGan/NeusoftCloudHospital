package com.neuedu.hospitalbackend.websocket.vo;


/**
 * 通知门诊大屏幕端更新排队信息的消息模版
 */
public class OutpatientQueueMessage {

    //病人的ID
    private Integer caseId;
    //病人的姓名
    private String name;
    //看诊医生的角色ID
    private String roleId;
    //信息类型: "add": 添加新的病人到队列中， "update": 加入新的正在看诊人员， "done": 初诊结束
    private String code;

    public OutpatientQueueMessage() {
    }

    public OutpatientQueueMessage(Integer caseId, String roleId, String name, String code) {
        this.caseId = caseId;
        this.name = name;
        this.roleId = roleId;
        this.code = code;
    }

    public Integer getCaseId() {
        return caseId;
    }

    public void setCaseId(Integer caseId) {
        this.caseId = caseId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
