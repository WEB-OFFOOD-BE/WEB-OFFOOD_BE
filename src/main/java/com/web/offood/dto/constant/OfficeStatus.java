package com.web.offood.dto.constant;

public enum OfficeStatus {
    DISABLED(1),
    ACTIVE_BUT_UNVERIFIED(1),
    ACTIVE(3),
    LOCK(4),
    ;
    private final Integer active;

    OfficeStatus(Integer active) {
        this.active = active;
    }

    public Integer getValue() {
        return active;
    }

    public String getName(Integer val) {
        return (this.name());
    }
}
