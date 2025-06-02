package com.demo.springbootmall.service;

import com.demo.springbootmall.dto.UserRegisterRequest;
import com.demo.springbootmall.model.User;

public interface UserService {

    Integer register(UserRegisterRequest userRegisterRequest);

    User getUserById(Integer userId);
}
