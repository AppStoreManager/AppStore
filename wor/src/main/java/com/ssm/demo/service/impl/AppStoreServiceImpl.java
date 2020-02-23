package com.ssm.demo.service.impl;

import com.ssm.demo.dao.*;
import com.ssm.demo.entity.*;
import com.ssm.demo.result.ErrorCode;
import com.ssm.demo.service.AppStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    * 本次程序运行，数据并非永久化，所以目前也只是更改状态
    *
    *已解决：按照新增而非更新插入，审核后按照不同的审核结果删除不同内容*/
    public int newAppBasic(String basicInformation, String author,String name,String type){
//        if(appsDao.selectByName(name)!=null){
//            System.out.println(appsDao.selectByName(name));
//            return ErrorCode.EXSITED;
//        }

        App app = new App();
        AppBasic appBasic = new AppBasic();
        AppVersion appVersion = new AppVersion();

        app.setAppName(name);
        app.setTypes(type);

        appBasic.setBasicInformation(basicInformation);
        appBasic.setAuthor(author);
        appBasic.setBname(name);
        appBasic.setUrl("D:\\JavaHomework\\AppStore");

        appVersion.setVname(name);
        appVersion.setVersionId("1.0.0");
        appVersion.setVersionInformation("应用"+name+"首次发布，欢迎使用");

        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");//可以方便地修改日期格式
        String time = dateFormat.format(now);
        appVersion.setUpdateTime(time);

        appBasic.setExamine(0);//初始置为待审核状态

        app.setBasic(appBasic);
        app.setVersion(appVersion);
        appsDao.insertNewApp(app);

        Developer developer = developerDao.selectDeveloper(author);
        List<App> appList = developer.getAppList();
        appList.add(app);
        developer.setAppList(appList);
        developerDao.updateDeveloper(developer);

        System.out.println("APP新增成功");
        return ErrorCode.CREATED;
    }
    public int newAppVersion(String name,String versionInformation,String versionId){
        App app = (App)appsDao.selectByName(name).get(0);
        AppVersion appVersion = new AppVersion();

        appVersion.setVname(name);
        appVersion.setVersionId(versionId);
        appVersion.setVersionInformation(versionInformation);

        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");//可以方便地修改日期格式
        String time = dateFormat.format(now);
        appVersion.setUpdateTime(time);

        app.setVersion(appVersion);

        AppBasic appBasic = app.getBasic();
        appBasic.setExamine(0);//设置为待审核
        app.setBasic(appBasic);


        Developer developer = developerDao.selectDeveloper(app.getBasic().getAuthor());
        List<App> appList = developer.getAppList();
        appList.add(app);
        developer.setAppList(appList);
        developerDao.updateDeveloper(developer);
        appsDao.insertNewVersion(app);
        System.out.println("版本新增成功");
        return ErrorCode.CREATED;

    }
    public int changeAppBasic(String basicInformation,String author,String name) {
        List<App> apps= appsDao.selectByName(name);

        Iterator<App> iter = apps.iterator();
        while (iter.hasNext()) {
            App app = (App) iter.next();
            AppBasic appBasic = app.getBasic();
            appBasic.setBasicInformation(basicInformation);
            appBasic.setAuthor(author);
            appBasic.setExamine(0);//设置为待审核
            AppVersion appVersion = app.getVersion();

            Date now = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");//可以方便地修改日期格式
            String time = dateFormat.format(now);
            appVersion.setUpdateTime(time);

            app.setBasic(appBasic);
            app.setVersion(appVersion);

            Developer developer = developerDao.selectDeveloper(app.getBasic().getAuthor());
            List<App> appList = developer.getAppList();
            appList.add(app);
            developer.setAppList(appList);
            developerDao.updateDeveloper(developer);

            appsDao.insertNewApp(app);
        }

        System.out.println("基础信息更新成功");
        return ErrorCode.UPDATED;
    }
    public int changeAppVersion(String name,String versionInformation,String versionId) {
        List<App> apps= appsDao.selectByName(name);

        Iterator<App> iter = apps.iterator();
        while (iter.hasNext()) {
            if (iter.next().getVersion().getVersionId().equals(versionId)) {
                App app = (App) iter.next();
                AppVersion appVersion = app.getVersion();
                appVersion.setVersionInformation(versionInformation);
                Date now = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");//可以方便地修改日期格式
                String time = dateFormat.format(now);
                appVersion.setUpdateTime(time);
                AppBasic appBasic = app.getBasic();
                appBasic.setExamine(0);//设置为待审核
                app.setBasic(appBasic);
                app.setVersion(appVersion);

                Developer developer = developerDao.selectDeveloper(app.getBasic().getAuthor());
                List<App> appList = developer.getAppList();
                appList.add(app);
                developer.setAppList(appList);
                developerDao.updateDeveloper(developer);
                appsDao.insertNewApp(app);
            }
        }
        System.out.println("App版本信息更新成功");
        return ErrorCode.UPDATED;
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
        System.out.println(apps.toString());
        return apps;
    }
    public App getApp(String name,String versionId){
        List<App> apps = appsDao.selectByName(name);
        if (apps == null) return null;
        Iterator<App> iter = apps.iterator();
        while (iter.hasNext()) {
            App app = (App) iter.next();
            if (app.getVersion().getVersionId().equals(versionId)) {
                return iter.next();
            }
        }
        return null;
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
        System.out.println(apps.toString());
        return apps;
    }
    public int deleteApps(String name){
        appsDao.deleteAll(name);
        System.out.println("已删除所有版本的"+name+"APP");
        return  ErrorCode.DELETED;
    }

    public int upApp(String name,String versionId){
        List<App> apps = appsDao.selectByName(name);
        if (apps == null) return ErrorCode.NOT_FOUND;

        //更改状态为已上架
        Iterator<App> iter = apps.iterator();
        while (iter.hasNext()) {
            App app = (App) iter.next();
            if (app.getVersion().getVersionId().equals(versionId)) {
                if(app.getBasic().getExamine()!=1)return ErrorCode.FORBIDDEN;
                else {
                    AppBasic appBasic = app.getBasic();
                    appBasic.setState(true);
                    app.setBasic(appBasic);
                    appsDao.updateVersion(app,name,versionId);
                }
            }
        }
        return ErrorCode.OK;
    }
    public int downApp(String name,String versionId){
        List<App> apps = appsDao.selectByName(name);
        if (apps == null) return ErrorCode.NOT_FOUND;

        //更改状态为已下架
        Iterator<App> iter = apps.iterator();
        while (iter.hasNext()) {
            App app = (App) iter.next();
            if (app.getVersion().getVersionId().equals(versionId)) {
                    AppBasic appBasic = app.getBasic();
                    appBasic.setState(false);
                    app.setBasic(appBasic);
                    appsDao.updateVersion(app,name,versionId);
            }
        }
        return ErrorCode.OK;
    }

    //参数judge的值取决于管理员的选择，选择通过judge为true，否则为false
    public int verify(String name,String versionId,boolean judge) {
        List<App> apps= appsDao.selectByName(name);
        Iterator<App> iter = apps.iterator();
        while (iter.hasNext()) {
            if(iter.next().getVersion().getVersionId().equals(versionId)&&iter.next().getBasic().getExamine()==0){
                AppBasic appBasic = iter.next().getBasic();
                if (judge) {
                    appBasic.setExamine(1);//设置为审核通过
                    appsDao.delete(name,versionId);
                    appsDao.insertNewApp(iter.next());
                    List<App> appList = appsDao.selectByAuthor(iter.next().getBasic().getAuthor());
                    Developer developer = developerDao.selectDeveloper(iter.next().getBasic().getAuthor());
                    developer.setAppList(appList);
                    developerDao.updateDeveloper(developer);
                }
                else appBasic.setExamine(-1);//设置为审核未通过
            }
        }
        return ErrorCode.OK;
    }
    public int judgeA(String userId,String password){
           Administrator administrator =(Administrator)administratorDao.getAdministrator(userId);
           if(administrator==null)return ErrorCode.NO_USER;
           else if(administrator.getPassword().equals(password))return ErrorCode.ADMINISTRATOR;
           else return ErrorCode.PASSWORD_ERROR;

    }
    public int judgeD(String userId,String password){
        Developer developer =(Developer)developerDao.selectDeveloper(userId);
        if(developer == null)return ErrorCode.NO_USER;
        else if(developer.getDevPassword().equals(password))return ErrorCode.DEVELOPER;
        else return ErrorCode.PASSWORD_ERROR;
    }
    public Administrator getAdminByName(String name){

        if(administratorDao.getAdministrator(name)==null)return null;
        System.out.println(administratorDao.getAdministrator(name).getPassword());
        return administratorDao.getAdministrator(name);
    }
    public Developer getDevByName(String name){
        if(developerDao.selectDeveloper(name)==null)return null;
        else return developerDao.selectDeveloper(name);
    }
    public List<App> getAllApps(String devName){
        return developerDao.selectDeveloper(devName).getAppList();
    }
    public int newAdmin(String userName,String nickname,String password,String range){
        if(administratorDao.getAdministrator(userName)!=null)return ErrorCode.EXSIT_USER;
        Administrator administrator =new Administrator();
        administrator.setId(userName);
        administrator.setAdministrator(nickname);
        administrator.setPassword(password);
        administrator.setVerifyRange(range);
        administratorDao.createNewAdministrator(administrator);
        return ErrorCode.CREATED;
    }

    public int newDev(String userName,String nickname,String password,String info){
        if(developerDao.selectDeveloper(userName)!=null)return ErrorCode.EXSIT_USER;
        Developer developer = new Developer();
        developer.setDevUsername(userName);
        developer.setDevPassword(password);
        developer.setDevInfo(info);
        developer.setDevNickname(nickname);
        List<App> appList = new ArrayList<>();
        developer.setAppList(appList);
        developerDao.addDeveloper(developer);
        return  ErrorCode.CREATED;
    }
}
