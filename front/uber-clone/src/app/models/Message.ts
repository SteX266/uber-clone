export class Message{
    senderId:string;
    senderEmail:string;
    text:string;
    date:string;
    constructor(senderId:string,senderEmail:string,text:string, date:string){
        this.text = text;
        this.senderId = senderId;
        this.senderEmail = senderEmail;
        this.date = date;
    }
}