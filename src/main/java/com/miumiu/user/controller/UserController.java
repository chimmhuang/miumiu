package com.miumiu.user.controller;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

/**
 * @author chimmhuang
 * @date 2019/1/8 0008 12:35
 * @description 用户模块controller
 */
@RestController
@RequestMapping("/user")
@Api(tags = "用户模块相关接口")
public class UserController {

    @Value("${wx-mini-Program.AppID}")
    private String APPID;
    @Value("${wx-mini-Program.AppSecret}")
    private String APPSECRET;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 微信小程序登录
     * @param map 临时TOKEN {"code":"ASHASJNFA"}
     * @return
     */
    @PostMapping("/wxmini/login")
    @ApiOperation(value = "微信小程序登录测试",notes = "测试阶段，该接口还未完成")
    @ApiImplicitParam(name = "code",value = "临时TOKEN",paramType = "body",example = "KWHASBNJKGIAnkg")
    public String login(@RequestBody Map<String,String> map) {
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid={APPID}&secret={APPSECRET}&js_code={code}&grant_type=authorization_code";

        Map<String, String> params = new HashMap<>();
        params.put("APPID", APPID);
        params.put("APPSECRET", APPSECRET);
        params.put("code", map.get("code"));

        String body = restTemplate.getForObject(url, String.class, params);
        return JSON.toJSONString(JSON.parse(body));
    }

}
