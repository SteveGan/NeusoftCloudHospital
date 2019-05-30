package com.neuedu.hospitalbackend;

import com.neuedu.hospitalbackend.controller.DepartmentManagementController;
import com.neuedu.hospitalbackend.controller.UserController;
import com.neuedu.hospitalbackend.model.dto.LoginParam;
import org.junit.Test;

public class UserMapperTest {
    @Test
    public void testLogin() {
        LoginParam loginParam = new LoginParam();
        loginParam.setUserId(10000001);
        loginParam.setPassword("password");
        System.out.println(new UserController().login(loginParam));
    }

/*    @Test
    public void testDemo() {
        System.out.println(new DepartmentManagementController().selectDepartmentById("1"));
    }*/
}
