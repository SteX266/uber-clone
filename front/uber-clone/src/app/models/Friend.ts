export class Friend{

    email:string;
    id:string;
    lastMessage:string;
    name:string;
    surname:string;
    photoUrl:string;
    

    constructor(email:string,id:string,lastMessage:string,name:string,surname:string ,photoUrl:string){
        this.email = email;
        this.id =id;
        this.lastMessage = lastMessage;
        this.name = name;
        this.surname = surname;
        this.photoUrl = photoUrl;
    }


}