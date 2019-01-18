package com.miumiu.user.service;

import com.alibaba.fastjson.JSON;
import com.miumiu.MiumiuApplication;
import com.miumiu.base.utils.FormatUtil;
import com.miumiu.domain.user.entity.User;
import com.miumiu.domain.user.response.UserResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author chimmhuang
 * @date 2019/1/18 0018 16:13
 * @description
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {MiumiuApplication.class})
public class UserServiceTest {

    @Autowired
    private UserService userService;

    String sessionKey = "mxaLVNx6LUpT9g2R7d10WQ==";

    @Test
    public void testGetUserInfoByWx() throws Exception {
        String openId = "o9osA0XLjYc6DKotPlovpuPCKvdM";
        User user = userService.getUserInfoByWx(openId);
        System.out.println(user);
    }

    @Test
    public void testUpdateSessionKey()  throws Exception {
        String sessionKey = "test";
        String openId = "o9osA0XLjYc6DKotPlovpuPCKvdM";
        userService.updateSessionKey(openId,sessionKey);
    }

    @Test
    public void testAutoLogin() throws Exception {
        String token = "04ff2456d3ec436aa91e20534e2606d820190117";
        UserResult userResult = userService.autoLogin(token);
        FormatUtil.printJson(JSON.toJSONString(userResult));
    }
}
