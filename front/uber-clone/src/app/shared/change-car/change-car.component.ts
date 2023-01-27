import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { UserProfileInfo } from 'src/app/models/user-profile-info';
import { CarService } from 'src/app/services/car/car.service';
import { SnackBarService } from 'src/app/services/snackbar/snackbar.service';
import { Location } from '@angular/common';
import { UserService } from 'src/app/services/user/user.service';
import { Vehicle } from 'src/app/models/car';

@Component({
  selector: 'app-change-car',
  templateUrl: './change-car.component.html',
  styleUrls: ['./change-car.component.scss'],
})
export class ChangeCarComponent {
  selectedId = 0;
  car!: Vehicle;
  constructor(
    private route: ActivatedRoute,
    private carService: CarService,
    private snackBarService: SnackBarService,
    private location: Location
  ) {}

  back(): void {
    this.location.back();
  }
  ngOnInit() {
    this.route.params.subscribe((params) => {
      this.selectedId = Number(params['id']);
      this.car = new Vehicle();
      this.car.allowsBaby = true;
      this.car.type = 'REGULAR';
      this.car.model = 'Yugo';
      this.car.numberOfSeats = 4;
      console.log(this.car);
    });
  }
  submitProfileChanges() {
    if (this.validateInputData()) {
      this.carService.updateCar(this.car).subscribe();
    }
  }

  validateInputData() {
    if (this.car.model === '') {
      this.snackBarService.openFailureSnackBar('Model field is required!');

      return false;
    }

    if (2 > this.car.numberOfSeats || this.car.numberOfSeats > 10) {
      this.snackBarService.openFailureSnackBar(
        'Number of seat  must be betwene 2 and 10!'
      );

      return false;
    }
    if (this.car.numberOfSeats === 0) {
      this.snackBarService.openFailureSnackBar(
        'Number of seats field is required!'
      );
      return false;
    }
    return true;
  }
}
