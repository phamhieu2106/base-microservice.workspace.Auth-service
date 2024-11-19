package com.henry.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public enum UserStatus {
    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE"),
    BLOCKED("BLOCKED");

    private final String value;

    private static final Map<UserStatus, String> map;

    static {
        map = new HashMap<>();
        map.put(ACTIVE, "Đã kích hoạt");
        map.put(INACTIVE, "Chưa kích hoạt");
        map.put(BLOCKED, "Bị chặn");
    }
}
