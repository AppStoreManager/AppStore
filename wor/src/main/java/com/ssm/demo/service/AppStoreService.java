package com.ssm.demo.service;

import com.ssm.demo.entity.App;

import java.util.List;

public interface AppStoreService {
    public String newAppBasic(String basicInformation,String author,String name);//新增App基础信息
    public String newAppVersion(String name,String versionInformation,String versionId);//新增App版本信息
    public String changeAppBasic(String basicInformation,String author,String name);//修改基本信息
    public String changeAppVersion(String name,String versionInformation,String versionId);//新增基本信息
    public List<App> getApps(String name);//返回对应名字的App
    public List<App> getUnVerApps(String type);//返回对应类型App
    public String deleteApps(String name);//删除所有对应名字的App信息
    public String upApp(String name,String versionId);//操作某名字特定版本的App上架
    public String downApp(String name,String versionId);//操作某名字特定版本的App下架
    public String verify(String name,String versionId,boolean judge);//审核对应名字对应版本的App
    public String judge(String userId,String password);/*判断用户名是否存在，与密码是否对应；返回结果4种，
                                                             用户名不存在返回“用户名不存在，”
                                                             密码不匹配返回“密码错误 ”
                                                             匹配且未管理员返回“Administrator”
                                                             匹配且为开发者返回“Developer”*/
}
