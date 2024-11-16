package com.chatx.controller.mapper;

import com.chatx.dto.ChatDto;
import com.chatx.dto.MessageDto;
import com.chatx.dto.UserDto;
import com.chatx.model.Message;

import java.util.ArrayList;
import java.util.List;

public class MessageDtoMapper {
	
	
	public static MessageDto toMessageDto(Message message) {
		
		ChatDto chatDto=ChatDtoMapper.toChatDto(message.getChat());
		UserDto userDto=UserDtoMapper.toUserDTO(message.getUser());
		
		MessageDto messageDto=new MessageDto();
		messageDto.setId(message.getId());
		messageDto.setChat(chatDto);
		messageDto.setContent(message.getContent());
		messageDto.setIs_read(message.getIs_read());
		messageDto.setTimeStamp(message.getTimeStamp());
		messageDto.setUser(userDto);
//		messageDto.set
		
		return messageDto;
	}
	
	public static List<MessageDto> toMessageDtos(List<Message> messages){
		
		List<MessageDto> messageDtos=new ArrayList<>();
		
		for(Message message : messages) {
			MessageDto messageDto=toMessageDto(message);
			messageDtos.add(messageDto);
		}
		
		return messageDtos;
	}

}
