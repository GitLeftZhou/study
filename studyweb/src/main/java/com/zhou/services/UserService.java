package com.zhou.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    public String getUser(){
        System.out.println("UserService.getUser");
        return "UserService.getUser--->";
    }

//    @Transactional
    public String saveUser(){
        System.out.println("UserService.saveUser");
        return "UserService.saveUser--->";
    }
}
