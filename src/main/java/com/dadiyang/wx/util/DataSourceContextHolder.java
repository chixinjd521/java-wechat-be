package com.dadiyang.wx.util;

public class DataSourceContextHolder {

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

    public static void setDbType(DataSourceType dbType) {
        contextHolder.set(dbType.getDbname());
    }

    public static DataSourceType getDbType() {
        return DataSourceType.forName(contextHolder.get());
    }

    public static void clearDbType() {
        contextHolder.remove();
    }
}