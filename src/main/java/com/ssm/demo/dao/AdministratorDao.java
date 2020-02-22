package com.dao;

import com.entity.Administrator;

import java.util.Map;

public interface AdministratorDao {
    boolean createNewAdministrator(Administrator administrator);
    Administrator getAdministrator(String userName);
    boolean deleteAdministrator(String userName);
    String getVerifyRange(String userName);
    String loginCheck(String userName); //返回用户的密码
    boolean changePassword(Map changePassword);//<username, password>
    boolean changeNickname(Map changeNickname);//<username, nickname>
    boolean changeVerifyRange(Map changeRange);//<username, range>
}

//设置boolean为返回值是在实现dao时候，需要先判断表中有无administrator的信息
//即先调用getAdministrator，若返回值不为空，在执行delete/update/insert，返回true，否则fanhuifalse