import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { FavoriteRouteDTO } from 'src/app/models/favorite-route-dto.model';
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

  addFavoriteRoute(favoriteRouteDto: FavoriteRouteDTO) {
    let url = environment.apiEndpoint + 'reservation/addFavoriteRoute';
    return this.http.post(
      url,
      favoriteRouteDto,
      this.authService.getHttpOptionsWithToken()
    );
  }

  getReservationByRideId(rideId:number){
    let url = environment.apiEndpoint + 'reservation/getReservationByRide/' + rideId;
    return this.http.get<any>(url, this.authService.getHttpOptionsWithToken());
  }
}
