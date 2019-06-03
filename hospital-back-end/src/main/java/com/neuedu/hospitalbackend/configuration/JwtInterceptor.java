package com.neuedu.hospitalbackend.configuration;

import com.neuedu.hospitalbackend.exception.UserLoginException;
import com.neuedu.hospitalbackend.util.JwtUtil;
import io.jsonwebtoken.Claims;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;


public class JwtInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer:")) {

            throw new UserLoginException("用户未登录");

        }

//取得token

        String token = authHeader.substring(7);

//验证token

        Claims claims = JwtUtil.checkToken(token);

        request.setAttribute("username", claims.getSubject());

        return true;

    }

}