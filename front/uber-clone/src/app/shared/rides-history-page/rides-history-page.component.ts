import { Component } from '@angular/core';
import { Ride } from 'src/app/models/ride';
import { UserProfileInfo } from 'src/app/models/user-profile-info';
import { AuthService } from 'src/app/services/auth/auth.service';
import { RideService } from 'src/app/services/ride/ride.service';

@Component({
  selector: 'app-rides-history-page',
  templateUrl: './rides-history-page.component.html',
  styleUrls: ['./rides-history-page.component.scss'],
})
export class RidesHistoryPageComponent {
  selectedCriteria = 'Price dsc';
  rides: Ride[] = [];

  stations = '';
  constructor(
    private rideService: RideService,
    private authService: AuthService
  ) {}
  ngOnInit() {
    this.rides = this.rideService.getRides(this.authService.getCurrentUserId);
    this.sorted();
  }

  sorted() {
    switch (this.selectedCriteria) {
      case 'Date asc': {
        this.rides.sort((a, b) => (a.startTime < b.startTime ? -1 : 1));
        break;
      }
      case 'Date dsc': {
        this.rides.sort((a, b) => (a.startTime >= b.startTime ? -1 : 1));

        break;
      }
      case 'Price asc': {
        this.rides.sort((a, b) => (a.estimatedCost < b.estimatedCost ? -1 : 1));

        break;
      }
      case 'Price dsc': {
        this.rides.sort((a, b) =>
          a.estimatedCost >= b.estimatedCost ? -1 : 1
        );
        break;
      }
    }
  }
}
