export class Message{
    senderId:string;
    senderEmail:string;
    text:string;
    date:string;
    receiverEmail:string;
    receiverId:string;
    constructor(senderId:string,senderEmail:string,text:string, date:string, receiverEmail?:string, receiverId?:string){
        this.text = text;
        this.senderId = senderId;
        this.senderEmail = senderEmail;
        this.date = date;
        this.receiverEmail = receiverEmail || "";
        this.receiverId = receiverId || "";

    }
}