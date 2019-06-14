package com.info.utils;

import java.util.UUID;

/**
 * 生成唯一id，key
 */
public class IdUtil {

    public static String createAppKey() {
        return UUID.randomUUID().toString();
    }
}
