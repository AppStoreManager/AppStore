package com.ssm.demo.entity;

public class App {
    AppBasic basic;
    AppVersion version;

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
}
