import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { UserCredentials } from 'src/app/models/UserCredentials';
import { AuthService } from 'src/app/services/auth/auth.service';
import { SnackBarService } from 'src/app/services/snackbar/snackbar.service';

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
    public snackBar: MatSnackBar,
    private authService: AuthService,
    private snackBarService:SnackBarService
  ) { }


  login() {
    this.authService.sendLoginRequest(new UserCredentials(this.email, this.password)).subscribe({next:(value) => {
      if (value) {
        this.snackBarService.openSuccessSnackBar('Login successful!');
        this.authService.saveToken("Bearer " + value.auth.token.accessToken);
      } else {
        this.snackBarService.openFailureSnackBar('Wrong credentials! Try again.');
      }
    },error:(err)=>{
      this.snackBarService.openFailureSnackBar('Wrong credentials! Try again.');
    }});

  }
}
