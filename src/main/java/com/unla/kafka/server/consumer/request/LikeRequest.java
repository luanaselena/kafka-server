package com.unla.kafka.server.consumer.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class LikeRequest {
	
	@JsonProperty("post_id")
	private Long postId;

	@JsonProperty("user_like_id")
	private Long userLikeId;

}
