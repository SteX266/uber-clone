import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Vehicle } from 'src/app/models/car';
import { UserProfileInfo } from 'src/app/models/user-profile-info';
import { AuthService } from 'src/app/services/auth/auth.service';
import { CarService } from 'src/app/services/car/car.service';
import { UpdateRequestService } from 'src/app/services/update-requests/update-request.service';
import { Location } from '@angular/common';
import { SnackBarService } from 'src/app/services/snackbar/snackbar.service';

@Component({
  selector: 'app-update-request',
  templateUrl: './update-request.component.html',
  styleUrls: ['./update-request.component.scss'],
})
export class UpdateRequestComponent {
  selectedId = 0;
  user1 = new UserProfileInfo(0, '', '', '', '', '', '', '');
  car1 = new Vehicle(0, '', 0, false, false, '', 0);
  user2 = new UserProfileInfo(0, '', '', '', '', '', '', '');
  car2 = new Vehicle(0, '', 0, false, false, '', 0);

  constructor(
    private route: ActivatedRoute,
    private updateRequestService: UpdateRequestService,
    private carService: CarService,
    private snackBarService: SnackBarService,

    private location: Location
  ) {}
  back(): void {
    this.location.back();
  }
  ngOnInit() {
    this.route.params.subscribe((params) => {
      let id = params['id'];
      this.selectedId = Number(id);
      this.updateRequestService
        .getUpdateUserRequest(this.selectedId)
        .subscribe((data) => {
          this.user2 = data;
          this.carService
            .getCarByRequestId(this.selectedId)
            .subscribe((data) => {
              this.car2 = data;
            });
        });
      this.updateRequestService
        .getOldUserDataByRequestId(this.selectedId)
        .subscribe((data) => {
          this.user1 = data;
          this.car1 = this.carService.getCarByUserById(data.id);
        });
    });
  }

  declineRequest() {
    this.updateRequestService
      .declineRequest(this.selectedId)
      .subscribe((valid) => {
        if (valid) {
          this.snackBarService.openSuccessSnackBar('Successfully changed info');
          this.back();
        } else
          this.snackBarService.openFailureSnackBar('Something went wrong!!!!');
      });
  }

  acceptRequest() {
    this.updateRequestService
      .acceptRequest(this.selectedId)
      .subscribe((valid) => {
        if (valid) {
          this.snackBarService.openSuccessSnackBar('Successfully changed info');
          this.back();
        } else
          this.snackBarService.openFailureSnackBar('Something went wrong!!!!');
      });
  }
}
