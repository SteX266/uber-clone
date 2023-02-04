import { SocialUser } from "@abacritt/angularx-social-login";

export class SocialLoginInfo{

    email:string;
    authToken:string;
    firstName:string;
    id:string;
    idToken:string;
    lastName:string;
    name:string;
    photoUrl:string;
    provider:string;

    constructor(value:SocialUser){
        this.email = value.email;
        this.authToken = value.authToken;
        this.firstName = value.firstName;
        this.id = value.id;
        this.idToken = value.idToken;
        this.lastName = value.lastName;
        this.name = value.name;
        this.photoUrl = value.photoUrl;
        this.provider = value.provider;
    }




}