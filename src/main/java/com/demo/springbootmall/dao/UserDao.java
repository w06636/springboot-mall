package com.demo.springbootmall.dao;

import com.demo.springbootmall.dto.UserRegisterRequest;
import com.demo.springbootmall.model.User;

public interface UserDao {

    Integer createUser(UserRegisterRequest userRegisterRequest);

    User getUserById(Integer userId);

    User getUserByEmail(String email);
}
