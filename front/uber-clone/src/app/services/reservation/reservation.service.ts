import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ReservationDTO } from 'src/app/models/reservation-dto.model';
import { environment } from 'src/environments/environment';
import { AuthService } from '../auth/auth.service';

@Injectable({
  providedIn: 'root',
})
export class ReservationService {
  constructor(private http: HttpClient, private authService: AuthService) {}

  makeReservation(reservationDto: ReservationDTO) {
    let url = environment.apiEndpoint + 'reservation/makeReservation';
    return this.http.post(
      url,
      reservationDto,
      this.authService.getHttpOptionsWithToken()
    );
  }
}
