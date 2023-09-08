package com.didenko.dao;

import com.didenko.entity.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository

public class UserRepository extends BaseRepository<Long, User> {

    public UserRepository() {
        super(User.class);
    }
}
