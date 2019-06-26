package com.neuedu.hospitalbackend.websocket.service.serviceinterface;


/**
 * 排队窗口显示器提醒服务接口
 * @author Steve
 */
public interface QueueNotifyService {


    /**
     * 发送websocket广播，告知屏幕更新
     * 排队列表信息
     * @param roleId 角色ID
     * @param patientId 病人ID
     * @param code "add": 添加新的病人到队列中， "update": 加入新的正在看诊人员， "done": 初诊结束
     */
    void notifyOutpatientQueue(String roleId, String patientId, String code);


}
