import { Component } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { UserCredentials } from 'src/app/models/UserCredentials';
import { AuthService } from 'src/app/services/auth/auth.service';
import { SnackBarService } from 'src/app/services/snackbar/snackbar.service';
import {
  GoogleLoginProvider,
  SocialAuthService,
  FacebookLoginProvider,
  SocialUser
} from '@abacritt/angularx-social-login';
import { Router } from '@angular/router';

@Component({
  selector: 'app-sign-in-modal',
  templateUrl: './sign-in-modal.component.html',
  styleUrls: ['./sign-in-modal.component.scss'],
})
export class SignInModalComponent {
  email: string = '';
  password: string = '';
  socialUser!: SocialUser;

  constructor(
    public dialogRef: MatDialogRef<SignInModalComponent>,
    private authService: AuthService,
    private snackBarService: SnackBarService,
    private socialAuthService: SocialAuthService,
    private router: Router,
  ) {}

  ngOnInit(){
    this.socialAuthService.authState.subscribe((user)=>{
      this.socialUser = user;
      console.log(this.socialUser);
      if(this.socialUser){
        this.authService.socialLogin(this.socialUser).subscribe({
          next: (value) => {
            if (value) {
              this.snackBarService.openSuccessSnackBar('Login successful!');
              this.authService.saveToken('Bearer ' + value.accessToken);
              this.authService.saveCurrentUserEmail(value.email);
              this.authService.saveCurrentUserId(value.id);
              this.authService.saveCurrentUserRole(value.userRole);
              console.log(this.authService.getToken());
              this.dialogRef.close();
              this.Redirect(value.userRole);
            } else {
              this.snackBarService.openFailureSnackBar(
                'Wrong credentials! Try again.'
              );
            }
          },
          error: (err) => {
            this.snackBarService.openFailureSnackBar(
              'Wrong credentials! Try again.'
            );
          },
        });
      }
      
    });
  }

  facebookLogin() {
    console.log("USAOOO");
    this.socialAuthService
      .signIn(FacebookLoginProvider.PROVIDER_ID);

  }



  login() {
    this.authService
      .sendLoginRequest(new UserCredentials(this.email, this.password))
      .subscribe({
        next: (value) => {
          if (value) {
            this.snackBarService.openSuccessSnackBar('Login successful!');
            this.authService.saveToken('Bearer ' + value.accessToken);
            this.authService.saveCurrentUserEmail(value.email);
            this.authService.saveCurrentUserId(value.id);
            this.authService.saveCurrentUserRole(value.userRole);
            console.log(this.authService.getToken());
            this.dialogRef.close();
            this.Redirect(value.userRole);
          } else {
            this.snackBarService.openFailureSnackBar(
              'Wrong credentials! Try again.'
            );
          }
        },
        error: (err) => {
          this.snackBarService.openFailureSnackBar(
            'Wrong credentials! Try again.'
          );
        },
      });
  }
  Redirect(userRole: string) {
    let route = '/' + userRole.toLowerCase();
    this.router.navigate([route]);
    console.log(route);
  }
}
