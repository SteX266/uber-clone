import { Component, OnInit } from '@angular/core';
import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import { Friend } from 'src/app/models/Friend';
import { AuthService } from 'src/app/services/auth/auth.service';
import { Message } from 'src/app/models/Message';
import { SnackBarService } from 'src/app/services/snackbar/snackbar.service';
import { ChatService } from 'src/app/services/chat/chat.service';

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.scss']
})
export class ChatComponent implements OnInit {
  friendList:Friend[] = [];
  currentUserId:string;
  messages:Message[] = [];
  newMessage:string = "";
  currentFriendName:string = "Petar";


  ngOnInit(){
    this.getFriendList();
  }

  constructor(private authService:AuthService, private snackBarService:SnackBarService, private chatService:ChatService){
    this.currentUserId = authService.getCurrentUserId();
  }

  sendMessage(){

    this.snackBarService.openSuccessSnackBar("Majmun");
    console.log(this.newMessage);
  }
  openChat(friend:Friend){
    this.currentFriendName = friend.name + " " + friend.surname;
    this.messages = this.chatService.getMessageList(this.currentUserId, friend.id);
    this.messages.forEach(element => {
      console.log(element.text);
    });

  }

  getFriendList(){
    this.friendList = this.chatService.getChatList(this.currentUserId);
  }

} 
