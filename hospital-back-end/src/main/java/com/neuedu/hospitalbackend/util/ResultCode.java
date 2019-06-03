package com.neuedu.hospitalbackend.util;

/**
 * 枚举了一些常用API操作码
 * Created by Raven on 2019/5/30.
 */
public enum ResultCode implements IErrorCode {
    SUCCESS(200, "操作成功"),
    FAILED(500, "操作失败"),
    // Raven
    E_600(600, "用户不存在"),
    E_601(601, "密码错误"),
    E_602(602, "查询失败"),
    E_603(603, "插入失败"),
    E_604(604, "更新项不存在");

    // Polaris

    // Amy


    private long code;
    private String message;

    ResultCode(long code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public long getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
