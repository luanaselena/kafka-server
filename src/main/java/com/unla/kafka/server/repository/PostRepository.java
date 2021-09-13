package com.unla.kafka.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unla.kafka.server.model.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

}
