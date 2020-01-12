package com.ssm.demo.dao;

import com.ssm.demo.entity.AppInformation;

public interface AppsDao{
 void insert(AppInformation appIn);
 void delete(String name);
 void updateBasicInformation(String basicInformation);
 void updateVersion(String versionInformation,String Version,String time);
 AppInformation selectByName(String name);
}