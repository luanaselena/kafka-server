package com.unla.kafka.server.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unla.kafka.server.model.Post;
import com.unla.kafka.server.repository.PostRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PostService {

	@Autowired
	private PostRepository postRepository;

	public void save(Post post) {
		postRepository.save(post);
	}
	
	public List<Post> getFollowersPosts(Long userId){
		log.info("Se van a consultar los posts a mostrar para el user id: {}", userId);
		
		var followersPosts = postRepository.getFollowersPosts(userId);
		
		log.info("Posts obtenidos: {}", followersPosts);
		
		return followersPosts;
	}

}
