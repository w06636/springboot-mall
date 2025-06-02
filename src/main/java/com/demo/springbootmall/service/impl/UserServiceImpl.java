package com.demo.springbootmall.service.impl;

import com.demo.springbootmall.dao.UserDao;
import com.demo.springbootmall.dto.UserRegisterRequest;
import com.demo.springbootmall.model.User;
import com.demo.springbootmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) {

        return userDao.createUser(userRegisterRequest);
    }

    @Override
    public User getUserById(Integer userId) {

        return userDao.getUserById(userId);
    }
}
