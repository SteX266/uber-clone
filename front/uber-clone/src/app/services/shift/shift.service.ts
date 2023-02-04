import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { LocationDTO } from 'src/app/models/location-dto.model';
import { environment } from 'src/environments/environment';
import { AuthService } from '../auth/auth.service';

@Injectable({
  providedIn: 'root',
})
export class ShiftService {
  constructor(private http: HttpClient, private authService: AuthService) {}

  startShift(locationDTO: LocationDTO) {
    let url = environment.apiEndpoint + 'location/startShift/';
    return this.http.post(
      url,
      locationDTO,
      this.authService.getHttpOptionsWithToken()
    );
  }
  toggleAvailable(available: boolean) {
    let url = environment.apiEndpoint + 'location/toggleAvailable/';
    return this.http.post(url, {
      available: available,
      driverId: this.authService.getCurrentUserId(),
    });
  }
}
