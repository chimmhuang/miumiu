package com.miumiu.user.service;

import com.miumiu.base.domain.PageResult;
import com.miumiu.domain.user.entity.User;
import com.miumiu.user.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
        return userDao.findByWxOpenid(openid);
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
        User user = userDao.findByWxOpenid(openId);
        if (user != null) {
            user.setSessionKey(sessionKey);
            userDao.save(user);
        }
    }

    /**
     * 分页查询所有用户信息
     * @param page 当前页（从1开始）
     * @param size 一页显示数
     */
    public PageResult<User> findAll(int page, int size) {
        Pageable pageable = new PageRequest(page-1,size);
        Page<User> page1 = userDao.findAll(pageable);

        return new PageResult<User>(page1.getTotalElements(), page, page1.getTotalPages(), page1.getContent());
    }
}
