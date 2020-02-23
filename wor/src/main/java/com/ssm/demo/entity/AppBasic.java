package com.ssm.demo.entity;

public class AppBasic{
    String basicInformation; //基本信息
    String url; //下载地址
    String author; //作者
    String bname; //APP的名字
    boolean state = false; //状态（上架/下架）
    int examine = 0;//审核情况（0等待审核 1审核通过 -1审核未通过）

    public void setBname(String bname) {
        this.bname = bname;
    }

    public String getBname() {
        return bname;
    }

    public String getBasicInformation() {
        return basicInformation;
    }

    public void setBasicInformation(String basicInformation) {
        this.basicInformation = basicInformation;
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

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public int getExamine() {
        return examine;
    }

    public void setExamine(int examine) {
        this.examine = examine;
    }
}