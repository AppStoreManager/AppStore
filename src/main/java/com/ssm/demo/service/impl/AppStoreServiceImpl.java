package com.ssm.demo.service.impl;

import com.ssm.demo.dao.AppsDao;
import com.ssm.demo.entity.App;
import com.ssm.demo.entity.AppBasic;
import com.ssm.demo.entity.AppVersion;
import com.ssm.demo.service.AppStoreService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class AppStoreServiceImpl implements AppStoreService {
    @Autowired
    AppsDao appsDao;
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

        appBasic.setState(false);//初始置为待审核状态 ture 审核成功已上架 false 待审核

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
        appBasic.setState(false);//设置为待审核
        app.setBasic(appBasic);

        appsDao.insertNewVersion(app);
        return "新版本上传成功，请耐心等待审核，审核结果将尽快告知您。";
    }
    public String changeAppBasic(String basicInformation,String author,String name) {
        AppBasic appBasic = new AppBasic();
        appBasic.setBasicInformation(basicInformation);
        appBasic.setAuthor(author);
        appBasic.setState(false);//设置为待审核

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
        appBasic.setState(false);//设置为待审核
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
            if (app.getBasic().isState() == false) {
                iter.remove();
            }
        }
        return apps;
    }
    public String deleteApps(String name){
        appsDao.delete(name);
        return "已删除所有版本的"+name+"APP";
    }

    //上架下架这里暂时没有写，发现少了一个状态参数，之前的state只是判断审核通过与否，这里还要追加上架/下架，追加涉及其他改动，需要商议
    public String upApp(String name,String versionId){

    }
    public String downApp(String name,String versionId){

    }

    //审核这里其实应该是三个状态，但是最开始欠考虑用了Boolean，只有两个状态了，暂时先用两个状态写
    //参数judge的值取决于管理员的选择，选择通过judge为true，否则为false
    public String verify(String name,String versionId,boolean judge){
        App app = (App)appsDao.selectByName(name).get(0);

        AppBasic appBasic = app.getBasic();

        if(judge) appBasic.setState(true);//设置为审核通过
        else appBasic.setState(false);//设置为审核未通过
        app.setBasic(appBasic);
        appsDao.updateVersion(app,name,versionId);
    }

}
