package com.unla.kafka.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unla.kafka.server.model.Follow;
import com.unla.kafka.server.repository.FollowRepository;
import com.unla.kafka.server.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FollowService {
	
	@Autowired
	private FollowRepository followRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	public Follow save(Long followerId, Long followingId) {
		log.info("Se va guardar un follow con los datos followerId: {} y followingId: {}", followerId, followingId);
		
		var follow = Follow.builder()
				.follower(userRepository.findById(followerId))
				.following(userRepository.findById(followingId))
				.build();
		
		return followRepository.save(follow);
	}

}
