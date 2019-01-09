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

    /**
     * 保存用户
     * @param user 用户实体类
     */
    public void saveUserInfo(User user) {
        userDao.save(user);
    }

    /**
     * 更新sessionkey
     * @param openId
     * @param sessionKey
     */
    public void updateSessionKey(String openId, String sessionKey) {
        User user = userDao.findUserByWxOpenid(openId);
        if (user != null) {
            user.setSessionKey(sessionKey);
            userDao.save(user);
        }
    }
}
