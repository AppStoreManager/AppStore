package com.ssm.demo.service;

public interface AppStoreService {
    public String newAppBasic(String basicInformation,String author,String name);
    public String newAppVersion(String name,String versionInformation,String versionId);
    public String changeAppBasic(String basicInformation,String author,String name);
}
