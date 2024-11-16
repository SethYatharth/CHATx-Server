package com.chatx.service;

import com.chatx.exception.UserException;
import com.chatx.model.User;
import com.chatx.request.UpdateUserRequest;

import java.util.List;

public interface UserService {
	
	public User findUserProfile(String jwt);
	
	public User updateUser(Integer userId, UpdateUserRequest req) throws UserException;
	
	public User findUserById(Integer userId) throws UserException;
	
	public List<User> searchUser(String query);
}
