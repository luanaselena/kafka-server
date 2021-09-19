package com.unla.kafka.server.controller;

import com.unla.kafka.server.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unla.kafka.server.producer.PostProducer;
import com.unla.kafka.server.service.PostService;

import java.util.List;

@RestController
public class PostController {

	@Autowired
	private PostService postService;

	@Autowired
	private PostProducer postProducer;

	@PostMapping("/followers-post/{user_id}")
	public ResponseEntity<List<Post>> getFollowersPosts(@PathVariable("user_id") Long userId) {
		var posts = postService.getFollowersPosts(userId);

		postProducer.producePosts(posts);

		return new ResponseEntity<List<Post>>(posts, HttpStatus.OK);
	}

}
