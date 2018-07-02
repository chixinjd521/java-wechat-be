package com.dadiyang.wx.dto;
/**
 * 服务调用结果实体
 * @author dadiyang
 * @date 2017/11/18
 */
public class ResultBean<T> {
    public static final int SUCCESS = 0;
    public static final int NEED_LOGIN = -2;
    public static final int FAIL = -1;
    private T data;
    private int code = SUCCESS;
    private String msg = "success";

    public static <T> ResultBean<T> createFailResult(String msg) {
        return new ResultBean<T>(msg, FAIL);
    }

    public static <T> ResultBean<T> createSuccessResult(T dat) {
        return new ResultBean<T>(SUCCESS, dat);
    }

    public ResultBean(int code, T data) {
        this.code = code;
        this.data = data;
    }

    public ResultBean(String msg, int code) {
        this.code = code;
        this.msg = msg;
    }

    public ResultBean() {
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
