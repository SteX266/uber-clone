import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { UserProfileInfo } from 'src/app/models/user-profile-info';
import { SnackBarService } from 'src/app/services/snackbar/snackbar.service';
import { UserService } from 'src/app/services/user/user.service';
import { Location } from '@angular/common';
import { AuthService } from 'src/app/services/auth/auth.service';

@Component({
  selector: 'app-user-personal-info-update',
  templateUrl: './user-personal-info-update.component.html',
  styleUrls: ['./user-personal-info-update.component.scss'],
})
export class UserPersonalInfoUpdateComponent {
  selectedId = 0;
  user!: UserProfileInfo;
  constructor(
    private route: ActivatedRoute,
    private userService: UserService,
    private snackBarService: SnackBarService,
    private location: Location,
    private auth: AuthService
  ) {}

  back(): void {
    this.location.back();
  }
  ngOnInit() {
    this.route.params.subscribe((params) => {
      this.user = this.userService.getUserById(
        Number(this.auth.getCurrentUserId())
      );
    });
  }
  removePhoto() {
    this.user.profilePicture = '';
  }
  submitProfileChanges() {
    if (this.validateInputData()) {
      this.userService.updateUser(this.user).subscribe();
      this.snackBarService.openSuccessSnackBar('Successfully changed info');
      this.back();
    } else {
    }
  }

  validateInputData() {
    if (this.user.name === '') {
      this.snackBarService.openFailureSnackBar('Name field is required!');

      return false;
    }
    if (this.user.surname === '') {
      this.snackBarService.openFailureSnackBar('Surname field is required!');

      return false;
    }
    if (this.user.city === '') {
      this.snackBarService.openFailureSnackBar('City field is required!');
      return false;
    }
    if (this.user.phoneNumber === '') {
      this.snackBarService.openFailureSnackBar(
        'Phone number field is required!'
      );
      return false;
    }
    return true;
  }
}
