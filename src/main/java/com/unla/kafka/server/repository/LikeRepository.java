package com.unla.kafka.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unla.kafka.server.model.Like;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long>{

}
