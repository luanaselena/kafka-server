package com.unla.kafka.server.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unla.kafka.server.model.Post;
import com.unla.kafka.server.service.PostService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class PostController {
	
	@Autowired
	private PostService postService;
	
	@PostMapping("/followers-post/{user_id}")
	public List<Post> getFollowersPosts(@PathVariable("user_id") Long userId) {
		log.info("Se van a consultar los posts del user id: {}", userId);
		
		var posts = postService.getFollowersPosts(userId);
		
		return posts;	
	}

}
