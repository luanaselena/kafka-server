package com.unla.kafka.server.consumer.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class FollowRequest {
	
	@JsonProperty("following_id")
	private Long followingId;

	@JsonProperty("follower_id")
	private Long followerId;

}
