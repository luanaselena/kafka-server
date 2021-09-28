package com.unla.kafka.server.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unla.kafka.server.model.Like;
import com.unla.kafka.server.model.Post;
import com.unla.kafka.server.repository.PostRepository;
import com.unla.kafka.server.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class PostProducer {

	private static final String NOTICIAS_TOPIC = "noticias";
	private static final String LIKES_TOPIC = "likes";

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private UserRepository userRepository;

	public void producePost(Post post) {
		log.info("Se va a serializar el post: {}", post);
		var jsonPost = serializePost(post);

		log.info("Se va a encolar en Kafka el post: {}", jsonPost);
		kafkaTemplate.send(NOTICIAS_TOPIC, jsonPost);
	}

	public void produceLike(Long postId, Long userLikeId) {
		var like = Like.builder()
				.post(postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post no encontrado")))
				.userLike(userRepository.findById(userLikeId)).build();

		log.info("Se va a serializar el like: {}", like);
		var jsonLike = serializeLike(like);

		log.info("Se va a encolar en Kafka el like: {}", jsonLike);
		kafkaTemplate.send(LIKES_TOPIC, jsonLike);
	}

	private String serializePost(Post post) {
		try {
			return objectMapper.writeValueAsString(post);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Error serializando post: " + post);
		}
	}

	private String serializeLike(Like like) {
		try {
			return objectMapper.writeValueAsString(like);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Error serializando like: " + like);
		}
	}

}
