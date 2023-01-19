
export class UserRegistrationRequest{
    username: string;
    name: string;
    surname:string;
    city:string;
    password: string;
    phoneNumber:string;

    constructor(username:string, name:string, surname:string, city:string, password:string, phoneNumber:string){

        this.username = username;
        this.name = name;
        this.surname = surname;
        this.city = city;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }


}