package com.unla.kafka.server.controller;

import java.util.ArrayList;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unla.kafka.server.controller.response.FollowersPostResponse;
import com.unla.kafka.server.model.Like;
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
    
    @Autowired
	private ObjectMapper objectMapper;

    @GetMapping("/followers-post")
    public ResponseEntity<List<FollowersPostResponse>> getFollowersPosts(@RequestParam("username") String username) {
		User user = userService.findByUsername(username);
		Long userId = user.getId();
		
		var posts = postService.getFollowersPosts(userId);
		postProducer.producePosts(posts);
		
		List<FollowersPostResponse> response = new ArrayList<>();
		posts.forEach(
				post -> {
					FollowersPostResponse follower = FollowersPostResponse.builder()
									.post(post)
									.likedsUsers(userService.getLikedsUsers(post.getId()))
									.build();
					
					response.add(follower);					
				}
			);
		
        return new ResponseEntity<List<FollowersPostResponse>>(response, HttpStatus.OK);
    }
    
    @PostMapping("/like")
    public ResponseEntity<String> saveLike(
    		@RequestParam("username") String username,
    		@RequestParam("post_id") Long postId){
    	var user = userService.findByUsername(username);
		var userId = user.getId();
    	
    	var like = likeService.save(postId, userId); 
    	
    	var jsonLike = serializeLike(like);
    	
    	postProducer.produceLike(jsonLike);
    	
    	return new ResponseEntity<String>(Strings.EMPTY, HttpStatus.OK);
    }
    
    @PostMapping("/post")
    public ResponseEntity<String> savePost(
    		@RequestParam("username") String username,
    		@RequestParam("title") String title,
    		@RequestParam("text") String text,
    		@RequestParam("image") String image){
    	
    	var post = new Post();
    	post.setUsername(username);
    	post.setTitle(title);
    	post.setText(text);
    	post.setImage(image);
    	post.setUser(userService.findByUsername(username));
    	
    	postService.save(post);
    	
    	return new ResponseEntity<String>(Strings.EMPTY, HttpStatus.OK);
    } 
    
    private String serializeLike(Like like) {
		try {
			return objectMapper.writeValueAsString(like);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Error serializando like: " + like);
		}
	}

}
