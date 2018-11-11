package com.zyx.miaosha.service;

import com.zyx.miaosha.dao.UserDao;
import com.zyx.miaosha.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author:zhangyx
 * @Date:Created in 20:182018/11/11
 * @Modified By:
 */
@Service
public class UserService {
    @Autowired
    private UserDao userDao;
    public User getById(Integer id){
        return userDao.getById(id);
    }
}
