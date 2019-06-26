package com.neuedu.hospitalbackend.websocket.controller;


import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.websocket.service.serviceinterface.QueueNotifyService;
import com.neuedu.hospitalbackend.websocket.vo.OutpatientQueueMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 用于接受前端http请求，并发送websocket讯息
 * @author Steve
 */
@RestController
@CrossOrigin
@RequestMapping("/notification")
public class NotificationController {

    private final QueueNotifyService queueNotifyService;

    @Autowired
    public NotificationController(QueueNotifyService queueNotifyService){
        this.queueNotifyService = queueNotifyService;
    }


    @RequestMapping(value = "/recipes", method = RequestMethod.GET)
    public void notifyOutpatientQueue(@RequestBody OutpatientQueueMessage outpatientQueueMessage){
        queueNotifyService.notifyOutpatientQueue(outpatientQueueMessage.getRoleId(), outpatientQueueMessage.getPatientId(), outpatientQueueMessage.getCode());
    }

}
