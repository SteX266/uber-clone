import { Component } from '@angular/core';
import {MatDialogRef} from '@angular/material/dialog';
import { UserRegistrationRequest } from 'src/app/models/UserRegistrationRequest';
import { AuthService } from 'src/app/services/auth/auth.service';
import { SnackBarService } from 'src/app/services/snackbar/snackbar.service';


@Component({
  selector: 'app-sign-up-driver-modal',
  templateUrl: './sign-up-driver-modal.component.html',
  styleUrls: ['./sign-up-driver-modal.component.scss']
})
export class SignUpDriverModalComponent {

  carModel:string = '';
  numberOfSeats:number=0;
  petFriendly:boolean=false;
  childrenFriendly:boolean=false;
  vehicleType:string = '';

  userType:string = "driver";


  email: string = '';
  password: string = '';
  repeatedPassword: string = '';
  name: string = '';
  surname: string = '';
  city: string = '';
  phoneNumber: string = '';

  constructor(public dialogRef: MatDialogRef<SignUpDriverModalComponent>, private authService:AuthService, private snackBarService:SnackBarService){}

  driverSignUp(){
    this.sendRegistrationRequest();
  }

  sendRegistrationRequest(){
    
    let userRequest = new UserRegistrationRequest(this.email, this.name, this.surname, this.city, this.password, this.phoneNumber, this.userType, this.carModel, this.numberOfSeats, this.petFriendly, this.childrenFriendly,this.vehicleType);
    this.authService.sendRegistrationRequest(userRequest).subscribe({next:(value) => {
      if (value) {
        this.snackBarService.openSuccessSnackBar('Registration of driver successful!');

      } else {
        this.snackBarService.openFailureSnackBar('Error! Try again.');
      }
    },error:(err)=>{
      this.snackBarService.openFailureSnackBar('Error! Try again.');
    }})

}
}
