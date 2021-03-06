package com.ssm.demo.dao;

import com.ssm.demo.entity.App;
import com.ssm.demo.entity.AppBasic;
import com.ssm.demo.entity.AppVersion;
import org.apache.ibatis.annotations.ResultMap;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface AppsDao{
    void insertNewApp(App app);//插入新的app
    void insertNewVersion(App app);//插入新版本
    void deleteAll(String name);//删除该名字APP的所有版本
    void delete(String name,String version);//删除对应名字对应版本的APP
    void updateBasicInformation(AppBasic appBasic,String name);//更新基本信息
    void updateVersion(App app,String name,String versionId);//每一次更新后修改版本说明，版本号，修改时间这些与版本相关的信息
    List<App> selectByName(String name);//返回全部对应名字的APP集合
    List<App> selectByType(String type);//返回全部对应类型的App集合
    List<App> selectByAuthor(String author);
}