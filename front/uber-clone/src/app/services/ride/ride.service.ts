import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { AuthService } from '../auth/auth.service';

@Injectable({
  providedIn: 'root',
})
export class RideService {
  constructor(private http: HttpClient, private authService: AuthService) {}

  getGeoJsonRouteById(rideId: number) {
    let url = environment.apiEndpoint + 'ride/getGeoJsonRoute/' + rideId;
    return this.http.get(url, this.authService.getHttpOptionsWithToken());
  }
}
