import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { AuthService } from '../auth/auth.service';

@Injectable({
  providedIn: 'root',
})
export class TimeService {
  constructor(private http: HttpClient, private authService: AuthService) {}

  updateTime(timeInSeconds: number, rideId: number) {
    let url = environment.apiEndpoint + 'ride/updateTime';
    return this.http.post(
      url,
      { time: timeInSeconds, rideId: rideId },
      this.authService.getHttpOptionsWithToken()
    );
  }
}
