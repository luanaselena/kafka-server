package com.unla.kafka.server.controller;

import com.unla.kafka.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/login")
    public boolean login(String username, String password){
        return userService.login(username, password);
    }

    @PostMapping("/newUser")
    public boolean newUser(String username, String name, String password){
        return userService.newUser(username, name, password);
    }
}
