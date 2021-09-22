package com.unla.kafka.server.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Getter
@Setter
public class User {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String name;

    private String password;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private Set<Post> userPosts = new HashSet<Post>();

    public User() { }

    public User(String username, String name, String password, Set<Post> userPosts) {
        this.username = username;
        this.name = name;
        this.password = password;
        this.userPosts = userPosts;
    }

    public User(String username, String name, String password) {
        this.username = username;
        this.name = name;
        this.password = password;
    }
}
