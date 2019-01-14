package com.miumiu.user.dao;

import com.miumiu.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author chimmhuang
 * @date 2019/1/9 0009 17:49
 * @description 用户表相关操作
 */
public interface UserDao extends JpaRepository<User, Integer> {

    User findByWxOpenid(String openid);
}
