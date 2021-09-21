package com.unla.kafka.server.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.unla.kafka.server.model.User;
import com.unla.kafka.server.repository.UserRepository;

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
    
    public List<User> getLikedsUsers(Long postId){
    	List<User> users = userRepository.getLikedsUsers(postId);
    	
    	List<User> usersListResponse = new ArrayList<>();
    	users.forEach(
    			persistUser -> {
    				var user = new User();
    				user.setId(persistUser.getId());
    				user.setUsername(persistUser.getUsername());
    				usersListResponse.add(user);
    			}    			
    		);
    	
    	return usersListResponse;
    }

    public boolean newUser(String username, String name, String password) {
        for(int i=0; i<getAll().size(); i++){
            if(username.equals(getAll().get(i).getUsername())){
                return false;
            }
        }
        userRepository.save(new User(username, name, password));
        return true;
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
    
    public List<User> getFollowingUsers(Long userId){
    	return userRepository.getFollowingUsers(userId);
    }
}
