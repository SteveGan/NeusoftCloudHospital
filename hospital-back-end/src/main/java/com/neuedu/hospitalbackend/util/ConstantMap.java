package com.neuedu.hospitalbackend.util;

import com.neuedu.hospitalbackend.constant.Cache;

/**
 * 将数字状态码等转换成映射的constant内容
 * @author Raven
 */
public class ConstantMap {
    public static String convert(String type, Byte childId) {
        return Cache.getConstant().get(type).get(childId);
    }
}
