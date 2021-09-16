package com.unla.kafka.server.controller;

import com.unla.kafka.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/login")
    public boolean login(String username, String password){
        return userService.login(username, password);
    }
}
