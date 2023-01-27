import { Component, OnInit,ViewChild,ElementRef } from '@angular/core';
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

  webSocket:WebSocket | undefined;
  @ViewChild('scroll') scrollContainer: ElementRef | undefined;

  friendList:Friend[] = [];
  currentUserId:string;
  currentUserEmail:string;
  messages:Message[] = [];
  newMessage:string = "";
  private stompClient:any;
  currentFriend:Friend;
  currentFriendName:string = "";

  messageDisabled:boolean = true;



  ngOnInit(){
    this.getFriendList();
    this.initializeWebSocketConnection();
    this.scrollToBottom();

  }

  ngAfterViewChecked() {
    this.scrollToBottom();
  }


  constructor(private authService:AuthService, private snackBarService:SnackBarService, private chatService:ChatService){
    this.currentUserId = authService.getCurrentUserId();
    this.currentUserEmail = authService.getCurrentUserEmail();
    this.currentFriend = new Friend("","","","","","");
  }


  initializeWebSocketConnection() {
    let ws = new SockJS('http://localhost:8080/socket');
    this.stompClient = Stomp.over(ws);
    this.stompClient.debug = null;
    let currentUserEmail = this.authService.getCurrentUserEmail()
    let that = this;
    
    this.stompClient.connect({}, function () {
      that.stompClient.subscribe('/chat', (message: { body: string }) => {
        if (message.body) {
          let msg: Message = JSON.parse(message.body);
          if (msg.senderEmail === currentUserEmail || msg.receiverEmail === currentUserEmail) {
            if(msg.senderEmail === that.currentFriend.email || msg.receiverEmail == that.currentFriend.email){
              that.messages.push(msg);

            }
            let friendExist = false;
            that.friendList.forEach(friend => {
              if(friend.email === msg.senderEmail || friend.email === msg.receiverEmail){
                friendExist = true;
                friend.lastMessage = msg.text;
              }
            });
            
            if(!friendExist){

              that.getFriendList();
                
            }
            
          }
        }
      });
    });
  }


  sendMessage(){
    let message = new Message(this.currentUserId, this.currentUserEmail,this.newMessage,"",this.currentFriend.email,this.currentFriend.id);
    this.stompClient.send("/send/message", {}, JSON.stringify(message));
    this.chatService.sendMessage(message).subscribe({next:(val)=>{
      console.log("Success");
    },error:(err)=>{
      this.snackBarService.openFailureSnackBar("Error!");
    }});
    this.scrollToBottom();
    this.newMessage = "";
  }
  openChat(friend:Friend){
    this.messageDisabled = false;
    this.currentFriend = friend;
    this.currentFriendName = friend.name + " " + friend.surname;
    this.messages = this.chatService.getMessageList(this.currentUserId, friend.id);
    this.messages.forEach(element => {
      console.log(element.text);
    });
    this.scrollToBottom();

  }

  getFriendList(){
    this.friendList = this.chatService.getChatList(this.currentUserId);
  }

  scrollToBottom(): void {
    try {
      this.scrollContainer!.nativeElement.scrollTop =
        this.scrollContainer!.nativeElement.scrollHeight;
    } catch (err) {}
  }

} 
