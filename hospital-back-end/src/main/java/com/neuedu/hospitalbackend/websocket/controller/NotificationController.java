package com.neuedu.hospitalbackend.websocket.controller;


import com.neuedu.hospitalbackend.websocket.service.serviceinterface.QueueNotifyService;
import com.neuedu.hospitalbackend.websocket.vo.OutpatientQueueMessage;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 用于接受前端http请求，并发送websocket讯息
 * @author Steve
 */
@RestController
@RequestMapping("/notification")
@CrossOrigin
public class NotificationController {

    private final QueueNotifyService queueNotifyService;

    @Autowired
    public NotificationController(QueueNotifyService queueNotifyService){
        this.queueNotifyService = queueNotifyService;
    }


    @ApiOperation("请求更新门诊医生叫号排队页面")
    @RequestMapping(value = "/outpatient/queue", method = RequestMethod.POST)
    public String notifyOutpatientQueue(@RequestBody OutpatientQueueMessage outpatientQueueMessage){
        queueNotifyService.notifyOutpatientQueue(outpatientQueueMessage.getRoleId(), outpatientQueueMessage.getCaseId(), outpatientQueueMessage.getName() ,outpatientQueueMessage.getCode());
        return "hi";
    }

}
