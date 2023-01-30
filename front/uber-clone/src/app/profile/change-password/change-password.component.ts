import { Component } from '@angular/core';
import { Location } from '@angular/common';
import { UserService } from 'src/app/services/user/user.service';
import { AuthService } from 'src/app/services/auth/auth.service';
import { SnackBarService } from 'src/app/services/snackbar/snackbar.service';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.scss'],
})
export class ChangePasswordComponent {
  newPassword: string = '';
  oldPassword: string = '';
  repeatPassword: string = '';

  constructor(
    private location: Location,
    private userService: UserService,
    private authService: AuthService,
    private snackBarService: SnackBarService
  ) {}

  back(): void {
    this.location.back();
  }
  submitPasswordChanges() {
    if (this.newPassword !== this.repeatPassword) {
      this.snackBarService.openFailureSnackBar('new passwords do not match!');
      return;
    }
    if (this.newPassword.length < 6) {
      this.snackBarService.openFailureSnackBar(
        'Password must be at least 5 characters long'
      );
      return;
    }

    this.userService
      .sendChangePasswordRequest(
        this.authService.getCurrentUserId(),
        this.oldPassword,
        this.newPassword
      )
      .subscribe({
        next: (value) => {
          if (value) {
            this.snackBarService.openSuccessSnackBar(
              'Password changed successfully!'
            );
            this.back();
          } else {
            this.snackBarService.openFailureSnackBar(
              'You entered wrong password!'
            );
          }
        },
        error: (err) => {
          this.snackBarService.openFailureSnackBar(
            'You entered wrong password!'
          );
        },
      });
  }
}
