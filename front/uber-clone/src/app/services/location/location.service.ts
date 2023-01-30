import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class LocationService {
  constructor(private http: HttpClient) {}

  getActiveDriverLocations(): Observable<any> {
    let path = environment.apiEndpoint + 'location/getActiveDriverLocations';
    return this.http.get<any>(path);
  }
  getDriverLocationByRideId(rideId: number) {
    let path =
      environment.apiEndpoint + 'location/getDriverLocationByRideId/' + rideId;
    return this.http.get(path);
  }
}
