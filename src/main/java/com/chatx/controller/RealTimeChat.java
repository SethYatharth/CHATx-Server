package com.chatx.controller;

import com.chatx.exception.ChatException;
import com.chatx.exception.UserException;
import com.chatx.model.Chat;
import com.chatx.model.Message;
import com.chatx.model.User;
import com.chatx.request.SendMessageRequest;
import com.chatx.service.ChatService;
import com.chatx.service.MessageService;
import com.chatx.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import java.util.Iterator;

@RestController
public class RealTimeChat {
	
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private UserService userService;
    
    @Autowired
    private MessageService messageService;
    
    @Autowired
    private ChatService chatService;
	
    @MessageMapping("/message")
    @SendTo("/group/public")
    public Message receiveMessage(@Payload Message message){
    	
    	System.out.println("receive message in public ---------- ");
    	
//    	simpMessagingTemplate.convertAndSend("/group/" +req.getChatId().toString(), req);
    	
    	simpMessagingTemplate.convertAndSend("/group/"+message.getChat().getId().toString(), message);
    	
        return message;
    }
    
	@MessageMapping("/chat/{groupId}")
	public Message sendToUser(@Payload SendMessageRequest req, @Header("Authorization") String jwt, @DestinationVariable String groupId) throws UserException, ChatException {
		System.out.println("recived private message - - - - - "+req);
		User user=userService.findUserProfile(jwt);
		System.out.println("userr private message - - - - - - "+user);
		req.setUserId(user.getId());
		
		Chat chat=chatService.findChatById(req.getChatId());
		
		Message createdMessage = messageService.sendMessage(req);
		
		User reciverUser=reciver(chat, user);
		
		simpMessagingTemplate.convertAndSendToUser(groupId, "/private", createdMessage);
		
		return createdMessage;
	}
	
	public User reciver(Chat chat,User reqUser) {
		Iterator<User> iterator = chat.getUsers().iterator();

		User user1 = iterator.next(); // get the first user
		User user2 = iterator.next();
		
		if(user1.getId().equals(reqUser.getId())){
			return user2;
		}
		return user1;
	}
	
	

}
