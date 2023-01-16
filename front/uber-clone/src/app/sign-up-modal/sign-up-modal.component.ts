import { Component } from '@angular/core';
import {MatDialog, MatDialogRef} from '@angular/material/dialog';
import{HttpHeaders, HttpClient} from '@angular/common/http';
@Component({
  selector: 'app-sign-up-modal',
  templateUrl: './sign-up-modal.component.html',
  styleUrls: ['./sign-up-modal.component.scss']
})
export class SignUpModalComponent {

  email: string = '';
  password: string = '';
  repeatedPassword: string = '';
  name: string = '';
  surname: string = '';
  city: string = '';
  phoneNumber: string = '';

  private readonly registrationUrl: string;
  
  constructor(public dialogRef: MatDialogRef<SignUpModalComponent>,private http:HttpClient){
    this.registrationUrl = "http://localhost:8080/auth/usersignup";
  }

  validateEmail(){
    return true;
  }

  validateInputData(){
    if (this.password !== this.repeatedPassword){
      console.log("Passwords do not match!");
    }


    if (!this.validateEmail()){
      console.log("Email is not valid!");
    }


    if (this.password.length < 6){
      console.log("Password must be at least 5 characters long");
      
      
    }
    if (this.name === ""){
      console.log("Name must not be empty!");
      
    }
    if (this.surname === ""){
      console.log("Surname must not be empty!");
      
    }
    if(this.city === ""){
      console.log("City must not be empty!");

    }
    if(this.phoneNumber === ""){
      console.log("Phone number must not be empty!");


    }

  }

  sendRegistrationRequest(){
    let body = {
      "email":this.email,
      "password":this.password,
      "name":this.name,
      "surname":this.surname,
      "city":this.city,
      "phoneNumber":this.phoneNumber,
      "userType":"client"


    }

    console.log(body);

    this.http.post<any>(this.registrationUrl,body,this.getHttpOptions()).subscribe();
    console.log(this.registrationUrl);
    if (this.http.post<any>(this.registrationUrl,body,this.getHttpOptions())){

      console.log("Uspesna registracija!");

    }
    else{
      console.log("Neuspesna registracija!");
    }

  }

  getHttpOptions(){
    return {
      headers: new HttpHeaders({
        'Access-Control-Allow-Origin': '*',
        'Content-Type':  'application/json',
      })
    };

  }


  userSignUp(){
    this.validateInputData();

    this.sendRegistrationRequest();


  }
}
