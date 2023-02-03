import { Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';

@Injectable({
  providedIn: 'root'
})
export class SnackBarService {

  constructor(public snackBar: MatSnackBar) { }

  openFailureSnackBar(text: string) {
    this.snackBar.open(text, 'Dismiss', {
      duration: 300000,
      panelClass: ['red-snackbar', 'login-snackbar'],
    });
  }

  openSuccessSnackBar(text: string) {
    this.snackBar.open(text, 'Dismiss', {
      duration: 300000,
      panelClass: ['green-snackbar', 'login-snackbar'],
    });
  }
}
