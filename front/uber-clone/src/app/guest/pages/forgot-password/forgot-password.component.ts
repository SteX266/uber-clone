import { Component } from '@angular/core';
import { AuthService } from 'src/app/services/auth/auth.service';
import { SnackBarService } from 'src/app/services/snackbar/snackbar.service';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.scss']
})
export class ForgotPasswordComponent {

  email:string = "";
  constructor(private authService:AuthService, private snackBarService:SnackBarService){}

  sendForgotPasswordRequest(){
    this.authService.sendForgotPasswordRequest(this.email).subscribe({next:(value)=>{
        if (value){
          this.snackBarService.openSuccessSnackBar("Success! We sent reset password link to your email.")
        }
        else{

          this.snackBarService.openFailureSnackBar("Error!");
        }
    },error:(err) =>{
      console.log(err);
      this.snackBarService.openFailureSnackBar("Error! User doesn't exist!" + err);
    }});
  }
}
