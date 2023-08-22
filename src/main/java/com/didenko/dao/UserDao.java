package com.didenko.dao;

import com.didenko.entity.User;

public class UserDao extends BaseDao<Long, User> {

    public UserDao() {
        super(User.class);
    }
}
