package com.ssm.demo.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ZhutianLin
 * @version 1.0.0
 *
 * */
public class Developer {
    //私有属性
    private String devUsername;//用户名，唯一的id
    private String devNickname;//昵称
    private String devPassword;//密码
    private String devInfo;//用户信息
    private List appList=new ArrayList();//用户App列表，初始为空

    //getter&setter
    public String getDevUsername() {
        return devUsername;
    }

    public void setDevUsername(String devUsername) {
        this.devUsername = devUsername;
    }

    public String getDevNickname() {
        return devNickname;
    }

    public void setDevNickname(String devNickname) {
        this.devNickname = devNickname;
    }

    public List getAppList() {
        return appList;
    }

    public void setAppList(List appList) {
        this.appList = appList;
    }

    public String getDevPassword() {
        return devPassword;
    }

    public void setDevPassword(String devPassword) {
        this.devPassword = devPassword;
    }

    public String getDevInfo() {
        return devInfo;
    }

    public void setDevInfo(String devInfo) {
        this.devInfo = devInfo;
    }
}
