package com.raccoon.webapp.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired private UserRepository repo;

    public List<User> findAll() {
        return (List<User>) repo.findAll();
    }
}
