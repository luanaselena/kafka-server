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

import com.unla.kafka.server.model.User;
import com.unla.kafka.server.producer.UserProducer;
import com.unla.kafka.server.service.UserService;

@RestController
@CrossOrigin
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserProducer userProducer;

	@GetMapping("/login")
	public ResponseEntity<Boolean> login(@RequestParam("username") String username,
			@RequestParam("password") String password) {
		boolean respuesta = userService.login(username, password);
		return new ResponseEntity<Boolean>(respuesta, HttpStatus.OK);
	}

	@PostMapping("/newUser")
	public ResponseEntity<Boolean> newUser(@RequestBody User user) {
		return new ResponseEntity<Boolean>(userService.newUser(user.getUsername(), user.getName(), user.getPassword()),
				HttpStatus.OK);
	}

	@GetMapping("/getUsers")
	public ResponseEntity<List<User>> getAll() {
		return new ResponseEntity<List<User>>(userService.getAll(), HttpStatus.OK);
	}

	// Traer usuarios que me siguen
	@GetMapping("/getFollowers")
	public ResponseEntity<List<User>> getFollowers(@RequestParam("username") String username) {
		User user = userService.findByUsername(username);

		List<User> followerUsers = userService.getFollowers(user.getId());

		return new ResponseEntity<List<User>>(followerUsers, HttpStatus.OK);
	}

	// Traer usuarios seguidos
	@GetMapping("/getFollowingUsers")
	public ResponseEntity<List<User>> getFollowingUsers(@RequestParam("username") String username) {
		User user = userService.findByUsername(username);

		List<User> followingUsers = userService.getFollowingUsers(user.getId());

		return new ResponseEntity<List<User>>(followingUsers, HttpStatus.OK);
	}

	@PostMapping("/follow")
	public ResponseEntity<String> saveFollow(@RequestParam("followingId") Long followingId,
			@RequestParam("followerUsername") String followerUsername) {

		Long followerId = userService.findByUsername(followerUsername).getId();

		userProducer.produceFollow(followerId, followingId);

		return new ResponseEntity<String>(Strings.EMPTY, HttpStatus.OK);
	}	
	
}
