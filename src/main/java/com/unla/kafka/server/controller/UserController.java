package com.unla.kafka.server.controller;

import com.unla.kafka.server.model.User;
import com.unla.kafka.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/login")
    public ResponseEntity<Boolean> login(@RequestParam("username") String username, @RequestParam("password") String password) {
        boolean respuesta = userService.login(username, password);
        return new ResponseEntity<Boolean>(respuesta, HttpStatus.OK);
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
