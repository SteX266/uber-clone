package com.backend.uberclone.controller;


import com.backend.uberclone.dto.ChatDTO;
import com.backend.uberclone.dto.MessageDTO;
import com.backend.uberclone.dto.MessageRequestDTO;
import com.backend.uberclone.dto.SuccessResponseDTO;
import com.backend.uberclone.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value = "/messages", produces = MediaType.APPLICATION_JSON_VALUE)
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("send/message")
    public void sendMessage(MessageDTO message){
        System.out.println("USAOOOO U SLANJEEE");
        this.simpMessagingTemplate.convertAndSend("/chat", message);
    }

    @PostMapping("saveSentMessage")
    public ResponseEntity<SuccessResponseDTO> saveSentMessage(@RequestBody MessageDTO message){
        this.messageService.sendMessage(message);

        return new ResponseEntity<>(new SuccessResponseDTO(), HttpStatus.OK);
    }

    @GetMapping(value="/getChats/{id}")
    public ResponseEntity<List<ChatDTO>> getAllChats(@PathVariable("id") String id){
        int userId;
        List<ChatDTO> chats= new ArrayList<>();
        try{
            userId = Integer.parseInt(id);
        }
        catch(Exception e){
            return new ResponseEntity<>(chats, HttpStatus.NOT_FOUND);
        }
        chats = this.messageService.getUserChats(userId);


        return new ResponseEntity<>(chats, HttpStatus.OK);

    }
    @GetMapping(value="/getMessages/{userId}/{friendId}")
    public ResponseEntity<List<MessageDTO>> getMessages(@PathVariable("userId") String userId, @PathVariable("friendId") String friendId){
        MessageRequestDTO messageRequestDTO;
        List<MessageDTO> messages = new ArrayList<>();
        try{
            messageRequestDTO = new MessageRequestDTO(Integer.parseInt(userId), Integer.parseInt(friendId));

        }
        catch(Exception e){
            return new ResponseEntity<>(messages, HttpStatus.BAD_REQUEST);
        }

        messages = messageService.getMessages(messageRequestDTO);;
        return new ResponseEntity<>(messages, HttpStatus.OK);

    }


}
