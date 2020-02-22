package com.ssm.demo.entity;
public class App {
    AppBasic basic;
    AppVersion version;
    String appName;
    String types;//{商城,游戏,影音,社交,资讯,学习,工具};App的类型，用于和管理员建立对应关系


    public String getAppName(){
        return appName;
    }

    public void setAppName(String appName){
        this.appName=appName;
    }

    public AppBasic getBasic() {
        return basic;
    }

    public AppVersion getVersion() {
        return version;
    }

    public void setVersion(AppVersion version) {
        this.version = version;
    }

    public void setBasic(AppBasic basic) {
        this.basic = basic;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }
}