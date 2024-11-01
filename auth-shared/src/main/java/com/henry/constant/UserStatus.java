package com.henry.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public enum UserStatus {
    ACTIVE(0),
    IN_ACTIVE(1),
    BLOCKED(2);

    private final int value;

    private static final Map<UserStatus, String> map;

    static {
        map = new HashMap<>();
        map.put(ACTIVE, "Đang hoạt động");
        map.put(IN_ACTIVE, "Ngừng hoạt động");
        map.put(BLOCKED, "Chặn");
    }
}
