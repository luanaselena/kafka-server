package com.unla.kafka.server.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unla.kafka.server.model.Follow;
import com.unla.kafka.server.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class UserProducer {

	private static final String FOLLOW_TOPIC = "follow";

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private UserRepository userRepository;

	public void produceFollow(Long followerId, Long followingId) {
		var follow = Follow.builder().follower(userRepository.findById(followerId))
				.following(userRepository.findById(followingId)).build();

		log.info("Se va a serializar el follow: {}", follow);
		var jsonFollow = serializeFollow(follow);

		log.info("Se va a encolar en Kafka el follow: {}", jsonFollow);
		kafkaTemplate.send(FOLLOW_TOPIC, jsonFollow);
	}

	private String serializeFollow(Follow follow) {
		try {
			return objectMapper.writeValueAsString(follow);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Error serializando follow: " + follow);
		}
	}

}
