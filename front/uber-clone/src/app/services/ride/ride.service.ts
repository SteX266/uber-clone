import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class RideService {
  constructor(private http: HttpClient) {}

  getGeoJsonRouteById(rideId: number) {
    let url = environment.apiEndpoint + 'ride/getGeoJsonRoute/' + rideId;
    return this.http.get(url);
  }
}
