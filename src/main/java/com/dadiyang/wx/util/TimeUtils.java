package com.dadiyang.wx.util;

/**
 * @author dadiyang
 */
public class TimeUtils {
    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {
        }
    }

    /**
     * 忽略异常地随机休眠一段时间
     *
     * @param gtMillis 大于n毫秒
     * @param ltMillis 小于n毫秒
     * @return 实际休眠的时间毫秒数
     */
    public static long randomSleep(long gtMillis, long ltMillis) {
        if (gtMillis > ltMillis) {
            throw new IllegalArgumentException("gtMillis must lower than ltMillis");
        }
        try {
            // 拟人操作
            long millis = (long) (Math.random() * (ltMillis - gtMillis)) + gtMillis;
            Thread.sleep(millis);
            return millis;
        } catch (InterruptedException ignored) {
        }
        return -1;
    }
}
