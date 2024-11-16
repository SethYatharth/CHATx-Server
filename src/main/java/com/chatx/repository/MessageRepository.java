package com.chatx.repository;

import java.util.List;

import com.chatx.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;



public interface MessageRepository extends JpaRepository<Message, Integer>{
	
	@Query("select m from Message m join m.chat c where c.id=:chatId")
	public List<Message> findMessageByChatId(@Param("chatId") Integer chatId);

}
