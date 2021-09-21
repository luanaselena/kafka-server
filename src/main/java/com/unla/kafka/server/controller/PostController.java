package com.unla.kafka.server.controller;

import java.util.List;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.unla.kafka.server.model.Post;
import com.unla.kafka.server.model.User;
import com.unla.kafka.server.producer.PostProducer;
import com.unla.kafka.server.service.LikeService;
import com.unla.kafka.server.service.PostService;
import com.unla.kafka.server.service.UserService;

@RestController
@CrossOrigin
public class PostController {

	@Autowired
	private PostService postService;

	@Autowired
	private PostProducer postProducer;
	
    @Autowired
    private UserService userService;
    
    @Autowired
	private LikeService likeService;

    @GetMapping("/followers-post")
    public ResponseEntity<List<Post>> getFollowersPosts(@RequestParam("username") String username) {
		User user = userService.findByUsername(username);
		Long userId = user.getId();
		
		var posts = postService.getFollowersPosts(userId);
		
		postProducer.producePosts(posts);
        return new ResponseEntity<List<Post>>(posts, HttpStatus.OK);
    }
    
    @PostMapping("/like")
    public ResponseEntity<String> saveLike(
    		@RequestParam("username") String username,
    		@RequestParam("post_id") Long postId){
    	var user = userService.findByUsername(username);
		var userId = user.getId();
    	
    	likeService.save(postId, userId);    	
    	
    	return new ResponseEntity<String>(Strings.EMPTY, HttpStatus.OK);
    }

}
