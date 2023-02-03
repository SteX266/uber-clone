import { Component } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import {
  SocialAuthService,
  FacebookLoginProvider,
} from '@abacritt/angularx-social-login';
import { AuthService } from 'src/app/services/auth/auth.service';
import { SnackBarService } from 'src/app/services/snackbar/snackbar.service';
import { UserRegistrationRequest } from 'src/app/models/UserRegistrationRequest';
@Component({
  selector: 'app-sign-up-modal',
  templateUrl: './sign-up-modal.component.html',
  styleUrls: ['./sign-up-modal.component.scss'],
})
export class SignUpModalComponent {
  email: string = '';
  password: string = '';
  repeatedPassword: string = '';
  name: string = '';
  surname: string = '';
  city: string = '';
  phoneNumber: string = '';
  userType: string = 'client';

  private readonly registrationUrl: string;

  constructor(
    private authService: AuthService,
    private snackBarService: SnackBarService,
    private socialAuthService: SocialAuthService,
    public dialogRef: MatDialogRef<SignUpModalComponent>
  ) {
    this.registrationUrl = 'http://localhost:8080/auth/usersignup';
  }

  facebookLogin() {
    this.socialAuthService
      .signIn(FacebookLoginProvider.PROVIDER_ID)
      .then((res) => {
        this.authService.socialLogin(res).subscribe({
          next: (value) => {
            if (value) {
              this.snackBarService.openSuccessSnackBar('Login successful!');
              console.log(value);
              this.authService.saveToken('Bearer ' + value.accessToken);
              console.log(this.authService.getToken());
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

  validateEmail() {
    return true;
  }

  validateInputData() {
    if (this.password !== this.repeatedPassword) {
      this.snackBarService.openFailureSnackBar('Passwords do not match!');
      return false;
    }

    if (!this.validateEmail()) {
      this.snackBarService.openFailureSnackBar('Email is not valid!');
      return false;
    }

    if (this.password.length < 6) {
      this.snackBarService.openFailureSnackBar(
        'Password must be at least 5 characters long'
      );
      return false;
    }
    if (this.name === '') {
      this.snackBarService.openFailureSnackBar('Name field is required!');

      return false;
    }
    if (this.surname === '') {
      this.snackBarService.openFailureSnackBar('Surname field is required!');

      return false;
    }
    if (this.city === '') {
      this.snackBarService.openFailureSnackBar('City field is required!');
      return false;
    }
    if (this.phoneNumber === '') {
      this.snackBarService.openFailureSnackBar(
        'Phone number field is required!'
      );
      return false;
    }
    return true;
  }

  sendRegistrationRequest() {
    let userRequest = new UserRegistrationRequest(
      this.email,
      this.name,
      this.surname,
      this.city,
      this.password,
      this.phoneNumber,
      this.userType
    );
    this.authService.sendRegistrationRequest(userRequest).subscribe({
      next: (value) => {
        if (value) {
          this.snackBarService.openSuccessSnackBar(
            'Registration successful! Check your mailbox to activate account.'
          );
        } else {
          this.snackBarService.openFailureSnackBar('Error! Try again.');
        }
      },
      error: (err) => {
        this.snackBarService.openFailureSnackBar('Error! Try again.');
      },
    });
  }

  userSignUp() {
    if (this.validateInputData()) {
      this.sendRegistrationRequest();
    }
  }
}
