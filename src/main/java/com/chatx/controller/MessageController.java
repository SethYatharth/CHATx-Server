package com.chatx.controller;


import com.chatx.controller.mapper.MessageDtoMapper;
import com.chatx.dto.MessageDto;
import com.chatx.exception.ChatException;
import com.chatx.exception.MessageException;
import com.chatx.exception.UserException;
import com.chatx.model.Message;
import com.chatx.model.User;
import com.chatx.request.SendMessageRequest;
import com.chatx.response.ApiResponse;
import com.chatx.service.MessageService;
import com.chatx.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<MessageDto> sendMessageHandler(@RequestHeader("Authorization") String jwt, @RequestBody SendMessageRequest req) throws UserException, ChatException {

        User reqUser = userService.findUserProfile(jwt);

        req.setUserId(reqUser.getId());

        Message message = messageService.sendMessage(req);

        MessageDto messageDto = MessageDtoMapper.toMessageDto(message);

        return new ResponseEntity<MessageDto>(messageDto, HttpStatus.OK);
    }

    @GetMapping("/chat/{chatId}")
    public ResponseEntity<List<MessageDto>> getChatsMessageHandler(@PathVariable Integer chatId) throws ChatException {

        List<Message> messages = messageService.getChatsMessages(chatId);

        List<MessageDto> messageDtos = MessageDtoMapper.toMessageDtos(messages);

        return new ResponseEntity<List<MessageDto>>(messageDtos, HttpStatus.ACCEPTED);

    }


    @DeleteMapping("/{messageId}")
    public ResponseEntity<ApiResponse> deleteMessageHandler(@PathVariable Integer messageId) throws MessageException {

        messageService.deleteMessage(messageId);

        ApiResponse res = new ApiResponse("message deleted successfully", true);

        return new ResponseEntity<ApiResponse>(res, HttpStatus.ACCEPTED);
    }


}
