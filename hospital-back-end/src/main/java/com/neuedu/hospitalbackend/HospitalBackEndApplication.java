package com.neuedu.hospitalbackend;

import com.neuedu.hospitalbackend.constant.Cache;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@MapperScan("com.neuedu.hospitalbackend.model.dao")
@EnableCaching
public class HospitalBackEndApplication {

    public static void main(String[] args) {
        SpringApplication.run(HospitalBackEndApplication.class, args);

        // 初始化全局缓存变量
        new Cache().initialize();
        System.out.println("[INFO]服务器启动成功！");
    }

}
