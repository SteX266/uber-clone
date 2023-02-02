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
  startString = '';
  endString = '';

  stations = '';

  constructor() {}

  ngOnInit() {
    this.ride.stops.forEach((element) => {
      this.stations = this.stations + ' - ' + element;
    });
    this.stations = this.stations.slice(2);
    this.startString = this.formateDate(this.ride.startTime);
    this.endString = this.formateDate(this.ride.endTime);
  }

  formateDate(date: any) {
    return (
      date[3] + ':' + date[4] + ' ' + date[1] + '/' + date[2] + '/' + date[0]
    );
  }
}
