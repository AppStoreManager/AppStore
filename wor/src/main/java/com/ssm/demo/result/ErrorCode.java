package com.ssm.demo.result;

public interface ErrorCode {
        /**
         200 请求成功
         201 创建成功
         202 修改成功
         203 已存在重复内容
         204 删除成功
         400 请求的地址不存在或者包含不支持的参数
         401 未授权
         403 被禁止访问
         404 请求的资源不存在
         421 用户已存在
         422 用户不存在
         423 用户为管理员
         424 用户为开发者
         500 密码错误
         */
        int OK = 200;
        int CREATED = 201;
        int UPDATED = 202;
        int EXSITED = 203;
        int DELETED = 204;
        int BAD_REQUEST = 400;
        int UNAUTHORIZED = 401;
        int FORBIDDEN = 403;
        int NOT_FOUND = 404;
        int EXSIT_USER = 421;
        int NO_USER = 422;
        int PASSWORD_ERROR = 500;
        int ADMINISTRATOR = 423;
        int DEVELOPER = 424;
}
