package com.movie.entity.code;

public enum OrderStatus {
    /**
     * 订单状态 - 未支付
     */
    UNPAID(0),
    /**
     * 订单状态 - 已支付但未使用
     */
    PAID_BUT_NOT_USED(1),
    /**
     * 订单状态 - 已使用
     */
    USED_ALREADY(2),
    /**
     * 订单状态 - 支付超时
     */
    PAYMENT_TIMEOUT(3),
    /**
     * 订单状态 - 支付取消
     */
    PAYMENT_CANCELLED(4);

    private final int code;

    OrderStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
