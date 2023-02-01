import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AbortDTO } from 'src/app/models/abort-dto.model';
import { GeoJsonFeature } from 'src/app/models/geo-json-feature.model';
import { UserProfileInfo } from 'src/app/models/user-profile-info';
import { environment } from 'src/environments/environment';
import { Ride } from '../../models/ride';
import { AuthService } from '../auth/auth.service';

@Injectable({
  providedIn: 'root',
})
export class RideService {
  constructor(private http: HttpClient, private authService: AuthService) {}

  getRides(getCurrentUserId: () => string): Ride[] {
    return [
      new Ride(
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
        220,
        new UserProfileInfo(2, '', 'Pera', 'Peric', '', '', '', ''),
        new Date(),
        new Date(),
        ['4.Jul 39', 'Koce kolarova 1', 'Junaka Milana Tepica 44']
      ),
      new Ride(
        1,
        [
          new UserProfileInfo(2, '', 'Vanja', 'Serfeze', '', '', '', ''),
          new UserProfileInfo(2, '', 'Mali', 'Slavko', '', '', '', ''),
          new UserProfileInfo(3, '', 'Petar', 'Markovic', '', '', '', ''),
          new UserProfileInfo(3, '', 'Milos', 'Bojanic', '', '', '', ''),
        ],
        'PREMIUM',
        true,
        true,
        'FINISHED',
        'INSTANT',
        150,
        new UserProfileInfo(2, '', 'Pera', 'Peric', '', '', '', ''),
        new Date(),
        new Date(),
        ['4.Jul 39', 'Koce kolarova 1']
      ),
    ];
  }

  getGeoJsonRouteById(rideId: number): Observable<string[]> {
    let url = environment.apiEndpoint + 'ride/getGeoJsonRoute/' + rideId;
    return this.http.get<string[]>(
      url,
      this.authService.getHttpOptionsWithToken()
    );
  }

  endRide(rideId: number) {
    let url = environment.apiEndpoint + 'ride/endRide';
    return this.http.post<string[]>(
      url,
      { rideId: rideId, driverId: this.authService.getCurrentUserId() },
      this.authService.getHttpOptionsWithToken()
    );
  }
  abortRide(rideId: number, reason: string) {
    let url = environment.apiEndpoint + 'ride/rejectRide';
    return this.http.post<string[]>(
      url,
      new AbortDTO(rideId, Number(this.authService.getCurrentUserId()), reason),
      this.authService.getHttpOptionsWithToken()
    );
  }

  startRide(rideId: number) {
    let url = environment.apiEndpoint + 'ride/startRide';
    return this.http.post<string[]>(
      url,
      { rideId: rideId, driverId: this.authService.getCurrentUserId() },
      this.authService.getHttpOptionsWithToken()
    );
  }

  driverArrived(rideId: number) {
    let url = environment.apiEndpoint + 'ride/driverArrived';
    return this.http.post<string[]>(
      url,
      rideId,
      this.authService.getHttpOptionsWithToken()
    );
  }
}
