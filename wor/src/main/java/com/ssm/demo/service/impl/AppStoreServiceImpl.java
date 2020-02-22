package com.ssm.demo.service.impl;

import com.ssm.demo.dao.*;
import com.ssm.demo.entity.*;
import com.ssm.demo.service.AppStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class AppStoreServiceImpl implements AppStoreService {
    @Autowired
    AppsDao appsDao;
    @Autowired
    DeveloperDao developerDao;
    @Autowired
    AdministratorDao administratorDao;
    private boolean isNum(String str){
        for (int i = str.length();--i>=0;){
            if (!Character.isDigit(str.charAt(i))){
                return false;
                }
            }
        return true;
    }
    private boolean IdVerify(String versionId){
        int firstDot = versionId.indexOf(".");
        int secondDot = versionId.lastIndexOf(".");
        if(firstDot == secondDot)return false;
        else {
            String str1 = versionId.substring(0,firstDot);
            String str2 = versionId.substring(firstDot+1,secondDot);
            String str3 = versionId.substring(secondDot+1,versionId.length());
            if(isNum(str1)&&isNum(str2)&&isNum(str3))return true;
            else return false;
        }
    }
    /*
    *这四个函数存在一个共同的问题，就是会直接把app的信息送到数据库，但是按道理应该先审核通过才送过去，但是审核不一定在
    * 本次程序运行，数据并非永久化，所以目前也只是更改状态*/
    public String newAppBasic(String basicInformation, String author,String name){
        App app = new App();
        AppBasic appBasic = new AppBasic();
        AppVersion appVersion = new AppVersion();

        appBasic.setBasicInformation(basicInformation);
        appBasic.setAuthor(author);
        appBasic.setName(name);
        appBasic.setUrl("D:\\JavaHomework\\AppStore");

        appVersion.setName(name);
        appVersion.setVersionId("1.0.0");
        appVersion.setVersionInformation("应用"+name+"首次发布，欢迎使用");

        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");//可以方便地修改日期格式
        String time = dateFormat.format(now);
        appVersion.setTime(time);

        appBasic.setExamine(0);//初始置为待审核状态

        app.setBasic(appBasic);
        app.setVersion(appVersion);
        appsDao.insertNewApp(app);


        return "app上传成功，请耐心等待审核，审核完成后将第一时间通知您";
    }
    public String newAppVersion(String name,String versionInformation,String versionId){
        if (!IdVerify(versionId)) return "版本号格式不正确，请按“数字.数字.数字”形式输入";
        App app = (App)appsDao.selectByName(name).get(0);
        AppVersion appVersion = new AppVersion();

        appVersion.setName(name);
        appVersion.setVersionId(versionId);
        appVersion.setVersionInformation(versionInformation);

        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");//可以方便地修改日期格式
        String time = dateFormat.format(now);
        appVersion.setTime(time);

        app.setVersion(appVersion);

        AppBasic appBasic = app.getBasic();
        appBasic.setExamine(0);//设置为待审核
        app.setBasic(appBasic);

        appsDao.insertNewVersion(app);
        return "新版本上传成功，请耐心等待审核，审核结果将尽快告知您。";
    }
    public String changeAppBasic(String basicInformation,String author,String name) {
        AppBasic appBasic = new AppBasic();
        appBasic.setBasicInformation(basicInformation);
        appBasic.setAuthor(author);
        appBasic.setExamine(0);//设置为待审核

        appsDao.updateBasicInformation(appBasic, name);


        return "App基础信息更新成功，，请等待管理员审核，审核结果将第一时间通知您。";
    }
    public String changeAppVersion(String name,String versionInformation,String versionId) {
        if (!IdVerify(versionId)) return "版本号格式不正确，请按“数字.数字.数字”形式输入";
        App app = (App)appsDao.selectByName(name).get(0);

        AppVersion appVersion = new AppVersion();
        appVersion.setVersionInformation(versionInformation);
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");//可以方便地修改日期格式
        String time = dateFormat.format(now);
        appVersion.setTime(time);

        app.setVersion(appVersion);

        AppBasic appBasic = app.getBasic();
        appBasic.setExamine(0);//设置为待审核
        app.setBasic(appBasic);

        appsDao.updateVersion(app,name,versionId);

        return "App基础信息更新成功，，请等待管理员审核，审核结果将第一时间通知您。";
    }

    public List<App> getApps(String name) {
        List<App> apps = appsDao.selectByName(name);

        if (apps == null) return null;

        //这里会不显示待审核的App，最终要不要显示有待商议
        Iterator<App> iter = apps.iterator();
        while (iter.hasNext()) {
            App app = (App) iter.next();
            if (app.getBasic().getExamine() == 0) {
                iter.remove();
            }
        }
        return apps;
    }
    public List<App> getUnVerApps(String type){
        List<App> apps = appsDao.selectByName(type);

        if (apps == null) return null;

        //这里会不显示已经审核的App
        Iterator<App> iter = apps.iterator();
        while (iter.hasNext()) {
            App app = (App) iter.next();
            if (app.getBasic().getExamine() != 0) {
                iter.remove();
            }
        }
        return apps;
    }
    public String deleteApps(String name){
        appsDao.deleteAll(name);
        return "已删除所有版本的"+name+"APP";
    }

    public String upApp(String name,String versionId){
        List<App> apps = appsDao.selectByName(name);
        if (apps == null) return null;

        //更改状态为已上架
        Iterator<App> iter = apps.iterator();
        while (iter.hasNext()) {
            App app = (App) iter.next();
            if (app.getVersion().getVersionId().equals(versionId)) {
                if(app.getBasic().getExamine()!=1)return "App审核中或未通过审核，无法上架";
                else {
                    AppBasic appBasic = app.getBasic();
                    appBasic.setState(true);
                    app.setBasic(appBasic);
                }
            }
        }
        return "App上架成功";
    }
    public String downApp(String name,String versionId){
        List<App> apps = appsDao.selectByName(name);
        if (apps == null) return null;

        //更改状态为已下架
        Iterator<App> iter = apps.iterator();
        while (iter.hasNext()) {
            App app = (App) iter.next();
            if (app.getVersion().getVersionId().equals(versionId)) {
                    AppBasic appBasic = app.getBasic();
                    appBasic.setState(false);
                    app.setBasic(appBasic);
            }
        }
        return "App下架成功";
    }

    //审核这里其实应该是三个状态，但是最开始欠考虑用了Boolean，只有两个状态了，暂时先用两个状态写
    //参数judge的值取决于管理员的选择，选择通过judge为true，否则为false
    public String verify(String name,String versionId,boolean judge) {
        App app = (App) appsDao.selectByName(name).get(0);

        AppBasic appBasic = app.getBasic();

        if (judge) appBasic.setExamine(1);//设置为审核通过
        else appBasic.setExamine(-1);//设置为审核未通过
        app.setBasic(appBasic);
        appsDao.updateVersion(app, name, versionId);
        return "审核完成";
    }
    public String judge(String userId,String password){
        Developer developer =(Developer)developerDao.selectDeveloper(userId);
        if(developer != null){
            if(developer.getDevPassword().equals(password))return "Developer";
            else return "密码错误";
        }
        else{
            Administrator administrator =(Administrator)administratorDao.getAdministrator(userId);
            if(administrator==null)return "用户名不存在";
            else if(administrator.getPassword().equals(password))return "Administrator";
            else return "密码错误";
        }

    }
}
