package com.neuedu.hospitalbackend.service.serviceimplementation.commonservice;

import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.model.dao.LogLoginMapper;
import com.neuedu.hospitalbackend.model.po.LogLogin;
import com.neuedu.hospitalbackend.model.vo.LoginParam;
import com.neuedu.hospitalbackend.model.dao.RoleMapper;
import com.neuedu.hospitalbackend.model.dao.UserMapper;
import com.neuedu.hospitalbackend.model.po.Role;
import com.neuedu.hospitalbackend.model.po.User;
import com.neuedu.hospitalbackend.service.serviceinterface.commonservice.OauthService;
import com.neuedu.hospitalbackend.util.AddressUtils;
import com.neuedu.hospitalbackend.util.CommonResult;
import com.neuedu.hospitalbackend.util.JwtUtil;
import com.neuedu.hospitalbackend.util.SHAUtils;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import eu.bitwalker.useragentutils.Version;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.neuedu.hospitalbackend.constant.Cache.hospitalLogger;
import static com.neuedu.hospitalbackend.util.ResultCode.E_601;


/**
 * 身份验证服务
 * @author Raven
 */
@Service
public class OauthServiceImpl implements OauthService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private LogLoginMapper logLoginMapper;

    @Override
    public CommonResult login(LoginParam loginParam, HttpServletRequest request) {
        // sha加密后密码
        String pwd = SHAUtils.encodeData(loginParam.getPassword());
        loginParam.setPassword(pwd);
        User user = userMapper.userAuthentication(loginParam.getUserId(), loginParam.getPassword());

        JSONObject result = new JSONObject();
        if(user != null){
            result.put("userId", user.getId());
            result.put("userName", user.getName());
            result.put("avatar", user.getAvatar());
            List<Role> roles = roleMapper.listRole(loginParam.getUserId());
            result.put("roles", roles);
            String token = JwtUtil.getToken(user.getName());
            result.put("web_token", "Bearer:" + token);
            System.out.println();
            hospitalLogger.info("登录成功: " + loginParam.getUserId() + " with token: " + token);
            insertLogLogin(loginParam, request);
            return CommonResult.success(result);
        } else{
            hospitalLogger.info("登录失败: " + loginParam.getUserId());
            return CommonResult.fail(E_601);
        }
    }

    /**
     * 登陆日志表记录登录c操作
     * @param loginParam 登录dto
     * @param request http请求
     */
    private void insertLogLogin(LoginParam loginParam, HttpServletRequest request) {
        LogLogin logLogin = new LogLogin();
        logLogin.setUserId(loginParam.getUserId());
        logLogin.setTime(new java.sql.Date(new java.util.Date().getTime()));
        String ip = getIPAddress(request);
        logLogin.setIp(ip);

        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        //获取操作系统信息
        OperatingSystem os = userAgent.getOperatingSystem();
        logLogin.setOs(os.getName());
        //获取浏览器信息
        Browser browser = userAgent.getBrowser();
        Version version = browser.getVersion(request.getHeader("User-Agent"));
        String info = "";
        try {
            info = browser.getName() + "/" + version.getVersion();
        } catch (NullPointerException e) {
            hospitalLogger.warn("未提取到" + ip + "的浏览器信息");
        }
        logLogin.setBrowser(info);

        logLogin.setType("[Web]登录");
        logLogin.setUrl(request.getHeader("Referer"));

        //获取地理位置
        String location = "";
        String localhost= "0:0:0:0:0:0:0:1";
        if (localhost.equals(ip)){
            location = "本机";
        } else {
            try {
                JSONObject object = AddressUtils.getIpInfo(ip);
                JSONObject data = object.getJSONObject("data");
                location = data.getString("isp") + ": " + data.getString("country") + " " +
                        data.getString("region") + " " + data.getString("city");
            } catch (Exception e) {
                hospitalLogger.warn("未提取到" + ip + "的地理位置信息");
                System.out.println(e);
            }
        }

        logLogin.setLocation(location);
        logLoginMapper.insert(logLogin);
    }

    /**
     * 获取登录用户ip
     * 注意0:0:0:0:0:0:0:1为本地
     * @param request HttpServletRequest
     * @return ip地址
     */
    private static String getIPAddress(HttpServletRequest request) {
        String ip = null;

        //X-Forwarded-For：Squid 服务代理
        String ipAddresses = request.getHeader("X-Forwarded-For");

        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            //Proxy-Client-IP：apache 服务代理
            ipAddresses = request.getHeader("Proxy-Client-IP");
        }

        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            //WL-Proxy-Client-IP：weblogic 服务代理
            ipAddresses = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            //HTTP_CLIENT_IP：有些代理服务器
            ipAddresses = request.getHeader("HTTP_CLIENT_IP");
        }

        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            //X-Real-IP：nginx服务代理
            ipAddresses = request.getHeader("X-Real-IP");
        }

        //有些网络通过多层代理，那么获取到的ip就会有多个，一般都是通过逗号（,）分割开来，并且第一个ip为客户端的真实IP
        if (ipAddresses != null && ipAddresses.length() != 0) {
            ip = ipAddresses.split(",")[0];
        }

        //还是不能获取到，最后再通过request.getRemoteAddr();获取
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
