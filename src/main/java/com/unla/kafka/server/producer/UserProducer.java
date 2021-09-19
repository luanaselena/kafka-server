package com.unla.kafka.server.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unla.kafka.server.consumer.request.FollowRequest;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class UserProducer {

	private static final String FOLLOW_TOPIC = "follow";

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	@Autowired
	private ObjectMapper objectMapper;

	public void produceFollow(FollowRequest followRequest) {
		log.info("Se va a serializar el follow: {}", followRequest);
		var jsonFollow = serializeFollow(followRequest);
		log.info("Follow serializado: {}", jsonFollow);

		log.info("Se va a encolar en Kafka el follow: {}", jsonFollow);
		kafkaTemplate.send(FOLLOW_TOPIC, jsonFollow);
	}
	
	private String serializeFollow(FollowRequest followRequest) {
		try {
			return objectMapper.writeValueAsString(followRequest);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Error serializando follow: " + followRequest);
		}
	}
}
