import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-sign-in-modal',
  templateUrl: './sign-in-modal.component.html',
  styleUrls: ['./sign-in-modal.component.scss'],
})
export class SignInModalComponent {
  email: string = '';
  password: string = '';

  private readonly loginUrl: string;
  constructor(
    public dialogRef: MatDialogRef<SignInModalComponent>,
    public snackBar: MatSnackBar,
    private http: HttpClient
  ) {
    this.loginUrl = 'http://localhost:8080/auth/login';
  }

  openFailureSnackBar(text: string) {
    this.snackBar.open(text, 'Dismiss', {
      duration: 3000,
      panelClass: ['red-snackbar', 'login-snackbar'],
    });
  }

  openSuccessSnackBar(text: string) {
    this.snackBar.open(text, 'Dismiss', {
      duration: 3000,
      panelClass: ['green-snackbar', 'login-snackbar'],
    });
  }
  getHttpOptions() {
    return {
      headers: new HttpHeaders({
        'Access-Control-Allow-Origin': '*',
        'Content-Type': 'application/json',
      }),
    };
  }
  sendLoginRequest(): Observable<Object> {
    let body = {
      email: this.email,
      password: this.password,
    };
    return this.http.post<any>(this.loginUrl, body, this.getHttpOptions());
  }
  login() {
    this.sendLoginRequest().subscribe(
      (data) => {
        console.log(data);
        if (data) {
          this.openSuccessSnackBar('Login successful!');
        } else {
          this.openFailureSnackBar('Wrong credentials! Try again.');
        }
      },
      (error) => {
        this.openFailureSnackBar('Wrong credentials! Try again.');
      }
    );
  }
}
