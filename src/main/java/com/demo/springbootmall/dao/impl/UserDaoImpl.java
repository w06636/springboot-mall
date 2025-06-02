package com.demo.springbootmall.dao.impl;

import com.demo.springbootmall.dao.UserDao;
import com.demo.springbootmall.dto.UserRegisterRequest;
import com.demo.springbootmall.model.User;
import com.demo.springbootmall.rowmapper.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Integer createUser(UserRegisterRequest userRegisterRequest) {

        String sql = "insert into user (email, password, create_date, last_modified_date) " +
                "values (:email, :password, :createDate, :lastModifiedDate)";

        Map<String, Object> map = new HashMap<>();
        map.put("email", userRegisterRequest.getEmail());
        map.put("password", userRegisterRequest.getPassword());

        Date date = new Date();
        map.put("createDate", date);
        map.put("lastModifiedDate", date);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }

    @Override
    public User getUserById(Integer userId) {

        String sql = "select user_id, email, password, create_date, " +
                "last_modified_date from user where user_id = :userId";

        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);

        List<User> userList = namedParameterJdbcTemplate.query(sql, map, new UserRowMapper());
        if (!userList.isEmpty()) {
            return userList.get(0);
        }
        return null;
    }

    @Override
    public User getUserByEmail(String email) {

        String sql = "select user_id, email, password, create_date, last_modified_date " +
                "from user where email = :email";

        Map<String, Object> map = new HashMap<>();
        map.put("email", email);

        List<User> list = namedParameterJdbcTemplate.query(sql, map, new UserRowMapper());
        if (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }
}
