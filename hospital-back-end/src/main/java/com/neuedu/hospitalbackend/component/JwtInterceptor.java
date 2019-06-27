package com.neuedu.hospitalbackend.component;

import com.neuedu.hospitalbackend.exception.UserLoginException;
import com.neuedu.hospitalbackend.exception.UserLoginInvalidException;
import com.neuedu.hospitalbackend.util.JwtUtil;
import io.jsonwebtoken.Claims;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;

import static com.neuedu.hospitalbackend.util.JwtUtil.resetResponse;
import static com.neuedu.hospitalbackend.util.ResultCode.E_603;
import static com.neuedu.hospitalbackend.util.ResultCode.E_604;

/**
 * JWT 拦截器
 * @author Raven, Steve
 */
public class JwtInterceptor extends HandlerInterceptorAdapter {

//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//
//        String authHeader = request.getHeader("Authorization");
//
//        // 校验失败
//        if (authHeader == null || !authHeader.startsWith("Bearer:")) {
//            resetResponse(response, E_603);
//            return false;
//        }
//
//        //取得token
//        String token = authHeader.substring(7);
//
//        //验证token
//        try {
//            Claims claims = JwtUtil.checkToken(token);
//            request.setAttribute("username", claims.getSubject());
//            String id = claims.getSubject();
//        }catch (UserLoginInvalidException e) {
//            resetResponse(response, E_604);
//        } catch (UserLoginException e) {
//            resetResponse(response, E_603);
//        }
//
//        return true;
//    }

}