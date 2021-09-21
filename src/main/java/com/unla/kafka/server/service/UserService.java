package com.unla.kafka.server.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.unla.kafka.server.model.Like;
import com.unla.kafka.server.model.Post;
import com.unla.kafka.server.model.User;
import com.unla.kafka.server.repository.LikeRepository;
import com.unla.kafka.server.repository.PostRepository;
import com.unla.kafka.server.repository.UserRepository;

@Service("userService")
public class UserService {

    @Autowired
    @Qualifier("userRepository")
    private UserRepository userRepository;

    @Autowired
    private LikeRepository likeRepository;
    
    @Autowired
    private PostRepository postRepository;
    
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
    
    public List<Like> getLikes(Long userId){
    	List<Like> likes = likeRepository.getLikes(userId);
    	
    	List<Like> likesResponse = new ArrayList<>();
    	likes.forEach(
    			like -> {
    				var user = new User();
    				user.setId(userRepository.findById(like.getUserLike().getId()).getId());
    				user.setUsername(userRepository.findById(like.getUserLike().getId()).getUsername());    
    				var post = new Post();
    				post.setId(postRepository.findById(like.getPost().getId()).get().getId());
    				post.setImage(postRepository.findById(like.getPost().getId()).get().getImage());
    				post.setText(postRepository.findById(like.getPost().getId()).get().getText());
    				post.setTitle(postRepository.findById(like.getPost().getId()).get().getTitle());
    				var likeResponse = Like.builder()
    						.id(like.getId())
    						.userLike(user)
    						.post(post)
    						.build();    				
    				likesResponse.add(likeResponse);
    			});
    	
    	return likesResponse;
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
