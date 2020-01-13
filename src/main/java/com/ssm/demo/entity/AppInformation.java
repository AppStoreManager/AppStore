package com.ssm.demo.entity;

public class AppInformation {
    String name; //APP的名字
    String basicInformation; //基本信息
    String versionInformation; //版本说明
    String version;//版本号
    String url; //下载地址
    String author; //作者
    String time; //修改时间
    boolean state; //状态（已上架/待审核）

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getBasicInformation() {
        return basicInformation;
    }

    public void setBasicInformation(String basicInformation) {
        this.basicInformation = basicInformation;
    }

    public String getVersionInformation() {
        return versionInformation;
    }

    public void setVersionInformation(String versionInformation) {
        this.versionInformation = versionInformation;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
}
