package com.unla.kafka.server.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String title;

	private String image;

	private String text;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="user_id", nullable = false)
	@JsonIgnore
	private User user;

	public Post() {
	}

	@Override
	public String toString() {
		return "Post [id=" + id + ", title=" + title + ", image=" + image + ", text=" + text + "]";
	}
	
}
