package com.neuedu.hospitalbackend.util;

/**
 * 枚举了一些常用API操作码
 * Created by Raven on 2019/5/30.
 */
public enum ResultCode implements IErrorCode {
    // 外层code
    SUCCESS(200, "操作成功"),
    FAILED(500, "操作失败"),

    // 内层error data
    // Raven
    E_600(600, "用户不存在"),
    E_601(601, "密码错误"),
    E_602(602, "操作项不存在"),
    E_603(603, "用户未登录，请重新登录"),
    E_604(604, "登录信息过期，请重新登录"),
    E_605(605, "插入失败"),

    // Polaris
    E_700(700, "更新医生余号失败"),
    E_701(701, "更新病历表状态失败"),

    // Amy
    E_800(800, "科室类型异常"),
    E_801(801, "部门参数异常"),
    E_802(802, "病历号参数异常"),
    E_803(803, "医技医生参数异常"),
    E_804(804, "项目操作权限异常"),
    E_805(805, "缺少必填参数"),
    E_806(806, "项目参数异常"),
    E_807(807, "申请参数异常");

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
