package com.neuedu.hospitalbackend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.neuedu.hospitalbackend.model.com")
public class HospitalBackEndApplication {

    public static void main(String[] args) {
        SpringApplication.run(HospitalBackEndApplication.class, args);
    }

}
