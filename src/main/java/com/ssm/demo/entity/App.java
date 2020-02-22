package com.ssm.demo.entity;
public class App {
    AppBasic basic;
    AppVersion version;
    String appName;
    public String getAppName(){
        return appName;
    }
<<<<<<< HEAD

    public void setAppName(String appName){
        this.appName=appName;
    }

=======
    
    pubic void setAppName(String appName){
        this.appName=appName;
    
    }
>>>>>>> f51167663e1425e7fe882cdf0a423af7826ace0f
    public AppBasic getBasic() {
        return basic;
    }

    public AppVersion getVersion() {
        return version;
    }

    public void setVersion(AppVersion version) {
        this.version = version;
    }

    public void setBasic(AppBasic basic) {
        this.basic = basic;
    }

}