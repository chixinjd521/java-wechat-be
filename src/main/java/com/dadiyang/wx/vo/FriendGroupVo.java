package com.dadiyang.wx.vo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 好友分组信息
 *
 * @author huangxuyang
 * @date 2018/4/7
 */
public class FriendGroupVo {
    private String id;
    private String name;
    private List<String> members;

    public FriendGroupVo() {
    }

    public FriendGroupVo(String name) {
        this.name = name;
        this.id = name;
        this.members = new ArrayList<>();
    }

    public FriendGroupVo(String name, List<String> members) {
        this.id = name;
        this.name = name;
        this.members = members;
    }

    public FriendGroupVo(String id, String name, List<String> members) {
        this.id = id;
        this.name = name;
        this.members = members;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }
}
