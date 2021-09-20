package com.unla.kafka.server.controller;

import com.unla.kafka.server.model.Post;
import com.unla.kafka.server.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.unla.kafka.server.producer.PostProducer;
import com.unla.kafka.server.service.PostService;
import com.unla.kafka.server.service.UserService;

import java.util.List;

@RestController
@CrossOrigin
public class PostController {

	@Autowired
	private PostService postService;

	@Autowired
	private PostProducer postProducer;
	
    @Autowired
    private UserService userService;

    @GetMapping("/followers-post")
    public ResponseEntity<List<Post>> login(@RequestParam("username") String username) {
		User user = userService.findByUsername(username);
		Long userId = user.getId();
		System.out.print(userId);
		
		var posts = postService.getFollowersPosts(userId);
		System.out.print(posts);
		
		postProducer.producePosts(posts);
        return new ResponseEntity<List<Post>>(posts, HttpStatus.OK);
    }
    
//	@GetMapping("/followers-post")
//	public ResponseEntity<List<Post>> getFollowersPosts(@RequestParam("username") String username) {
//		
//		User user = userService.findByUsername(username);
//		Long userId = user.getId();
//		
//		var posts = postService.getFollowersPosts(userId);
//
//		postProducer.producePosts(posts);
//
//		return new ResponseEntity<List<Post>>(posts, HttpStatus.OK);
//	}

}
