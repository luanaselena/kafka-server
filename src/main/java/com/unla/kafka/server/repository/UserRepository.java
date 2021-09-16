package com.unla.kafka.server.repository;

import com.unla.kafka.server.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Serializable> {
    public abstract User findById(Long id);

    public abstract User findByUsername(String username);
}
