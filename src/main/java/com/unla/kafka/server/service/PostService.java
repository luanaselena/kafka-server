package com.unla.kafka.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unla.kafka.server.model.Post;
import com.unla.kafka.server.repository.PostRepository;

@Service
public class PostService {

	@Autowired
	private PostRepository postRepository;

	public void save(Post post) {
		postRepository.save(post);
	}

}
