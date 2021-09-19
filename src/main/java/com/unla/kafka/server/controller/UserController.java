package com.unla.kafka.server.controller;

import com.unla.kafka.server.model.User;
import com.unla.kafka.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
@CrossOrigin
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/login")
    public ResponseEntity<Boolean> login(String username, String password){
        return new ResponseEntity<Boolean>(userService.login(username, password), HttpStatus.OK);
    }

    @PostMapping("/newUser")
    public ResponseEntity<Boolean> newUser(@RequestBody User user){
        return new ResponseEntity<Boolean>(userService.newUser(user.getUsername(), user.getName(), user.getPassword()), HttpStatus.OK);
    }

    @GetMapping("/getUsers")
    public ResponseEntity<List<User>> getAll(){
        return new ResponseEntity<List<User>>(userService.getAll(),HttpStatus.OK);
    }
}
