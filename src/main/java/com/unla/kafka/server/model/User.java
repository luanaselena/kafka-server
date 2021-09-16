package com.unla.kafka.server.model;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
