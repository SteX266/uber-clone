import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Vehicle } from '../../models/car';
import { AuthService } from '../auth/auth.service';

@Injectable({
  providedIn: 'root',
})
export class CarService {
  constructor(private http: HttpClient, private authService: AuthService) {}

  getCarByUserById(idNumber: number): Vehicle {
    let car = new Vehicle(0, '', 0, false, false, '');

    this.sendGetCarByUserByIdRequest(idNumber).subscribe({
      next: (val) => {
        car.id = val.id;
        car.allowsBaby = val.allowsBaby;
        car.allowsPet = val.allowsPet;
        car.model = val.model;
        car.numberOfSeats = val.numberOfSeats;
        car.type = val.type;
      },
    });
    return car;
  }

  sendGetCarByUserByIdRequest(id: number) {
    return this.http.get<Vehicle>(
      environment.apiEndpoint + 'vehicle/getCarByUserId/' + id,
      this.authService.getHttpOptionsWithToken()
    );
  }

  updateCar(car: Vehicle) {
    return this.http.post<any>(
      environment.apiEndpoint + 'vehicle/updateVehicle',
      car,
      this.authService.getHttpOptionsWithToken()
    );
  }
}
