import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Friend } from 'src/app/models/Friend';
import { AuthService } from '../auth/auth.service';
import { environment } from 'src/environments/environment';
import { Message } from 'src/app/models/Message';

@Injectable({
  providedIn: 'root'
})
export class ChatService {



  
  sendMessage(message: Message) {
    return this.http.post<any>(environment.apiEndpoint + "messages/saveSentMessage",message,this.authService.getHttpOptionsWithToken());
  }


  constructor(private http:HttpClient, private authService:AuthService) { }

  getChatList(userId:string):Friend[]{
    let friends:Friend[] = [];

    this.sendGetChatListRequest(userId).subscribe({next:(val)=>{
      val.forEach((element:any) => {
        let friend:Friend = new Friend(element.email, element.id, element.lastMessage, element.name, element.surname, element.photoUrl);
        friends.push(friend);
      });

    },error:(err)=>{


    }});

    return friends;
  }

  sendGetChatListRequest(userId:string){
    return this.http.get<any>(environment.apiEndpoint + "messages/getChats/" + userId, this.authService.getHttpOptionsWithToken());

  }
  getMessageList(userId:string,friendId:string){
    let messages:Message[] = [];

    this.sendMessageListRequest(userId, friendId).subscribe({next:(val)=>{
      console.log(val);
      val.forEach((element:any)=>{
        let message:Message = new Message(element.senderId, element.senderEmail,element.text,"");
        messages.push(message);
      })
    }, error:(err)=>{

    }});
    return messages;
  }

  sendMessageListRequest(userId:string, friendId:string){
    return this.http.get<any>(environment.apiEndpoint + "messages/getMessages/" + userId +"/"+friendId  ,this.authService.getHttpOptionsWithToken());

  }
}
