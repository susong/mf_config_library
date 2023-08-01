package com.mf.config;

/**
 * 用于统计函数调用时间
 */
 final class TimeCosts {

    private long startTime = 0;

    public TimeCosts() {
        startTime = System.currentTimeMillis();
    }

    public void begin() {
        startTime = System.currentTimeMillis();
    }

    public long end() {
        return System.currentTimeMillis() - startTime;
    }
}
