package com.unla.kafka.server.controller.response;

import java.util.List;

import com.unla.kafka.server.model.Post;
import com.unla.kafka.server.model.User;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FollowersPostResponse {
	
	private Post post;
	
	private List<User> likedsUsers;

}
