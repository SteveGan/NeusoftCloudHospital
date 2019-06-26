package com.neuedu.hospitalbackend.websocket.vo;


/**
 * 通知门诊大屏幕端更新排队信息的消息模版
 */
public class OutpatientQueueMessage {

    //病人的ID
    private String patientId;
    //看诊医生的角色ID
    private String roleId;
    //信息类型: "add": 添加新的病人到队列中， "update": 加入新的正在看诊人员， "done": 初诊结束
    private String code;

    public OutpatientQueueMessage() {
    }

    public OutpatientQueueMessage(String patientId, String roleId, String code) {
        this.patientId = patientId;
        this.roleId = roleId;
        this.code = code;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
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
}
