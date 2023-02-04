package com.backend.uberclone.service;

import com.backend.uberclone.dto.ChatDTO;
import com.backend.uberclone.dto.MessageDTO;
import com.backend.uberclone.dto.MessageRequestDTO;
import com.backend.uberclone.model.Message;
import com.backend.uberclone.model.User;
import com.backend.uberclone.repository.MessageRepository;
import com.backend.uberclone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class MessageService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageRepository messageRepository;
    public List<ChatDTO> getUserChats(Integer userId){
        List<ChatDTO> chats = new ArrayList<>();

        User user = userRepository.findOneById(userId);
        if (user.getRole().equals("ADMIN")){
            chats = generateUniqueChats(user.getRecievedMessages(), user.getSentMessages());
        }
        else{
            ChatDTO chat = new ChatDTO();
            chat.setId(1);
            chat.setName("Admin");
            chat.setSurname("");
            chat.setEmail("bubibubisa@gmail.com");
            chat.setPhotoUrl("");
            chat.setLastMessage(getLastMessage("bubibubisa@gmail.com", user.getRecievedMessages(), user.getSentMessages()));
            chats.add(chat);
        }

        return chats;
    }

    private List<ChatDTO> generateUniqueChats(List<Message> receivedMessages, List<Message> sentMessages) {
        List<ChatDTO> chats = new ArrayList<>();
        List<String> usernames = new ArrayList<>();
        for (Message m:receivedMessages){
            String sender = m.getSender().getUsername();
            boolean exists = false;
            for (String username:usernames){
                if (username.equals(sender)){
                    exists = true;
                }
            }
            if (!exists){
                usernames.add(sender);
            }
        }
        for (Message m:sentMessages){
            String reciever = m.getRecipient().getUsername();
            boolean exists = false;
            for (String username:usernames){
                if (username.equals(reciever)){
                    exists = true;
                }
            }
            if (!exists){
                usernames.add(reciever);
            }
        }

        for (String username:usernames){
            User u = userRepository.findOneByEmail(username);
            String lastMessage = getLastMessage(username, receivedMessages, sentMessages);
            ChatDTO chat = new ChatDTO(username, u.getId(), lastMessage, u.getName(), u.getSurname(), u.getProfilePicture());
            chats.add(chat);
        }

        return chats;

    }

    private String getLastMessage(String username, List<Message> receivedMessages, List<Message> sentMessages) {
        String lastMessage = "";
        LocalDateTime lastDate = LocalDateTime.now().minusYears(10);
        for (Message m:receivedMessages){
            if ((username.equals(m.getSender().getUsername()))&& m.getDate().compareTo(lastDate) > 0 ){
            lastDate = m.getDate();
            lastMessage = m.getText();
            }
        }
        for (Message m:sentMessages){
            if ((username.equals(m.getRecipient().getUsername()))&& m.getDate().compareTo(lastDate) > 0 ){
                lastDate = m.getDate();
                lastMessage = m.getText();
            }
        }
        return lastMessage;

    }

    public List<MessageDTO> getMessages(MessageRequestDTO messageRequestDTO) {
        List<MessageDTO> messages = new ArrayList<>();

        User currentUser = userRepository.findOneById(messageRequestDTO.getCurrentUserId());

        for (Message m:currentUser.getRecievedMessages()){
            if (m.getSender().getId() == messageRequestDTO.getFriendId()){
                MessageDTO message= new MessageDTO(m.getSender().getId(),m.getSender().getEmail(), m.getText(),m.getDate());
                messages.add(message);
            }
        }
        for (Message m:currentUser.getSentMessages()){
            if(m.getRecipient().getId() == messageRequestDTO.getFriendId()){
                MessageDTO message = new MessageDTO(m.getSender().getId(),m.getSender().getEmail(), m.getText(), m.getDate());
                messages.add(message);
            }
        }
        Collections.sort(messages);
        return messages;
    }

    public void sendMessage(MessageDTO message) {
        User sender = userRepository.findOneByEmail(message.getSenderEmail());
        User receiver = userRepository.findOneByEmail(message.getReceiverEmail());
        Message m = new Message();
        m.setDate(LocalDateTime.now());
        m.setRead(false);
        m.setSender(sender);
        m.setRecipient(receiver);
        m.setText(message.getText());
        messageRepository.save(m);


    }
}
