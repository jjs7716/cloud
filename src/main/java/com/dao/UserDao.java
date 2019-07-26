package com.dao;

import com.domain.User;
import java.util.List;

public interface UserDao {
    /**
     * 添加一个用户
     * @param user
     */
    void insert(User user);

    /**
     * 用户校验
     * @param user
     * @return
     */
    List<User> checkUser(User user);

    /**
     * 用户名校验
     * @param user
     * @return
     */
    User checkUsername(User user);


    /**
     * 修改密码
     * @param user
     */
    void updatePassword(User user);
}
