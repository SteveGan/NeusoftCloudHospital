package com.neuedu.hospitalbackend.util;

/**
 * 封装API的错误码
 * Created by Raven on 2019/5/30.
 */
public interface IErrorCode {
    long getCode();

    String getMessage();
}