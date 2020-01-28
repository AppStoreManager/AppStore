package com.ssm.demo.service.impl;

import com.ssm.demo.dao.AppsDao;
import com.ssm.demo.entity.App;
import com.ssm.demo.entity.AppBasic;
import com.ssm.demo.entity.AppVersion;
import com.ssm.demo.service.AppStoreService;

import java.text.SimpleDateFormat;
import java.util.Date;
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
    public String newAppBasic(String basicInformation, String author,String name){
        App app = new App();
        AppBasic appBasic = new AppBasic();
        AppVersion appVersion = new AppVersion();

        appBasic.setBasicInformation(basicInformation);
        appBasic.setAuthor(author);
        appBasic.setName(name);
        appBasic.setState(false);//初始置为待审核状态 ture 审核成功已上架 false 待审核
        appBasic.setUrl("D:\\JavaHomework\\AppStore");

        appVersion.setName(name);
        appVersion.setVersionId("1.0.0");
        appVersion.setVersionInformation("应用"+name+"首次发布，欢迎使用");

        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");//可以方便地修改日期格式
        String time = dateFormat.format(now);
        appVersion.setTime(time);

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

        appsDao.insertNewVersion(app);
        return "新版本上传成功，请耐心等待审核，审核结果将尽快告知您。";
    }
    public String changeAppBasic(String basicInformation,String author,String name) {
        AppBasic appBasic = new AppBasic();
        appBasic.setBasicInformation(basicInformation);
        appBasic.setAuthor(author);

        appsDao.updateBasicInformation(appBasic, name);
        return "App基础信息更新成功，，请等待管理员审核，审核结果将第一时间通知您。";
    }
}
