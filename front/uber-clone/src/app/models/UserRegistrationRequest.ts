
export class UserRegistrationRequest{
    email: string;
    name: string;
    surname:string;
    city:string;
    password: string;
    phoneNumber:string;
    userType:string;

    constructor(email:string, name:string, surname:string, city:string, password:string, phoneNumber:string, userType:string){

        this.email = email;
        this.name = name;
        this.surname = surname;
        this.city = city;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.userType = userType;
    }


}