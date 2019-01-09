package com.miumiu.domain.user.response;

import com.miumiu.base.domain.response.ResultCode;
import io.swagger.annotations.ApiModel;
import lombok.ToString;

/**
 * @author Created by chimmhuang on 2018/12/15 0015.
 */
@ToString
@ApiModel(value = "UserCode",description = "user模块相关的状态码")
public enum UserCode implements ResultCode {

    /**
     * 没有从cookie中取出用户信息
     */
    USER_NO_COOKIE_VALUE(false,21001,"Cookie中没有用户信息！"),
    /**
     * 用户名为空
     */
    USER_USERNAME_IS_NULL(false,21002,"用户名为空！"),
    /**
     * 密码为空
     */
    USER_PASSWORD_IS_NULL(false,21003,"密码为空！"),
    /**
     * 登录失败
     */
    USER_LOGIN_FAIL(false,21004,"用户名或密码错误！"),
    /**
     * session中没有用户信息
     */
    USER_NO_SESSOION_VALUE(false,21005,"session中没有用户信息！");



    //操作代码
    boolean success;
    //操作代码
    int code;
    //提示信息
    String message;


    private UserCode(boolean success, int code, String message){
        this.success = success;
        this.code = code;
        this.message = message;
    }

    @Override
    public boolean success() {
        return success;
    }

    @Override
    public int code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }
}
