import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { GeoJsonFeature } from 'src/app/models/geo-json-feature.model';
import { environment } from 'src/environments/environment';
import { AuthService } from '../auth/auth.service';

@Injectable({
  providedIn: 'root',
})
export class RideService {
  constructor(private http: HttpClient, private authService: AuthService) {}

  getGeoJsonRouteById(rideId: number): Observable<string[]> {
    let url = environment.apiEndpoint + 'ride/getGeoJsonRoute/' + rideId;
    return this.http.get<string[]>(
      url,
      this.authService.getHttpOptionsWithToken()
    );
  }
}
