package com.demo.springbootmall.service.impl;

import com.demo.springbootmall.dao.UserDao;
import com.demo.springbootmall.dto.UserLoginRequest;
import com.demo.springbootmall.dto.UserRegisterRequest;
import com.demo.springbootmall.model.User;
import com.demo.springbootmall.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) {

        User user = userDao.getUserByEmail(userRegisterRequest.getEmail());
        if (user != null) {
            log.warn("使用者email已存在 id:{}, email:{}", user.getUserId(), user.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        // 使用MD5生成密碼雜湊值
        String hashedPassword = DigestUtils.md5DigestAsHex(userRegisterRequest.getPassword().getBytes());
        log.info("加密過後密碼: {}", hashedPassword);
        userRegisterRequest.setPassword(hashedPassword);

        // 建立帳號
        return userDao.createUser(userRegisterRequest);
    }

    @Override
    public User getUserById(Integer userId) {

        return userDao.getUserById(userId);
    }

    @Override
    public User login(UserLoginRequest userLoginRequest) {

        User user = userDao.getUserByEmail(userLoginRequest.getEmail());
        if (user == null) {
            log.warn("尚未註冊 email: {}", userLoginRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        // 使用MD5生成密碼雜湊值
        String hashedPassword = DigestUtils.md5DigestAsHex(userLoginRequest.getPassword().getBytes());
        log.info("hashedPassword : {}", hashedPassword);

        if (StringUtils.equals(user.getPassword(), hashedPassword)) {
            return user;
        } else {
            log.warn("密碼不正確 {}", user.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
