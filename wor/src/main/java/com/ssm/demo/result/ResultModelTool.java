package com.ssm.demo.result;

import java.util.HashMap;
import java.util.Map;

public class ResultModelTool {
    public static ResultModel handleResultModel(ResultModel resultModel) {

        ResultModel handledModel = new ResultModel();
        Map map = new HashMap();
        if (resultModel != null) {
            int error = resultModel.getCode();
            handledModel.setCode(error);
            switch (error) {
                case 200:
                    handledModel.setMsg("请求成功");
                    handledModel.setData(map);
                    handledModel.setData(resultModel.getData());
                    break;
                case 201:
                    handledModel.setMsg("新内容创建成功，请耐心等待审核，审核结果将尽快通知您");
                    handledModel.setData(map);
                    handledModel.setData(resultModel.getData());
                    break;
                case 202:
                    handledModel.setMsg("信息修改成功，请耐心等待审核，审核结果将尽快通知您");
                    handledModel.setData(resultModel.getData());
                    handledModel.setData(resultModel.getData());
                    break;
                case 203:
                    handledModel.setMsg("该App已存在，请重设名称");
                    handledModel.setData(resultModel.getData());
                    break;
                case 204:
                    handledModel.setMsg("删除成功");
                    handledModel.setData(resultModel.getData());
                    break;
                case 400:
                    handledModel.setMsg("请求的地址不存在或者包含不支持的参数");
                    handledModel.setData(resultModel.getData());
                    break;
                case 401:
                    handledModel.setMsg("权限不足");
                    handledModel.setData(map);
                    break;
                case 403:
                    handledModel.setMsg("禁止访问");
                    handledModel.setData(map);
                    break;
                case 404:
                    handledModel.setMsg("请求的资源不存在");
                    handledModel.setData(map);
                    break;
                case 421:
                    handledModel.setMsg("用户名已被占用");
                    handledModel.setData(map);
                    break;
                case 422:
                    handledModel.setMsg("用户不存在");
                    handledModel.setData(map);
                    break;
                case 423:
                    handledModel.setMsg("已确认管理员权限");
                    handledModel.setData(map);
                    handledModel.setData(resultModel.getData());
                    break;
                case 424:
                    handledModel.setMsg("已确认开发者身份");
                    handledModel.setData(map);
                    handledModel.setData(resultModel.getData());
                    break;
                case 500:
                    handledModel.setMsg("密码错误");
                    handledModel.setData(map);
                    break;
            }
        }
        return handledModel;
    }
}
