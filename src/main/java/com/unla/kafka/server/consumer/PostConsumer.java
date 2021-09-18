package com.unla.kafka.server.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unla.kafka.server.model.Post;
import com.unla.kafka.server.service.PostService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class PostConsumer {

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private PostService postService;

	@KafkaListener(topics = "publish-post", groupId = "post")
	public void consumePost(String message) {
		log.info("Nuevo post consumido de Kafka: ".concat(message));

		log.info("Se va a deserializar el mensaje recibido");
		var post = readKafkaPost(message);

		log.info("Se va a persistir un nuevo post");
		postService.save(post);
	}

	private Post readKafkaPost(String message) {
		try {
			return objectMapper.readValue(message, Post.class);
		} catch (JsonProcessingException ex) {
			throw new RuntimeException("Error deserializando mensaje de kafka: ".concat(message));
		}
	}

}
