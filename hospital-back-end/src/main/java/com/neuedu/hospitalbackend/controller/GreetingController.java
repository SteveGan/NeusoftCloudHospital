package com.neuedu.hospitalbackend.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.testwebsocket.Greeting;
import com.neuedu.hospitalbackend.testwebsocket.HelloMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin
public class GreetingController {

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public JSONObject greeting() throws Exception{
        Thread.sleep(1000); // simulated delay
        System.out.println("get request");
        JSONObject obj= new JSONObject();
        obj.put("name", "Steve");
        return obj;
    }
}
