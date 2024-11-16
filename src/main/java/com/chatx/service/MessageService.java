package com.chatx.service;


import com.chatx.exception.ChatException;
import com.chatx.exception.MessageException;
import com.chatx.exception.UserException;
import com.chatx.model.Message;
import com.chatx.request.SendMessageRequest;

import java.util.List;

public interface MessageService  {
	
	public Message sendMessage(SendMessageRequest req) throws UserException, ChatException;
	
	public List<Message> getChatsMessages(Integer chatId) throws ChatException;
	
	public Message findMessageById(Integer messageId) throws MessageException;
	
	public String deleteMessage(Integer messageId) throws MessageException;

}
