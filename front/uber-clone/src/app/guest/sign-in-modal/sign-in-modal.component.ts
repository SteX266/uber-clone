import { Component } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { UserCredentials } from 'src/app/models/UserCredentials';
import { AuthService } from 'src/app/services/auth/auth.service';
import { SnackBarService } from 'src/app/services/snackbar/snackbar.service';
import {
  GoogleLoginProvider,
  SocialAuthService,
  FacebookLoginProvider,
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

  constructor(
    public dialogRef: MatDialogRef<SignInModalComponent>,
    private authService: AuthService,
    private snackBarService: SnackBarService,
    private socialAuthService: SocialAuthService,
    private router: Router
  ) {}

  facebookLogin() {
    this.socialAuthService
      .signIn(FacebookLoginProvider.PROVIDER_ID)
      .then((res) => {
        this.authService.socialLogin(res).subscribe({
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
      });
  }

  googleLogin() {
    this.socialAuthService.signIn(GoogleLoginProvider.PROVIDER_ID).then(() => {
      console.log('GUGUL');
    });
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
