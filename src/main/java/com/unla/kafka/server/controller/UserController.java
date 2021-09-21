package com.unla.kafka.server.controller;

import java.util.List;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.unla.kafka.server.consumer.request.FollowRequest;
import com.unla.kafka.server.model.Like;
import com.unla.kafka.server.model.User;
import com.unla.kafka.server.producer.UserProducer;
import com.unla.kafka.server.service.FollowService;
import com.unla.kafka.server.service.UserService;

@RestController
@CrossOrigin
public class UserController {

    @Autowired
    UserService userService;
    
    @Autowired
	private FollowService followService;
    
    @Autowired
	private UserProducer userProducer;

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
    
    @GetMapping("/getFollowingUsers")
    public ResponseEntity<List<User>> getFollowingUsers(
    		@RequestParam("username") String username){
    	User user = userService.findByUsername(username);
    	
    	List<User> followingUsers = userService.getFollowingUsers(user.getId());
    	
    	userProducer.produceFollowingUsers(followingUsers);
    	
        return new ResponseEntity<List<User>>(followingUsers,HttpStatus.OK);
    }
    
    @PostMapping("/follow")
    public ResponseEntity<String> saveFollow(
    		@RequestParam("followingId") Long followingId,
    		@RequestParam("followerId") Long followerId){
    	
    	followService.save(followerId, followingId);
    	
    	var followRequest = new FollowRequest();
    	followRequest.setFollowerId(followerId);
    	followRequest.setFollowingId(followingId);
    	
    	userProducer.produceFollow(followRequest);    	
    	
    	return new ResponseEntity<String>(Strings.EMPTY, HttpStatus.OK);
    }
    
    @GetMapping("/likes")
    public ResponseEntity<List<Like>> getLikes(
    		@RequestParam("username") String username){
    	User user = userService.findByUsername(username);
    	
    	var likes = userService.getLikes(user.getId());
    	
    	userProducer.produceLikes(likes);
    	
        return new ResponseEntity<List<Like>>(likes,HttpStatus.OK);
    }
}
