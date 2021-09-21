package com.unla.kafka.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.unla.kafka.server.model.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
	
	@Query(nativeQuery=true, value="SELECT * FROM post WHERE user_id IN (SELECT following_id FROM follow WHERE follower_id = :userId)")
	List<Post> getFollowersPosts(Long userId);
	
	@Query(nativeQuery=true, value="SELECT * FROM post WHERE user_id = :userId")
	List<Post> findByUserId(Long userId);

}
