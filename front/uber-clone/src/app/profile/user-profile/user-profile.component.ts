import { ParseSourceFile } from '@angular/compiler';
import { Component, OnChanges, SimpleChanges } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Vehicle } from 'src/app/models/car';
import { UserProfileInfo } from 'src/app/models/user-profile-info';
import { AuthService } from 'src/app/services/auth/auth.service';
import { CarService } from 'src/app/services/car/car.service';
import { UserService } from 'src/app/services/user/user.service';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.scss'],
})
export class UserProfileComponent {
  selectedId = 0;
  user = new UserProfileInfo(0, '', '', '', '', '', '', '');
  car = new Vehicle(0, '', 0, false, false, '', 0);
  constructor(
    private route: ActivatedRoute,
    private userService: UserService,
    private carService: CarService,
    private auth: AuthService
  ) {}

  ngOnInit() {
    this.route.params.subscribe((params) => {
      let id = params['id'];
      if (id == undefined) {
        id = this.auth.getCurrentUserId();
      }
      this.selectedId = Number(id);
      this.user = this.userService.getUserById(this.selectedId);
      this.setUserCar();
    });
  }

  setUserCar() {
    this.car = this.carService.getCarByUserById(this.selectedId);
  }
}
