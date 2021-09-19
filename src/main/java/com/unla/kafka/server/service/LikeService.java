package com.unla.kafka.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unla.kafka.server.model.Like;
import com.unla.kafka.server.repository.LikeRepository;
import com.unla.kafka.server.repository.PostRepository;
import com.unla.kafka.server.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LikeService {
	
	@Autowired
	private LikeRepository likeRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	public Like save(Long postId, Long userLikeId) {
		log.info("Se va guardar un like con los datos postId: {} y userLikeId: {}", postId, userLikeId);
		
		var like = Like.builder()
				.post(postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post no encontrado")))
				.userLike(userRepository.findById(userLikeId))
				.build();
				
		
		return likeRepository.save(like);
	}

}
