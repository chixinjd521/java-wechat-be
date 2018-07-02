package com.dadiyang.wx.dto;

/**
 * 群发单个结果
 *
 * @author huangxuyang
 * @date 2018/4/6
 */
public class SendManySingleResult {
    private Integer code;
    private String desc;
    private String uid;
    private int all;
    private int progress;

    public SendManySingleResult() {
    }

    public SendManySingleResult(Integer code, String uid, int all, int progress) {
        this.code = code;
        this.uid = uid;
        this.all = all;
        this.progress = progress;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getAll() {
        return all;
    }

    public void setAll(int all) {
        this.all = all;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    @Override
    public String toString() {
        return "发送结果：" + (code == null ? "未知" : code == 0 ? "成功" : code == 100 ? "用户不存在" : "") + "，当前进度：" + progress + "/" + all;
    }
}
