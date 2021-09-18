package com.unla.kafka.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unla.kafka.server.producer.PostProducer;
import com.unla.kafka.server.service.PostService;

@RestController
public class PostController {

	@Autowired
	private PostService postService;

	@Autowired
	private PostProducer postProducer;

	@PostMapping("/followers-post/{user_id}")
	public void getFollowersPosts(@PathVariable("user_id") Long userId) {
		var posts = postService.getFollowersPosts(userId);

		postProducer.producePosts(posts);
	}

}
