import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { SnackBarService } from 'src/app/services/snackbar/snackbar.service';
import { UserService } from 'src/app/services/user/user.service';

@Component({
  selector: 'app-admin-other-user-profile',
  templateUrl: './admin-other-user-profile.component.html',
  styleUrls: ['./admin-other-user-profile.component.scss'],
})
export class AdminOtherUserProfileComponent {
  id = 0;

  constructor(
    private userService: UserService,
    private route: ActivatedRoute,
    private snackBarService: SnackBarService
  ) {}

  ngOnInit() {
    this.route.params.subscribe((params) => {
      let id = params['id'];
      this.id = Number(id);
    });
  }

  BanUser() {
    this.userService.banUser(this.id).subscribe((valid) => {
      if (valid) {
        this.snackBarService.openSuccessSnackBar('User successfully baned');
      } else
        this.snackBarService.openFailureSnackBar('Something went wrong!!!!');
    });
  }
}
