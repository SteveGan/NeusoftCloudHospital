package com.neuedu.hospitalbackend;

import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.util.AddressUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.net.URISyntaxException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HospitalBackEndApplicationTests {

    @Test
    public void testIpAddress() throws IOException, URISyntaxException {
        JSONObject object = AddressUtils.getIpInfo("192.168.2.6");
        System.out.println(object);
    }

}
