
export class UserRegistrationRequest{
    email: string;
    name: string;
    surname:string;
    city:string;
    password: string;
    phoneNumber:string;
    userType:string;
    carModel:string;
    numberOfSeats:number;
    petFriendly:boolean;
    childrenFriendly:boolean;
    vehicleType:string;


    constructor(email:string, name:string, surname:string, city:string, password:string, phoneNumber:string, userType:string, carModel?:string, numberOfSeats?:number,petFriendly?:boolean, childrenFriendly?:boolean, vehicleType?:string){

        this.email = email;
        this.name = name;
        this.surname = surname;
        this.city = city;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.userType = userType;
        this.carModel = carModel || "";
        this.numberOfSeats = numberOfSeats || 0;
        this.petFriendly = petFriendly || false;
        this.childrenFriendly = childrenFriendly || false;
        this.vehicleType = vehicleType || "STANDARD";
    }


}