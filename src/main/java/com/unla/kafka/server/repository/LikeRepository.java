package com.unla.kafka.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.unla.kafka.server.model.Like;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long>{
	
	@Query(nativeQuery=true, value="SELECT * FROM likes WHERE post_id IN (SELECT id FROM post WHERE user_id = :userId)")
	List<Like> getLikes(Long userId);

}
