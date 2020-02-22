package com.ssm.demo.service;

import com.ssm.demo.entity.App;

import java.util.List;

public interface AppStoreService {
    public String newAppBasic(String basicInformation,String author,String name);//新增App基础信息
    public String newAppVersion(String name,String versionInformation,String versionId);//新增App版本信息
    public String changeAppBasic(String basicInformation,String author,String name);//修改基本信息
    public String changeAppVersion(String name,String versionInformation,String versionId);//新增基本信息
    public List<App> getApps(String name);//返回对应名字的App
    public String deleteApps(String name);//删除App信息
    public String downApp(String name,String versionId);//App下架
    public String verify(String name,String versionId,boolean judge);//审核App
}
