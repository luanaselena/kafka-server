package com.unla.kafka.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unla.kafka.server.model.Like;
import com.unla.kafka.server.repository.LikeRepository;

@Service
public class LikeService {

	@Autowired
	private LikeRepository likeRepository;

	public Like save(Like like) {
		return likeRepository.save(like);
	}

}
