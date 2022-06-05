package com.web.offood.dto.constant.status;

public enum RestaurantStatus {
    DISABLED(1),
    ACTIVE_BUT_UNVERIFIED(2),
    ACTIVE(3),
    LOCK(4),
    WAITING_CONFIRMATION(5),
    ;
    private final Integer active;

    RestaurantStatus(Integer active) {
        this.active = active;
    }

    public Integer getValue() {
        return active;
    }

    public String getName(Integer val) {
        return (this.name());
    }
}
