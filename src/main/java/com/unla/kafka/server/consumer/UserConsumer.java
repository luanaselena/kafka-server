package com.unla.kafka.server.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unla.kafka.server.consumer.request.FollowRequest;
import com.unla.kafka.server.producer.UserProducer;
import com.unla.kafka.server.service.FollowService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class UserConsumer {
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private FollowService followService;
	
	@Autowired
	private UserProducer userProducer;
	
	@KafkaListener(topics = "followed", groupId = "user")
	public void consumeFollow(String message) {
		log.info("Nuevo follow consumido de Kafka: ".concat(message));

		log.info("Se va a deserializar el mensaje recibido");
		var followRequest = readKafkaFollow(message);
		log.info("Follow deserializado: {}", followRequest);

		log.info("Se va a persistir un nuevo follow");
		followService.save(followRequest.getFollowerId(), followRequest.getFollowingId());
		
		userProducer.produceFollow(followRequest);				
	}
	
	private FollowRequest readKafkaFollow(String message) {
		try {
			return objectMapper.readValue(message, FollowRequest.class);
		} catch (JsonProcessingException ex) {
			throw new RuntimeException("Error deserializando mensaje de kafka: ".concat(message));
		}
	}

}
