package com.dadiyang.wx.util;

public enum  DataSourceType {
    WX_DB("wx");

    private String dbname;
    DataSourceType(String dbname) {
        this.dbname = dbname;
    }

    public String getDbname() {
        return dbname;
    }

    public void setDbname(String dbname) {
        this.dbname = dbname;
    }

    public static DataSourceType forName(String dbname){
        for (DataSourceType type : DataSourceType.values()) {
            if(type.dbname.equalsIgnoreCase(dbname)){
                return type;
            }
        }
        return null;
    }
}
