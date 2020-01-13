package com.ssm.demo.dao;

import com.ssm.demo.entity.AppInformation;

import java.util.List;

public interface AppsDao{
 void insert(AppInformation appIn);//插入新的app或者新版本
 void delete(String name);//由名字删除所有版本APP
 void delete(String name,String version);//删除对应名字对应版本的APP
 void updateBasicInformation(String basicInformation);//更新基本信息
 void updateVersion(String versionInformation,String Version,String time);//每一次更新后修改版本说明，版本号，修改时间这些与版本相关的信息
 List<AppInformation> selectByName(String name);//返回全部对应名字的APP集合
}