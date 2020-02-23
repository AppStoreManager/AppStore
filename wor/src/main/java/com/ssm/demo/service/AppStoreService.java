package com.ssm.demo.service;

import com.ssm.demo.entity.Administrator;
import com.ssm.demo.entity.App;
import com.ssm.demo.entity.Developer;

import java.util.List;

public interface AppStoreService {
    public int newAppBasic(String basicInformation,String author,String name);//新增App基础信息
    public int newAppVersion(String name,String versionInformation,String versionId);//新增App版本信息
    public int changeAppBasic(String basicInformation,String author,String name);//修改基本信息
    public int changeAppVersion(String name,String versionInformation,String versionId);//新增基本信息
    public List<App> getApps(String name);//返回对应名字的App
    public App getApp(String name,String versionId);
    public List<App> getUnVerApps(String type);//返回对应类型App
    public int deleteApps(String name);//删除所有对应名字的App信息
    public int upApp(String name,String versionId);//操作某名字特定版本的App上架
    public int downApp(String name,String versionId);//操作某名字特定版本的App下架
    public int verify(String name,String versionId,boolean judge);//审核对应名字对应版本的App
    public int judge(String userId,String password);/*判断用户名是否存在，与密码是否对应；返回结果4种，
                                                             用户名不存在返回“用户名不存在，”
                                                             密码不匹配返回“密码错误 ”
                                                             匹配且未管理员返回“Administrator”
                                                             匹配且为开发者返回“Developer”*/
    public Administrator getAdminByName(String name);
    public Developer getDevByName(String name);
    public List<App> getAllApps(String devName);
    public int newAdmin(String userName,String nickName,String password,String range);
    public int newDev(String userName,String Nickname,String password,String info);
}
