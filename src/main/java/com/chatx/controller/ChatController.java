package com.chatx.controller;


import com.chatx.controller.mapper.ChatDtoMapper;
import com.chatx.dto.ChatDto;
import com.chatx.exception.ChatException;
import com.chatx.exception.UserException;
import com.chatx.model.Chat;
import com.chatx.model.User;
import com.chatx.request.GroupChatRequest;
import com.chatx.request.RenameGroupRequest;
import com.chatx.request.SingleChatRequest;
import com.chatx.service.ChatService;
import com.chatx.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chats")
public class ChatController {

//	private ChatRepository chatRepo;
	
	@Autowired
	private ChatService chatService;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/single")
	public ResponseEntity<ChatDto> creatChatHandler(@RequestBody SingleChatRequest singleChatRequest, @RequestHeader("Authorization")  String jwt) throws UserException {
		
		System.out.println("single chat --------");
		User reqUser=userService.findUserProfile(jwt);
		
		Chat chat=chatService.createChat(reqUser.getId(),singleChatRequest.getUserId(),false);
		ChatDto chatDto= ChatDtoMapper.toChatDto(chat);
		
		return new ResponseEntity<ChatDto>(chatDto,HttpStatus.OK);
	}
	
	@PostMapping("/group")
	public ResponseEntity<ChatDto> createGroupHandler(@RequestBody GroupChatRequest groupChatRequest, @RequestHeader("Authorization") String jwt) throws UserException{
		
		User reqUser=userService.findUserProfile(jwt);
		
		Chat chat=chatService.createGroup(groupChatRequest, reqUser.getId());
		ChatDto chatDto=ChatDtoMapper.toChatDto(chat);
		
		return new ResponseEntity<ChatDto>(chatDto,HttpStatus.OK);
		
	}
	
	@GetMapping("/{chatId}")
	public ResponseEntity<ChatDto> findChatByIdHandler(@PathVariable Integer chatId) throws ChatException {
		
		Chat chat =chatService.findChatById(chatId);
		
		ChatDto chatDto=ChatDtoMapper.toChatDto(chat);
		
		return new ResponseEntity<ChatDto>(chatDto,HttpStatus.OK);
		
	}


	
	@GetMapping("/user")
	public ResponseEntity<List<ChatDto>> findAllChatByUserIdHandler(@RequestHeader("Authorization")String jwt) throws UserException{
		
		User user=userService.findUserProfile(jwt);
		
		List<Chat> chats=chatService.findAllChatByUserId(user.getId());
		
		List<ChatDto> chatDtos=ChatDtoMapper.toChatDtos(chats);
		
		return new ResponseEntity<List<ChatDto>>(chatDtos,HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/{chatId}/add/{userId}")
	public ResponseEntity<ChatDto> addUserToGroupHandler(@PathVariable Integer chatId,@PathVariable Integer userId) throws UserException, ChatException{
		
		Chat chat=chatService.addUserToGroup(userId, chatId);
		
		ChatDto chatDto=ChatDtoMapper.toChatDto(chat);
		
		return new ResponseEntity<ChatDto>(chatDto,HttpStatus.OK);
	}
	
	@PutMapping("/{chatId}/rename")
	public ResponseEntity<ChatDto> renameGroupHandler(@PathVariable Integer chatId, @RequestBody RenameGroupRequest renameGoupRequest, @RequestHeader("Authorization") String jwt) throws ChatException, UserException{
		
		User reqUser=userService.findUserProfile(jwt);
		
		Chat chat =chatService.renameGroup(chatId, renameGoupRequest.getGroupName(), reqUser.getId());
		
		ChatDto chatDto=ChatDtoMapper.toChatDto(chat);
		
		return new ResponseEntity<ChatDto>(chatDto,HttpStatus.OK);
	}
	
	@PutMapping("/{chatId}/remove/{userId}")
	public ResponseEntity<ChatDto> removeFromGroupHandler(@RequestHeader("Authorization") String jwt, @PathVariable Integer chatId,@PathVariable Integer userId) throws UserException, ChatException{
		
		User reqUser=userService.findUserProfile(jwt);
		
		Chat chat=chatService.removeFromGroup(chatId, userId, reqUser.getId());
		
		ChatDto chatDto=ChatDtoMapper.toChatDto(chat);
		
		return new ResponseEntity<ChatDto>(chatDto,HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{chatId}/{userId}")
	public ResponseEntity<ChatDto> deleteChatHandler(@PathVariable Integer chatId, @PathVariable Integer userId) throws ChatException, UserException{
		
		Chat chat=chatService.deleteChat(chatId, userId);
		ChatDto chatDto=ChatDtoMapper.toChatDto(chat);
		
		return new ResponseEntity<ChatDto>(chatDto,HttpStatus.OK);
	}
}
