import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { LocationDTO } from 'src/app/models/location-dto.model';
import { environment } from 'src/environments/environment';
import { AuthService } from '../auth/auth.service';

@Injectable({
  providedIn: 'root',
})
export class LocationService {
  constructor(private http: HttpClient, private authService: AuthService) {}

  getActiveDriverLocations(): Observable<any> {
    let path = environment.apiEndpoint + 'location/getActiveDriverLocations';
    return this.http.get<any>(path);
  }
  getDriverLocationByRideId(rideId: number) {
    let path =
      environment.apiEndpoint + 'location/getDriverLocationByRideId/' + rideId;
    return this.http.get(path);
  }
  updateLocation(locationDTO: LocationDTO) {
    let path = environment.apiEndpoint + 'location/updateLocation';
    return this.http.post(
      path,
      locationDTO,
      this.authService.getHttpOptionsWithToken()
    );
  }
}
