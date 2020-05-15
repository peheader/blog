package com.pediy.blog.service;

import com.pediy.blog.dao.UserRepository;
import com.pediy.blog.po.User;
import com.pediy.blog.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

    @Override
    public User checkUser(String username, String password) {
        User user = userRepository.findByUsernameAndPassword(username, MD5Util.encode(password));
        return user;
    }
}
