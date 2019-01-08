package com.miumiu.user.controller;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

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

    @PostMapping("/wxmini/login")
    public String login(String code) {
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid={APPID}&secret={APPSECRET}&js_code={code}&grant_type=authorization_code";

        Map<String, String> params = new HashMap<>();
        params.put("APPID", APPID);
        params.put("APPSECRET", APPSECRET);
        params.put("code", code);

        String body = restTemplate.getForObject(url, String.class, params);
        return JSON.toJSONString(JSON.parse(body));
    }

}
