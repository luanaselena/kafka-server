package com.unla.kafka.server.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unla.kafka.server.model.Like;
import com.unla.kafka.server.model.Post;
import com.unla.kafka.server.service.LikeService;
import com.unla.kafka.server.service.PostService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class PostConsumer {
	
	private static final String NOTICIAS_TOPIC = "noticias";
	private static final String LIKES_TOPIC = "likes";

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private PostService postService;

	@Autowired
	private LikeService likeService;

	@KafkaListener(topics = NOTICIAS_TOPIC, groupId = "post")
	public void consumePost(String message) {
		log.info("Nuevo post consumido de Kafka: ".concat(message));

		log.info("Se va a deserializar el mensaje recibido");
		var post = readKafkaPost(message);

		log.info("Se va a persistir el nuevo post: {}", post);
		postService.save(post);
	}

	@KafkaListener(topics = LIKES_TOPIC, groupId = "post")
	public void consumeLike(String message) {
		log.info("Nuevo like consumido de Kafka: ".concat(message));

		log.info("Se va a deserializar el mensaje recibido");
		var like = readKafkaLike(message);

		log.info("Se va a persistir un nuevo like");
		likeService.save(like);
	}

	private Post readKafkaPost(String message) {
		try {
			return objectMapper.readValue(message, Post.class);
		} catch (JsonProcessingException ex) {
			throw new RuntimeException("Error deserializando mensaje de kafka: ".concat(message));
		}
	}

	private Like readKafkaLike(String message) {
		try {
			return objectMapper.readValue(message, Like.class);
		} catch (JsonProcessingException ex) {
			throw new RuntimeException("Error deserializando mensaje de kafka: ".concat(message));
		}
	}

}
