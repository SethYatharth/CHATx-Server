package com.chatx.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {


    private String content;
    private Integer id;
    private LocalDateTime timeStamp;
    private Boolean is_read;
    private UserDto user;
    private ChatDto chat;

}
