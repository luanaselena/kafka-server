package com.unla.kafka.server.producer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unla.kafka.server.model.Post;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class PostProducer {

	private static final String NOTICIAS_TOPIC = "noticias";
	private static final String MY_POSTS = "my-posts";
	private static final String LIKES_TOPIC = "likes";

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	@Autowired
	private ObjectMapper objectMapper;

	public void producePosts(List<Post> posts) {
		log.info("Se van a serializar los posts");
		var jsonPosts = serializePosts(posts);
		log.info("Post serializados: {}", jsonPosts);

		log.info("Se van a encolar en Kafka los posts: {}", jsonPosts);
		kafkaTemplate.send(NOTICIAS_TOPIC, jsonPosts);
	}
	
	public void produceOwnPosts(List<Post> posts) {
		var jsonPosts = serializePosts(posts);
		
		kafkaTemplate.send(MY_POSTS, jsonPosts);
	}
	
	public void produceLike(String like) {
		log.info("Se va a encolar en Kafka el like: {}", like);
		kafkaTemplate.send(LIKES_TOPIC, like);
	}

	private String serializePosts(List<Post> posts) {
		try {
			return objectMapper.writeValueAsString(posts);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Error serializando posts: " + posts);
		}
	}

}
