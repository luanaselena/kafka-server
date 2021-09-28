package com.unla.kafka.server.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unla.kafka.server.model.Follow;
import com.unla.kafka.server.service.FollowService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class UserConsumer {

	private static final String FOLLOW_TOPIC = "follow";

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private FollowService followService;

	@KafkaListener(topics = FOLLOW_TOPIC, groupId = "user")
	public void consumeFollow(String message) {
		log.info("Nuevo follow consumido de Kafka: ".concat(message));

		log.info("Se va a deserializar el mensaje recibido");
		var follow = readKafkaFollow(message);
		log.info("Follow deserializado: {}", follow);

		log.info("Se va a persistir un nuevo follow");
		followService.save(follow);
	}

	private Follow readKafkaFollow(String message) {
		try {
			return objectMapper.readValue(message, Follow.class);
		} catch (JsonProcessingException ex) {
			throw new RuntimeException("Error deserializando mensaje de kafka: ".concat(message));
		}
	}

}
