import { ParseSourceFile } from '@angular/compiler';
import { Component, OnChanges, SimpleChanges } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Vehicle } from 'src/app/models/car';
import { UserProfileInfo } from 'src/app/models/user-profile-info';
import { AuthService } from 'src/app/services/auth/auth.service';
import { CarService } from 'src/app/services/car/car.service';
import { UserService } from 'src/app/services/user/user.service';
import { SafeResourceUrl } from '@angular/platform-browser';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.scss'],
})
export class UserProfileComponent {
  selectedId = 0;
  user = new UserProfileInfo(0, '', '', '', '', '', '', '');
  srcData: SafeResourceUrl | undefined;
  imageUrl: string = '';
  car = new Vehicle(0, '', 0, false, false, '', 0);
  constructor(
    private route: ActivatedRoute,
    private userService: UserService,
    private carService: CarService,
    private auth: AuthService,
    private sanitizer: DomSanitizer

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

    this.userService.getImage(Number(this.auth.getCurrentUserId())).subscribe((data)=>{

      this.imageUrl = URL.createObjectURL(data);
      this.srcData = this.sanitizer.bypassSecurityTrustUrl(this.imageUrl);

    });
  }

  setUserCar() {
    this.car = this.carService.getCarByUserById(this.selectedId);
  }
}
