package com.ssm.demo.entity;

public class AppVersion{
    String name;
    String versionInformation; //版本说明
    String versionId;//版本号
    String updateTime; //修改时间
    public String getVersionInformation() {
        return versionInformation;
    }

    public void setVersionInformation(String versionInformation) {
        this.versionInformation = versionInformation;
    }

    public String getVersionId() {
        return versionId;
    }

    public void setVersionId(String versionId) {
        this.versionId = versionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}