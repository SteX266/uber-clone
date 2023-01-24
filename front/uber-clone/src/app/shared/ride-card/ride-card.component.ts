import { Component } from '@angular/core';
import { Ride } from 'src/app/models/ride';
import { UserProfileInfo } from 'src/app/models/user-profile-info';

@Component({
  selector: 'app-ride-card',
  templateUrl: './ride-card.component.html',
  styleUrls: ['./ride-card.component.scss'],
})
export class RideCardComponent {
  ride = new Ride(
    1,
    [
      new UserProfileInfo(1, '', 'Vanja', 'Serfeze', '', '', '', ''),
      new UserProfileInfo(2, '', 'Mali', 'Slavko', '', '', '', ''),

      new UserProfileInfo(1, '', 'Petar', 'Markovic', '', '', '', ''),
      new UserProfileInfo(2, '', 'Milos', 'Bojanic', '', '', '', ''),
    ],
    'PREMIUM',
    true,
    true,
    'FINISHED',
    'INSTANT',
    120,
    new UserProfileInfo(2, '', 'Pera', 'Peric', '', '', '', ''),
    new Date(),
    new Date(),
    ['4.Jul 39', 'Koce kolarova 1', 'Junaka Milana Tepica 44']
  );

  stations = '';
  details = '';
  ngOnInit() {
    this.ride.stops.forEach((element) => {
      this.stations = this.stations + ' - ' + element;
    });
    this.stations = this.stations.slice(2);
  }
}
