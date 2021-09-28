package com.unla.kafka.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unla.kafka.server.model.Follow;
import com.unla.kafka.server.repository.FollowRepository;

@Service
public class FollowService {

	@Autowired
	private FollowRepository followRepository;

	public Follow save(Follow follow) {
		return followRepository.save(follow);
	}

}
