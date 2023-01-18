import { Component } from '@angular/core';
import {MatDialogRef} from '@angular/material/dialog';
import{HttpHeaders, HttpClient} from '@angular/common/http';
import { MatSnackBar } from '@angular/material/snack-bar';

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
  
  constructor(public dialogRef: MatDialogRef<SignUpModalComponent>,public snackBar:MatSnackBar,private http:HttpClient){
    this.registrationUrl = "http://localhost:8080/auth/usersignup";
  }

  openSuccessSnackBar(text: string){
    this.snackBar.open(text, "Dismiss", {
      duration: 3000,
      panelClass: ['green-snackbar', 'login-snackbar'],
     });
    }
    openFailureSnackBar(text: string){
    this.snackBar.open(text, "Dismiss", {
      duration: 3000,
      panelClass: ['red-snackbar','login-snackbar'],
      });
     }

  validateEmail(){
    return true;
  }

  validateInputData(){
    if (this.password !== this.repeatedPassword){
      this.openFailureSnackBar("Passwords do not match!");
      return false;
    }


    if (!this.validateEmail()){
      this.openFailureSnackBar("Email is not valid!");
      return false;
    }


    if (this.password.length < 6){
      this.openFailureSnackBar("Password must be at least 5 characters long");
      return false;      
      
    }
    if (this.name === ""){
      this.openFailureSnackBar("Name field is required!");

      return false;
      
    }
    if (this.surname === ""){
      this.openFailureSnackBar("Surname field is required!");
      
      return false;
      
    }
    if(this.city === ""){
      this.openFailureSnackBar("City field is required!");
      return false;

    }
    if(this.phoneNumber === ""){
      this.openFailureSnackBar("Phone number field is required!");
      return false


    }
    return true;

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
    this.openSuccessSnackBar("Registration successful!");

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

    if (this.validateInputData()){

      this.sendRegistrationRequest();
      
    }



  }
}
