import { Component } from '@angular/core';
import { AuthService } from 'src/app/services/auth/auth.service';
import { SnackBarService } from 'src/app/services/snackbar/snackbar.service';
import { Router,NavigationEnd  } from '@angular/router';

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.scss']
})
export class ResetPasswordComponent {

  newPassword:string = "";
  token:string = "";
  constructor(private authService:AuthService, private snackBarService:SnackBarService, private router:Router){}


  sendResetPasswordRequest(){


    
    console.log(this.router.url);
    this.token = this.router.url.split("token=")[1];
    console.log(this.token);

    this.authService.sendResetPasswordRequest(this.token,this.newPassword).subscribe({next:(value)=>{
        if (value){
          this.snackBarService.openSuccessSnackBar("Success! You can now log in with your new password.");
        }
        else{
          this.snackBarService.openFailureSnackBar("Error!");
        }


    },error:(err)=>{
      this.snackBarService.openFailureSnackBar("Error!");

    }});

  }
}
