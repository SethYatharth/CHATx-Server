package com.chatx.controller.mapper;

import com.chatx.dto.UserDto;
import com.chatx.model.User;

import java.util.HashSet;
import java.util.Set;

public class UserDtoMapper {

	
	public static UserDto toUserDTO(User user) {
		
		UserDto userDto=new UserDto();
		
		userDto.setEmail(user.getEmail());
		userDto.setFull_name(user.getFull_name());
		userDto.setId(user.getId());
		userDto.setProfile_picture(user.getProfile_picture());
		
		return userDto;
		
	}
	
	public static HashSet<UserDto> toUserDtos(Set<User> set){
		HashSet<UserDto> userDtos=new HashSet<>();
		
		for(User user:set) {
			UserDto userDto=toUserDTO(user);
			userDtos.add(userDto);
		}
		
		return userDtos;
	}
}
