package com.pediy.blog.service;

import com.pediy.blog.po.User;

public interface UserService {
    User checkUser(String username,String password);
}
