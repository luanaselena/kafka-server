package com.unla.kafka.server.service;

import com.unla.kafka.server.model.User;
import com.unla.kafka.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class UserService {

    @Autowired
    @Qualifier("userRepository")
    private UserRepository userRepository;

    public List<User> getAll(){
        return userRepository.findAll();
    }

    public User findById(Long id){
        return userRepository.findById(id);
    }

    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public void newUser(String username, String name, String password){
        userRepository.save(new User(username, name, password));
    }

    public boolean login(String username, String password){
        for(int i=0; i<getAll().size(); i++){
            if(username.equals(getAll().get(i).getUsername())){
                if(password.equals(getAll().get(i).getPassword())){
                    return true;
                }
            }
        }
        return false;
    }
}
