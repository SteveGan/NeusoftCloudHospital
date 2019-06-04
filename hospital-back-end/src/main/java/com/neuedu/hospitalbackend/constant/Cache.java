package com.neuedu.hospitalbackend.constant;

import com.neuedu.hospitalbackend.model.dao.RegistrationMapper;
import com.neuedu.hospitalbackend.service.serviceimplementation.tollstationservice.RegistrationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 复杂的全局缓存操作，注意依赖注入的先后顺序
 * 参考资料: https://blog.csdn.net/xinpz/article/details/81061515
 * @author Raven
 */
@Component
public class Cache {
    // 可用病历id
    private static Integer nextRegistrationId;
    // 可用患者id
    private Integer nextPatientId;

    @Autowired
    private RegistrationMapper registrationMapper;
    private static Cache cache;

    @PostConstruct
    public void init() {
        cache = this;
        cache.registrationMapper = this.registrationMapper;
    }

    public void initialize() {
        Integer id = cache.registrationMapper.getNextId();
        nextRegistrationId = id;
        System.out.println("[INFO]初始化可用病历id: "+ nextRegistrationId);
    }

    public static Integer getNextRegistrationId() {
        return nextRegistrationId;
    }

    public static void setNextRegistrationId(Integer nextRegistrationId) {
        Cache.nextRegistrationId = nextRegistrationId;
    }
}
