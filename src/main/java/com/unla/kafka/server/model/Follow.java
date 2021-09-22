package com.unla.kafka.server.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Builder;
import lombok.Data;

@Data
@Entity
@Builder
public class Follow {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "followingId")
	private User following;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "followerId")
	private User follower;

	@Override
	public String toString() {
		return "Follow [id=" + id + ", following=" + following.getId() + ", follower=" + follower.getId() + "]";
	}
	
}
