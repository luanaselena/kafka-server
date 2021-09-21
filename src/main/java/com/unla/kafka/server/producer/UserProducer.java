package com.unla.kafka.server.producer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unla.kafka.server.consumer.request.FollowRequest;
import com.unla.kafka.server.model.Like;
import com.unla.kafka.server.model.User;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class UserProducer {

	private static final String FOLLOW_TOPIC = "follow";
	private static final String USER_TOPIC = "user";
	private static final String USER_FOLLOWERS_TOPIC = "user-followers";
	private static final String LIKE_TOPIC = "like";

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
	
	public void produceFollowingUsers(List<User> users) {
		var jsonFollow = serializeUsers(users);
		
		kafkaTemplate.send(USER_TOPIC, jsonFollow);
	}
	
	public void produceFollowerUsers(List<User> users) {
		var jsonFollow = serializeUsers(users);
		
		kafkaTemplate.send(USER_FOLLOWERS_TOPIC, jsonFollow);
	}
	
	public void produceLikes(List<Like> likes) {
		var jsonLikes = serializeLikes(likes);
		
		kafkaTemplate.send(LIKE_TOPIC, jsonLikes);
	}
	
	private String serializeFollow(FollowRequest followRequest) {
		try {
			return objectMapper.writeValueAsString(followRequest);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Error serializando follow: " + followRequest);
		}
	}
	
	private String serializeUsers(List<User> users) {
		try {
			return objectMapper.writeValueAsString(users);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Error serializando users: " + users);
		}
	}
	
	private String serializeLikes(List<Like> likes) {
		try {
			return objectMapper.writeValueAsString(likes);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Error serializando likes: " + likes);
		}
	}
}
