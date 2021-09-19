package com.unla.kafka.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unla.kafka.server.model.Follow;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long>{
}
