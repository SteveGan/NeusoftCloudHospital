package com.neuedu.hospitalbackend.constant;

import com.neuedu.hospitalbackend.model.dao.ConstantMapper;
import com.neuedu.hospitalbackend.model.dao.InvoiceMapper;
import com.neuedu.hospitalbackend.model.dao.RegistrationMapper;
import com.neuedu.hospitalbackend.model.po.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private static Integer nextPatientId;
    // 常量表
    private static Map<String, Map<Byte, String>> constant = new HashMap<>();
    //可用发票号
    private static String nextInvoiceCode;

    @Autowired
    private RegistrationMapper registrationMapper;

    @Autowired
    private ConstantMapper constantMapper;

    @Autowired
    private InvoiceMapper invoiceMapper;

    private static Cache cache;

    @PostConstruct
    public void init() {
        cache = this;
        cache.registrationMapper = this.registrationMapper;
        cache.constantMapper = this.constantMapper;
        cache.invoiceMapper = this.invoiceMapper;
    }

    public void initialize() {
        // 初始化可用病历id
        Integer id = cache.registrationMapper.getNextId();
        if (id == null)
            id = 1000001;
        nextRegistrationId = id;
        System.out.println("[INFO]初始化可用病历id: "+ nextRegistrationId);

        // 初始化常量表
        List<Constant> list = cache.constantMapper.list();
        System.out.println("[INFO]常量表记录数: " + list.size());
        for (Constant item : list) {
            if (constant.containsKey(item.getType())) {
                constant.get(item.getType()).put(item.getChildId(), item.getName());
            } else {
                HashMap<Byte, String> hashMap = new HashMap<>();
                hashMap.put(item.getChildId(), item.getName());
                constant.put(item.getType(), hashMap);
            }
        }

        // 初始化可用发票号
        String invoiceCode = cache.invoiceMapper.getAvailableInvoiceCode();
        cache.invoiceMapper.updateInvoiceStatusById(invoiceCode);
        nextInvoiceCode = invoiceCode;
        System.out.println("[INFO]初始化可用发票号: "+ nextInvoiceCode);
    }

    public static Integer getNextRegistrationId() {
        return nextRegistrationId;
    }

    public static void setNextRegistrationId(Integer nextRegistrationId) {
        Cache.nextRegistrationId = nextRegistrationId;
    }

    public static Map<String, Map<Byte, String>> getConstant() {
        return constant;
    }

    public static void setConstant(Map<String, Map<Byte, String>> constant) {
        Cache.constant = constant;
    }

    public static String getNextInvoiceCode() {
        return nextInvoiceCode;
    }

    public static void setNextInvoiceCode(String nextInvoiceCode) {
        Cache.nextInvoiceCode = nextInvoiceCode;
    }
}
