package com.unla.kafka.server.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.unla.kafka.server.model.User;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Serializable> {
    public abstract User findById(Long id);

    public abstract User findByUsername(String username);
    
    @Query(nativeQuery=true, value="SELECT * FROM user WHERE id IN (SELECT user_like_id FROM likes WHERE post_id = :postId)")
    public abstract List<User> getLikedsUsers(Long postId);
}
