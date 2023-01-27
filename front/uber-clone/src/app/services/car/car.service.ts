import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Vehicle } from '../../models/car';
import { AuthService } from '../auth/auth.service';

@Injectable({
  providedIn: 'root',
})
export class CarService {
  getCarByUserId(id: number) {
    return this.http.get<Vehicle>(
      environment.apiEndpoint + 'vehicle/getCarByUserId/' + id,
      this.authService.getHttpOptionsWithToken()
    );
  }

  constructor(private http: HttpClient, private authService: AuthService) {}

  updateCar(car: Vehicle) {
    return this.http.post<any>(
      environment.apiEndpoint + 'vehicle/updateVehicle',
      car,
      this.authService.getHttpOptionsWithToken()
    );
  }
}
