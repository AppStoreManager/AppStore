package com.ssm.demo.controller;

import com.ssm.demo.entity.Administrator;
import com.ssm.demo.entity.App;
import com.ssm.demo.entity.Developer;
import com.ssm.demo.result.ResultModel;
import com.ssm.demo.result.ResultModelTool;
import com.ssm.demo.service.AppStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class IndexController {
    @Autowired
    AppStoreService storeService;
    @RequestMapping(value = "/admin")
    @ResponseBody
    public ResultModel getAdministrator(@RequestParam String name) {
        Administrator administrator = storeService.getAdminByName(name);
        ResultModel resultModel = new ResultModel();
        resultModel.setCode(200);
        resultModel.setData(administrator);
        System.out.println(resultModel.getData());
        return ResultModelTool.handleResultModel(resultModel);
    }//测试完成

    @RequestMapping(value = "/censor",method = RequestMethod.GET)
    public ResultModel getVerifyApp(@RequestParam String name){
        Administrator administrator = storeService.getAdminByName(name);
        ResultModel resultModel = new ResultModel();
        resultModel.setCode(200);
        resultModel.setData(storeService.getUnVerApps(administrator.getVerifyRange()));
        return ResultModelTool.handleResultModel(resultModel);
    }
    @RequestMapping(value = "/censor",method = RequestMethod.POST)
    public ResultModel getNewInfo(@RequestParam String name,@RequestParam String versionId,@RequestParam boolean judge){
        ResultModel resultModel = new ResultModel();
        int errorCode = storeService.verify(name, versionId, judge);
        Map map = new HashMap<String,String>();
        map.put(versionId,storeService.getApp(name, versionId).getBasic().getExamine());
        resultModel.setData(map);
        resultModel.setCode(errorCode);
        return ResultModelTool.handleResultModel(resultModel);
    }
    @RequestMapping(value = "/deve")
    public ResultModel getDeveloper(@RequestParam String name){
        Developer developer = storeService.getDevByName(name);
        ResultModel resultModel = new ResultModel();
        resultModel.setCode(200);
        resultModel.setData(developer);
        return ResultModelTool.handleResultModel(resultModel);
    }
    @RequestMapping(value = "/apps")
    public ResultModel getApps(@RequestParam String name){
        List<App> apps = storeService.getApps(name);
        ResultModel resultModel = new ResultModel();
        resultModel.setCode(200);
        resultModel.setData(apps);
        return ResultModelTool.handleResultModel(resultModel);
    }
    @RequestMapping(value = "/appBasic",method = RequestMethod.POST)
    public ResultModel getAppBasic(@RequestParam String devname){
        List<App> apps = storeService.getAllApps(devname);
        ResultModel resultModel = new ResultModel();
        resultModel.setCode(200);
        resultModel.setData(apps);
        return ResultModelTool.handleResultModel(resultModel);
    }
    @RequestMapping(value = "/cappBasic")
    public ResultModel changeBasic(@RequestParam String basicInformation,@RequestParam String author,@RequestParam String name){
        ResultModel resultModel = new ResultModel();
        resultModel.setData(null);
        int errorCode = storeService.changeAppBasic(basicInformation, author, name);
        resultModel.setCode(errorCode);
        return ResultModelTool.handleResultModel(resultModel);
    }
    @RequestMapping(value = "/nappBasic")
    public ResultModel newBasic(@RequestParam String basicInformation,@RequestParam String author,@RequestParam String name){
        ResultModel resultModel = new ResultModel();
        resultModel.setData(null);
        int errorCode = storeService.newAppBasic(basicInformation, author, name);
        resultModel.setCode(errorCode);
        return ResultModelTool.handleResultModel(resultModel);
    }
    @RequestMapping(value = "/on")
    public ResultModel onApp(@RequestParam String name,@RequestParam String versionId){
        ResultModel resultModel = new ResultModel();
        resultModel.setData(null);
        int errorCode = storeService.upApp(name,versionId);
        resultModel.setCode(errorCode);
        return ResultModelTool.handleResultModel(resultModel);
    }
    @RequestMapping(value = "/down")
    public ResultModel downApp(@RequestParam String name,@RequestParam String versionId){
        ResultModel resultModel = new ResultModel();
        resultModel.setData(null);
        int errorCode = storeService.downApp(name,versionId);
        resultModel.setCode(errorCode);
        return ResultModelTool.handleResultModel(resultModel);
    }
    @RequestMapping(value = "/delete")
    public ResultModel delApp(@RequestParam String name){
        ResultModel resultModel = new ResultModel();
        resultModel.setData(null);
        int errorCode = storeService.deleteApps(name);
        resultModel.setCode(errorCode);
        return ResultModelTool.handleResultModel(resultModel);
    }

    @RequestMapping(value = "/newAdmin")//测试完成
    @ResponseBody
    public ResultModel newAdmin(@RequestParam String userName,@RequestParam String nickName,@RequestParam String password,@RequestParam String range){
        ResultModel resultModel = new ResultModel();
        resultModel.setData(null);
        int errorCode = storeService.newAdmin(userName, nickName, password, range);
        resultModel.setCode(errorCode);
        return ResultModelTool.handleResultModel(resultModel);
    }

    @RequestMapping(value = "/newDev")//测试完成
    @ResponseBody
    public ResultModel newDev(@RequestParam String userName,@RequestParam String nickName,@RequestParam String password,@RequestParam String info){
        ResultModel resultModel = new ResultModel();
        resultModel.setData(null);
        int errorCode = storeService.newDev(userName, nickName, password, info);
        resultModel.setCode(errorCode);
        return ResultModelTool.handleResultModel(resultModel);
    }
}
