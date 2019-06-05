package com.neuedu.hospitalbackend.util;

import com.alibaba.fastjson.JSON;
import com.neuedu.hospitalbackend.exception.UserLoginException;
import com.neuedu.hospitalbackend.exception.UserLoginInvalidException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import static com.neuedu.hospitalbackend.util.ResultCode.E_603;
import static com.neuedu.hospitalbackend.util.ResultCode.E_604;

/**
 * JWT配置
 * 参考资料: https://blog.csdn.net/weixin_41835866/article/details/82119017
 * @author Raven
 */
public class JwtUtil {

    // 私钥
    final static String base64EncodedSecretKey = "9f2unr1#@FF#@@$fqwadjkd1iodn";

    // 过期时间,测试使用十分钟
    final static long TOKEN_EXP = 1000 * 60 * 10;


    public static String getToken(String userName) {
        return Jwts.builder().setSubject(userName).claim("roles", "user").
                setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXP))
                .signWith(SignatureAlgorithm.HS256, base64EncodedSecretKey).compact();
    }

    /**
     * 检查token,只要不正确就会抛出异常
     **/
    public static Claims checkToken(String token, HttpServletResponse response) throws UserLoginInvalidException, UserLoginException, IOException {

        try {
            final Claims claims = Jwts.parser().setSigningKey(base64EncodedSecretKey).parseClaimsJws(token).getBody();
            return claims;
        } catch (ExpiredJwtException e1) {
            resetResponse(response, E_604);
            throw new UserLoginInvalidException("登录信息过期，请重新登录");
        } catch (Exception e) {
            resetResponse(response, E_603);
            throw new UserLoginException("用户未登录，请重新登录");
        }
    }

    public static void resetResponse(HttpServletResponse response, ResultCode code) throws IOException {
        // 重置response
        response.reset();
        // 设置编码格式
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        // 设置response内容
        PrintWriter pw = response.getWriter();
        pw.write(JSON.toJSONString(CommonResult.fail(code)));

        pw.flush();
        pw.close();
    }
}