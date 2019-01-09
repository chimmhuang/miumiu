package com.miumiu.user.service;

import com.miumiu.domain.user.entity.User;
import com.miumiu.user.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author chimmhuang
 * @date 2019/1/9 0009 17:51
 * @description 用户模块service
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    /**
     * 根据openid查找用户
     * @param openid
     * @return User/null
     */
    public User getUserInfoByWx(String openid) {
        return userDao.findUserByWxOpenid(openid);
    }
}
