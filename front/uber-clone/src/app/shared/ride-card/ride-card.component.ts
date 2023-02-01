import { Component, Input } from '@angular/core';
import { Ride } from 'src/app/models/ride';
import { UserProfileInfo } from 'src/app/models/user-profile-info';

@Component({
  selector: 'app-ride-card',
  templateUrl: './ride-card.component.html',
  styleUrls: ['./ride-card.component.scss'],
})
export class RideCardComponent {
  @Input()
  ride!: Ride;

  stations = '';

  constructor() {}

  ngOnInit() {
    console.log(this.ride);
    this.ride.stops.forEach((element) => {
      this.stations = this.stations + ' - ' + element;
    });
    this.stations = this.stations.slice(2);
  }
}
