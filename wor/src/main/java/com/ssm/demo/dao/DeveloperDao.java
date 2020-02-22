package com.ssm.demo.dao;
import com.ssm.demo.entity.Developer;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ZhutianLin
 * @version 1.0.0
 * */
@Repository
public interface DeveloperDao {
    //增加
    boolean addDeveloper(Developer dev);//增加开发者

    //删除
    boolean deleteDeveloper(String username);//删除开发者

    //修改
    boolean updateDeveloper(Developer dev);//修改开发者

    //查找
    Developer selectDeveloper(String name);//获取开发者


}
