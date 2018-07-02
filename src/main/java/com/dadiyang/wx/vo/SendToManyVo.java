package com.dadiyang.wx.vo;

import java.util.List;

public class SendToManyVo {
    private List<String> ids;
    private String content;

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
