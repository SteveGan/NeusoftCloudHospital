package com.neuedu.hospitalbackend.websocket.service.serviceimpl;

import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.websocket.service.serviceinterface.QueueNotifyService;
import com.neuedu.hospitalbackend.websocket.vo.OutpatientQueueMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

/**
 * class that implements QueueNotifyService Interface
 * @author Steve
 */
@Service
public class QueueNotifyServiceImpl implements QueueNotifyService {

    private final SimpMessageSendingOperations simpMessageSendingOperations;


    @Autowired
    public QueueNotifyServiceImpl(SimpMessageSendingOperations simpMessageSendingOperations){
        this.simpMessageSendingOperations = simpMessageSendingOperations;
    }

    @Override
    public void notifyOutpatientQueue(String roleId, Integer patientId, String patientName, String code){

        System.out.println("发送websocket广播到 " + "/topic/outpatient/queue/" + roleId);
        this.simpMessageSendingOperations.convertAndSend(
                "/topic/outpatient/queue/" + roleId,
                new OutpatientQueueMessage(patientId, roleId, patientName, code)
        );
    }
}
