package com.zhiyiyo.crm.utils;

import java.util.UUID;

public class UUIDUtil {

    /**
     * 获取 32 位 UUID
     * @return UUID
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
